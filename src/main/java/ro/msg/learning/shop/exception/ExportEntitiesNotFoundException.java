package ro.msg.learning.shop.exception;

import ro.msg.learning.shop.model.EntityWithUUID;

public class ExportEntitiesNotFoundException extends RuntimeException {

    public ExportEntitiesNotFoundException(Class<? extends EntityWithUUID> entityWithUUIDClass, String classifier) {
        super("Could not find any " + entityWithUUIDClass.getSimpleName() + " to export for " + classifier);
    }

}