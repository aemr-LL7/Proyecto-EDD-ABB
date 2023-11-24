package DataStructureClasses;

/**
 *
 * @author B-St
 */
public class OurHashTable<T> {

    //dimesiones originales de la tabla, el tamano inicial y un tamano de extension.
    private final int DEFAULT_TABLE_SIZE = 256;
    private final int DEFAULT_EXTENSION_SIZE = 64;
    private int tableSize;

    //Limite del 70% de la tabla para extenderla dinamicamente
    private final int FILLED_TRESHOLD = (int) (this.DEFAULT_TABLE_SIZE * 0.7);
    private OurEntry<T>[] table;

    //Lista de entradas para la reduccion de la complejidad a la hora de recorrer
    private final SimpleList<OurEntry> entriesList;

    public OurHashTable() {
        this.table = new OurEntry[this.DEFAULT_TABLE_SIZE];
        this.entriesList = new SimpleList<>();
        this.updateTableSize();
    }

    public SimpleList<OurEntry> getEntriesList() {
        return this.entriesList;
    }

    /*
    Funcion para introducir documentos a la tabla, tambien maneja el cambio de tamano si se excede el limite. 
     */
    public void put(String key, T value) {

        if (!this.isKeyTaken(key)) {

            int hashedKey = key.toLowerCase().hashCode();
            int hash = hashFunction(hashedKey);
            hash = Math.abs(hash);
            OurEntry<T> newEntry = new OurEntry<>(key.toLowerCase(), hashedKey, value);

            if (table[hash] == null) {
                table[hash] = newEntry;
            } else {
                OurEntry<T> current = table[hash];

                while (current.getNext() != null) {
                    current = current.getNext();
                }
                current.setNext(newEntry);
            }

            this.entriesList.addAtTheEnd(newEntry);
            int filledSpots = this.entriesList.getSize();
            if (filledSpots >= this.FILLED_TRESHOLD) {
                this.extendTable();
            }
        } else {

            String capitalizedKey = key.substring(0, 1).toUpperCase() + key.substring(1);
            System.out.println("el nombre " + capitalizedKey + " esta tomado, por favor escoja otra.");
        }
    }

    public T get(String key) {

        int hashedKey = key.toLowerCase().hashCode();
        int hash = Math.abs(hashFunction(hashedKey));
        OurEntry<T> returning = this.table[hash];
        while (returning != null) {

            if ((returning.getHashedKey() == hashedKey) && (returning.getKey().equals(key.toLowerCase()))) {
                return returning.getValue();

            }
            returning = returning.getNext();
        }

        return null;
    }

    public void showUsersTable() {
        for (int i = 0; i < this.table.length; i++) {
            OurEntry<T> current = this.table[i];

            while (current != null) {
                System.out.println(current.getKey() + ": " + current.getValue().toString());
                current = current.getNext();
            }
        }
    }

    //Clona la tabla con un nuevo tamano
    private void extendTable() {

        int newTableSize = tableSize + DEFAULT_EXTENSION_SIZE;
        this.updateTableSize(newTableSize);
        OurEntry<T>[] newTable = new OurEntry[newTableSize];

        // Rehashing de las entradas existentes
        for (OurEntry<T> entry : this.table) {
            int key = entry.getHashedKey();
            int hash = hashFunction(key);

            if (newTable[hash] == null) {
                newTable[hash] = entry;
            } else {
                OurEntry<T> current = newTable[hash];
                while (current.getNext() != null) {
                    current = current.getNext();
                }
                current.setNext(entry);
            }
        }

        this.table = newTable;
    }

    private int hashFunction(int key) {
        int hash = key % this.tableSize;
        return hash;
    }

    private void updateTableSize() {
        this.tableSize = this.table.length;
    }

    private void updateTableSize(int newTablesize) {
        this.tableSize = newTablesize;
    }

    public boolean isKeyTaken(String key) {

        SimpleNode<OurEntry> pAux = this.entriesList.getpFirst();

        while (pAux != null) {

            if (pAux.getData().getKey().equals(key.toLowerCase())) {
                return true;
            }
            pAux = pAux.getpNext();

        }

        return false;
    }

}
