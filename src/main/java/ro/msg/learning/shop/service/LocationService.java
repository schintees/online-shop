package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.EntityNotFoundException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.repository.LocationRepository;

import java.util.UUID;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location get(UUID id) {
        return locationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Location.class, id));
    }

    public Location create(Location location) {
        return locationRepository.save(location);
    }

    public void deleteAll() {
        locationRepository.deleteAll();
    }

}
