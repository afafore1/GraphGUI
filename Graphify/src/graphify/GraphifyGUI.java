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
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.RenderedImage;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Ayomitunde
 */
public class GraphifyGUI extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    Image backgroundImage;
    Image bufferImage;
    Graphics2D bufferGraphic;
    private ActionListener decreseWeights;
    private JToggleButton[] tools = new JToggleButton[3];
    static boolean isWeighted = false;
    static boolean isComplete = false;

    public GraphifyGUI() {
            initComponents();
            bufferImage = createImage(pnlGraph.getWidth() - 2, pnlGraph.getHeight() - 2);
            bufferGraphic = (Graphics2D) bufferImage.getGraphics();
            hider();
            Model.vertices = new HashMap<>();
            Model.edges = new ArrayList<>();
            Model.failed = new ArrayList<>();
            Model.vertexColors = new Color[]{Color.blue, Color.red, Color.yellow, Color.green, Color.magenta, Color.orange};
            Model.randomKeys = new HashSet<>();
            Model.glowMap = new HashMap<>();
            Model.cutV = new ArrayList<>();
            Model.set = new HashMap<>();
            Model.visited = new HashMap<>();
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

            for (int i = 0; i < tools.length; i++) {
                tools[i] = new JToggleButton();
                tools[i].setFocusable(false);
                tools[i].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                tools[i].setPreferredSize(new java.awt.Dimension(40, 40));
                tools[i].setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                tools[i].addActionListener((ActionEvent e) -> {
                    for (JToggleButton tool : tools) {
                        if (tool != e.getSource()) {
                            tool.setSelected(false);
                        }
                    }
                });
                jToolBar1.add(tools[i]);
            }
            tools[0].setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphify/images/vertex.png")));
            tools[0].setToolTipText("Place Vertex");
            tools[1].setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphify/images/bidirectional.png")));
            tools[1].setToolTipText("Bidirectonal");
            tools[2].setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphify/images/directional.png")));
            tools[2].setToolTipText("Directional");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnReset = new javax.swing.JButton();
        lblInfo = new java.awt.Label();
        btnStart = new javax.swing.JButton();
        btnPrintList = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        pnlGraph = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                graph();
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, null);
            }
        };
        jScrollPane2 = new javax.swing.JScrollPane();
        txtConsole = new javax.swing.JTextArea();
        jToolBar1 = new javax.swing.JToolBar();
        sldrWeightSpeed = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        lblCapacity = new javax.swing.JLabel();
        lblCapTransferred = new javax.swing.JLabel();
        lblInitalDistance = new javax.swing.JLabel();
        lblFinalDistance = new javax.swing.JLabel();
        lblInitalDistValue = new javax.swing.JLabel();
        lblFinalDistValue = new javax.swing.JLabel();
        btnGenResult = new javax.swing.JButton();
        jcbAlgo = new javax.swing.JComboBox<>();
        btnClearConsole = new javax.swing.JButton();
        rdnFailSim = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        txtVertexLookUp = new javax.swing.JTextField();
        btnFindVertex = new javax.swing.JButton();
        lblNoIterations = new javax.swing.JLabel();
        txtIterNum = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lblTimeTaken = new javax.swing.JLabel();
        lblTemperature = new javax.swing.JLabel();
        txtTemperature = new javax.swing.JTextField();
        lblCoolingRate = new javax.swing.JLabel();
        txtCoolingRate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lblIterationNum = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0));
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuNew = new javax.swing.JMenuItem();
        mnuOpen = new javax.swing.JMenuItem();
        mnuSave = new javax.swing.JMenuItem();
        mnuSaveAs = new javax.swing.JMenuItem();
        mnuQuit = new javax.swing.JMenuItem();
        mnuEdit = new javax.swing.JMenu();
        mnuUpdateWeight = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        chkComplete = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        lblInfo.setText("Source: None - Destination: None");

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

        jSplitPane1.setDividerSize(3);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.7);

        pnlGraph.setBackground(new java.awt.Color(255, 255, 255));
        pnlGraph.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlGraph.setPreferredSize(new java.awt.Dimension(400, 500));
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
        pnlGraph.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pnlGraphKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnlGraphLayout = new javax.swing.GroupLayout(pnlGraph);
        pnlGraph.setLayout(pnlGraphLayout);
        pnlGraphLayout.setHorizontalGroup(
            pnlGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1089, Short.MAX_VALUE)
        );
        pnlGraphLayout.setVerticalGroup(
            pnlGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 496, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(pnlGraph);

        txtConsole.setEditable(false);
        txtConsole.setColumns(20);
        txtConsole.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtConsole.setRows(5);
        jScrollPane2.setViewportView(txtConsole);

        jSplitPane1.setRightComponent(jScrollPane2);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);

        sldrWeightSpeed.setMaximum(950);
        sldrWeightSpeed.setValue(500);

        jLabel1.setText("Cost Change Speed");

        lblCapacity.setText("Capacity Transferred");

        lblCapTransferred.setText("0");

        lblInitalDistance.setText("Initial Distance");

        lblFinalDistance.setText("Final Distance");

        lblInitalDistValue.setText("0");

        lblFinalDistValue.setText("0");

        btnGenResult.setText("Generate Result");
        btnGenResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenResultActionPerformed(evt);
            }
        });

        jcbAlgo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BFS", "OtherBfs", "DFS", "Dijkstra", "Connectedness", "TSP-SA", "TSP-GA", "Nearest Neighbor" }));
        jcbAlgo.setSelectedIndex(0);
        jcbAlgo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAlgoActionPerformed(evt);
            }
        });

        btnClearConsole.setText("Clear Console");
        btnClearConsole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearConsoleActionPerformed(evt);
            }
        });

        rdnFailSim.setText("Fail Sim");
        rdnFailSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdnFailSimActionPerformed(evt);
            }
        });

        jLabel2.setText("Select Simulation");

        btnFindVertex.setText("Vertex LookUp");
        btnFindVertex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindVertexActionPerformed(evt);
            }
        });

        lblNoIterations.setText("Number of Iterations");

        txtIterNum.setText("100");

        jLabel6.setText("Time Taken");

        lblTimeTaken.setText("0");

        lblTemperature.setText("Temperature");

        txtTemperature.setText("10000");

        lblCoolingRate.setText("Cooling Rate");

        txtCoolingRate.setText("0.003");

        jLabel3.setText("Iteration");

        lblIterationNum.setText("0");

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

        mnuEdit.setText("Edit");

        mnuUpdateWeight.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        mnuUpdateWeight.setText("Node Properties");
        mnuUpdateWeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUpdateWeightActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuUpdateWeight);

        jMenu1.setText("Graph Type");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem1.setText("Weighted");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem2.setText("UnWeighted");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        chkComplete.setSelected(false);
        chkComplete.setText("Complete");
        chkComplete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCompleteActionPerformed(evt);
            }
        });
        jMenu1.add(chkComplete);

        mnuEdit.add(jMenu1);

        jMenuBar1.add(mnuEdit);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdnFailSim)
                    .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClearConsole, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcbAlgo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 655, Short.MAX_VALUE)
                                .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblInitalDistance)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblInitalDistValue, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblFinalDistance)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblFinalDistValue, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(43, 43, 43)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTimeTaken, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblIterationNum, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(lblCapacity))))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sldrWeightSpeed, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblCapTransferred, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnGenResult)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPrintList, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9))))
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1095, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblTemperature, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTemperature, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblNoIterations, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIterNum, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(btnFindVertex)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtCoolingRate, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtVertexLookUp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblCoolingRate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdnFailSim)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbAlgo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClearConsole, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNoIterations)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIterNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTemperature)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTemperature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblCoolingRate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCoolingRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtVertexLookUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFindVertex)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jSplitPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sldrWeightSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCapTransferred, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPrintList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGenResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(lblCapacity))
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblInitalDistance)
                                        .addComponent(lblInitalDistValue))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(lblTimeTaken)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblFinalDistance)
                                    .addComponent(lblFinalDistValue)
                                    .addComponent(jLabel3)
                                    .addComponent(lblIterationNum))))
                        .addGap(15, 15, 15)
                        .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblFinalDistValue, lblInitalDistValue});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hider() {
        lblInitalDistance.setVisible(false);
        lblFinalDistance.setVisible(false);
        lblInitalDistValue.setVisible(false);
        lblFinalDistValue.setVisible(false);
//        lblPopulationSize.setVisible(false);
//        txtPopSize.setVisible(false);
        lblNoIterations.setVisible(false);
        txtIterNum.setVisible(false);
        lblTemperature.setVisible(false);
        lblCoolingRate.setVisible(false);
        txtTemperature.setVisible(false);
        txtCoolingRate.setVisible(false);
        btnGenResult.setVisible(false);
    }

    private void Autodraw(Point dest) {
        Image buff = createImage(pnlGraph.getWidth() - 1, pnlGraph.getHeight() - 1);
        Graphics buffG = buff.getGraphics();
        buffG.drawImage(bufferImage, 0, 0, this);
        Point source = Model.vertices.get(Model._selectedNode).getLocation();
        drawArrow(buffG, source.x, source.y, dest.x, dest.y);
        drawArrow(buffG, dest.x, dest.y, source.x, source.y);
        pnlGraph.getGraphics().drawImage(buff, 1, 1, this);
    }

    private void AutoAddEdge(Vertex v) {
        Model.weight = 0;
        if (Model._selectedNode >= 0) {
            for (Integer d : Model.vertices.keySet()) {
                Vertex dest = null;
                if (!Model.vertices.get(d).equals(v)) {
                    dest = Model.vertices.get(d);
                } else {
                    continue;
                }
                Point point = dest.getLocation();
                int xDistance = Math.abs(v.getX() - dest.getX());
                int yDistance = Math.abs(v.getY() - dest.getY());
                Model.weight = (int) Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
                addEdge(Model.Edgeid, v.getId(), dest.getId(), Model.weight);
                addEdge(Model.Edgeid, dest.getId(), v.getId(), Model.weight);
                Autodraw(point);
                //Model._selectedNode = -1;
                Model.changesMade = true;
                Model.Edgeid++;
                //graph();
            }
        }
        graph();
    }

    private void pnlGraphMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMouseDragged
        if (SwingUtilities.isLeftMouseButton(evt)) {
            if ((Model.toolType == Model.TOOL_BIDIRECTIONAL
                    || Model.toolType == Model.TOOL_DIRECTIONAL)
                    && Model._selectedNode >= 0) {
                Image buff = createImage(pnlGraph.getWidth() - 1, pnlGraph.getHeight() - 1);
                Graphics buffG = buff.getGraphics();
                buffG.drawImage(bufferImage, 0, 0, this);
                Point source = Model.vertices.get(Model._selectedNode).getLocation();
                drawArrow(buffG, source.x, source.y, evt.getX(), evt.getY());
                if (Model.toolType == Model.TOOL_BIDIRECTIONAL) {
                    drawArrow(buffG, evt.getX(), evt.getY(), source.x, source.y);
                }
                pnlGraph.getGraphics().drawImage(buff, 1, 1, this);
            }
            if ((Model.toolType == Model.TOOL_NONE
                    || Model.toolType == Model.TOOL_VERTEX)
                    && Model._selectedNode >= 0) {
                Vertex selectedVertex = Model.vertices.get(Model._selectedNode);
                int dX = evt.getX() - selectedVertex.getLocation().x;
                int dY = evt.getY() - selectedVertex.getLocation().y;
                for (Iterator<Vertex> it
                        = Model.vertices.values().iterator();
                        it.hasNext();) {
                    Vertex vertex = it.next();
                    if (vertex.getSelected()) {
                        Point newLocation = vertex.getLocation();
                        newLocation.x += dX;
                        newLocation.y += dY;
                    }
                }
                graph();
                Model.changesMade = true;
            }
        }
    }//GEN-LAST:event_pnlGraphMouseDragged

    private void pnlGraphMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
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
        }
    }//GEN-LAST:event_pnlGraphMouseClicked

    private void pnlGraphMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMousePressed
        pnlGraph.requestFocus();
        if (SwingUtilities.isLeftMouseButton(evt)) {
            Model.toolType = getTool();
            String[] types = new String[]{"Person", "City", "Place"};
            Model._selectedNode = nodeSelected(evt.getX(), evt.getY());
            if (Model.node1 == -1) {
                Model.node1 = Model._selectedNode;
            }
            if (Model.toolType == Model.TOOL_VERTEX
                    || Model.toolType == Model.TOOL_NONE) {
                if (!evt.isControlDown()) {
                    if (Model._selectedNode >= 0) {
                        Vertex selectedVertex
                                = Model.vertices.get(Model._selectedNode);
                        if (!selectedVertex.getSelected()) {
                            clearSelected();
                        }
                        selectedVertex.setSelected(true);
                        return;
                    } else {
                        clearSelected();
                    }
                } else if (Model._selectedNode >= 0) {
                    Vertex selectedVertex
                            = Model.vertices.get(Model._selectedNode);
                    selectedVertex.setSelected(!selectedVertex.getSelected());
                    if (!selectedVertex.getSelected()) {
                        Model._selectedNode = -1;
                        return;
                    }
                }
            }
            if (Model.toolType == Model.TOOL_VERTEX) {
                if (Model._selectedNode < 0) {
                    Model.changesMade = true;
                    if (isComplete == true) {
                        Vertex v = new Vertex(Model.id, new Point(evt.getX(), evt.getY()), String.valueOf(Model.id), types[(int) (Math.random() * types.length)], (int) (Math.random() * 50));
                        Model.vertices.put(v.getId(), v);
                        Model._selectedNode = Model.id;
                        Model.id++;
                        AutoAddEdge(v);
                    } else {
                        Vertex v = new Vertex(Model.id, new Point(evt.getX(), evt.getY()), String.valueOf(Model.id), types[(int) (Math.random() * types.length)], (int) (Math.random() * 50));
                        Model.vertices.put(v.getId(), v);
                        Model.id++;
                    }
                } else if (evt.isControlDown() && evt.isShiftDown()) { // control shift to fail all edges leading out of a vertex
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
                    if (null != Model.sim) {
                        switch (Model.sim) {
                            case "DFS":
                                Algorithms.Dfs(Model.vertices.get(Model._source));
                                Algorithms.shortestPath(Model._source, Model._dest);
                                break;
                            case "BFS":
                                Algorithms.Bfs(Model.vertices.get(Model._source));
                                Algorithms.shortestPath(Model._source, Model._dest);
                                break;
                            case "Dijkstra":
                                Algorithms.execute(Model.vertices.get(Model._source));
                                Algorithms.shortestPath(Model._source, Model._dest);
                                break;
                            default:
                                break;
                        }
                    }
                    Model.changesMade = true;
                }
            } else if (Model.toolType != Model.TOOL_NONE) {
                if (Model._selectedNode >= 0) {
                    Vertex selectedVertex
                            = Model.vertices.get(Model._selectedNode);
                    clearSelected();
                    selectedVertex.setSelected(true);
                }
            }
            graph();
        }
    }//GEN-LAST:event_pnlGraphMousePressed

    private void pnlGraphMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMouseReleased
        if (SwingUtilities.isLeftMouseButton(evt)) {
            Model.weight = 0;
            if (Model._selectedNode >= 0) {
                int destination = nodeSelected(evt.getX(), evt.getY());
                if (destination >= 0 && destination != Model._selectedNode) {
                    int xDistance = Math.abs(Model.vertices.get(Model._selectedNode).getX() - Model.vertices.get(destination).getX());
                    int yDistance = Math.abs(Model.vertices.get(Model._selectedNode).getY() - Model.vertices.get(destination).getY());
                    Model.weight = (int) Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
                    addEdge(Model.Edgeid, Model._selectedNode, destination, Model.weight);
                    Model._selectedNode = -1;
                    Model.changesMade = true;
                    Model.Edgeid++;
                }
            }
            graph();
        }
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
        hider();
        String selected = jcbAlgo.getSelectedItem().toString();
        if (selected.equals("TSP-GA")) {
            lblInitalDistance.setVisible(true);
            lblFinalDistance.setVisible(true);
            lblInitalDistValue.setVisible(true);
            lblFinalDistValue.setVisible(true);
//            lblPopulationSize.setVisible(true);
//            txtPopSize.setVisible(true);
            lblNoIterations.setVisible(true);
            txtIterNum.setVisible(true);
//            if (Model.vertices.size() != -1) {
//                txtPopSize.setText(String.valueOf(Model.vertices.size()));
//            }
        } else if (selected.equals("TSP-SA")) {
            lblInitalDistance.setVisible(true);
            lblFinalDistance.setVisible(true);
            lblInitalDistValue.setVisible(true);
            lblFinalDistValue.setVisible(true);
            lblTemperature.setVisible(true);
            lblCoolingRate.setVisible(true);
            txtTemperature.setVisible(true);
            txtCoolingRate.setVisible(true);
        }else if(selected.equals("Nearest Neighbor")){
            lblFinalDistance.setVisible(true);
            lblFinalDistValue.setVisible(true);
        }
    }//GEN-LAST:event_jcbAlgoActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        String x = String.valueOf(jcbAlgo.getSelectedItem());
        Model.sim = x;
        Model.glowMap.clear();
        txtConsole.setText("");
        Model.visited.clear();
        Model.set.clear();
        Model.cutV.clear();
        switch (x) {
            case "DFS":
                txtConsole.setText("");
                if (Model._source == -1 || Model._dest == -1 || Model.vertices.get(Model._source).eList().isEmpty()) {
                    if (Model._source == -1) {
                        printlnConsole("Please choose a source by double clicking a node\nMake sure source has connections");
                    } else {
                        printlnConsole("Please choose a destination by double clicking a node");
                    }
                    return;
                }
                Algorithms.Dfs(Model.vertices.get(Model._source));
                Algorithms.shortestPath(Model._source, Model._dest);
                break;
            case "BFS":
                txtConsole.setText("");
                if (Model._source == -1 || Model._dest == -1 || Model.vertices.get(Model._source).eList().isEmpty()) {
                    if (Model._source == -1) {
                        printlnConsole("Please choose a source by double clicking a node\nMake sure source has connections");
                    } else {
                        printlnConsole("Please choose a destination by double clicking a node");
                    }
                    return;
                }
                Algorithms.Bfs(Model.vertices.get(Model._source));
                Algorithms.shortestPath(Model._source, Model._dest);
                break;
            case "OtherBfs":
                txtConsole.setText("");
                Algorithms.disJointshortestPath(Model._source, Model._dest);
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
            case "Nearest Neighbor":
                txtConsole.setText("");
                Algorithms.NearestNeighbor();
                lblFinalDistValue.setText(String.valueOf(Model.FinalDistanceValue));
                break;
            default:
                break;
        }
        btnGenResult.setVisible(true);
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnPrintListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintListActionPerformed
        Iterator<Vertex> verts = Model.vertices.values().iterator();
        while (verts.hasNext()) {
            Vertex next = verts.next();
            printlnConsole(next.getName() + "->" + next.eList());
        }
        if (Model._source != -1) {
            printlnConsole("Source is: " + Model._source);
        }
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

    private void saveResult() throws IOException {
        ImageIO.write((RenderedImage) bufferImage, "PNG", new File("Result.png"));
    }
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

    private void pnlGraphKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pnlGraphKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_DELETE:
                Model.glowMap.clear();
                Integer[] keys = new Integer[Model.vertices.size()];
                Model.vertices.keySet().toArray(keys);
                for (Integer key : keys) {
                    Vertex remove = Model.vertices.get(key);
                    if (remove.getSelected()) {
                        Model.changesMade = true;
                        Model.cutV.clear();
                        Model._source = -1;
                        Model._dest = -1;

                        for (Iterator<Edge> e = Model.edges.iterator(); e.hasNext();) {
                            Edge next = e.next();
                            if (next.getSource() == remove || next.getDest() == remove) {
                                next.getSource().eList().remove(next);
                                next.getDest().eList().remove(next);
                                e.remove();
                            }
                        }
                        Model.vertices.remove(key);

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
                }
                break;
            case KeyEvent.VK_V:
                clearTools();
                tools[0].setSelected(true);
                break;
            case KeyEvent.VK_B:
                clearTools();
                tools[1].setSelected(true);
                break;
            case KeyEvent.VK_D:
                clearTools();
                tools[2].setSelected(true);
                break;
            case KeyEvent.VK_N:
                clearTools();
                break;
            case KeyEvent.VK_I:
                if (Model._selectedNode != -1) {
                    if (Model.node1 != -1) {
                        Model.node2 = Model._selectedNode;
                        try {
                            String newWeight = JOptionPane.showInputDialog(this, "Input Weight:");
                            if (newWeight == null) {
                                break;
                            }
                            Integer weight = Integer.parseInt(newWeight);
                            for (Edge edge : Model.vertices.get(Model.node1).eList()) {
                                if (edge.getDest() == Model.vertices.get(Model.node2)) {
                                    edge.setWeight(weight);
                                    break;
                                }
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Value must be an integer!");
                        }

                    }

                }
                break;
            default:
                break;
        }
        graph();
    }//GEN-LAST:event_pnlGraphKeyPressed

    private void btnGenResultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenResultActionPerformed
        // TODO add your handling code here:
        try {
            saveResult();
        } catch (IOException ex) {
            Logger.getLogger(GraphifyGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGenResultActionPerformed

    private void mnuUpdateWeightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUpdateWeightActionPerformed
        // TODO add your handling code here:
        NodeEdit.run();
    }//GEN-LAST:event_mnuUpdateWeightActionPerformed

    private void rdnFailSimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdnFailSimActionPerformed
        // TODO add your handling code here:
        Timer exe = new Timer(1, null);
        if (rdnFailSim.isSelected()) {
            decreseWeights = (ActionEvent e) -> {
                if ("Dijkstra".equals(Model.sim)) {
                    if (Model.decreaseWeightEllapse < (1000 - sldrWeightSpeed.getValue())) {
                        Model.decreaseWeightEllapse++;
                    } else {
                        Model.decreaseWeightEllapse = 0;
                        reduceIncreaseWeight();
                        autoFailure();
                        autoHeal();
                        graph();
                    }
                }
            };
            exe = new Timer(1, decreseWeights);
            exe.start();
        }
    }//GEN-LAST:event_rdnFailSimActionPerformed

    boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
        }
        return false;
    }
    private void btnFindVertexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindVertexActionPerformed
        // TODO add your handling code here:
        String vertexNum = txtVertexLookUp.getText();
        if (isInteger(vertexNum)) {
            Model._findNode = Integer.parseInt(vertexNum);
            for (Integer i : Model.vertices.keySet()) {
                if (i.equals(Model._findNode)) {
                    graph();
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Value should be an Integer",
                    "Value Error!",
                    JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnFindVertexActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        isWeighted = true;
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        isWeighted = false;
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void chkCompleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCompleteActionPerformed
        // TODO add your handling code here:
        if (chkComplete.isSelected()) {
            isComplete = true;
        } else {
            isComplete = false;
        }

    }//GEN-LAST:event_chkCompleteActionPerformed

    private void addEdge(Integer edgeId, Integer sourceid, Integer destid, int weight) {
        Edge newEdge = new Edge(edgeId, Model.vertices.get(sourceid), Model.vertices.get(destid), weight, false);
        Model.vertices.get(sourceid).eList().add(newEdge);
        if (Model.toolType == Model.TOOL_BIDIRECTIONAL) {
            newEdge.setBidirectional(true);
            Edge tempEdge = new Edge(edgeId, Model.vertices.get(destid), Model.vertices.get(sourceid), weight, false);
            Model.vertices.get(destid).eList().add(tempEdge);
        }
        Model.edges.add(newEdge);
    }

    private void reduceIncreaseWeight() {
        if (Model._source > -1 && Model._dest > -1) {
            int edgeSize = Model.edges.size();
            int rand = (int) (Math.random() * edgeSize);
            Edge e = Model.edges.get(rand);
            int cost = e.getWeight() + (int) (Math.sqrt(e.getWeight())) * (Math.random() > .5 ? -1 : 1);
            if (cost <= 0) {
                cost += 5;
            }
            e.setWeight(cost); // changes it
            Model.glowMap.clear();
            Algorithms.execute(Model.vertices.get(Model._source));
            Algorithms.shortestPath(Model._source, Model._dest);
            e.setGlowLevel(1);
        }

        //graph();
    }

    private void autoFailure() { // randomly fail nodes in the network
        if (Model._source > -1 && Model._dest > -1) {
            if (0.2 >= Math.random()) { // make probability of a node failure low
                int vertexSize = Model.vertices.size();
                Vertex v = null;
                int rand = 0;
                while (v == null) {
                    rand = (int) (Math.random() * vertexSize);
                    v = Model.vertices.get(rand);
                }
                if (!(rand == Model._source || rand == Model._dest)) { // do not fail source or destination
                    if (Model.failed.contains(v)) {
                        Model.failed.remove(v);
                    } else {
                        Model.failed.add(v);
                        printlnConsole(v.getName() + " has failed");
                    }
//                    Iterator<Edge> e = v.eList().iterator();
//                    while (e.hasNext()) {
//                        Edge next = e.next();
//                        next.setFailed(!next.isFailed()); //set it to opposite of what it is
//                    }
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
//        try {
//            this.backgroundImage = ImageIO.read(new File("C:\\Users\\Ayomitunde\\Desktop\\GraphifyGUI\\Graphify\\src\\graphify\\images\\us-map.png"));
//        } catch (IOException ex) {
//            Logger.getLogger(GraphifyGUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
        bufferGraphic.setColor(Color.white);
        bufferGraphic.fillRect(0, 0, pnlGraph.getWidth(), pnlGraph.getHeight());
        Model.connectionCache.clear();
        // Regular connections
        int xmid = 0;
        int ymid = 0;
        bufferGraphic.setColor(Color.black);
        bufferGraphic.setStroke(new BasicStroke(1));
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
            if (e.getBidirectional()) {
                drawArrowOnGraphics(bufferGraphic, dest.x, dest.y, source.x, source.y);
            }
            int edgeWeight = e.getWeight();
            if (!(edgeWeight == -1)) {
                bufferGraphic.setColor(
                        new Color(180, 30, 255, (int) (e.getGlowLevel() * 255)));
                bufferGraphic.fillRect(xmid - 6, ymid - 14, 50, 20);
                bufferGraphic.setColor(Color.black);
//                if(isComplete == true){
//                    bufferGraphic.setStroke(new BasicStroke(1));
//                }else{
//                    bufferGraphic.setStroke(new BasicStroke(1));
//                }
                bufferGraphic.setStroke(new BasicStroke(1));

                if (isWeighted == true) {
                    bufferGraphic.drawString(String.valueOf(edgeWeight), xmid, ymid);
                } else {
                    bufferGraphic.drawString("", xmid, ymid);
                }
                e.glowDie(.05);
            }
        }

        // Glowing connections
        bufferGraphic.setColor(new Color(10, 230, 40));
        bufferGraphic.setStroke(new BasicStroke(4));

        if (!btnReset.isSelected()) {
            for (Iterator<Vertex> it = Model.glowMap.keySet().iterator();
                    it.hasNext();) {
                Vertex sourceVertex = it.next();
                Vertex destinationVertex = Model.glowMap.get(sourceVertex);
                Point sourcePoint = sourceVertex.getLocation();
                Point destPoint = destinationVertex.getLocation();
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
            } else if (v.getId() == Model._selectedNode || v.getSelected()) {
                bufferGraphic.setColor(Color.orange);
            } else if (Model._findNode != -1 && v.getId() == Model._findNode) {
                bufferGraphic.setColor(Color.BLUE.brighter());
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

    private int getTool() {
        for (int i = 0; i < tools.length; i++) {
            if (tools[i].isSelected()) {
                return i;
            }
        }
        return -1;
    }

    private void clearTools() {
        for (JToggleButton tool : tools) {
            tool.setSelected(false);
        }
    }

    private void clearSelected() {
        for (Vertex vertex : Model.vertices.values()) {
            vertex.setSelected(false);
        }
    }

    public void setlblCapTransferred(String txt) {
        lblCapTransferred.setText(txt);
    }

    public void setlblCapTransferredColor(Color c) {
        lblCapTransferred.setForeground(c);
    }

    public void setlblIterations(String txt) {
        lblIterationNum.setText(txt);
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
            Model.Gui = new GraphifyGUI();
            Model.Gui.setLocationRelativeTo(null);
            Model.Gui.setExtendedState(MAXIMIZED_BOTH);
            Model.Gui.show();
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearConsole;
    private javax.swing.JButton btnFindVertex;
    private javax.swing.JButton btnGenResult;
    private javax.swing.JButton btnPrintList;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnStart;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBoxMenuItem chkComplete;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JComboBox<String> jcbAlgo;
    private javax.swing.JLabel lblCapTransferred;
    private javax.swing.JLabel lblCapacity;
    private javax.swing.JLabel lblCoolingRate;
    private javax.swing.JLabel lblFinalDistValue;
    private javax.swing.JLabel lblFinalDistance;
    private java.awt.Label lblInfo;
    private javax.swing.JLabel lblInitalDistValue;
    private javax.swing.JLabel lblInitalDistance;
    public static javax.swing.JLabel lblIterationNum;
    private javax.swing.JLabel lblNoIterations;
    private javax.swing.JLabel lblTemperature;
    public static javax.swing.JLabel lblTimeTaken;
    private javax.swing.JMenu mnuEdit;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenuItem mnuNew;
    private javax.swing.JMenuItem mnuOpen;
    private javax.swing.JMenuItem mnuQuit;
    private javax.swing.JMenuItem mnuSave;
    private javax.swing.JMenuItem mnuSaveAs;
    private javax.swing.JMenuItem mnuUpdateWeight;
    private javax.swing.JPanel pnlGraph;
    private javax.swing.JRadioButton rdnFailSim;
    private javax.swing.JSlider sldrWeightSpeed;
    private javax.swing.JTextArea txtConsole;
    public static javax.swing.JTextField txtCoolingRate;
    public static javax.swing.JTextField txtIterNum;
    public static javax.swing.JTextField txtTemperature;
    private javax.swing.JTextField txtVertexLookUp;
    // End of variables declaration//GEN-END:variables
}
