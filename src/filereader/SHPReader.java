
package filereader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.nocrala.tools.gis.data.esri.shapefile.ShapeFileReader;
import org.nocrala.tools.gis.data.esri.shapefile.exception.InvalidShapeFileException;
import org.nocrala.tools.gis.data.esri.shapefile.header.ShapeFileHeader;
import org.nocrala.tools.gis.data.esri.shapefile.shape.AbstractShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.PointData;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.MultiPointZShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PointShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PolygonShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PolylineShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.MultiPointMShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.MultiPointPlainShape;
import java.util.ArrayList;

public class SHPReader {
    protected ArrayList<MyPoint2D> points = new ArrayList();
    protected ArrayList<MyLine2D> lines = new ArrayList();
    protected ArrayList<MyLine2D> polygones = new ArrayList();
    protected File f;
    //Bounds Variables
    protected String boxMinX;
    protected String boxMinY;
    protected String boxMaxX;
    protected String boxMaxY;
    //Info Variables
    protected String fileCode;
    protected String fileLength;
    protected String version;
    protected String shpType;
    //Math display variables
    protected float minX = 0.0f;
    protected float minY = 0.0f;
    protected float maxX = 0.0f;
    protected float maxY = 0.0f;
    
    
    public SHPReader(String path)
    {
        this.OpenSHP(path);
    }
    
     public void OpenSHP(String path)
    {
        try
        {
            File f = new File(path);
            FileInputStream is = new FileInputStream(f);
            ShapeFileReader r = new ShapeFileReader(is);
            ShapeFileHeader h = r.getHeader();
            
            minX = (float)h.getBoxMinX();
            minY = (float)h.getBoxMinY();
            maxX = (float)h.getBoxMaxX();
            maxY = (float)h.getBoxMaxY();
            
            boxMinX = ""+h.getBoxMinX();
            boxMinY = ""+h.getBoxMinY();
            boxMaxX = ""+h.getBoxMaxX();
            boxMaxY = ""+h.getBoxMaxY();
            
            fileCode = ""+h.getFileCode();
            fileLength = ""+h.getFileLength();
            version = ""+h.getVersion();
            shpType = ""+h.getShapeType();
            
            PointReader(r);
            is.close();
        }catch(Exception e)
        { e.printStackTrace(); }
    }
    public void getPointsXY(AbstractShape s)
    {
        PointShape aPoint = (PointShape) s;
        double x = aPoint.getX();
        double y = aPoint.getY();
        MyPoint2D point = new MyPoint2D();
        point.pointX = x;
        point.pointY = y;
        points.add(point);
    }
    public void getMultiPointZXY(AbstractShape s)
    {
        MultiPointZShape aPoints = (MultiPointZShape) s;
        for (int alfa = 0; alfa < aPoints.getPoints().length; alfa++)
        {
            MyPoint2D point = new MyPoint2D();
            double x = aPoints.getPoints()[alfa].getX();
            double y = aPoints.getPoints()[alfa].getY();
            point.pointX = x;
            point.pointY = y;
            points.add(point);
        }
    }
    public void getMultiPointMXY(AbstractShape s)
    {
        MultiPointMShape aPoints = (MultiPointMShape) s;
        for (int alfa = 0; alfa < aPoints.getPoints().length; alfa++)
        {
            MyPoint2D point = new MyPoint2D();
            double x = aPoints.getPoints()[alfa].getX();
            double y = aPoints.getPoints()[alfa].getY();
            point.pointX = x;
            point.pointY = y;
            points.add(point);
        }
    }
    public void getMultiPointXY(AbstractShape s)
    {
        MultiPointPlainShape aPoints = (MultiPointPlainShape) s;
        for (int alfa = 0; alfa < aPoints.getPoints().length; alfa++)
        {
            MyPoint2D point = new MyPoint2D();
            double x = aPoints.getPoints()[alfa].getX();
            double y = aPoints.getPoints()[alfa].getY();
            point.pointX = x;
            point.pointY = y;
            points.add(point);
        }
    }
    public void getPolyLineXY(AbstractShape s)
    {
        PolylineShape aPolyline = (PolylineShape) s;
        for(int alfa = 0; alfa < aPolyline.getNumberOfParts(); alfa ++)
        {
            ArrayList<MyPoint2D> temp = new ArrayList();
            for(int beta = 0; beta < aPolyline.getPointsOfPart(alfa).length; beta ++)
            {
                double x = aPolyline.getPointsOfPart(alfa)[beta].getX();
                double y = aPolyline.getPointsOfPart(alfa)[beta].getY();
                MyPoint2D point = new MyPoint2D();
                point.pointX = x;
                point.pointY = y;
                temp.add(point);
            }
            MyLine2D linea = new MyLine2D();
            linea.pointList = temp;
            lines.add(linea);
        }  
    }
    public void getPolygonXY(AbstractShape s)
    {
        PolygonShape aPolygon = (PolygonShape) s;
        for (int alfa = 0; alfa < aPolygon.getNumberOfParts(); alfa++)
        {
            ArrayList<MyPoint2D> temp = new ArrayList();
            for (int beta = 0; beta < aPolygon.getPointsOfPart(alfa).length; beta++)
            {
                double x = aPolygon.getPointsOfPart(alfa)[beta].getX();
                double y = aPolygon.getPointsOfPart(alfa)[beta].getY();
                MyPoint2D point = new MyPoint2D();
                point.pointX = x;
                point.pointY = y;
                temp.add(point);
            }
            MyLine2D polygone = new MyLine2D();
            polygone.pointList = temp;
            polygones.add(polygone);
        }
    }
    public void PointReader(ShapeFileReader r)
    {
        AbstractShape s;
        try
        {
            while((s = r.next()) != null)
            {
                switch(s.getShapeType())
                {
                    case POINT:
                        getPointsXY(s);
                        break;
                    case MULTIPOINT:
                        getMultiPointXY(s);
                        break;
                    case MULTIPOINT_Z:
                        getMultiPointZXY(s);
                        break;
                    case MULTIPOINT_M:
                        getMultiPointMXY(s);
                        break;
                    case POLYLINE:
                        getPolyLineXY(s);
                        break;
                    case POLYGON:
                        getPolygonXY(s);
                        break;
                }
            }
        }catch(Exception e)
        { e.printStackTrace(); }
    }
    public String getBoxMinX()
    { return boxMinX; }
    public String getBoxMinY()
    { return boxMinY; }
    public String getBoxMaxX()
    { return boxMaxX; }
    public String getBoxMaxY()
    { return boxMaxY; }
    public String getFileCode()
    { return fileCode; }
    public String getFileLength()
    { return fileLength; }
    public String getVersion()
    { return version; }
    public String getSahpeType()
    { return shpType; }
    public ArrayList getPoints()
    { return points; }
    public ArrayList getLines()
    { return lines; }
    public ArrayList getPolygons()
    { return polygones; }
    public float getMinX()
    { return minX; }
    public float getMinY()
    { return minY; }
    public float getMaxX()
    { return maxX; }
    public float getMaxY()
    { return maxY; }
}
