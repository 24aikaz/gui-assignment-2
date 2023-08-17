import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class ScanLine extends JPanel {
	
	private Vector<Point> polygon;      // hold polygon vertices
	private Color fillColor;
	
	public void paintComponent(Graphics g)
	{
		
        super.paintComponent(g);
        drawShape.drawGrid((Graphics2D)g, this.getWidth(), this.getHeight());
        g.setColor(fillColor);
        // draw the connecting lines
        Point p;
        Point p2;
        for (int i = 0; i < polygon.size() - 1; i++)
        {
            p = polygon.get(i);
            p2 = polygon.get(i+1);
            g.drawLine(p.x, p.y, p2.x, p2.y);
        }
        fillPoly(g);
    }
	
	
	
	public Edge[] createEdges()
    {
        Edge[] sortedEdges = new Edge[polygon.size()-1];
        for (int i = 0; i < polygon.size() - 1; i++)
        {
            //if (polygon.elementAt(i).y == polygon.elementAt(i+1).y) continue;
            if (polygon.elementAt(i).y < polygon.elementAt(i+1).y)
                sortedEdges[i] = new Edge(polygon.elementAt(i), polygon.elementAt(i+1));
            else
                sortedEdges[i] = new Edge(polygon.elementAt(i+1), polygon.elementAt(i));
        }
        return sortedEdges;
    }
	
	
	public void fillPoly(Graphics g) 
	{
		
		// create edges array from polygon vertice vector
        // make sure that first vertice of an edge is the smaller one
        Edge[] sortedEdges = this.createEdges();
         
        // sort all edges by y coordinate, smallest one first, lousy bubblesort
        Edge tmp;
        
        for (int i = 0; i < sortedEdges.length - 1; i++)
            for (int j = 0; j < sortedEdges.length - 1; j++)
            {
                if (sortedEdges[j].p1.y > sortedEdges[j+1].p1.y) 
                {
                    // swap both edges
                    tmp = sortedEdges[j];
                    sortedEdges[j] = sortedEdges[j+1];
                    sortedEdges[j+1] = tmp;
                }  
            }
        
        // find biggest y-coord of all vertices
        int scanlineEnd = 0;
        for (int i = 0; i < sortedEdges.length; i++)
        {
            if (scanlineEnd < sortedEdges[i].p2.y)
                scanlineEnd = sortedEdges[i].p2.y;
        }
        
     // scanline starts at smallest y coordinate
        int scanline = sortedEdges[0].p1.y;
        
        // this list holds all cutpoints from current scanline with the polygon
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        // move scanline step by step down to biggest one
        for (scanline = sortedEdges[0].p1.y; scanline <= scanlineEnd; scanline++)
        {
            //System.out.println("ScanLine: " + scanline); // DEBUG
            
            list.clear();
            
            // loop all edges to see which are cut by the scanline
            for (int i = 0; i < sortedEdges.length; i++)
            {   
                
                // here the scanline intersects the smaller vertice
                if (scanline == sortedEdges[i].p1.y) 
                {
                    if (scanline == sortedEdges[i].p2.y)
                    {
                        // the current edge is horizontal, so we add both vertices
                        sortedEdges[i].deactivate();
                       list.add((int)sortedEdges[i].curX);
                    }
                    else
                    {
                        sortedEdges[i].activate();
                        // we don't insert it in the list cause this vertice is also
                        // the (bigger) vertice of another edge and already handled
                    }
                }
                
                // here the scanline intersects the bigger vertice
                if (scanline == sortedEdges[i].p2.y)
                {
                	//remove unwanted edges
                    sortedEdges[i].deactivate();
                    list.add((int)sortedEdges[i].curX);
                }
                
                // here the scanline intersects the edge, so calc intersection point
                if (scanline > sortedEdges[i].p1.y && scanline < sortedEdges[i].p2.y)
                {
                    sortedEdges[i].update();
                   list.add((int)sortedEdges[i].curX);
                }
                
            }
            
            // now we have to sort our list with our x-coordinates, ascending
            int swaptmp;
            for (int i = 0; i < list.size(); i++)
                for (int j = 0; j < list.size() - 1; j++)
                {
                    if (list.get(j) > list.get(j+1))
                    {
                        swaptmp = list.get(j);
                       list.set(j, list.get(j+1));
                       list.set(j+1, swaptmp);
                    }
                
                }
         
            
           //draw lines on polygon created
            // so draw all line segments on current scanline
            for (int i = 0; i < list.size(); i+=2)
            {
               g.drawLine(list.get(i), scanline, 
                          list.get(i+1), scanline); 
            } 
            
            
            
        }
        
	}
	
	//pass arrayX, arrayY and color as parameters in constructor
	public ScanLine(int x[], int y[], Color fillColor)
	{
		setBackground(Color.WHITE);
		this.fillColor = fillColor;
		polygon = new Vector<Point>();
        // test
		/*
        polygon.add(new Point(40, 40));
        polygon.add(new Point(140, 40));
        polygon.add(new Point(140, 90));
        polygon.add(new Point(100, 90));
        polygon.add(new Point(100, 60));
        polygon.add(new Point(60, 60));
        polygon.add(new Point(60, 120));
        polygon.add(new Point(40, 120));
        polygon.add(polygon.firstElement());
        
        */
		
		for (int i=0; i<x.length; i++) 
		{
			polygon.add(new Point(x[i],y[i]));
			 
		}
		polygon.add(polygon.firstElement());
	}

}
