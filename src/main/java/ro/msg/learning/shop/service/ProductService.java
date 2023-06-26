package ro.msg.learning.shop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.EntityNotFoundException;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryService productCategoryService;

    public Product get(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Product.class, id));
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product create(Product product) {
        product.setCategory(productCategoryService.createIfNotFound(product.getCategory()));
        return productRepository.save(product);
    }

    public Product update(UUID id, Product product) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setWeight(product.getWeight());
            existingProduct.setCategory(productCategoryService.createIfNotFound(product.getCategory()));
            existingProduct.setSupplier(product.getSupplier());
            existingProduct.setImageUrl(product.getImageUrl());
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new EntityNotFoundException(Product.class, id));
    }

    public void delete(UUID id) {
        productRepository.findById(id) //
                .orElseThrow(() -> new EntityNotFoundException(Product.class, id));
        productRepository.deleteById(id);
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }

}
