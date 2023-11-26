package DataStructureClasses;

import Classes.Registry;
import Classes.User;
import Classes.UserAdministrator;
import Classes.UserCommon;
import Classes.UserHumanResources;

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

    // Nombre del archivo original
    private String originalFileName;

    public OurHashTable() {
        this.table = new OurEntry[this.DEFAULT_TABLE_SIZE];
        this.entriesList = new SimpleList<>();
        this.updateTableSize();
    }

    public SimpleList<OurEntry> getEntriesList() {
        return this.entriesList;
    }

    // Método para obtener el nombre del archivo original
    public String getOriginalFileName() {
        return originalFileName;
    }

    // Método para establecer el nombre del archivo original
    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
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
                OurEntry<T> current = this.table[hash];

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

            System.out.println("La key" + key + " ya esta tomada, por favor escoja otra.");

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

    public void delete(String key) {

        int hashedKey = key.toLowerCase().hashCode();
        int hash = Math.abs(hashFunction(hashedKey));

        if (this.table[hash] == null) {
            System.out.println("No hay elemento asociado con esa key.");
        } else {

            OurEntry<T> bucketedEntry = this.table[hash].getNext();

            //Eliminamos la entry de la lista de entries
            this.entriesList.delete(bucketedEntry);
            this.table[hash] = null;

            while (bucketedEntry != null) {
                this.putEntry(bucketedEntry);
                bucketedEntry = bucketedEntry.getNext();
            }

        }

    }

    //Clona la tabla con un nuevo tamano
    private void extendTable() {

        int newTableSize = tableSize + DEFAULT_EXTENSION_SIZE;
        this.updateTableSize(newTableSize);
        OurEntry<T>[] newTable = new OurEntry[newTableSize];
        this.table = newTable;

        SimpleNode<OurEntry> pAux = this.entriesList.getpFirst();

        // Rehashing de las entradas existentes
        while (pAux != null) {

            OurEntry<T> auxEntry = pAux.getData();
            this.putEntry(auxEntry);

        }
    }

    private void putEntry(OurEntry entry) {

        int hashedKey = entry.getHashedKey();
        int hash = Math.abs(hashFunction(hashedKey));

        if (this.table[hash] == null) {

            this.table[hash] = entry;

        } else {
            OurEntry<T> current = this.table[hash];

            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(entry);
        }

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

        int hashedKey = key.toLowerCase().hashCode();
        int hash = Math.abs(hashFunction(hashedKey));

        if (this.table[hash] == null) {
            return false;
        } else {

            OurEntry<T> bucketedEntry = this.table[hash].getNext();

            while (bucketedEntry != null) {
                if ((bucketedEntry.getHashedKey() == hashedKey) && (bucketedEntry.getKey().equals(key.toLowerCase()))) {
                    return true;

                }
                bucketedEntry = bucketedEntry.getNext();
            }

            return false;
        }
    }

    public void clear() {
        this.table = new OurEntry[this.DEFAULT_TABLE_SIZE];
        this.entriesList.wipeList();
        this.updateTableSize();
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

    public SimpleList<User> getUsersList() {
        SimpleList<User> usersList = new SimpleList<>();

        for (int i = 0; i < this.table.length; i++) {
            OurEntry<T> current = this.table[i];

            while (current != null) {
                T value = current.getValue();

                if (value instanceof UserCommon) {
                    usersList.addAtTheEnd((UserCommon) value);
                } else if (value instanceof UserHumanResources) {
                    usersList.addAtTheEnd((UserHumanResources) value);
                } else if (value instanceof UserAdministrator) {
                    usersList.addAtTheEnd((UserAdministrator) value);
                }

                current = current.getNext();
            }
        }

        return usersList;
    }

}
