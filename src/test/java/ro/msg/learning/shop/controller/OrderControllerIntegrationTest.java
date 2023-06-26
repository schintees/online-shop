package ro.msg.learning.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ro.msg.learning.shop.ShopApplication;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.dto.OrderDetailDTO;
import ro.msg.learning.shop.exception.OutOfStockException;
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.service.CustomerService;
import ro.msg.learning.shop.service.ProductService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ShopApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
@ActiveProfiles("test")
class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    private Customer customer;
    private List<Product> products;

    @BeforeEach
    void before() throws Exception {
        mvc.perform(delete("/test-database/clear").contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
        mvc.perform(post("/test-database/populate").contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());

        customer = customerService.getAll().get(0);
        products = productService.getAll();
    }

    @Test
    void createOrderSuccessfully() throws Exception {
        List<OrderDetailDTO> orderDetails = products.stream()
                .map(p -> OrderDetailDTO.builder().product(p.getId()).quantity(1).build())
                .collect(Collectors.toList());
        OrderDTO orderDTO = OrderDTO.builder()
                .customer(customer.getId())
                .createdAt(LocalDateTime.now())
                .address(Address.builder().country("Romania").city("Cluj-Napoca").county("Cluj").streetAddress("Florilor 2").build())
                .orderDetails(orderDetails)
                .build();

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        mvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderDTO)))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.shippedFrom").exists());

    }

    @Test
    void createOrderMissingStock() throws Exception {
        List<OrderDetailDTO> orderDetails = products.stream()
                .map(p -> OrderDetailDTO.builder().product(p.getId()).quantity(1000).build())
                .collect(Collectors.toList());
        OrderDTO orderDTO = OrderDTO.builder()
                .customer(customer.getId())
                .createdAt(LocalDateTime.now())
                .address(Address.builder().country("Romania").city("Cluj-Napoca").county("Cluj").streetAddress("Florilor 2").build())
                .orderDetails(orderDetails)
                .build();

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        mvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OutOfStockException));
    }

}