package ro.msg.learning.shop.service.helper;

import ro.msg.learning.shop.model.Address;

import java.math.BigDecimal;
import java.util.List;

public interface DistanceHelper {

    List<BigDecimal> getDistancesForLocation(Address fromAddress, List<Address> targetAddresses);
}
