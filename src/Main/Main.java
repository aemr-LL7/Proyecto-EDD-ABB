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

        UserAdministrator user = new UserAdministrator("Eros", 23);
        UserAdministrator user1 = new UserAdministrator("Daniela", 24);
        UserAdministrator user2 = new UserAdministrator("Garfie", 25);

        OurHashTable table = new OurHashTable();

        Document document = new Document("Papaya", user, false);
        Document document1 = new Document("Pina", user1, true);
        Document document2 = new Document("Tomate", user2, false);

        table.put(document.getName(), document);
        table.put(document1.getName(), document1);
        table.put(document2.getName(), document2);
        
        String documentString = table.getDocument(document1.getName(), user1).toString();
        System.out.println("Documento encontrado! \n\n"+documentString);

    }
}
