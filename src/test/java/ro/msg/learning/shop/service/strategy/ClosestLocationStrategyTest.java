package ro.msg.learning.shop.service.strategy;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ro.msg.learning.shop.exception.OutOfStockException;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.service.helper.DistanceHelper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClosestLocationStrategyTest {

    @InjectMocks
    private ClosestLocationStrategy strategy;

    @Mock
    private StockService stockService;

    @Mock
    private LocationService locationService;

    @Mock
    private DistanceHelper distanceHelper;

    @Test
    void testFindStocks_Success() {
        // Given
        Product product1 = Product.builder().id(UUID.randomUUID()).name("av").build();
        Product product2 = Product.builder().id(UUID.randomUUID()).name("bv").build();
        OrderDetail orderDetail1 = OrderDetail.builder().product(product1).quantity(50).build();
        OrderDetail orderDetail2 = OrderDetail.builder().product(product2).quantity(20).build();
        List<OrderDetail> orderDetails = Arrays.asList(orderDetail1, orderDetail2);

        Address address1 = Address.builder().city("Cluj-Napoca").country("Romania").streetAddress("Artarului").county("Cluj").build();
        Address address2 = Address.builder().city("Timisoara").country("Romania").streetAddress("Uranus").county("Timis").build();

        Location location1 = Location.builder().id(UUID.randomUUID()).name("Cluj").address(address1).build();
        Location location2 = Location.builder().id(UUID.randomUUID()).name("Timis").address(address2).build();

        Stock stock1 = Stock.builder().product(product1).location(location1).quantity(65).build();
        Stock stock2 = Stock.builder().product(product2).location(location2).quantity(77).build();
        Stock stock3 = Stock.builder().product(product2).location(location1).quantity(23).build();

        when(locationService.getAll()).thenReturn(List.of(location1, location2));
        when(distanceHelper.getDistancesForLocation(any(), any()))
                .thenReturn(List.of(BigDecimal.valueOf(0), BigDecimal.valueOf(249.0909), BigDecimal.valueOf(210.9686)));

        when(stockService.getByProductAndQuantityAndLocation(eq(product1), anyInt(), eq(location1))).thenReturn(Optional.of(stock1));
        when(stockService.getByProductAndQuantityAndLocation(eq(product1), anyInt(), eq(location2))).thenReturn(Optional.empty());
        when(stockService.getByProductAndQuantityAndLocation(eq(product2), anyInt(), eq(location1))).thenReturn(Optional.of(stock3));
        when(stockService.getByProductAndQuantityAndLocation(eq(product2), anyInt(), eq(location2))).thenReturn(Optional.of(stock2));

        // When
        List<Stock> result = strategy.findStocks(Order.builder().orderDetails(orderDetails).build());

        // Then
        assertEquals(2, result.size());
        assertEquals(location1.getId(), result.get(0).getLocation().getId());
        assertEquals(location1.getId(), result.get(1).getLocation().getId());

        verify(stockService, times(3)).getByProductAndQuantityAndLocation(any(Product.class), anyInt(), any(Location.class));
    }

    @Test
    void testFindStocks_OutOfStock() {
        // Given
        Product product1 = Product.builder().id(UUID.randomUUID()).name("av").build();
        Product product2 = Product.builder().id(UUID.randomUUID()).name("bv").build();
        OrderDetail orderDetail1 = OrderDetail.builder().product(product1).quantity(50).build();
        OrderDetail orderDetail2 = OrderDetail.builder().product(product2).quantity(20).build();
        List<OrderDetail> orderDetails = Arrays.asList(orderDetail1, orderDetail2);

        Address address1 = Address.builder().city("Cluj-Napoca").country("Romania").streetAddress("Artarului").county("Cluj").build();
        Address address2 = Address.builder().city("Timisoara").country("Romania").streetAddress("Uranus").county("Timis").build();

        Location location1 = Location.builder().id(UUID.randomUUID()).name("Cluj").address(address1).build();
        Location location2 = Location.builder().id(UUID.randomUUID()).name("Timis").address(address2).build();

        Stock stock1 = Stock.builder().product(product1).location(location1).quantity(65).build();
        Stock stock2 = Stock.builder().product(product2).location(location2).quantity(77).build();
        Stock stock3 = Stock.builder().product(product2).location(location1).quantity(23).build();

        when(locationService.getAll()).thenReturn(List.of(location1, location2));
        when(distanceHelper.getDistancesForLocation(any(), any()))
                .thenReturn(List.of(BigDecimal.valueOf(0), BigDecimal.valueOf(249.0909), BigDecimal.valueOf(210.9686)));

        when(stockService.getByProductAndQuantityAndLocation(eq(product1), anyInt(), eq(location1))).thenReturn(Optional.of(stock1));
        when(stockService.getByProductAndQuantityAndLocation(eq(product1), anyInt(), eq(location2))).thenReturn(Optional.empty());
        when(stockService.getByProductAndQuantityAndLocation(eq(product2), anyInt(), eq(location1))).thenReturn(Optional.empty());
        when(stockService.getByProductAndQuantityAndLocation(eq(product2), anyInt(), eq(location2))).thenReturn(Optional.of(stock2));

        // When
        assertThrows(OutOfStockException.class, () ->
                strategy.findStocks(Order.builder().orderDetails(orderDetails).build())
        );

        // Then
        verify(stockService, times(3)).getByProductAndQuantityAndLocation(any(Product.class), anyInt(), any(Location.class));
    }
}