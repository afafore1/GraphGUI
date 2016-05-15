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
    
    public static void setPath(Tour best) {
        Model.glowMap.clear();
        for (int i = 0; i < best.getTour().size(); i++) {
            Vertex next = best.getTour().get(i);
            Vertex parent;
            if (i + 1 != best.getTour().size()) {
                parent = best.getTour().get(i + 1);
            } else {
                parent = best.getTour().get(0);
            }
            Model.glowMap.put(next, parent);
        }
        Model.Gui.graph();
    }
    
    
    public static void start(){
        reset();
        Population pop = new Population(Model.vertices.size(), true);
        Model.Gui.printlnConsole("Initial distance: "+pop.getFittest().getTourDistance());
        Model.InitialDistanceValue = pop.getFittest().getTourDistance();
        
        pop = GA.evolvePopulation(pop);
        for(int i = 0; i < 100; i++){
            pop = GA.evolvePopulation(pop);
        }
        
        Model.Gui.printlnConsole("Finished");
        Model.Gui.printlnConsole("Final distance: "+pop.getFittest().getTourDistance());
        Model.Gui.printlnConsole("Solution: \n"+pop.getFittest());
        Model.FinalDistanceValue = pop.getFittest().getTourDistance();
        setPath(pop.getFittest());
    }
}
