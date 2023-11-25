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
        // Usar StringBuilder para construir el formato personalizado
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Nombre: ").append(this.name).append(" - Creado por: ").append(this.creator);
        return stringBuilder.toString();
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
