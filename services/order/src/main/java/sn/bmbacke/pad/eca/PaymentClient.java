package sn.bmbacke.pad.eca;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "payment-service",
        url = "${application.config.payment-url}"
)
public interface PaymentClient {

    Integer requestOrderPayment(@RequestBody PaymentRequest paymentRequest) ;
}
