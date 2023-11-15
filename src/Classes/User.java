package Classes;

import DataStructureClasses.SimpleList;

/**
 *
 * @author andre
 */
public class User {

    private String name;
    //type se usa como parametro para prioridad
    private String type;

    //este int se consigue al crear el usuario  y contrastandolo con el type. Menor int es mayor prioridad, empezando de 0.
    //si priority = -1, significa que hubo un error en la asignacion de la prioridad y sera ignorado para toda operacion.
    private int priority;

    private SimpleList files_list;

    /*
        Inicializar constructor
     */
    public User(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /*
        Sobrecarga de metodo Usuario
     */
    public User(String name, String type, SimpleList<User> files_list) {
        this.name = name;
        this.type = type;
        this.files_list = files_list;
        this.definePriority();

    }

    private void definePriority() {

        switch (this.type.toLowerCase()) {
            case "gerente":
                this.priority = 0;
                break;
            case "personal_juridico":
                this.priority = 1;
                break;
            case "personal_administrativo":
                this.priority = 2;
                break;
            case "personal_comun":
                this.priority = 3;
                break;

            default:
                System.out.println("Prioridad no encontrada, el usuario sera ignorado.");
                this.priority = -1;
        }

    }

    /*
        Getters y Setters
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return type;
    }

    public void setPriority(String priority) {
        this.type = priority;
    }

    public SimpleList getFiles_list() {
        return files_list;
    }

    public void setFiles_list(SimpleList files_list) {
        this.files_list = files_list;
    }

}
