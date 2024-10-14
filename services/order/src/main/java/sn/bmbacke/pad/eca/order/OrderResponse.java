package sn.bmbacke.pad.eca.order;

import java.math.BigDecimal;

public record OrderResponse(
        Integer id,
        String reference,
        BigDecimal amount,
        String customerId,
        PaymentMethod paymentMethod
) {
}
