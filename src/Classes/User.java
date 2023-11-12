/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import DataStructureClasses.SimpleList;

/**
 *
 * @author andre
 */
public class User {

    private String name;
    private String priority;
    private SimpleList files_list;

    /*
        Inicializar constructor
    */
    public User(String name, String priority) {
        this.name = name;
        this.priority = priority;
    }

    /*
        Sobrecarga de metodo Usuario
    */
    public User(String name, String priority, SimpleList<User> files_list) {
        this.name = name;
        this.priority = priority;
        this.files_list = files_list;
    }
    
    /*
        Getters y Setters
    */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public SimpleList getFiles_list() {
        return files_list;
    }

    public void setFiles_list(SimpleList files_list) {
        this.files_list = files_list;
    }
    
    
    
    
    
}
