package sn.bmbacke.pad.eca.customer;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
