package DataStructureClasses;

import Classes.Document;

/**
 *
 * @author B-St
 * 
 * CAMBIAR TODO PARA QUE FUNCIONE CON LA CLASE DE REGISTRY!!!!!!!!!!!!!!!!!!!!!
 */
public class DocumentHeapThree {

    private Document[] heap;
    private int size;//Cantidad de elementos del Heap
    private HeapNode root;

    //Constructor para crear Heap vacio
    public DocumentHeapThree() {
        this.heap = new Document[128];
        this.size = -1;
    }

    public int getSize() {
        return size;
    }

    public Document[] getHeapArray() {
        return this.heap;
    }

    public boolean isEmpty() {
        return size == -1;
    }

    //funcion que retorna el padre del documento en la posicion dada
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

    /*
    esta funcion sirve para subir el documento hasta su posicion real/
     */
    private void shiftUp(int i) {
        while (i > 0 && !(this.heap[this.parent(i)].hasPriorityOver(this.heap[i]))) {
            //Mientras el padre no tenga prioridad sobre el hijo (El timestamp del padre es menor que el del hijo)
            //Se cambia el uno por el otro.
            swap(this.parent(i), i);

            //cambiar i por el padre de i para seguir al documento mientras sube 
            i = this.parent(i);
        }
    }

    public boolean hasPriorityOver(Document document){
        return false;
    }
    
    private void shiftDown(int i) {

        int maxIndex = i;

        //hijo izquierdo
        int leftChildPosition = this.leftChild(i);

        //Si el hijo izquierdo es mayor que el padre, se guarda su posicion
        if (leftChildPosition <= this.size && (this.heap[leftChildPosition].hasPriorityOver(this.heap[maxIndex]))) {
            maxIndex = leftChildPosition;
        }

        //hijo derecho
        int rightChildPosition = this.rightChild(i);

        //Si el hijo derecho es mayor que el padre, se guarda su posicion
        if (leftChildPosition <= this.size && (this.heap[rightChildPosition].hasPriorityOver(this.heap[maxIndex]))) {
            maxIndex = rightChildPosition;
        }

        //Si el indice inicial no es el mayor
        if (i != maxIndex) {
            this.swap(i, maxIndex);
            this.shiftDown(maxIndex);
        }

    }

    //funcion para insertar un elemento en el heap 
    public void insert(Document document) {
        this.size++;
        this.heap[this.size] = document;

        //Se manda hacia arriba hasta que se cumpla la regla de que sus hijos sean menores
        this.shiftUp(this.size);
    }

    //funcion para extraer el elemento de mayor prioridad
    public Document extractMax() {
        if (this.isEmpty()) {
            System.out.println("No se puede extraer de un montículo vacío");
            return null;
        }

        Document returningDocument = heap[0];

        //Reemplazamos la raiz con la hoja mas lejana
        this.heap[0] = this.heap[this.size];
        this.size--;

        //mandar hacia abajo el nuevo elemento hasta que se cumpla la condicion (El padre es mayor que los hijos)
        this.shiftDown(0);
        return returningDocument;
    }

    //funcion para ver el elemento que esta al tope del heap
    public Document getMax() {

        return this.heap[0];

    }

    //Funcion para cambiar los valores de dos posiciones netre si
    private void swap(int i, int j) {

        Document temp = this.heap[i];
        this.heap[i] = this.heap[j];
        this.heap[j] = temp;

    }
    
    public void remove(int i){
        
        //Para que esto funcione con registry, hay que anadirle una cantidad gigante para que siempre sea el mayor hasta que suba
//        this.heap[i] = this.getMax() + 1; 
        
        //Mover el nodo hasta arriba para poderlo sacar
//        this.shiftUp(i);

//        sacar el que esta arriba
//        this.extractMax();
    }

}