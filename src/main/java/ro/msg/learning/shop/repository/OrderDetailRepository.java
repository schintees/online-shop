package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.OrderDetail;

import java.util.UUID;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
}
