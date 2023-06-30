package ro.msg.learning.shop.mapper;

import ro.msg.learning.shop.dto.RevenueDTO;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Revenue;

public class RevenueMapper {

    public static RevenueDTO toDto(Revenue revenue) {
        if (revenue == null) {
            return null;
        }
        return RevenueDTO.builder()
                .locationId(revenue.getLocation().getId())
                .locationName(revenue.getLocation().getName())
                .date(revenue.getDate())
                .sum(revenue.getSum())
                .build();
    }

    public static Revenue toEntity(RevenueDTO revenueDTO) {
        if (revenueDTO == null) {
            return null;
        }
        return Revenue.builder()
                .location(Location.builder().id(revenueDTO.getLocationId()).name(revenueDTO.getLocationName()).build())
                .date(revenueDTO.getDate())
                .sum(revenueDTO.getSum())
                .build();
    }
}
