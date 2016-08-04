
package filereader;

import java.util.ArrayList;


public class FileReader {


    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<String> points = new TextReader("mde.txt").startReading();
        String shpType = new SHPReader("e14b49_acueducto50_l_utm.shp").getSahpeType();
        
        System.out.println(shpType);
        
        for(String line:points)
        {
            System.out.println(line);
        }
    }
    
}
