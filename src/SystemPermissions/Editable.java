package SystemPermissions;
import Property.Property;
import Property.PropertyUpdateRequest;

/**
 * Interface representing entities that have permission to edit properties.
 * This interface is part of the system's permission model, defining the
 * contract for classes (typically brokers) that can modify property details.
 *
 * Classes implementing this interface should have the EDIT_PROPERTY permission
 * and are expected to provide functionality for updating property information.
 */
public interface Editable
{
    /**
     * Updates a property with the information contained in the update request.
     * Only fields that are set in the update request will be modified.
     *
     * @param property The property to edit
     * @param updateRequest The update request containing the new values
     */
    void editProperty(Property property, PropertyUpdateRequest updateRequest);
}