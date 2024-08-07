package sn.bmbacke.pad.eca.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public Integer createProduct(ProductRequest productRequest) {
        return null;
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> productPurchaseRequests) {
        return null;
    }


    public ProductResponse getProduct(Integer productId) {
        return null;
    }

    public List<ProductResponse> getProducts() {
        return null;
    }
}
