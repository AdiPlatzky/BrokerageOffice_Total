package SystemPermissions;
import Property.Property;
import java.util.List;

/**
 * Interface representing entities that have permission to view properties.
 * This interface is part of the system's permission model, defining the
 * contract for classes (typically users) that can view property details.
 *
 * Classes implementing this interface should have the VIEW_PROPERTY permission
 * and are expected to provide functionality for retrieving property information.
 */
public interface Viewable
{
   /**
    * Retrieves a list of properties that this entity has permission to view.
    *
    * @return A list of properties
    */
   List<Property> viewProperties();
}