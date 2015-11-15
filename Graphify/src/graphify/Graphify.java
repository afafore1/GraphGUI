package graphify;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Graphify extends javax.swing.JFrame {

    HashMap<Integer, ArrayList<Integer>> nodes = new HashMap();
    private Queue<Integer> queue;
    HashMap<Integer, Point> locations = new HashMap();
    private int[] distTo;
    private LinkedHashMap<Integer, Integer> set = new LinkedHashMap<Integer, Integer>();
    int[] visited;
    int _selectedNode = -1;
    int _SIZE_OF_NODE = 20;
    int id = 0;
    static int _source = 0;
    Image bufferImage;
    Graphics bufferGraphic;

    public Graphify() {
        initComponents();
        bufferImage = createImage(pnlGraph.getWidth(), pnlGraph.getHeight());
        bufferGraphic = bufferImage.getGraphics();
        queue = new LinkedList<Integer>();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlGraph = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        btnReset1 = new javax.swing.JButton();

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
            .addGap(0, 249, Short.MAX_VALUE)
        );

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnReset1.setText("Print List");
        btnReset1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReset1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 205, Short.MAX_VALUE)
                        .addComponent(btnReset1))
                    .addComponent(pnlGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset)
                    .addComponent(btnReset1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlGraphMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMousePressed
        _selectedNode = nodeSelected(evt.getX(), evt.getY());
        if (_selectedNode < 0) {
            nodes.put(id, new ArrayList());
            locations.put(id++, new Point(evt.getX(), evt.getY()));
        }
        graph();
    }//GEN-LAST:event_pnlGraphMousePressed

    private void pnlGraphMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMouseDragged
        if (_selectedNode >= 0) {
            Image buff = createImage(pnlGraph.getWidth(), pnlGraph.getHeight());
            Graphics buffG = buff.getGraphics();
            buffG.drawImage(bufferImage, 0, 0, this);
            Point source = locations.get(_selectedNode);
            buffG.drawLine(source.x, source.y,
                    evt.getX(), evt.getY());
            pnlGraph.getGraphics().drawImage(buff, 0, 0, this);
        }
    }//GEN-LAST:event_pnlGraphMouseDragged

    private void pnlGraphComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnlGraphComponentResized
        bufferImage = createImage(pnlGraph.getWidth(), pnlGraph.getHeight());
        bufferGraphic = bufferImage.getGraphics();
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

    public ArrayList<Integer> getEdge(int source) {
        if (source > nodes.size()) {
            System.out.println("Vertex not present");
            return null;
        }
        return nodes.get(source);
    }

    void bfs(int source) {
        int V = nodes.size();
        distTo = new int[V];
        visited = new int[V];
        for (int i = 0; i < V; i++) {
            visited[i] = -1;
        }
        ArrayList<Integer> conn = new ArrayList<Integer>();
        int i, element;
        visited[source] = 0;
        queue.add(source);
        while (!queue.isEmpty()) {
            element = queue.remove();
            System.out.println(element + " removed");
            i = element;
            conn.add(element);
            ArrayList<Integer> iList = getEdge(i);
            int x = 0;
            while (x < iList.size()) {
                if (visited[iList.get(x)] == -1) {
                    queue.add(iList.get(x));
                    visited[iList.get(x)] = i;
                    distTo[iList.get(x)] = distTo[i] + 1;
                }
                x++;
            }
        }
        System.out.println("Order is " + conn);
    }

    public int hasPath(int v) {
        return visited[v];
    }

    public int distTo(int v) {
        return distTo[v];
    }

    public void shortestPath(int v, int e) {
        if (e == v) {
            System.out.println(v + "-->" + v);
            System.exit(0);
        }
        for (int i = e; i > 0; i = visited[i]) {
            if (i == v) {
                break;
            }
            if (visited[i] != -1) {
                set.put(i, visited[i]);
            }
        }
        String dset = set.toString().replace("}", "").replace("{", "");
        dset = new StringBuilder(dset).reverse().toString().replaceAll("=", "-->");
        System.out.println(dset);
    }
    //[0, 2, 19, 5, 7, 9, 14]

    private void btnReset1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReset1ActionPerformed
        for (int i = 0; i < nodes.size(); i++) {
            System.out.println(i + "->" + getEdge(i));
        }
        System.out.println("Source is: " + _source);
    }//GEN-LAST:event_btnReset1ActionPerformed


    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        nodes = new HashMap();
        locations = new HashMap();
        id = 0;
        graph();
    }//GEN-LAST:event_btnResetActionPerformed

    private void pnlGraphMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMouseClicked
        _selectedNode = nodeSelected(evt.getX(), evt.getY());
        if (evt.getClickCount() == 2) {
            set.clear();
            _source = _selectedNode;
            bfs(_source);
            shortestPath(_source, nodes.size() - 1);
        }

    }//GEN-LAST:event_pnlGraphMouseClicked

    private void graph() {
        bufferGraphic.setColor(Color.white);
        bufferGraphic.fillRect(0, 0, pnlGraph.getWidth(), pnlGraph.getHeight());
        bufferGraphic.setColor(Color.black);
        for (int i = 0; i < locations.size(); i++) {
            Point thePoint = (Point) locations.values().toArray()[i];
            for (Integer destinationIndex : (ArrayList<Integer>) nodes.values().toArray()[i]) {
                Point destinantionPoint = locations.get(destinationIndex);
                bufferGraphic.drawLine(thePoint.x, thePoint.y,
                        destinantionPoint.x, destinantionPoint.y);
            }
        }
        for (int i = 0; i < locations.size(); i++) {
            Point thePoint = (Point) locations.values().toArray()[i];
            if (locations.keySet().toArray()[i] == (Integer) _selectedNode) {
                bufferGraphic.setColor(Color.orange);
            } else {
                bufferGraphic.setColor(Color.red);
            }
            bufferGraphic.fillOval(thePoint.x - _SIZE_OF_NODE / 2,
                    thePoint.y - _SIZE_OF_NODE / 2, _SIZE_OF_NODE, _SIZE_OF_NODE);
        }

        bufferGraphic.setColor(Color.blue);
        for (int i = 0; i < locations.size(); i++) {
            Point thePoint = (Point) locations.values().toArray()[i];
            bufferGraphic.drawString("V " + locations.keySet().toArray()[i],
                    thePoint.x + _SIZE_OF_NODE + 4, thePoint.y + _SIZE_OF_NODE + 4);
        }
        pnlGraph.getGraphics().drawImage(bufferImage, 0, 0, this);
    }

    private int nodeSelected(int x, int y) {
        for (int i = 0; i < locations.size(); i++) {
            Point thePoint = (Point) locations.values().toArray()[i];
            int deltaX = x - (thePoint.x - _SIZE_OF_NODE / 2);
            int deltaY = y - (thePoint.y - _SIZE_OF_NODE / 2);
            if (Math.sqrt(deltaX * deltaX
                    + deltaY * deltaY) <= _SIZE_OF_NODE) {
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
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnReset1;
    private javax.swing.JPanel pnlGraph;
    // End of variables declaration//GEN-END:variables
}
