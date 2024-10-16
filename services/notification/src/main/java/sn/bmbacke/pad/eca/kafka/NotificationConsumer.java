package sn.bmbacke.pad.eca.kafka;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import sn.bmbacke.pad.eca.email.EmailService;
import sn.bmbacke.pad.eca.kafka.order.OrderConfirmation;
import sn.bmbacke.pad.eca.kafka.payment.PaymentConfirmation;
import sn.bmbacke.pad.eca.notification.Notification;
import sn.bmbacke.pad.eca.notification.NotificationRepository;
import sn.bmbacke.pad.eca.notification.NotificationType;

import java.time.LocalDateTime;

import static java.lang.String.format;
import static sn.bmbacke.pad.eca.notification.NotificationType.ORDER_CONFIRMATION;
import static sn.bmbacke.pad.eca.notification.NotificationType.PAYMENT_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumerPaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming the message  from payment-topic Topic::: {}", paymentConfirmation);

        repository.save(
                Notification.builder()
                        .type(PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        var customerName = format("%s %s", paymentConfirmation.customerFirstName(), paymentConfirmation.customerLastName());

        emailService.sentPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );

    }

    @KafkaListener(topics = "order-topic")
    public void consumerOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming the message  from order-topic Topic::: {}", orderConfirmation);

        repository.save(
                Notification.builder()
                        .type(ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );
        var customerName = format("%s %s", orderConfirmation.customer().firstname(), orderConfirmation.customer().lastname());

        emailService.sentOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }
}
