package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"productId", "productName", "locationId", "locationName", "quantity"})
public class StockDTO {
    private UUID productId;
    private String productName;
    private UUID locationId;
    private String locationName;
    private Integer quantity;
}
