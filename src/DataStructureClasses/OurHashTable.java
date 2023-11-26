package DataStructureClasses;

/**
 *
 * @author B-St
 */
public class OurHashTable<T> {

    //dimesiones originales de la tabla, el tamano inicial y un tamano de extension.
    private final int DEFAULT_TABLE_SIZE = 256;
    private int tableSize;
    
    //Array donde se almacenararn las entries
    private OurEntry<T>[] table;

    //Lista de entradas para la reduccion de la complejidad a la hora de recorrer
    private final SimpleList<T> entriesList;

    // Nombre del archivo original
    private String originalFileName;

    public OurHashTable() {
        this.table = new OurEntry[this.DEFAULT_TABLE_SIZE];
        this.entriesList = new SimpleList();
        this.updateTableSize();
    }

    public SimpleList<T> getEntriesList() {
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

            this.entriesList.addAtTheEnd(value);
        } else {

            System.out.println("La key " + key + " ya esta tomada, por favor escoja otra.");

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
            System.out.println("No hay elemento asociado con la key: " + key.toLowerCase() );
        } else {

            //Conseguir el siguiente elemento en la lista de colisiones si existe
            OurEntry<T> bucketedEntry = this.table[hash].getNext();

            //Eliminamos la entry de la lista de entries
            this.entriesList.delete(this.table[hash].getValue());
            this.table[hash] = null;

            //Re introducir toda la lista de clisiones a la hashtable
            while (bucketedEntry != null) {
                this.put(bucketedEntry.getKey(), bucketedEntry.getValue());
                bucketedEntry = bucketedEntry.getNext();
            }
            
            System.out.println();


        }

    }

    private int hashFunction(int key) {
        int hash = key % this.tableSize;
        return hash;
    }

    private void updateTableSize() {
        this.tableSize = this.table.length;
    }


    public boolean isKeyTaken(String key) {

        int hashedKey = key.toLowerCase().hashCode();
        int hash = Math.abs(hashFunction(hashedKey));

        if (this.table[hash] == null) {
            return false;
        } else {

            OurEntry<T> bucketedEntry = this.table[hash];

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


}
