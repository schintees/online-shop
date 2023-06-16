package ro.msg.learning.shop.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {
    private String country;
    private String city;
    private String county;
    private String streetAddress;

}
