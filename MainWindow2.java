
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MainWindow2 extends JFrame {

    // Color palette
    Color light_whitish_gray = new Color(222, 225, 221);
    Color whitish_gray = new Color(202, 205, 193);
    Color light_bluish_gray = new Color(153, 174, 173);
    Color bluish_gray = new Color(109, 145, 151);
    Color light_grayish_green = new Color(101, 139, 111);
    Color dark_bluish_gray = new Color(47, 87, 93);
    Color dark_grayish_green = new Color(40, 54, 61);

    drawShape ds;

    JFrame frame; // the main frame

    // Title bar buttons
    JButton minimize_btn, maximize_btn, close_btn;

    //Menu buttons
    JButton menu_btn_collapse, menu_btn_expand; 

    // Panels
    JPanel titlebar_panel, drawing_panel, menu_panel, full_menu_panel;

    public MainWindow2() {
        InitializeGUI();
    }

    public void InitializeGUI() {
        // ICON MANAGEMENT
        ImageIcon app_icon_img = new ImageIcon("icons/icon_nobg.png");
        setIconImage(app_icon_img.getImage());

        // TITLE BAR PANEL
        titlebar_panel = new JPanel(new BorderLayout());
        JPanel button_container = new JPanel(new FlowLayout());
        button_container.setBackground(light_bluish_gray);
        
        close_btn = new JButton(new ImageIcon(new ImageIcon("icons/close.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
        close_btn.setBackground(light_bluish_gray);
        close_btn.setBorderPainted(false);
        close_btn.setFocusPainted(false);
        close_btn.addActionListener(new btnCloseHandler());

        maximize_btn = new JButton(new ImageIcon(new ImageIcon("icons/window-restore-regular.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
        maximize_btn.setBackground(light_bluish_gray);
        maximize_btn.setBorderPainted(false);
        maximize_btn.setFocusPainted(false);

        minimize_btn = new JButton(new ImageIcon(new ImageIcon("icons/window-minimize-solid.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
        minimize_btn.setBackground(light_bluish_gray);
        minimize_btn.setBorderPainted(false);
        minimize_btn.setFocusPainted(false);
        
        titlebar_panel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 30));
        titlebar_panel.setBackground(light_bluish_gray);
        button_container.add(minimize_btn);
        button_container.add(maximize_btn);
        button_container.add(close_btn);
        titlebar_panel.add(button_container, BorderLayout.EAST);

        // DRAWING PANEL
        drawing_panel = new JPanel(new GridLayout(0, 1, 0, 0));
		ds = new drawShape("rectangle");
		drawing_panel.add(ds);
        drawing_panel.setBackground(light_whitish_gray);
		add(drawing_panel, BorderLayout.CENTER);

        // Button effects
        close_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               close_btn.setBackground(light_grayish_green);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               close_btn.setBackground(light_bluish_gray);
            }
        }); 


        //call the MENU PANEL
        CollapsedMenu();
        
        setUndecorated(true);
        add(titlebar_panel, BorderLayout.NORTH);
        setTitle("Draw");
        setVisible(true);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // MENU UI ACTIONS
    public void CollapsedMenu(){
        menu_panel = new JPanel(new BorderLayout());
        menu_panel.setPreferredSize(new Dimension(30, Toolkit.getDefaultToolkit().getScreenSize().height));
        menu_panel.setBackground(bluish_gray);
        menu_btn_collapse = new JButton(new ImageIcon(new ImageIcon("icons/bars-solid.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        menu_btn_collapse.setBorderPainted(false);
        menu_btn_collapse.setBackground(bluish_gray);
        menu_btn_collapse.setFocusPainted(false);
        menu_btn_collapse.addActionListener(new menuExpand());
        menu_panel.add(menu_btn_collapse, BorderLayout.NORTH);
        add(menu_panel, BorderLayout.WEST);
    }

    public class menuExpand implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            menu_panel.setVisible(false);
			RenderMenu();
		}
    }

    public void RenderMenu(){
        full_menu_panel = new JPanel(new GridLayout(9, 1, 5, 0));
        full_menu_panel.setPreferredSize(new Dimension(400, Toolkit.getDefaultToolkit().getScreenSize().height));
        full_menu_panel.setBackground(bluish_gray);

        JPanel header = new JPanel(new BorderLayout(ALLBITS, ABORT));
        menu_btn_expand = new JButton(new ImageIcon(new ImageIcon("icons/arrow-left-solid.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        menu_btn_expand.addActionListener(new menuCollapse());
        menu_btn_expand.setBackground(bluish_gray);
        menu_btn_expand.setBorderPainted(false);
        menu_btn_expand.setFocusPainted(false);
        JLabel menu_title = new JLabel("App Tools");
        menu_title.setForeground(whitish_gray);
        header.setBackground(bluish_gray);
        header.add(menu_btn_expand, BorderLayout.WEST);
        header.add(menu_title, BorderLayout.CENTER);
        full_menu_panel.add(header);
        
        
        add(full_menu_panel, BorderLayout.WEST);
    }

    public class menuCollapse implements ActionListener{
        public void actionPerformed(ActionEvent e) {
			full_menu_panel.setVisible(false);
            CollapsedMenu();
		}
    }

    // BUTTON HANDLERS
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
    

    public static void main(String[] args) {
        new MainWindow2();
    }

}
