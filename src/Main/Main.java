/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Classes.User;
import DataStructureClasses.SimpleList;
import FileManager.FileManager;
import java.io.File;
import DataStructureClasses.HeapTree;

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

        // prueba lectura
        FileManager manager = new FileManager();
        File selectedFile = manager.selectFile();
        SimpleList<User> usersList = manager.readUsersFromCSV(selectedFile);

        usersList.printList();

        System.out.println("Usuario y Tipo:");
        for (int i = 0; i < usersList.getSize(); i++) {
            User users = usersList.getValueByIndex(i);
            System.out.println("[" + i + "] " + users.getName() + ", " + users.getPriority());
        }
        
        // prueba escritura 
        usersList.addAtTheEnd(new User("Mairo", "prioridad_alta"));
        usersList.addAtTheEnd(new User("Heros", "prioridad_media"));
        usersList.addAtTheEnd(new User("Andru", "prioridad_baja"));
        
        System.out.println("");
        manager.writeUsersToCSV(usersList);

        HeapTree minHeap = new HeapTree(7);
        minHeap.insert(30);
        minHeap.insert(48);
        minHeap.insert(15);
        minHeap.insert(67);
        minHeap.insert(24);
        minHeap.insert(17);
        minHeap.insert(5);
        
        minHeap.printTree();
        minHeap.showArray();
    
    }
}
