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
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Ayomitunde
 */
public class Algorithms {

    private static GraphifyGUI GG;
    private static HashMap<Integer, Vertex> vertex;
    private static ArrayList<Edge> edges;
    private static Queue<Vertex> q;
    HashMap<Integer, Integer> connectionCache = new HashMap<>();
    private final HashMap<Integer, Integer> glowMap = new HashMap<>();
    HashMap<Integer, HashSet<Integer>> nodes;
    private Queue<Integer> queue;
    private Stack<Vertex> stack;
    private HashMap<Integer, Integer> distTo;
    private HashMap<Integer, Integer> set = new HashMap<>();
    private HashMap<Integer, Integer> visited;
    private static ArrayList<Integer> conn;
    private HashSet<Vertex> bconn;
    private ArrayList<Integer> cutV;
    int _selectedNode = -1;
    int _SIZE_OF_NODE = 20;
    int id = 0;
    int time = 0;
    Integer maxColors = 0;
    int _source;
    int _dest;
    //for dijkstra
    private HashSet<Vertex> uSNodes; // unsettled
    private HashSet<Vertex> sNodes; // settled
    private HashMap<Vertex, Vertex> parents;
    private HashMap<Vertex, Integer> dist; // distance

    public Algorithms(GraphifyGUI GG) {
        Algorithms.GG = GG;
        Algorithms.vertex = new HashMap<>();
        Algorithms.edges = new ArrayList<>();
        this.nodes = new HashMap<>();
        this.queue = new LinkedList<>();
        this.stack = new Stack<>();
        this.cutV = new ArrayList<>();
        this.visited = new HashMap<>();
        this.set = new HashMap<>();
        this.visited = new HashMap<>();
    }

    public HashSet<Edge> getEdge(int source) {
        vertex = GraphifyGUI.getNode();
        return vertex.get(source).eList();
    }

