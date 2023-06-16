package ro.msg.learning.shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends EntityWithUUID {
    @ManyToOne
    @JoinColumn(name = "shipped_from_id")
    private Location shippedFrom;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDateTime createdAt;

    @Embedded
    private Address address;

    @ToString.Exclude
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}
