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
import java.util.ListIterator;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Ayomitunde
 */
public class Algorithms {

    private GraphifyGUI GG;
    private HashMap<Integer, Vertex> vertex;
    private Queue<Vertex> q;
    HashMap<Integer, Integer> connectionCache = new HashMap<>();
    private HashMap<Integer, Integer> glowMap = new HashMap<>();
    HashMap<Integer, HashSet<Integer>> nodes;
    private Queue<Integer> queue;
    
    private Stack<Vertex> stack;
    private HashMap<Integer, Integer> distTo;
    private HashMap<Integer, Integer> set = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> visited;
    private HashMap<Integer, Vertex> color;
    private HashMap<Integer, Integer> greedyresult;
    private HashSet<Integer> _colors2;
    private ArrayList<Integer> conn;
    private ArrayList<Vertex> bconn;
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
        this.vertex = new HashMap<>();
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

    void dfs(Vertex source) {
        vertex = GraphifyGUI.getNode();
        reset();
       // distTo = new HashMap<>();
        bconn = new ArrayList<>();
        source.wasVisited = true;
        source.parent = source;
        stack.push(source);
        while (!stack.isEmpty()) {
            Vertex current = stack.peek();
            GG.printlnConsole("Considering element " + current.getName());
            if (!bconn.contains(current)) {
                bconn.add(current);
            }
            Iterator<Vertex> currentList = current.vList().iterator();
            while (currentList.hasNext()) {
                Vertex next = currentList.next();
                if (!next.wasVisited) {
                    GG.printlnConsole("Pushing " + next.getName());
                    stack.push(next);
                    next.parent = current;
                    //distTo.put(n, distTo.get(element) + 1);
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

 public void Bfs(Vertex source){
                vertex = GraphifyGUI.getNode();
                reset();
		q = new LinkedList<>();
		source.wasVisited = true;
		q.add(source);
		source.parent = source;
		List<String> bAge = new LinkedList<>();
                conn = new ArrayList<>();
		while(!q.isEmpty()){
			Vertex current = q.poll();
                        conn.add(current.getId());
			//GG.printlnConsole("Current vertex is "+current.getName());
			Iterator<Vertex> currentList = current.vList().iterator();
			while(currentList.hasNext()){
				Vertex next = currentList.next();
				if(next.wasVisited == false){
					next.wasVisited = true;
					q.add(next);
					next.parent = current;
					//System.out.println("Adding "+next.getName()+" to the queue. Parent is "+next.getParent().getName());
					if(next.getAge() >= 18 && next.getAge() <= 20){
						bAge.add(next.getName());
						System.out.println("Parents of "+next.getName()+" is "+next.getParent());
						System.out.println(next.getName()+" has an age of "+next.getAge());
					}
				}
			}
		}
                GG.printlnConsole("Order is "+conn);
		
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

  void reset(){
      vertex = GraphifyGUI.getNode();
      Iterator<Vertex> vert = vertex.values().iterator();
      while(vert.hasNext()){
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
            if(!key.wasVisited)return false;
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
