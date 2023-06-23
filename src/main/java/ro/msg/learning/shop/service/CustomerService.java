package ro.msg.learning.shop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.repository.CustomerRepository;

import java.util.List;

@Transactional
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }

}
