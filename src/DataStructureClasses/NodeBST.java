/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataStructureClasses;

/**
 *
 * @author andre
 * @param <T>
 */
public class NodeBST<T> {

    private T data;
    private NodeBST<T> pLeft, pRight, pFather;
    // puede tener un apuntador al nodo padre

    /*
        Inicializar constructor
     */
    public NodeBST(T data) {
        this.data = data;
        this.pLeft = null;
        this.pRight = null;
    }

    /*
        Constructor con apuntador al padre (raiz)
     */
    public NodeBST(T data, NodeBST<T> pFather) {
        this.data = data;
        this.pLeft = null;
        this.pRight = null;
        this.pFather = pFather;
    }

    /*
        Getters y Setters
     */
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public NodeBST<T> getpLeft() {
        return pLeft;
    }

    public void setpLeft(NodeBST<T> pLeft) {
        this.pLeft = pLeft;
    }

    public NodeBST<T> getpRight() {
        return pRight;
    }

    public void setpRight(NodeBST<T> pRight) {
        this.pRight = pRight;
    }

    public NodeBST<T> getpFather() {
        return pFather;
    }

    public void setpFather(NodeBST<T> pFather) {
        this.pFather = pFather;
    }

}
