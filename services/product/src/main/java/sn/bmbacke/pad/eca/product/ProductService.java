package sn.bmbacke.pad.eca.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.bmbacke.pad.eca.exception.ProductPurchaseException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public Integer createProduct(ProductRequest productRequest) {
        var product = productMapper.toProduct(productRequest);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> productPurchaseRequests) throws ProductPurchaseException {
        var productIds = productPurchaseRequests.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        var storedProducts= productRepository.findAllByIdInOrderById(productIds);
        if (storedProducts.size() != productIds.size()) {
            throw new ProductPurchaseException("Some products not found");
        }

        var storedRequest = productPurchaseRequests.stream().sorted(Comparator.comparing(ProductPurchaseRequest::productId)).toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var storedProduct = storedProducts.get(i);
            var request = storedRequest.get(i);
            if (storedProduct.getAvailableQuantity() < request.quantity()) {
                throw new ProductPurchaseException("Not enough stock for product " + storedProduct.getName());
            }

            storedProduct.setAvailableQuantity(storedProduct.getAvailableQuantity() - request.quantity());

            productRepository.save(storedProduct);

            purchasedProducts.add(productMapper.toProductPurchaseResponse(storedProduct,request.quantity()));
        }
        return purchasedProducts;
    }


    public ProductResponse getProduct(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }
}
