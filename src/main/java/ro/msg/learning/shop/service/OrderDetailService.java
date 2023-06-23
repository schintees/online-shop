package ro.msg.learning.shop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.repository.OrderDetailRepository;

@Service
@Transactional
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public void deleteAll() {
        orderDetailRepository.deleteAll();
    }
}
