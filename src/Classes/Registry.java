package Classes;

import java.util.Objects;

/**
 *
 * @author B-St
 */
public class Registry {

    //Hacer timestamp con el tiempo de ejecucion
    private long timestamp;
    private Document document;
    private boolean isPriority;

    // CONSTTRUCTOR 
    public Registry(int timestamp, Document document, boolean isPriority) {
        this.timestamp = timestamp;
        this.document = document;
        this.isPriority = isPriority;

    }

    //metodo usado para determinar cual tiene mayor prioridad. Los nulos no se consideran menores para propositos de ordenamiento del arbol
    public boolean isTimeLowerThan(Registry comparingRegistry) {

        if (comparingRegistry == null) {
            return true;
        } else if (this.timestamp == comparingRegistry.getTimestamp() && this.userHasPriorityOver(comparingRegistry)) {
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

    // En la clase Registry
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Registry registry = (Registry) obj;
        return Double.compare(registry.timestamp, timestamp) == 0
                && isPriority == registry.isPriority
                && Objects.equals(document, registry.document);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, document, isPriority);
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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
