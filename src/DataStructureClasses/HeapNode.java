package DataStructureClasses;

/**
 *
 * @author andre
 * @param <T>
 */
public class HeapNode<T> {

    private T data;
    private HeapNode<T> pLeft, pRight, pFather;
    // puede tener un apuntador al nodo padre

    /*
        Inicializar constructor
     */
    public HeapNode(T data) {
        this.data = data;
        this.pLeft = null;
        this.pRight = null;
    }

    /*
        Constructor con apuntador al padre (raiz)
     */
    public HeapNode(T data, HeapNode<T> pFather) {
        this.data = data;
        this.pLeft = null;
        this.pRight = null;
        this.pFather = pFather;
    }

    //Devolver la data del nodo
    public T getData() {
        return data;
    }

    //Establecer la data del nodo
    public void setData(T data) {
        this.data = data;
    }

    //Obtener el hijo izquierdo del nodo
    public HeapNode<T> getpLeft() {
        return pLeft;
    }

    //Determinar el hijo izquierdo del nodo
    public void setpLeft(HeapNode<T> pLeft) {
        this.pLeft = pLeft;
    }
    
    //Obtener el hijo derecho del nodo
    public HeapNode<T> getpRight() {
        return pRight;
    }

    //Establecer el hijo izquierdo del nodo
    public void setpRight(HeapNode<T> pRight) {
        this.pRight = pRight;
    }

    //Obtener el padre del nodo
    public HeapNode<T> getpFather() {
        return pFather;
    }

    public void setpFather(HeapNode<T> pFather) {
        this.pFather = pFather;
    }

}
