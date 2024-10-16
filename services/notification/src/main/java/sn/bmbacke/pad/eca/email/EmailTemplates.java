package sn.bmbacke.pad.eca.email;

import lombok.Getter;

public enum EmailTemplates {
    ORDER_CONFIRMATION("order-confirmation.html","Order Confirmation"),
    PAYMENT_CONFIRMATION("payment-confirmation.html","Payment Successfully processed");
    @Getter
    private String templateName;
    @Getter
    private String subject;

    EmailTemplates(String templateName, String subject) {
        this.templateName = templateName;
        this.subject = subject;
    }
}
