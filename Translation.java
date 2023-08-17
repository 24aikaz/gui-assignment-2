import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Translation extends JPanel implements ActionListener {
	
	int[] ptX;
	int[] ptY;
	
	int[] originalX;
    int[] originalY;
    
	int tx, ty;
	double currentTx=0;
	double currentTy=0;
	double txInc, tyInc;
	String shape;
	
	Timer timer = new Timer(5,this);
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D ga = (Graphics2D) g;
		drawShape.drawGrid(ga, this.getWidth(), this.getHeight());
		ga.setStroke(new BasicStroke(3));
		
		int[] imgPoint;
		for (int i=0; i<this.ptX.length; i++) {
			imgPoint = translate(originalX[i], originalY[i], (int)currentTx, (int)-currentTy);
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
	
	public static double[][] initTransfMatrix(int tx,int ty) {
		
		double[][] X = new double[3][3];
		
		X[0][0] = 1;    X[0][1] = 0;    X[0][2] = tx;
		X[1][0] = 0;    X[1][1] = 1;    X[1][2] = ty;
		X[2][0] = 0;    X[2][1] = 0;    X[2][2] = 1;
		return X;
	     
	 }
	
	public static int[] translate(int x, int y, int tx, int ty) {
		
		double[][] transformationMatrix = initTransfMatrix(tx, ty);
		double[][] pointsMatrix = Transformation.initPointsMatrix(x,y);
		double[][] imageMatrix = Transformation.multiplyMatrix(transformationMatrix, pointsMatrix);
		int[] imagePoints = {(int) Math.round(imageMatrix[0][0]), (int) Math.round(imageMatrix[1][0])};
		
		return imagePoints;
		
	}
	
	public Translation(int[] ptX, int[] ptY, int tx, int ty, String shape) {
		
		this.ptX=ptX;
		this.ptY=ptY;
		
		originalX = new int[ptX.length];
        originalY = new int[ptY.length];
        for (int i=0; i<ptX.length; i++) {
        	originalX[i]=ptX[i];
            originalY[i]=ptY[i];
        }
		
		this.tx = tx;
		this.ty = ty;
		this.shape=shape;
		setBackground(Color.WHITE);
		
		double step;
		if (Math.abs(tx) > Math.abs(ty)) {
			step = Math.abs(tx/2);
		}
		else {
			step=Math.abs(ty/2);
		}
		
		txInc = (float) (tx/step);
		tyInc = (float) (ty/step);
		System.out.println(tyInc);
		timer.start();

	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		
        if(Math.abs(currentTx) > Math.abs(tx) || Math.abs(currentTy) > Math.abs(ty)) {
             timer.stop();
             System.out.println("working");
        }
        else {
        	currentTx+=txInc;
        	currentTy+=tyInc;
            repaint();
        }
        
    }
	
}
