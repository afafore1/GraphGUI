/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphify;

import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Ayomitunde
 */
public class Population {
    Tour[] tours;
    
    public Population(int populationSize, boolean initialize){
        tours = new Tour[populationSize];
        if(initialize == true){
            for(int i = 0; i < populationSize; i++){
                Tour newTour = new Tour();
                newTour.generateIndividual();
                saveTour(i, newTour);
            }
        }
    }
    
    public final void saveTour(int index, Tour tour){
        tours[index] = tour;
    }
    
    public Tour getTour(int index){
        return tours[index];
    }
    
    public void getParents(){
        getFittest(0);
        getFittest(1);       
    }
    
    public Tour getFittest(int index){
        Tour fittest = tours[index];
        int fittestIndex = 0;
        for(int i = index > 0 ? 2 : 1; i < populationSize(); i++){
            Tour next = getTour(i);
            if(fittest.getFitness() <= next.getFitness()){
                fittest = next;
                fittestIndex = i;
            }
        }
        //swap fittest with location index specified
        tours[fittestIndex] = getTour(index);
        tours[index] = fittest;
        return fittest;
    }
    
    public int populationSize(){
        return tours.length;
    }
    
}
