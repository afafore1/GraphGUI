/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphify;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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

/**
 *
 * @author Ayomitunde
 */
public class GraphifyGUI extends javax.swing.JFrame {

    HashMap<Integer, Integer> connectionCache = new HashMap<>();
    HashMap<Integer, Integer> glowMap;
    //private static HashMap<Integer, HashSet<Integer>> nodes = new HashMap();
    Queue<Integer> queue;
    Stack<Integer> stack;
    HashMap<Integer, Point> locations = new HashMap();
    HashMap<Integer, Integer> distTo;
    Map<Integer, Integer> set;
    HashMap<Integer, Integer> visited;
    HashMap<Integer, Integer> color;
    HashMap<Integer, Integer> fcolors;
    HashMap<Integer, Integer> greedyresult;
    HashSet<Integer> _colors2;
    HashSet<Integer> randomKeys;
    ArrayList<Integer> cutV;
    Color[] vertexColors;
    int _selectedNode = -1;
    final int ARR_SIZE = 4;
    int _SIZE_OF_NODE = 20;
    int id = 0;
    int Edgeid = 0;
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
        vertices = new HashMap<>();
        edges = new ArrayList<>();
        queue = alg.getQueue();
        stack = alg.getStack();
        cutV = alg.getCutV();
        color = alg.getColor();
        _colors2 = alg.getColors2();
        visited = alg.getVisited();
        greedyresult = alg.getGreedyResult();
        glowMap = alg.getGlowMap();
        set = alg.getSet();
        distTo = alg.distTo();
        vertexColors = new Color[]{Color.blue, Color.red, Color.yellow, Color.green, Color.magenta, Color.orange};
        randomKeys = new HashSet<Integer>();
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
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (checkForChange()) {
                    System.exit(0);
                }
            }
        });
    }

    public static HashMap getNode() {
        return GraphifyGUI.vertices;
    }
    
    public static ArrayList getEdges(){
        return GraphifyGUI.edges;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        pnlGraph = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        btnPrintList = new javax.swing.JButton();
        btnRandomize = new javax.swing.JButton();
        lblInfo = new java.awt.Label();
        lblResult = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtConsole = new javax.swing.JTextArea();
        btnClearConsole = new javax.swing.JButton();
        jcbAlgo = new javax.swing.JComboBox<>();
        btnStart = new javax.swing.JButton();
        txtQuery = new javax.swing.JTextField(20);
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
        btnReset.addActionListener(this::btnResetActionPerformed);

        btnPrintList.setText("Print List");
        btnPrintList.addActionListener(new ActionListenerImpl());

        btnRandomize.setText("Run Query");
        btnRandomize.addActionListener(this::btnRandomizeActionPerformed);

        lblInfo.setText("Source: None - Destination: None");

        txtConsole.setEditable(false);
        txtConsole.setColumns(20);
        txtConsole.setFont(new java.awt.Font("Courier New", 0, 16)); // NOI18N
        txtConsole.setRows(5);
        jScrollPane1.setViewportView(txtConsole);

        btnClearConsole.setText("Clear Console");
        btnClearConsole.addActionListener(this::btnClearConsoleActionPerformed);

        jcbAlgo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"BFS", "DFS", "Dijkstra", "Double Graph", "Vertex Cover", "Bipartite", "Cut", "GColoring", "isEulerian", "Connectedness"}));
        jcbAlgo.addActionListener(this::jcbAlgoActionPerformed);

        btnStart.setText("Start");
        btnStart.addActionListener(this::btnStartActionPerformed);
        txtQuery.setToolTipText("Enter Query");
        txtQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandExecute();
            }
        });
        mnuFile.setText("File");

        mnuNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        mnuNew.setText("New");
        mnuNew.addActionListener(this::mnuNewActionPerformed);
        mnuFile.add(mnuNew);

        mnuOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        mnuOpen.setText("Open");
        mnuOpen.addActionListener(this::mnuOpenActionPerformed);
        mnuFile.add(mnuOpen);

        mnuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mnuSave.setText("Save");
        mnuSave.addActionListener(this::mnuSaveActionPerformed);
        mnuFile.add(mnuSave);

        mnuSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuSaveAs.setText("Save as...");
        mnuSaveAs.addActionListener(this::mnuSaveAsActionPerformed);
        mnuFile.add(mnuSaveAs);

        mnuQuit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        mnuQuit.setText("Quit");
        mnuQuit.addActionListener(this::mnuQuitActionPerformed);
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
                                        .addComponent(txtQuery)
                                        .addComponent(btnRandomize)
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
                                .addComponent(txtQuery)
                                .addComponent(btnRandomize)
                                .addComponent(btnPrintList)
                                .addComponent(btnClearConsole)
                                .addComponent(jcbAlgo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnStart))
                        .addContainerGap())
        );

        pack();
    }// </editor-fold>  

    private void pnlGraphMousePressed(java.awt.event.MouseEvent evt) {
        _selectedNode = nodeSelected(evt.getX(), evt.getY());
        String[] types = new String[]{"Person", "City", "Place"};
        if (_selectedNode < 0 && SwingUtilities.isLeftMouseButton(evt)) {
            changesMade = true;
            //nodes.put(id, new HashSet());
            Vertex v = new Vertex(id, String.valueOf(id), types[(int) (Math.random() * types.length)], (int) (Math.random() * 50));
            vertices.put(v.getId(), v);
            locations.put(v.getId(), new Point(evt.getX(), evt.getY()));
            id++;
        } else if (SwingUtilities.isLeftMouseButton(evt)) {
        } else if (SwingUtilities.isRightMouseButton(evt)) {
            changesMade = true;
            glowMap.clear();
            greedyresult.clear();
            cutV.clear();
            _colors2.clear();
            _source = -1;
            _dest = -1;

            Vertex remove = vertices.get(_selectedNode);
            Iterator<Vertex> v = vertices.values().iterator();
            while (v.hasNext()) {
                Vertex vert = v.next();
                if (vert.vList().contains(remove)) {
                    vert.vList().remove(remove);
                }
            }
            vertices.remove(_selectedNode);
            locations.remove(_selectedNode);

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
    }

    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
                Graphics2D g = (Graphics2D) g1.create();
                double dx = x2 - x1, dy = y2 - y1;
                double angle = Math.atan2(dy, dx);
                int len = (int) Math.sqrt(dx*dx + dy*dy);
                AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
                at.concatenate(AffineTransform.getRotateInstance(angle));
                g.transform(at);

                // Draw horizontal arrow starting in (0, 0)
                g.drawLine(0, 0, len, 0);
                g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                              new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
            }
    private void pnlGraphMouseDragged(java.awt.event.MouseEvent evt) {
        if (_selectedNode >= 0) {
            if (SwingUtilities.isLeftMouseButton(evt)) {
                Image buff = createImage(pnlGraph.getWidth() - 1, pnlGraph.getHeight() - 1);
                Graphics buffG = buff.getGraphics();
                buffG.drawImage(bufferImage, 0, 0, this);
                Point source = locations.get(_selectedNode);
                //buffG.drawLine(source.x, source.y,
                        //evt.getX(), evt.getY());
                drawArrow(buffG, source.x, source.y, evt.getX(), evt.getY());
                pnlGraph.getGraphics().drawImage(buff, 1, 1, this);
                
            } else if (SwingUtilities.isMiddleMouseButton(evt)) {
                locations.get(_selectedNode).x = evt.getX();
                locations.get(_selectedNode).y = evt.getY();
                graph();
                changesMade = true;
            }
        }
    }

    private void pnlGraphComponentResized(java.awt.event.ComponentEvent evt) {
        bufferImage = createImage(pnlGraph.getWidth() - 2, pnlGraph.getHeight() - 2);
        bufferGraphic = (Graphics2D) bufferImage.getGraphics();
    }

    private void pnlGraphMouseReleased(java.awt.event.MouseEvent evt) {
        if (_selectedNode >= 0) {
            int destination = nodeSelected(evt.getX(), evt.getY());
            if (destination >= 0 && destination != _selectedNode) {
                addEdge(Edgeid, _selectedNode, destination, (int)(Math.random() * 100));
                _selectedNode = -1;
                changesMade = true;
                Edgeid++;
            }
        }
        graph();
    }

    private void addEdge(int edgeId, int sourceid, int destid, final int weight) {
        Edge newEdge = new Edge(edgeId, vertices.get(sourceid), vertices.get(destid), 0, weight);
        edges.add(newEdge);
        printlnConsole(newEdge.getId()+" "+newEdge.getSource().getName()+" "+newEdge.getDest().getName()+" "+newEdge.getWeight());
        vertices.get(sourceid).vList().add(vertices.get(destid));
        vertices.get(destid).vList().add(vertices.get(sourceid));
    }

    private void btnPrintListActionPerformed(java.awt.event.ActionEvent evt) {
        for (int i = 0; i < vertices.size(); i++) {
            printlnConsole(vertices.get(i) + "->" + vertices.get(i).vList());
        }
        printlnConsole("Source is: " + _source);
    }

    private void btnRandomizeActionPerformed(java.awt.event.ActionEvent evt) {
        commandExecute();
    }

    private void commandExecute() {
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
    }

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {
        changesMade = true;
        reset();
    }

    private void pnlGraphMouseClicked(java.awt.event.MouseEvent evt) {
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
            }
            graph();
        }
    }

    private void reset() {
        //nodes = new HashMap();
        vertices = new HashMap();
        locations = new HashMap();
        id = 0;
        cutV = new ArrayList<>();
        alg.getCutV().clear();
        _colors2 = new HashSet<>();
        alg.getColors2().clear();
        glowMap.clear();
        alg.getGlowMap().clear();
        greedyresult.clear();
        alg.getGreedyResult().clear();
        _source = -1;
        _dest = -1;
        graph();
    }

    private void btnClearConsoleActionPerformed(java.awt.event.ActionEvent evt) {
        txtConsole.setText("");
    }

    private void jcbAlgoActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {
        String x = String.valueOf(jcbAlgo.getSelectedItem());
        glowMap.clear();
        alg.getGlowMap().clear();
        txtConsole.setText("");
        visited.clear();
        alg.getVisited().clear();
        set.clear();
        alg.getSet().clear();
        greedyresult.clear();
        alg.getGreedyResult().clear();
        cutV.clear();
        alg.getCutV().clear();
        if (x.equals("DFS")) {
            txtConsole.setText("");
            if (_source == -1 || _dest == -1 || vertices.get(_source).vList().isEmpty() || vertices.get(_dest).vList().isEmpty()) {
                if (_source == -1) {
                    printlnConsole("Please choose a source by double clicking a node\nMake sure source has connections");
                } else {
                    printlnConsole("Please choose a destination by double clicking a node\nMake sure destination has connections");
                }
                return;
            }
            printlnConsole("Running Dfs...");
            alg.Dfs(vertices.get(_source));
            alg.shortestPath(_source, _dest);
        } else if (x == "BFS") {
            txtConsole.setText("");
            if (_source == -1 || _dest == -1 || vertices.get(_source).vList().isEmpty() || vertices.get(_dest).vList().isEmpty()) {
                if (_source == -1) {
                    printlnConsole("Please choose a source by double clicking a node\nMake sure source has connections");
                } else {
                    printlnConsole("Please choose a destination by double clicking a node\nMake sure destination has connections");
                }
                return;
            }
            alg.Bfs(vertices.get(_source));
            alg.shortestPath(_source, _dest);
        }else if(x == "Dijkstra"){
            txtConsole.setText("");
            if (_source == -1 || _dest == -1 || vertices.get(_source).vList().isEmpty() || vertices.get(_dest).vList().isEmpty()) {
                if (_source == -1) {
                    printlnConsole("Please choose a source by double clicking a node\nMake sure source has connections");
                } else {
                    printlnConsole("Please choose a destination by double clicking a node\nMake sure destination has connections");
                }
                return;
            }
            alg.execute(vertices.get(_source));
            alg.shortestPath(_source, _dest);
        }else if (x == "Cut") {
            _source = -1;
            _dest = -1;
            alg.AP();
            cutV = alg.getCutV();
            graph();
        } else if (x == "Connectedness") {
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
        } else if (x == "isEulerian") {
            txtConsole.setText("");
            if (_source == -1) {
                printlnConsole("Please select a source to begin");
                return;
            }
            if (alg.isEulerian()) {
                printlnConsole("There is an euler circuit");
            } else {
                printlnConsole("Euler circuit does not exist");
            }
        }

    }

    private void mnuNewActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkForChange()) {
            reset();
            currentProject = null;
            changesMade = false;
        }
    }

    private void mnuQuitActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkForChange()) {
            System.exit(0);
        }

    }

    private void mnuSaveActionPerformed(java.awt.event.ActionEvent evt) {
        justSave();
    }

    private void mnuSaveAsActionPerformed(java.awt.event.ActionEvent evt) {
        saveAs();
    }

    void getLoadInfo(File f, HashMap<Integer, Vertex> verts, HashMap<Integer, Point> locs) throws FileNotFoundException {
        String[] types = new String[]{"Person", "City", "Place"};
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            String c = sc.nextLine();
            String[] tokens = c.split(",");
            int key = Integer.parseInt(tokens[0]);
            Integer x = Integer.parseInt(tokens[1]); // locations x and y
            Integer y = Integer.parseInt(tokens[2]);
            Vertex v = new Vertex(key, "Vertex " + key, types[(int) (Math.random() * types.length)], (int) (Math.random() * 50));
            verts.put(key, v);
            locs.put(key, new Point(x, y));
        }
    }

    private void mnuOpenActionPerformed(java.awt.event.ActionEvent evt) { // needs to be changed to get correct info
        if (checkForChange()) {
            JFileChooser theChooser = new JFileChooser();
            theChooser.setFileFilter(new FileNameExtensionFilter("GraphifyGUI files", "sgf"));
            if (theChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    changesMade = false;
                    reset();
                    currentProject = theChooser.getSelectedFile().getPath();
                    File theFile = new File(currentProject);
                    Scanner scanner = new Scanner(theFile);
                    getLoadInfo(theFile, vertices, locations);
                    while (scanner.hasNext()) {
                        String currentLine = scanner.nextLine();
                        String[] tokens = currentLine.split(",");
                        int key = Integer.parseInt(tokens[0]); // vertex id
                        Vertex v = vertices.get(key); // get this vertex
                        for (int i = 3; i < tokens.length; i++) {
                            int e = Integer.parseInt(tokens[i]);
                            Vertex d = vertices.get(e);
                            v.vList().add(d);
                        }
                        id = key;
                    }
                    id++;
                    graph();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GraphifyGUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
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

        Iterator<Integer> locs = locations.keySet().iterator();
        while (locs.hasNext()) {
            int next = locs.next();
            int sourceKey = vertices.get(next).getId();
            Point thePoint = locations.get(next);
            for (Vertex destKey : vertices.get(next).vList()) {
                if (!(connectionCache.containsKey(sourceKey)
                        && connectionCache.get(sourceKey) == destKey.getId()
                        || connectionCache.containsKey(destKey.getId())
                        && connectionCache.get(destKey.getId()) == sourceKey)) {
                    Point destPoint = locations.get(destKey.getId());
                    bufferGraphic.drawLine(thePoint.x, thePoint.y, destPoint.x, destPoint.y);
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
        List keys = new ArrayList(locations.keySet());
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
                Integer k = vertices.get(i).getId();
                if (null != greedyresult.get(k)) switch (greedyresult.get(k)) {
                    case 0:
                        bufferGraphic.setColor(vertexColors[0]);
                        break;
                    case 1:
                        bufferGraphic.setColor(vertexColors[1]);
                        break;
                    case 2:
                        bufferGraphic.setColor(vertexColors[2]);
                        break;
                    case 3:
                        bufferGraphic.setColor(vertexColors[3]);
                        break;
                    case 4:
                        bufferGraphic.setColor(vertexColors[4]);
                        break;
                    case 5:
                        bufferGraphic.setColor(vertexColors[5]);
                        break;
                    default:
                        break;
                }
            }

            if(!cutV.isEmpty()){
                if (cutV.contains(keys.get(i))) {
                bufferGraphic.setColor(Color.green);
            }
            }
            

            bufferGraphic.fillOval(thePoint.x - _SIZE_OF_NODE / 2,
                    thePoint.y - _SIZE_OF_NODE / 2, _SIZE_OF_NODE, _SIZE_OF_NODE);
        }

        // Node labels.
        bufferGraphic.setColor(Color.blue);
        for (int i = 0; i < locations.size(); i++) {
            Point thePoint = (Point) locations.values().toArray()[i];
            bufferGraphic.drawString("V " + locations.keySet().toArray()[i],
                    thePoint.x - _SIZE_OF_NODE / 2,
                    thePoint.y - _SIZE_OF_NODE / 2);
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
        Iterator<Integer> loc = locations.keySet().iterator();
        while (loc.hasNext()) {
            int next = loc.next();
            Point thePoint = locations.get(next);
            int deltaX = x - (thePoint.x - _SIZE_OF_NODE / 2);
            int deltaY = y - (thePoint.y - _SIZE_OF_NODE / 2);
            if (Math.sqrt(deltaX * deltaX
                    + deltaY * deltaY) <= _SIZE_OF_NODE + 6) {
                return next;
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

    private String getSaveString() {
        String result = "";
        for (int i = 0; i < vertices.size(); i++) {
            Vertex key = vertices.get(i); // get the vertex
            int keyId = key.getId();
            result += keyId + ",";
            result += locations.get(key.getId()).x + "," + locations.get(key.getId()).y; // get locations
            Iterator<Vertex> v = key.vList().iterator();
            while (v.hasNext()) {
                Vertex n = v.next();
                result += "," + n.getId();
            }
            result += "\n";
        }
        return result;
    }

//    private void randomize(int max) { // I don't really like this... Seem quite messy
//        String result = "";
//        int Ncon = 12;
//        // create connections for each nodes
//        for (int i = 0; i < max; i++) {
//            HashSet<Integer> st = new HashSet<>();
//            while (st.size() < (int) (Math.random() * Ncon)) {
//                int con = (int) (Math.random() * max);
//                if (con != i) {
//                    st.add(con);
//                }
//            }
//            nodes.put(i, st);
//        }
//
//        // add connections for nodes equally. Undirected graph i.e. if node a consist node b, the same must happen the other way
//        for (int i = 0; i < nodes.size(); i++) {
//            Iterator<Integer> t = alg.getEdge(i).iterator();
//            while (t.hasNext()) {
//                int nextNum = t.next();
//                if (nodes.get(nextNum) != null) {
//                    if (!(nodes.get(nextNum).contains(i))) {
//                        HashSet<Integer> tList = nodes.get(nextNum);
//                        tList.add(i);
//                        nodes.put(nextNum, tList);
//                    }
//                }
//
//            }
//        }
//        HashMap<Integer, Integer> nums = new HashMap<>();
//        for (int i = 0; i < nodes.size(); i++) {
//            int t = (int) (Math.random() * 925 + 20);
//            int s = (int) (Math.random() * 325 + 20);
//            int x = 0;
//            int y = 0;
//            if(nums.containsKey(t) || nums.containsValue(s)){
//                i--;
//            }else{
//                nums.put(t, s);
//            x = (int) (2 * t);
//            y = (int) (2 * s);
//            result += i + "," + x + "," + y + "," + nodes.get(i).toString().replace("[", "").replace("]", "").replaceAll(" ", "") + "\n";
//            }            
//            
//        }
//
//        Scanner scanner = new Scanner(result);
//        while (scanner.hasNext()) {
//            String currentLine = scanner.nextLine();
//            String[] tokens = currentLine.split(",");
//            Integer key = Integer.parseInt(tokens[0]);
//            Integer x = Integer.parseInt(tokens[1]);
//            Integer y = Integer.parseInt(tokens[2]);
//            HashSet<Integer> connections = new HashSet();
//            for (int i = 3; i < tokens.length; i++) {
//                connections.add(Integer.parseInt(tokens[i]));
//            }
//            nodes.put(key, connections);
//            locations.put(key, new Point(x, y));
//            id = key;
//        }
//        id++;
//        graph();
//        //return result;
//    }
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
            Logger.getLogger(GraphifyGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveAs() {
        JFileChooser theChooser = new JFileChooser();
        theChooser.setFileFilter(new FileNameExtensionFilter("GraphifyGUI files", "sgf"));
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
            java.util.logging.Logger.getLogger(GraphifyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GraphifyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GraphifyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GraphifyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            GraphifyGUI theGraph = new GraphifyGUI();
            theGraph.setLocationRelativeTo(null);
            theGraph.show();
        });
    }

    private javax.swing.JButton btnClearConsole;
    private javax.swing.JButton btnPrintList;
    private javax.swing.JButton btnRandomize;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnStart;
    private javax.swing.JTextField txtQuery;
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
    // End of variables declaration             

    private class ActionListenerImpl implements ActionListener {

        public ActionListenerImpl() {
        }

        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnPrintListActionPerformed(evt);
        }
    }

}
