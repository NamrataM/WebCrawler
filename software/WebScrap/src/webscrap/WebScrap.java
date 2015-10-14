/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webscrap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Namrata
 */
public class WebScrap {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Document doc;
        try{
            doc = Jsoup.connect("http://www.metmuseum.org/collection/the-collection-online/search/15538?pos=1&rpp=30&pg=1&rndkey=20150122&ft=*&deptids=2").get();
            
            File jsonFile = new File("Records.json");	
            FileWriter output = new FileWriter(jsonFile);
            JSONArray store = new JSONArray();
            //Declarations for JSON output
            String nameTag = "Name";
            String name;
            String artistTag = "Artist";
            String artistName;
            String imgURLTag = "imgURL";
            String imgsrc;
            String dateTag = "Date";
            String date;
            String geoTag = "Geography";
            String geoVal;
            String cultureTag = "Culture";
            String culture;
            String mediumTag = "Medium";
            String medium;
            String dimTag = "Dimension";
            String dim;
            String classTag = "Classification";
            String classification;
            String credit_line_tag = "Credit_Line";
            String credit_line;
            String accessNumTag = "Accession_Number";
            String accessNum;
            String RnRTag = "Rights_and_Reproduction";
            String RnR;
            
            
            //trying to load the next urls
            String next ="http://www.metmuseum.org/collection/the-collection-online/search/11432?pos=1&rpp=30&pg=1&rndkey=20150123&ft=*&deptids=2";
            int i=500;
            while (i!=0) 
            {
             
            name = "";
            artistName = "";
            imgsrc = "";
            date = "";
            //geoVal = "not available";
            //culture = "not available";
            medium = "";
            dim = "";
            classification= "";
            credit_line = "";
            accessNum = "";
            //RnR = "not available";
            
            doc = Jsoup.connect(next).get();
            String o_title= doc.getElementsByTag("h2").text();
            String[] part_o = o_title.split("Email");
                    String part_o1 = part_o[0];
                    String part_o2 = part_o[1];
            //System.out.println(o_title);
            name = part_o1;
            //String artist = doc.getElementsByTag("h3").text();
            //System.out.println(artist);
            //artistName = artist;
            Elements imgdiv = doc.select("div#inner-image-container img");
            for (Element e:imgdiv)
            {imgsrc = e.absUrl("src");
            }
            
            Elements divs;
            divs = doc.select("div.tombstone");
            Elements divchild;
            divchild = divs.select("div");
            int count = 0;
            for (Element div : divchild) {
                String info = div.text();
                if(count!=0)
                {
                 String[] parts = info.split(":");
                    String part1 = parts[0];
                    String part2 = parts[1];
                    
                    switch(part1)
                    {case "Artist": artistName = part2;
                                    break;
                     case "Date": date = part2;
                                  break;
                    case "Geography": geoVal = part2;
                                    break;
                    case "Culture": culture = part2;
                                    break;
                    case "Medium": medium = part2;
                                    break;
                    case "Dimensions": dim = part2;
                                    break;
                    case "Classification": classification = part2;
                                    break;
                    case "Credit Line": credit_line = part2;
                                    break;
                    case "Accession Number": accessNum = part2;
                                    break;
                    case "Rights and Reproduction": RnR = part2;
                                    break;
                    }
                }
                count++;
               }
            if( classification.equals(" Paintings"))
            {
                //System.out.println(nameTag+name);
                //System.out.println(artistTag+artistName);
                //System.out.println(imgURLTag+imgsrc);
                //System.out.println(dateTag+date);
                //System.out.println(mediumTag+medium);
                //System.out.println(dimTag+dim);
                //System.out.println(classTag+classification);
                //System.out.println(credit_line_tag+credit_line);
                //System.out.println(accessNumTag+accessNum);
                //System.out.println(i);
                //json writing
                JSONObject jsonObj = new JSONObject();
                jsonObj.put(nameTag,name);
                jsonObj.put(artistTag,artistName);
                jsonObj.put(imgURLTag,imgsrc);
                jsonObj.put(dateTag,date);
                jsonObj.put(mediumTag,medium);
                jsonObj.put(dimTag,dim);
                jsonObj.put(classTag,classification);
                jsonObj.put(credit_line_tag,credit_line);
                jsonObj.put(accessNumTag,accessNum);
                
                store.add(jsonObj);
                i--;}		
		//going to next page		
            Element link = doc.select("a.next").first();
            next = link.attr("abs:href");
            
            }
            output.write(store.toJSONString());
		output.write("\n");
                output.flush();
		output.close();
            
    }catch(IOException e){
        }
    
    
}
}