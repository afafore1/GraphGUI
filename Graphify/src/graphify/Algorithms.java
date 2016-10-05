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
import java.util.Stack;

/**
 *
 * @author Ayomitunde
 */
public class Algorithms {

    static boolean pop = true;
    /**
     *
     * @param u
     * @param visited
     * @param disc
     * @param low
     * @param parent
     * @param ap
     */
    public static void APF(int u, HashMap<Integer, Integer> visited, HashMap<Integer, Integer> disc, HashMap<Integer, Integer> low, HashMap<Integer, Integer> parent, HashMap<Integer, Integer> ap) {
        int children = 0;
        Model.visited.put(u, 0);
        Model.time = ++Model.time;
        disc.put(u, Model.time);
        low.put(u, Model.time);
        Iterator<Edge> i = Model.vertices.get(u).eList().iterator();

        while (i.hasNext()) {
            int v = i.next().getId(); // v is current adj to u
            if (Model.visited.get(v) == -1) {
                children++;
                parent.put(v, u);
                APF(v, Model.visited, disc, low, parent, ap); // recursive for it
                int val = Math.min(low.get(u), low.get(v));
                low.put(u, val);

                if (u == Model._source && children > 1) {
                    ap.put(u, 1);
                }
                // if u is not root and low value of one of its child is more than discovery value of u
                if (u != Model._source && low.get(v) >= disc.get(u)) { // need a check for this if statement.. always marks beginning as a cut even when it's not
                    ap.put(u, 1);
                }
            } else if (v != parent.get(u)) {
                int val = Math.min(low.get(u), low.get(v));
                low.put(u, val);
            }
        }
    }

    public static void AP() {
        Model.visited = new HashMap<>();
        boolean cutExist = false;
        HashMap<Integer, Integer> disc = new HashMap<>();
        HashMap<Integer, Integer> low = new HashMap<>();
        HashMap<Integer, Integer> parent = new HashMap<>();
        HashMap<Integer, Integer> ap = new HashMap<>();

        Iterator<Integer> allNodes = Model.vertices.keySet().iterator();
        while (allNodes.hasNext()) {
            int key = allNodes.next();
            parent.put(key, -1);
            Model.visited.put(key, -1);
            ap.put(key, 0);
        }

        allNodes = Model.vertices.keySet().iterator();
        while (allNodes.hasNext()) {
            int key = allNodes.next();
            if (Model.visited.get(key) == -1) {
                APF(key, Model.visited, disc, low, parent, ap);
            }
        }

        allNodes = Model.vertices.keySet().iterator();
        while (allNodes.hasNext()) {
            int key = allNodes.next();
            if (ap.get(key) == 1) {
                Model.Gui.printlnConsole(key + " is a cut vertex");
                Model.cutV.add(key);
                cutExist = true;
            }
        }

        if (cutExist == false) {
            Model.Gui.printlnConsole("No cut vertex in Graph");
        }
    }

    public static void Dfs(Vertex source) {
        reset();
        Model.stack = new Stack<>();
        Model.bconn = new HashSet<>();
        source.setVisited(true);
        source.parent = source;
        Model.stack.push(source);
        while (!Model.stack.isEmpty()) {
            Vertex current = Model.stack.peek();
            Model.Gui.printlnConsole("Considering element " + current.getName());
            Model.bconn.add(current.getName());
            if (current.eList().isEmpty()) {
                Model.Gui.printlnConsole("Removing " + Model.stack.pop());
            } else {
                for (Iterator<Edge> currentList = current.eList().iterator(); currentList.hasNext();) {
                    IEdge t = currentList.next();
                    if (!t.isFailed()) {
                        Vertex next = getConn(current, t);
                        if (next.visited() == false) { // visited just one at a time
                            Model.Gui.printlnConsole("Pushing " + next.getName());
                            Model.stack.push(next);
                            next.parent = current;
                            next.setVisited(true);
                            break;
                        }
                        if (!currentList.hasNext()) {
                            Vertex backEdge = Model.stack.pop();
                            Model.Gui.printlnConsole("Back edge " + backEdge.getName());
                        }
                    } else {
                        Model.stack.pop();
                    }
                }
            }
        }
        Model.Gui.printlnConsole("order is " + Model.bconn);
    }

