package Main;

import Classes.Document;
import Classes.User;
import Classes.UserAdministrator;
import DataStructureClasses.OurHashTable;

/**
 *
 * @author andre
 */
public class Main {

    public static void main(String[] args) {
        /*
        TODO:
        
            -Crear priority heap
            -Comprender funcionamiento del timer (clases timer y timetask)
        
         */
        
        OurHashTable<Document> table = new OurHashTable<Document>();
        
        User creator = new UserAdministrator("Carlos", 11);
        
        Document document = new Document("Caracas", creator);
        Document document1 = new Document("Poz", creator);
        
        table.put(document.getName(), document); 
        table.put(document1.getName(), document1); 

        Document foundDocument = table.get("poz3");
        
        System.out.println("Usuario encontrado: \n\n" + foundDocument.toString());

    }
}
