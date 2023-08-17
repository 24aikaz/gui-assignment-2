import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Rotation extends JPanel implements ActionListener {
    
    int[] ptX;
    int[] ptY;
    int[] originalX;
    int[] originalY;
    int aboutptX, aboutptY;
    double angle;
    String shape;
    double currentAngle=0;
    
    Timer timer = new Timer(10,this);
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D ga = (Graphics2D) g;
        drawShape.drawGrid(ga, this.getWidth(), this.getHeight());
        ga.fillOval(aboutptX-8, aboutptY-8, 16, 16);
        ga.setStroke(new BasicStroke(3));
        
        int[] imgPoint;
        for (int i=0; i<this.ptX.length; i++) {
            imgPoint = rotate(originalX[i], originalY[i], currentAngle, aboutptX, aboutptY);
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
    
    public static double[][] initTransfMatrix(int aboutPt_x,int aboutPt_y, double angle) {
        
        double[][] X = new double[3][3];
        double angleRad = Math.toRadians(angle);
        
        X[0][0] = cos (angleRad);    X[0][1] = -sin(angleRad);    X[0][2] = 0;
        X[1][0] = sin (angleRad);    X[1][1] = cos (angleRad);    X[1][2] = 0;
        X[2][0] = 0;                X[2][1] = 0;                X[2][2] = 1;
        return X;
         
     }
    
    public static int[] rotate(int x, int y, double angle, int aboutPt_x, int aboutPt_y) {
        
        double[][] rotationMatrix = initTransfMatrix(aboutPt_x, aboutPt_y, angle);
        double[][] inverseTranslationMatrix = Translation.initTransfMatrix((-1)*aboutPt_x, (-1)*aboutPt_y);
        double[][] translationMatrix = Translation.initTransfMatrix(aboutPt_x, aboutPt_y);
        
        double[][] transformationMatrix = Transformation.multiplyMatrix(translationMatrix, rotationMatrix);
        transformationMatrix = Transformation.multiplyMatrix(transformationMatrix, inverseTranslationMatrix);
        
        double[][] pointsMatrix = Transformation.initPointsMatrix(x,y);
        double[][] imageMatrix = Transformation.multiplyMatrix(transformationMatrix, pointsMatrix);
        int[] imagePoints = {(int) Math.round(imageMatrix[0][0]), (int) Math.round(imageMatrix[1][0])};
        
        return imagePoints;
        
    }
    
    public Rotation(int[] ptX, int[] ptY, int aboutptX, int aboutptY, double angle, String shape) {
        
        this.ptX=ptX;
        this.ptY=ptY;
        
        originalX = new int[ptX.length];
        originalY = new int[ptY.length];
        for (int i=0; i<ptX.length; i++) {
        	originalX[i]=ptX[i];
            originalY[i]=ptY[i];
        }
        
        this.aboutptX=aboutptX;
        this.aboutptY=aboutptY;
        this.angle=angle;
        this.shape=shape;
        setBackground(Color.WHITE);
        
        timer.start();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    	if (angle > 0) {
	        if(currentAngle > angle) {
	             timer.stop();
	        }
	        else {
	            currentAngle+=0.5;
	            repaint();
	        }
    	}
    	else {
    		if(currentAngle < angle) {
	             timer.stop();
	        }
	        else {
	            currentAngle-=0.5;
	            repaint();
	        }
    	}
        
    }

}
