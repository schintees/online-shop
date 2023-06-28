package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MapQuestResponseDTO {

    private Info info;
    private List<BigDecimal> distance;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info {
        @JsonProperty("statuscode")
        private Integer statusCode;
        private List<String> messages;
    }

}
