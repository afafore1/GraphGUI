/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphify;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
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
    private HashMap<Integer, Integer> glowMap = new HashMap<>();
    HashMap<Integer, HashSet<Integer>> nodes;
    private Queue<Integer> queue;

    private Stack<Vertex> stack;
    private HashMap<Integer, Integer> distTo;
    private HashMap<Integer, Integer> set = new HashMap<>();
    private HashMap<Integer, Integer> visited;
    private HashMap<Integer, Vertex> color;
    private HashMap<Integer, Integer> greedyresult;
    private HashSet<Integer> _colors2;
    private static ArrayList<Integer> conn;
    private HashSet<Vertex> bconn;
    private ArrayList<Integer> cutV;
    private Color[] vertexColors;
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
        this.GG = GG;
        Algorithms.vertex = new HashMap<>();
        Algorithms.edges = new ArrayList<>();
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

    public HashSet<Vertex> getEdge(int source) {
        vertex = GraphifyGUI.getNode();
        return vertex.get(source).vList();
    }
    
    private int getWeight(Vertex s, Vertex d){
        for(Edge e : edges){
            if(e.getSource().equals(s) && e.getDest().equals(d)){
                return e.getWeight();
            }
        }
        return -1; // edge does not exist then
    }

    void APF(int u, HashMap<Integer, Integer> visited, HashMap<Integer, Integer> disc, HashMap<Integer, Integer> low, HashMap<Integer, Integer> parent, HashMap<Integer, Integer> ap) {
        int children = 0;
        visited.put(u, 0);
        time = ++time;
        disc.put(u, time);
        low.put(u, time);
        Iterator<Vertex> i = getEdge(u).iterator();

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
            Iterator<Vertex> currentList = current.vList().iterator();
            while (currentList.hasNext()) {
                Vertex next = currentList.next();
                if (!next.wasVisited) { // visited just one at a time
                    GG.printlnConsole("Pushing " + next.getName());
                    stack.push(next);
                    next.parent = current;
                    break;
                }
                if (!currentList.hasNext()) {
                    Vertex backEdge = stack.pop();
                    GG.printlnConsole("Back edge " + backEdge.getName());
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
            Iterator<Vertex> currentList = current.vList().iterator();
            while (currentList.hasNext()) {
                Vertex next = currentList.next();
                if (next.wasVisited == false) {
                    next.wasVisited = true;
                    q.add(next);
                    next.parent = current;
                   // GG.printlnConsole(next.getName() + " has type of " + next.getType());
                }
            }
        }
        GG.printlnConsole("Order is " + conn);
    }
    
    //dijsktra
    public void execute(Vertex source){
        vertex = GraphifyGUI.getNode();
        edges = GraphifyGUI.getEdges();
        reset();
        sNodes = new HashSet<>();
        uSNodes = new HashSet<>();
        dist = new HashMap<>();
        parents = new HashMap<>();
        dist.put(source, 0);
        uSNodes.add(source);
        while(uSNodes.size()> 0){
            Vertex v = getMin(uSNodes);
            sNodes.add(v);
            uSNodes.remove(v);
            findMinDist(v);
        }
    }

    //settled nodes
    private boolean isSettled(Vertex v){
        return sNodes.contains(v);
    }
    //getNeighbors
    private List<Vertex> getNeighbors(Vertex v){
        List<Vertex> neighbors = new ArrayList<>();
        HashSet<Vertex> n = getEdge(v.getId());
        Iterator<Vertex> neighb = n.iterator();
        while(neighb.hasNext()){
            Vertex next = neighb.next();
            if(!isSettled(next)){
                neighbors.add(next);
            }
        }
        return neighbors;
    }
    // find min distance
    private void findMinDist(Vertex v){
        List<Vertex> neighbors = getNeighbors(v);
        for(Vertex t : neighbors){
            if(GSD(t) > GSD(v)+ getWeight(v,t)){
                dist.put(t, GSD(v) + getWeight(v,t));
                t.parent = v;
                uSNodes.add(t);
            }
        }
    }
    
    private Vertex getMin(HashSet<Vertex> v){
        Vertex min = null;
        for(Vertex vert : v){
            if(min == null){
                min = vert;
            }else{
                if(GSD(vert) < GSD(min)){
                    min = vert;
                }
            }
        }
        return min;
    }
    
    private int GSD(Vertex d){
        Integer distance = dist.get(d);
        if(distance == null){
            return Integer.MAX_VALUE;
        }else{
            return distance;
        }
    }
    
    public void findPath(Vertex dest){
        set.clear();
        Vertex s = dest;
        if(parents.get(s) == null){
            GG.printlnConsole("No path exist");
            return;
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
            Iterator<Vertex> currentList = current.vList().iterator();
            while (currentList.hasNext()) {
                Vertex next = currentList.next();
                if (next.wasVisited == false) {
                    next.wasVisited = true;
                    q.add(next);
                    next.parent = current;
                    if (!source.vList().contains(next)) {
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
                                    }else{
                                        conn.add(next.getId());
                                        for(int i = 1; i < conn.size(); i++){
                                            for(int j = i; j > 0; j--){
                                                if(vertex.get(conn.get(j)).getRating() < vertex.get(conn.get(j-1)).getRating()){
                                                    conn.add(j, conn.get(j-1));
                                                    conn.add(j-1, conn.get(j));
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
        GG.printlnConsole(glowMap.toString());
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

    boolean isEulerian() {
        int noOfOdds = 0;
        if (isConnected()) {
            Iterator<Vertex> allNode = vertex.values().iterator();
            while (allNode.hasNext()) {
                Vertex key = allNode.next();
                int keyEdgeSize = key.vList().size();
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

    public HashSet getBConn() {
        return this.bconn;
    }

    public ArrayList getCutV() {
        return this.cutV;
    }

    public Color[] getVertexColors() {
        return this.vertexColors;
    }

}
