package Main;

import Classes.Document;
import Classes.Registry;
import Classes.UserCommon;
import DataStructureClasses.RegistryHeapTree;

/**
 *
 * @author andre
 */
public class Main {

    public static void main(String[] args) {
        /*
        TODO:
        
            -Interfaz
        
         */

//        UserCommon user1 = new UserCommon("Danny", 29986549);
//        UserCommon user2 = new UserCommon("Herosland", 29986549);
//        
//        Document doc1 = new Document("Pajarito", user1);
//        Document doc2 = new Document("Amarillo los platano", user1);
//        Document doc3 = new Document("La odisea", user2);
//        Document doc4 = new Document("Las desventuras de eduardo", user2);
//        Document doc5 = new Document("Aminoacidos traviesos", user2);
//        
//        Registry reg1 = new Registry(1,doc1,true);
//        Registry reg2 = new Registry(2,doc2,false);
//        Registry reg3 = new Registry(3,doc3,false);
//        Registry reg4 = new Registry(4,doc4,false);
//        Registry reg5 = new Registry(4,doc5,false);
//        
//        RegistryHeapTree manito = new RegistryHeapTree();
//        manito.insert(reg1);
//        manito.insert(reg2);
//        manito.insert(reg3);
//        manito.insert(reg4);
//        //manito.insert(reg5);
//        
//        manito.printTree();
//        System.out.println("");
//        
//        System.out.println("Array brah");
//        System.out.println(manito.arrayToString());
        long startTime = System.currentTimeMillis() / 60000;
        
        for(int i =0; i<10000 ; i++){
            ;
        }
        
        long endingTime = System.currentTimeMillis();
        long executionTime = (endingTime - startTime) / 60000;
        
        
        System.out.println("Tiempo de ejecucion: " + executionTime);
        
    }
}
