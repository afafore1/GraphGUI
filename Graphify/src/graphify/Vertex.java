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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Vertex {
	public String label;
	public boolean wasVisited;
	public Vertex parent;
	private HashSet <Vertex> vList;
	private int id;
	public int age;

        public Vertex(GraphifyGUI GG){
            
        }
	public Vertex(int id, String vertexLabel, int age) {
		this.label = vertexLabel;
		this.wasVisited = false;
		this.parent = this; // change this to vertex
		this.id = id;
		this.age = age;
		this.vList = new HashSet<>();
	}

	public String getLabel() {
		return (this.label+" ");
	}
	
	public String getName(){
		return this.label;
	}
	
	public int getId(){
		return this.id;
	}
        
        void setId(int id){
            this.id = id;
        }
	
	public int getAge(){
		return this.age;
	}
        
        public Vertex getVertex(int id){
            return this;
        }
        
        public HashSet<Vertex> vList(){
		return this.vList;
	}
	
	public Vertex getParent(){
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

class Edge{
	private final String id;
	private final Vertex source;
	private final Vertex dest;
	private final int weight;
	
	public Edge(String id, Vertex source, Vertex dest, int weight){
		this.id = id;
		this.source = source;
		this.dest = dest;
		this.weight = weight;
	}
	
	public String getId(){
		return id;
	}
	
	public Vertex getDest(){
		return dest;
	}
	
	public Vertex getSource(){
		return source;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public String getConnections(){
		return source.label+" <==> "+dest.label;
	}
	
	@Override
	public String toString(){
		return source +" "+dest;
	}
}

class Graph {
	private final List<Vertex> vertices;
	private final HashSet<Edge> edges;
	HashMap<Integer, Vertex> nodes;
	private Queue<Vertex> queue;
	
	public Graph(List<Vertex> vertices, HashSet<Edge> edges){
		this.vertices = vertices;
		this.edges = edges;
	}
	
	public List<Vertex> getVertices(){
		return vertices;
	}
	
	public HashSet<Edge> getEdges(){
		return edges;
	}

	public void printList(Vertex v){
		for(Vertex x : v.vList()){
			x.getLabel();
		}
	}
	
	// get all people with age between 18 to 20 and show their parents
	


}