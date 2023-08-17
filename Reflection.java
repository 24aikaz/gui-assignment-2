import java.awt.*;
import javax.swing.*;

public class Reflection extends JPanel {
	
	int[] ptX;
	int[] ptY;
	int option;
	static int originX, originY;
	String shape;
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D ga = (Graphics2D) g;
		drawShape.drawGrid(ga, this.getWidth(), this.getHeight());
		ga.setColor(Color.decode("#012A4A"));
		ga.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
		switch (option){
			case 0://x-axis
			{
				ga.drawLine(0, originY, originX*2, originY);
				break;
			}
		
			case 1://y-axis
			{
				ga.drawLine(originX, 0, originX, originY*2);
				break;
			}
		
			case 2://about line y=x
			{
				ga.drawLine(originY+originX, 0, -(originY*2)+originY+originX, originY*2);
				break;
			}
		
			case 3://About line y=-x
			{
				ga.drawLine((originY*2)-originY+originX, originY*2, -originY+originX, 0);
				break;
			}
			
			case 4://about x and y-axis (in the origin)
			{
				ga.drawLine(0, originY, originX*2, originY);
				ga.drawLine(originX, 0, originX, originY*2);
				break;
			}
		}
		ga.setColor(Color.BLACK);
		ga.setStroke(new BasicStroke(3));
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
	
	public static double[][] initTransfMatrix(int option) {
		
		double[][] array = new double[3][3];
		
		for (int i=0; i<array.length; i++) {
			for (int j=0; j<array.length; j++) {
				array[i][j]=0;
			}
		}
		
		switch (option){
			case 0://x-axis
			{
				array[0][0]= 1;
				array[1][1]= -1;
				array[1][2]= 2*originY;
				array[2][2]= 1;
				break;
			}
		
			case 1://y-axis
			{
				array[0][0]= -1;
				array[0][2]= 2*originX;
				array[1][1]= 1;
				array[2][2]= 1;
				break;
			}
		
			case 2://about line y=x
			{
				array[0][1]= -1;
				array[0][2]= originY+originX;
				array[1][0]= -1;
				array[1][2]= originX+originY;
				array[2][2]= 1;
				break;
			}
		
			case 3://About line y=-x
			{
				array[0][1]= 1;
				array[0][2]= -originY+originX;
				array[1][0]= 1;
				array[1][2]= -originX+originY;
				array[2][2]= 1;
				break;
			}
			
			case 4://about x and y-axis (in the origin)
			{
				array[0][0]= -1;
				array[0][2]= 2*originX;
				array[1][1]= -1;
				array[1][2]= 2*originY;
				array[2][2]= 1;	
				break;
			}
		}
		
		return array;
	     
	 }
	
	public static int[] reflect(int x, int y, int option) {
		
		double[][] transformationMatrix = initTransfMatrix(option);
		double[][] pointsMatrix = Transformation.initPointsMatrix(x,y);
		double[][] imageMatrix = Transformation.multiplyMatrix(transformationMatrix, pointsMatrix);
		int[] imagePoints = {(int) Math.round(imageMatrix[0][0]), (int) Math.round(imageMatrix[1][0])};
		
		return imagePoints;
		
	}
	
	public Reflection(int[] ptX, int[] ptY, int option, int originX, int originY, String shape) {

		this.ptX=ptX;
		this.ptY=ptY;
		this.option=option;
		Reflection.originX=originX;
		Reflection.originY=originY;
		this.shape=shape;
		setBackground(Color.WHITE);
		int[] imgPoint;
		for (int i=0; i<this.ptX.length; i++) {
			imgPoint = reflect(this.ptX[i], this.ptY[i], this.option);
			this.ptX[i]=imgPoint[0];
			this.ptY[i]=imgPoint[1];
		}
		repaint();
	}

}