    public static void Bfs(Vertex source) {
        reset();
        Model.queue = new LinkedList<>(); // FIFO [1,2
        source.setVisited(true); // marked as Model.visited [0,1,2
        Model.queue.add(source); // put into queue
        source.parent = source; // setShortestPath parent
        Model.conn = new ArrayList<>();
        while (!Model.queue.isEmpty()) { // source
            Vertex current = Model.queue.poll(); // remove first 0
            Model.conn.add(current.getId());
            for (Iterator<Edge> currentList = current.eList().iterator(); currentList.hasNext();) {
                Edge t = currentList.next();
                Vertex next = getConn(current, t);
                if (!t.isFailed()) {
                    if (!next.visited()) {
                        next.setVisited(true);
                        Model.queue.add(next);
                        next.parent = current;
                    }
                }
            }
        }
        Model.Gui.printlnConsole("Order is " + Model.conn);
    }

    public static void NearestNeighbor() {
        reset();
        Model.startTime = System.currentTimeMillis();
        Model.stack = new Stack();
        // start with a random Node
        int random = (int) (Math.random() * Model.vertices.size());
        Vertex startNode = Model.vertices.get(random);
        startNode.setVisited(true);
        Model.stack.push(startNode);
        while (!Model.stack.isEmpty()) {
            startNode = nextPath(startNode);
            if(startNode == null){
                printSolution();
            }
        }
        Model.endTime = System.currentTimeMillis();
        GraphifyGUI.lblTimeTaken.setText(String.valueOf((Model.endTime - Model.startTime))+" ms");
    }
    
    public static void startGANN(Population pop){
        Tour [] tours = pop.getTours();
        int size = pop.populationSize() - 1;
        for(int i = 0; i < size; i++){
            Tour t = tours[i];
            t = GANearestNeighbor(t, size);
            pop.saveTour(i, t);
        }
    }

    public static Tour GANearestNeighbor(Tour tour, int populationSize){
        reset();
        Model.stack = new Stack();
        int random = (int) (Math.random() * Model.vertices.size());
        Vertex startNode = Model.vertices.get(random);
        startNode.setVisited(true);
        Model.stack.push(startNode);
        while (!Model.stack.isEmpty()) {
            startNode = nextPath(startNode);
            if(startNode == null){
                while(!Model.stack.isEmpty()){
                    Vertex current = Model.stack.pop();
                    tour.setVertex(populationSize, current);
                    populationSize--;
                }
            }
        }
        return tour;
    }
    static void printSolution(){
        Model.glowMap.clear();
        Vertex last = Model.stack.peek();
        Model.FinalDistanceValue = 0;
        while(!Model.stack.isEmpty()){
            Vertex current = Model.stack.pop();
            Vertex next = Model.stack.isEmpty() ? last : Model.stack.peek();
            Model.glowMap.put(current, next);
            Model.FinalDistanceValue += GetEdgeWeight(current, next);
            Model.Gui.printConsole("Vertex "+ current.label+" -> ");
        }
    }
    private static Vertex nextPath(Vertex source) {
        Vertex n = null;
        int leastWeight = Integer.MAX_VALUE;
        for (Edge e : source.eList()) {
            if (e.getWeight() < leastWeight) {
                if (e.getDest().visited() == false) {
                    leastWeight = e.getWeight();
                    n = e.getDest();
                    pop = false;
                }
            }
        }
        if(n != null){
            n.setVisited(true);
            Model.stack.push(n);
        }
        return n;
    }

    public static boolean pathExist(Vertex source, Vertex dest) {
        Model.vertices.values().stream().forEach((v) -> {
            v.setVisited(false);
        });
        Model.queue = new LinkedList<>(); // FIFO
        source.setVisited(true); // marked as Model.visited
        Model.queue.add(source); // put into queue
        while (!Model.queue.isEmpty()) { // source
            Vertex current = Model.queue.poll(); // remove first 
            for (Iterator<Edge> currentList = current.eList().iterator(); currentList.hasNext();) {
                Edge t = currentList.next();
                if (!t.isFailed()) {
                    Vertex next = getConn(current, t);
                    if (next == dest) {
                        return true; // a path exist
                    } else if (next.visited() == false) {
                        next.setVisited(true);
                        Model.queue.add(next);
                    }
                }
            }
        }
        return false;
    }

    public static void disjointPathBfs(Vertex source) {
        Model.vertices.values().stream().forEach((v) -> {
            v.setVisited(false);
        });
        Model.tempShortPath = new HashMap<>();
        Model.queue = new LinkedList<>(); // FIFO
        source.setVisited(true); // marked as Model.visited
        Model.queue.add(source); // put into queue
        source.parent = source; // setShortestPath parent
        Model.conn = new ArrayList<>();
        while (!Model.queue.isEmpty()) { // source
            Vertex current = Model.queue.poll(); // remove first 
            Model.conn.add(current.getId());
            for (Iterator<Edge> currentList = current.eList().iterator(); currentList.hasNext();) {
                Edge t = currentList.next();
                Vertex next = getConn(current, t);
                if (!t.isFailed()) {
                    if (next.visited() == false) {
                        next.setVisited(true);
                        Model.queue.add(next);
                        next.parent = current;
                    }
                }
            }
        }
        Model.Gui.printlnConsole("Order is " + Model.conn);
    }

