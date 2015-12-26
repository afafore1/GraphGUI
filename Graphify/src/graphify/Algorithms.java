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
                Model.graph.printlnConsole(key + " is a cut vertex");
                Model.cutV.add(key);
                cutExist = true;
            }
        }

        if (cutExist == false) {
            Model.graph.printlnConsole("No cut vertex in Graph");
        }
    }

    public static void Dfs(Vertex source) {
        reset();
        Model.stack = new Stack<>();
        Model.bconn = new HashSet<>();
        source.wasVisited = true;
        source.parent = source;
        Model.stack.push(source);
        while (!Model.stack.isEmpty()) {
            Vertex current = Model.stack.peek();
            Model.graph.printlnConsole("Considering element " + current.getName());
            Model.bconn.add(current.getName());
            if (current.eList().isEmpty()) {
                Model.graph.printlnConsole("Removing " + Model.stack.pop());
            } else {
                for (Iterator<Edge> currentList = current.eList().iterator(); currentList.hasNext();) {
                    Edge t = currentList.next();
                    if (!t.isFailed()) {
                        Vertex next = getConn(current, t);
                        if (!next.wasVisited) { // visited just one at a time
                            Model.graph.printlnConsole("Pushing " + next.getName());
                            Model.stack.push(next);
                            next.parent = current;
                            next.wasVisited = true;
                            break;
                        }
                        if (!currentList.hasNext()) {
                            Vertex backEdge = Model.stack.pop();
                            Model.graph.printlnConsole("Back edge " + backEdge.getName());
                        }
                    } else {
                        Model.stack.pop();
                    }
                }
            }
        }
        Model.graph.printlnConsole("order is " + Model.bconn);
    }

    public static void Bfs(Vertex source) {
        reset();
        Model.queue = new LinkedList<>(); // FIFO
        source.wasVisited = true; // marked as Model.visited
        Model.queue.add(source); // put into queue
        source.parent = source; // setShortestPath parent
        Model.conn = new ArrayList<>();
        System.err.println("new line");
        while (!Model.queue.isEmpty()) { // source
            Vertex current = Model.queue.poll(); // remove first 
            Model.conn.add(current.getId());
            for (Iterator<Edge> currentList = current.eList().iterator(); currentList.hasNext();) {
                Edge t = currentList.next();
                Vertex next = getConn(current, t);
                if (!t.isFailed()) {
                    if (next.wasVisited == false) {
                        next.wasVisited = true;
                        Model.queue.add(next);
                        next.parent = current;
                    }
                }
            }
        }
        Model.graph.printlnConsole("Order is " + Model.conn);
    }

    /**
     *
     * @param s passed in vertex
     * @param e edge associated with vertex s
     * @return get destination of vertex passed in from edge
     */
    public static Vertex getConn(Vertex s, Edge e) {
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

    // get weight... Weight is a combination of the actual weight + pAmount on that edge
    public static int getWeight(Vertex s, Vertex d) {
        for (Edge e : Model.edges) {
            if (e.getSource() == s && e.getDest() == d || e.getSource() == d && e.getDest() == s) {
                return e.getWeight() / e.getpheromoneAmount();
            }
        }
        return -1; // edge does not exist then
    }

    public static int getpAmount(Vertex s, Vertex d) {
        for (Edge e : Model.edges) {
            if (e.getSource() == s && e.getDest() == d || e.getSource() == d && e.getDest() == s) {
                return e.getpheromoneAmount();
            }
        }
        return -1;
    }

    //getNeighbors
    public static List<Vertex> getNeighbors(Vertex v) {
        List<Vertex> neighbors = new ArrayList<>();
        HashSet<Edge> n = Model.vertices.get(v.getId()).eList();
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
    public static void findMinDist(Vertex v) {
        List<Vertex> neighbors = getNeighbors(v);
        neighbors.stream().forEach((t) -> {
            int combWeight = GSD(v) + getWeight(v, t);
            if (GSD(t) > combWeight) {
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
        source.wasVisited = true; // marked as Model.visited
        Model.suggestQueue.add(source); // put into queue
        source.parent = source; // setShortestPath parent
        Model.conn = new ArrayList<>();
        while (!Model.suggestQueue.isEmpty()) { // source
            Vertex current = Model.suggestQueue.poll(); // remove first 
            Iterator<Edge> currentList = current.eList().iterator();
            while (currentList.hasNext()) {
                Edge t = currentList.next();
                Vertex next = getConn(current, t);
                if (next.wasVisited == false) {
                    next.wasVisited = true;
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
                                        Model.graph.printlnConsole("adding " + next.getId());
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
            Model.graph.printlnConsole(v + "-->" + v);
            return;
        }
        for (int i = e; i >= 0; i = Model.vertices.get(i).getParent().getId()) {
            if (i == v) {
                break;
            }
            if (Model.vertices.get(i).getParent().getId() != -1) {
                Model.setShortestPath.put(Model.vertices.get(i).getParent(), Model.vertices.get(i));
                Model.Capacity += getpAmount(Model.vertices.get(i).parent, Model.vertices.get(i));
            }
        }

        Model.graph.setlblCapTransferred(String.valueOf(Model.Capacity));
        if (Model.Capacity <= Model.vertices.get(e).getCapacity()) {
            Model.graph.setlblCapTransferredColor(Color.red);
        } else {
            Model.graph.setlblCapTransferredColor(Color.blue);
        }
        Model.glowMap.clear();
        Model.glowMap = (HashMap) Model.setShortestPath.clone();
        Model.graph.graph();
    }

    public static void reset() {
        Model.vertices.values().stream().forEach((v) -> {
            v.wasVisited = false;
        });
        Model.setShortestPath.clear();
        Model.glowMap.clear();
    }

    static boolean isConnected() {
        Vertex s = Model.vertices.get(Model._source);
        Bfs(s);
        Iterator<Vertex> vert = Model.vertices.values().iterator();
        while (vert.hasNext()) {
            Vertex key = vert.next();
            if (!key.wasVisited) {
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
