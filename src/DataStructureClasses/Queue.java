/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataStructureClasses;

import java.util.Comparator;

/**
 *
 * @author andre
 * @param <T>
 */
public class Queue<T> {

    private SimpleNode<T> pHead;
    private SimpleNode<T> pTail;
    private int size;

    /*
        Inicializar constructor
     */
    public Queue() {
        this.pHead = null;
        this.pTail = null;
        this.size = 0;
    }

    /*
        Comprueba si la Cola está vacia, devuelve null si esto se cumple
     */
    public boolean isQueueEmpty() {
        return this.pHead == null;
    }

    // ENCOLAR
    public void Enqueue(T value) {
        SimpleNode<T> nodo = new SimpleNode<T>(value);

        if (this.isQueueEmpty()) {
            this.pHead = nodo;
            this.pTail = nodo;
        } else {
            // si se inserta un nuevo elemento (teniendo elementos dentro) la cola apuntara al nuevo nodo
            this.pTail.setpNext(nodo);
            this.pTail = nodo;
        }
        this.size++;
    }

    // DESENCOLAR (SIEMPRE AL INICIO HEAD)
    public SimpleNode<T> Dequeue() {
        SimpleNode<T> nodo = this.pHead;
        if (!this.isQueueEmpty()) {
            // Tenemos un nodo que apunta al inicio, y la cabeza será su siguiente elemento
            // de esta forma pierde conexion o referencia con el anterior y se elimina
            this.pHead = this.pHead.getpNext();
            this.size--;
            return nodo;
        }
        return null;
    }

    // nuevos metodos: imprimir, buscar, insertar final, eliminar al final, eliminar por posicion, invertir
    public void printQueue() {
        for (int i = 0; i < this.size; i++) {
            T data = this.pHead.getData();
            System.out.println(data);
            this.Dequeue();
            this.Enqueue(data);
        }
    }

    public String printToString() {
        String output = "";
        for (int i = 0; i < this.size; i++) {
            T data = this.pHead.getData();
            output += data + "->";
            this.Dequeue();
            this.Enqueue(data);
        }
        return output + "//";
    }

    public void reverse() {
        if (!this.isQueueEmpty()) {
            T data = this.pHead.getData();
            this.Dequeue();
            // RECURSIVO
            this.reverse();
            this.Enqueue(data);
        }
    }

    public void enqueueAtFirst(T value) {
        SimpleNode<T> nodo = new SimpleNode<T>(value);
        if (this.isQueueEmpty()) {
            this.pHead = nodo;
            this.pTail = nodo;
            this.size++;
        } else {
            // el metodo encolar incrementa el tamaño en 1
            this.Enqueue(value);
            for (int i = 0; i < (this.size - 1); i++) {
                T auxData = this.pHead.getData();
                this.Dequeue();
                this.Enqueue(auxData);
            }

        }
        //this.size ++;
    }

    // el valor existira dentro de la cola?
    public boolean searchData(T value) {

        boolean found = false;

        for (int i = 0; i < this.size; i++) {
            T data = this.pHead.getData();
            if (value == data) {
                found = true;
            }
            //recorrer todos los elementos de la cola
            this.Dequeue();
            this.Enqueue(data);
        }
        return found;
    }

    private int compare(T a, T b) {
        // Si a es menor que b, devuelve un valor negativo
        if ((Integer) a < (Integer) b) {
            return -1;
        } // Si a es mayor que b, devuelve un valor positivo
        else if ((Integer) a > (Integer) b) {
            return 1;
        } // Si a es igual a b, devuelve 0
        else {
            return 0;
        }
    }

    // metodo buscar el mayor
    private T getHigher() {
        T higher = pHead.getData();
        for (int i = 0; i < size; i++) {
            T data = pHead.getData();
            if (compare(data, higher) > 0) {
                higher = data;
            }
            Dequeue();
            Enqueue(data);
        }
        return higher;
    }

    private T getLower() {
        T lower = pHead.getData();
        for (int i = 0; i < size; i++) {
            T data = pHead.getData();
            if (compare(data, lower) < 0) {
                lower = data;
            }
            Dequeue();
            Enqueue(data);
        }
        return lower;
    }

    public void orderAscending() {
        order(true);
    }

    public void orderDescending() {
        order(false);
    }

    private void order(boolean ascending) {
        if (!isQueueEmpty()) {
            T value = null;
            if (ascending) {
                value = getLower();
            } else {
                value = getHigher();
            }
            Dequeue();
            order(ascending);
            Enqueue(value);
        }
    }

    /*
        Getters y Setters
     */
    public SimpleNode<T> getpHead() {
        return pHead;
    }

    public void setpHead(SimpleNode<T> pHead) {
        this.pHead = pHead;
    }

    public SimpleNode<T> getpTail() {
        return pTail;
    }

    public void setpTail(SimpleNode<T> pTail) {
        this.pTail = pTail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
