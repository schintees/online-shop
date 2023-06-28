package ro.msg.learning.shop.service.helper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ro.msg.learning.shop.dto.MapQuestRequestDTO;
import ro.msg.learning.shop.dto.MapQuestResponseDTO;
import ro.msg.learning.shop.exception.MapQuestException;
import ro.msg.learning.shop.model.Address;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Component
public class MapQuestDistanceHelper implements DistanceHelper {

    @Value("${mapquest.url}")
    private String mapQuestUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<BigDecimal> getDistancesForLocation(Address fromAddress, List<Address> targetAddresses) {

        List<Address> requestAddresses = Stream.concat(Stream.of(fromAddress), targetAddresses.stream()).toList();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<MapQuestRequestDTO> entity = new HttpEntity<>(MapQuestRequestDTO.builder().locations(requestAddresses).build(), headers);

        var response = restTemplate.postForEntity(mapQuestUrl, entity, MapQuestResponseDTO.class);
        if (response.getBody() == null || response.getBody().getInfo() == null || response.getBody().getDistance() == null) {
            throw new MapQuestException("Failed to get response from MapQuest");
        }

        var responseBody = response.getBody();
        if (responseBody.getInfo().getStatusCode() != 0) {
            throw new MapQuestException(StringUtils.join(responseBody.getInfo().getMessages(), '.'));
        }

        if (requestAddresses.size() != responseBody.getDistance().size()) {
            throw new MapQuestException("Failed to get the distance for all the locations");
        }

        return responseBody.getDistance();
    }

}
