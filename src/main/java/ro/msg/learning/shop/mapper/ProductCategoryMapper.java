package ro.msg.learning.shop.mapper;

import ro.msg.learning.shop.dto.ProductCategoryDTO;
import ro.msg.learning.shop.model.ProductCategory;

public class ProductCategoryMapper {

    private ProductCategoryMapper() {

    }

    public static ProductCategoryDTO toDto(ProductCategory productCategory) {
        if (productCategory == null) {
            return null;
        }
        return ProductCategoryDTO.builder()
                .name(productCategory.getName())
                .description(productCategory.getDescription())
                .build();
    }

    public static ProductCategory toEntity(ProductCategoryDTO productCategoryDTO) {
        if (productCategoryDTO == null) {
            return null;
        }
        return ProductCategory.builder()
                .name(productCategoryDTO.getName())
                .description(productCategoryDTO.getDescription())
                .build();
    }
}
