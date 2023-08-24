import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class welcome extends JFrame{

    // Color palette
    Color light_whitish_gray = new Color(222, 225, 221);
    Color whitish_gray = new Color(202, 205, 193);
    Color light_bluish_gray = new Color(153, 174, 173);
    Color bluish_gray = new Color(109, 145, 151);
    Color light_grayish_green = new Color(101, 139, 111);
    Color dark_bluish_gray = new Color(47, 87, 93);
    Color dark_grayish_green = new Color(40, 54, 61);

    // Title bar buttons
    JButton minimize_btn, maximize_btn, close_btn, resize_btn;

    //Other buttons
    JButton start_btn;

    // Panels
    JPanel titlebar_panel, content_panel, button_panel;
    
    public welcome(){
        InitializeUI();
    }

    public void InitializeUI(){
        // ICON MANAGEMENT
        ImageIcon app_icon_img = new ImageIcon("icons/icon_nobg.png");
        setIconImage(app_icon_img.getImage());

        // TITLE BAR PANEL
        titlebar_panel = new JPanel(new BorderLayout());
        JPanel button_container = new JPanel(new FlowLayout());
        button_container.setBackground(light_bluish_gray);
        
        close_btn = new JButton(new ImageIcon(new ImageIcon("icons/normal/close2.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
        close_btn.setBackground(light_bluish_gray);
        close_btn.setBorderPainted(false);
        close_btn.setFocusPainted(false);
        close_btn.addActionListener(new btnCloseHandler());

        resize_btn = new JButton(new ImageIcon(new ImageIcon("icons/normal/resize.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
        resize_btn.setBackground(light_bluish_gray);
        resize_btn.setBorderPainted(false);
        resize_btn.setFocusPainted(false);

        minimize_btn = new JButton(new ImageIcon(new ImageIcon("icons/normal/minimize.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
        minimize_btn.setBackground(light_bluish_gray);
        minimize_btn.setBorderPainted(false);
        minimize_btn.setFocusPainted(false);

        maximize_btn = new JButton(new ImageIcon(new ImageIcon("icons/normal/window-maximize.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
        maximize_btn.setBackground(light_bluish_gray);
        maximize_btn.setBorderPainted(false);
        maximize_btn.setFocusPainted(false);
        
        titlebar_panel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 30));
        titlebar_panel.setBackground(light_bluish_gray);
        button_container.add(minimize_btn);
        button_container.add(maximize_btn);
        button_container.add(close_btn);
        titlebar_panel.add(button_container, BorderLayout.EAST);

        // Content panel
        content_panel = new JPanel(new BorderLayout());
        content_panel.setBackground(light_whitish_gray);

        JPanel app_icon_panel = new JPanel(new FlowLayout());
        app_icon_panel.setBackground(light_whitish_gray);
        JLabel imgJLabel = new JLabel(new ImageIcon(new ImageIcon("icons/icon_nobg.png").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
        app_icon_panel.add(imgJLabel);
        content_panel.add(app_icon_panel, BorderLayout.NORTH);

        JPanel labels = new JPanel(new GridLayout(15, 1));
        labels.setBackground(light_whitish_gray);

        JLabel h1 = new JLabel("Welcome!", SwingConstants.CENTER);
        h1.setFont(new Font("Arial", Font.BOLD, 30));
        h1.setForeground(light_grayish_green);
        JLabel p_1 = new JLabel("Members of the team: ", SwingConstants.CENTER);
        p_1.setFont(new Font("Arial", Font.PLAIN, 16));
        p_1.setForeground(dark_grayish_green);

        JLabel name1 = new JLabel("Jaynesh", SwingConstants.CENTER);
        JLabel name2 = new JLabel("Isha", SwingConstants.CENTER);
        JLabel name3 = new JLabel("Ashley", SwingConstants.CENTER);
        JLabel name4 = new JLabel("Zakiyyah", SwingConstants.CENTER);

        name1.setFont(new Font("Arial", Font.PLAIN, 20));
        name2.setFont(new Font("Arial", Font.PLAIN, 20));
        name3.setFont(new Font("Arial", Font.PLAIN, 20));
        name4.setFont(new Font("Arial", Font.PLAIN, 20));
        name1.setForeground(dark_bluish_gray);
        name2.setForeground(dark_bluish_gray);
        name3.setForeground(dark_bluish_gray);
        name4.setForeground(dark_bluish_gray);

        JLabel p_2 = new JLabel("Press the button to start!", SwingConstants.CENTER);
        p_2.setFont(new Font("Arial", Font.PLAIN, 16));
        p_2.setForeground(dark_grayish_green);

        labels.add(new JLabel(""));
        labels.add(h1);
        labels.add(new JLabel(""));
        labels.add(new JLabel(""));
        labels.add(p_1);
        labels.add(new JLabel(""));
        labels.add(name1);
        labels.add(name2);
        labels.add(name3);
        labels.add(name4);
        labels.add(new JLabel(""));
        labels.add(new JLabel(""));
        labels.add(new JLabel(""));
        labels.add(p_2);

        content_panel.add(labels);

        add(content_panel, BorderLayout.CENTER);

        // Button panel
        button_panel = new JPanel(new BorderLayout());
        button_panel.setBackground(light_whitish_gray);
        JPanel btn_container = new JPanel(new FlowLayout());
        btn_container.setBackground(light_whitish_gray);
        start_btn = new JButton(new ImageIcon(new ImageIcon("icons/normal/power-off-solid.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        start_btn.setBackground(light_whitish_gray);
        start_btn.setBorderPainted(false);
        start_btn.setFocusPainted(false);
        start_btn.setToolTipText("Click to start drawing");
        start_btn.addActionListener(new btnStartHandler());
        btn_container.add(start_btn);
        button_panel.add(btn_container);
        add(button_panel, BorderLayout.SOUTH);

        setUndecorated(true);
        add(titlebar_panel, BorderLayout.NORTH);
        setTitle("Welcome");
        setVisible(true);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Hover effects
        close_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               close_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/close-hover.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               close_btn.setBackground(light_bluish_gray);
               close_btn.setIcon(new ImageIcon(new ImageIcon("icons/normal/close2.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
        });
        resize_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               resize_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/resize-hover.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               resize_btn.setBackground(light_bluish_gray);
               resize_btn.setIcon(new ImageIcon(new ImageIcon("icons/normal/resize.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
        });
        minimize_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               minimize_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/minimize-hover.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               minimize_btn.setBackground(light_bluish_gray);
               minimize_btn.setIcon(new ImageIcon(new ImageIcon("icons/normal/minimize.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
        });
        maximize_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               maximize_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/window-maximize-hover.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               maximize_btn.setBackground(light_bluish_gray);
               maximize_btn.setIcon(new ImageIcon(new ImageIcon("icons/normal/window-maximize.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
        });
        start_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               start_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/power-hover.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               start_btn.setBackground(light_whitish_gray);
               start_btn.setIcon(new ImageIcon(new ImageIcon("icons/normal/power-off-solid.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
            }
        });
    }

    public class btnCloseHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==close_btn) {
			  dispose();
			}
			if (e.getSource()==minimize_btn) {
			setState(Frame.ICONIFIED);
			}
		}
	}

    public class btnStartHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
            new MainWindow2();
		}
	}

    public static void main(String[] args){
        new welcome();
    }

}
