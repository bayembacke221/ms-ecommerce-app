package sn.bmbacke.pad.eca;

import sn.bmbacke.pad.eca.customer.CustomerResponse;
import sn.bmbacke.pad.eca.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer orderId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String orderReference,
        CustomerResponse customer
) {
}
