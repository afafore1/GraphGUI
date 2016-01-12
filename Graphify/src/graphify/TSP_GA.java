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
public class TSP_GA {
    private static void reset(){
        Model.InitialDistanceValue = 0;
        Model.FinalDistanceValue = 0;
    }
    public static void start(){
        reset();
        Population pop = new Population(Model.vertices.size(), true);
        Model.graph.printlnConsole("Initial distance: "+pop.getFittest().getTourDistance());
        Model.InitialDistanceValue = pop.getFittest().getTourDistance();
        
        pop = GA.evolvePopulation(pop);
        for(int i = 0; i < 100; i++){
            pop = GA.evolvePopulation(pop);
        }
        
        Model.graph.printlnConsole("Finished");
        Model.graph.printlnConsole("Final distance: "+pop.getFittest().getTourDistance());
        Model.graph.printlnConsole("Solution: \n"+pop.getFittest());
        Model.FinalDistanceValue = pop.getFittest().getTourDistance();
    }
}
