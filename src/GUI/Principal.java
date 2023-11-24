package GUI;

import Classes.Document;
import Classes.Registry;
import Classes.User;
import Classes.UserAdministrator;
import Classes.UserCommon;
import Classes.UserHumanResources;
import DataStructureClasses.OurHashTable;
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author B-St
 */
public class Principal extends javax.swing.JFrame {

    //Variable que guarda el tiempo de inicio del programa en un objeto Instant
    private Instant startTime;
    private boolean changesWereMade = false;

    private HeapVisualizer visualizer = new HeapVisualizer();
    private RegistryHeapTree heapTree = new RegistryHeapTree();

    private OurHashTable<User> usersTable;
    private OurHashTable<Document> documentsTable;

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
        model.addColumn("Tipo");
        model.addColumn("Documento");
        this.layoutUserTable.setModel(model);
        
        // Crear y visualizar el montículo
        RegistryHeapTree heap = new RegistryHeapTree();
        // Agregar documentos al montículo
        UserCommon user1 = new UserCommon("Danny", 29986549);
        UserCommon user2 = new UserCommon("Herosland", 29986549);
        UserAdministrator user3 = new UserAdministrator("Yisus", 1);

        Document doc1 = new Document("Pajarito", user1);
        Document doc2 = new Document("Amarillo los platano", user1);
        Document doc3 = new Document("La odisea", user2);
        Document doc4 = new Document("Las desventuras de eduardo", user2);
        Document doc5 = new Document("Aminoacidos traviesos", user3);

        Registry reg1 = new Registry(1, doc1, true);
        Registry reg2 = new Registry(2, doc2, false);
        Registry reg3 = new Registry(3, doc3, false);
        Registry reg4 = new Registry(4, doc4, false);
        Registry reg5 = new Registry(1, doc5, true);

        this.heapTree.insert(reg1);
        this.heapTree.insert(reg2);
        this.heapTree.insert(reg3);
        this.heapTree.insert(reg4);
        this.heapTree.insert(reg5);

        // Visualizar en un panel
        // visualizer.visualizeHeap(heap, this.heapPanel);
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

