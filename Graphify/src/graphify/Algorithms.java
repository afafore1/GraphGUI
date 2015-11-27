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
import java.util.List;
import java.util.Objects;
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

    public Algorithms(GraphifyGUI GG) {
        this.GG = GG;
        this.nodes = new HashMap<>();
        this.queue = new LinkedList<>();
        this.stack = new Stack<>();
        this.cutV = new ArrayList<>();
        this._colors2 = new HashSet<>();
        this.visited = new HashMap<>();
        this.set = new HashMap<>();
        this.visited = new HashMap<>();
        this.color = new HashMap<>();
        this.greedyresult = new HashMap<>();
        this.vertexColors = new Color[]{Color.blue, Color.red, Color.yellow, Color.green, Color.magenta, Color.orange};
    }

    public HashSet<Integer> getEdge(int source) {
        nodes = GraphifyGUI.getNode();
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
        nodes = GraphifyGUI.getNode();
        visited = new HashMap<>();
        boolean cutExist = false;
        HashMap<Integer, Integer> disc = new HashMap<>();
        HashMap<Integer, Integer> low = new HashMap<>();
        HashMap<Integer, Integer> parent = new HashMap<>();
        HashMap<Integer, Integer> ap = new HashMap<>();

        Iterator<Integer> allNodes = nodes.keySet().iterator();
        while (allNodes.hasNext()) {
            int key = allNodes.next();
            parent.put(key, -1);
            visited.put(key, -1);
            ap.put(key, 0);
        }

        allNodes = nodes.keySet().iterator();
        while (allNodes.hasNext()) {
            int key = allNodes.next();
            if (visited.get(key) == -1) {
                APF(key, visited, disc, low, parent, ap);
            }
        }

        allNodes = nodes.keySet().iterator();
        while (allNodes.hasNext()) {
            int key = allNodes.next();
            if (ap.get(key) == 1) {
                GG.printlnConsole(key + " is a cut vertex");
                cutV.add(key);
                cutExist = true;
            }
        }

        if (cutExist == false) {
            GG.printlnConsole("No cut vertex in Graph");
        }
    }

    void dfs(int source) {
        nodes = GG.getNode();
        distTo = new HashMap<>();
        visited = new HashMap<>();

        Iterator<Integer> allNodes = nodes.keySet().iterator();
        while (allNodes.hasNext()) {
            int key = allNodes.next();
            visited.put(key, -1);
            distTo.put(key, 0);
        }

        bconn = new ArrayList<>();
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
            Iterator<Integer> l = iList.iterator();
            while (l.hasNext()) {
                int n = l.next();
                if (visited.get(n) == -1) {
                    GG.printlnConsole("Pushing " + n);
                    stack.push(n);
                    visited.put(n, element);
                    distTo.put(n, distTo.get(element) + 1);
                    break;
                }
                if (l.hasNext() == false) {
                    int backEdge = stack.pop();
                    GG.printlnConsole("Back edge " + backEdge);
                }
            }
        }
        GG.printlnConsole("order is " + bconn);
    }

    void bfs(int source) {
        nodes = GG.getNode();
        distTo = new HashMap<>();
        visited = new HashMap<>();
        Iterator<Integer> allNodes = nodes.keySet().iterator();
        while (allNodes.hasNext()) {
            int key = allNodes.next();
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
            Iterator<Integer> l = iList.iterator();
            while (l.hasNext()) {
                int n = l.next();
                if (visited.get(n) == -1) {
                    queue.add(n);
                    visited.put(n, i);
                    distTo.put(n, distTo.get(i) + 1);
                }
            }
        }
        GG.printlnConsole("Order is " + conn);
    }
    

    void makeTree(int source) {
        nodes = GraphifyGUI.getNode();
        visited = new HashMap<>();
        boolean isTree = false;
        HashMap<Integer, Boolean> isVisited = new HashMap<>();
        HashSet<Integer> bconn = new HashSet<>();
        Iterator<Integer> allNodes = nodes.keySet().iterator();
        while (allNodes.hasNext()) {
            int key = allNodes.next();
            visited.put(key, -1);
            isVisited.put(key, false);
        }
        int element;
        visited.put(source, 0); // start vertex
        stack.push(source);
        while (!stack.isEmpty()) {
            element = stack.peek();
            //GG.printlnConsole("Considering element " + element);
            bconn.add(element);
            HashSet<Integer> iList = getEdge(element);
            Iterator<Integer> l = iList.iterator();
            while (l.hasNext()) {
                int n = l.next();
                if (visited.get(n) == -1) {
                   // GG.printlnConsole("Pushing " + n);
                    isVisited.put(n, true);
                    stack.push(n);
                    visited.put(n, element);
                    break;
                }else if(visited.get(element) != n && visited.get(n) != element && n != source){
                    isTree = true;
                    l.remove();
                    getEdge(n).remove(element);
                    GG.printlnConsole("Removing connection between "+n+ " and "+element);
                }
                if (l.hasNext() == false) {
                    stack.pop(); // not necessarily a back edge
                }
            }
        }
        if(isTree == false){
            GG.printlnConsole("Graph is already a tree");
        }else{
            GG.printlnConsole("Graph is not a tree\nMaking it a tree ...");
        }
        GG.printlnConsole("order is " + bconn);
    }

    boolean isConnected() {
        dfs(_source);
        Iterator<Integer> allNode = nodes.keySet().iterator();
        while (allNode.hasNext()) {
            int key = allNode.next();
            if (visited.get(key) == -1) {
                return false;
            }
        }
        return true;
    }

    boolean isEulerian() {
        int noOfOdds = 0;
        if (isConnected()) {
            Iterator<Integer> allNode = nodes.keySet().iterator();
            while (allNode.hasNext()) {
                int key = allNode.next();
                int keyEdgeSize = getEdge(key).size();
                if (keyEdgeSize % 2 != 0 && keyEdgeSize != 0) {
                    noOfOdds++;
                }
            }
        } else {
            return false;
        }
        if (noOfOdds == 0) {
            return true;
        }
        if (noOfOdds == 2) {
            GG.printlnConsole("There is an euler path");
        }
        return false;
    }

    void greedyColoring(int nc) {
        nodes = GG.getNode();
        HashMap<Integer, Integer> available = new HashMap<Integer, Integer>();
        Iterator<Integer> allNode = nodes.keySet().iterator();
        while (allNode.hasNext()) {
            int key = allNode.next();
            greedyresult.put(key, -1);
            available.put(key, 0); //set all to false
        }
        greedyresult.put(_source, 0);
        allNode = nodes.keySet().iterator();
        while (allNode.hasNext()) {
            int key = allNode.next();
            HashSet<Integer> kList = getEdge(key);
            Iterator<Integer> u = kList.iterator();
            while (u.hasNext()) {
                int k = u.next();
                if (greedyresult.get(k) != -1) {
                    available.put(greedyresult.get(k), 1);
                }
            }
            Integer nColor = 0;
            Iterator<Integer> allNodes = nodes.keySet().iterator();
            while (allNodes.hasNext()) {
                nColor = allNodes.next();
                if (available.get(nColor) == 0) {
                    break;
                }
            }
            greedyresult.put(key, nColor);
            if (greedyresult.get(key) > maxColors) {
                maxColors = nColor;
            }
            u = kList.iterator();
            while (u.hasNext()) {
                int k = u.next();
                if (greedyresult.get(k) != -1) {
                    available.put(greedyresult.get(k), 0);
                }
            }
        }

        Iterator<Integer> allNodes = nodes.keySet().iterator();
        while (allNodes.hasNext()) {
            int key = allNodes.next();
            GG.printlnConsole("Vertex " + key + " ---> Color " + greedyresult.get(key));
        }
    }

    void Bipartite(int source) { // will test for 3
        nodes = GG.getNode();
        int V = nodes.size();
        color = new HashMap<Integer, Integer>();
        _colors2 = new HashSet<Integer>();
        Iterator<Integer> allNodes = nodes.keySet().iterator();
        while (allNodes.hasNext()) {
            int key = allNodes.next();
            color.put(key, -1);
        }
        int element;
        color.put(source, 1); // start first color with 1, all adjacent to 1 should have color 0
        queue.add(source);
        while (!queue.isEmpty()) {
            element = queue.remove();
            HashSet<Integer> iList = getEdge(element);
            Iterator<Integer> x = iList.iterator();
            while (x.hasNext()) {
                int key = x.next();
                if (Objects.equals(color.get(element), color.get(key))) {
                    _colors2.add(element);
                    _colors2.add(key);
                } else if (color.get(key) == -1) {
                    color.put(key, 1 - color.get(element));
                    queue.add(key);
                }
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

    public Queue getQueue() {
        return this.queue;
    }

    public Stack getStack() {
        return this.stack;
    }

    public HashMap getGlowMap() {
        return this.glowMap;
    }

    public HashMap distTo() {
        return this.distTo;
    }

    public HashMap getSet() {
        return this.set;
    }

    public HashMap getVisited() {
        return this.visited;
    }

    public HashMap getColor() {
        return this.color;
    }

    public HashMap getGreedyResult() {
        return this.greedyresult;
    }

    public HashSet getColors2() {
        return this._colors2;
    }

    public ArrayList getConn() {
        return this.conn;
    }

    public ArrayList getBConn() {
        return this.bconn;
    }

    public ArrayList getCutV() {
        return this.cutV;
    }

    public Color[] getVertexColors() {
        return this.vertexColors;
    }

}
