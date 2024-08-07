package sn.bmbacke.pad.eca.customer;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import sn.bmbacke.pad.eca.exception.CustomerNotFoundException;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    public String createCustomer(CustomerRequest customerRequest) {
        var customer = customerRepository.save(customerMapper.toCustomer(customerRequest));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest customerRequest) {
        var customer = customerRepository.findById(customerRequest.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Cannot update customer:: No customer found with the provided :ID: %s", customerRequest.id())
                ));
        mergerCustomer(customer, customerRequest);
        customerRepository.save(customer);
    }

    private void mergerCustomer(Customer customer, CustomerRequest customerRequest) {
        if (StringUtils.isNotBlank(customerRequest.firstname())) {
            customer.setFirstname(customerRequest.firstname());
        }
        if (StringUtils.isNotBlank(customerRequest.lastname())) {
            customer.setLastname(customerRequest.lastname());
        }
        if (StringUtils.isNotBlank(customerRequest.email())) {
            customer.setEmail(customerRequest.email());
        }
        if (customerRequest.address() != null) {
            customer.setAddress(customerRequest.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::fromCustomer
                )
                .toList();
    }

    public Boolean isCustomerExist(String customerId) {
        return customerRepository
                .findById(customerId)
                .isPresent();
    }

    public CustomerResponse getCustomer(String customerId) {
        var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Cannot get customer:: No customer found with the provided :ID: %s", customerId)
                ));
        return customerMapper.fromCustomer(customer);
    }

    public void deleteCustomer(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException(
                    format("Cannot delete customer:: No customer found with the provided :ID: %s", customerId)
            );
        }
        customerRepository.deleteById(customerId);
    }
}
