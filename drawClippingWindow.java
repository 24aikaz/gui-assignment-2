import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class drawClippingWindow extends JPanel {
	
	int[] ptX;
	int[] ptY;
	String shape;
	
	int x1, y1, x2, y2;
	int xMin, yMin, xMax, yMax;
	
	
	
	public int getxMin() {
		return xMin;
	}

	public int getyMin() {
		return yMin;
	}

	public int getxMax() {
		return xMax;
	}
	
	public int getyMax() {
		return yMax;
	}
	
	public void initXY() {
		xMin = -1;
		yMin = -1;
		xMax = -1;
		yMax = -1;
	}

	public drawClippingWindow(int[] ptX, int[] ptY, String shape) {
		
		this.ptX=ptX;
		this.ptY=ptY;
		this.shape=shape;
		initXY();
		setBackground(Color.WHITE);
		
		addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
        		x1=e.getX();
    			y1=e.getY();
    			x2=x1;
    			y2=y1;
    			repaint();
            }
        });
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
					x2=e.getX();
					y2=e.getY();
					
					xMin = Math.min(x1, x2);
					yMin = Math.min(y1, y2);
					xMax = Math.max(x1, x2);
					yMax = Math.max(y1, y2);
					
					repaint();
			}
        });
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D ga = (Graphics2D) g;
		drawShape.drawGrid(ga, this.getWidth(), this.getHeight());
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
		
		ga.setColor(Color.decode("#012A4A"));
		ga.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
		ga.drawRect(xMin, yMin, xMax-xMin, yMax-yMin);
		
	}

}
