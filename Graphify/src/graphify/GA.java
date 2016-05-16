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
    public static Tour parent1;
    public static Tour parent2;

    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.populationSize(), false);
        int elitismOffset = 0;
        if (_elitism) {
            newPopulation.saveTour(0, pop.getFittest(0));
            elitismOffset = 1;
        }

        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            //parent1 = tourSelection(pop);
            tournamentSelection(pop);
            parent1 = pop.getTour(0);
            parent2 = pop.getTour(1);
            Tour child = crossover(parent1, parent2);
            Model.Gui.printlnConsole("Iteration " + Model.counter + "\nParent1 " + parent1.toString() + "\nParent2 " + parent2.toString() + "\nChild   " + child.toString());
            TSP_GA.setPath(child);
            child.getFitness();
            newPopulation.saveTour(i, child);
            Model.Gui.setlblIterations(String.valueOf(Model.counter++));
        }

        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            mutate(newPopulation.getTour(i));
        }
        return newPopulation;
    }

    public static Tour crossover(Tour parent1, Tour parent2) {
        Tour child = new Tour();

        int startPos = (int) (Math.random() * parent1.tourSize());
        int endPos = (int) (Math.random() * parent1.tourSize());

        for (int i = 0; i < child.tourSize(); i++) {
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setVertex(i, parent1.getVertex(i));
            } else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setVertex(i, parent1.getVertex(i));
                }
            }
        }


        for (int i = 0; i < parent2.tourSize(); i++) {
            if (!child.containsVertex(parent2.getVertex(i))) {
                for (int j = 0; j < child.tourSize(); j++) {
                    if (child.getVertex(j) == null) {
                        child.setVertex(j, parent2.getVertex(i));
                        break;
                    }
                }
            }
        }
        //authen(child);
        return child;
    }

    // if there is a null after crossover, pick a random node and add it
    static void authen(Tour tour) {
        for (int i = 0; i < tour.tourSize(); i++) {
            if (tour.getVertex(i) == null) {
                for (Integer v : Model.vertices.keySet()) {
                    Vertex vertex = Model.vertices.get(v);
                    if (tour.containsVertex(vertex) == false) {
                        tour.setVertex(i, vertex);
                    }
                }
            }
        }
    }

    private static void mutate(Tour tour) {
        for (int tourPos1 = 0; tourPos1 < tour.tourSize(); tourPos1++) {
            if (Math.random() < _mutationRate) {
                int tourPos2 = (int) (tour.tourSize() * Math.random());
                Vertex vertex1 = tour.getVertex(tourPos1);
                Vertex vertex2 = tour.getVertex(tourPos2);

                tour.setVertex(tourPos2, vertex1);
                tour.setVertex(tourPos1, vertex2);
            }
        }
    }

    private static Tour tourSelection(Population pop) {
        Population tournament = new Population(_tournamentSize, false);
        for (int i = 0; i < _tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.populationSize());
            tournament.saveTour(i, pop.getTour(randomId));
        }
        Tour fittest = tournament.getFittest(0);
        return fittest;
    }

    private static void tournamentSelection(Population pop) {
        Population tournament = new Population(_tournamentSize, false);
        
        for (int i = 0; i < _tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.populationSize());
            tournament.saveTour(i, pop.getTour(randomId));
        }
        tournament.getParents();
    }

}
