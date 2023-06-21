package ro.msg.learning.shop.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@SuperBuilder
public class Address {
    private String country;
    private String city;
    private String county;
    private String streetAddress;

}
