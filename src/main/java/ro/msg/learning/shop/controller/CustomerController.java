package ro.msg.learning.shop.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.CustomerDTO;
import ro.msg.learning.shop.mapper.CustomerMapper;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.service.CustomerService;

@RestController
@RequestMapping(path = "/customers",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> create(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerService.create(CustomerMapper.toEntity(customerDTO));

        return ResponseEntity.ok(CustomerMapper.toDto(customer));
    }
}
