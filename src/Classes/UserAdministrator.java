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
public class UserAdministrator implements User {

    //Nombre
    private String name;

    //Cedula
    private int CI;

    //Lista en la que se guardan referencias a los objetos Document q creo el usuario
    private SimpleList<Document> files_list;

    private final int priority = 2;

    //modificador de prioridad para el tiempo
    private final double priorityModifier = 1.30;

    /*
       constructor de Usuario
     */
    public UserAdministrator(String name, int CI) {
        this.name = name;
        this.files_list = new SimpleList<Document>();
        this.CI = CI;
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
    public int getCI() {
        return this.CI;
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
