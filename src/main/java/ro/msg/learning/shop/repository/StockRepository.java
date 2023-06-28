package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    List<Stock> findByProductAndQuantityGreaterThanEqualOrderByQuantityDesc(Product product, Integer quantity);

    Optional<Stock> findByProductAndQuantityGreaterThanEqualAndLocationOrderByQuantityDesc(Product product,
                                                                                           Integer quantity,
                                                                                           Location location);

    List<Stock> findByLocation(Location location);

}
