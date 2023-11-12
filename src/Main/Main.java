/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DataStructureClasses.Queue;
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
        
        */
        
        Queue<Integer> colita = new Queue();
        colita.Enqueue(9);
        colita.Enqueue(11);
        colita.Enqueue(2);
        colita.Enqueue(5);
        colita.Enqueue(6);
        
        System.out.println("Cola antes de orndenar:");
        System.out.println(colita.printToString());
        
        System.out.println("\nCola ASCENDENTE:");
        colita.orderAscending();
        System.out.println(colita.printToString());
        
        System.out.println("\nCola DESCENDENTE:");
        colita.orderDescending();
        System.out.println(colita.printToString());
        
        FileManager fileManager = new FileManager();
        File file = fileManager.selectFile();
        fileManager.printCSV(file);
    }
}
