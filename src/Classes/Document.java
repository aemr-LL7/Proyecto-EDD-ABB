package Classes;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author B-St
 */
public class Document {

    private String name;
    private int numPages;
    private static final String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam ullamcorper sit amet neque ac vulputate. Praesent ut sodales risus. Suspendisse ultricies posuere enim ut aliquam. Duis pellentesque ut lacus sed porttitor. Morbi fringilla tempor nulla, id luctus sem fermentum id. Vestibulum mi dolor, volutpat vitae ante vulputate, mollis molestie dui. Quisque in luctus metus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Mauris vel rhoncus urna. Aenean maximus augue metus, in maximus est dictum non. Suspendisse potenti. Etiam pulvinar enim at erat elementum, quis vestibulum enim vulputate.\n";
    
    //Determina si se usara la prioridad del usuario a la hora de generar la prioridad de insercion en el heap.
    private boolean isPriority;
    
    //Apuntador al usuario que creo el documento
    private User creator;

    public Document(String name, User creator, boolean isPriority) {
        this.name = name;
        int randomGeneratedPages = ThreadLocalRandom.current().nextInt(0, 20 + 1);
        this.numPages = randomGeneratedPages;
        this.isPriority = false;
        this.creator = creator;
        this.isPriority = isPriority;
    }

    public Document(String name, int numPages, User creator, boolean isPriority) {
        this.name = name;
        this.setNumPages(numPages);
        this.isPriority = false;
        this.creator = creator;
        this.isPriority = isPriority;
    }

    public int getCreatorCI(){
        return this.creator.getCI();
    }
    
    public void setPriorit(boolean priority) {
        this.isPriority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public String getContent() {
        return content;
    }

}
