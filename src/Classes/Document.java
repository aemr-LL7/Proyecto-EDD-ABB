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
    private String content;
    
    //Apuntador al usuario que creo el documento
    private User creator;

    public Document(String name, String content, User creator) {

        //Nombre del documento
        this.name = name.toLowerCase();
        
        //Contenido del documento
        this.content = content;

        //Genera un numero al azar de paginas
        int randomlyGeneratedPages = ThreadLocalRandom.current().nextInt(0, 20 + 1);
        this.docSize = randomlyGeneratedPages;

        //Creador del documento
        this.creator = creator;

    }

    public String toString() {
        return "Nombre del documento: " + "\n-" + this.name + "\n\n" + "Numero de paginas: \n-" + this.docSize + "\n\nContenido: \nLorem Ipsum.\n" + "\nCreador: \n-" + this.creator.getName() + "\n\nEs prioridad?\n\n";
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
    
    public void setContent(String content) {
        this.content = content;
    }

}
