package ro.msg.learning.shop.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import ro.msg.learning.shop.exception.OutOfStockException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.service.helper.DistanceHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ClosestLocationStrategy implements OrderStrategy {

    @Autowired
    private LocationService locationService;

    @Autowired
    private StockService stockService;

    @Autowired
    private DistanceHelper distanceHelper;

    @Override
    public List<Stock> findStocks(Order order) {

        List<OrderDetail> orderDetails = order.getOrderDetails();
        List<Location> locations = locationService.getAll();

        List<BigDecimal> distances = distanceHelper.getDistancesForLocation(order.getAddress(), locations.stream().map(Location::getAddress).toList());

        SortedMap<BigDecimal, Location> distanceLocationMap = IntStream.range(1, distances.size())
                .boxed()
                .collect(Collectors.toMap(
                        distances::get,
                        index -> locations.get(index - 1),
                        (a, b) -> b,
                        TreeMap::new
                ));

        return distanceLocationMap.values().stream().map(location -> findStockForLocation(location, orderDetails))
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(OutOfStockException::new);
    }

    private Optional<List<Stock>> findStockForLocation(Location location, List<OrderDetail> orderDetails) {

        List<Stock> foundStocks = orderDetails.stream()
                .map(orderDetail -> stockService.getByProductAndQuantityAndLocation(orderDetail.getProduct(), orderDetail.getQuantity(), location))
                .takeWhile(Optional::isPresent)
                .map(Optional::get)
                .toList();

        return orderDetails.size() == foundStocks.size() ? Optional.of(foundStocks) : Optional.empty();
    }
}
