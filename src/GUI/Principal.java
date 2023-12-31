package GUI;

import Classes.Document;
import Classes.Registry;
import Classes.User;
import Classes.UserAdministrator;
import Classes.UserCommon;
import Classes.UserHumanResources;
import DataStructureClasses.OurHashTable;
import DataStructureClasses.OurQueue;
import DataStructureClasses.RegistryHeapTree;
import DataStructureClasses.SimpleList;
import FileManager.FileManager;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author andre
 */
public class Principal extends javax.swing.JFrame {

    //Variable que guarda el tiempo de inicio del programa en un objeto Instant
    private Instant startTime;
    private boolean changesWereMade = false;

    private HeapVisualizer visualizer = new HeapVisualizer();
    private RegistryHeapTree heapTree = new RegistryHeapTree();

    private OurHashTable<User> usersTable;
    private OurHashTable<Document> documentsTable;
    private static int windowCount = 0;

    public Principal() {

        // para cambiar aspecto visual antes de inicializar los componentes
        this.setSystemLookAndFeel();

        //Inicializacion del "cronometro interno". 
        this.startTime = Instant.now();
        initComponents();

        //Inicializar EDD donde se guardan usuarios y documentos
        this.usersTable = new OurHashTable<User>();
        this.documentsTable = new OurHashTable<Document>();

        // iniciar visual de graphsteam
        System.setProperty("org.graphstream.ui", "swing");

        // properties gui
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Main window");

        // TABLA USUARIOS
        DefaultTableModel model = new DefaultTableModel();
        //Inicializar Contenido
        model.setRowCount(0);
        model.addColumn("Nombre");
        model.addColumn("DNI");
        //model.addColumn("Tipo");
        model.addColumn("Documentos");
        this.layoutUserTable.setModel(model);

        // LISTA REGISTROS
        DefaultListModel listmodel = new DefaultListModel();
        this.registryLayoutList.setModel(listmodel);

    }

    //Se le resta al tiempo actual el tiempo del inicio del programa para obtener la diferencia en segundos que sera usada para las etiquetas de tiempo.
    private long getEventTime() {

        Instant currentTime = Instant.now();
        Duration elapsedTime = Duration.between(this.startTime, currentTime);
        return elapsedTime.getSeconds();

    }

    private void createUser(String userName, int userDni, int userType) {

        switch (userType) {
            case 0:
                UserCommon commonUser = new UserCommon(userName, userDni);
                this.usersTable.put(Integer.toString(commonUser.getDni()), commonUser);
                break;
            case 1:
                UserHumanResources humanUser = new UserHumanResources(userName, userDni);
                this.usersTable.put(Integer.toString(humanUser.getDni()), humanUser);
                break;
            case 2:
                UserAdministrator adminUser = new UserAdministrator(userName, userDni);
                this.usersTable.put(Integer.toString(adminUser.getDni()), adminUser);
                break;
            default:
                break;
        }
        System.out.println("Usuario registrado con exito.");

    }

