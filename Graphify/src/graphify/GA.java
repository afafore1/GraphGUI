/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphify;

/**
 *
 * @author Ayomitunde
 */
public class GA {
    
    private static final double _mutationRate = 0.015;
    private static final int _tournamentSize = 10;
    private static final boolean _elitism = true;
    
    public static Population evolvePopulation(Population pop){
        Population newPopulation = new Population(pop.populationSize(), false);
        
        int elitismOffset = 0;
        if(_elitism){
            newPopulation.saveTour(0, pop.getFittest());
            elitismOffset = 1;
        }
        
        for(int i = elitismOffset; i < newPopulation.populationSize(); i++){
            Tour parent1 = tournamentSelection(pop);
            Tour parent2 = tournamentSelection(pop);
            
            Tour child = crossover(parent1, parent2);
            newPopulation.saveTour(i, child);
        }
        
        for(int i = elitismOffset; i < newPopulation.populationSize(); i++){
            mutate(newPopulation.getTour(i));
        }
        return newPopulation;
    }
    
    public static Tour crossover(Tour parent1, Tour parent2){
        Tour child = new Tour();
        
        int startPos = (int)(Math.random() * parent1.tourSize());
        int endPos = (int)(Math.random() * parent1.tourSize());
        
        for(int i = 0; i < child.tourSize(); i++){
            if(startPos < endPos && i > startPos && i < endPos){
                child.setVertex(i, parent1.getVertex(i));
            }else if(startPos > endPos){
                if(!(i < startPos && i > endPos)){
                    child.setVertex(i, parent2.getVertex(i));
                }
            }
        }
        
        for(int i = 0; i < parent2.tourSize(); i++){
            if(!child.containsVertex(parent2.getVertex(i))){
                for(int j = 0; j < child.tourSize(); j++){
                    if(child.getVertex(j) == null){
                        child.setVertex(j, parent2.getVertex(j));
                        break;
                    }
                }
            }
        } 
        return child;
    }
    
    private static void mutate(Tour tour){
        for(int tourPos1 = 0; tourPos1 < tour.tourSize(); tourPos1++){
            if(Math.random() < _mutationRate){
                int tourPos2 = (int)(tour.tourSize() * Math.random());
                Vertex vertex1 = Model.vertices.get(tourPos1);
                Vertex vertex2 = Model.vertices.get(tourPos2);
                
                tour.setVertex(tourPos2, vertex1);
                tour.setVertex(tourPos1, vertex2);
            }
        }
    }
    
    private static Tour tournamentSelection(Population pop){
        Population tournament = new Population(_tournamentSize, false);
        for(int i = 0; i < _tournamentSize; i++){
            int randomId = (int)(Math.random() * pop.populationSize());
            tournament.saveTour(i, pop.getTour(randomId));
        }
        Tour fittest = tournament.getFittest();
        return fittest;
    }
    
}
