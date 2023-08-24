
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
    JButton minimize_btn, maximize_btn, close_btn, resize_btn;

    //Menu buttons
    JButton menu_btn_collapse, menu_btn_expand; 
    JButton line_btn, circle_btn, square_btn, polygon_btn;
    JButton color_picker_btn, apply_color_btn;
    JButton transform_picker_btn, apply_transform_btn, tranform_close_btn;
    JButton clip_btn, apply_clip_btn;
    JButton clear_btn, play_btn;

    // Panels
    JPanel titlebar_panel, drawing_panel, menu_panel, full_menu_panel, content_panel, transformation_panel;

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
        
        close_btn = new JButton(new ImageIcon(new ImageIcon("icons/close2.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
        close_btn.setBackground(light_bluish_gray);
        close_btn.setBorderPainted(false);
        close_btn.setFocusPainted(false);
        close_btn.addActionListener(new btnCloseHandler());

        resize_btn = new JButton(new ImageIcon(new ImageIcon("icons/resize.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
        resize_btn.setBackground(light_bluish_gray);
        resize_btn.setBorderPainted(false);
        resize_btn.setFocusPainted(false);

        minimize_btn = new JButton(new ImageIcon(new ImageIcon("icons/minimize.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
        minimize_btn.setBackground(light_bluish_gray);
        minimize_btn.setBorderPainted(false);
        minimize_btn.setFocusPainted(false);

        maximize_btn = new JButton(new ImageIcon(new ImageIcon("icons/window-maximize.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
        maximize_btn.setBackground(light_bluish_gray);
        maximize_btn.setBorderPainted(false);
        maximize_btn.setFocusPainted(false);
        
        titlebar_panel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 30));
        titlebar_panel.setBackground(light_bluish_gray);
        button_container.add(minimize_btn);
        button_container.add(resize_btn);
        button_container.add(close_btn);
        titlebar_panel.add(button_container, BorderLayout.EAST);

        // PANEL that contains the drawing panel and menu
        content_panel = new JPanel(new BorderLayout());
        add(content_panel, BorderLayout.CENTER);

        // DRAWING PANEL
        drawing_panel = new JPanel(new GridLayout(0, 1, 0, 0));
		ds = new drawShape("rectangle");
		drawing_panel.add(ds);
        drawing_panel.setBackground(light_whitish_gray);
		content_panel.add(drawing_panel, BorderLayout.CENTER);

        // Button effects
        close_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               close_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/close-hover.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               close_btn.setBackground(light_bluish_gray);
               close_btn.setIcon(new ImageIcon(new ImageIcon("icons/close2.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
        });
        resize_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               resize_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/resize-hover.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               resize_btn.setBackground(light_bluish_gray);
               resize_btn.setIcon(new ImageIcon(new ImageIcon("icons/resize.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
        });
        minimize_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               minimize_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/minimize-hover.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               minimize_btn.setBackground(light_bluish_gray);
               minimize_btn.setIcon(new ImageIcon(new ImageIcon("icons/minimize.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
        });
        maximize_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               maximize_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/window-maximize-hover.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               maximize_btn.setBackground(light_bluish_gray);
               maximize_btn.setIcon(new ImageIcon(new ImageIcon("icons/window-maximize.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
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
        menu_panel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 40));
        menu_panel.setBackground(bluish_gray);
        menu_btn_collapse = new JButton(new ImageIcon(new ImageIcon("icons/bars-solid.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        menu_btn_collapse.setBorderPainted(false);
        menu_btn_collapse.setBackground(bluish_gray);
        menu_btn_collapse.setFocusPainted(false);
        menu_btn_collapse.addActionListener(new menuExpand());
        menu_panel.add(menu_btn_collapse, BorderLayout.WEST);
        content_panel.add(menu_panel, BorderLayout.NORTH);
    }

    public class menuExpand implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            menu_panel.setVisible(false);
			RenderMenu();
		}
    }

    public void RenderMenu(){
        full_menu_panel = new JPanel(new BorderLayout());
        full_menu_panel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 200));
        full_menu_panel.setBackground(bluish_gray);

        JPanel header = new JPanel(new BorderLayout(ALLBITS, ABORT));
        menu_btn_expand = new JButton(new ImageIcon(new ImageIcon("icons/chevron-up-solid.png").getImage().getScaledInstance(18, 20, Image.SCALE_DEFAULT)));
        menu_btn_expand.addActionListener(new menuCollapse());
        menu_btn_expand.setBackground(bluish_gray);
        menu_btn_expand.setBorderPainted(false);
        menu_btn_expand.setFocusPainted(false);
        JLabel menu_title = new JLabel("App Tools");
        menu_title.setForeground(whitish_gray);
        header.setBackground(bluish_gray);
        header.add(menu_btn_expand, BorderLayout.WEST);
        header.add(menu_title, BorderLayout.CENTER);
        full_menu_panel.add(header, BorderLayout.NORTH);

        MenuDetails();  //Function for adding the contents of the menu
        
        content_panel.add(full_menu_panel, BorderLayout.NORTH);
    }

    public class menuCollapse implements ActionListener{
        public void actionPerformed(ActionEvent e) {
			full_menu_panel.setVisible(false);
            transformation_panel.setVisible(false);
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


    public void MenuDetails(){
        JPanel tools_container = new JPanel(new GridLayout(1, 5, 10, 10));
        
        // SHAPES
        JPanel shapes_panel = new JPanel(new BorderLayout());
        JLabel shapes_label = new JLabel("Shape");
        shapes_panel.add(shapes_label, BorderLayout.NORTH);
        JPanel shapes_container = new JPanel(new GridLayout(2, 2));
        line_btn = new JButton(new ImageIcon(new ImageIcon("icons/slash-solid.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        shapes_container.add(line_btn);

        circle_btn = new JButton(new ImageIcon(new ImageIcon("icons/circle-regular.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        shapes_container.add(circle_btn);

        square_btn = new JButton(new ImageIcon(new ImageIcon("icons/square-regular.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        shapes_container.add(square_btn);

        polygon_btn = new JButton(new ImageIcon(new ImageIcon("icons/draw-polygon-solid.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        shapes_container.add(polygon_btn);

        shapes_panel.add(shapes_container, BorderLayout.CENTER);
        tools_container.add(shapes_panel);

        // COLOUR
        JPanel colour_panel = new JPanel(new BorderLayout());
        JLabel colour_label = new JLabel("Colour");
        colour_panel.add(colour_label, BorderLayout.NORTH);
        JPanel colour_container = new JPanel(new GridLayout(2, 1));

        color_picker_btn = new JButton("Choose color");
        colour_container.add(color_picker_btn);

        apply_color_btn = new JButton(new ImageIcon(new ImageIcon("icons/paint-roller-solid.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        colour_container.add(apply_color_btn);

        colour_panel.add(colour_container, BorderLayout.CENTER);

        tools_container.add(colour_panel);

        // TRANSFORMATION
        JPanel transform_panel = new JPanel(new BorderLayout());
        JLabel transform_label = new JLabel("Transform");
        transform_panel.add(transform_label, BorderLayout.NORTH);
        JPanel transform_container = new JPanel(new GridLayout(2, 1));

        transform_picker_btn = new JButton("Choose transformation");
        transform_picker_btn.addActionListener(new transformDetails());
        transform_container.add(transform_picker_btn);
        transformations();
        transformation_panel.setVisible(false);

        transform_panel.add(transform_container, BorderLayout.CENTER);

        tools_container.add(transform_panel);

        // CLIPPING
        JPanel clip_panel = new JPanel(new BorderLayout());
        JLabel clip_label = new JLabel("Clip");
        clip_panel.add(clip_label, BorderLayout.NORTH);
        JPanel clip_container = new JPanel(new GridLayout(2, 1));

        clip_btn = new JButton("Start clipping");
        clip_container.add(clip_btn);

        apply_clip_btn = new JButton(new ImageIcon(new ImageIcon("icons/crop-simple-solid.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        clip_container.add(apply_clip_btn);

        clip_panel.add(clip_container);

        tools_container.add(clip_panel);

        // ANIMATION
        JPanel animate_panel = new JPanel(new BorderLayout());
        JLabel animate_label = new JLabel("Animation");
        animate_panel.add(animate_label, BorderLayout.NORTH);
        JPanel animate_container = new JPanel(new GridLayout(1, 2));

        play_btn = new JButton(new ImageIcon(new ImageIcon("icons/play-solid.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        animate_container.add(play_btn);

        clear_btn = new JButton(new ImageIcon(new ImageIcon("icons/rotate-solid.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        animate_container.add(clear_btn);

        animate_panel.add(animate_container, BorderLayout.CENTER);

        tools_container.add(animate_panel);


        full_menu_panel.add(tools_container, BorderLayout.CENTER);
    }
    public class transformDetails implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            transformation_panel.setVisible(true);
		}
    }    

    public void transformations(){
        transformation_panel = new JPanel(new BorderLayout());
        transformation_panel.setPreferredSize(new Dimension(400, Toolkit.getDefaultToolkit().getScreenSize().height));
        JPanel header2 = new JPanel(new BorderLayout());
        tranform_close_btn = new JButton(new ImageIcon(new ImageIcon("icons/close.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
        tranform_close_btn.addActionListener(new transformClose());
        header2.add(tranform_close_btn, BorderLayout.WEST);
        JLabel transform_title = new JLabel("Configuration");
        header2.add(transform_title, BorderLayout.CENTER);
        transformation_panel.add(header2, BorderLayout.NORTH);

        
        
        
        apply_transform_btn = new JButton(new ImageIcon(new ImageIcon("icons/wand-sparkles-solid.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        transformation_panel.add(apply_transform_btn, BorderLayout.SOUTH);

        content_panel.add(transformation_panel, BorderLayout.EAST);
    }

    public class transformClose implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            transformation_panel.setVisible(false);
		}
    } 
    

    public static void main(String[] args) {
        new MainWindow2();
    }

}
