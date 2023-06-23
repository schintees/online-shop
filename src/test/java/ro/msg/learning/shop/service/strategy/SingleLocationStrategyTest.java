package ro.msg.learning.shop.service.strategy;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ro.msg.learning.shop.exception.OutOfStockException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.service.StockService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class SingleLocationStrategyTest {

    @InjectMocks
    private SingleLocationStrategy strategy;

    @Mock
    private StockService stockService;

    @Test
    public void testFindStocks_Success() {
        // Given
        Product product1 = Product.builder().id(UUID.randomUUID()).name("av").build();
        Product product2 = Product.builder().id(UUID.randomUUID()).name("bv").build();
        OrderDetail orderDetail1 = OrderDetail.builder().product(product1).quantity(5).build();
        OrderDetail orderDetail2 = OrderDetail.builder().product(product2).quantity(2).build();
        List<OrderDetail> orderDetails = Arrays.asList(orderDetail1, orderDetail2);

        Location location1 = Location.builder().id(UUID.randomUUID()).name("Cluj").build();
        Location location2 = Location.builder().id(UUID.randomUUID()).name("Timis").build();

        Stock stock1 = Stock.builder().product(product1).location(location2).quantity(6).build();
        Stock stock2 = Stock.builder().product(product2).location(location1).quantity(23).build();
        Stock stock3 = Stock.builder().product(product2).location(location2).quantity(11).build();

        when(stockService.getAllByProductAndQuantity(eq(product1), anyInt())).thenReturn(List.of(stock1));
        when(stockService.getAllByProductAndQuantity(eq(product2), anyInt())).thenReturn(List.of(stock2, stock3));

        // When
        List<Stock> result = strategy.findStocks(orderDetails);

        // Then
        assertEquals(2, result.size());
        assertEquals(location2.getId(), result.get(0).getLocation().getId());
        assertEquals(location2.getId(), result.get(1).getLocation().getId());

        verify(stockService, times(2)).getAllByProductAndQuantity(any(Product.class), anyInt());
    }

    @Test
    public void testFindStocks_OutOfStock() {
        // Given
        Product product1 = Product.builder().id(UUID.randomUUID()).name("av").build();
        Product product2 = Product.builder().id(UUID.randomUUID()).name("bv").build();
        OrderDetail orderDetail1 = OrderDetail.builder().product(product1).quantity(5).build();
        OrderDetail orderDetail2 = OrderDetail.builder().product(product2).quantity(2).build();
        List<OrderDetail> orderDetails = Arrays.asList(orderDetail1, orderDetail2);

        Location location1 = Location.builder().id(UUID.randomUUID()).name("Cluj").build();
        Location location2 = Location.builder().id(UUID.randomUUID()).name("Timis").build();

        Stock stock1 = Stock.builder().product(product2).location(location1).quantity(11).build();
        Stock stock2 = Stock.builder().product(product2).location(location2).quantity(0).build();

        when(stockService.getAllByProductAndQuantity(eq(product1), anyInt())).thenReturn(List.of());
        when(stockService.getAllByProductAndQuantity(eq(product2), anyInt())).thenReturn(List.of(stock1, stock2));

        // When
        assertThrows(OutOfStockException.class, () -> {
            strategy.findStocks(orderDetails);
        });

        // Then
        verify(stockService, times(1)).getAllByProductAndQuantity(any(Product.class), anyInt());
    }

}