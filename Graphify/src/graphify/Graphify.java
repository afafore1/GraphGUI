package graphify;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import javax.swing.SwingUtilities;

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
    ArrayList<Integer> conn;
    ArrayList<Integer> bconn;
    ArrayList<Integer> cutV;
    int _selectedNode = -1;
    int _SIZE_OF_NODE = 20;
    int id = 0;
    int time = 0;
    static int _source = -1;
    static int _dest = -1;
    Image bufferImage;
    Graphics2D bufferGraphic;

    public Graphify() {
        initComponents();
        bufferImage = createImage(pnlGraph.getWidth() - 2, pnlGraph.getHeight() - 2);
        bufferGraphic = (Graphics2D) bufferImage.getGraphics();
        queue = new LinkedList<Integer>();
        stack = new Stack<Integer>();
        cutV = new ArrayList<Integer>();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlGraph = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        btnPrintList = new javax.swing.JButton();
        lblInfo = new java.awt.Label();
        lblResult = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
            .addGap(0, 341, Short.MAX_VALUE)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 324, Short.MAX_VALUE)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblResult))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset)
                    .addComponent(btnPrintList))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlGraphMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMousePressed
        _selectedNode = nodeSelected(evt.getX(), evt.getY());
        if (_selectedNode < 0 && SwingUtilities.isLeftMouseButton(evt)) {
            nodes.put(id, new HashSet());
            locations.put(id++, new Point(evt.getX(), evt.getY()));
        } else if (SwingUtilities.isLeftMouseButton(evt)) {
        } else if (SwingUtilities.isRightMouseButton(evt)) {
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
        System.out.println(u+" has edges "+getEdge(u));
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
                if (u != _source && low.get(v) >= disc.get(u)) {
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
        HashMap <Integer, Integer> low = new HashMap<>();
        HashMap <Integer, Integer> parent = new HashMap<>();
        HashMap <Integer, Integer> ap = new HashMap<>();

        for (int i = 0; i < V; i++) {
            Integer key = (Integer) nodes.keySet().toArray()[i];
            parent.put(key, -1);
           // parent[key] = -1;
            visited.put(key, -1);
            ap.put(key, 0);
        }

        for (int i = 0; i < V; i++) {
            Integer key = (Integer) nodes.keySet().toArray()[i];
            if (visited.get(key) == -1) {
                APF(i, visited, disc, low, parent, ap);
            }
        }

        for (int i = 0; i < V; i++) {
            Integer key = (Integer) nodes.keySet().toArray()[i];
            if (ap.get(key) == 1) {
                System.out.println(key+" is a cut vertex");
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
            System.out.println("Considering element " + element);
            if (!bconn.contains(element)) {
                bconn.add(element);
            }
            HashSet<Integer> iList = getEdge(element);
            int x = 0;
            while (x < iList.size()) {
                Integer key = (Integer) iList.toArray()[x];
                if (visited.get(key) == -1) {
                    System.out.println("Pushing " + (Integer) iList.toArray()[x]);
                    stack.push((Integer) iList.toArray()[x]);
                    visited.put(key, element);
                    distTo.put(key, distTo.get(element) + 1);
                    break;
                }
                x++;
                if (x == iList.size()) {
                    int backEdge = stack.pop();
                    System.out.println("Back edge " + backEdge);
                }
            }
        }
        for (int i = 0; i < V; i++) {
            if (ap[i] == true) {
                System.out.println(i+" is a cut vertex");
                cutV.add(i);
            }
        }
        System.out.println("order is " + bconn);
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
            System.out.println(element + " removed");
            i = element;
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
        System.out.println("Order is " + conn);
    }

    public int hasPath(int v) {
        return visited.get(v);
    }

    public int distTo(int v) {
        return distTo.get(v);
    }

    public void shortestPath(int v, int e) {
        if (e == v) {
            System.out.println(v + "-->" + v);
//            System.exit(0);
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
        System.out.println(set.toString().replaceAll("=", "-->"));
        glowMap.clear();
        for (int i : set.keySet()) {
            glowMap.put(i, set.get(i));
        }
        graph();
    }
    //[0, 2, 19, 5, 7, 9, 14]

    private void btnPrintListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintListActionPerformed
        for (int i = 0; i < nodes.size(); i++) {
            int key = (Integer) nodes.keySet().toArray()[i];
            System.out.println(key + "->" + getEdge(key));
        }
        System.out.println("Source is: " + _source);
    }//GEN-LAST:event_btnPrintListActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        nodes = new HashMap();
        locations = new HashMap();
        id = 0;
        graph();
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
                bfs(_source);
                shortestPath(_source, _dest);
                dfs(_source);
                cutV = new ArrayList<Integer>();
                AP();
            }
            graph();
        }
    }//GEN-LAST:event_pnlGraphMouseClicked
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
        bufferGraphic.setStroke(new BasicStroke(1));
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
        bufferGraphic.setColor(new Color(200, 40, 232));
        bufferGraphic.setStroke(new BasicStroke(8));
        for (int sourceKey : glowMap.keySet()) {
            int destKey = glowMap.get(sourceKey);
            Point sourcePoint = (Point) locations.get(sourceKey);
            Point destPoint = (Point) locations.get(destKey);
            bufferGraphic.drawLine(sourcePoint.x, sourcePoint.y,
                    destPoint.x, destPoint.y);
        }

        // Nodes - red circles.
        for (int i = 0; i < locations.size(); i++) {
            Point thePoint = (Point) locations.values().toArray()[i];
            if (locations.keySet().toArray()[i]
                    == (Integer) _source) {
                bufferGraphic.setColor(Color.green);
            } else if (locations.keySet().toArray()[i]
                    == (Integer) _dest) {
                bufferGraphic.setColor(Color.blue);
            } else if (locations.keySet().toArray()[i] == (Integer) _selectedNode) {
                bufferGraphic.setColor(Color.orange);
            } else if(!cutV.contains(locations.keySet().toArray()[i])){
                bufferGraphic.setColor(Color.red);
            } else{
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
    private javax.swing.JButton btnPrintList;
    private javax.swing.JButton btnReset;
    private java.awt.Label lblInfo;
    private javax.swing.JLabel lblResult;
    private javax.swing.JPanel pnlGraph;
    // End of variables declaration//GEN-END:variables
}
