package ro.msg.learning.shop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.ExportEntitiesNotFoundException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Revenue;
import ro.msg.learning.shop.repository.RevenueRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class RevenueService {

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private OrderService orderService;

    public List<Revenue> getAllByDate(LocalDate localDate) {
        List<Revenue> revenues = revenueRepository.findAllByDate(localDate);
        if (revenues.isEmpty()) {
            throw new ExportEntitiesNotFoundException(Revenue.class, localDate.toString());
        }
        return revenues;
    }

    public void saveRevenuesForToday() {
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        LocalDate nowLocalDate = nowLocalDateTime.toLocalDate();
        List<Order> orders = orderService.getAllByCreatedAt(nowLocalDate.atStartOfDay(), nowLocalDateTime);

        Map<UUID, BigDecimal> locationSumMap = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getShippedFrom().getId(),
                        Collectors.mapping(
                                order -> order.getOrderDetails().stream()
                                        .map(this::getOrderDetailPrice)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));

        locationSumMap.forEach((location, sum) -> revenueRepository.save(computeRevenue(location, sum, nowLocalDate)));
    }

    private BigDecimal getOrderDetailPrice(OrderDetail orderDetail) {
        return orderDetail.getProduct().getPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity()));
    }

    private Revenue computeRevenue(UUID locationId, BigDecimal locationSum, LocalDate localDate) {
        return Revenue.builder()
                .location(Location.builder().id(locationId).build())
                .sum(locationSum)
                .date(localDate)
                .build();
    }

}
