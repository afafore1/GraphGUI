/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphify;

import java.io.IOException;
import java.util.ArrayList;
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
    private boolean found = false;
    static String nextSearchWord = "";
    
    public boolean strip(String url, String searchWord) throws IOException{
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
            Elements allLinks = hdoc.select("a[href*=/define.php?term]");
            Elements badLinks = hdoc.select("a[href*="+searchWord+"&]");
            Elements languages = hdoc.select("a[href*=urbandictionary.com/define.php?term="+searchWord+"]");
            allLinks.removeAll(badLinks);
            allLinks.removeAll(languages);
//            System.out.println("-------------------Languages------------------");
//            System.out.println(languages);
//            System.out.println("-------------------All Links------------------");
//            System.out.println(allLinks);
//            System.out.println("-------------------Bad Links------------------");
//            System.out.println(badLinks);
//            System.exit(0);
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
        found = body.toLowerCase().contains(searchWord.toLowerCase());
        if(found)
        {
            Elements word = doc.select("a.word");
            Elements meaning = doc.select("div.meaning");
            String text = "";
            for(int i = 0; i < word.size(); i++)
            {
                if(!Crawler.searchedText.contains(word.get(i).ownText().toLowerCase()) && text.isEmpty())
                {
                    text = word.get(i).ownText().toLowerCase();
                    Crawler.searchedText.add(text);
                }
                
                String textMean = meaning.get(i).ownText();
                Crawler._unvisited.add(word.get(i).attr("abs:href"));
                if(Crawler.wordMeaning.containsKey(text)){
                    ArrayList<String> textList = Crawler.wordMeaning.get(text);
                    textList.add(textMean);
                    Crawler.wordMeaning.put(text, textList);
                }else{
                    ArrayList<String> list = new ArrayList<>();
                    Crawler.wordMeaning.put(text, list);
                }
                System.out.println(text+": "+textMean);
            }
            if(!text.isEmpty()) {
            	System.err.println("I am text "+text);
            	nextSearchWord = text;
            }else{
            	
            }
        }        
        return found;
    }
    
    public List<String> getLinks()
    {
        return links;
    }
}
