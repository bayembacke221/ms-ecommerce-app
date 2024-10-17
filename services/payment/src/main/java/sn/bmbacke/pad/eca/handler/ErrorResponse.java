package sn.bmbacke.pad.eca.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {

}
