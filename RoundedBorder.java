import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

public class RoundedBorder implements Border {

	    private int radius;
	    private Color color;
	    boolean fill;

	    RoundedBorder(int radius, Color color) {
	        this.radius = radius;
	        this.color = color;
	        fill = true;
	    }
	    
	    RoundedBorder(int radius, Color color, boolean fill) {
	        this.radius = radius;
	        this.color = color;
	        this.fill = fill;
	    }


	    public Insets getBorderInsets(Component c) {
	        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
	    }


	    public boolean isBorderOpaque() {
	        return true;
	    }


	    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	    	g.setColor(color);
	    	if (fill) {
	    		g.fillRoundRect(x, y, width-1, height-1, radius, radius);
	    	}
	    	else {
	    		g.drawRoundRect(x, y, width-1, height-1, radius, radius);
	    	}
	    }
	}