/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Classes.Document;
import Classes.UserAdministrator;
import Classes.UserCommon;
import Classes.UserHumanResources;
import DataStructureClasses.RegistryHeapTree;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author B-St
 */
public class Principal extends javax.swing.JFrame {
    
    private long startingTime;
    
    /**
     * Creates new form GUI
     */
    public Principal() {
        initComponents();
        // iniciar visual
        System.setProperty("org.graphstream.ui", "swing");
        
        // properties
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 600);

        // Crear y visualizar el montículo
        RegistryHeapTree heap = new RegistryHeapTree();
        // Agregar documentos al montículo
        UserCommon user1 = new UserCommon("Andru", 291);
        UserHumanResources user2 = new UserHumanResources("Luisito", 290);
        UserAdministrator user3 = new UserAdministrator("Eros", 292);
        
        Document doc1 = new Document("Divina Comedia", user1, true);
        Document doc2 = new Document("La caida del imperio", user1, false);
        Document doc3 = new Document("Yeaaaah Right", user3, true);
        Document doc4 = new Document("My HDD", user3, true);
        Document doc5 = new Document("Watch me Roll", user2, false);
        Document doc6 = new Document("Super-Stick", user1, false);
        Document doc7 = new Document("Busqueda Implacable", user2, true);
        
        heap.insert(doc1);
        heap.insert(doc2);
        heap.insert(doc3);
        heap.insert(doc4);
        heap.insert(doc5);
        heap.insert(doc6);
        heap.insert(doc7);

        HeapVisualizer visualizer = new HeapVisualizer();
        visualizer.visualizeHeap(heap, this.heapPanel);
       

     }

    private long getCreationTime(){
           
        long creationTime = System.currentTimeMillis() - this.startingTime;
        return creationTime;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        heapPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        heapPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout heapPanelLayout = new javax.swing.GroupLayout(heapPanel);
        heapPanel.setLayout(heapPanelLayout);
        heapPanelLayout.setHorizontalGroup(
            heapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        heapPanelLayout.setVerticalGroup(
            heapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        getContentPane().add(heapPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    private javax.swing.JPanel heapPanel;
    // End of variables declaration//GEN-END:variables
}
