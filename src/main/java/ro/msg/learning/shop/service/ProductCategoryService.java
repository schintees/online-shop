package ro.msg.learning.shop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.repository.ProductCategoryRepository;

@Service
@Transactional
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductCategory createIfNotFound(ProductCategory productCategory) {
        return productCategoryRepository.findByName(productCategory.getName())
                .orElseGet(() -> productCategoryRepository.save(productCategory));
    }

    public void deleteAll() {
        productCategoryRepository.deleteAll();
    }

}
