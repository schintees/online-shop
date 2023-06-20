package ro.msg.learning.shop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.Address;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private UUID id;
    private UUID shippedFrom;
    @NotNull
    private UUID customer;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private Address address;
    @NotNull
    private List<OrderDetailDTO> orderDetails;

}
