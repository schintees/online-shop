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
public class Supplier extends EntityWithUUID {
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Product> products;
}
