/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphify;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Ayomitunde
 */
public class JSearch {
    private static final String _userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<>();
    private Document doc;
    
    public boolean strip(String url) throws IOException{
        try{
            Connection conn = Jsoup.connect(url).userAgent(_userAgent);
            Document hdoc = conn.get();
            doc = hdoc;
            if(conn.response().statusCode() == 200) // ok
            {
                System.out.println("Visiting "+url);
            }
            if(!conn.response().contentType().contains("text/html"))
            {
                System.out.println("No html here");
                return false;
            }
            Elements allLinks = hdoc.select("a[href]");
            for(Element l : allLinks)
            {
                links.add(l.absUrl("href"));
            }
            return true;
        }catch(IOException ioe)
        {
            return false;
        }
    }
    
    public boolean searchWord(String searchWord)
    {
        if(doc == null){
            return false;
        }
        String body = doc.body().text();
        return body.toLowerCase().contains(searchWord.toLowerCase());
    }
    
    public List<String> getLinks()
    {
        return links;
    }
}
