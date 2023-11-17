package FileManager;

import Classes.User;
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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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

    //abre una ventana en la que se selecciona un archivo
    public File selectFile() {
        try {
            JFileChooser filechooser = new JFileChooser();
            filechooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            filechooser.setAcceptAllFileFilterUsed(false);
            filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv"));
            filechooser.showOpenDialog(filechooser);

            File file = filechooser.getSelectedFile();
            if (file.getName().endsWith(".csv")) {
                return file;
            } else {
                System.out.println("Por favor, selecciona un archivo CSV.");
                return null;
            }

        } catch (Exception e) {
            System.out.println("Algo salió mal:(");
            return null;
        }
    }

    // WIP Leer informacin del csv, Hace falta crear los usuarios implementando la cedula y la prioridad de las clases hermanas en su interfaz
//    public SimpleList<User> readUsersFromCSV(File inFile) {
//        SimpleList<User> userList = new SimpleList();
//
//        if (inFile.getName().endsWith(".csv")) {
//            try {
//                FileReader fileReader = new FileReader(inFile);
//                BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//                String line;
//                //String parts[];
//                while ((line = bufferedReader.readLine()) != null) {
//                    if (line.contains("usuario") && line.contains("tipo")) {
//                        ;
//                    } else {
//                        if (!line.isEmpty()) {
//                            String[] data = line.split(","); // Dividir la línea por comas
//                            if (data.length == 2) {
//                                String username = data[0].trim();
//                                String type = data[1].trim();
//
//                                // Crear un objeto User y agregarlo a la lista
//                                User newUser = new User(username, type);
//                                userList.addAtTheEnd(newUser);
//                            } else {
//                                System.out.println("Linea no reconocida! " + line);
//                            }
//                        }
//                    }
//
//                }
//                fileReader.close();
//                bufferedReader.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("El archivo no es de tipo CSV");
//        }
//        return userList;
//    }

//    public void writeUsersToCSV(SimpleList<UserBlank> userList) {
//        try {
//            FileWriter fileWriter = new FileWriter("usuarios.csv");
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//
//            // Etiquetas
//            bufferedWriter.write("usuario, tipo");
//            bufferedWriter.newLine();
//
//            for (int i = 0; i < userList.getSize(); i++) {
//                UserBlank newUser = userList.getValueByIndex(i);
//                bufferedWriter.write(newUser.getName() + ", " + newUser.getPriority() + "\n");
//            }
//
//            bufferedWriter.close();
//            System.out.println("Usuarios guardados en el archivo CSV exitosamente");
//        } catch (IOException e) {
//            System.out.println("Algo salió mal:(");
//        }
//    }

}
