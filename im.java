import javax.swing.*;
import java.awt.*;

public class im extends JFrame{
	private JPanel frame;
	private JButton j1;
	public im() {
		super("ass1");
		
		frame=new JPanel();
		frame.setLayout(null);
	
		setLayout(null);
		Icon imgIcon = new ImageIcon("C:\\Users\\Kaushik\\Desktop\\computer science\\Object Oriented Techniques\\g3.gif");
		Icon imgIcon2 = new ImageIcon("C:\\Users\\Kaushik\\Desktop\\computer science\\Object Oriented Techniques\\button.png");
		j1=new JButton(imgIcon2);
		j1.setPreferredSize(new Dimension(40, 40));
		
		JLabel label = new JLabel(imgIcon);
		label.setBounds(0, 0, 1800, 1000); // You can use your own values
		label.setVisible(true);
		frame.add(label);
		
		
		//frame.add(j1);
		frame.setBounds(0,0,1800,1000);
		frame.setOpaque(false);
		frame.setVisible(true);

		add(frame);
		add(j1);
		j1.setBounds(0, 0, 1800, 1000); // You can use your own values
		j1.setVisible(true);
	}
	public static void main(String[] args) {
		im q=new im();
		q.setSize(1200,1000);
		q.setVisible(true);
		q.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}