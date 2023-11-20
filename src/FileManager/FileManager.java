package FileManager;

import Classes.User;
import Classes.UserAdministrator;
import Classes.UserCommon;
import Classes.UserHumanResources;
import DataStructureClasses.SimpleList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author andre
 *
 * Clase que se encarga del manejo de los archivos del proyecto
 */
public class FileManager {

    private final String SAVED_DIRECTORY = ".saved";

    //crea el objeto 
    public FileManager() {
    }

    //revisa si existe una carpeta .saved en el directorio
    public boolean savedPathExists() {
        Path path = Paths.get(this.SAVED_DIRECTORY);
        return Files.exists(path);
    }

    //cuenta la cantidad de archivos en la carpeta .saved
    public int fileCounter() {
        File directory = new File(this.SAVED_DIRECTORY);
        int fileCount = directory.list().length;
        return fileCount;
    }

    //guarda la informacion de un archivo a txt
    public void saveFileToTxt(File inFile) {

        if (this.savedPathExists()) {

            try {
                String newFileName = "saved_" + this.fileCounter() + ".txt";
                File outFile = new File(this.SAVED_DIRECTORY, newFileName);
                FileWriter fileWriter = new FileWriter(outFile);
                String line;
                try {
                    FileReader filereader = new FileReader(inFile);
                    BufferedReader reader = new BufferedReader(filereader);

                    while ((line = reader.readLine()) != null) {
                        fileWriter.write(line);
                        fileWriter.write("\n");
                        line = reader.readLine();
                    }

                    reader.close();
                    JOptionPane.showMessageDialog(null, "El archivo ha sido cargado exitosamente!");

                } catch (IOException e) {
                    e.printStackTrace(System.out);
                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún archivo");
                }

                fileWriter.close();
            } catch (Exception e) {
                System.out.println("Algo salio mal :(");
            }
        } else {

            try {
                File parentFolder = new File(this.SAVED_DIRECTORY);
                parentFolder.mkdir();
                File outFile = new File(parentFolder, "saved_0.txt");
                FileWriter fileWriter = new FileWriter(outFile);
                String line;

                try {
                    FileReader filereader = new FileReader(inFile);
                    BufferedReader reader = new BufferedReader(filereader);

                    while ((line = reader.readLine()) != null) {
                        fileWriter.write(line);
                        fileWriter.write("\n");
                        line = reader.readLine();
                    }

                    reader.close();
                    JOptionPane.showMessageDialog(null, "El archivo ha sido cargado exitosamente!");

                } catch (IOException e) {
                    e.printStackTrace(System.out);
                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún archivo");
                }

                fileWriter.close();
            } catch (IOException excepcion) {
                excepcion.printStackTrace(System.out);
            }

        }
    }

    //guarda una string a txt
    public void saveStringToTxt(String string) {

        try {
            File outFile = new File("Prueba_String.txt");
            FileWriter fileWriter = new FileWriter(outFile);

            fileWriter.write(string);
            fileWriter.close();
//            System.out.println(":)");
        } catch (Exception e) {
            System.out.println(":(");
        }

    }

    // Método para establecer el aspecto visual del sistema
    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //abre una ventana en la que se selecciona un archivo
    public File selectFile() {
        try {
            // Cambiar el aspecto visual del sistema
            this.setSystemLookAndFeel();

            JFileChooser filechooser = new JFileChooser();
            filechooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            filechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            filechooser.setDialogTitle("Seleccione un archivo");
            filechooser.setAcceptAllFileFilterUsed(false);
            filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv"));

            // Mostrar la ventana de selección de archivos
            int result = filechooser.showOpenDialog(filechooser);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = filechooser.getSelectedFile();
                if (file.getName().endsWith(".csv")) {
                    return file;
                } else {
                    System.out.println("Por favor, selecciona un archivo CSV.");
                    return null;
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun archivo.", "Cancelando", JOptionPane.CANCEL_OPTION);
                return null;
            }

        } catch (Exception e) {
            System.out.println("Algo salió mal:(");
            return null;
        }
    }

    // WIP Leer informacin del csv, Hace falta crear los usuarios implementando la cedula y la prioridad de las clases hermanas en su interfaz
    // WIP Leer informacin del csv
    public SimpleList readUsersFromCSV(File inFile) {
        SimpleList userList = new SimpleList();

        if (inFile.getName().endsWith(".csv")) {
            try {
                FileReader fileReader = new FileReader(inFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line;
                //String parts[];
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains("usuario") && line.contains("tipo") && line.contains("cedula")) {
                        ;
                    } else {
                        if (!line.isEmpty()) {
                            String[] data = line.split(","); // Dividir la línea por comas
                            if (data.length == 3) {
                                String username = data[0].trim();
                                String type = data[1].trim();
                                int dni = Integer.parseInt(data[2].trim());

                                // Crear un objeto User y agregarlo a la lista
                                if (type.equals("common")) {
                                    UserCommon commonUser = new UserCommon(username, dni);
                                    userList.addAtTheEnd(commonUser);
                                } else if (type.equals("humanres")) {
                                    UserHumanResources humanresUser = new UserHumanResources(username, dni);
                                    userList.addAtTheEnd(humanresUser);
                                } else if (type.equals("admin")) {
                                    UserAdministrator adminUser = new UserAdministrator(username, dni);
                                    userList.addAtTheEnd(adminUser);
                                }

                            } else {
                                System.out.println("Linea no reconocida! " + line);
                            }
                        }
                    }

                }
                fileReader.close();
                bufferedReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            JOptionPane.showMessageDialog(null, "El archivo no es de tipo CSV", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("El archivo no es de tipo CSV");
        }
        return userList;
    }

    public void writeUsersToCSV(SimpleList<User> userList) {
        try {
            FileWriter fileWriter = new FileWriter("usuarios.csv");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Etiquetas
            bufferedWriter.write("usuario, tipo, cedula");
            bufferedWriter.newLine();

            for (int i = 0; i < userList.getSize(); i++) {
                User retrievedUser = userList.getValueByIndex(i);

                // Obtener tipo de usuario específico
                String userType = "";
                if (retrievedUser instanceof UserCommon) {
                    userType = "common";
                } else if (retrievedUser instanceof UserHumanResources) {
                    userType = "humanres";
                } else if (retrievedUser instanceof UserAdministrator) {
                    userType = "admin";
                }

                // Escribir en el archivo CSV
                bufferedWriter.write(retrievedUser.getName() + ", " + userType + ", " + retrievedUser.getDni());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            JOptionPane.showMessageDialog(null, "Usuarios guardados en el archivo CSV exitosamente", "Guardado con exito", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Usuarios guardados en el archivo CSV exitosamente");
        } catch (IOException e) {
            System.out.println("Algo salió mal:(");
        }
    }

}
