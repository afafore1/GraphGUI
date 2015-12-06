/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 *this class will be used for analyzing commands
 * @author Ayomitunde
 */

 enum Types{
    Person,
    Place,
    City
}
public class Commands {
    static HashSet<Vertex> _peppz = new HashSet<>();
    static HashSet<Vertex> _plaz = new HashSet<>();
    static HashSet<Vertex> _citz = new HashSet<>();
    static ArrayList<Integer> cutV;
    
    static String action(String query, Vertex source, HashMap<Integer, Vertex> v){
        String [] tokens = query.split(" ");
        String first = tokens[0];
        if(first.equals("find")){
            return find(query,v, source);
        }else if(first.equals("get")){
            return get(query, source);
        }else if(first.equals("places")){
            return places(query, source);
        }
        return "";
    }
    
    static String find(String query, HashMap<Integer, Vertex> v,Vertex s){
        String result = "";
        
        String [] tokens = query.split(" ");
        String sec = tokens[1];
        String n = tokens[2];
        //String n2 = tokens[3];
        if(sec.equals("all")){
            if(n != null){
                cutV = new ArrayList<>();
                if(n.equals("people") || n.equals("places") || n.equals("cities")){
                    Iterator<Vertex> verts = v.values().iterator();
                    while(verts.hasNext()){
                        int prev = s.getId();
                        Vertex next = verts.next();
                        if(next.getType().equals(Types.Person.toString()) && n.equals("people")){ // change to enumerable
                            _peppz.add(next);
                            //if(n2.isEmpty()){
                            cutV.add(prev); cutV.add(next.getId());
                             result += next.getLabel();   
                            //}else if(n2.equals("between")){
                                
                            //}
                            
                        }else if(next.getType().equals(Types.City.toString()) && n.equals("cities")){
                            _citz.add(next);
                            cutV.add(prev); cutV.add(next.getId());
                            result += next.getLabel();
                        }else if(next.getType().equals(Types.Place.toString()) && n.equals("places")){
                            _plaz.add(next);
                            cutV.add(prev); cutV.add(next.getId());
                            result += next.getLabel();
                        }
                        s = next;
                    }
                }
            }
        }else if(sec.equals("my")){
            if(n!= null){
                cutV = new ArrayList<>();
                if(n.equals("friends") || n.equals("places") || n.equals("cities")){
                    Iterator<Vertex> mine = s.vList().iterator();
                    while(mine.hasNext()){
                        int prev = s.getId();
                        Vertex next = mine.next();
                        if(next.getType().equals(Types.Person.toString()) && n.equals("friends")){
                            cutV.add(prev); cutV.add(next.getId());
                            result+= " "+prev+" "+next.getId();
                        }else if(next.getType().equals("City") && n.equals("cities")){
                            cutV.add(prev); cutV.add(next.getId());
                        }else if(next.getType().equals("Place") && n.equals("places")){
                            cutV.add(prev); cutV.add(next.getId());
                        }
                        s = next;
                    }
                }
            }
        }
        return result;
    }
    
    static void between(String query){
        String [] tokens = query.split(" ");
    }
    static String places(String query, Vertex source){
        String places = "";
        String [] tokens = query.split(" ");
        String sec = tokens[1];
        if(sec.equals("i")){
            Iterator<Vertex> p = source.vList().iterator();
            while(p.hasNext()){
                Vertex pla = p.next();
                if(pla.getType().equals("Place")){
                    places += pla.getName()+" ";
                }
            }
        }
        return places;
    }
    
    static String get(String query, Vertex source){
        String [] tokens = query.split(" ");
        String sec = tokens[1];
        String friendList = "";
        if(sec.equals("friends")){
            Iterator<Vertex> friends = source.vList().iterator();
            while(friends.hasNext()){
                Vertex f = friends.next();
                if(f.getType().equals("Person"))
                friendList += f.getName()+" ";
            }
        }
        return friendList;
    }
    
}
