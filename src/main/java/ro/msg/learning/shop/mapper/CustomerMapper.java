package ro.msg.learning.shop.mapper;

import ro.msg.learning.shop.dto.CustomerDTO;
import ro.msg.learning.shop.model.Customer;

public class CustomerMapper {

    private CustomerMapper() {

    }

    public static CustomerDTO toDto(Customer customer) {
        return CustomerDTO.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .username(customer.getUsername())
                .emailAddress(customer.getEmailAddress())
                .build();
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        return Customer.builder()
                .firstName(customerDTO.getFirstName())
                .lastName(customerDTO.getLastName())
                .username(customerDTO.getUsername())
                .password(customerDTO.getPassword())
                .emailAddress(customerDTO.getEmailAddress())
                .build();
    }
}
