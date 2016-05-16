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
        Model.counter = 1;
        String txtPopSize = GraphifyGUI.txtPopSize.getText();
        String txtIterNum = GraphifyGUI.txtIterNum.getText();
        int vertexSize = Model.vertices.size();
        int popSize = !String.valueOf(vertexSize).equals(txtPopSize) ? Integer.parseInt(txtPopSize) : vertexSize;
        int iterations = !txtIterNum.equals("100") ? Integer.parseInt(txtIterNum) : 100;
        Model.startTime = System.currentTimeMillis();
        Population pop = new Population(popSize, true);
        Model.InitialDistanceValue = pop.getFittest(0).getTourDistance();
        
        pop = GA.evolvePopulation(pop);
        for(int i = 0; i < iterations; i++){
            pop = GA.evolvePopulation(pop);
            //Model.Gui.setlblIterations(String.valueOf(Model.counter++));
        }
        Model.endTime = System.currentTimeMillis();
        GraphifyGUI.lblTimeTaken.setText(String.valueOf((Model.endTime - Model.startTime))+" ms");
        Model.Gui.printlnConsole("Solution: \n"+pop.getFittest(0));
        Model.FinalDistanceValue = pop.getFittest(0).getTourDistance();
        setPath(pop.getFittest(0));
    }
}
