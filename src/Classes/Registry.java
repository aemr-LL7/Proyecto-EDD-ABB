package Classes;

/**
 *
 * @author B-St
 */
public class Registry {

    //Hacer timestamp con el tiempo de ejecucion
    private double timestamp;
    private Document document;
    private boolean isPriority;

    public Registry(Document document, boolean isPriority) {
        
        long creationTime = System.currentTimeMillis();
        double priorityModifier = document.getCreator().getPriorityModifier();

        this.document = document;
        this.isPriority = isPriority;

        if (this.isPriority) {
            this.timestamp = (long) (creationTime * priorityModifier);
        } else {
            this.timestamp = creationTime;
        }
    }
    
    // CONSTTRUCTOR DEBUG

    public Registry(int timestamp , Document document, boolean isPriority) {
        this.timestamp = timestamp;
        this.document = document;
        this.isPriority = isPriority;
    }
    

    //metodo usado para determinar cual tiene mayor prioridad
    public boolean isTimeLowerThan(Registry comparingRegistry) {

        if (this.timestamp == comparingRegistry.getTimestamp() && this.userHasPriorityOver(comparingRegistry)) {
            return true;
        } else if (this.timestamp < comparingRegistry.getTimestamp()) {
            return true;
        } else {
            return false;
        }

    }

    private boolean userHasPriorityOver(Registry comparingRegistry) {
        return this.document.getCreator().getPriority() > comparingRegistry.getDocument().getCreator().getPriority();
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
