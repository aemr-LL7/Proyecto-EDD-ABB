/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataStructureClasses;

/**
 * @author andre
 * @param <T>
 */
public class SimpleNode<T>{
    
    private T data;
    private SimpleNode pNext;
    
    public SimpleNode(T data){
        
        this.data = data;
        this.pNext = null;
        
    }
    // Sobrecarga de metodos: Dos metodos con el mismo nombre pueden ser distintos si tienen argumentos diferentes
    public SimpleNode(T data, SimpleNode<T> pNext){
        
        this.data = data;
        this.pNext = pNext;
        
    }  
    
    // Insert code: Getters and Setters
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public SimpleNode getpNext() {
        return pNext;
    }

    public void setpNext(SimpleNode<T> pNext) {
        this.pNext = pNext;
    }
    
}
