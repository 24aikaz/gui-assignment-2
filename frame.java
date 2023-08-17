import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.event.*;

public class frame 
{
JButton b1;
    public frame() 
    {
    	String data[][]={ {"Fullname","Student ID"},{"Yashna Ramphull","2011063"},    
                {"Jashveer Paltan","2012379"},    
                {"Rudraman Soomirtee","2012304"},
                {"Kaushik Bullywon","2011321"},
                {"Tushaar Mungul","2013149"}};  
     
    	String header[]={"Fullname","Student ID"};         
       JTable jt=new JTable(data,header);
       
       jt.setFont(new Font("Serif", Font.BOLD, 20));
        jt.setOpaque(false);
       // ((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setOpaque(false);
        
       /* for (int column = 0; column < jt.getColumnCount(); column++) {
            for (int row = 0; row < jt.getRowCount(); row++) {
                TableCellRenderer renderer = jt.getCellRenderer(row, column);
               //((TableCellRenderer)jt.getDefaultRenderer(Object.class)).setOpaque(false);
                renderer.setOpaque(false);
                
                Component comp = jt.prepareRenderer(renderer, row, column);
            }
        }*/
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)jt.getDefaultRenderer(Object.class);
        renderer.setOpaque(false);
        jt.setShowGrid(false);
        JFrame obj = new JFrame("GUI Assignment");
        JLabel image = new JLabel(new ImageIcon("C:\\Users\\Kaushik\\Desktop\\computer science\\Object Oriented Techniques\\g3.gif"));
        //image.setBounds(0,0, 10, 10);
        //JLabel image2 = new JLabel(new ImageIcon("C:\\Users\\Kaushik\\Desktop\\computer science\\Object Oriented Techniques\\button.png"));
        Icon imgIcon2 = new ImageIcon("C:\\Users\\Kaushik\\Desktop\\computer science\\Object Oriented Techniques\\button.png");
		b1=new JButton(imgIcon2);
		///obj.add(jt);
        obj.setExtendedState(JFrame.MAXIMIZED_BOTH);
        obj.add(image);
       // b1.setBounds(600,350, 150, 40);
        b1.setBounds(600,550, 150, 40);
        jt.setBounds(525,400, 450, 95); // Chnage size here
        jt.setOpaque(false);
        image.add(jt);
        image.add(b1);
        MyHandler h= new MyHandler();
      b1.addActionListener(h);
        obj.setVisible(true);
        
        
    }
    
    public class MyHandler implements ActionListener{
    	
    	public void actionPerformed(ActionEvent ae) {
    		if(ae.getSource()==b1) {
    			new MainWindow();
            }
    	}
    }
    public static void main(String args[]) 
    {
        new frame();
    }
}