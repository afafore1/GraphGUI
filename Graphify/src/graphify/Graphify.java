package graphify;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class Graphify extends javax.swing.JFrame {
    HashMap<Integer, ArrayList<Integer>> nodes = new HashMap();
    HashMap<Integer, Point> locations = new HashMap();
    int selectedNode = -1;
    int SIZE_OF_NODE = 20;
    int id = 0;
    Image bufferImage;
    Graphics bufferGraphic;
    public Graphify() {
        initComponents();
        bufferImage = createImage(pnlGraph.getWidth(), pnlGraph.getHeight());
        bufferGraphic = bufferImage.getGraphics();
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
        selectedNode = nodeSelected(evt.getX(), evt.getY());
        if (selectedNode < 0) {
            nodes.put(id, new ArrayList());
            locations.put(id++, new Point(evt.getX(), evt.getY()));
        }
        graph();
    }//GEN-LAST:event_pnlGraphMousePressed

    private void pnlGraphMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraphMouseDragged
        if (selectedNode >= 0) {
            Image buff = createImage(pnlGraph.getWidth(), pnlGraph.getHeight());
            Graphics buffG = buff.getGraphics();
            buffG.drawImage(bufferImage, 0, 0, this);
            Point source = locations.get(selectedNode);
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
        if (selectedNode >= 0) {
            int destination = nodeSelected(evt.getX(), evt.getY());
            if (destination >= 0 && destination != selectedNode) {
                nodes.get(selectedNode).add(destination);
                nodes.get(destination).add(selectedNode);
                selectedNode = -1;
            }
        }
        graph();
    }//GEN-LAST:event_pnlGraphMouseReleased

    private void btnReset1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReset1ActionPerformed
        for (int i = 0; i < nodes.size(); i++) {
            ArrayList<Integer> connections = (ArrayList<Integer>) nodes.values().toArray()[i];
            int key = (Integer) nodes.keySet().toArray()[i];
            System.out.println("Node number " + key + ":");
            for (Integer theConnection: connections) {
                System.out.println("\tConnects with: " + theConnection);
            }
        }
    }//GEN-LAST:event_btnReset1ActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        nodes = new HashMap();
        locations = new HashMap();
        id = 0;
        graph();
    }//GEN-LAST:event_btnResetActionPerformed

    private void graph() {
        bufferGraphic.setColor(Color.white);
        bufferGraphic.fillRect(0, 0, pnlGraph.getWidth(), pnlGraph.getHeight());
        bufferGraphic.setColor(Color.black);
        for (int i = 0; i < locations.size(); i++) {
            Point thePoint = (Point) locations.values().toArray()[i];
            for (Integer destinationIndex: (ArrayList<Integer>) nodes.values().toArray()[i]) {
                Point destinantionPoint = locations.get(destinationIndex);
                bufferGraphic.drawLine(thePoint.x, thePoint.y,
                        destinantionPoint.x, destinantionPoint.y);
            }
        }
        for (int i = 0; i < locations.size(); i++) {
            Point thePoint = (Point) locations.values().toArray()[i];
            if (locations.keySet().toArray()[i] == (Integer) selectedNode) {
                bufferGraphic.setColor(Color.orange);
            } else {
                bufferGraphic.setColor(Color.red);
            }
            bufferGraphic.fillOval(thePoint.x - SIZE_OF_NODE / 2, 
                    thePoint.y - SIZE_OF_NODE / 2, SIZE_OF_NODE, SIZE_OF_NODE);
        }

        bufferGraphic.setColor(Color.blue);
        for (int i = 0; i < locations.size(); i++) {
            Point thePoint = (Point) locations.values().toArray()[i];
            bufferGraphic.drawString("V " + locations.keySet().toArray()[i],
                    thePoint.x + SIZE_OF_NODE + 4, thePoint.y + SIZE_OF_NODE + 4);
        }
        pnlGraph.getGraphics().drawImage(bufferImage, 0, 0, this);
    }
    
    private int nodeSelected (int x, int y) {
        for (int i = 0; i < locations.size(); i++) {
            Point thePoint = (Point) locations.values().toArray()[i];
            int deltaX = x - (thePoint.x - SIZE_OF_NODE / 2);
            int deltaY = y - (thePoint.y - SIZE_OF_NODE / 2);
            if (Math.sqrt(deltaX * deltaX
                    + deltaY * deltaY) <= SIZE_OF_NODE) {
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
