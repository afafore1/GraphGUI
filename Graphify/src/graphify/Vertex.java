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
    private boolean wasVisited;
    public Vertex parent;
    private Point location;
    private HashSet<Edge> eList;
    private HashMap<Integer, Edge> path;
    private int id;
    private int cap;
    private String type;
    private boolean selected = false;

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

    
    public int getX(){
        return this.location.x;
    }
    
    public int getY(){
        return this.location.y;
    }

    public String getLabel() {
        return (this.label + " ");
    }
    
    public HashMap getPath(){
        return this.path;
    }

    /**
     *
     * @return 
     * returns name assigned to this vertex
     */
    public String getName() {
        return this.label;
    }
    
    public boolean visited(){
        return this.wasVisited;
    }
    
    public void setVisited(boolean bool){
        this.wasVisited = bool;
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

    public boolean getSelected() {
        return selected;
    }
    
    public void setSelected(boolean sel) {
        selected = sel;
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
    private int weight;
    private boolean fail;
    private double glowLevel = 0;
    private boolean bidirectional;

    public Edge(int id, Vertex source, Vertex dest, int weight, boolean fail) {
        this.id = id;
        this.source = source;
        this.dest = dest;
        this.weight = weight;
        this.fail = fail;
        bidirectional = false;
    }

    public boolean getBidirectional() {
        return bidirectional;
    }

    public void setBidirectional(boolean bi) {
        bidirectional = bi;
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

    public int getWeight() {
        return weight;
    }
    
    public void setWeight(int w){
        this.weight = w;
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
