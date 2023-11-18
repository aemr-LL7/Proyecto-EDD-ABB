package DataStructureClasses;

import Classes.Document;

/**
 *
 * @author andre
 * @param <T>
 */

/*
    Dudas sobre el arbol: tiene limite establecido de arcivos? Que hacer con los archivos sobrantes?
 */
public class HeapTree {

    private int[] heap;
    private int size;//Cantidad de elementos del Heap

    //Constructor para crear Heap vacio
    public HeapTree(int maxSize) {
        this.size = 0;
        this.heap = new int[maxSize];
    }

    //Constructor para crear Heap a partir de un arreglo de numeros
//    public HeapTree(int[] array) {
//        this.size = array.length;
//        //Se recibe el arreglo y se asigna al heap
//        this.heap = array;
//
//        //Se construye el heap
//        this.buildHeap();
//    }

    public void buildHeap() {
        for (int i = this.size / 2; i >= 0; i--) {
            this.Heapify(i, this.size - 1);
        }
    }

    private void Heapify(int i, int j) {
        //Comprueba que el hijo izq de la posicion i sea un valor menor o igual a j
        if ((this.leftChild(i)) <= j) {
            //k almacena la posicion del valor que tiene que subir
            int k;
            //Si el hijo derecho de i es menor q j significa que tiene que elegir el mayor de los dos hijos
            if ((this.rightChild(i)) <= j) {
                //Se elige la pos que tiene el valor mayor
                if (this.heap[this.rightChild(i)] <= this.heap[this.leftChild(i)]) {
                    k = this.rightChild(i);
                } else {
                    k = this.leftChild(i);
                }
            } else {//Significa que solo tiene hijo izquierdo
                k = this.leftChild(i);
            }
            //Revisa si es mayor, se hace el intercambio para armar nuevamente el min-heap
            if (heap[i] > heap[k]) {
                this.swapValues(i, k);
                this.Heapify(k, j);
            }
        }
    }

    //Retorna la cantidad de elementos que tiene el Min-Heap
    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    

    public void insert(int dato) {
        if (this.size == this.heap.length) {
            System.out.println("Min-Heap está lleno!");
        } else {
            int posActual = this.size;
            //Se inserta el elemento en el arreglo
            this.heap[posActual] = dato;
            //Incrementa el contador
            this.size++;

            // mientras que el hijo tenga un valor menor a su padre se intercambian
            while (this.heap[posActual] < this.heap[this.parent(posActual)]) {
                this.swapValues(posActual, parent(posActual));
                posActual = this.parent(posActual);
            }
        }
    }

    //Cambia el valor de la posicion i del arreglo, por la de j
    private void swapValues(int i, int j) {
        int temp = this.heap[i];
        this.heap[i] = this.heap[j];
        this.heap[j] = temp;
    }

    //Dada la posicion de un nodo, devuelve la posicion de su nodo padre
    private int parent(int position) {
        return ((position - 1) / 2);
    }

    //Dada la posicion de un nodo, devuelve la posicion de su hijo izquierdo
    private int leftChild(int position) {
        return ((2 * position) + 1);
    }

    //Dada la posicion de un nodo, devuelve la posicion de su hijo derecho
    private int rightChild(int position) {
        return ((2 * position) + 2);
    }

    //Desarma el heap para dejar el arreglo ordenado de mayor a menor
    public void heapSort() {
        // comienza desde la ultima posicion del arreglo
        for (int i = this.heap.length - 1; i > 0; i--) {
            this.swapValues(0, i);
            this.Heapify(0, i - 1);
        }
    }

    //Retorna el menor valor del Min-Heap asumiendo que existe
    public int getMin() {
        return this.heap[0];
    }

    //Extrae el menor elemento del Min-Heap y lo retorna
    public int extractMin() {
        if (this.heap.length == 0) {
            System.out.println("MinHeap se encuentra vacio!");
        } else if (this.heap.length == 1) {
            int minimum = this.heap[0];
            this.heap[0] = 0;//Se borra el elemento con un cero
            this.size--;//Se decrementa el tamanyo del heap
            return minimum;
        }
        //Se guarda el minimo en una variable
        int min = this.heap[0];
        this.heap[0] = this.heap[this.size - 1];//Se intercambia el ultimo por el primer elemento
        this.heap[this.size - 1] = 0;//Se borra el ultimo elemento del heap con un cero

        //Se decrementa el tamaño del heap
        this.size--;

        //Se rearma el heap
        this.Heapify(0, this.size - 1);

        // return min key
        return min;
    }

    // POR NIVELES
    public void showArray() {
        System.out.println("--Inicio--");
        for (int i = 0; i < this.heap.length; i++) {
            System.out.print(this.heap[i] + "|");
        }
        System.out.println();
        System.out.println("--Fin--");
    }
    
    // La función Math. ceil() devuelve el entero mayor o igual más próximo a un número dado
    public void printTree() {
        for (int i = 0; i < Math.ceil(this.size / 2); i++) {
            try {
                System.out.print(" PADRE : " + this.heap[i]);
            } catch (Exception e) {
                System.out.println("");
            }

            try {
                System.out.print(" HIJO IZQUIERDO: " + this.heap[this.leftChild(i)]);
            } catch (Exception e) {
                System.out.println("");
            }

            try {
                System.out.print(" HIJO DERECHO:" + this.heap[this.rightChild(i)]);
            } catch (Exception e) {
                System.out.println("");
            }
            System.out.println();
        }
    }
}
