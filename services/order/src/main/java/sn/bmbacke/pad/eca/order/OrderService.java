package sn.bmbacke.pad.eca.order;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.bmbacke.pad.eca.customer.CustomerClient;
import sn.bmbacke.pad.eca.exception.BusinessException;
import sn.bmbacke.pad.eca.kafka.OrderConfirmation;
import sn.bmbacke.pad.eca.kafka.OrderProducer;
import sn.bmbacke.pad.eca.orderline.OrderLine;
import sn.bmbacke.pad.eca.orderline.OrderLineRequest;
import sn.bmbacke.pad.eca.orderline.OrderLineService;
import sn.bmbacke.pad.eca.product.ProductClient;
import sn.bmbacke.pad.eca.product.PurchaseRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;

    private final ProductClient productClient;

    private final OrderRepository orderRepository;

    private final OrderMapper mapper;

    private final OrderLineService orderLineService;

    private final OrderProducer orderProducer;

    public Integer createOrder(@Valid OrderRequest orderRequest) {
        var customer = customerClient.getCustomer(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order ::: Customer not found"));

        var purchasedProduct=this.productClient.purchaseProducts(orderRequest.products());

        var orderSaved = orderRepository.save(mapper.toOrder(orderRequest));

        for (PurchaseRequest purchaseRequest: orderRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            orderSaved.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProduct
                )
        );


        return  orderSaved.getId();
    }

    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream()
                .map(mapper::fromOrder)
                .toList();
    }

    public OrderResponse getOrder(Integer id) {
        return orderRepository.findById(id)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id %s not found", id)));
    }
}
