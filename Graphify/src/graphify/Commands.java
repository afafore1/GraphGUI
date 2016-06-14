/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * this class will be used for analyzing commands
 *
 * @author Ayomitunde
 */
enum Types {
    Person,
    Place,
    City
}

public class Commands {

    static ArrayList<Integer> cutV;

    static String action(String query, Vertex source, HashMap<Integer, Vertex> v) {
        String[] tokens = query.split(" ");
        String first = tokens[0];
        if (first.equals("find")) {
            return find(query, v, source);
        } else if (first.equals("get")) {
            return get(query, source);
        } else if (first.equals("places")) {
            return places(query, source);
        }
        return "";
    }

    static String find(String query, HashMap<Integer, Vertex> v, Vertex s) {
        String result = "";
        String[] tokens = query.split(" ");
        String sec = tokens[1];
        // not so wise implementation
        String chec = String.valueOf(sec.charAt(sec.length() - 1));
        String n = null;
        if (query.indexOf(chec) + 1 != query.length()) {
            n = tokens[2];
        }
        cutV = new ArrayList<>();
        //String n2 = tokens[3];
        if (sec.equals("all")) {
            if (n != null) {
                cutV.clear();
                if (n.equals("people") || n.equals("places") || n.equals("cities")) {
                    Iterator<Vertex> verts = v.values().iterator();
                    while (verts.hasNext()) {
                        Vertex next = verts.next();
                        if (next.getType().equals(Types.Person.toString()) && n.equals("people")) { // change to enumerable
                            cutV.add(next.getId());
                            result += next.getLabel();
                            //}else if(n2.equals("between")){

                            //}   
                        } else if (next.getType().equals(Types.City.toString()) && n.equals("cities")) {
                            cutV.add(next.getId());
                            result += next.getLabel();
                        } else if (next.getType().equals(Types.Place.toString()) && n.equals("places")) {
                            cutV.add(next.getId());
                            result += next.getLabel();
                        }
                    }
                }
            } else {
                // do nothing for now 
            }
        } else if (sec.equals("my")) {
            if (n != null) {
                cutV.clear();
                result = "";
                if (n.equals("friends") || n.equals("places") || n.equals("cities")) {
                    Iterator<Edge> mine = s.eList().iterator();
                    while (mine.hasNext()) {
                        Edge t = mine.next();
                        Vertex next = Algorithms.getConn(s, t);
                        if (next.getType().equals(Types.Person.toString()) && n.equals("friends")) {
                            cutV.add(next.getId());
                        } else if (next.getType().equals(Types.City.toString()) && n.equals("cities")) {
                            cutV.add(next.getId());

                        } else if (next.getType().equals(Types.Place.toString()) && n.equals("places")) {
                            cutV.add(next.getId());
                        }
                    }
                }
            }
        } else if (sec.equals("friends") || sec.equals("places") || sec.equals("cities")) {
            cutV.clear();
            result = "";
            switch (sec) {
                case "friends":
                    cutV = Algorithms.BfsSuggeest(s, 0);
                    break;
                case "places":
                    cutV = Algorithms.BfsSuggeest(s, 1);
                    break;
                case "cities":
                    cutV = Algorithms.BfsSuggeest(s, 2);
                    break;
                default:
                    break;
            }
        }else if(sec.equals("best")){
            if(n != null){
                cutV.clear();
                result = "";
                if(n.equals("places") || n.equals("cities")){
                    if(n.equals("places")){
                        cutV = Algorithms.BfsSuggeest(s, 3);
                    }
                }
            }
        }
        return result;
    }

    static void between(String query) {
        String[] tokens = query.split(" ");
    }

    static String places(String query, Vertex source) {
        String places = "";
        String[] tokens = query.split(" ");
        String sec = tokens[1];
        if (sec.equals("i")) {
            Iterator<Edge> p = source.eList().iterator();
            while (p.hasNext()) {
                Edge t = p.next();
                Vertex pla = Algorithms.getConn(source, t);
                if (pla.getType().equals("Place")) {
                    places += pla.getName() + " ";
                }
            }
        }
        return places;
    }

    static String get(String query, Vertex source) {
        String[] tokens = query.split(" ");
        String sec = tokens[1];
        String friendList = "";
        if (sec.equals("friends")) {
            Iterator<Edge> friends = source.eList().iterator();
            while (friends.hasNext()) {
                Edge t = friends.next();
                Vertex f = Algorithms.getConn(source, t);
                if (f.getType().equals("Person")) {
                    friendList += f.getName() + " ";
                }
            }
        }
        return friendList;
    }

}
