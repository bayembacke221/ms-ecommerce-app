package sn.bmbacke.pad.eca.payment;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        Integer orderId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String orderReference,
        Customer customer
) {
}
