/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataStructureClasses;

/**
 *
 * @author andre
 */
public class PriorityQueue {
    
    // En base al monticulo binario
    private HeapTree heap;

    public PriorityQueue(int maxsize) {
        this.heap = new HeapTree(maxsize);
    }

    public boolean isEmpty() {
        // Verificar si la cola de prioridad está vacía
        return heap.getSize() == 0;
    }

    public void enqueue(int priority) {
        // Insertar el elemento en la cola de prioridad (montículo)
        this.heap.insert(priority);
    }

    public int dequeue() {
        // Extraer el elemento de mayor prioridad (mínimo en el montículo)
        return this.heap.extractMin();
    }

    public int peek() {
        // Obtener el elemento de mayor prioridad sin extraerlo
        return this.heap.getMin();
    }

    public void printPriorityQueue() {
        // Imprimir la cola de prioridad (montículo) por niveles
        this.heap.printTree();
    }
}
