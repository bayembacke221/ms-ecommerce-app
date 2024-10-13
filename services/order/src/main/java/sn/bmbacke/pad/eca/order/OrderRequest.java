package sn.bmbacke.pad.eca.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import sn.bmbacke.pad.eca.product.PurchaseRequest;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Amount must be positive")
        BigDecimal amount,
        @NotNull(message = "Customer  is required")
                @NotEmpty(message = "Customer is required")
                @NotBlank(message = "Customer is required")
        String customerId,
        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,
        @NotEmpty(message = "You should at least have one product in your order")
        List<PurchaseRequest> products
) {
}
