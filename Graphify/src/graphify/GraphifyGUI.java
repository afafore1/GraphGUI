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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Ayomitunde
 */
public class GraphifyGUI extends javax.swing.JFrame {

    HashMap<Integer, Integer> connectionCache = new HashMap<>();
    HashMap<Integer, Integer> glowMap;
    Runnable decreseWeights;
    Queue<Integer> queue;
    Stack<Integer> stack;
    String sim = null;
    HashMap<Integer, Integer> distTo;
    Map<Integer, Integer> set;
    HashMap<Integer, Integer> visited;
    HashMap<Integer, Integer> color;
    HashMap<Integer, Integer> fcolors;
    HashMap<Integer, Integer> greedyresult;
    HashSet<Integer> _colors2;
    HashSet<Integer> randomKeys;
    ArrayList<Integer> cutV;
    ArrayList<Vertex> failed;
    Color[] vertexColors;
    int _selectedNode = -1;
    final int ARR_SIZE = 4;
    int _SIZE_OF_NODE = 20;
    int id = 0;
    int Edgeid = 0;
    int weight = 0;
    List<Integer> edgeWeights; // store weights here
    int time = 0;
    Integer maxColors = 0;
    int _source = -1;
    int _dest = -1;
    Image bufferImage;
    Graphics2D bufferGraphic;
    String currentProject = null;
    boolean changesMade = false;
    static boolean algCalled = false;
    double dotOffset = 0.0;
    Algorithms alg;
    Vertex ver;

    private static HashMap<Integer, Vertex> vertices;
    private static ArrayList<Edge> edges;

