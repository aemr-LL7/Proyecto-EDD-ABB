package DataStructureClasses;

import Classes.Document;
import Classes.User;

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

    /*
    Funcion para introducir documentos a la tabla, tambien maneja el cambio de tamano si se exede el limite. 
     */
    public void put(String key, T value) {
        int inputKey = key.toLowerCase().hashCode();
        int hash = hashFunction(inputKey);
        hash = Math.abs(hash); 
        OurEntry<T> newEntry = new OurEntry<>(inputKey, value);

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

    }
    
    public T get(String inputKey) {

        int key = inputKey.hashCode();
        int hash = hashFunction(key);
        hash = Math.abs(hash); 
        OurEntry<T> returning = this.table[hash];
        while (returning != null) {
            if (this.checkKey(key, returning)) {
                return returning.getValue();

            }
            returning = returning.getNext();
        }

        return null;
    }

    public User getUser(String name, int CI) {

        int key = name.toLowerCase().hashCode();
        int hash = Math.abs(hashFunction(key));
        OurEntry<User> returning = (OurEntry<User>) this.table[hash];
        while (returning != null) {
            if (this.checkKey(key, returning) && (returning.getValue().getDni()) == CI) {
                return returning.getValue();

            }
            returning = returning.getNext();
        }

        return null;
    }

    public Document getDocument(String name, User creator) {

        int key = name.toLowerCase().hashCode();
        int hash = Math.abs(hashFunction(key));
        hash = Math.abs(hash); 
        OurEntry<Document> returning = (OurEntry<Document>) this.table[hash];
        while (returning != null) {
            if (this.checkKey(key, returning) && (returning.getValue().getCreatorCI() == creator.getDni())) {
                return returning.getValue();

            }
            returning = returning.getNext();
        }

        return null;
    }

    //Clona la tabla con un nuevo tamano
    private void extendTable() {

        int newTableSize = tableSize + DEFAULT_EXTENSION_SIZE;
        this.updateTableSize(newTableSize);
        OurEntry<T>[] newTable = new OurEntry[newTableSize];

        // Rehashing de las entradas existentes
        for (OurEntry<T> entry : this.table) {
            int key = entry.getKey();
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
    
    
    
    private boolean checkKey(int key, OurEntry entry) {
        return key == entry.getKey();
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

}
