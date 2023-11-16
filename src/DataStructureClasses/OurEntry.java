package DataStructureClasses;

/**
 *
 * @author B-St
 */
class OurEntry<T> {
    private int key;
    private T value;
    private OurEntry<T> next;

    public OurEntry(int key, T value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    public int getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public OurEntry<T> getNext() {
        return next;
    }

    public void setNext(OurEntry<T> next) {
        this.next = next;
    }
}