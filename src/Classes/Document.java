package Classes;

import DataStructureClasses.SimpleList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author B-St
 */
public class Document {

    private String name;
    private int docSize;
    private static final String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam ullamcorper sit amet neque ac vulputate. Praesent ut sodales risus. Suspendisse ultricies posuere enim ut aliquam. Duis pellentesque ut lacus sed porttitor. Morbi fringilla tempor nulla, id luctus sem fermentum id. Vestibulum mi dolor, volutpat vitae ante vulputate, mollis molestie dui. Quisque in luctus metus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Mauris vel rhoncus urna. Aenean maximus augue metus, in maximus est dictum non. Suspendisse potenti. Etiam pulvinar enim at erat elementum, quis vestibulum enim vulputate.\n";


    //Apuntador al usuario que creo el documento
    private User creator;

    public Document(String name, User creator) {

        //Nombre del documento
        this.name = name.toLowerCase();

        //Genera un numero al azar de paginas
        int randomlyGeneratedPages = ThreadLocalRandom.current().nextInt(0, 20 + 1);
        this.docSize = randomlyGeneratedPages;

        //Creador del documento
        this.creator = creator;

        //Anadir el documento a la lista de documentos del creador 
        SimpleList<Document> creatorDocuments = this.creator.getFiles_list();
        creatorDocuments.addAtTheEnd(this);
    }

    public String toString() {
        return "Nombre del documento: " + "\n-" + this.name + "\n\n" + "Numero de paginas: \n-" + this.docSize + "\n\nContenido: \nLorem Ipsum.\n" + "\nCreador: \n-" + this.creator.getName() + "\n\nEs prioridad?\n\n";
    }

    //Esta fnucion hay que cambiarla de lugar y funcionalidad
    public boolean hasPriorityOver(Document document){
        return false;
    }
    
    public User getCreator() {
        return creator;
    }

    public int getCreatorCI() {
        return this.creator.getDni();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumPages() {
        return docSize;
    }

    public void setNumPages(int numPages) {
        this.docSize = numPages;
    }

    public String getContent() {
        return content;
    }

}
