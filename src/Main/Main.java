package Main;

import DataStructureClasses.SimpleList;
import FileManager.FileManager;
import java.io.File;


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
        FileManager filemanager = new FileManager();
        File fileselect = filemanager.selectFile();
        SimpleList testList = filemanager.readUsersFromCSV(fileselect);

        testList.printUsersList();

    }
}
