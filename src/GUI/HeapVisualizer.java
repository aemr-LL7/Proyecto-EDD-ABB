/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Classes.Registry;
import Classes.User;
import Classes.UserAdministrator;
import Classes.UserCommon;
import Classes.UserHumanResources;
import DataStructureClasses.RegistryHeapTree;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

/**
 *
 * @author andre
 */
public class HeapVisualizer {

    private Graph graph;
    private Viewer viewer;

    public HeapVisualizer() {
        // Crear un nuevo grafo
        this.graph = new SingleGraph("Monticulo Binario");
        this.viewer = null;
    }

    public void eraseVisualizer() {
        if (viewer != null) {
            viewer.close();
            viewer = null;
        }

        graph = new SingleGraph("Grafo");
        viewer = graph.display(true);
        viewer.enableAutoLayout();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
        graph.clear();
    }

    public void resetView() {
        if (viewer != null) {
            viewer.getDefaultView().getCamera().resetView();
        }
    }

    public void visualizeHeap(RegistryHeapTree heap) {
        this.eraseVisualizer(); // Limpiar el grafo existente
        // Nivel actual en el árbol
        int level = 0;

        for (int i = 0; i <= heap.getHeapSize(); i++) {
            Registry registry = heap.getHeapArray()[i];
            User userType = registry.getDocument().getCreator();

            Node node = graph.addNode(Integer.toString(i));
            node.setAttribute("ui.label", "Name: " + registry.getDocument().getName() + ", " + "By: " + registry.getDocument().getCreator().getName());

            // Establecer un color diferente para el nombre de cada nodo según el tipo de usuario
            if (userType instanceof UserCommon) {
                node.setAttribute("ui.style", "text-color: black; size: 15px; text-size: 15px;");
            } else if (userType instanceof UserHumanResources) {
                node.setAttribute("ui.style", "text-color: orange; size: 15px; text-size: 15px;");
            } else if (userType instanceof UserAdministrator) {
                node.setAttribute("ui.style", "text-color: blue; size: 15px; text-size: 15px;");
            }

            // Mas ajustes de estilo
            if (registry.isPriority()) {
                node.setAttribute("ui.style", "fill-color: red; size: 15px; text-size: 15px;");
            } else {
                node.setAttribute("ui.style", "fill-color: green; size: 15px; text-size: 15px;");
            }

            // Añadir aristas
            if (i != 0) {
                int parentIndex = (i - 1) / 2;

                // Calcular la posicion en funcion del nivel y la posicion
                double[] coordinates = calculateNodeCoordinates(i, level);
                node.setAttribute("xyz", coordinates[0], coordinates[1], coordinates[2]);

                Edge edge = graph.addEdge(Integer.toString(parentIndex) + "-" + Integer.toString(i),
                        Integer.toString(parentIndex), Integer.toString(i), true);
                edge.setAttribute("ui.style", "fill-color: black; size: 2px;");
            }

            // Actualizar el nivel cuando se completa un nivel completo del árbol
            if (i == Math.pow(2, level + 1) - 2) {
                level++;
            }
        }

        // Obtener el panel de vista y agregarlo al panel proporcionado
        ViewPanel view = (ViewPanel) viewer.getDefaultView();
//        graphPanel.setLayout(new BorderLayout());
//        graphPanel.add(view, BorderLayout.CENTER);
//        graphPanel.revalidate();
//        graphPanel.repaint();

        // Listener de la rueda del mouse para el zoom
        view.addMouseWheelListener(e -> {
            if (e.getWheelRotation() < 0) {
                // Rueda hacia arriba (zoom in)
                this.zoomIn(viewer);
            } else {
                // Rueda hacia abajo (zoom out)
                this.zoomOut(viewer);
            }
        });
    }

    public void zoomIn(Viewer viewer) {
        adjustZoom(viewer, 0.9);
        updateViewRange(viewer);
    }

    public void zoomOut(Viewer viewer) {
        adjustZoom(viewer, 1.1);
        updateViewRange(viewer);
    }

    private void adjustZoom(Viewer viewer, double factor) {
        if (viewer != null && viewer.getDefaultView() != null) {
            viewer.getDefaultView().getCamera().setViewPercent(viewer.getDefaultView().getCamera().getViewPercent() * factor);
        }
    }

    private void updateViewRange(Viewer viewer) {
        if (viewer != null && viewer.getDefaultView() != null) {
            viewer.getDefaultView().getCamera().setViewPercent(viewer.getDefaultView().getCamera().getViewPercent());
        }
    }

    private double[] calculateNodeCoordinates(int position, int level) {
        double x = (position % Math.pow(2, level + 1)) * 50;
        double y = -level * 50;
        double z = 0;

        return new double[]{x, y, z};
    }

}
