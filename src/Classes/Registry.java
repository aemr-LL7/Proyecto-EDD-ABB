package Classes;

/**
 *
 * @author B-St
 */
public class Registry {
    
    private double timestamp;
    private Document document;
    private boolean isPriority;

    public Registry(double timestamp, Document document, boolean isPriority) {
        this.timestamp = timestamp;
        this.document = document;
        this.isPriority = isPriority;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public boolean isIsPriority() {
        return isPriority;
    }

    public void setIsPriority(boolean isPriority) {
        this.isPriority = isPriority;
    }
    
}
