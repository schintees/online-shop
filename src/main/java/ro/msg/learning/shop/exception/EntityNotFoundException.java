package ro.msg.learning.shop.exception;

import ro.msg.learning.shop.model.EntityWithUUID;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<? extends EntityWithUUID> entityWithUUIDClass, UUID id) {
        super("Could not find " + entityWithUUIDClass.getSimpleName() + " with id " + id);
    }
}
