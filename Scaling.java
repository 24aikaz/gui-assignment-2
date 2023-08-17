import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Scaling extends JPanel implements ActionListener {
	
	int[] ptX;
	int[] ptY;
	int[] originalX;
    int[] originalY;
	int aboutptX, aboutptY;
	double sx, sy;
	double sxInc, syInc;
	double currentSx=1;
	double currentSy=1;
	String shape;
	
	Timer timer = new Timer(10,this);
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D ga = (Graphics2D) g;
		drawShape.drawGrid(ga, this.getWidth(), this.getHeight());
		ga.fillOval(aboutptX-8, aboutptY-8, 16, 16);
		ga.setStroke(new BasicStroke(3));
		
		int[] imgPoint;
		for (int i=0; i<this.ptX.length; i++) {
			imgPoint = scale(originalX[i], originalY[i], currentSx, currentSy, this.aboutptX, this.aboutptY);
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

	public static double[][] initTransfMatrix(double sx, double sy) {
		
		double[][] m_array = new double[3][3];
		
		m_array[0][0] = sx;    m_array[0][1] = 0;    m_array[0][2] = 0;
		m_array[1][0] = 0;    m_array[1][1] = sy;    m_array[1][2] = 0;
		m_array[2][0] = 0;    m_array[2][1] = 0;    m_array[2][2] = 1;
		return m_array;
	     
	 }
	
	public static int[] scale(int x, int y, double sx, double sy, int aboutPt_x, int aboutPt_y) {
		
		double[][] scalingMatrix = initTransfMatrix(sx, sy);
		double[][] inverseTranslationMatrix = Translation.initTransfMatrix((-1)*aboutPt_x, (-1)*aboutPt_y);
		double[][] translationMatrix = Translation.initTransfMatrix(aboutPt_x, aboutPt_y);
		
		double[][] transformationMatrix = Transformation.multiplyMatrix(translationMatrix, scalingMatrix);
		transformationMatrix = Transformation.multiplyMatrix(transformationMatrix, inverseTranslationMatrix);
		
		double[][] pointsMatrix = Transformation.initPointsMatrix(x,y);
		double[][] imageMatrix = Transformation.multiplyMatrix(transformationMatrix, pointsMatrix);
		int[] imagePoints = {(int) Math.round(imageMatrix[0][0]), (int) Math.round(imageMatrix[1][0])};
		
		return imagePoints;
		
	}
	
	public Scaling(int[] ptX, int[] ptY, int aboutptX, int aboutptY, double sx, double sy, String shape) {

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
		this.sx=sx;
		this.sy=sy;
		this.shape=shape;
		setBackground(Color.WHITE);
		
		double step;
		if (Math.abs(sx) > Math.abs(sy)) {
			step = Math.abs(sx*50);
		}
		else {
			step=Math.abs(sy*50);
		}
		
		sxInc = (float) (sx/step);
		syInc = (float) (sy/step);
		
		timer.start();

	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		
        if(Math.abs(currentSx) > Math.abs(sx) || Math.abs(currentSy) > Math.abs(sy)) {
             timer.stop();
        }
        else {
        	currentSx+=sxInc;
        	currentSy+=syInc;
            repaint();
        }
        
    }
	
}
