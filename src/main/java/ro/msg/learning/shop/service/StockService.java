package ro.msg.learning.shop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.List;
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

    public void update(Stock stock, Integer quantityToBeTaken) {
        stock.setQuantity(stock.getQuantity() - quantityToBeTaken);
        stockRepository.save(stock);
    }

    public List<Stock> getAllByLocation(UUID locationId) {
        return stockRepository.findByLocation(locationService.get(locationId));
    }

    public void deleteAll() {
        stockRepository.deleteAll();
    }

}
