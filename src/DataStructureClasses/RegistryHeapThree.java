package DataStructureClasses;

import Classes.Registry;

/**
 *
 * @author B-St
 *
 * Debugear con respecto al registry
 */
public class RegistryHeapThree {

    private Registry[] heap;
    private int size; //Cantidad de elementos del Heap
    private static final int MAX_SIZE = 128;

    //Constructor para crear Heap vacio
    public RegistryHeapThree() {
        this.heap = new Registry[this.MAX_SIZE];
        this.size = -1;
    }

    public int getSize() {
        return size;
    }

    public Registry[] getHeapArray() {
        return this.heap;
    }

    public boolean isEmpty() {
        return size == -1;
    }

    //funcion que retorna el padre del registro en la posicion dada
    private int parent(int i) {
        return (i - 1) / 2;
    }

    //Retorna el hijo izquierdo de la posicion dada
    private int leftChild(int i) {
        return ((2 * i) + 1);
    }

    //Retorna el hijo derecho de la posicion dada
    private int rightChild(int i) {
        return ((2 * i) + 2);
    }

    //comprueba si el elemento en la posicion es una hoja
    private boolean isLeaf(int i) {

        if (i > (this.size / 2)) {
            return true;
        }
        return false;
    }

    //Intercambiar los elementos de dos posiciones dadas en el heap
    private void swap(int i, int j) {

        Registry aux = this.heap[i];

        this.heap[j] = this.heap[i];
        this.heap[i] = aux;
    }

    //heapificar???? el elemento en la posicion i
    private void minHeapify(int pos) {
        if (!isLeaf(pos)) {
            int swapPos = pos;
            // swap with the minimum of the two children
            // to check if right child exists. Otherwise default value will be '0'
            // and that will be swapped with the parent node.
            if (rightChild(pos) <= size) {
                if (heap[leftChild(pos)].isTimeLowerThan(heap[rightChild(pos)])) {
                    swapPos = leftChild(pos);
                } else {
                    swapPos = rightChild(pos);
                }
            } else {
                swapPos = leftChild(pos);
            }

            if (heap[pos].isTimeLowerThan(heap[leftChild(pos)]) || heap[pos].isTimeLowerThan(heap[rightChild(pos)])) {
                swap(pos, swapPos);
                minHeapify(swapPos);
            }
        }
    }

    //Insertar 
    public void insert(Registry registry) {

        if (this.size >= this.MAX_SIZE) {
            return;
        }

        
        this.heap[this.size+1] = registry;
        this.size++;
        int current = this.size; 

        while ((this.heap[current].isTimeLowerThan(this.heap[parent(current)]))) {
            swap(current, parent(current));
            current = parent(current);
        }

    }

    //Eliminar y retornar el inimo
    public Registry remove() {

        //Devolver el elemento en la cima
        Registry popped = this.heap[0];

        //Cambiar el front por la hoja mas lejana
        this.heap[0] = this.heap[size];
        minHeapify(0);
        this.size--;

        return popped;
    }

}
