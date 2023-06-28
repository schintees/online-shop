package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.model.Address;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MapQuestRequestDTO {

    private List<Address> locations;

}
