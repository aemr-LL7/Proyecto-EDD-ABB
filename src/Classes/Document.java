package Classes;

import DataStructureClasses.SimpleList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author B-St
 */
public class Document {

    private String name;
    private int numPages;
    private static final String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam ullamcorper sit amet neque ac vulputate. Praesent ut sodales risus. Suspendisse ultricies posuere enim ut aliquam. Duis pellentesque ut lacus sed porttitor. Morbi fringilla tempor nulla, id luctus sem fermentum id. Vestibulum mi dolor, volutpat vitae ante vulputate, mollis molestie dui. Quisque in luctus metus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Mauris vel rhoncus urna. Aenean maximus augue metus, in maximus est dictum non. Suspendisse potenti. Etiam pulvinar enim at erat elementum, quis vestibulum enim vulputate.\n";
    
    //Timestamp para la prioridad
    private double timeOfCreation;
    
    //Determina si se usara la prioridad del usuario a la hora de generar la prioridad de insercion en el heap.
    private boolean isPriority;
    
    //Apuntador al usuario que creo el documento
    private User creator;

    public Document(String name, User creator, boolean isPriority) {
        
        //Tiempo de la creacion del documento en milisegundos  
        this.timeOfCreation = (int) System.currentTimeMillis();
                
        this.name = name;
        
        //Genera un numero al azar de paginas
        int randomlyGeneratedPages = ThreadLocalRandom.current().nextInt(0, 20 + 1);
        this.numPages = randomlyGeneratedPages;
        
        //Creador del documento
        this.creator = creator;
        
        //Revisa si el documento esta marcado como prioritario
        this.isPriority = isPriority;
        
        //Anadir el documento a la lista de documentos del creador 
        SimpleList<Document> creatorDocuments = this.creator.getFiles_list();
        creatorDocuments.addAtTheEnd(this);
    }

//    public Document(String name, int numPages, User creator, boolean isPriority) {
//        this.name = name;
//        this.setNumPages(numPages);
//        this.creator = creator;
//        this.isPriority = isPriority;
//        
//        SimpleList<Document> creatorDocuments = this.creator.getFiles_list();
//        creatorDocuments.addAtTheEnd(this);
//    }
    
    public boolean hasPriorityOver(Document comparingDocument){
        
        User comparingUser = comparingDocument.getCreator();
        double comparingTime = comparingDocument.getTimeOfCreation();
        
        if (this.isPriority) {
            this.timeOfCreation *= this.creator.getPriorityModifier();
        }
        
        if (this.creator.hasPriorityOver(comparingUser) && this.timeOfCreation == comparingTime){
            return true;
        } else if (this.timeOfCreation > comparingTime){
            return true;
        } else {
            return false;
        }
        
    }
    
    public boolean equalInPriorityTo(Document comparingDocument){
        return this.timeOfCreation == comparingDocument.getTimeOfCreation();
    }
    
    public String toString(){
        
        String priority;
        
        if(this.isPriority){
            priority = "Verdadero.";
        } else {
            priority = "Falso.";
        }
        
        return "Nombre del documento: " + "\n-" + this.name +"\n\n"+ "Numero de paginas: \n-"+this.numPages+"\n\nContenido: \nLorem Ipsum.\n"+ "\nCreador: \n-"+ this.creator.getName()+"\n\nEs prioridad?\n-" + priority+"\n\n";
    }

    public User getCreator() {
        return creator;
    }
    
    public int getCreatorCI(){
        return this.creator.getDni();
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

    public double getTimeOfCreation() {
        return timeOfCreation;
    }

    public boolean isIsPriority() {
        return isPriority;
    }
    

}