    void APF(int u, HashMap<Integer, Integer> visited, HashMap<Integer, Integer> disc, HashMap<Integer, Integer> low, HashMap<Integer, Integer> parent, HashMap<Integer, Integer> ap) {
        int children = 0;
        visited.put(u, 0);
        time = ++time;
        disc.put(u, time);
        low.put(u, time);
        Iterator<Edge> i = getEdge(u).iterator();

        while (i.hasNext()) {
            int v = i.next().getId(); // v is current adj to u
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
        vertex = GraphifyGUI.getNode();
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

    public void Dfs(Vertex source) {
        vertex = GraphifyGUI.getNode();
        edges = GraphifyGUI.getEdges();
        reset();
        stack = new Stack<>();
        bconn = new HashSet<>();
        source.wasVisited = true;
        source.parent = source;
        stack.push(source);
        while (!stack.isEmpty()) {
            Vertex current = stack.peek();
            GG.printlnConsole("Considering element " + current.getName());
            bconn.add(current);
            Iterator<Edge> currentList = current.eList().iterator();
            while (currentList.hasNext()) {
                Edge t = currentList.next();
                if (!t.isFailed()) {
                    Vertex next = getConn(current, t);
                    if (!next.wasVisited) { // visited just one at a time
                        GG.printlnConsole("Pushing " + next.getName());
                        stack.push(next);
                        next.parent = current;
                        next.wasVisited = true;
                        break;
                    }
                    if (!currentList.hasNext()) {
                        Vertex backEdge = stack.pop();
                        GG.printlnConsole("Back edge " + backEdge.getName());
                    }
                } else {
                    stack.pop();
                }
            }
        }
        GG.printlnConsole("order is " + bconn);
    }

    public static void Bfs(Vertex source) {
        vertex = GraphifyGUI.getNode();
        edges = GraphifyGUI.getEdges();
        reset();
        q = new LinkedList<>(); // FIFO
        source.wasVisited = true; // marked as visited
        q.add(source); // put into queue
        source.parent = source; // set parent
        conn = new ArrayList<>();
        while (!q.isEmpty()) { // source
            Vertex current = q.poll(); // remove first 
            conn.add(current.getId());
            Iterator<Edge> currentList = current.eList().iterator();
            while (currentList.hasNext()) {
                Edge t = currentList.next();
                if (!t.isFailed()) {
                    Vertex next = getConn(current, t);
                    if (next.wasVisited == false) {
                        next.wasVisited = true;
                        q.add(next);
                        next.parent = current;
                    }
                }
            }
        }
        GG.printlnConsole("Order is " + conn);
    }

    static Vertex getConn(Vertex s, Edge e) {
        if (s == e.getSource()) {
            return e.getDest();
        } else {
            return e.getSource();
        }
    }

    //dijsktra
    public void execute(Vertex source) {
        // get vertices and edges from GUI
        vertex = GraphifyGUI.getNode();
        edges = GraphifyGUI.getEdges();
        reset();
        sNodes = new HashSet<>(); // settled nodes will be placed in this set
        uSNodes = new HashSet<>(); // unsettled nodes will be placed in this set
        dist = new HashMap<>(); // weight to get to node
        parents = new HashMap<>(); // parent/nodes we came from
        dist.put(source, 0); // first set source to 0
        uSNodes.add(source); // add source to unsettled nodes
        while (uSNodes.size() > 0) { // do this until no more unsettled nodes
            Vertex v = getMin(uSNodes); // we use min node from unsettled nodes each time to process
            sNodes.add(v); // add it to settled nodes
            uSNodes.remove(v); // remove it
            findMinDist(v); // find min distance
        }
    }

    //settled nodes
    private boolean isSettled(Vertex v) {
        return sNodes.contains(v);
    }

    // get weight... Weight is a combination of the actual weight + pAmount on that edge
    private int getWeight(Vertex s, Vertex d) {
        for (Edge e : edges) {
            if (e.getSource() == s && e.getDest() == d || e.getSource() == d && e.getDest() == s) {
                return e.getWeight();
            }
        }
        return -1; // edge does not exist then
    }

    //getNeighbors
    private List<Vertex> getNeighbors(Vertex v) {
        List<Vertex> neighbors = new ArrayList<>();
        HashSet<Edge> n = getEdge(v.getId());
        Iterator<Edge> neighb = n.iterator();
        while (neighb.hasNext()) {
            Edge t = neighb.next();
            if (!t.isFailed()) {
                Vertex next = getConn(v, t);
                if (!isSettled(next)) {
                    neighbors.add(next);
                }
            }
        }
        return neighbors;
    }

    // find min distance
    private void findMinDist(Vertex v) {
        int combWeight = 0;
        List<Vertex> neighbors = getNeighbors(v);
        Iterator<Vertex> vert = neighbors.iterator();
        while (vert.hasNext()) {
            Vertex t = vert.next();
            combWeight = GSD(v) + getWeight(v, t);
            if (GSD(t) > combWeight) {
                dist.put(t, GSD(v) + getWeight(v, t));
                t.parent = v;
                uSNodes.add(t);
            }
        }
        GG.printlnConsole(""+combWeight);
    }

    private Vertex getMin(HashSet<Vertex> v) {
        Vertex min = null;
        for (Vertex vert : v) {
            if (min == null) {
                min = vert;
            } else if (GSD(vert) < GSD(min)) {
                min = vert;
            }
        }
        return min;
    }

    private int GSD(Vertex d) {
        Integer distance = dist.get(d);
        if (distance == null) {
            return Integer.MAX_VALUE;
        } else {
            return distance;
        }
    }

    public static ArrayList BfsSuggest(Vertex source, int num) {
        vertex = GraphifyGUI.getNode();
        reset();
        q = new LinkedList<>(); // FIFO
        source.wasVisited = true; // marked as visited
        q.add(source); // put into queue
        source.parent = source; // set parent
        conn = new ArrayList<>();
        while (!q.isEmpty()) { // source
            Vertex current = q.poll(); // remove first 
            Iterator<Edge> currentList = current.eList().iterator();
            while (currentList.hasNext()) {
                Edge t = currentList.next();
                Vertex next = getConn(current, t);
                if (next.wasVisited == false) {
                    next.wasVisited = true;
                    q.add(next);
                    next.parent = current;
                    if (!source.eList().contains(t)) {
                        switch (num) {
                            case 0:
                                if (next.getType().equals(Types.Person.toString())) {
                                    if (conn.size() <= 5) {
                                        conn.add(next.getId());
                                    }
                                }
                                break;
                            case 1:
                                if (next.getType().equals(Types.Place.toString())) {
                                    if (conn.size() <= 5) {
                                        conn.add(next.getId());
                                    }
                                }
                                break;
                            case 2:
                                if (next.getType().equals(Types.City.toString())) {
                                    if (conn.size() <= 5) {
                                        conn.add(next.getId());
                                    }
                                }
                                break;
                            case 3:
                                if (next.getType().equals(Types.Place.toString())) {
                                    if (conn.isEmpty()) { // keep sorted always
                                        GG.printlnConsole("adding " + next.getId());
                                        conn.add(next.getId());
                                    } else {
                                        conn.add(next.getId());
                                        for (int i = 1; i < conn.size(); i++) {
                                            for (int j = i; j > 0; j--) {
                                                if (vertex.get(conn.get(j)).getCapacity() < vertex.get(conn.get(j - 1)).getCapacity()) {
                                                    conn.add(j, conn.get(j - 1));
                                                    conn.add(j - 1, conn.get(j));
                                                }
                                            }
                                        }
                                    }
                                }
                            default:
                                break;
                        }

                    }
                }
            }
        }

        return conn;
    }

    public void shortestPath(int v, int e) {
        if (e == v) {
            GG.printlnConsole(v + "-->" + v);
            return;
        }
        for (int i = e; i >= 0; i = vertex.get(i).getParent().getId()) {
            if (i == v) {
                break;
            }
            if (vertex.get(i).getParent().getId() != -1) {
                set.put(vertex.get(i).getParent().getId(), i);
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

    static void reset() {
        vertex = GraphifyGUI.getNode();
        Iterator<Vertex> vert = vertex.values().iterator();
        while (vert.hasNext()) {
            Vertex v = vert.next();
            v.wasVisited = false;
        }
    }

    boolean isConnected() {
        Vertex s = (Vertex) GraphifyGUI.getNode().get(_source);
        Bfs(s);
        Iterator<Vertex> vert = vertex.values().iterator();
        while (vert.hasNext()) {
            Vertex key = vert.next();
            if (!key.wasVisited) {
                return false;
            }
        }
        return true;
    }

    public int hasPath(int v) {
        return visited.get(v);
    }

    public int distTo(int v) {
        return distTo.get(v);
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

    public ArrayList getConn() {
        return Algorithms.conn;
    }

    public HashSet getBConn() {
        return this.bconn;
    }

    public ArrayList getCutV() {
        return this.cutV;
    }

}