    private int showExitConfirmationDialog() {

        String[] options = new String[]{"Sí, salir", "No, quedarse"};
        int choice = JOptionPane.showOptionDialog(null, "¿Realmente quieres salir del programa?", "Confirmación de salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
        return choice;
    }

    private void showAboutDialog() {
        String aboutMessage = "◙ MATERIA: ► Estructuras de Datos ◄\n♦ Proyecto Monticulos Binarios y Colas de Prioridad.\n\n"
                + "◙ AUTORES: \n"
                + "▬ Andres Marquez.\n"
                + "▬ Eros Villarroel.\n\n"
                + "◙ Botones: \n\n- \n\n"
                + "◙ Errores conocidos: \n\na \n\n"
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        layoutUserTable = new javax.swing.JTable();
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
        column.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgHeap.setBackground(new java.awt.Color(52, 76, 124));
        bgHeap.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        showHeapTreeLabel.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        showHeapTreeLabel.setForeground(new java.awt.Color(255, 255, 255));
        showHeapTreeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/icon_structure.png"))); // NOI18N
        showHeapTreeLabel.setText("Show Heap");
        showHeapTreeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showHeapTreeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showHeapTreeLabelMouseClicked(evt);
            }
        });
        bgHeap.add(showHeapTreeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 80));

        column.add(bgHeap, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 590, 250, 80));

        jPanel1.setBackground(new java.awt.Color(36, 120, 175));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(36, 120, 175));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/menu_icon.png"))); // NOI18N
        jLabel2.setOpaque(true);
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 40, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("MENU");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

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
        column.add(addUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, -1, -1));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Eliminar Usuario");
        column.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, -1, -1));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Crear nuevo");
        column.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, -1, -1));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("documento");
        column.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, -1, -1));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Mostrar lista de usuarios");
        column.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, -1, -1));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Enviar a Imprimir");
        column.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 510, -1, -1));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Mostar Cola de Impresion");
        column.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 550, -1, -1));

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Eliminar un documento de la cola");
        column.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, -1, -1));

        jLabel3.setBackground(new java.awt.Color(87, 169, 210));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/add.png"))); // NOI18N
        jLabel3.setOpaque(true);
        column.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 180, 40));

        layout.add(column, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 670));

        jPanel2.setBackground(new java.awt.Color(252, 245, 245));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setText("NOTA FUNCIONES IMPORTANTES:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        jLabel11.setText("LIBERAR o VACIAR IMPRESORA");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));

        jLabel12.setText("simulará el avance en la cola de impresión, es decir,");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 170, -1));

        jLabel13.setText("se toma el elemento que tiene la etiqueta de tiempo más pequeña, se desencola y se “imprime”. ");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 170, -1));

        jLabel14.setText("No pierda de vista que esta operación es equivalente a eliminar_min del Montículo binario. ");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 160, -1));

        jLabel16.setText("La cola de impresión no guarda información referente a los propietarios de los documentos que contiene");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 170, -1));

        jLabel17.setText("de manera que esto es una dificultad a la hora de mandar a eliminar un documento. TABLAS DE DISPERSOIN");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, 170, -1));

        jLabel18.setText("Lista de usuarios siempre se debe ver");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 440, -1, -1));

        jLabel19.setText("por cada usuario se podrán observar sus documentos creados");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 470, -1, -1));

        jLabel20.setText("Un usuario podrá eliminar un documento que aún no ha sido enviado a la cola de impresión.");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 490, -1, -1));

        jLabel21.setText("En todo momento se podrá observar la cola de impresión");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 520, -1, -1));

        jLabel22.setText("secuencia de registros correspondientes a los documentos agregados a la cola de impresión");
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 540, -1, -1));

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
        jScrollPane1.setViewportView(layoutUserTable);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, -1, -1));

        layout.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 860, 590));

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
            } else {
                this.visualizer.visualizeHeap(heapTree);
            }
        }

    }//GEN-LAST:event_showHeapTreeLabelMouseClicked

    private void loadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadBtnActionPerformed
        if (evt.getSource() == this.loadBtn) {
            // seleccionar el archivo
            if (this.changesWereMade) {
                int option = JOptionPane.showConfirmDialog(null, "Hay cambios sin guardar. Desea guardar antes de cargar otro archivo?", "Guardar cambios", JOptionPane.YES_NO_CANCEL_OPTION);
                
                if (option == JOptionPane.YES_OPTION) {
                    // Aquí puedes agregar la lógica para guardar los cambios
                    // Después de guardar, establece changesMade a false
                    this.changesWereMade = false;
                } else if (option == JOptionPane.NO_OPTION) {
                    // No se guardan los cambios, permite cargar otro archivo
                    // Aquí puedes agregar la lógica para cargar otro archivo
                } else {
                    // Cancelar la operación
                    // Puedes decidir cómo manejar la cancelación (por ejemplo, no cargar el nuevo archivo)
                }
            } else {
                // En caso de que no se haya realizado ningun cambio
                FileManager fileManager = new FileManager();
                File file = fileManager.selectFile();

                try {
                    if (file != null) {
                        this.usersTable = fileManager.readUsersFromCSV(file);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error al convertir datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error desconocido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                JOptionPane.showMessageDialog(null, "Los usuarios se han cargado con exito!", "Carga de archivo", JOptionPane.INFORMATION_MESSAGE);
                this.usersTable.showUsersTable();
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
            System.out.println(this.changesWereMade);
            
            JOptionPane.showMessageDialog(null, "Usuario registrado con exito!", "Añadir usuario", JOptionPane.INFORMATION_MESSAGE);
            this.usersTable.showUsersTable();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Formato invalido para el DNI o tipo de usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException n) {
            JOptionPane.showMessageDialog(null, "Cancelando operacion.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error desconocido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_addUserMouseClicked

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
    private javax.swing.JMenuItem exitBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel layout;
    private javax.swing.JTable layoutUserTable;
    private javax.swing.JMenuItem loadBtn;
    private javax.swing.JMenuBar menubar;
    private javax.swing.JMenuItem saveBtn;
    private javax.swing.JLabel showHeapTreeLabel;
    // End of variables declaration//GEN-END:variables

}
