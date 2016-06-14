/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//code modified from http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/
// purpose here is to enhance this soon
package graphify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;

/**
 *
 * @author Ayomitunde
 */
public class Crawler {
    static Document doc;
    static int _maxSearch = 28;
    static HashSet<String> _visited = new HashSet<>();
    static List<String> _unvisited = new LinkedList<>();
    static HashMap<String, ArrayList> wordMeaning = new HashMap<>();
    static String searchWord = "";

    public static void Crawl(String url, String searchWord) throws IOException {
          while(_visited.size() < _maxSearch){
              String currentUrl;
              JSearch js = new JSearch();
              if(_unvisited.isEmpty()){
                  currentUrl = url;
                  _visited.add(url);
              }else{
                  currentUrl = nextUrl();
              }
              js.strip(currentUrl, searchWord);
              if(js.searchWord(searchWord))
              {
                  System.out.println("found "+searchWord+" at "+currentUrl);
                 // break;
              }
              _unvisited.addAll(js.getLinks());
          }
          System.out.println("Visited all ");
    }
    
    private static String nextUrl()
    {
        String nextUrl;
        do
        {
            nextUrl = _unvisited.remove(0);
            //removeDups(subUrl);
        }while(_visited.contains(nextUrl) && _unvisited.size() > 0);
        _visited.add(nextUrl);
        return nextUrl;
    }
    
    public static void main(String [] args){
        searchWord = "hi";
        try {
            Crawl("http://www.urbandictionary.com/define.php?term=hi", searchWord);
        } catch (IOException ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
