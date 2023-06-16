package ro.msg.learning.shop.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Location extends EntityWithUUID {
    private String name;

    @Embedded
    private Address address;

    @ToString.Exclude
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Revenue> revenues;

    @ToString.Exclude
    @OneToMany(mappedBy = "shippedFrom")
    private List<Order> orders;

    @ToString.Exclude
    @OneToMany(mappedBy = "location")
    private List<Stock> stocks;

}
