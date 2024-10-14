package sn.bmbacke.pad.eca.payment.payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.bmbacke.pad.eca.payment.notification.NotificationProducer;
import sn.bmbacke.pad.eca.payment.notification.PaymentNotificationRequest;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;
    public Integer createPayment(PaymentRequest paymentRequest) {
        var payment = repository.save(mapper.toPayment(paymentRequest));
        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        paymentRequest.orderReference(),
                        paymentRequest.amount(),
                        paymentRequest.paymentMethod(),
                        paymentRequest.customer().firstname(),
                        paymentRequest.customer().lastname(),
                        paymentRequest.customer().email()
                )
        );
        return payment.getId();
    }
}
