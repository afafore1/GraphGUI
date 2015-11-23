/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphify;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Ayomitunde
 */

public class Algorithms {
    private GraphifyGUI GG;
    HashMap<Integer, Integer> connectionCache = new HashMap<>();
    private HashMap<Integer, Integer> glowMap = new HashMap<>();
    HashMap<Integer, HashSet<Integer>> nodes;
    private Queue<Integer> queue;
    private Stack<Integer> stack;
    private HashMap<Integer, Integer> distTo;
    private HashMap<Integer, Integer> set = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> visited;
    private HashMap<Integer, Integer> color;
    private HashMap<Integer, Integer> greedyresult;
    private HashSet<Integer> _colors2;
    private ArrayList<Integer> conn;
    private ArrayList<Integer> bconn;
    private ArrayList<Integer> cutV;
    private Color[] vertexColors;
    int _selectedNode = -1;
    int _SIZE_OF_NODE = 20;
    int id = 0;
    int time = 0;
    Integer maxColors = 0;
    int _source;
    int _dest;
    
    
    public Algorithms(GraphifyGUI GG){
        this.GG = GG;
        this.nodes = new HashMap<>();
        this.queue = new LinkedList<Integer>();  
        this.stack = new Stack<Integer>();
        this.cutV = new ArrayList<Integer>();
        this._colors2 = new HashSet<Integer>();
        this.visited = new HashMap<Integer, Integer>();
        this.set = new HashMap<Integer, Integer>();
        this.visited = new HashMap<Integer, Integer>();
        this.color = new HashMap<Integer, Integer>();
        this.greedyresult = new HashMap<Integer, Integer>();
        this.vertexColors = new Color[]{Color.blue, Color.red, Color.yellow, Color.green, Color.magenta, Color.orange};
    }
    
    
    public HashSet<Integer> getEdge(int source) {
        nodes = GG.getNode();
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
        nodes = GG.getNode();
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
                GG.printlnConsole(key + " is a cut vertex");
                cutV.add(key);
            }
        }
    }

    void dfs(int source) {
        nodes = GG.getNode();
        int V = nodes.size();
        distTo = new HashMap<>();
        visited = new HashMap<>();
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
            GG.printlnConsole("Considering element " + element);
            if (!bconn.contains(element)) {
                bconn.add(element);
            }
            HashSet<Integer> iList = getEdge(element);
            int x = 0;
            while (x < iList.size()) {
                Integer key = (Integer) iList.toArray()[x];
                if (visited.get(key) == -1) {
                    GG.printlnConsole("Pushing " + (Integer) iList.toArray()[x]);
                    stack.push((Integer) iList.toArray()[x]);
                    visited.put(key, element);
                    distTo.put(key, distTo.get(element) + 1);
                    break;
                }
                x++;
                if (x == iList.size()) {
                    int backEdge = stack.pop();
                    GG.printlnConsole("Back edge " + backEdge);
                }
            }
        }
        GG.printlnConsole("order is " + bconn);
    }

    void bfs(int source) {
        nodes = GG.getNode();
        GG.printlnConsole(nodes.toString());
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
            GG.printlnConsole(element + " removed");
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
        GG.printlnConsole("Order is " + conn);
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
        nodes = GG.getNode();
        int V = nodes.size();
        HashMap<Integer, Integer> available = new HashMap<Integer, Integer>();
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
            GG.printlnConsole("Vertex " + key + " ---> Color " + greedyresult.get(key));
        }

    }

    void Bipartite(int source) { // will test for 3
        nodes = GG.getNode();
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
            GG.printlnConsole("Graph is not bipartite at " + _colors2.toString());
        } else {
            GG.printlnConsole("Graph is bipartite");
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
            GG.printlnConsole(v + "-->" + v);
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
        GG.printlnConsole(set.toString().replaceAll("=", "-->"));
        glowMap.clear();
        for (int i : set.keySet()) {
            glowMap.put(i, set.get(i));
        }
        GG.graph();
    }
    //[0, 2, 19, 5, 7, 9, 14]
    
     public Queue getQueue(){
        return this.queue;
    }    
    public Stack getStack(){
        return this.stack;
    }
    public HashMap getGlowMap(){
        return this.glowMap;
    }
     public HashMap distTo(){
        return this.distTo;
    }
      public HashMap getSet(){
        return this.set;
    }
       public HashMap getVisited(){
        return this.visited;
    }
       public HashMap getColor(){
        return this.color;
    }
       public HashMap getGreedyResult(){
        return this.greedyresult;
    }
       public HashSet getColors2(){
           return this._colors2;
       }
       public ArrayList getConn(){
           return this.conn;
       }
       public ArrayList getBConn(){
           return this.bconn;
       }
       public ArrayList getCutV(){
           return this.cutV;
       }
       public Color [] getVertexColors(){
           return this.vertexColors;
       }

}
