/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphify;

import java.util.Iterator;

/**
 *
 * @author Ayomitunde
 */
public class TSP_SA {

    static double temp = 10000;
    static double coolingRate = 0.003;

    public static double acceptanceProbablity(int energy, int newEnergy, double temperature) {
        if (newEnergy < energy) {
            return 1.0;
        }
        return Math.exp(energy - newEnergy) / temperature;
    }
    
    public static void setPath(Tour best){
        Model.glowMap.clear();
        for(Iterator<Vertex> vert = best.getTour().iterator(); vert.hasNext(); ){
            Vertex next = vert.next();
            if(!vert.hasNext()){
                next.parent = best.getTour().get(0);
            }
            next.parent = vert.next();
        }
        int startIndex = best.getTour().get(0).getId();
        int endIndex = best.getTour().get(best.getTour().size() -1).getId();
        for(int i = startIndex ; i == endIndex ; i = Model.vertices.get(i).parent.getId()){
            Model.glowMap.put(Model.vertices.get(i), Model.vertices.get(i).parent);
        }
        Model.graph.graph();
        //System.out.println(startIndex+" "+endIndex);
        //Algorithms.shortestPath(startIndex, endIndex);
    }

    public static void start() {
        Tour currentSolution = new Tour();
        currentSolution.generateIndividual();
        Model.graph.printlnConsole("Initial Solution distance: "+currentSolution.getTourDistance());
        
        Tour best = new Tour(currentSolution.getTour());
        setPath(best);
        while(temp > 1){
            Tour newSolution = new Tour(currentSolution.getTour());
            
            int tourPos1 = (int)(newSolution.tourSize() * Math.random());
            int tourPos2 = (int)(newSolution.tourSize() * Math.random());
            
            Vertex vertexSwap1 = newSolution.getVertex(tourPos1);
            Vertex vertexSwap2 = newSolution.getVertex(tourPos2);
            
            newSolution.setVertex(tourPos1, vertexSwap2);
            newSolution.setVertex(tourPos2, vertexSwap1);
            
            int currentEnergy = currentSolution.getTourDistance();
            int neighborEnergy = newSolution.getTourDistance();
            
            if(acceptanceProbablity(currentEnergy, neighborEnergy, temp) > Math.random()){
                currentSolution = new Tour(newSolution.getTour());
            }
            
            if(currentSolution.getTourDistance() < best.getTourDistance()){
                best = new Tour(currentSolution.getTour());
                //setPath(best);
            }
            
            temp *= 1 - coolingRate;
        }
        Model.graph.printlnConsole("Final Solution: "+best.getTourDistance());
        Model.graph.printlnConsole("Tour: "+best);
        setPath(best);
        for(Vertex v : best.getTour()){
            Model.graph.printlnConsole(v.getName());
        }
        
    }

}
