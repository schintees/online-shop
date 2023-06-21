package ro.msg.learning.shop.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSxxx");

    private String timestamp;
    private String error;

    public ErrorResponse(String error) {
        this.timestamp = formatter.format(ZonedDateTime.now(ZoneId.of("UTC")));
        this.error = error;
    }

}

