package graphify;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ayomitunde
 */
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.io.Serializable;

public class Vertex implements Serializable {

    public String label;
    public boolean wasVisited;
    public Vertex parent;
    private Point location;
    private HashSet<Edge> eList;
    private int id;
    private int cap;
    private String type;

    public Vertex(GraphifyGUI GG) {

    }

    public Vertex(int id, Point loc, String vertexLabel, String type, int capacity) {
        this.label = vertexLabel;
        this.wasVisited = false;
        this.type = type;
        this.parent = this; // change this to vertex
        this.id = id;
        this.cap = capacity;
        this.location = loc;
        this.eList = new HashSet<>();
    }

    public String getLabel() {
        return (this.label + " ");
    }

    /**
     *
     * @return 
     * returns name assigned to this vertex
     */
    public String getName() {
        return this.label;
    }

    /**
     *
     * @return 
     * returns type of this vertex
     */
    public String getType() {
        return this.type;
    }

    public int getId() {
        return this.id;
    }

    void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return 
     * returns the capacity of this vertex
     */
    public int getCapacity() {
        return this.cap;
    }
    
    /**
     *
     * @param x
     * sets capacity of vertex to x
     */
    public void setCapacity(int x){
        this.cap = x;
    }

    public Vertex getVertex(int id) {
        return this;
    }

    /**
     *
     * @return 
     * returns x,y coordinate of vertex
     */
    public Point getLocation() {
        return this.location;
    }

    /**
     *
     * @return 
     * returns edges connected to this vertex
     */
    public HashSet<Edge> eList() {
        return this.eList;
    }

    /**
     *
     * @return 
     * returns the parent of this vertex
     */
    public Vertex getParent() {
        return this.parent;
    }

//	public int hashCode(){
//		final int prime = 31;
//		int result = 1;
//                if(id < 0){
//                    result = 0;
//                }else{
//                    prime * result + String.valueOf(id).hashCode();
//                }
//		result = prime * result + ((id < 0) ? 0 :id.hashCode());
//		return result;
//	}
//	
//	public boolean equals(Object v){
//		if(this == v) return true;
//		if(v == null) return false;
//		if(getClass() != v.getClass()) return false;
//		Vertex x = (Vertex) v;
//		if(id == null){
//			if(x.id != null) return false;
//		}else if (!id.equals(x.id)){
//			return false;
//		}
//		return true;
//	}
}

class Edge implements Serializable {

    private final int id;
    private final Vertex source;
    private final Vertex dest;
    private final int weight;
    private int pheromoneAmount;
    private boolean fail;
    private double glowLevel = 0;

    public Edge(int id, Vertex source, Vertex dest, int pAmount, int weight, boolean fail) {
        this.id = id;
        this.source = source;
        this.dest = dest;
        this.pheromoneAmount = pAmount;
        this.weight = weight;
        this.fail = fail;
    }

    public int getId() {
        return id;
    }

    public Vertex getDest() {
        return dest;
    }

    public Vertex getSource() {
        return source;
    }

    public int getpheromoneAmount() {
        return pheromoneAmount;
    }

    public void setpAmount(int x) {
        this.pheromoneAmount = x;
    }

    public int getWeight() {
        return weight;
    }

    public String getConnections() {
        return source.getId() + "," + dest.getId();
    }

    public double getGlowLevel() {
        return glowLevel;
    }

    public void glowDie(double decrement) {
        glowLevel = Math.max(glowLevel - decrement, 0);
    }

    public void setGlowLevel(double newGlow) {
        glowLevel = newGlow;
    }

    public boolean isFailed() {
        return this.fail;
    }

    public void setFailed(boolean bool) {
        this.fail = bool;
    }

    @Override
    public String toString() {
        return this.getId() + " " + source.getName() + " " + dest.getName() + " " + this.getWeight() + " " + this.isFailed();
    }
}

class Graph {

    private final List<Vertex> vertices;
    private final HashSet<Edge> edges;
    HashMap<Integer, Vertex> nodes;
    private Queue<Vertex> queue;

    public Graph(List<Vertex> vertices, HashSet<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public HashSet<Edge> getEdges() {
        return edges;
    }

    // get all people with age between 18 to 20 and show their parents
}
