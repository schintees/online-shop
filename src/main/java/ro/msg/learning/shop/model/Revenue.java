package ro.msg.learning.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Revenue extends EntityWithUUID {
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private LocalDate date;

    private BigDecimal sum;
}
