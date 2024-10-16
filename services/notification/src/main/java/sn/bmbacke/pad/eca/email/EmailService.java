package sn.bmbacke.pad.eca.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.UTF8;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import sn.bmbacke.pad.eca.kafka.order.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_RELATED;
import static sn.bmbacke.pad.eca.email.EmailTemplates.ORDER_CONFIRMATION;
import static sn.bmbacke.pad.eca.email.EmailTemplates.PAYMENT_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sentPaymentSuccessEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());

        helper.setFrom("mbackembaye74@gmail.com");

        String template = PAYMENT_CONFIRMATION.getTemplateName();

        Map<String, Object> variables= new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(variables);
        helper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(template, context);
            helper.setText(htmlTemplate, true);
            helper.setTo(destinationEmail);
            javaMailSender.send(mimeMessage);
            log.info("INFO: Email sent successfully to {} with template {}", destinationEmail, template);
        } catch (MessagingException e) {
            log.warn("WARN: Error sending email to {} with template {}", destinationEmail, template);
        }
    }


    @Async
    public void sentOrderConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products
    ) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());

        helper.setFrom("mbackembaye74@gmail.com");

        String template = ORDER_CONFIRMATION.getTemplateName();

        Map<String, Object> variables= new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", amount);
        variables.put("orderReference", orderReference);
        variables.put("products", products);

        Context context = new Context();
        context.setVariables(variables);
        helper.setSubject(ORDER_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(template, context);
            helper.setText(htmlTemplate, true);
            helper.setTo(destinationEmail);
            javaMailSender.send(mimeMessage);
            log.info("INFO: Email sent successfully to {} with template {}", destinationEmail, template);
        } catch (MessagingException e) {
            log.warn("WARN: Error sending email to {} with template {}", destinationEmail, template);
        }
    }
}
