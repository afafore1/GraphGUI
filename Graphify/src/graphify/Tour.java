/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author Ayomitunde
 */
public class Tour {

    private ArrayList<Vertex> tour = new ArrayList<>();
    private double fitness = 0;
    private int distance = 0;

    public Tour() {
        for (int i = 0; i < Model.vertices.size(); i++) {
            tour.add(null);
        }
    }

    public Tour(ArrayList<Vertex> tour) {
        this.tour = tour;
    }

    public void generateIndividual() {
        Model.vertices.keySet().stream().forEach((vertexIndex) -> {
            setVertex(vertexIndex, Model.vertices.get(vertexIndex));
        });
        Collections.shuffle(tour);
    }

    public boolean isSafe(Vertex s, Vertex d) {
        for (Edge e : s.eList()) {
            if (!tour.contains(d) && e.getDest() == d) {
                return true;
            }
        }
        return false;
    }

    public Vertex getVertex(int vertexIndex) {
        return (Vertex) (tour.get(vertexIndex));
    }

    public void setVertex(int tourPosition, Vertex vertex) {
        tour.set(tourPosition, vertex);
        fitness = 0;
        distance = 0;
    }

    public int getDistance(Vertex source, Vertex destination) {
        for (Edge e : Model.edges) {
            if ((e.getSource().equals(source) && e.getDest().equals(destination))
                    || (e.getDest().equals(source) && e.getSource().equals(destination))) {
                return e.getWeight();
            }
        }
        return -1;
    }

    public double getFitness() {
        if (fitness == 0) {
            fitness = 1 / (double) getTourDistance();
        }
        return fitness;
    }

    public int getTourDistance() {
        if (distance == 0) {
            for (int vertexIndex = 0; vertexIndex < tourSize(); vertexIndex++) {
                Vertex fromVertex = getVertex(vertexIndex);
                Vertex destinationVertex;
                if (vertexIndex + 1 < tourSize()) {
                    destinationVertex = getVertex(vertexIndex + 1);
                } else {
                    destinationVertex = getVertex(0);
                }
                int dist = getDistance(fromVertex, destinationVertex);
                if (dist != -1) { // means there is a connection
                    distance += dist;
                }
            }
        }
        return distance;
    }

    public int tourSize() {
        return tour.size();
    }

    public boolean containsVertex(Vertex vertex) {
        return tour.contains(vertex);
    }

    @Override
    public String toString() {
        String geneString = "| ";
        for (int i = 0; i < tourSize(); i++) {
            geneString += getVertex(i).label + " | ";
        }
        return geneString;
    }

    public ArrayList<Vertex> getTour() {
        return this.tour;
    }
}
