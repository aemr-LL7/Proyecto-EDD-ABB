package Classes;


/**
 *
 * @author B-St
 */
public class Utils<T> {

    public Utils() {
    }
    
    public boolean isDocument(T data) {
        return (data instanceof Document);
    }

    public boolean isUser(T data) {
        return (data instanceof UserAdministrator) || (data instanceof UserCommon) || (data instanceof UserHumanResources);
    }

}
