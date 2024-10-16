package sn.bmbacke.pad.eca.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.bmbacke.pad.eca.notification.NotificationProducer;
import sn.bmbacke.pad.eca.notification.PaymentNotificationRequest;

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
