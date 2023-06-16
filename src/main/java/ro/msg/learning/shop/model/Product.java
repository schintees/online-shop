package ro.msg.learning.shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends EntityWithUUID {
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private String imageUrl;

    @ToString.Exclude
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    @ToString.Exclude
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Stock> stocks;

}
