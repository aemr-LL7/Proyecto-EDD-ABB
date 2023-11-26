package Classes;

/**
 *
 * @author B-St
 */
public class Registry {

    //Hacer timestamp con el tiempo de ejecucion
    private double timesTamp;
    private Document document;
    private boolean isPriority;

    // CONSTTRUCTOR 
    public Registry(int timestamp, Document document, boolean isPriority) {
        this.timesTamp = timestamp;
        this.document = document;
        this.isPriority = isPriority;

    }

    //metodo usado para determinar cual tiene mayor prioridad. Los nulos no se consideran menores para propositos de ordenamiento del arbol
    public boolean isTimeLowerThan(Registry comparingRegistry) {

        if (this == null) {
            return false;
        } else if (comparingRegistry == null) {
            return true;
        } else if (this.timesTamp == comparingRegistry.getTimestamp() && this.userHasPriorityOver(comparingRegistry)) {
            return true;
        } else if (this.timesTamp < comparingRegistry.getTimestamp()) {
            return true;
        } else {
            return false;
        }

    }

    private boolean userHasPriorityOver(Registry comparingRegistry) {
        return this.document.getCreator().getPriority() > comparingRegistry.getDocument().getCreator().getPriority();
    }

    public String toString() {

        String prio;
        if (this.isPriority) {
            prio = " Si";
        } else {
            prio = " No";
        }

        return "Timestamp: " + this.timesTamp + " Nombre del documento: " + this.document.getName() + " Prioridad? -> " + prio + ".";

    }

    public double getTimestamp() {
        return timesTamp;
    }

    public void setTimestamp(double timestamp) {
        this.timesTamp = timestamp;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public boolean isPriority() {
        return isPriority;
    }

    public void setIsPriority(boolean isPriority) {
        this.isPriority = isPriority;
    }

}
