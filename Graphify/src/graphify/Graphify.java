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
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Graphify extends javax.swing.JFrame {

    HashMap<Integer, Integer> connectionCache = new HashMap<>();
    HashMap<Integer, Integer> glowMap = new HashMap<>();
    HashMap<Integer, HashSet<Integer>> nodes = new HashMap();
    private Queue<Integer> queue;
    private Stack<Integer> stack;
    HashMap<Integer, Point> locations = new HashMap();
    private HashMap<Integer, Integer> distTo;
    private Map<Integer, Integer> set = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> visited;
    HashMap<Integer, Integer> color;
    HashMap<Integer, Integer> fcolors;
    HashMap<Integer, Integer> greedyresult;
    HashSet<Integer> _colors2;
    ArrayList<Integer> conn;
    ArrayList<Integer> bconn;
    ArrayList<Integer> cutV;
    Color[] vertexColors;
    int _selectedNode = -1;
    int _SIZE_OF_NODE = 20;
    int id = 0;
    int time = 0;
    Integer maxColors = 0;
    static int _source = -1;
    static int _dest = -1;
    Image bufferImage;
    Graphics2D bufferGraphic;
    String currentProject = null;
    boolean changesMade = false;
    double dotOffset = 0.0;

    public Graphify() {
        initComponents();
        bufferImage = createImage(pnlGraph.getWidth() - 2, pnlGraph.getHeight() - 2);
        bufferGraphic = (Graphics2D) bufferImage.getGraphics();
        queue = new LinkedList<Integer>();
        stack = new Stack<Integer>();
        cutV = new ArrayList<Integer>();
        _colors2 = new HashSet<Integer>();
        visited = new HashMap<Integer, Integer>();
        Timer animationTimer = new Timer(30, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (glowMap.size() > 0) {
                    dotOffset = (dotOffset + .07) % 1;
                    graph();
                }
            }
        });
        animationTimer.start();
        greedyresult = new HashMap<Integer, Integer>();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (checkForChange()) {
                    System.exit(0);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlGraph = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        btnPrintList = new javax.swing.JButton();
        lblInfo = new java.awt.Label();
        lblResult = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtConsole = new javax.swing.JTextArea();
        btnClearConsole = new javax.swing.JButton();
        jcbAlgo = new javax.swing.JComboBox<>();
        btnStart = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuNew = new javax.swing.JMenuItem();
        mnuOpen = new javax.swing.JMenuItem();
        mnuSave = new javax.swing.JMenuItem();
        mnuSaveAs = new javax.swing.JMenuItem();
        mnuQuit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

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
            .addGap(0, 282, Short.MAX_VALUE)
        );

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnPrintList.setText("Print List");
        btnPrintList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintListActionPerformed(evt);
            }
        });

        lblInfo.setText("Source: None - Destination: None");

        txtConsole.setEditable(false);
        txtConsole.setColumns(20);
        txtConsole.setFont(new java.awt.Font("Courier New", 0, 16)); // NOI18N
        txtConsole.setRows(5);
        jScrollPane1.setViewportView(txtConsole);

        btnClearConsole.setText("Clear Console");
        btnClearConsole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearConsoleActionPerformed(evt);
            }
        });

        jcbAlgo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BFS", "DFS", "Bipartite", "Cut", "GColoring", "isEulerian", "Connectedness" }));
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
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClearConsole)
                        .addGap(44, 44, 44)
                        .addComponent(jcbAlgo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 315, Short.MAX_VALUE)
                        .addComponent(btnPrintList))
                    .addComponent(pnlGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblResult))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset)
                    .addComponent(btnPrintList)
                    .addComponent(btnClearConsole)
                    .addComponent(jcbAlgo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStart))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlGraphMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMousePressed
        _selectedNode = nodeSelected(evt.getX(), evt.getY());
        if (_selectedNode < 0 && SwingUtilities.isLeftMouseButton(evt)) {
            changesMade = true;
            nodes.put(id, new HashSet());
            locations.put(id++, new Point(evt.getX(), evt.getY()));
        } else if (SwingUtilities.isLeftMouseButton(evt)) {
        } else if (SwingUtilities.isRightMouseButton(evt)) {
            changesMade = true;
            glowMap.clear();
            greedyresult.clear();
            cutV.clear();
            _colors2.clear();
            _source = -1;
            _dest = -1;
            nodes.remove(_selectedNode);
            locations.remove(_selectedNode);

            for (HashSet<Integer> connections : nodes.values()) {
                for (int j = 0; j < connections.size(); j++) {
                    Integer connection = (Integer) connections.toArray()[j];
                    if (connection == _selectedNode) {
                        connections.remove(connection);
                        j--;
                    }
                }
            }
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

    private void pnlGraphMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMouseDragged
        if (_selectedNode >= 0) {
            if (SwingUtilities.isLeftMouseButton(evt)) {
                Image buff = createImage(pnlGraph.getWidth() - 1, pnlGraph.getHeight() - 1);
                Graphics buffG = buff.getGraphics();
                buffG.drawImage(bufferImage, 0, 0, this);
                Point source = locations.get(_selectedNode);
                buffG.drawLine(source.x, source.y,
                        evt.getX(), evt.getY());
                pnlGraph.getGraphics().drawImage(buff, 1, 1, this);
            } else if (SwingUtilities.isMiddleMouseButton(evt)) {
                locations.get(_selectedNode).x = evt.getX();
                locations.get(_selectedNode).y = evt.getY();
                graph();
                changesMade = true;
            }
        }
    }//GEN-LAST:event_pnlGraphMouseDragged

    private void pnlGraphComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnlGraphComponentResized
        bufferImage = createImage(pnlGraph.getWidth() - 2, pnlGraph.getHeight() - 2);
        bufferGraphic = (Graphics2D) bufferImage.getGraphics();
    }//GEN-LAST:event_pnlGraphComponentResized

    private void pnlGraphMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMouseReleased
        if (_selectedNode >= 0) {
            int destination = nodeSelected(evt.getX(), evt.getY());
            if (destination >= 0 && destination != _selectedNode) {
                nodes.get(_selectedNode).add(destination);
                nodes.get(destination).add(_selectedNode);
                _selectedNode = -1;
                changesMade = true;
            }
        }
        graph();
    }//GEN-LAST:event_pnlGraphMouseReleased

    public HashSet<Integer> getEdge(int source) {
        return nodes.get(source);
    }

    void APF(int u, HashMap<Integer, Integer> visited, HashMap<Integer, Integer> disc, HashMap<Integer, Integer> low, HashMap<Integer, Integer> parent, HashMap<Integer, Integer> ap) {
        int children = 0;
        visited.put(u, 0);
        time = ++time;
        disc.put(u, time);
        low.put(u, time);
        Iterator<Integer> i = getEdge(u).iterator();

        while (i.hasNext()) {
            int v = i.next(); // v is current adj to u
            if (visited.get(v) == -1) {
                children++;
                parent.put(v, u);
                APF(v, visited, disc, low, parent, ap); // recursive for it
                int val = Math.min(low.get(u), low.get(v));
                low.put(u, val);

                if (u == _source && children > 1) {
                    ap.put(u, 1);
                }
                // if u is not root and low value of one of its child is more than discovery value of u
                if (u != _source && low.get(v) >= disc.get(u)) { // need a check for this if statement.. always marks beginning as a cut even when it's not
                    ap.put(u, 1);
                }
            } else if (v != parent.get(u)) {
                int val = Math.min(low.get(u), low.get(v));
                low.put(u, val);
            }
        }
    }

    void AP() {
        int V = nodes.size();
        visited = new HashMap<>();
        HashMap<Integer, Integer> disc = new HashMap<>();
        HashMap<Integer, Integer> low = new HashMap<>();
        HashMap<Integer, Integer> parent = new HashMap<>();
        HashMap<Integer, Integer> ap = new HashMap<>();

        for (int i = 0; i < V; i++) {
            Integer key = (Integer) nodes.keySet().toArray()[i];
            parent.put(key, -1);
            visited.put(key, -1);
            ap.put(key, 0);
        }

        for (int i = 0; i < V; i++) {
            Integer key = (Integer) nodes.keySet().toArray()[i];
            if (visited.get(key) == -1) {
                APF(key, visited, disc, low, parent, ap);
            }
        }

        for (int i = 0; i < V; i++) {
            Integer key = (Integer) nodes.keySet().toArray()[i];
            if (ap.get(key) == 1) {
                printlnConsole(key + " is a cut vertex");
                cutV.add(key);
            }
        }
    }

    void dfs(int source) {
        int V = nodes.size();
        distTo = new HashMap<>();
        visited = new HashMap<>();
        boolean ap[] = new boolean[V];
        for (int i = 0; i < V; i++) {
            Integer key = (Integer) nodes.keySet().toArray()[i];
            visited.put(key, -1);
            distTo.put(key, 0);
        }
        bconn = new ArrayList<Integer>();
        int element;
        visited.put(source, 0); // start vertex
        stack.push(source);
        while (!stack.isEmpty()) {
            element = stack.peek();
            printlnConsole("Considering element " + element);
            if (!bconn.contains(element)) {
                bconn.add(element);
            }
            HashSet<Integer> iList = getEdge(element);
            int x = 0;
            while (x < iList.size()) {
                Integer key = (Integer) iList.toArray()[x];
                if (visited.get(key) == -1) {
                    printlnConsole("Pushing " + (Integer) iList.toArray()[x]);
                    stack.push((Integer) iList.toArray()[x]);
                    visited.put(key, element);
                    distTo.put(key, distTo.get(element) + 1);
                    break;
                }
                x++;
                if (x == iList.size()) {
                    int backEdge = stack.pop();
                    printlnConsole("Back edge " + backEdge);
                }
            }
        }
        printlnConsole("order is " + bconn);
    }

    void bfs(int source) {
        int V = nodes.size();
        distTo = new HashMap<>();
        visited = new HashMap<>();
        for (int i = 0; i < V; i++) {
            Integer key = (Integer) nodes.keySet().toArray()[i];
            visited.put(key, -1);
            distTo.put(key, 0);
        }
        conn = new ArrayList<Integer>();
        int i, element;
        visited.put(source, 0);
        queue.add(source);
        while (!queue.isEmpty()) {
            element = queue.remove();
            printlnConsole(element + " removed");
            i = element; // what is the point of i = element here ?
            conn.add(element);
            HashSet<Integer> iList = getEdge(i);
            int x = 0;
            while (x < iList.size()) {
                Integer key = (Integer) iList.toArray()[x];
                if (visited.get(key) == -1) {
                    queue.add((Integer) iList.toArray()[x]);
                    visited.put(key, i);
                    distTo.put(key, distTo.get(i) + 1);
                }
                x++;
            }
        }
        printlnConsole("Order is " + conn);
    }

    boolean isConnected() {
        dfs(_source);
        for (int i = 0; i < nodes.size(); i++) {
            Integer key = (Integer) nodes.keySet().toArray()[i];
            if (visited.get(key) == -1) {
                return false;
            }
        }
        return true;
    }

    boolean isEulerian() {
        int noOfOdds = 0;
        if (isConnected()) {
            for (int i = 0; i < nodes.size(); i++) {
                Integer key = (Integer) nodes.keySet().toArray()[i];
                int keyEdgeSize = getEdge(key).size();
                if (keyEdgeSize % 2 != 0) {
                    noOfOdds++;
                }
            }
        } else {
            return false;
        }
        if (noOfOdds == 2) {
            return true;
        }
        return false;
    }

    void greedyColoring(int nc) {
        int V = nodes.size();
        greedyresult = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> available = new HashMap<Integer, Integer>();
        vertexColors = new Color[]{Color.blue, Color.red, Color.yellow, Color.green, Color.magenta, Color.orange};
        for (int i = 0; i < V; i++) {
            Integer key = (Integer) nodes.keySet().toArray()[i];
            greedyresult.put(key, -1);
            available.put(key, 0); //set all to false
        }
        greedyresult.put(_source, 0);
        for (int x = 0; x < V; x++) {
            Integer key = (Integer) nodes.keySet().toArray()[x];
            HashSet<Integer> kList = getEdge(key);
            int u = 0;
            while (u < kList.size()) {
                Integer k = (Integer) kList.toArray()[u];
                if (greedyresult.get(k) != -1) {
                    available.put(greedyresult.get(k), 1);
                }
                u++;
            }
            // find first avail color
            Integer nColor = 0;
            for (int i = 0; i < V; i++) {
                nColor = (Integer) nodes.keySet().toArray()[i];
                if (available.get(nColor) == 0) {
                    break;
                }
            }
            greedyresult.put(key, nColor);
            if (greedyresult.get(key) > maxColors) {
                maxColors = nColor;
            }
            u = 0;
            while (u < kList.size()) {
                Integer k = (Integer) kList.toArray()[u];
                if (greedyresult.get(k) != -1) {
                    available.put(greedyresult.get(k), 0);
                }
                u++;
            }
        }

        for (int i = 0; i < V; i++) {
            Integer key = (Integer) nodes.keySet().toArray()[i];
            printlnConsole("Vertex " + key + " ---> Color " + greedyresult.get(key));
        }

    }

    void Bipartite(int source) { // will test for 3
        int V = nodes.size();
        color = new HashMap<Integer, Integer>();
        _colors2 = new HashSet<Integer>();
        for (int i = 0; i < V; i++) {
            Integer key = (Integer) nodes.keySet().toArray()[i];
            color.put(key, -1);
        }
        int element;
        color.put(source, 1); // start first color with 1, all adjacent to 1 should have color 0
        queue.add(source);
        while (!queue.isEmpty()) {
            element = queue.remove();
            HashSet<Integer> iList = getEdge(element);
            int x = 0;
            while (x < iList.size()) {
                Integer key = (Integer) iList.toArray()[x];
                if (color.get(element) == color.get(key)) {
                    _colors2.add(element);
                    _colors2.add(key);
                } else if (color.get(key) == -1) {
                    color.put(key, 1 - color.get(element));
                    queue.add(key);
                }
                x++;
            }
        }
        if (_colors2.size() > 1) {
            printlnConsole("Graph is not bipartite at " + _colors2.toString());
        } else {
            printlnConsole("Graph is bipartite");
        }

    }

    public int hasPath(int v) {
        return visited.get(v);
    }

    public int distTo(int v) {
        return distTo.get(v);
    }

    public void shortestPath(int v, int e) {
        if (e == v) {
            printlnConsole(v + "-->" + v);
            return;
        }
        for (int i = e; i >= 0; i = visited.get(i)) {
            if (i == v) {
                break;
            }
            if (visited.get(i) != -1) {
                set.put(visited.get(i), i);
            }
        }
        // removed rset
        printlnConsole(set.toString().replaceAll("=", "-->"));
        glowMap.clear();
        for (int i : set.keySet()) {
            glowMap.put(i, set.get(i));
        }
        graph();
    }
    //[0, 2, 19, 5, 7, 9, 14]

    private void reset() {
        nodes = new HashMap();
        locations = new HashMap();
        id = 0;
        cutV = new ArrayList<Integer>();
        _colors2 = new HashSet<Integer>();
        glowMap.clear();
        greedyresult.clear();
        _source = -1;
        _dest = -1;
        graph();
    }

    private void btnPrintListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintListActionPerformed
        for (int i = 0; i < nodes.size(); i++) {
            int key = (Integer) nodes.keySet().toArray()[i];
            printlnConsole(key + "->" + getEdge(key));
        }
        printlnConsole("Source is: " + _source);
    }//GEN-LAST:event_btnPrintListActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        changesMade = true;
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

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
                // Implement path finding here.
                set.clear();
                //bfs(_source);
                //shortestPath(_source, _dest);
                //dfs(_source);
                //cutV = new ArrayList<Integer>();
                //AP();
                //lSC(3);
            }
            graph();
        }
    }//GEN-LAST:event_pnlGraphMouseClicked

    private void btnClearConsoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearConsoleActionPerformed
        txtConsole.setText("");
    }//GEN-LAST:event_btnClearConsoleActionPerformed

    private void jcbAlgoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAlgoActionPerformed

    }//GEN-LAST:event_jcbAlgoActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        String x = String.valueOf(jcbAlgo.getSelectedItem());
        glowMap.clear();
        txtConsole.setText("");
        visited.clear();
        set.clear();
        greedyresult.clear();
        if (x == "Bipartite") {
            glowMap.clear();
            txtConsole.setText("");
            if (_source == -1) {
                printlnConsole("Please choose a source by double clicking a node");
                return;
            }
            Bipartite(_source);
        } else if (x == "DFS") {
            txtConsole.setText("");
            if (_source == -1) {
                printlnConsole("Please choose a source by double clicking a node");
                return;
            }
            dfs(_source);
            shortestPath(_source, _dest);
        } else if (x == "BFS") {
            txtConsole.setText("");
            if (_source == -1 || _dest == -1) {
                if (_source == -1) {
                    printlnConsole("Please choose a source by double clicking a node");
                } else {
                    printlnConsole("Please choose a destination by double clicking a node");
                }
                return;
            }
            bfs(_source);
            shortestPath(_source, _dest);
        } else if (x == "Cut") {
            cutV = new ArrayList<Integer>();
            AP();
            graph();
        } else if (x == "GColoring") {
            glowMap.clear();
            cutV.clear();
            if (_source == -1) {
                printlnConsole("Please select a source to begin ");
                return;
            }
            greedyColoring(2);
            graph();
            greedyresult.clear();
        } else if (x == "Connectedness") {
            txtConsole.setText("");
            if (_source == -1) {
                printlnConsole("Please select a source to begin");
                return;
            }
            if (isConnected()) {
                printlnConsole("Graph is Connected");
            } else {
                printlnConsole("Graph is a disconnected Graph");
            }
        } else if (x == "isEulerian") {
            txtConsole.setText("");
            if (_source == -1) {
                printlnConsole("Please select a source to begin");
                return;
            }
            if (isEulerian()) {
                printlnConsole("There is an eulerian cycle");
            } else {
                printlnConsole("Eulerian cycle does not exist");
            }
        }

    }//GEN-LAST:event_btnStartActionPerformed

    private void mnuNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewActionPerformed
        if (checkForChange()) {
            reset();
            currentProject = null;
            changesMade = false;
        }
    }//GEN-LAST:event_mnuNewActionPerformed

    private void mnuQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuQuitActionPerformed
        if (checkForChange()) {
            System.exit(0);
        }

    }//GEN-LAST:event_mnuQuitActionPerformed

    private void mnuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSaveActionPerformed
        justSave();
    }//GEN-LAST:event_mnuSaveActionPerformed

    private void mnuSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSaveAsActionPerformed
        saveAs();
    }//GEN-LAST:event_mnuSaveAsActionPerformed

    private void mnuOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuOpenActionPerformed
        if (checkForChange()) {
            JFileChooser theChooser = new JFileChooser();
            theChooser.setFileFilter(new FileNameExtensionFilter("Graphify files", "sgf"));
            if (theChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    changesMade = false;
                    reset();
                    currentProject = theChooser.getSelectedFile().getPath();
                    File theFile = new File(currentProject);
                    Scanner scanner = new Scanner(theFile);
                    while (scanner.hasNext()) {
                        String currentLine = scanner.nextLine();
                        String[] tokens = currentLine.split(",");
                        Integer key = Integer.parseInt(tokens[0]);
                        Integer x = Integer.parseInt(tokens[1]);
                        Integer y = Integer.parseInt(tokens[2]);
                        HashSet<Integer> connections = new HashSet();
                        for (int i = 3; i < tokens.length; i++) {
                            connections.add(Integer.parseInt(tokens[i]));
                        }
                        nodes.put(key, connections);
                        locations.put(key, new Point(x, y));
                        id = key;
                    }
                    id++;
                    graph();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Graphify.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }//GEN-LAST:event_mnuOpenActionPerformed

    private String getNodeInfo(int nodeId) {
        if (nodeId == -1) {
            return "None";
        }
        return "" + nodeId;
    }

    private void graph() {
        bufferGraphic.setColor(Color.white);
        bufferGraphic.fillRect(0, 0, pnlGraph.getWidth(), pnlGraph.getHeight());
        connectionCache.clear();

        // Regular connections
        bufferGraphic.setColor(Color.black);
        bufferGraphic.setStroke(new BasicStroke(2));
        for (int i = 0; i < locations.size(); i++) {
            Integer sourceKey = (Integer) nodes.keySet().toArray()[i];
            Point thePoint = (Point) locations.values().toArray()[i];
            for (Integer destinationKey
                    : (HashSet<Integer>) nodes.values().toArray()[i]) {
                if (!(connectionCache.containsKey(sourceKey)
                        && connectionCache.get(sourceKey) == destinationKey
                        || connectionCache.containsKey(destinationKey)
                        && connectionCache.get(destinationKey) == sourceKey)) {
                    Point destinantionPoint = locations.get(destinationKey);
                    bufferGraphic.drawLine(thePoint.x, thePoint.y,
                            destinantionPoint.x, destinantionPoint.y);
                    connectionCache.put(sourceKey, destinationKey);
                }
            }
        }

        // Glowing connections
        bufferGraphic.setColor(new Color(10, 230, 40));
        bufferGraphic.setStroke(new BasicStroke(8));
        if (!btnReset.isSelected()) {
            for (int sourceKey : glowMap.keySet()) {
                int destKey = glowMap.get(sourceKey);
                Point sourcePoint = (Point) locations.get(sourceKey);
                Point destPoint = (Point) locations.get(destKey);
                drawDottedLine(bufferGraphic, sourcePoint, destPoint, dotOffset);
            }
        }

        // Nodes - red circles.
        for (int i = 0; i < locations.size(); i++) {
            Point thePoint = (Point) locations.values().toArray()[i];

            if (locations.keySet().toArray()[i].equals((Integer) _source)) {
                bufferGraphic.setColor(Color.green);
            } else if (locations.keySet().toArray()[i].equals((Integer) _dest)) {
                bufferGraphic.setColor(Color.blue);
            } else if (locations.keySet().toArray()[i].equals((Integer) _selectedNode)) {
                bufferGraphic.setColor(Color.orange);
            } else {
                bufferGraphic.setColor(Color.red);
            }
            if (greedyresult.size() > 0) {
                Integer k = (Integer) nodes.keySet().toArray()[i];
                if (greedyresult.get(k) == 0) {
                    bufferGraphic.setColor(vertexColors[0]);
                } else if (greedyresult.get(k) == 1) {
                    bufferGraphic.setColor(vertexColors[1]);
                } else if (greedyresult.get(k) == 2) {
                    bufferGraphic.setColor(vertexColors[2]);
                } else if (greedyresult.get(k) == 3) {
                    bufferGraphic.setColor(vertexColors[3]);
                } else if (greedyresult.get(k) == 4) {
                    bufferGraphic.setColor(vertexColors[4]);
                } else if (greedyresult.get(k) == 5) {
                    bufferGraphic.setColor(vertexColors[5]);
                }
            }

            if (cutV.contains(locations.keySet().toArray()[i])) {
                bufferGraphic.setColor(Color.gray);
            }

            bufferGraphic.fillOval(thePoint.x - _SIZE_OF_NODE / 2,
                    thePoint.y - _SIZE_OF_NODE / 2, _SIZE_OF_NODE, _SIZE_OF_NODE);
        }

        // Node labels.
        bufferGraphic.setColor(Color.blue);
        for (int i = 0; i < locations.size(); i++) {
            Point thePoint = (Point) locations.values().toArray()[i];
            bufferGraphic.drawString("V " + locations.keySet().toArray()[i],
                    thePoint.x + _SIZE_OF_NODE + 4, thePoint.y + _SIZE_OF_NODE + 4);
        }
        pnlGraph.getGraphics().drawImage(bufferImage, 1, 1, this);
        lblInfo.setText("Source: " + getNodeInfo(_source)
                + " - Destination: " + getNodeInfo(_dest));
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
        for (int i = 0; i < locations.size(); i++) {
            Point thePoint = (Point) locations.values().toArray()[i];
            int deltaX = x - (thePoint.x - _SIZE_OF_NODE / 2);
            int deltaY = y - (thePoint.y - _SIZE_OF_NODE / 2);
            if (Math.sqrt(deltaX * deltaX
                    + deltaY * deltaY) <= _SIZE_OF_NODE + 6) {
                return (int) locations.keySet().toArray()[i];
            }
        }
        return -1;
    }

    private void printConsole(String string) {
        txtConsole.append(string);
    }

    private void printlnConsole(String string) {
        txtConsole.append(string + "\n");
    }

    private String getSaveString() {
        String result = "";
        for (int i = 0; i < nodes.size(); i++) {
            int key = (int) nodes.keySet().toArray()[i];
            result += key + ",";
            result += locations.get(key).x + "," + locations.get(key).y;
            for (Integer connection : nodes.get(key)) {
                result += "," + connection;
            }
            result += "\n";
        }
        return result;
    }

    private void save(String path) {
        try {
            File fileToSave = new File(path);
            FileWriter writer;
            writer = new FileWriter(fileToSave);
            String stringToWrite = getSaveString();
            writer.write(stringToWrite);
            changesMade = false;
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Graphify.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveAs() {
        JFileChooser theChooser = new JFileChooser();
        theChooser.setFileFilter(new FileNameExtensionFilter("Graphify files", "sgf"));
        if (theChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String nominatedPath = theChooser.getSelectedFile().getPath();
            if (!nominatedPath.endsWith(".sgf")) {
                nominatedPath += ".sgf";
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
            if (option == JOptionPane.YES_OPTION) {
                justSave();
                return true;
            } else if (option == JOptionPane.CANCEL_OPTION) {
                return false;
            } else if (option == JOptionPane.NO_OPTION) {
                return true;
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Graphify.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Graphify.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Graphify.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Graphify.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Graphify theGraph = new Graphify();
                theGraph.setLocationRelativeTo(null);
                theGraph.show();
            }
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
    private javax.swing.JLabel lblResult;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenuItem mnuNew;
    private javax.swing.JMenuItem mnuOpen;
    private javax.swing.JMenuItem mnuQuit;
    private javax.swing.JMenuItem mnuSave;
    private javax.swing.JMenuItem mnuSaveAs;
    private javax.swing.JPanel pnlGraph;
    private javax.swing.JTextArea txtConsole;
    // End of variables declaration//GEN-END:variables
}
