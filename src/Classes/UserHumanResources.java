/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import DataStructureClasses.SimpleList;

/**
 *
 * @author B-St
 */
public class UserHumanResources implements User {

    //Nombre
    private String name;

    //Cedula
    private int dni;

    //Lista en la que se guardan referencias a los objetos Document q creo el usuario
    private SimpleList<Document> files_list;

    private final int priority = 1;
    
    //modificador de prioridad para el tiempo
    private final double priorityModifier = 1.00;

    /*
       constructor de Usuario
     */
    public UserHumanResources(String name, int dni) {
        this.name = name;
        this.files_list = new SimpleList<Document>();
        this.dni = dni;
    }

    /*
        Getters y Setters
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public SimpleList getFiles_list() {
        return files_list;
    }

    @Override
    public void setFiles_list(SimpleList files_list) {
        this.files_list = files_list;
    }

    @Override
    public int getDni() {
        return this.dni;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public boolean hasPriorityOver(User comparingUser) {
        return this.priority > comparingUser.getPriority();
    }
    
    @Override
    public double getPriorityModifier() {
        return this.priorityModifier;
    }

}
