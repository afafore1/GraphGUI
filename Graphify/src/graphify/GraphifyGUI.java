/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphify;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Ayomitunde
 */
public class GraphifyGUI extends javax.swing.JFrame {
    Image bufferImage;
    Graphics2D bufferGraphic;
    private ActionListener decreseWeights;

    public GraphifyGUI() {
        initComponents();
        bufferImage = createImage(pnlGraph.getWidth() - 2, pnlGraph.getHeight() - 2);
        bufferGraphic = (Graphics2D) bufferImage.getGraphics();
        Model.edgeWeights = new ArrayList<>();
        Model.vertices = new HashMap<>();
        Model.edges = new ArrayList<>();
        Model.failed = new ArrayList<>();
        Model.vertexColors = new Color[]{Color.blue, Color.red, Color.yellow, Color.green, Color.magenta, Color.orange};
        Model.randomKeys = new HashSet<>();
        Timer animationTimer = new Timer(30, (ActionEvent e) -> {
            if (Model.glowMap.size() > 0) {
                Model.dotOffset = (Model.dotOffset + .07) % 1;
                graph();
            }
        });
        animationTimer.start();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (checkForChange()) {
                    System.exit(0);
                }
            }
        });
        decreseWeights = (ActionEvent e) -> {
            if ("Dijkstra".equals(Model.sim)) {
                reduceIncreasepAmount();
                autoFailure();
                autoHeal();
                //graph();
            }
        };
        Timer exe = new Timer(Model.pChangeTime, decreseWeights);
        exe.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlGraph = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtConsole = new javax.swing.JTextArea();
        btnReset = new javax.swing.JButton();
        lblInfo = new java.awt.Label();
        btnClearConsole = new javax.swing.JButton();
        jcbAlgo = new javax.swing.JComboBox<>();
        btnStart = new javax.swing.JButton();
        btnPrintList = new javax.swing.JButton();
        txtQuery = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuNew = new javax.swing.JMenuItem();
        mnuOpen = new javax.swing.JMenuItem();
        mnuSave = new javax.swing.JMenuItem();
        mnuSaveAs = new javax.swing.JMenuItem();
        mnuQuit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(709, 584));
        setSize(new java.awt.Dimension(709, 584));

        pnlGraph.setBackground(new java.awt.Color(255, 255, 255));
        pnlGraph.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlGraph.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlGraphMouseDragged(evt);
            }
        });
        pnlGraph.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlGraphMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlGraphMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnlGraphMouseReleased(evt);
            }
        });
        pnlGraph.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                pnlGraphComponentResized(evt);
            }
        });

        javax.swing.GroupLayout pnlGraphLayout = new javax.swing.GroupLayout(pnlGraph);
        pnlGraph.setLayout(pnlGraphLayout);
        pnlGraphLayout.setHorizontalGroup(
            pnlGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlGraphLayout.setVerticalGroup(
            pnlGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 329, Short.MAX_VALUE)
        );

        txtConsole.setEditable(false);
        txtConsole.setColumns(20);
        txtConsole.setFont(new java.awt.Font("Courier New", 0, 16)); // NOI18N
        txtConsole.setRows(5);
        jScrollPane1.setViewportView(txtConsole);

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        lblInfo.setText("Source: None - Destination: None");

        btnClearConsole.setText("Clear Console");
        btnClearConsole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearConsoleActionPerformed(evt);
            }
        });

        jcbAlgo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BFS", "DFS", "Dijkstra", "Connectedness" }));
        jcbAlgo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAlgoActionPerformed(evt);
            }
        });

        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnPrintList.setText("Print List");
        btnPrintList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintListActionPerformed(evt);
            }
        });

        txtQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQueryActionPerformed(evt);
            }
        });

        mnuFile.setText("File");

        mnuNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        mnuNew.setText("New");
        mnuNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewActionPerformed(evt);
            }
        });
        mnuFile.add(mnuNew);

        mnuOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        mnuOpen.setText("Open");
        mnuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpenActionPerformed(evt);
            }
        });
        mnuFile.add(mnuOpen);

        mnuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mnuSave.setText("Save");
        mnuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSaveActionPerformed(evt);
            }
        });
        mnuFile.add(mnuSave);

        mnuSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuSaveAs.setText("Save as...");
        mnuSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSaveAsActionPerformed(evt);
            }
        });
        mnuFile.add(mnuSaveAs);

        mnuQuit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        mnuQuit.setText("Quit");
        mnuQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuQuitActionPerformed(evt);
            }
        });
        mnuFile.add(mnuQuit);

        jMenuBar1.add(mnuFile);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlGraph, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClearConsole)
                        .addGap(10, 10, 10)
                        .addComponent(jcbAlgo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQuery, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrintList))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset)
                    .addComponent(btnPrintList)
                    .addComponent(btnClearConsole)
                    .addComponent(jcbAlgo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStart)
                    .addComponent(txtQuery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlGraphMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMouseDragged
        if (Model._selectedNode >= 0) {
            if (SwingUtilities.isLeftMouseButton(evt)) {
                Image buff = createImage(pnlGraph.getWidth() - 1, pnlGraph.getHeight() - 1);
                Graphics buffG = buff.getGraphics();
                buffG.drawImage(bufferImage, 0, 0, this);
                Point source = Model.vertices.get(Model._selectedNode).getLocation();
                drawArrow(buffG, source.x, source.y, evt.getX(), evt.getY());
                pnlGraph.getGraphics().drawImage(buff, 1, 1, this);
            } else if (SwingUtilities.isMiddleMouseButton(evt)) {
                Model.vertices.get(Model._selectedNode).getLocation().x = evt.getX();
                Model.vertices.get(Model._selectedNode).getLocation().y = evt.getY();
                graph();
                Model.changesMade = true;
            }
        }
    }//GEN-LAST:event_pnlGraphMouseDragged

    private void pnlGraphMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMouseClicked
        Model._selectedNode = nodeSelected(evt.getX(), evt.getY());
        if (evt.getClickCount() == 2) {
            if (Model._source == -1 && Model._dest == -1
                    || Model._source != -1 && Model._dest != -1) {
                Model.glowMap.clear();
                Model._source = Model._selectedNode;
                Model._dest = -1;
            } else if (Model._source != Model._selectedNode) {
                Model.glowMap.clear();
                Model._dest = Model._selectedNode;
                Model.set.clear();
                Vertex dest = Model.vertices.get(Model._dest);
                dest.setCapacity(150);
            }

            graph();
        }
    }//GEN-LAST:event_pnlGraphMouseClicked

    private void pnlGraphMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMousePressed
        Model._selectedNode = nodeSelected(evt.getX(), evt.getY());
        String[] types = new String[]{"Person", "City", "Place"};
        if (Model._selectedNode < 0 && SwingUtilities.isLeftMouseButton(evt)) {
            Model.changesMade = true;
            //nodes.put(Model.id, new HashSet());
            Vertex v = new Vertex(Model.id, new Point(evt.getX(), evt.getY()), String.valueOf(Model.id), types[(int) (Math.random() * types.length)], (int) (Math.random() * 50));
            Model.vertices.put(v.getId(), v);
            Model.id++;
        } else if (SwingUtilities.isLeftMouseButton(evt)) {
            if (evt.isControlDown() && evt.isShiftDown()) { // control shift to fail all Model.edges leading out of a vertex
                Vertex fail = Model.vertices.get(Model._selectedNode);
                if (Model.failed.contains(fail)) {
                    Model.failed.remove(fail);
                } else {
                    Model.failed.add(fail);
                }
                Iterator<Edge> e = fail.eList().iterator();
                while (e.hasNext()) {
                    Edge next = e.next();
                    next.setFailed(!next.isFailed()); //set it to opposite of what it is
                }
                Model.visited.clear();
                Model.glowMap.clear();
                Model.set.clear();
                if ("DFS".equals(Model.sim)) {
                    Algorithms.Dfs(Model.vertices.get(Model._source));
                    Algorithms.shortestPath(Model._source, Model._dest);
                } else if ("BFS".equals(Model.sim)) {
                    Algorithms.Bfs(Model.vertices.get(Model._source));
                    Algorithms.shortestPath(Model._source, Model._dest);
                } else if ("Dijkstra".equals(Model.sim)) {
                    Algorithms.execute(Model.vertices.get(Model._source));
                    Algorithms.shortestPath(Model._source, Model._dest);
                }
                Model.changesMade = true;
            }
        } else if (SwingUtilities.isRightMouseButton(evt)) {
            Model.changesMade = true;
            Model.glowMap.clear();
            Model.cutV.clear();
            Model._source = -1;
            Model._dest = -1;

            Vertex remove = Model.vertices.get(Model._selectedNode);
            Iterator<Edge> e = Model.edges.iterator();
            while (e.hasNext()) {
                Edge next = e.next();
                if (next.getSource() == remove || next.getDest() == remove) {
                    next.getSource().eList().remove(next);
                    next.getDest().eList().remove(next);
                    e.remove();
                }
            }
            Model.vertices.remove(Model._selectedNode);

            if (Model._selectedNode == Model._dest) {
                Model._dest = -1;
                Model.glowMap.clear();
            }
            if (Model._selectedNode == Model._source) {
                Model._source = -1;
                Model._dest = -1;
                Model.glowMap.clear();
            }
            Model._selectedNode = -1;
        }
        graph();
    }//GEN-LAST:event_pnlGraphMousePressed

    private void pnlGraphMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMouseReleased
        Model.weight = 0;
        if (Model._selectedNode >= 0) {
            int destination = nodeSelected(evt.getX(), evt.getY());
            if (destination >= 0 && destination != Model._selectedNode) {
                Model.weight = (int) (Math.random() * 100);
                int pAmount = (int) (Math.random() * 40 + 1);
                addEdge(Model.Edgeid, Model._selectedNode, destination, pAmount, Model.weight);
                Model.edgeWeights.add(Model.weight);
                Model._selectedNode = -1;
                Model.changesMade = true;
                Model.Edgeid++;
            }
        }
        graph();
    }//GEN-LAST:event_pnlGraphMouseReleased

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        Model.changesMade = true;
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnClearConsoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearConsoleActionPerformed
        txtConsole.setText("");
    }//GEN-LAST:event_btnClearConsoleActionPerformed

    private void jcbAlgoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAlgoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbAlgoActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        String x = String.valueOf(jcbAlgo.getSelectedItem());
        Model.sim = x;
        Model.glowMap.clear();
        Model.glowMap.clear();
        txtConsole.setText("");
        Model.visited.clear();
        Model.visited.clear();
        Model.set.clear();
        Model.set.clear();
        Model.cutV.clear();
        switch (x) {
            case "DFS":
                txtConsole.setText("");
                if (Model._source == -1 || Model._dest == -1 || Model.vertices.get(Model._source).eList().isEmpty() || Model.vertices.get(Model._dest).eList().isEmpty()) {
                    if (Model._source == -1) {
                        printlnConsole("Please choose a source by double clicking a node\nMake sure source has connections");
                    } else {
                        printlnConsole("Please choose a destination by double clicking a node\nMake sure destination has connections");
                    }
                    return;
                }
                Algorithms.Dfs(Model.vertices.get(Model._source));
                Algorithms.shortestPath(Model._source, Model._dest);
                break;
            case "BFS":
                txtConsole.setText("");
                if (Model._source == -1 || Model._dest == -1 || Model.vertices.get(Model._source).eList().isEmpty() || Model.vertices.get(Model._dest).eList().isEmpty()) {
                    if (Model._source == -1) {
                        printlnConsole("Please choose a source by double clicking a node\nMake sure source has connections");
                    } else {
                        printlnConsole("Please choose a destination by double clicking a node\nMake sure destination has connections");
                    }
                    return;
                }
                Algorithms.Bfs(Model.vertices.get(Model._source));
                Algorithms.shortestPath(Model._source, Model._dest);
                break;
            case "Dijkstra":
                txtConsole.setText("");
                if (Model._source == -1 || Model._dest == -1 || Model.vertices.get(Model._source).eList().isEmpty() || Model.vertices.get(Model._dest).eList().isEmpty()) {
                    if (Model._source == -1) {
                        printlnConsole("Please choose a source by double clicking a node\nMake sure source has connections");
                    } else {
                        printlnConsole("Please choose a destination by double clicking a node\nMake sure destination has connections");
                    }
                    return;
                }
                Algorithms.execute(Model.vertices.get(Model._source));
                Algorithms.shortestPath(Model._source, Model._dest);
                break;
            case "Connectedness":
                txtConsole.setText("");
                if (Model._source == -1) {
                    printlnConsole("Please select a source to begin");
                    return;
                }
                if (Algorithms.isConnected()) {
                    printlnConsole("Graph is Connected");
                } else {
                    printlnConsole("Graph is a disconnected Graph");
                }
                break;
            default:
                break;
        }
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnPrintListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintListActionPerformed
        Iterator<Vertex> verts = Model.vertices.values().iterator();
        while (verts.hasNext()) {
            Vertex next = verts.next();
            printlnConsole(next.getName() + "->" + next.eList());
        }

        printlnConsole("Source is: " + Model._source);
    }//GEN-LAST:event_btnPrintListActionPerformed

    private void pnlGraphComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnlGraphComponentResized
        bufferImage = createImage(pnlGraph.getWidth() - 2, pnlGraph.getHeight() - 2);
        bufferGraphic = (Graphics2D) bufferImage.getGraphics();
    }//GEN-LAST:event_pnlGraphComponentResized

    private void mnuQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuQuitActionPerformed
        if (checkForChange()) {
            System.exit(0);
        }
    }//GEN-LAST:event_mnuQuitActionPerformed

    private void mnuSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSaveAsActionPerformed
        saveAs();
    }//GEN-LAST:event_mnuSaveAsActionPerformed

    private void mnuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSaveActionPerformed
        justSave();
    }//GEN-LAST:event_mnuSaveActionPerformed

    private void mnuOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuOpenActionPerformed
        if (checkForChange()) {
            JFileChooser theChooser = new JFileChooser();
            theChooser.setFileFilter(new FileNameExtensionFilter("GraphifyGUI files", "ser"));
            if (theChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    Model.changesMade = false;
                    reset();
                    Model.currentProject = theChooser.getSelectedFile().getPath();
                    File theFile = new File(Model.currentProject);
                    Open(theFile);
                    graph();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GraphifyGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GraphifyGUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }//GEN-LAST:event_mnuOpenActionPerformed

    private void mnuNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewActionPerformed
        if (checkForChange()) {
            reset();
            Model.currentProject = null;
            Model.changesMade = false;
        }
    }//GEN-LAST:event_mnuNewActionPerformed

    private void txtQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQueryActionPerformed
        //Model._source = -1;
        Model._dest = -1;
        Model.glowMap.clear();
        String query = txtQuery.getText();
        query = query.toLowerCase(); // perform all operations in lower case
        Model.cutV.clear();
        printlnConsole(Commands.action(query, Model.vertices.get(Model._source), Model.vertices));
        graph();
        //String nodeNum = JOptionPane.showInputDialog(null, "Enter number of nodes");
        // randomize(Integer.parseInt(nodeNum));        
    }//GEN-LAST:event_txtQueryActionPerformed

    private void addEdge(Integer edgeId, Integer sourceid, Integer destid, Integer pAmount, final Integer weight) {
        Edge newEdge = new Edge(edgeId, Model.vertices.get(sourceid), Model.vertices.get(destid), pAmount, weight, false);
        Model.edges.add(newEdge);
        Model.vertices.get(sourceid).eList().add(newEdge);
    }

    private void reduceIncreasepAmount() {
        if (Model._source > -1 && Model._dest > -1) {
            int edgeSize = Model.edges.size();
            int rand = (int) (Math.random() * edgeSize);
            Edge e = Model.edges.get(rand);
            int pAmount = e.getpheromoneAmount() + (int) (Math.sqrt(e.getpheromoneAmount())) * (Math.random() > .5 ? -1 : 1);
            if (pAmount <= 0) {
                pAmount += 5;
            }
            e.setpAmount(pAmount); // changes it
            Model.glowMap.clear();
            Algorithms.execute(Model.vertices.get(Model._source));
            Algorithms.shortestPath(Model._source, Model._dest);
            e.setGlowLevel(1);
        }

        //graph();
    }

    private void autoFailure() { // randomly fail nodes in the network
        if (Model._source > -1 && Model._dest > -1) {
            if (Math.random() > 0.5) { // make probability of a node failure low
                int vertexSize = Model.vertices.size();
                int rand = (int) (Math.random() * vertexSize);
                Vertex v = Model.vertices.get(rand);
                if (!(rand == Model._source || rand == Model._dest)) { // do not fail source or destination
                    if (Model.failed.contains(v)) {
                        Model.failed.remove(v);
                    } else {
                        Model.failed.add(v);
                        printlnConsole(v.getName() + " has Model.failed");
                    }
                    Iterator<Edge> e = v.eList().iterator();
                    while (e.hasNext()) {
                        Edge next = e.next();
                        next.setFailed(!next.isFailed()); //set it to opposite of what it is
                    }
                    Model.glowMap.clear();
                    Algorithms.execute(Model.vertices.get(Model._source));
                    Algorithms.shortestPath(Model._source, Model._dest);
                }
            }
        }
    }

    private void autoHeal() {
        if (!Model.failed.isEmpty()) {
            for (Iterator<Vertex> it = Model.failed.iterator(); it.hasNext();) {
                Vertex v = it.next();
                if (Math.random() > 0.5) {
                    it.remove();
                    printlnConsole(v.getName() + " has been healed");
                    v.eList().stream().forEach((next) -> {
                        next.setFailed(!next.isFailed());
                    });
                }
            }
        }
    }

    void Open(File file) throws FileNotFoundException, IOException {
        try {
            try (FileInputStream fis = new FileInputStream(file)) {
                ObjectInputStream ois = new ObjectInputStream(fis);
                Model.vertices = (HashMap) ois.readObject();
                Model.edges = (ArrayList) ois.readObject();
                Model.id = (int) ois.readObject();
                Model.failed = (ArrayList) ois.readObject();
                ois.close();
            }
        } catch (IOException ioe) {
        } catch (ClassNotFoundException c) {
            printlnConsole("Class not found");
        }
    }

    private void reset() {
        Model.vertices = new HashMap();
        Model.edges = new ArrayList();
        Model.id = 0;
        Model.cutV = new ArrayList<>();
        Model._colors2 = new HashSet<>();
        Model.glowMap.clear();
        Model.glowMap.clear();
        Model._source = -1;
        Model._dest = -1;
        graph();
    }

    private String getNodeInfo(int nodeId) {
        if (nodeId == -1) {
            return "None";
        }
        return "" + nodeId;
    }

    public void graph() {
        bufferGraphic.setColor(Color.white);
        bufferGraphic.fillRect(0, 0, pnlGraph.getWidth(), pnlGraph.getHeight());
        Model.connectionCache.clear();
        // Regular connections
        int xmid = 0;
        int ymid = 0;
        bufferGraphic.setColor(Color.black);
        bufferGraphic.setStroke(new BasicStroke(2));
        for (Iterator<Edge> edge = Model.edges.iterator(); edge.hasNext();) {
            Edge e = edge.next();
            Point source = (Point) e.getSource().getLocation().clone();
            Point dest = (Point) e.getDest().getLocation().clone();
            double dx = dest.x - source.x;
            double dy = source.y - dest.y;
            double angle = Math.atan2(dy, dx);
            source.x += (int) (Math.cos(angle) * Model._SIZE_OF_NODE / 2);
            source.y -= (int) (Math.sin(angle) * Model._SIZE_OF_NODE / 2);
            dest.x -= (int) (Math.cos(angle) * Model._SIZE_OF_NODE / 2);
            dest.y += (int) (Math.sin(angle) * Model._SIZE_OF_NODE / 2);
            xmid = (source.x + dest.x) / 2 + 5;
            ymid = (source.y + dest.y) / 2 + 5;
            drawArrowOnGraphics(bufferGraphic, source.x, source.y, dest.x, dest.y);
//            bufferGraphic.drawLine(source.x, source.y, dest.x, dest.y);
            int edgeWeight = e.getWeight();
            if (!(edgeWeight == -1)) {
                bufferGraphic.setColor(
                        new Color(180, 30, 255, (int) (e.getGlowLevel() * 255)));
                bufferGraphic.fillRect(xmid - 6, ymid - 14, 50, 20);
                bufferGraphic.setColor(Color.black);
                bufferGraphic.setStroke(new BasicStroke(2));
                bufferGraphic.drawString(edgeWeight + "/" + e.getpheromoneAmount(), xmid, ymid);
                e.glowDie(.05);
            }
        }

        // Glowing connections
        bufferGraphic.setColor(new Color(10, 230, 40));
        bufferGraphic.setStroke(new BasicStroke(6));
        if (!btnReset.isSelected()) {
            for (Edge edge : Model.glowMap) {
                Point sourcePoint = edge.getSource().getLocation();
                Point destPoint = edge.getDest().getLocation();
                drawDottedLine(bufferGraphic, sourcePoint, destPoint, Model.dotOffset);
            }
        }

        // Nodes - red circles.
        Model.vertices.values().stream().forEach((v) -> {
            Point thePoint = v.getLocation();
            if (v.getId() == Model._source) {
                bufferGraphic.setColor(Color.green);
            } else if (v.getId() == Model._dest) {
                bufferGraphic.setColor(Color.blue);
            } else if (v.getId() == Model._selectedNode) {
                bufferGraphic.setColor(Color.orange);
            } else {
                bufferGraphic.setColor(Color.red);
            }
            if (!Model.failed.isEmpty()) {
                if (Model.failed.contains(v)) {
                    bufferGraphic.setColor(Color.gray);
                }
            }
            if (!Model.cutV.isEmpty()) {
                if (Model.cutV.contains(v.getId())) {
                    bufferGraphic.setColor(Color.green);
                }
            }

            bufferGraphic.fillOval(thePoint.x - Model._SIZE_OF_NODE / 2,
                    thePoint.y - Model._SIZE_OF_NODE / 2, Model._SIZE_OF_NODE, Model._SIZE_OF_NODE);
        });
        // Node labels.
        bufferGraphic.setColor(Color.blue);
        Model.vertices.values().stream().forEach((v) -> {
            Point thePoint = v.getLocation();
            bufferGraphic.drawString("" + v.getId(),
                    thePoint.x - Model._SIZE_OF_NODE / 2,
                    thePoint.y - Model._SIZE_OF_NODE / 2);
        });
        pnlGraph.getGraphics().drawImage(bufferImage, 1, 1, this);
        lblInfo.setText("Source: " + getNodeInfo(Model._source)
                + " - Destination: " + getNodeInfo(Model._dest));
    }

    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();
        drawArrowOnGraphics(g, x1, y1, x2, y2);
    }

    private void drawArrowOnGraphics(Graphics2D g1, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1, dy = y1 - y2;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);

        int p1X = (int) (x2 + Math.cos(angle + Math.PI * 3 / 4) * Model.ARR_SIZE);
        int p1Y = (int) (y2 - Math.sin(angle + Math.PI * 3 / 4) * Model.ARR_SIZE);
        int p2X = (int) (x2 + Math.cos(angle - Math.PI * 3 / 4) * Model.ARR_SIZE);
        int p2Y = (int) (y2 - Math.sin(angle - Math.PI * 3 / 4) * Model.ARR_SIZE);
        // Draw horizontal arrow starting in (0, 0)
        g1.drawLine(x1, y1, x2, y2);
        Polygon polygon = new Polygon();
        polygon.addPoint(x2, y2);
        polygon.addPoint(p1X, p1Y);
        polygon.addPoint(p2X, p2Y);
        g1.fillPolygon(polygon);
    }

    private void drawDottedLine(Graphics2D g, Point p1, Point p2, double offset) {
        long total = (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y);
        total = (long) Math.sqrt(total);
        for (long i = (long) (offset * 20); i <= total; i += 20) {
            int x1 = (int) (p1.x + (p2.x - p1.x) * i / total);
            int y1 = (int) (p1.y + (p2.y - p1.y) * i / total);
            int x2 = (int) (p1.x + (p2.x - p1.x) * Math.min(i + 5, total) / total);
            int y2 = (int) (p1.y + (p2.y - p1.y) * Math.min(i + 5, total) / total);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    private int nodeSelected(int x, int y) {
        for (Vertex v : Model.vertices.values()) {
            Point thePoint = v.getLocation();
            int deltaX = x - (thePoint.x - Model._SIZE_OF_NODE / 2);
            int deltaY = y - (thePoint.y - Model._SIZE_OF_NODE / 2);
            if (Math.sqrt(deltaX * deltaX
                    + deltaY * deltaY) <= Model._SIZE_OF_NODE + 6) {
                return v.getId();
            }
        }
        return -1;
    }

    public void printConsole(String string) {
        txtConsole.append(string);
    }

    public void printlnConsole(String string) {
        txtConsole.append(string + "\n");
        txtConsole.setCaretPosition(txtConsole.getText().length());
    }

    static Vertex getConn(Vertex s, Edge e) {
        if (s == e.getSource()) {
            return e.getDest();
        } else {
            return e.getSource();
        }
    }

    private void save(String path) {
        try {
            try (FileOutputStream fileOut = new FileOutputStream(path); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(Model.vertices);
                out.writeObject(Model.edges);
                out.writeObject(Model.id);
                out.writeObject(Model.failed);
            }

        } catch (IOException ex) {
            Logger.getLogger(GraphifyGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveAs() {
        JFileChooser theChooser = new JFileChooser();
        theChooser.setFileFilter(new FileNameExtensionFilter("GraphifyGUI files", "ser"));
        if (theChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String nominatedPath = theChooser.getSelectedFile().getPath();
            if (!nominatedPath.endsWith(".ser")) {
                nominatedPath += ".ser";
            }
            Model.currentProject = nominatedPath;
            save(Model.currentProject);
        }
    }

    private void justSave() {
        if (Model.currentProject == null) {
            saveAs();
        } else {
            save(Model.currentProject);
        }
    }

    private boolean checkForChange() {
        if (Model.changesMade) {
            int option = JOptionPane.showConfirmDialog(this,
                    "Changes have been made. Do you want to save before continuing?", "", JOptionPane.YES_NO_CANCEL_OPTION);
            switch (option) {
                case JOptionPane.YES_OPTION:
                    justSave();
                    return true;
                case JOptionPane.CANCEL_OPTION:
                    return false;
                case JOptionPane.NO_OPTION:
                    return true;
                default:
                    break;
            }
        }
        return true;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        graph();
    }

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GraphifyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            Model.graph = new GraphifyGUI();
            Model.graph.setLocationRelativeTo(null);
            Model.graph.setExtendedState(MAXIMIZED_BOTH);
            Model.graph.show();
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearConsole;
    private javax.swing.JButton btnPrintList;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnStart;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jcbAlgo;
    private java.awt.Label lblInfo;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenuItem mnuNew;
    private javax.swing.JMenuItem mnuOpen;
    private javax.swing.JMenuItem mnuQuit;
    private javax.swing.JMenuItem mnuSave;
    private javax.swing.JMenuItem mnuSaveAs;
    private javax.swing.JPanel pnlGraph;
    private javax.swing.JTextArea txtConsole;
    private javax.swing.JTextField txtQuery;
    // End of variables declaration//GEN-END:variables
}
