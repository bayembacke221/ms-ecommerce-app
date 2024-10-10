package sn.bmbacke.pad.eca.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(Integer id,
                             @NotNull(message = "Product name is required")
                             String name,
                             @NotNull(message = "Product description is required")
                             String description,
                             @Positive(message = "Product price must be greater than 0")
                             BigDecimal price,
                             @Positive(message = "Product stock must be greater than 0")
                             Integer stock,
                             @NotNull(message = "Product category is required")
                             Integer categoryId
) {
}
