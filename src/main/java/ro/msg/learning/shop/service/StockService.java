package ro.msg.learning.shop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.ExportEntitiesNotFoundException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StockService {

    @Autowired
    private LocationService locationService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductService productService;

    public void create(Stock stock) {
        stockRepository.save(stock);
    }

    public List<Stock> getAllByProductAndQuantity(Product product, Integer quantity) {
        return stockRepository.findByProductAndQuantityGreaterThanEqualOrderByQuantityDesc(
                productService.get(product.getId()), quantity);
    }

    public Optional<Stock> getByProductAndQuantityAndLocation(Product product, Integer quantity, Location location) {
        return stockRepository.findByProductAndQuantityGreaterThanEqualAndLocationOrderByQuantityDesc(
                productService.get(product.getId()), quantity, location);
    }

    public void update(Stock stock, Integer quantityToBeTaken) {
        stock.setQuantity(stock.getQuantity() - quantityToBeTaken);
        stockRepository.save(stock);
    }

    public List<Stock> getAllByLocation(UUID locationId) {
        List<Stock> stocks = stockRepository.findByLocation(locationService.get(locationId));
        if (stocks.isEmpty()) {
            throw new ExportEntitiesNotFoundException(Stock.class, locationId.toString());
        }
        return stocks;
    }

    public void deleteAll() {
        stockRepository.deleteAll();
    }

}
