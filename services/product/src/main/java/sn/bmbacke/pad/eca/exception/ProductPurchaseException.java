package sn.bmbacke.pad.eca.exception;

public class ProductPurchaseException extends RuntimeException {
    public ProductPurchaseException(String someProductsNotFound) {
        super(someProductsNotFound);
    }
}
