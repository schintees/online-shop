package ro.msg.learning.shop.mapper;

import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Supplier;

public class ProductMapper {

    private ProductMapper() {

    }

    public static ProductDTO toDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .weight(product.getWeight())
                .imageUrl(product.getImageUrl())
                .supplier(product.getSupplier().getId())
                .category(ProductCategoryMapper.toDto(product.getCategory()))
                .build();
    }

    public static Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .weight(productDTO.getWeight())
                .imageUrl(productDTO.getImageUrl())
                .supplier(Supplier.builder().id(productDTO.getSupplier()).build())
                .category(ProductCategoryMapper.toEntity(productDTO.getCategory()))
                .build();
    }
}
