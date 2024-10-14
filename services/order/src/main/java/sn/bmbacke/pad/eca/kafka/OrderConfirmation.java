package sn.bmbacke.pad.eca.kafka;

import sn.bmbacke.pad.eca.customer.CustomerResponse;
import sn.bmbacke.pad.eca.order.PaymentMethod;
import sn.bmbacke.pad.eca.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