    public static Edge getEdge(Vertex s, Vertex d) {
        for (Edge edge : Model.edges) {
            if (edge.getSource() == s && edge.getDest() == d) {
                return edge;
            }
        }
        return null;
    }
    
    public static int GetEdgeWeight(Vertex s, Vertex d){
        for(Edge edge : s.eList()){
            if(edge.getDest().equals(d)){
                return edge.getWeight();
            }
        }
        return -1;
    }

    public static void disJointshortestPath(int v, int e) {
        while (pathExist(Model.vertices.get(v), Model.vertices.get(e)) == true) {
            System.out.println("Running ..");
            disjointPathBfs(Model.vertices.get(v));
            System.out.println("done!\nStarting shortest path");
            for (int i = e; i >= 0; i = Model.vertices.get(i).getParent().getId()) {
                if (i == v) {
                    break;
                }
                if (Model.vertices.get(i).getParent().getId() != -1) {
                    Edge failedge = getEdge(Model.vertices.get(i).getParent(), Model.vertices.get(i));
                    if (failedge != null) {
                        failedge.setFailed(true);
                    }
                    Model.tempShortPath.put(Model.vertices.get(i).getParent(), Model.vertices.get(i));
                }
            }
            Model.glowMap.clear();
            Model.glowMap = (HashMap) Model.tempShortPath.clone();
            Model.Gui.graph();
            Model.disjointPaths.put(Model.pathvalue, Model.tempShortPath);
            Model.pathvalue++;
        }
        System.out.println("No path exist!");
        System.out.println("paths are " + Model.disjointPaths);

    }

    /**
     *
     * @param s passed in vertex
     * @param e edge associated with vertex s
     * @return get destination of vertex passed in from edge
     */
    public static Vertex getConn(Vertex s, IEdge e) {
        if (e.getBidirectional()) {
            if (e.getSource() == s) {
                return e.getDest();
            } else {
                return e.getSource();
            }
        } else {
            return e.getDest();
        }
    }

    //dijsktra
    public static void execute(Vertex source) {
        // get vertices and edges from GUI
        Model.sNodes = new HashSet<>(); // settled Model.vertices will be placed in this setShortestPath
        Model.uSNodes = new HashSet<>(); // unsettled Model.vertices will be placed in this setShortestPath
        Model.dist = new HashMap<>(); // weight to get to node
        reset();
        Model.dist.put(source, 0); // first setShortestPath source to 0
        Model.uSNodes.add(source); // add source to unsettled Model.vertices
        while (Model.uSNodes.size() > 0) { // do this until no more unsettled Model.vertices
            Vertex v = getMin(Model.uSNodes); // we use min node from unsettled Model.vertices each time to process
            Model.sNodes.add(v); // add it to settled Model.vertices
            Model.uSNodes.remove(v); // remove it
            findMinDist(v); // find min distance
        }
    }

    //settled Model.vertices
    public static boolean isSettled(Vertex v) {
        return Model.sNodes.contains(v);
    }

    //returns weight
    public static int getWeight(Vertex s, Vertex d) {
        for (Edge e : Model.edges) {
            if (e.getSource() == s && e.getDest() == d || e.getSource() == d && e.getDest() == s) {
                return e.getWeight();
            }
        }
        return -1; // edge does not exist then
    }

    //getNeighbors
    public static List<Vertex> getNeighbors(Vertex v) {
        List<Vertex> neighbors = new ArrayList<>();
        HashSet<Edge> n = Model.vertices.get(v.getId()).eList();
        Iterator<Edge> neighb = n.iterator();
        while (neighb.hasNext()) {
            IEdge t = neighb.next();
            if (!Model.failed.contains(t.getDest())) {
                Vertex next = getConn(v, t);
                if (!isSettled(next)) {
                    neighbors.add(next);
                }
            }
        }
        return neighbors;
    }

