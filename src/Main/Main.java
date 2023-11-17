package Main;

import DataStructureClasses.OurHashTable;
import DataStructureClasses.SimpleList;

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

        OurHashTable testTable = new OurHashTable();

        testTable.put("uno", 1);
        testTable.put("tres", 3);
        testTable.put("dos", 2);

        try {
            int returning = (int) testTable.get("ddd");
            System.out.println("Numero recuperado: " + returning);
        } catch (Exception e) {
            System.out.println("Numero no encontrado.");
        }

    }
}
