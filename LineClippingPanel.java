import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LineClippingPanel extends JPanel {

	//Defining region codes
    public static final int INSIDE = 0;// 0000
    public static final int LEFT   = 1;// 0001
    public static final int RIGHT  = 2;// 0010
    public static final int BOTTOM = 4;// 0100
    public static final int TOP    = 8;// 1000

   
    
    //Defining x_max, y_max and x_min, y_min for clipping rectangle
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;
    private int[]x_coordinates;
    private int[]y_coordinates;

   private CohenSutherland cohenSutherland_clipper;
 
 
    	
    /*Constructor*/
    public LineClippingPanel(int xMin, int yMin, int xMax, int yMax, int[]x_coordinates, int[]y_coordinates) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
        this.x_coordinates = x_coordinates;
        this.y_coordinates = y_coordinates;
        
        cohenSutherland_clipper = new CohenSutherland();
   
    }


    
    /*class for line*/
    private class LineSegment {
        public int x0;
        public int y0;
        public int x1;
        public int y1;

        public LineSegment(int x0, int y0, int x1, int y1) {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
        }

        @Override
        public String toString() {
            return "LineSegment(x0: " + x0 + ", y0: " + y0 + "; x1: " + x1 + ", y1: " + y1 + ")";
        }
    }

    

    public class CohenSutherland {

    	//  Function to compute region code for a point(x, y)
    	//  x Horizontal coordinate
    	//	y Vertical coordinate
    	//	returns computed OutCode
        private int computeOutCode(double x, double y) {
            int code = INSIDE;
            //using "|" bitwise or operation
            if (x < xMin) {
                code |= LEFT;
            } else if (x > xMax) {
                code |= RIGHT;
            }
            if (y < yMin) {
                code |= BOTTOM;
            } else if (y > yMax) {
                code |= TOP;
            }
            

            return code;
        }
        
        /*function of type lineSegment which will check the region code for the end points of a line .. Function takes an original line as parameter*/
        public LineSegment clip(LineSegment line) {
            //System.out.println("\nExecuting Cohen-Sutherland...");
            int x0 = line.x0, x1 = line.x1, y0 = line.y0, y1 = line.y1; //coordinates of line are stored in variables from lineSegment line
            int outCode0 = computeOutCode(x0, y0);//outcode for first coordinates
            int outCode1 = computeOutCode(x1, y1);//outcode for second coordinates
            //System.out.println("OutCode for pt 1: " + outCode0 + ", OutCode for pt 2: " + outCode1);
            boolean accept = false;

            while (true) {
                if ((outCode0 | outCode1) == 0) { // Bitwise OR is 0. Trivially accept
                    accept = true;
                    break;
                } else if ((outCode0 & outCode1) != 0) { // Bitwise AND is not 0. Trivially reject
                    break;
                } else {
                    int x, y;

                    // Pick at least one point outside rectangle
                    int outCodeOut = (outCode0 != 0) ? outCode0 : outCode1;

                    // Now find the intersection point;
                    // use formulas y = y0 + slope * (x - x0), x = x0 + (1 / slope) * (y - y0)
                    if ((outCodeOut & TOP) != 0) {
                        x = x0 + (x1 - x0) * (yMax - y0) / (y1 - y0);
                        y = yMax;
                    } else if ((outCodeOut & BOTTOM) != 0) {
                        x = x0 + (x1 - x0) * (yMin - y0) / (y1 - y0);
                        y = yMin;
                    } else if ((outCodeOut & RIGHT) != 0) {
                        y = y0 + (y1 - y0) * (xMax - x0) / (x1 - x0);
                        x = xMax;
                    } else {
                        y = y0 + (y1 - y0) * (xMin - x0) / (x1 - x0);
                        x = xMin;
                    }

                    // Now we move outside point to intersection point to clip
                    if (outCodeOut == outCode0) {
                        x0 = x;
                        y0 = y;
                        outCode0 = computeOutCode(x0, y0);
                    } else {
                        x1 = x;
                        y1 = y;
                        outCode1 = computeOutCode(x1, y1);
                    }
                }
            }
            if (accept) {
                return new LineSegment(x0, y0, x1, y1);//returns the clipped part of the line
            }
            return null;//returns null..No need to clip line
        }
        
    }

    

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        drawShape.drawGrid(g2d, this.getWidth(), this.getHeight());

        g2d.setColor(Color.decode("#012A4A"));
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{10}, 0));
        
        g2d.drawRect(xMin, yMin, xMax-xMin, yMax-yMin);
        
        g2d.setStroke(new BasicStroke(3));
        
        /*
        drawLine(g2d, xMin, 0, xMin, getHeight());
        drawLine(g2d, xMax, 0, xMax, getHeight());
        drawLine(g2d, 0, yMin, getWidth(), yMin);
        drawLine(g2d, 0, yMax, getWidth(), yMax);
        */
        
       
        int x0, y0, x1, y1;
        LineSegment line, clippedLine;
        int pos_in_array=0;
    
        
        for (int i = 0; i < x_coordinates.length/2; i++) {
     	
        		
        	x0 = x_coordinates[pos_in_array];
        	x1 = x_coordinates[pos_in_array+1];
        	
        	y0 = y_coordinates[pos_in_array];
        	y1 = y_coordinates[pos_in_array +1];
        	
        	pos_in_array += 2;
        	
        	
            	
            line = new LineSegment(x0, y0, x1, y1);//Generatedline
             clippedLine = cohenSutherland_clipper.clip(line);//pass line in clipper

            //System.out.println("Original: " + line);
            //System.out.println("Clipped: " + clippedLine);

            if (clippedLine == null) {
                //g2d.setColor(Color.red);
            	//drawLine(g2d, line.x0, line.y0, line.x1, line.y1);
            	
            } else {
              //  g2d.setColor(Color.red);
               // drawLine(g2d, line.x0, line.y0, clippedLine.x0, clippedLine.y0);
               // drawLine(g2d, clippedLine.x1, clippedLine.y1, line.x1, line.y1);
                g2d.setColor(Color.black);
                drawLine(g2d, clippedLine.x0, clippedLine.y0, clippedLine.x1, clippedLine.y1);
            }
        }
    }

    
    private void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
       // g.drawLine(x1, getHeight() - y1, x2, getHeight() - y2);
    	g.drawLine(x1,y1,x2,y2);
    }

}