    private void loadUserFile(File inFile) {
        try {
            FileManager fileManager = new FileManager();
            if (inFile != null) {
                this.usersTable = fileManager.readUsersFromCSV(inFile);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al convertir datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error desconocido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteASingleDocument(User selectedUser) {
        SimpleList<Document> userDocumentList = selectedUser.getFiles_list();

        // Verificar si el usuario tiene documentos antes de continuar
        if (userDocumentList.getSize() > 0) {
            Object[] documentsArray = userDocumentList.toArray(); // Obtener una matriz de documentos

            // Mostrar un cuadro de lista para que el usuario elija un documento a eliminar
            JList<Object> documentList = new JList<>(documentsArray);
            JScrollPane scrollPane = new JScrollPane(documentList);

            int option = JOptionPane.showOptionDialog(
                    null,
                    scrollPane,
                    "Selecciona el documento a eliminar:",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    null
            );

            if (option == JOptionPane.OK_OPTION) {
                // Obtener el documento seleccionado apartir del indice
                Object selectedDocument = documentList.getSelectedValue();
                int documentIndex = userDocumentList.indexOf((Document) selectedDocument);

                //Conseguir el documento
                Document poppedDocument = userDocumentList.getValueByIndex(documentIndex);

                // Eliminar el documento del usuario
                userDocumentList.deleteByIndex(documentIndex);

                //Eliminar el documento de la cola
                this.heapTree.eliminateRegistry(poppedDocument.getName().toLowerCase());

                //Eliminar el documento de la hashTable;
                this.documentsTable.delete(poppedDocument.getName());

                // Actualizar la interfaz después de eliminar el documento
                this.refreshLayoutTable();
                JOptionPane.showMessageDialog(null, "Documento eliminado exitosamente!", "Eliminar Documento", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Cancelando Operacion...", "Cancelar", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(null, "El usuario no tiene documentos para eliminar.", "Eliminar Documento", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void addToPrintQueue(User selectedUser) {
        SimpleList<Document> userDocumentList = selectedUser.getFiles_list();

        // Verificar si el usuario tiene documentos antes de continuar
        if (userDocumentList.getSize() > 0) {
            Object[] documentsArray = userDocumentList.toArray(); // Obtener una matriz de documentos

            // Mostrar un cuadro de lista para que el usuario elija un documento a enviar a la cola
            JList<Object> documentList = new JList<>(documentsArray);
            JScrollPane scrollPane = new JScrollPane(documentList);

            JOptionPane.showMessageDialog(null, "Por favor, selecciona el documento deseado...", "Enviar a la Cola", JOptionPane.INFORMATION_MESSAGE);

            int option = JOptionPane.showOptionDialog(
                    null,
                    scrollPane,
                    "Selecciona el documento a enviar a la cola:",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    null
            );

            if (option == JOptionPane.OK_OPTION) {
                // Obtener el documento seleccionado a partir del índice
                Object selectedDocument = documentList.getSelectedValue();
                int documentIndex = userDocumentList.indexOf((Document) selectedDocument);

                // Conseguir el documento
                Document toSentDocument = userDocumentList.getValueByIndex(documentIndex);

                // Añadir el documento del usuario a la cola de impresión
                if (!this.heapTree.containsDocument(toSentDocument.getName())) {

                    // Prioridad del documento
                    int priorityOption = JOptionPane.showConfirmDialog(
                            null,
                            "¿Este documento es prioritario?",
                            "Prioridad del Documento",
                            JOptionPane.YES_NO_OPTION
                    );

                    boolean isPriority = (priorityOption == JOptionPane.YES_OPTION);

                    // Crear registro y añadir a la cola
                    Registry newDocRegister = new Registry((int) this.getEventTime(), toSentDocument, isPriority);
                    this.heapTree.insert(newDocRegister);

                    // actualizar lista de registros
                    this.refreshLayoutList();
                    System.out.println("Se hara?");
                    this.heapTree.printTree();

                    JOptionPane.showMessageDialog(null, "El documento está preparado en la cola para ser impreso!", "Cola de impresión", JOptionPane.INFORMATION_MESSAGE);

                    //this.heapTree.oldPrint();
                } else {
                    JOptionPane.showMessageDialog(null, "El documento '" + toSentDocument.getName() + "' ya está en la cola de impresión.", "Cola de impresión", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "El usuario no tiene documentos para enviar a la cola.", "Cola de impresión", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminateRegistryQueue() {

        Object[] registryArray = this.heapTree.getRegistryNamesArray();
        JList<Object> registryList = new JList<>(registryArray);
        JScrollPane scrollPane = new JScrollPane(registryList);

        int option = JOptionPane.showOptionDialog(
                null,
                scrollPane,
                "Selecciona el registro a eliminar:",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );

        if (option == JOptionPane.OK_OPTION) {
            // Obtener el registro seleccionado a partir del índice
            Object selectedRegistry = registryList.getSelectedValue();
            String registryName = selectedRegistry.toString();

            // Validar si el usuario realmente quiere eliminar el registro
            int confirmOption = JOptionPane.showConfirmDialog(
                    null,
                    "Estas seguro de que quieres eliminar el registro para el documento '" + registryName + "'?",
                    "Eliminar Registro",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmOption == JOptionPane.YES_OPTION) {
                // Llamar al método para eliminar el registro
                System.out.println("REGISTRO PARA ELIMINAR: ");
                System.out.println(registryName);
                this.heapTree.eliminateRegistry(registryName);
                JOptionPane.showMessageDialog(null, "El documento se ha eliminado del registro!", "Cola de Impresion", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, "No se eliminará del registro.\nCancelando Operacion...", "Cancelar", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cancelando Operacion...", "Cancelar", JOptionPane.ERROR_MESSAGE);
            return;
        }

    }

    private void refreshLayoutTable() {
        DefaultTableModel model = (DefaultTableModel) this.layoutUserTable.getModel();
        model.setRowCount(0);  // Limpiar filas actuales

        // Lógica para obtener los usuarios de la tabla hash y agregarlos a la tabla
        SimpleList<User> usersList = this.usersTable.getEntriesList();

        for (int i = 0; i < usersList.getSize(); i++) {
            model.addRow(new Object[]{usersList.getValueByIndex(i).getName().toUpperCase(), usersList.getValueByIndex(i).getDni(), usersList.getValueByIndex(i).getDocumentNames()});
        }
    }

    private void refreshLayoutList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.clear();

        // prueba
        OurQueue<Registry> auxQueue = new OurQueue<>();
        this.heapTree.printTreeAndUpdateList(auxQueue, listModel);
        this.registryLayoutList.setModel(listModel);

        this.registryLayoutList.revalidate();
        this.registryLayoutList.repaint();
    }

    private void updateComboUsers() {
        // Restablecer items del combo
        this.comboBoxUsers.removeAllItems();
        SimpleList<User> usersList = this.usersTable.getEntriesList();

        for (int i = 0; i < usersList.getSize(); i++) {
            this.comboBoxUsers.addItem(usersList.getValueByIndex(i).getName());
        }
    }

    private void showPrintedDocumentInfo(Document document) {
        // Incrementar el recuento de ventanas emergentes
        windowCount++;

        // Calcular la posición ajustada en función del recuento
        int xOffset = 20 * windowCount;
        int yOffset = 20 * windowCount;

        // Crear una ventana para mostrar la información del documento
        JFrame frame = new JFrame("Información del Documento");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);

        // Ajustar la posición de la ventana
        frame.setLocationRelativeTo(null);  // Centrar en la pantalla
        frame.setLocation(frame.getLocation().x + xOffset, frame.getLocation().y + yOffset);

        JPanel panel = new JPanel();
        frame.add(panel);

        // Crear etiquetas para mostrar la información
        JLabel nameLabel = new JLabel("Nombre del Documento: " + document.getName());
        JLabel creatorLabel = new JLabel("Creado por: " + document.getCreator().getName());

        JTextArea contentArea = new JTextArea(document.getContent());
        contentArea.setEditable(false);

        // Agregar etiquetas y área de contenido al panel
        panel.add(nameLabel);
        panel.add(creatorLabel);
        panel.add(contentArea);

        // Hacer visible la ventana
        frame.setVisible(true);
    }

    private int showExitConfirmationDialog() {

        String[] options = new String[]{"Sí, salir", "No, quedarse"};
        int choice = JOptionPane.showOptionDialog(null, "Realmente quieres salir del programa?", "Confirmación de salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
        return choice;
    }

    private void showAboutDialog() {
        String aboutMessage = "◙ MATERIA: ► Estructuras de Datos ◄\n♦ Proyecto Monticulos Binarios y Colas de Prioridad.\n\n"
                + "◙ AUTORES: \n"
                + "▬ Andres Marquez.\n"
                + "▬ Eros Villarroel.\n\n"
                + "◙ Botones: \n\n-Añadir Usuario: Agrega un nuevo usuario. Pide Nombre, Cedula y Tipo de Usuario.\n-Eliminar Usuario: Elimina un usuario seleccionado, llevandose consigo sus documentos creado (Excepto los que fueron enviados a imprimir).\n-Eliminar Documento: Selecciona un usuario de la tabla y elimina alguno de los documentos creados.\n-Eliminar Doc de la Cola: Elimina algun documento seleccionado de la cola de impresion.\n-Enviar a Imprimir: Selecciona un usuario de la tabla y Agrega un documento proveniente de dicho usuario a la Cola de Impresion.\n-Mostar HeapTree: Muestra una representacion visual de la cola de prioridad a traves de la libreria GraphStream\n-Crear Documento: Crea un nuevo documento para el usuario seleccionado.\n-Imprimir Documento: Imprime todos los documentos presentes en la cola de prioridad y la vacia.\n\n"
                + "► Librerias usadas: \n\n1) AbsoluteLayout.\n2) GraphStream.\n\n";

        JOptionPane.showMessageDialog(
                null,
                aboutMessage,
                "Acerca del Programa",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        layout = new javax.swing.JPanel();
        column = new javax.swing.JPanel();
        bgHeap = new javax.swing.JPanel();
        showHeapTreeLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        addUser = new javax.swing.JLabel();
        deleteUser = new javax.swing.JLabel();
        sendToQueue = new javax.swing.JLabel();
        deleteDocument = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        deleteFromQueue = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        layoutUserTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        contentAreaText = new javax.swing.JTextArea();
        titleField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        comboBoxUsers = new javax.swing.JComboBox<>();
        createNewDocument = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        printQueueBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        registryLayoutList = new javax.swing.JList<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        menubar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        loadBtn = new javax.swing.JMenuItem();
        saveBtn = new javax.swing.JMenuItem();
        exitBtn = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        aboutMsgBtn = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        layout.setBackground(new java.awt.Color(112, 196, 224));
        layout.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        column.setBackground(new java.awt.Color(25, 68, 110));
        column.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        column.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgHeap.setBackground(new java.awt.Color(52, 76, 124));
        bgHeap.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        showHeapTreeLabel.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        showHeapTreeLabel.setForeground(new java.awt.Color(255, 255, 255));
        showHeapTreeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/icon_structure.png"))); // NOI18N
        showHeapTreeLabel.setText("Show Heap");
        showHeapTreeLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        showHeapTreeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showHeapTreeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showHeapTreeLabelMouseClicked(evt);
            }
        });
        bgHeap.add(showHeapTreeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 80));

        column.add(bgHeap, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 590, 250, 80));

        jPanel1.setBackground(new java.awt.Color(36, 120, 175));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(36, 120, 175));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/menu_icon.png"))); // NOI18N
        jLabel2.setOpaque(true);
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 40, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("MENU");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, 40));

        column.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 60));

        addUser.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        addUser.setForeground(new java.awt.Color(255, 255, 255));
        addUser.setText("Añadir Usuario");
        addUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addUserMouseClicked(evt);
            }
        });
        column.add(addUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, -1, 40));

