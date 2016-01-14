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
public class TSP_SA {

    static double temp = 10000;
    static double coolingRate = 0.003;

    private static void reset() {
        temp = 10000;
        coolingRate = 0.003;
        Model.InitialDistanceValue = 0;
        Model.FinalDistanceValue = 0;
    }

    public static double acceptanceProbablity(int energy, int newEnergy, double temperature) {
        if (newEnergy < energy) {
            return 1.0;
        }
        return Math.exp(energy - newEnergy) / temperature;
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
    
    public static void printTour(Tour best){
        for(int i = 0; i < best.getTour().size(); i++){
            Vertex next = best.getTour().get(i);
            Vertex parent;
            if (i + 1 != best.getTour().size()) {
                parent = best.getTour().get(i + 1);
            } else {
                parent = best.getTour().get(0);
            }
            Model.Gui.printConsole(next.getName()+"->"+parent.getName()+" |");
        }
    }

    public static void start() {
        reset();
        Tour currentSolution = new Tour();
        currentSolution.generateIndividual();
        Model.Gui.printlnConsole("Initial Solution distance: " + currentSolution.getTourDistance());
        Model.InitialDistanceValue = currentSolution.getTourDistance();
        Tour best = new Tour(currentSolution.getTour());
        setPath(best);
        while (temp > 1) {
            Tour newSolution = new Tour(currentSolution.getTour());

            int tourPos1 = (int) (newSolution.tourSize() * Math.random());
            int tourPos2 = (int) (newSolution.tourSize() * Math.random());

            Vertex vertexSwap1 = newSolution.getVertex(tourPos1);
            Vertex vertexSwap2 = newSolution.getVertex(tourPos2);

            newSolution.setVertex(tourPos1, vertexSwap2);
            newSolution.setVertex(tourPos2, vertexSwap1);

            int currentEnergy = currentSolution.getTourDistance();
            int neighborEnergy = newSolution.getTourDistance();

            if (acceptanceProbablity(currentEnergy, neighborEnergy, temp) > Math.random()) {
                currentSolution = new Tour(newSolution.getTour());
            }

            if (currentSolution.getTourDistance() < best.getTourDistance()) {
                best = new Tour(currentSolution.getTour());
                setPath(best);
            }

            temp *= 1 - coolingRate;
        }
        Model.Gui.printlnConsole("Final Solution: " + best.getTourDistance());
        Model.FinalDistanceValue = best.getTourDistance();
        Model.Gui.printlnConsole("Tour: " + best);
        setPath(best);

    }

}
