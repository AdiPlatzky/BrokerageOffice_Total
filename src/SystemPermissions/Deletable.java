package SystemPermissions;
import Property.Property;

import java.util.List;

/**
 * Interface representing entities that have permission to delete properties.
 * This interface is part of the system's permission model, defining the
 * contract for classes (typically sellers) that can remove properties from the system.
 *
 * Classes implementing this interface should have the DELETE_PROPERTY permission
 * and are expected to provide functionality for removing properties from a collection.
 */
public interface Deletable
{
    /**
     * Deletes a property from a collection of properties.
     *
     * @param properties The collection of properties
     * @param property The property to delete
     */
    void deleteProperty(List<Property> properties, Property property);
}