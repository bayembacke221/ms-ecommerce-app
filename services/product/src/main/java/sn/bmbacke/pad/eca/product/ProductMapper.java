package sn.bmbacke.pad.eca.product;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import sn.bmbacke.pad.eca.category.Category;

@Service
public class ProductMapper {

    public Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .id(productRequest.id())
                .name(productRequest.name())
                .description(productRequest.description())
                .availableQuantity(productRequest.stock())
                .price(productRequest.price())
                .category(
                        Category.builder()
                        .id(productRequest.categoryId())
                        .build()
                )
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product storedProduct, @NotNull(message = "Quantity is mandatory") Integer quantity) {
        return new ProductPurchaseResponse(
                storedProduct.getId(),
                storedProduct.getName(),
                storedProduct.getDescription(),
                storedProduct.getPrice(),
                quantity
        );
    }
}
