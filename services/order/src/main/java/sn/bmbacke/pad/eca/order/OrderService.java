package sn.bmbacke.pad.eca.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.bmbacke.pad.eca.customer.CustomerClient;
import sn.bmbacke.pad.eca.exception.BusinessException;
import sn.bmbacke.pad.eca.orderline.OrderLine;
import sn.bmbacke.pad.eca.orderline.OrderLineRequest;
import sn.bmbacke.pad.eca.orderline.OrderLineService;
import sn.bmbacke.pad.eca.product.ProductClient;
import sn.bmbacke.pad.eca.product.PurchaseRequest;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;

    private final ProductClient productClient;

    private final OrderRepository orderRepository;

    private final OrderMapper mapper;

    private final OrderLineService orderLineService;

    public Integer createOrder(@Valid OrderRequest orderRequest) {
        var customer = customerClient.getCustomer(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order ::: Customer not found"));

        this.productClient.purchaseProducts(orderRequest.products());

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
    }
}
