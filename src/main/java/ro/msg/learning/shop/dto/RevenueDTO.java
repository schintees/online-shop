package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"locationId", "locationName", "date", "sum"})
public class RevenueDTO {

    private UUID locationId;
    private String locationName;
    private LocalDate date;
    private BigDecimal sum;

}
