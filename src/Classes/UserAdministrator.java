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
public class UserAdministrator implements User{

    //Nombre
    private String name;
    
    //Cedula
    private static int CI;
    
    //Lista en la que se guardan referencias a los objetos Document q creo el usuario
    private SimpleList files_list;
    
    private final int priority = 0;

    /*
       constructor de Usuario
     */
    public UserAdministrator(String name, int Ci){
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
    public int getCI(){
        return this.CI;
    }

    @Override
    public String getPriority() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

        
}
