
package filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TextReader {
    private ArrayList<String> _file;
    private File file;
    private FileReader fReader;
    private BufferedReader buffReader;
    private StringTokenizer strToken;
    private String readedLine;
    
    public TextReader()
    {
        
    }
    
    public TextReader(String strPath)
    {
        file = new File(strPath); 
        try {
            fReader = new FileReader(file);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        
        buffReader = new BufferedReader(fReader);
    }
    
    public String ReadLine(BufferedReader buff)
    {
        String line = "";
        try
        {
            line = buff.readLine();
        }
        catch(IOException ioe) { ioe.printStackTrace(); } 
        return line;
    }
    
    public ArrayList readMultiLine(BufferedReader buff)
    {
        String line = "";
        ArrayList<String> lines = new ArrayList<String>();
        try {
            while((line = buff.readLine()) != null)
            {
                lines.add(line);
            }
        } catch (Exception e) {
        }
        return lines;
    }
    
    public ArrayList startReading()
    {
        _file = this.readMultiLine(buffReader);
        return _file;
    }
    
}
