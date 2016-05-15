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
    
    public Tour getFittest(){
        Tour fittest = tours[0];
        for(int i = 1; i < populationSize(); i++){
            Tour next = getTour(i);
            //System.out.println(next.getTour()+" "+next.getTourDistance());
            if(fittest.getFitness() <= next.getFitness()){
                fittest = next;
            }
        }
        return fittest;
    }
    
    public int populationSize(){
        return tours.length;
    }
    
}
