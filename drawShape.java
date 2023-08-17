import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class drawShape extends JPanel {
	
	String shape;
	int[] x, y;
	int count;
	boolean stop;
	Polygon poly;
	
	public int[] getXPoints() {
		if (shape=="polygon") {
			int[] returnX = new int[count];
			for (int i=0; i<count; i++) {
				returnX[i]=x[i+1];
			}
			return returnX;
		}
		else if (shape=="rectangle"){
			int[] returnX = new int[4];
			returnX[0]=x[0];
			returnX[1]=x[1];
			returnX[2]=x[1];
			returnX[3]=x[0];
			return returnX;
		}
		else {
			int[] returnX = new int[2];
			returnX[0]=x[0];
			returnX[1]=x[1];
			return returnX;
		}
	}
	
	public int[] getYPoints() {
		if (shape=="polygon") {
			int[] returnY = new int[count];
			for (int i=0; i<count; i++) {
				returnY[i]=y[i+1];
			}
			return returnY;
		}
		else if (shape=="rectangle"){
			int[] returnY = new int[4];
			returnY[0]=y[0];
			returnY[1]=y[0];
			returnY[2]=y[1];
			returnY[3]=y[1];
			return returnY;
		}
		else {
			int[] returnY = new int[2];
			returnY[0]=y[0];
			returnY[1]=y[1];
			return returnY;
		}
	}
	
	public int[] getXLines() {
		if (shape=="polygon") {
			int[] returnX = new int[count*2];
			for (int i=0; i<returnX.length-1; i++) {
				returnX[i]=x[(i+3)/2];
			}
			returnX[returnX.length-1]=x[1];
			return returnX;
		}
		else if (shape=="rectangle"){
			int[] returnX = new int[8];
			returnX[0]=x[0];
			returnX[1]=x[1];
			returnX[2]=x[1];
			returnX[3]=x[1];
			returnX[4]=x[1];
			returnX[5]=x[0];
			returnX[6]=x[0];
			returnX[7]=x[0];
			return returnX;
		}
		else {
			int[] returnX = new int[2];
			returnX[0]=x[0];
			returnX[1]=x[1];
			return returnX;
		}
	}
	
	public int[] getYLines() {
		if (shape=="polygon") {
			int[] returnY = new int[count*2];
			for (int i=0; i<returnY.length-1; i++) {
				returnY[i]=y[(i+3)/2];
			}
			returnY[returnY.length-1]=y[1];
			return returnY;
		}
		else if (shape=="rectangle"){
			int[] returnY = new int[8];
			returnY[0]=y[0];
			returnY[1]=y[0];
			returnY[2]=y[0];
			returnY[3]=y[1];
			returnY[4]=y[1];
			returnY[5]=y[1];
			returnY[6]=y[1];
			returnY[7]=y[0];
			return returnY;
		}
		else {
			int[] returnY = new int[2];
			returnY[0]=y[0];
			returnY[1]=y[1];
			return returnY;
		}
	}
	
	public String getShape() {
		return shape;
	}
	
	public static void drawGrid(Graphics2D ga, int width, int height) {
		ga.setColor(Color.decode("#4b6275"));
		ga.setStroke(new BasicStroke(3));
		ga.drawLine(width/2, 0, width/2, height);
		ga.drawLine(0, height/2, width, height/2);
		ga.drawString("0", (width/2)-10, (height/2)+20);
		ga.setStroke(new BasicStroke(1));
		
		for (int i=75; i<(width/2); i+=75) {
			ga.drawLine((width/2)+i, 0, (width/2)+i, height);
			ga.drawLine((width/2)-i, 0, (width/2)-i, height);
			ga.setStroke(new BasicStroke(4));
			ga.drawLine((width/2)+i, (height/2)+5, (width/2)+i, (height/2)+1);
			ga.drawString(i+"", (width/2)+i-10, (height/2)+20);
			ga.drawLine((width/2)-i, (height/2)+5, (width/2)-i, (height/2)+1);
			ga.drawString("-"+i, (width/2)-i-10, (height/2)+20);
			ga.setStroke(new BasicStroke(1));
			
		}
		
		for (int j=75; j<(height/2); j+=75) {
			
			ga.drawLine(0, (height/2)+j, width, (height/2)+j);
			ga.drawLine(0, (height/2)-j, width, (height/2)-j);
			ga.setStroke(new BasicStroke(4));
			ga.drawLine((width/2)-5, (height/2)+j, (width/2)-1, (height/2)+j);
			ga.drawString(j+"", (width/2)-30, (height/2)-j);
			ga.drawLine((width/2)-5, (height/2)-j, (width/2)-1, (height/2)-j);
			ga.drawString("-"+j, (width/2)-30, (height/2)+j);
			ga.setStroke(new BasicStroke(1));
			
		}
		ga.setColor(Color.BLACK);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D ga = (Graphics2D) g;
		drawGrid(ga, this.getWidth(), this.getHeight());
		ga.setStroke(new BasicStroke(3));
		switch (shape) {
			case "rectangle":
			{
				ga.drawRect(Math.min(x[1], x[0]), Math.min(y[1], y[0]), Math.max(x[1], x[0])-Math.min(x[1], x[0]), Math.max(y[1], y[0])-Math.min(y[1], y[0]));
				break;
			}
			case "circle":
			{
				int r = (int) Math.hypot((x[1]-x[0]), (y[1]-y[0]));
				ga.drawOval(x[0]-r, y[0]-r, r*2, r*2);
				break;
			}
			case "line":
			{
				ga.drawLine(x[0], y[0], x[1], y[1]);
				break;
			}
			case "polygon":
			{
				if (stop) {
					for (int i=1; i<=count; i++) {
						poly.addPoint(x[i], y[i]);
					}
					ga.drawPolygon(poly);
				}
				else {
					for (int i=1; i<count; i++) {
						ga.drawLine(x[i], y[i], x[i+1], y[i+1]);
					}
				}
				break;
			}
		}
	}

	public drawShape(String shape) {
		count=1;
		stop=false;
		poly = new Polygon();
		x=new int[200];	//max 200 points in a polygon
		y=new int[200];
		x[0]=0;
		y[0]=0;
		this.shape=shape;
		setBackground(Color.WHITE);
		addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	if (shape=="polygon") {
            		if ((e.getButton()==MouseEvent.BUTTON1) && (!stop)) {
            			count++;
            		}
            		else if (e.getButton()==MouseEvent.BUTTON3) {
            			stop=true;
            		}
            	}
            	else {
            		x[0]=e.getX();
        			y[0]=e.getY();
        			x[1]=x[0];
        			y[1]=y[0];
            	}
    			repaint();
            }
        });
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (shape!="polygon") {
					x[1]=e.getX();
					y[1]=e.getY();
					repaint();
				}
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				if ((shape=="polygon") && (!stop)) {
					x[count]=e.getX();
					y[count]=e.getY();
					x[count+1]=e.getX();
					y[count+1]=e.getY();
					repaint();
				}
			}
        });
	}
	
}
