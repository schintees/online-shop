package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.*;

import java.math.BigDecimal;

@Profile("test")
@Service
public class PopulateTestDatabaseService {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductService productService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    public void populate() {

        // Product
        ProductCategory productCategory = ProductCategory.builder().name("sunglasses").description("unisex sunglasses").build();
        Supplier supplier = Supplier.builder().name("blenders").build();
        supplierService.create(supplier);

        Product product1 = Product.builder().name("aviator").description("category 5 lenses").price(BigDecimal.valueOf(350)).weight(10.7).category(productCategory).supplier(supplier).imageUrl("https://www.sgl.com/aviator").build();
        Product product2 = Product.builder().name("browline").description("category 2 lenses").price(BigDecimal.valueOf(700)).weight(29.0).category(productCategory).supplier(supplier).imageUrl("https://www.sgl.com/aviator").build();
        productService.create(product1);
        productService.create(product2);

        Location location1 = Location.builder().name("Cluj").address(Address.builder().country("Romania").city("Cluj-Napoca").county("Cluj").streetAddress("Frunzisului 20").build()).build();
        Location location2 = Location.builder().name("Timis").address(Address.builder().country("Romania").city("Timisoara").county("Timis").streetAddress("Parului 210A").build()).build();
        locationService.create(location1);
        locationService.create(location2);

        Customer customer = Customer.builder().firstName("Ian").lastName("Doe").username("iandoe").password("password").emailAddress("iandoe@gmail.com").build();
        customerService.create(customer);

        Stock stock1 = Stock.builder().product(product1).location(location1).quantity(11).build();
        Stock stock2 = Stock.builder().product(product1).location(location2).quantity(7).build();
        Stock stock3 = Stock.builder().product(product2).location(location1).quantity(3).build();
        Stock stock4 = Stock.builder().product(product2).location(location2).quantity(40).build();
        stockService.create(stock1);
        stockService.create(stock2);
        stockService.create(stock3);
        stockService.create(stock4);
    }

    public void clear() {
        productCategoryService.deleteAll();
        supplierService.deleteAll();
        productService.deleteAll();
        customerService.deleteAll();
        stockService.deleteAll();
        locationService.deleteAll();
        orderDetailService.deleteAll();
        orderService.deleteAll();
    }

}
