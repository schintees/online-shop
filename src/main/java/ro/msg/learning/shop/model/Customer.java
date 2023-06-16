package ro.msg.learning.shop.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends EntityWithUUID {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String emailAddress;

    @ToString.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;
}