    public GraphifyGUI() {
        initComponents();
        bufferImage = createImage(pnlGraph.getWidth() - 2, pnlGraph.getHeight() - 2);
        bufferGraphic = (Graphics2D) bufferImage.getGraphics();
        this.alg = new Algorithms(this);
        this.ver = new Vertex(this);
        edgeWeights = new ArrayList<>();
        vertices = new HashMap<>();
        edges = new ArrayList<>();
        queue = alg.getQueue();
        stack = alg.getStack();
        cutV = alg.getCutV();
        failed = new ArrayList<>();
        visited = alg.getVisited();
        glowMap = alg.getGlowMap();
        set = alg.getSet();
        distTo = alg.distTo();
        vertexColors = new Color[]{Color.blue, Color.red, Color.yellow, Color.green, Color.magenta, Color.orange};
        randomKeys = new HashSet<>();
        Timer animationTimer = new Timer(30, (ActionEvent e) -> {
            if (glowMap.size() > 0) {
                dotOffset = (dotOffset + .07) % 1;
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
        this.decreseWeights = () -> {
            if ("Dijkstra".equals(sim)) {
                reduceIncreasepAmount();
                //graph();
            }
        };
        int time = 500;
        ScheduledExecutorService exe = Executors.newScheduledThreadPool(1);
        exe.scheduleAtFixedRate(decreseWeights, 0, time, TimeUnit.MILLISECONDS);
    }

    public static HashMap getNode() {
        return GraphifyGUI.vertices;
    }

    public static ArrayList getEdges() {
        return GraphifyGUI.edges;
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
        if (_selectedNode >= 0) {
            if (SwingUtilities.isLeftMouseButton(evt)) {
                Image buff = createImage(pnlGraph.getWidth() - 1, pnlGraph.getHeight() - 1);
                Graphics buffG = buff.getGraphics();
                buffG.drawImage(bufferImage, 0, 0, this);
                Point source = vertices.get(_selectedNode).getLocation();
                drawArrow(buffG, source.x, source.y, evt.getX(), evt.getY());
                pnlGraph.getGraphics().drawImage(buff, 1, 1, this);
            } else if (SwingUtilities.isMiddleMouseButton(evt)) {
                vertices.get(_selectedNode).getLocation().x = evt.getX();
                vertices.get(_selectedNode).getLocation().y = evt.getY();
                graph();
                changesMade = true;
            }
        }
    }//GEN-LAST:event_pnlGraphMouseDragged

    private void pnlGraphMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMouseClicked
        _selectedNode = nodeSelected(evt.getX(), evt.getY());
        if (evt.getClickCount() == 2) {
            if (_source == -1 && _dest == -1
                    || _source != -1 && _dest != -1) {
                glowMap.clear();
                _source = _selectedNode;
                _dest = -1;
            } else if (_source != _selectedNode) {
                glowMap.clear();
                _dest = _selectedNode;
                set.clear();
                Vertex dest = vertices.get(_dest);
                dest.setCapacity(150);
            }

            graph();
        }
    }//GEN-LAST:event_pnlGraphMouseClicked

    private void pnlGraphMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMousePressed
        _selectedNode = nodeSelected(evt.getX(), evt.getY());
        String[] types = new String[]{"Person", "City", "Place"};
        if (_selectedNode < 0 && SwingUtilities.isLeftMouseButton(evt)) {
            changesMade = true;
            //nodes.put(id, new HashSet());
            Vertex v = new Vertex(id, new Point(evt.getX(), evt.getY()), String.valueOf(id), types[(int) (Math.random() * types.length)], (int) (Math.random() * 50));
            vertices.put(v.getId(), v);
            id++;
        } else if (SwingUtilities.isLeftMouseButton(evt)) {
            if (evt.isControlDown() && evt.isShiftDown()) { // control shift to fail all edges leading out of a vertex
                Vertex fail = vertices.get(_selectedNode);
                if (failed.contains(fail)) {
                    failed.remove(fail);
                } else {
                    failed.add(fail);
                }
                Iterator<Edge> e = fail.eList().iterator();
                while (e.hasNext()) {
                    Edge next = e.next();
                    next.setFailed(!next.isFailed()); //set it to opposite of what it is
                }
                alg.getVisited().clear();
                alg.getGlowMap().clear();
                alg.getSet().clear();
                if ("DFS".equals(sim)) {
                    alg.Dfs(vertices.get(_source));
                    alg.shortestPath(_source, _dest);
                } else if ("BFS".equals(sim)) {
                    Algorithms.Bfs(vertices.get(_source));
                    alg.shortestPath(_source, _dest);
                } else if ("Dijkstra".equals(sim)) {
                    alg.execute(vertices.get(_source));
                    alg.shortestPath(_source, _dest);
                }
                changesMade = true;
            }
        } else if (SwingUtilities.isRightMouseButton(evt)) {
            changesMade = true;
            glowMap.clear();
            cutV.clear();
            _colors2.clear();
            _source = -1;
            _dest = -1;

            Vertex remove = vertices.get(_selectedNode);
            Iterator<Edge> e = edges.iterator();
            while (e.hasNext()) {
                Edge next = e.next();
                if (next.getSource() == remove || next.getDest() == remove) {
                    next.getSource().eList().remove(next);
                    next.getDest().eList().remove(next);
                    e.remove();
                }
            }
            vertices.remove(_selectedNode);

            if (_selectedNode == _dest) {
                _dest = -1;
                glowMap.clear();
            }
            if (_selectedNode == _source) {
                _source = -1;
                _dest = -1;
                glowMap.clear();
            }
            _selectedNode = -1;
        }
        graph();
    }//GEN-LAST:event_pnlGraphMousePressed

    private void pnlGraphMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMouseReleased
        weight = 0;
        if (_selectedNode >= 0) {
            int destination = nodeSelected(evt.getX(), evt.getY());
            if (destination >= 0 && destination != _selectedNode) {
                weight = (int) (Math.random() * 100);
                int pAmount = (int) (Math.random() * 40 + 1);
                addEdge(Edgeid, _selectedNode, destination, pAmount, weight);
                edgeWeights.add(weight);
                _selectedNode = -1;
                changesMade = true;
                Edgeid++;
            }
        }
        graph();
    }//GEN-LAST:event_pnlGraphMouseReleased

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        changesMade = true;
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
        sim = x;
        glowMap.clear();
        alg.getGlowMap().clear();
        txtConsole.setText("");
        visited.clear();
        alg.getVisited().clear();
        set.clear();
        alg.getSet().clear();
        cutV.clear();
        alg.getCutV().clear();
        switch (x) {
            case "DFS":
                txtConsole.setText("");
                if (_source == -1 || _dest == -1 || vertices.get(_source).eList().isEmpty() || vertices.get(_dest).eList().isEmpty()) {
                    if (_source == -1) {
                        printlnConsole("Please choose a source by double clicking a node\nMake sure source has connections");
                    } else {
                        printlnConsole("Please choose a destination by double clicking a node\nMake sure destination has connections");
                    }
                    return;
                }
                alg.Dfs(vertices.get(_source));
                alg.shortestPath(_source, _dest);
                break;
            case "BFS":
                txtConsole.setText("");
                if (_source == -1 || _dest == -1 || vertices.get(_source).eList().isEmpty() || vertices.get(_dest).eList().isEmpty()) {
                    if (_source == -1) {
                        printlnConsole("Please choose a source by double clicking a node\nMake sure source has connections");
                    } else {
                        printlnConsole("Please choose a destination by double clicking a node\nMake sure destination has connections");
                    }
                    return;
                }
                Algorithms.Bfs(vertices.get(_source));
                alg.shortestPath(_source, _dest);
                break;
            case "Dijkstra":
                txtConsole.setText("");
                if (_source == -1 || _dest == -1 || vertices.get(_source).eList().isEmpty() || vertices.get(_dest).eList().isEmpty()) {
                    if (_source == -1) {
                        printlnConsole("Please choose a source by double clicking a node\nMake sure source has connections");
                    } else {
                        printlnConsole("Please choose a destination by double clicking a node\nMake sure destination has connections");
                    }
                    return;
                }
                alg.execute(vertices.get(_source));
                alg.shortestPath(_source, _dest);
                break;
            case "Connectedness":
                txtConsole.setText("");
                if (_source == -1) {
                    printlnConsole("Please select a source to begin");
                    return;
                }
                if (alg.isConnected()) {
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
        Iterator<Vertex> verts = vertices.values().iterator();
        while (verts.hasNext()) {
            Vertex next = verts.next();
            printlnConsole(next.getName() + "->" + next.eList());
        }

        printlnConsole("Source is: " + _source);
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
                    changesMade = false;
                    reset();
                    currentProject = theChooser.getSelectedFile().getPath();
                    File theFile = new File(currentProject);
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
            currentProject = null;
            changesMade = false;
        }
    }//GEN-LAST:event_mnuNewActionPerformed

