package sn.bmbacke.pad.eca.orderline;

import jakarta.validation.constraints.NotNull;

public record OrderLineRequest(Integer id,
                               @NotNull(message = "Order is required")
                               Integer orderId,
                               @NotNull(message = "Product is required")
                               Integer productId,
                               @NotNull(message = "Quantity is required")
                               double quantity) {
}
