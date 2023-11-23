package DataStructureClasses;

import Classes.Registry;

/**
 *
 * @author B-St
 *
 * Hola como estas
 */
public class RegistryHeapTree {

    private Registry[] heap;
    private int heapSize; //Cantidad de elementos del Heap
    private static final int MAX_SIZE = 128;

    //Constructor para crear Heap vacio
    public RegistryHeapTree() {
        this.heap = new Registry[this.MAX_SIZE];
        this.heapSize = -1;
    }

    //Conseguir el tamano del heap
    public int getHeapSize() {
        return heapSize;
    }

    //conseguir el heap
    public Registry[] getHeapArray() {
        return this.heap;
    }

    //revisar si el heap esta vacio
    public boolean isEmpty() {
        return heapSize == -1 || this.heap[0] == null;
    }

    //ver si el heap esta lleno revisando la ultima posicion
    public boolean isFull() {
        return (this.heap[MAX_SIZE - 1] != null);
    }

    //funcion que retorna el padre del registro en la posicion dada
    private int parent(int i) {
        return ((i - 1) / 2);
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

        if (i > (this.heapSize / 2)) {
            return true;
        }
        return false;
    }

    //Intercambiar los elementos de dos posiciones dadas en el heap.
    //se hace el cambio izquiera por derecha, en este caso heap[i] -> heap[j].
    private void swap(int i, int j) {

        Registry aux = this.heap[i];

        this.heap[i] = this.heap[j];
        this.heap[j] = aux;
    }

    //Bajar los elementos que sean mayores y subir el menor de sus hijos
    private void minHeapify(int pos) {
        if (!isLeaf(pos)) {
            int swapPos = pos;

            /*
            Pone en la izquierda el hijo menor de los dos. 
            Si el hijo derecho no existe, no se cambia la posicion.
            Luego se revisa si el hijo izquierdo es menor que el padre, si el hijo izquierdo es menor, se sube.
             */
            if (this.rightChildIsNull(pos)) {
                
                swapPos = this.leftChild(pos);
                
            } else if (this.leftChildIsNull(pos)) {
                
                //Si el hijo izquierdo es null, se cambia por el derecho 
                this.swap(this.leftChild(pos), this.rightChild(pos));
                swapPos = this.rightChild(pos);
                
            } else if (this.heap[this.rightChild(pos)].isTimeLowerThan(this.heap[this.leftChild(pos)])) {
                this.swap(this.leftChild(pos), this.rightChild(pos));
                swapPos = this.leftChild(pos);
            }

            //Se revisa el hijo derecho tambien para reducir errores en el codigo
            if (!(this.heap[pos].isTimeLowerThan(this.heap[leftChild(pos)])) || !(this.heap[pos].isTimeLowerThan(this.heap[rightChild(pos)]))) {
                swap(pos, swapPos);
                minHeapify(swapPos);
            }
        }
    }

    private boolean rightChildIsNull(int pos) {
        return this.heap[this.rightChild(pos)] == null;
    }

    private boolean leftChildIsNull(int pos) {
        return this.heap[this.leftChild(pos)] == null;
    }

    //Insertar 
    public void insert(Registry registry) {
        if (this.heapSize == this.heap.length) {
            System.out.println("Min-Heap está lleno!");
        }

        if (this.isEmpty()) {
            this.heap[0] = registry;
            this.heapSize++;
        } else {
            this.heap[this.heapSize + 1] = registry;
            this.heapSize++;
            int justAddedPos = this.heapSize;

            while (this.heap[justAddedPos].getTimestamp() < this.heap[parent(justAddedPos)].getTimestamp()) {
                swap(justAddedPos, parent(justAddedPos));
                justAddedPos = parent(justAddedPos);
            }
        }

    }

    //Eliminar y retornar el inimo
    public Registry remove() {

        //Devolver el elemento en la cima
        Registry popped = this.heap[0];

        //Cambiar el front por la hoja mas lejana
        this.heap[0] = this.heap[heapSize];
        minHeapify(0);
        this.heapSize--;

        return popped;
    }

    public void printTree() {

        int counter = 0;
        while (this.heap[counter] != null) {

            //String fName = this.heap[((counter - 1) / 2)].getDocument().getName();
            String parentName;
            String lSonName = "N/A";
            String rSonName = "N/A";

            if (counter == 0) {
                parentName = "N/A";
            } else {
                parentName = this.heap[this.parent(counter)].getDocument().getName();
            }

            if (this.rightChildIsNull(counter)) {
                rSonName = "N/A";
            } else {
                rSonName = this.heap[this.rightChild(counter)].getDocument().getName();
            }

            if (this.leftChildIsNull(counter)) {
                lSonName = "N/A";
            } else {
                lSonName = this.heap[this.leftChild(counter)].getDocument().getName();
            }

            System.out.print("Nodo (Posicion " + counter + "): " + this.heap[counter].getDocument().getName() + "\nHijo izqiuerdo: " + lSonName + "\nHijo derecho: " + rSonName + "\nPadre del nodo: " + parentName + "\n\n");
            counter++;

        }

    }

    public String arrayToString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i <= this.heapSize; i++) {
            result.append("[").append(i).append("] ").append(this.heap[i].getDocument().getName()).append("\n");

//            if (i < this.heapSize) {
//                result.append(", ");
//            }
        }

        return result.toString();
    }
}