    // find min distance
    public static void findMinDist(Vertex v) {
        List<Vertex> neighbors = getNeighbors(v);
        neighbors.stream().forEach((Vertex t) -> {
            int combWeight = GSD(v) + getWeight(v, t);
            /*getWeight*/ if (GSD(t) > combWeight) {
                Model.dist.put(t, GSD(v) + getWeight(v, t));
                t.parent = v;
                Model.uSNodes.add(t);
            }
        });
    }

    public static Vertex getMin(HashSet<Vertex> v) {
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

    public static int GSD(Vertex d) {
        Integer distance = Model.dist.get(d);
        if (distance == null) {
            return Integer.MAX_VALUE;
        } else {
            return distance;
        }
    }

    public static ArrayList BfsSuggeest(Vertex source, int num) {
        reset();
        Model.suggestQueue = new LinkedList<>(); // FIFO
        source.setVisited(true); // marked as Model.visited
        Model.suggestQueue.add(source); // put into queue
        source.parent = source; // setShortestPath parent
        Model.conn = new ArrayList<>();
        while (!Model.suggestQueue.isEmpty()) { // source
            Vertex current = Model.suggestQueue.poll(); // remove first 
            Iterator<Edge> currentList = current.eList().iterator();
            while (currentList.hasNext()) {
                IEdge t = currentList.next();
                Vertex next = getConn(current, t);
                if (next.visited() == false) {
                    next.setVisited(true);
                    Model.suggestQueue.add(next);
                    next.parent = current;
                    if (!source.eList().contains(t)) {
                        switch (num) {
                            case 0:
                                if (next.getType().equals(Types.Person.toString())) {
                                    if (Model.conn.size() <= 5) {
                                        Model.conn.add(next.getId());
                                    }
                                }
                                break;
                            case 1:
                                if (next.getType().equals(Types.Place.toString())) {
                                    if (Model.conn.size() <= 5) {
                                        Model.conn.add(next.getId());
                                    }
                                }
                                break;
                            case 2:
                                if (next.getType().equals(Types.City.toString())) {
                                    if (Model.conn.size() <= 5) {
                                        Model.conn.add(next.getId());
                                    }
                                }
                                break;
                            case 3:
                                if (next.getType().equals(Types.Place.toString())) {
                                    if (Model.conn.isEmpty()) { // keep sorted always
                                        Model.Gui.printlnConsole("adding " + next.getId());
                                        Model.conn.add(next.getId());
                                    } else {
                                        Model.conn.add(next.getId());
                                        for (int i = 1; i < Model.conn.size(); i++) {
                                            for (int j = i; j > 0; j--) {
                                                if (Model.vertices.get(Model.conn.get(j)).getCapacity() < Model.vertices.get(Model.conn.get(j - 1)).getCapacity()) {
                                                    Model.conn.add(j, Model.conn.get(j - 1));
                                                    Model.conn.add(j - 1, Model.conn.get(j));
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

        return Model.conn;
    }

    public static void shortestPath(int v, int e) {
        Model.Capacity = 0;
        if (e == v) {
            Model.Gui.printlnConsole(v + "-->" + v);
            return;
        }
        for (int i = e; i >= 0; i = Model.vertices.get(i).getParent().getId()) {
            if (i == v) {
                break;
            }
            if (Model.vertices.get(i).getParent().getId() != -1) {
                Model.setShortestPath.put(Model.vertices.get(i).getParent(), Model.vertices.get(i));
                Model.Capacity += getWeight(Model.vertices.get(i).parent, Model.vertices.get(i));
            }
        }

        Model.Gui.setlblCapTransferred(String.valueOf(Model.Capacity));
        if (Model.Capacity <= Model.vertices.get(e).getCapacity()) {
            Model.Gui.setlblCapTransferredColor(Color.red);
        } else {
            Model.Gui.setlblCapTransferredColor(Color.blue);
        }
        Model.glowMap.clear();
        Model.glowMap = (HashMap) Model.setShortestPath.clone();
        Model.Gui.graph();
    }

    public static void reset() {
        for (Vertex v : Model.vertices.values()) {
            v.setVisited(false);
        }
        for (Edge e : Model.edges) {
            e.setFailed(false);
        }
        Model.setShortestPath.clear();
        Model.glowMap.clear();
    }

    static boolean isConnected() {
        Vertex s = Model.vertices.get(Model._source);
        Bfs(s);
        Iterator<Vertex> vert = Model.vertices.values().iterator();
        while (vert.hasNext()) {
            Vertex key = vert.next();
            if (!key.visited()) {
                return false;
            }
        }
        return true;
    }

    public static int hasPath(int v) {
        return Model.visited.get(v);
    }

    public static int distTo(int v) {
        return Model.distTo.get(v);
    }
}