    private void txtQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQueryActionPerformed
        //_source = -1;
        _dest = -1;
        glowMap.clear();
        String query = txtQuery.getText();
        query = query.toLowerCase(); // perform all operations in lower case
        cutV.clear();
        printlnConsole(Commands.action(query, vertices.get(_source), vertices));
        cutV = Commands.cutV;
        graph();
        //String nodeNum = JOptionPane.showInputDialog(null, "Enter number of nodes");
        // randomize(Integer.parseInt(nodeNum));        
    }//GEN-LAST:event_txtQueryActionPerformed

    private void addEdge(int edgeId, int sourceid, int destid, int pAmount, final int weight) {
        Edge newEdge = new Edge(edgeId, vertices.get(sourceid), vertices.get(destid), pAmount, weight, false);
        edges.add(newEdge);
        vertices.get(sourceid).eList().add(newEdge);
        vertices.get(destid).eList().add(newEdge);
    }

    private void reduceIncreasepAmount() {
        if (_source > -1 && _dest > -1) {
            int edgeSize = edges.size();
            int rand = (int) (Math.random() * edgeSize);
            Edge e = edges.get(rand);
            int pAmount = e.getpheromoneAmount() + (int) (Math.sqrt(e.getpheromoneAmount())) * (Math.random() > .5 ? -1 : 1);
            if(pAmount == 0){
                pAmount += 5;
            }
            e.setpAmount(pAmount); // changes it
            glowMap.clear();
            alg.execute(vertices.get(_source));
            alg.shortestPath(_source, _dest);
        }

        //graph();
    }

    void Open(File file) throws FileNotFoundException, IOException {
        try {
            try (FileInputStream fis = new FileInputStream(file)) {
                ObjectInputStream ois = new ObjectInputStream(fis);
                vertices = (HashMap) ois.readObject();
                edges = (ArrayList) ois.readObject();
                id = (int) ois.readObject();
                failed = (ArrayList) ois.readObject();
                ois.close();
            }
        } catch (IOException ioe) {
        } catch (ClassNotFoundException c) {
            printlnConsole("Class not found");
        }
    }

    private void reset() {
        vertices = new HashMap();
        edges = new ArrayList();
        id = 0;
        cutV = new ArrayList<>();
        alg.getCutV().clear();
        _colors2 = new HashSet<>();
        glowMap.clear();
        alg.getGlowMap().clear();
        _source = -1;
        _dest = -1;
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
        connectionCache.clear();
        // Regular connections
        bufferGraphic.setColor(Color.black);
        bufferGraphic.setStroke(new BasicStroke(2));
        int xmid = 0;
        int ymid = 0;
        for (Edge e : edges) {
            Point source = e.getSource().getLocation();
            Point dest = e.getDest().getLocation();
            xmid = (source.x + dest.x) / 2 + 5;
            ymid = (source.y + dest.y) / 2 + 5;
            bufferGraphic.drawLine(source.x, source.y, dest.x, dest.y);
            int edgeWeight = e.getWeight();
            if (!(edgeWeight == -1)) {
                bufferGraphic.drawString(edgeWeight + "/" + e.getpheromoneAmount(), xmid, ymid);
            }
        }

        // Glowing connections
        bufferGraphic.setColor(new Color(10, 230, 40));
        bufferGraphic.setStroke(new BasicStroke(6));
        if (!btnReset.isSelected()) {
            for (Iterator<Integer> it = glowMap.keySet().iterator(); it.hasNext();) {
                int sourceKey = it.next();
                int destKey = glowMap.get(sourceKey);
                Point sourcePoint = vertices.get(sourceKey).getLocation();
                Point destPoint = vertices.get(destKey).getLocation();
                drawDottedLine(bufferGraphic, sourcePoint, destPoint, dotOffset);
            }
        }

        // Nodes - red circles.
        for (Vertex v : vertices.values()) {
            Point thePoint = v.getLocation();
            if (v.getId() == _source) {
                bufferGraphic.setColor(Color.green);
            } else if (v.getId() == _dest) {
                bufferGraphic.setColor(Color.blue);
            } else if (v.getId() == _selectedNode) {
                bufferGraphic.setColor(Color.orange);
            } else {
                bufferGraphic.setColor(Color.red);
            }
            if (!failed.isEmpty()) {
                if (failed.contains(v)) {
                    bufferGraphic.setColor(Color.gray);
                }
            }
            if (!cutV.isEmpty()) {
                if (cutV.contains(v.getId())) {
                    bufferGraphic.setColor(Color.green);
                }
            }

            bufferGraphic.fillOval(thePoint.x - _SIZE_OF_NODE / 2,
                    thePoint.y - _SIZE_OF_NODE / 2, _SIZE_OF_NODE, _SIZE_OF_NODE);
        }
        // Node labels.
        bufferGraphic.setColor(Color.blue);
        for (Vertex v : vertices.values()) {
            Point thePoint = v.getLocation();
            bufferGraphic.drawString("" + v.getId(),
                    thePoint.x - _SIZE_OF_NODE / 2,
                    thePoint.y - _SIZE_OF_NODE / 2);
        }
        pnlGraph.getGraphics().drawImage(bufferImage, 1, 1, this);
        lblInfo.setText("Source: " + getNodeInfo(_source)
                + " - Destination: " + getNodeInfo(_dest));
    }

    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();
        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);
        g.fillPolygon(new int[]{len, len - ARR_SIZE, len - ARR_SIZE, len},
                new int[]{0, -ARR_SIZE, ARR_SIZE, 0}, 4);
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
        for (Vertex v : vertices.values()) {
            Point thePoint = v.getLocation();
            int deltaX = x - (thePoint.x - _SIZE_OF_NODE / 2);
            int deltaY = y - (thePoint.y - _SIZE_OF_NODE / 2);
            if (Math.sqrt(deltaX * deltaX
                    + deltaY * deltaY) <= _SIZE_OF_NODE + 6) {
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
                out.writeObject(vertices);
                out.writeObject(edges);
                out.writeObject(id);
                out.writeObject(failed);
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
            currentProject = nominatedPath;
            save(currentProject);
        }
    }

    private void justSave() {
        if (currentProject == null) {
            saveAs();
        } else {
            save(currentProject);
        }
    }

    private boolean checkForChange() {
        if (changesMade) {
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
            GraphifyGUI theGraph = new GraphifyGUI();
            theGraph.setLocationRelativeTo(null);
            theGraph.setExtendedState(MAXIMIZED_BOTH);
            theGraph.show();
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
