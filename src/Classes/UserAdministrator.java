/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import DataStructureClasses.SimpleList;
import DataStructureClasses.SimpleNode;

/**
 *
 * @author B-St
 */
public class UserAdministrator implements User {

    //Nombre
    private String name;

    //Cedula
    private int dni;

    //Lista en la que se guardan referencias a los objetos Document q creo el usuario
    private SimpleList<Document> files_list;

    private final int priority = 2;

    //modificador de prioridad para el tiempo
    private final double priorityModifier = 0.70;

    /*
       constructor de Usuario
     */
    public UserAdministrator(String name, int dni) {
        this.name = name;
        this.files_list = new SimpleList<Document>();
        this.dni = dni;
    }

    @Override
    public boolean isNameAvailable(String documentName) {

        SimpleNode<Document> pAux = this.files_list.getpFirst();

        while (pAux != null) {
            if (pAux.getData().getName().equals(documentName.toLowerCase())) {
                return false;
            }
            pAux = pAux.getpNext();
        }
        return true;
    }
    
    @Override
    public String toString(){
        return "Nombre del usuario: "+this.name+"\nCI del usuario: "+this.dni+"\nTipo de usuario: Administrador.\n";
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
