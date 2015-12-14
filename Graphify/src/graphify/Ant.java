/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphify;

import java.util.HashSet;

/**
 *
 * @author Ayomitunde
 */
public class Ant {
    int antID;
    String antName;
    int currNode;
    HashSet<Integer> path;
    public Ant(int id, String name, int currentNode){
        this.antID = id;
        this.antName = name;
        this.currNode = currentNode;
    }
    
}