        deleteUser.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        deleteUser.setForeground(new java.awt.Color(255, 255, 255));
        deleteUser.setText("Eliminar Usuario");
        deleteUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteUserMouseClicked(evt);
            }
        });
        column.add(deleteUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, -1, 40));

        sendToQueue.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        sendToQueue.setForeground(new java.awt.Color(255, 255, 255));
        sendToQueue.setText("Enviar a Imprimir");
        sendToQueue.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sendToQueue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendToQueueMouseClicked(evt);
            }
        });
        column.add(sendToQueue, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 490, -1, 40));

        deleteDocument.setBackground(new java.awt.Color(87, 169, 210));
        deleteDocument.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        deleteDocument.setForeground(new java.awt.Color(255, 255, 255));
        deleteDocument.setText("Eliminar Documento");
        deleteDocument.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteDocument.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteDocumentMouseClicked(evt);
            }
        });
        column.add(deleteDocument, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, 40));

        jLabel3.setBackground(new java.awt.Color(87, 169, 210));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/add.png"))); // NOI18N
        jLabel3.setOpaque(true);
        column.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 190, 40));

        jLabel6.setBackground(new java.awt.Color(87, 169, 210));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/delete.png"))); // NOI18N
        jLabel6.setOpaque(true);
        column.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 200, 40));

        jLabel18.setBackground(new java.awt.Color(114, 172, 202));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/send.png"))); // NOI18N
        jLabel18.setOpaque(true);
        column.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 200, 40));

        jLabel19.setBackground(new java.awt.Color(87, 169, 210));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/lista.png"))); // NOI18N
        jLabel19.setOpaque(true);
        column.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 230, 40));

        jPanel5.setBackground(new java.awt.Color(87, 169, 210));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        deleteFromQueue.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        deleteFromQueue.setForeground(new java.awt.Color(255, 255, 255));
        deleteFromQueue.setText("Eliminar de la Cola");
        deleteFromQueue.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteFromQueue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteFromQueueMouseClicked(evt);
            }
        });
        jPanel5.add(deleteFromQueue, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 180, 30));

        column.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 190, 30));

        jPanel6.setBackground(new java.awt.Color(38, 87, 135));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(73, 135, 196));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/delete cola.png"))); // NOI18N
        jPanel4.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 50, -1));

        jPanel6.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 110, 60));

        column.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 250, 150));

        layout.add(column, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 670));

        jPanel2.setBackground(new java.awt.Color(252, 245, 245));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        layoutUserTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        layoutUserTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        layoutUserTable.setShowGrid(true);
        jScrollPane1.setViewportView(layoutUserTable);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 340, 190));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccione...", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Fira Code", 1, 12))); // NOI18N
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        contentAreaText.setColumns(20);
        contentAreaText.setFont(new java.awt.Font("Georgia", 2, 13)); // NOI18N
        contentAreaText.setRows(5);
        contentAreaText.setEnabled(false);
        contentAreaText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                contentAreaTextFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                contentAreaTextFocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(contentAreaText);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 270, 110));

        titleField.setFont(new java.awt.Font("Roboto", 3, 13)); // NOI18N
        titleField.setText("Titulo...");
        titleField.setActionCommand("<Not Set>");
        titleField.setEnabled(false);
        titleField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                titleFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                titleFieldFocusLost(evt);
            }
        });
        jPanel3.add(titleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 170, 30));

        jLabel4.setFont(new java.awt.Font("Fira Code", 1, 12)); // NOI18N
        jLabel4.setText("Usuario");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 50, 30));

        comboBoxUsers.setFont(new java.awt.Font("Fira Code", 1, 12)); // NOI18N
        comboBoxUsers.setEnabled(false);
        jPanel3.add(comboBoxUsers, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 32, 130, 30));

        createNewDocument.setFont(new java.awt.Font("Fira Code", 1, 12)); // NOI18N
        createNewDocument.setText("Crear");
        createNewDocument.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        createNewDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNewDocumentActionPerformed(evt);
            }
        });
        jPanel3.add(createNewDocument, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, -1, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 340, 250));

        jLabel25.setFont(new java.awt.Font("Fira Code", 1, 16)); // NOI18N
        jLabel25.setText("Cola de Impresión");
        jPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 340, -1, -1));

        printQueueBtn.setFont(new java.awt.Font("Fira Code", 1, 12)); // NOI18N
        printQueueBtn.setText("Imprimir!");
        printQueueBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printQueueBtnActionPerformed(evt);
            }
        });
        jPanel2.add(printQueueBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 340, 110, 30));

        registryLayoutList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        registryLayoutList.setFont(new java.awt.Font("Fira Code Light", 1, 13)); // NOI18N
        registryLayoutList.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        registryLayoutList.setFocusable(false);
        registryLayoutList.setRequestFocusEnabled(false);
        jScrollPane3.setViewportView(registryLayoutList);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 380, 340, 190));

        jLabel27.setFont(new java.awt.Font("Fira Code", 1, 16)); // NOI18N
        jLabel27.setText("Lista de Usuarios");
        jPanel2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/print.png"))); // NOI18N
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 330, 40, 40));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/listusers.png"))); // NOI18N
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 40, 40));

        jPanel7.setBackground(new java.awt.Color(227, 223, 232));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/enlace.png"))); // NOI18N
        jPanel7.add(jLabel12);

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 160, 230, 60));

        jPanel8.setBackground(new java.awt.Color(227, 223, 232));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/enlace.png"))); // NOI18N
        jPanel8.add(jLabel15);

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 230, 60));

        jPanel9.setBackground(new java.awt.Color(202, 196, 209));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/register.png"))); // NOI18N
        jPanel9.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 40, 30));

        jLabel23.setFont(new java.awt.Font("Fira Code", 1, 16)); // NOI18N
        jLabel23.setText("Crear Documento");
        jPanel9.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 190, 30));
        jPanel9.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 210, 30));

        jPanel2.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 820, 130));

        jPanel10.setBackground(new java.awt.Color(202, 196, 209));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 820, 130));

        jPanel11.setBackground(new java.awt.Color(227, 216, 241));
        jPanel2.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 220, 80, 40));

        jPanel12.setBackground(new java.awt.Color(227, 216, 241));
        jPanel2.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, 80, 40));

        layout.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 860, 590));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/nubes.png"))); // NOI18N
        layout.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/nubes.png"))); // NOI18N
        jLabel9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        layout.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 140, -1, -1));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/montanas.png"))); // NOI18N
        layout.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, -1, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/montanas.png"))); // NOI18N
        layout.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 420, -1, -1));

        getContentPane().add(layout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1220, 670));

        jMenu1.setText("Archivo");

        loadBtn.setText("Abrir");
        loadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadBtnActionPerformed(evt);
            }
        });
        jMenu1.add(loadBtn);

        saveBtn.setText("Guardar");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });
        jMenu1.add(saveBtn);

        exitBtn.setText("Salir...");
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });
        jMenu1.add(exitBtn);

        menubar.add(jMenu1);

        jMenu2.setText("Acerca de");

        aboutMsgBtn.setText("Sobre el Proyecto");
        aboutMsgBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMsgBtnActionPerformed(evt);
            }
        });
        jMenu2.add(aboutMsgBtn);

        menubar.add(jMenu2);

        setJMenuBar(menubar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showHeapTreeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showHeapTreeLabelMouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            if (this.heapTree.getHeapSize() < 0) {
                JOptionPane.showMessageDialog(null, "\nERROR: No se puede mostrar el arbol.\nEl arbol se encuentra vacio!\n", "Advertencia", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                this.visualizer.visualizeHeap(heapTree);
            }
        }

    }//GEN-LAST:event_showHeapTreeLabelMouseClicked

    private void loadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadBtnActionPerformed
        if (evt.getSource() == this.loadBtn) {
            // seleccionar el archivo
            FileManager fileManager = new FileManager();

            if (this.changesWereMade) {
                int option = JOptionPane.showConfirmDialog(null, "Hay cambios sin guardar. Desea guardar antes de cargar otro archivo?", "Guardar cambios", JOptionPane.YES_NO_CANCEL_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    // Después de guardar, establece changesMade a false
                    File file = fileManager.selectFile();

                    //SimpleList<User> userAuxList = this.usersTable.getUsersList();
                    fileManager.writeUsersToCSV(this.usersTable);
                    this.usersTable.clear();
                    JOptionPane.showMessageDialog(null, "Usuarios guardados en el archivo CSV exitosamente", "Guardado con exito", JOptionPane.INFORMATION_MESSAGE);

                    // CARGAR ARCHIVO
                    this.loadUserFile(file);

                    // Actualizar tabla y cambios
                    this.refreshLayoutTable();
                    this.refreshLayoutList();
                    this.updateComboUsers();
                    this.titleField.setEnabled(true);
                    this.contentAreaText.setEnabled(true);
                    this.comboBoxUsers.setEnabled(true);
                    this.usersTable.showUsersTable();
                    this.changesWereMade = false;

                    JOptionPane.showMessageDialog(null, "Los usuarios se han cargado con exito!", "Carga de archivo", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Guardar y cargar - limpiar hash\n");

                } else if (option == JOptionPane.NO_OPTION) {
                    // No se guardan los cambios, permite cargar otro archivo
                    this.usersTable.clear();
                    File file = fileManager.selectFile();

                    // CARGAR ARCHIVO
                    this.loadUserFile(file);

                    // Actualizar tabla y cambios
                    this.refreshLayoutTable();
                    this.refreshLayoutList();
                    this.updateComboUsers();
                    this.titleField.setEnabled(true);
                    this.contentAreaText.setEnabled(true);
                    this.comboBoxUsers.setEnabled(true);
                    this.changesWereMade = true;
                    this.usersTable.showUsersTable();

                    JOptionPane.showMessageDialog(null, "Los usuarios se han cargado con exito!", "Carga de archivo", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Cargar sin guardar - limpiar hash\n");

                } else {
                    // Cancelar la operación
                    JOptionPane.showMessageDialog(null, "Operacion cancelada! ", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                // En caso de que sea la primera vez cargando archivo (no se ha realizado ningun cambio al incio)
                File file = fileManager.selectFile();
                this.loadUserFile(file);

                this.refreshLayoutTable();
                this.refreshLayoutList();
                this.updateComboUsers();
                this.titleField.setEnabled(true);
                this.contentAreaText.setEnabled(true);
                this.comboBoxUsers.setEnabled(true);
                this.changesWereMade = true;
                this.usersTable.showUsersTable();
                JOptionPane.showMessageDialog(null, "Los usuarios se han cargado con exito!", "Carga de archivo", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }//GEN-LAST:event_loadBtnActionPerformed

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        if (evt.getSource() == this.exitBtn) {

            int option = showExitConfirmationDialog();

            if (option == JOptionPane.YES_OPTION) {
                System.out.println("Exit");
                JOptionPane.showMessageDialog(null, "Saliendo del programa...");
                System.exit(0);
            } else {
                // El usuario eligio quedarse
                System.out.println("Stay");
                JOptionPane.showMessageDialog(null, "Continuando con el programa...");
                return;
            }
        }
    }//GEN-LAST:event_exitBtnActionPerformed

    private void aboutMsgBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMsgBtnActionPerformed
        // TODO add your handling code here:
        if (evt.getSource() == this.aboutMsgBtn) {
            this.showAboutDialog();
        }
    }//GEN-LAST:event_aboutMsgBtnActionPerformed

    private void addUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addUserMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            try {
                JOptionPane.showMessageDialog(null, "Creando un nuevo usuario...", "Atencion!", JOptionPane.INFORMATION_MESSAGE);

                String userName = JOptionPane.showInputDialog(null, "Por favor ingrese el nombre del usuario: ");
                if (userName == null || userName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nombre de usuario invalido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int userDni = Integer.parseInt(JOptionPane.showInputDialog(null, "Indique el numero de cedula del usuario: "));
                if (userDni < 0) {
                    JOptionPane.showMessageDialog(null, "Número de cédula invalido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (this.usersTable.isKeyTaken(Integer.toString(userDni))) {
                    JOptionPane.showMessageDialog(null, "El usuario con cedula '" + userDni + "'\nya se encuentra en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int userType = Integer.parseInt(JOptionPane.showInputDialog(null, "Indique el tipo de usuario...\n[0]: Usuario Comun\n[1]: Usuario Recursos Humanos\n[2]: Adminsitrador\n"));
                if (userType < 0 || userType > 2) {
                    JOptionPane.showMessageDialog(null, "Tipo de usuario invalido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear usuarios para añadir a la tabla hash
                this.createUser(userName, userDni, userType);
                // indicar cambios
                this.changesWereMade = true;
                this.refreshLayoutTable();
                this.updateComboUsers();
                this.titleField.setEnabled(true);
                this.contentAreaText.setEnabled(true);
                this.comboBoxUsers.setEnabled(true);
                this.usersTable.showUsersTable();

                JOptionPane.showMessageDialog(null, "Usuario registrado con exito!", "Añadir usuario", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Formato invalido para el DNI o tipo de usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException n) {
                JOptionPane.showMessageDialog(null, "Cancelando operacion.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error desconocido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }


    }//GEN-LAST:event_addUserMouseClicked

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        // TODO add your handling code here:
        if (evt.getSource() == this.saveBtn) {
            if (this.usersTable.getEntriesList().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Error: El proyecto se encuentra sin ningun dato!", "Mensaje de Error", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                FileManager fileManager = new FileManager();
                //SimpleList<User> userAuxList = this.usersTable.getUsersList();
                fileManager.writeUsersToCSV(this.usersTable);
                JOptionPane.showMessageDialog(null, "Se han guardado los datos con exito!", "Guardar Informacion", JOptionPane.INFORMATION_MESSAGE);

            }
        }
    }//GEN-LAST:event_saveBtnActionPerformed

    private void titleFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_titleFieldFocusGained
        // Cuando el JTextField obtiene foco, eliminar el texto predeterminado
        if (this.titleField.getText().equals("Titulo...")) {
            this.titleField.setText("");
        }
    }//GEN-LAST:event_titleFieldFocusGained

    private void titleFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_titleFieldFocusLost
        // Cuando el JTextField pierde foco, restaurar el texto predeterminado si esta vacio
//        if (this.titleField.getText().isEmpty()) {
//            this.titleField.setText("Titulo...");
//        }
    }//GEN-LAST:event_titleFieldFocusLost

    private void contentAreaTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_contentAreaTextFocusGained

//        if (this.contentAreaText.getText().equals("Contenido...")) {
//            this.contentAreaText.setText("");
//        }
    }//GEN-LAST:event_contentAreaTextFocusGained

    private void contentAreaTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_contentAreaTextFocusLost
//        if (this.contentAreaText.getText().isEmpty()) {
//            this.contentAreaText.setText("Contenido...");
//        }
    }//GEN-LAST:event_contentAreaTextFocusLost

    private void createNewDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createNewDocumentActionPerformed
        // TODO add your handling code here:
        if (evt.getSource() == this.createNewDocument) {
            SimpleList<User> userList = this.usersTable.getEntriesList();
            String titleDocument = this.titleField.getText().toLowerCase();
            String contentDocument = this.contentAreaText.getText();
            Document createdDocument = null;

            if (this.comboBoxUsers.isEnabled()) {

                for (int i = 0; i < userList.getSize(); i++) {
                    User user = userList.getValueByIndex(i);

                    if (this.comboBoxUsers.getSelectedItem().equals(user.getName())) {
                        // Creacion del documento
                        if (user.getDocumentNames().equalsIgnoreCase(titleDocument)) {
                            JOptionPane.showMessageDialog(null, "Error: El documento ya existe en la lista del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        } else {
                            createdDocument = new Document(titleDocument, contentDocument, user);
                            // añadir documento a la lista de documentos del usuario
                            user.addDocument(createdDocument);
                            System.out.println(createdDocument.getContent());
                        }
                    }

                }
                this.refreshLayoutTable();
                JOptionPane.showMessageDialog(null, "Se ha creado un nuevo documento!", "Creacion de Documentos", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, "Error: No se pudo crear el documento.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }//GEN-LAST:event_createNewDocumentActionPerformed

    private void deleteDocumentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteDocumentMouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            SimpleList<User> userList = this.usersTable.getEntriesList();
            int selectedRowIndex = this.layoutUserTable.getSelectedRow();

            if (selectedRowIndex != -1) {
                User selectedUser = userList.getValueByIndex(selectedRowIndex);

                if (selectedUser != null) {
                    this.deleteASingleDocument(selectedUser);

                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona un usuario desde la tabla para eliminar documentos.", "Eliminar Documento", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona un usuario desde la tabla para eliminar documentos.", "Eliminar Documento", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_deleteDocumentMouseClicked

    private void deleteFromQueueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteFromQueueMouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            if (!this.heapTree.isEmpty()) {
                System.out.println("Delete that thing");
                this.eliminateRegistryQueue();

                // Actualizar la visualización de la cola y el JList
                this.refreshLayoutList();
                this.refreshLayoutTable();

            } else {
                JOptionPane.showMessageDialog(null, "Error: La cola de impresión está vacía!\nNo hay registros para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }//GEN-LAST:event_deleteFromQueueMouseClicked

    private void deleteUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteUserMouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            SimpleList<User> userList = this.usersTable.getEntriesList();
            User selectedUser = userList.getValueByIndex(this.layoutUserTable.getSelectedRow());

            if (selectedUser != null) {
                int option = JOptionPane.showConfirmDialog(null, "\nUsuario seleccionado => " + selectedUser.getName().toUpperCase() + "\n\nEstas seguro de que deseas eliminar al usuario?\n", "Eliminar Usuario", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    // Eliminar documentos del usuario
                    SimpleList<Document> userDocumentList = selectedUser.getFiles_list();
                    userDocumentList.wipeList();

                    // Eliminar al usuario de la tabla y de la tabla hash
                    userList.delete(selectedUser);
                    this.usersTable.delete(Integer.toString(selectedUser.getDni()));
                    // Actualizar la informacion de la tabla
                    this.updateComboUsers();
                    this.refreshLayoutTable();
                    System.out.println("\n\nTabla nueva:");
                    JOptionPane.showMessageDialog(null, "El usuario seleccionado ha sido eliminado de la base de datos!", "Eliminar Usuario", JOptionPane.INFORMATION_MESSAGE);
                    this.usersTable.showUsersTable();

                } else {
                    JOptionPane.showMessageDialog(null, "Atencion! Cancelando Operacion.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona un usuario desde la tabla para eliminar.", "Eliminar Usuario", JOptionPane.WARNING_MESSAGE);
            }
        }

    }//GEN-LAST:event_deleteUserMouseClicked

    private void sendToQueueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendToQueueMouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON1) {
            SimpleList<User> userList = this.usersTable.getEntriesList();
            int selectedRowIndex = this.layoutUserTable.getSelectedRow();

            if (selectedRowIndex != -1) {
                User selectedUser = userList.getValueByIndex(selectedRowIndex);

                if (selectedUser != null) {
                    // ENVIAR A LA COLA
                    this.addToPrintQueue(selectedUser);
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario desde la tabla.", "Cola de Impresion", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario desde la tabla para enviar documentos.", "Eliminar Documento", JOptionPane.WARNING_MESSAGE);
            }

        }

    }//GEN-LAST:event_sendToQueueMouseClicked

    private void printQueueBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printQueueBtnActionPerformed
        // TODO add your handling code here:
        if (evt.getSource() == this.printQueueBtn) {
            if (!this.heapTree.isEmpty()) {
                // Mostrar mensaje de confirmacion
                int confirmOption = JOptionPane.showConfirmDialog(
                        null,
                        "Estas seguro de que quieres imprimir los documentos en la cola?",
                        "Imprimir Documentos",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmOption == JOptionPane.YES_OPTION) {
                    while (!this.heapTree.isEmpty()) {
                        // Extraer el documento con mayor prioridad de la cola
                        Registry removingRegistry = this.heapTree.removeMin();
                        Document printedDocument = removingRegistry.getDocument();

                        // actualizar tablas
                        this.refreshLayoutList();
                        this.refreshLayoutTable();

                        // Mostrar ventana con la informacion del documento
                        this.showPrintedDocumentInfo(printedDocument);

                        // Mostrar mensaje de éxito para cada documento
                        JOptionPane.showMessageDialog(null, "Documento impreso con éxito: " + printedDocument.getName(), "Impresión Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    }

                    //JOptionPane.showMessageDialog(null, "Documentos impresos con exito.", "Impresión Exitosa", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Operación de impresion cancelada.", "Cancelar", JOptionPane.WARNING_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Error: La cola de impresion está vacía. No hay documentos para imprimir.", "Cola de Impresion Vacía", JOptionPane.ERROR_MESSAGE);
                return;
            }

        }
    }//GEN-LAST:event_printQueueBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMsgBtn;
    private javax.swing.JLabel addUser;
    private javax.swing.JPanel bgHeap;
    private javax.swing.JPanel column;
    private javax.swing.JComboBox<String> comboBoxUsers;
    private javax.swing.JTextArea contentAreaText;
    private javax.swing.JButton createNewDocument;
    private javax.swing.JLabel deleteDocument;
    private javax.swing.JLabel deleteFromQueue;
    private javax.swing.JLabel deleteUser;
    private javax.swing.JMenuItem exitBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel layout;
    private javax.swing.JTable layoutUserTable;
    private javax.swing.JMenuItem loadBtn;
    private javax.swing.JMenuBar menubar;
    private javax.swing.JButton printQueueBtn;
    private javax.swing.JList<String> registryLayoutList;
    private javax.swing.JMenuItem saveBtn;
    private javax.swing.JLabel sendToQueue;
    private javax.swing.JLabel showHeapTreeLabel;
    private javax.swing.JTextField titleField;
    // End of variables declaration//GEN-END:variables

}
