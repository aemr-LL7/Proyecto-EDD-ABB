/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataStructureClasses;

/**
 *
 * @author andre
 */

/*
    Dudas sobre el arbol: tiene limite establecido de arcivos? Que hacer con los archivos sobrantes?
 */
public class HeapTree<T> {

    private HeapNode<T> pRoot;
    private int size;

    public HeapTree() {
        this.pRoot = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.pRoot == null;
    }

    /*
        RECORRIDOS DEL ARBOL
     */
    public void preorden(HeapNode<T> treeRoot) {
        if (treeRoot != null) {
            System.out.println(treeRoot.getData());
            this.preorden(treeRoot.getpLeft());
            this.preorden(treeRoot.getpRight());
        }
    }

    public void inorden(HeapNode<T> treeRoot) {
        if (treeRoot != null) {
            this.inorden(treeRoot.getpLeft());
            System.out.println(treeRoot.getData());
            this.inorden(treeRoot.getpRight());
        }
    }

    public void postorden(HeapNode<T> treeRoot) {
        if (treeRoot != null) {
            this.postorden(treeRoot.getpLeft());
            this.postorden(treeRoot.getpRight());
            System.out.println(treeRoot.getData());
        }
    }

    /*
        Añadir, eliminar, buscar
     */
    // null no se encuentra el padre, debe existir al menos un nodo en el arbol
    public HeapNode<T> searchFatherForInsertion(HeapNode<T> root, T data) {
        if (root.getData() == data) {
            return null;
        } else if ((Integer) root.getData() > (Integer) data) {
            return this.searchFatherForInsertion(root.getpLeft(), data);
        } else {
            return this.searchFatherForInsertion(root.getpRight(), data);
        }
    }

    public void add(T data) {
        HeapNode<T> newNode = new HeapNode<>(data);
        if (isEmpty()) {
            pRoot = newNode;
        } else {
            HeapNode<T> father = searchFatherForInsertion(pRoot, data);
            if ((Integer) father.getData() > (Integer) data) {
                father.setpLeft(newNode);
            } else {
                father.setpRight(newNode);
            }
            newNode.setpFather(father);
        }
        size++;
    }

    public void delete(T data) {

    }

    /*
        Getters y Setters
     */
    public HeapNode<T> getpRoot() {
        return pRoot;
    }

    public void setpRoot(HeapNode<T> pRoot) {
        this.pRoot = pRoot;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
