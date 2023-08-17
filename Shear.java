import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Shear extends JPanel implements ActionListener{
    
    int[] ptX;
    int[] ptY;
    double shx, shy;
    int xRef, yRef;
    String shape;
    int[] originalX;
    int[] originalY;
    
    double shxInc, shyInc;
    
    double currentShx=0;
    double currentShy=0;
    
    
    Timer timer = new Timer(10,this);
   
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D ga = (Graphics2D) g;
        drawShape.drawGrid(ga, this.getWidth(), this.getHeight());
        ga.setStroke(new BasicStroke(3));
        
        int[] imgPoint;
        for (int i=0; i<this.ptX.length; i++) {
            imgPoint = shear(originalX[i], originalY[i], currentShx,currentShy, xRef, yRef);
            this.ptX[i]=imgPoint[0];
            this.ptY[i]=imgPoint[1];
        }
        switch (shape) {
            case "rectangle":
            {
                Polygon poly = new Polygon();
                for (int i=0; i<ptX.length; i++) {
                    poly.addPoint(ptX[i], ptY[i]);
                }
                ga.drawPolygon(poly);
                break;
            }
            case "circle":
            {
                int r = (int) Math.hypot((ptX[1]-ptX[0]), (ptY[1]-ptY[0]));
                ga.drawOval(ptX[0]-r, ptY[0]-r, r*2, r*2);
                break;
            }
            case "line":
            {
                ga.drawLine(ptX[0], ptY[0], ptX[1], ptY[1]);
                break;
            }
            case "polygon":
            {
                Polygon poly = new Polygon();
                for (int i=0; i<ptX.length; i++) {
                    poly.addPoint(ptX[i], ptY[i]);
                }
                ga.drawPolygon(poly);
                break;
            }
        }
    }
    
    public static double[][] initTransfMatrix(double shx,double shy, int xRef, int yRef) {
        
        double[][] m_array = new double[3][3];
        
        m_array[0][0] = 1;        m_array[0][1] = shx;  m_array[0][2] = ((-shx)*yRef);
        m_array[1][0] = shy;    m_array[1][1] = 1;    m_array[1][2] = ((-shy)*xRef);
        m_array[2][0] = 0;       m_array[2][1] = 0;    m_array[2][2] = 1;
        return m_array;
         
     }
    
    public static int[] shear(int x, int y, double shx, double shy, int xRef, int yRef) {
        
        double[][] transformationMatrix = initTransfMatrix(shx, shy, xRef, yRef);
        double[][] pointsMatrix = Transformation.initPointsMatrix(x,y);
        double[][] imageMatrix = Transformation.multiplyMatrix(transformationMatrix, pointsMatrix);
        int[] imagePoints = {(int) Math.round(imageMatrix[0][0]), (int) Math.round(imageMatrix[1][0])};
        
        return imagePoints;
        
    }
    
    public Shear(int[] ptX, int[] ptY, double shx, double shy, int xRef, int yRef, String shape) {

        this.ptX=ptX;
        this.ptY=ptY;
        
        originalX = new int[ptX.length];
        originalY = new int[ptY.length];
        for (int i=0; i<ptX.length; i++) {
            originalX[i]=ptX[i];
            originalY[i]=ptY[i];
        }
        
        this.shx=Math.abs(shx);
        this.shy=Math.abs(shy);
        this.xRef=xRef;
        this.yRef=yRef;
        this.shape=shape;
        setBackground(Color.WHITE);
        
            if (shx == 0 ) {
                shyInc = -0.05;
                shxInc = 0;
                
            }
           if(shy == 0) {
               shxInc = -0.05;
               shyInc =0;
           }
           
        timer.start();
        
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(shy==0) {
            if(currentShx < -shx) {
                timer.stop();
            }
            else {
                currentShx+=shxInc;
                currentShy+=shyInc;
                repaint();
            }        
        
        }
    
        if(shx==0) {
            if(currentShy < -shy) {
                timer.stop();
            }
            else {
                currentShx+=shxInc;
                currentShy+=shyInc;
                repaint();
            }        
        }
    }
}