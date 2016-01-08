/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphify;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Ayomitunde
 */
public class Tour {
    
    private ArrayList<Vertex> tour = new ArrayList<>();
    private double fitness = 0;
    private int distance = 0;
    
    public Tour(){
        for(int i = 0; i < Model.vertices.size(); i++){
            tour.add(null);
        }
    }
    
    public Tour(ArrayList<Vertex> tour){
        this.tour = tour;
    }
    
    public void generateIndividual(){
        for(int vertexIndex = 0; vertexIndex < Model.vertices.size(); vertexIndex++){
            setVertex(vertexIndex, Model.vertices.get(vertexIndex));
        }
        Collections.shuffle(tour);
    }
    
    public Vertex getVertex(int tourPosition){
        return (Vertex)tour.get(tourPosition);
    }
    
    public void setVertex(int tourPosition, Vertex vertex){
        tour.set(tourPosition, vertex);
        fitness = 0;
        distance = 0;
    }
    
    public int getDistance(Vertex source, Vertex destination){
        for(Edge e : source.eList()){
            if(e.getDest() == destination || e.getDest() == source){
                return e.getWeight();
            }
        }
        return -1;
    }
    
    public int getTourDistance(){
        if(distance == 0){
            int tourDistance = 0;
            
            for(int vertexIndex = 0; vertexIndex < tourSize(); vertexIndex++ ){
                Vertex fromVertex = getVertex(vertexIndex);
                Vertex destinationVertex;
                if(vertexIndex+1 < tourSize()){
                    destinationVertex = getVertex(vertexIndex+1);
                }else{
                    destinationVertex = getVertex(0);
                }
                int dist = getDistance(fromVertex, destinationVertex);
                if(dist != -1){ // means there is a connection
                    tourDistance += getDistance(fromVertex, destinationVertex);
                    //System.out.println("distance from "+fromVertex.getName()+" to "+destinationVertex.getName()+" is "+getDistance(fromVertex, destinationVertex));
                }
                
            }
            distance = tourDistance;
        }
        return distance;
    }
    
    public int tourSize(){
        return tour.size();
    }
    
    public boolean containsVertex(Vertex vertex){
        return tour.contains(vertex);
    }
    
    @Override
    public String toString(){
        String geneString = "| ";
        for(int i = 0; i < tourSize(); i++){
            geneString += getVertex(i)+" | ";
        }
        return geneString;
    }
    
    public ArrayList<Vertex> getTour(){
        return this.tour;
    }
}
