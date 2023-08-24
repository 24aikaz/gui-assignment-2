
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

    // Font sizes
    int h3 = 18;
    int h4 = 14;
    int h5 = 16;
    int h6 = 12;

    drawShape ds;

    drawClippingWindow cw;

    Color selectedColor;

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

    //For transformation panel
    JPanel transformation_content;
    JComboBox<String> jcb_transformations;
	JComboBox<String> jcb_shearOptions;
	JComboBox<String> jcb_reflectionOptions;
    JTextField tf_tx, tf_ty;
	JTextField tf_normalRotationDeg, tf_fpRotationDeg;
	JTextField tf_fpRotationX, tf_fpRotationY;
	JTextField tf_normalScalingSx, tf_normalScalingSy;
	JTextField tf_fpScalingSx, tf_fpScalingSy;
	JTextField tf_fpScalingX, tf_fpScalingY;
	JTextField tf_shearFactor;
    JPanel jp_transformationContainer, jp_translation, jp_normalRotation, jp_rotationFixedPoint, jp_normalScaling, jp_fpScaling, jp_shear, jp_reflection;
    JPanel jp_optionsOptions;
    JPanel translation_panel, nRotation_panel, fpRotation_panel, nScaling_panel, fpScaling_panel, shear_panel, reflection_panel;
    JPanel tContainer;

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
        menu_btn_collapse = new JButton(new ImageIcon(new ImageIcon("icons/normal/chevron-down.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
        menu_btn_collapse.setBorderPainted(false);
        menu_btn_collapse.setBackground(bluish_gray);
        menu_btn_collapse.setFocusPainted(false);
        menu_btn_collapse.addActionListener(new menuExpand());
        menu_panel.add(menu_btn_collapse, BorderLayout.WEST);
        content_panel.add(menu_panel, BorderLayout.NORTH);

        menu_btn_collapse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               menu_btn_collapse.setIcon(new ImageIcon(new ImageIcon("icons/hover/chevron-down-hover.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               menu_btn_collapse.setBackground(bluish_gray);
               menu_btn_collapse.setIcon(new ImageIcon(new ImageIcon("icons/normal/chevron-down.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
            }
        });
    }

    public class menuExpand implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            menu_panel.setVisible(false);
			RenderMenu();
		}
    }

    public void RenderMenu(){
        full_menu_panel = new JPanel(new BorderLayout());
        full_menu_panel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 150));
        full_menu_panel.setBackground(bluish_gray);

        JPanel header = new JPanel(new BorderLayout(ALLBITS, ABORT));
        menu_btn_expand = new JButton(new ImageIcon(new ImageIcon("icons/normal/chevron-up.png").getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT)));
        menu_btn_expand.addActionListener(new menuCollapse());
        menu_btn_expand.setBackground(bluish_gray);
        menu_btn_expand.setBorderPainted(false);
        menu_btn_expand.setFocusPainted(false);
        menu_btn_expand.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               menu_btn_expand.setIcon(new ImageIcon(new ImageIcon("icons/hover/chevron-up-hover.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               menu_btn_expand.setBackground(bluish_gray);
               menu_btn_expand.setIcon(new ImageIcon(new ImageIcon("icons/normal/chevron-up.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
            }
        });
        JLabel menu_title = new JLabel("App Tools");
        menu_title.setFont(new Font("Arial", Font.BOLD, h6));
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
        tools_container.setBackground(bluish_gray);

        // SHAPES
        JPanel shapes_panel = new JPanel(new BorderLayout());
        JLabel shapes_label = new JLabel("Shapes", SwingConstants.CENTER);
        shapes_label.setFont(new Font("Arial", Font.PLAIN, h4));
        shapes_label.setForeground(light_whitish_gray);
        shapes_panel.add(shapes_label, BorderLayout.NORTH);
        shapes_panel.setBackground(bluish_gray);
        JPanel shapes_container = new JPanel(new GridLayout(2, 2));
        shapes_container.setBackground(bluish_gray);

        line_btn = new JButton(new ImageIcon(new ImageIcon("icons/normal/line.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        line_btn.addActionListener(new drawingToolHandler());
        line_btn.setToolTipText("Draw Line");
        shapes_container.add(line_btn);

        circle_btn = new JButton(new ImageIcon(new ImageIcon("icons/normal/circle.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        circle_btn.addActionListener(new drawingToolHandler());
        circle_btn.setToolTipText("Draw Circle");
        shapes_container.add(circle_btn);

        square_btn = new JButton(new ImageIcon(new ImageIcon("icons/normal/square.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        square_btn.addActionListener(new drawingToolHandler());
        square_btn.setToolTipText("Draw Rectangle");
        shapes_container.add(square_btn);

        polygon_btn = new JButton(new ImageIcon(new ImageIcon("icons/normal/polygon.png").getImage().getScaledInstance(30, 32, Image.SCALE_DEFAULT)));
        polygon_btn.addActionListener(new drawingToolHandler());
        polygon_btn.setToolTipText("Draw Polygon");
        shapes_container.add(polygon_btn);

        shapes_panel.add(shapes_container, BorderLayout.CENTER);
        tools_container.add(shapes_panel);

        // COLOUR
        JPanel colour_panel = new JPanel(new BorderLayout());
        colour_panel.setBackground(bluish_gray);
        JLabel colour_label = new JLabel("Colour", SwingConstants.CENTER);
        colour_label.setFont(new Font("Arial", Font.PLAIN, h4));
        colour_label.setForeground(light_whitish_gray);
        colour_panel.add(colour_label, BorderLayout.NORTH);
        JPanel colour_container = new JPanel(new GridLayout(2, 1));
        colour_container.setBackground(bluish_gray);

        JPanel c = new JPanel(new FlowLayout());
        c.setBackground(bluish_gray);
        color_picker_btn = new JButton("Pick Color");
        color_picker_btn.setToolTipText("Click to open color picker");
        color_picker_btn.setFont(new Font("Arial", Font.BOLD, h3));
        color_picker_btn.addActionListener(new btnColorPickerHandler());
        c.add(color_picker_btn);
        colour_container.add(c);

        apply_color_btn = new JButton(new ImageIcon(new ImageIcon("icons/normal/paint.png").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
        apply_color_btn.addActionListener(new btnFillHandler());
        apply_color_btn.setToolTipText("Click to apply color on drawing");
        colour_container.add(apply_color_btn);

        colour_panel.add(colour_container, BorderLayout.CENTER);
        tools_container.add(colour_panel);

        // TRANSFORMATION
        JPanel transform_panel = new JPanel(new BorderLayout());
        transform_panel.setBackground(bluish_gray);
        JLabel transform_label = new JLabel("Transform", SwingConstants.CENTER);
        transform_label.setFont(new Font("Arial", Font.PLAIN, h4));
        transform_label.setForeground(light_whitish_gray);
        transform_panel.add(transform_label, BorderLayout.NORTH);
        JPanel transform_container = new JPanel(new GridLayout(2, 1));
        transform_container.setBackground(bluish_gray);

        JPanel t = new JPanel(new FlowLayout());
        t.setBackground(bluish_gray);
        transform_picker_btn = new JButton("Pick Type");
        transform_picker_btn.setToolTipText("Click to open configurations");
        transform_picker_btn.setFont(new Font("Arial", Font.BOLD, h3));
        transform_picker_btn.addActionListener(new transformDetails());
        t.add(transform_picker_btn);
        transform_container.add(t);
        transformations();
        transformation_panel.setVisible(false);

        transform_panel.add(transform_container, BorderLayout.CENTER);

        tools_container.add(transform_panel);

        // CLIPPING
        cw = new drawClippingWindow(null, null, null);
        JPanel clip_panel = new JPanel(new BorderLayout());
        clip_panel.setBackground(bluish_gray);
        JLabel clip_label = new JLabel("Clip", SwingConstants.CENTER);
        clip_label.setFont(new Font("Arial", Font.PLAIN, h4));
        clip_label.setForeground(light_whitish_gray);
        clip_panel.add(clip_label, BorderLayout.NORTH);
        JPanel clip_container = new JPanel(new GridLayout(2, 1));
        clip_container.setBackground(bluish_gray);

        JPanel cl = new JPanel(new FlowLayout());
        cl.setBackground(bluish_gray);
        clip_btn = new JButton("Pick Clip");
        clip_btn.setToolTipText("Click to start cropping");
        clip_btn.setFont(new Font("Arial", Font.BOLD, h3));
        clip_btn.addActionListener(new btnClippingHandler());
        cl.add(clip_btn);
        clip_container.add(cl);

        apply_clip_btn = new JButton(new ImageIcon(new ImageIcon("icons/normal/crop.png").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
        apply_clip_btn.addActionListener(new btnClippingHandler());
        apply_clip_btn.setToolTipText("Apply the clip on screen");
        clip_container.add(apply_clip_btn);

        clip_panel.add(clip_container);

        tools_container.add(clip_panel);

        // ANIMATION
        JPanel animate_panel = new JPanel(new BorderLayout());
        animate_panel.setBackground(bluish_gray);
        JLabel animate_label = new JLabel("Animation", SwingConstants.CENTER);
        animate_label.setFont(new Font("Arial", Font.PLAIN, h4));
        animate_label.setForeground(light_whitish_gray);
        animate_panel.add(animate_label, BorderLayout.NORTH);
        JPanel animate_container = new JPanel(new GridLayout(1, 2));
        animate_container.setBackground(bluish_gray);

        play_btn = new JButton(new ImageIcon(new ImageIcon("icons/normal/play.png").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
        play_btn.setToolTipText("Click to play animation");
        animate_container.add(play_btn);

        clear_btn = new JButton(new ImageIcon(new ImageIcon("icons/normal/rotate.png").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
        clear_btn.setToolTipText("Click to clear animation buffer");
        animate_container.add(clear_btn);

        animate_panel.add(animate_container, BorderLayout.CENTER);

        tools_container.add(animate_panel);

        // Styling buttons
        line_btn.setBackground(bluish_gray);
        line_btn.setBorderPainted(false);
        line_btn.setFocusPainted(false);
        line_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               line_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/line-hover.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               line_btn.setBackground(bluish_gray);
               line_btn.setIcon(new ImageIcon(new ImageIcon("icons/normal/line.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
            }
        });

        square_btn.setBackground(bluish_gray);
        square_btn.setBorderPainted(false);
        square_btn.setFocusPainted(false);
        square_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               square_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/square-hover.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               square_btn.setBackground(bluish_gray);
               square_btn.setIcon(new ImageIcon(new ImageIcon("icons/normal/square.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
            }
        });

        circle_btn.setBackground(bluish_gray);
        circle_btn.setBorderPainted(false);
        circle_btn.setFocusPainted(false);
        circle_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               circle_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/circle-hover.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               circle_btn.setBackground(bluish_gray);
               circle_btn.setIcon(new ImageIcon(new ImageIcon("icons/normal/circle.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
            }
        });
        

        polygon_btn.setBackground(bluish_gray);
        polygon_btn.setBorderPainted(false);
        polygon_btn.setFocusPainted(false);
        polygon_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               polygon_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/polygon-hover.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               polygon_btn.setBackground(bluish_gray);
               polygon_btn.setIcon(new ImageIcon(new ImageIcon("icons/normal/polygon.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
            }
        });

        color_picker_btn.setBackground(bluish_gray);
        color_picker_btn.setBorderPainted(false);
        color_picker_btn.setFocusPainted(false);
        color_picker_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               color_picker_btn.setForeground(dark_bluish_gray);
               color_picker_btn.setBackground(light_bluish_gray);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               color_picker_btn.setBackground(bluish_gray);
               color_picker_btn.setForeground(dark_grayish_green);
            }
        });

        apply_color_btn.setBackground(bluish_gray);
        apply_color_btn.setBorderPainted(false);
        apply_color_btn.setFocusPainted(false);
        apply_color_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               apply_color_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/paint-hover.png").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               apply_color_btn.setBackground(bluish_gray);
               apply_color_btn.setIcon(new ImageIcon(new ImageIcon("icons/normal/paint.png").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
            }
        });

        transform_picker_btn.setBackground(bluish_gray);
        transform_picker_btn.setBorderPainted(false);
        transform_picker_btn.setFocusPainted(false);
        transform_picker_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               transform_picker_btn.setForeground(dark_bluish_gray);
               transform_picker_btn.setBackground(light_bluish_gray);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               transform_picker_btn.setBackground(bluish_gray);
               transform_picker_btn.setForeground(dark_grayish_green);
            }
        });

        clip_btn.setBackground(bluish_gray);
        clip_btn.setBorderPainted(false);
        clip_btn.setFocusPainted(false);
        clip_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               clip_btn.setForeground(dark_bluish_gray);
               clip_btn.setBackground(light_bluish_gray);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               clip_btn.setBackground(bluish_gray);
               clip_btn.setForeground(dark_grayish_green);
               
            }
        });

        apply_clip_btn.setBackground(bluish_gray);
        apply_clip_btn.setBorderPainted(false);
        apply_clip_btn.setFocusPainted(false);
        apply_clip_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               apply_clip_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/crop-hover.png").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               apply_clip_btn.setBackground(bluish_gray);
               apply_clip_btn.setIcon(new ImageIcon(new ImageIcon("icons/normal/crop.png").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
            }
        });

        clear_btn.setBackground(bluish_gray);
        clear_btn.setBorderPainted(false);
        clear_btn.setFocusPainted(false);
        clear_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               clear_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/rotate-hover.png").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               clear_btn.setBackground(bluish_gray);
               clear_btn.setIcon(new ImageIcon(new ImageIcon("icons/normal/rotate.png").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
            }
        });

        play_btn.setBackground(bluish_gray);
        play_btn.setBorderPainted(false);
        play_btn.setFocusPainted(false);
        play_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               play_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/play-hover.png").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               play_btn.setBackground(bluish_gray);
               play_btn.setIcon(new ImageIcon(new ImageIcon("icons/normal/play.png").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
            }
        });


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
        tranform_close_btn = new JButton(new ImageIcon(new ImageIcon("icons/normal/close2.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
        tranform_close_btn.addActionListener(new transformClose());
        header2.add(tranform_close_btn, BorderLayout.WEST);
        JLabel transform_title = new JLabel("   Configuration");
        transform_title.setFont(new Font("Arial", Font.PLAIN, h5));
        header2.add(transform_title, BorderLayout.CENTER);
        transformation_panel.add(header2, BorderLayout.NORTH);

        transformation_content = new JPanel(new BorderLayout());

        JPanel choosetype_panel = new JPanel(new GridLayout(1, 2));
        JLabel select_type_label = new JLabel("Select type", SwingConstants.CENTER);
        select_type_label.setFont(new Font("Arial", Font.BOLD, h5));
        choosetype_panel.add(select_type_label);
        String[] transformationOptions = {"Translation", "Normal Rotation", "Rotation about Fixed Point", "Normal Scaling", "Scaling about Fixed Point", "Shear", "Reflections"};
		jcb_transformations = new JComboBox<String>(transformationOptions);
        jcb_transformations.addActionListener(new jcbTransformationHandler());
        choosetype_panel.add(jcb_transformations);
        transformation_content.add(choosetype_panel, BorderLayout.NORTH);

        tContainer = new JPanel(new BorderLayout());
        showTransformDetails(); //Show respective panels for each transformation
        transformation_content.add(tContainer, BorderLayout.CENTER);

        transformation_panel.add(transformation_content, BorderLayout.CENTER);

        apply_transform_btn = new JButton(new ImageIcon(new ImageIcon("icons/normal/wand.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        apply_transform_btn.addActionListener(new btnApplyTransHandler());
        apply_transform_btn.setToolTipText("Click to apply transformation");
        transformation_panel.add(apply_transform_btn, BorderLayout.SOUTH);

        // Styles
        tranform_close_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               tranform_close_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/close-hover.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               tranform_close_btn.setBackground(whitish_gray);
               tranform_close_btn.setIcon(new ImageIcon(new ImageIcon("icons/normal/close2.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
            }
        });
        transformation_panel.setBackground(whitish_gray);
        choosetype_panel.setBackground(whitish_gray);
        tranform_close_btn.setBackground(whitish_gray);
        tranform_close_btn.setBorderPainted(false);
        tranform_close_btn.setFocusPainted(false);
        transformation_content.setBackground(whitish_gray);
        header2.setBackground(whitish_gray);
        jcb_transformations.setBackground(whitish_gray);
        apply_transform_btn.setBackground(whitish_gray);
        apply_transform_btn.setBorderPainted(false);
        apply_transform_btn.setFocusPainted(false);
        apply_transform_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
               apply_transform_btn.setIcon(new ImageIcon(new ImageIcon("icons/hover/wand-hover.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
               apply_transform_btn.setBackground(whitish_gray);
               apply_transform_btn.setIcon(new ImageIcon(new ImageIcon("icons/normal/wand.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
            }
        });

        content_panel.add(transformation_panel, BorderLayout.EAST);
    }

    public class transformClose implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            transformation_panel.setVisible(false);
		}
    } 

    public class btnColorPickerHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JColorChooser colorChooser = new JColorChooser();
			MainWindow2.this.selectedColor = JColorChooser.showDialog(null, "Choose fill color", selectedColor);
		}
	}

    public class btnFillHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            drawing_panel.removeAll();
            drawing_panel.add(new ScanLine(ds.getXPoints(), ds.getYPoints(), selectedColor));
            drawing_panel.repaint();
            drawing_panel.revalidate();
        }
    }

    public class btnClippingHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == clip_btn) {
				drawing_panel.removeAll();
				cw = new drawClippingWindow(ds.getXPoints(), ds.getYPoints(), ds.getShape());
				drawing_panel.add(cw);
			}
			if (e.getSource() == apply_clip_btn) {
				if (cw.getxMin()==-1 || cw.getyMin()==-1 || cw.getxMax()==-1 || cw.getyMax()==-1) {
					JOptionPane.showMessageDialog(MainWindow2.this, "No clipping window found, draw one first!");
				}
				else {
					drawing_panel.removeAll();
					drawing_panel.add(new LineClippingPanel(cw.getxMin(), cw.getyMin(), cw.getxMax(), cw.getyMax(), ds.getXLines(),ds.getYLines()));
					cw.initXY();
				}
			}
			drawing_panel.revalidate();
			drawing_panel.repaint();
		}
	}

    public class drawingToolHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			drawing_panel.removeAll();
			if (e.getSource()==square_btn) {
				ds = new drawShape("rectangle");
			}
			else if (e.getSource()==circle_btn) {
				ds = new drawShape("circle");
			}
			else if (e.getSource()==line_btn) {
				ds = new drawShape("line");
			}
			else if (e.getSource()==polygon_btn) {
				JOptionPane.showMessageDialog(MainWindow2.this, "Left-click to place vertices.\nWhen you are done, Right-click to stop and complete the polygon.");
				ds = new drawShape("polygon");
			}
			drawing_panel.add(ds);
			repaint();
			revalidate();
		}
	}

    public class jcbTransformationHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			tContainer.removeAll();
			JComboBox jcb_transformation = (JComboBox) e.getSource();
			switch (jcb_transformation.getSelectedIndex()) {
				case 0:
				{
					tContainer.add(translation_panel);
					circle_btn.setEnabled(true);
					break;
				}
				case 1:
				{
					tContainer.add(nRotation_panel);
					circle_btn.setEnabled(true);
					break;
				}
				case 2:
				{
					tContainer.add(fpRotation_panel);
					circle_btn.setEnabled(true);
					break;
				}
				case 3:
				{
					tContainer.add(nScaling_panel);
					circle_btn.setEnabled(false);
					if (ds.getShape()=="circle") {
						drawing_panel.removeAll();
						drawing_panel.add(new drawShape("rectangle"));
					}
					break;
				}
				case 4:
				{
					tContainer.add(fpScaling_panel);
					circle_btn.setEnabled(false);
					if (ds.getShape()=="circle") {
						drawing_panel.add(new drawShape("rectangle"));
					}
					break;
				}
				case 5:
				{
					tContainer.add(shear_panel);
					circle_btn.setEnabled(false);
					if (ds.getShape()=="circle") {
						drawing_panel.removeAll();
						drawing_panel.add(new drawShape("rectangle"));
					}
					break;
				}
				case 6:
				{
					tContainer.add(reflection_panel);
					circle_btn.setEnabled(true);
					break;
				}
			}
			revalidate();
			repaint();
		}
	}

    public void showTransformDetails(){
        //Translation
        translation_panel = new JPanel(new GridLayout(10, 1));
        translation_panel.setBackground(whitish_gray);

        tf_tx = new JTextField(6);
        tf_ty = new JTextField(6);
        JPanel ttx = new JPanel(new FlowLayout());
        ttx.setBackground(whitish_gray);
        JLabel lbl_tx = new JLabel(" Tx :");
        lbl_tx.setFont(new Font("Arial", Font.BOLD, h3));
        JPanel tty = new JPanel(new FlowLayout());
        tty.setBackground(whitish_gray);
        JLabel lbl_ty = new JLabel(" Ty :");
        lbl_ty.setFont(new Font("Arial", Font.BOLD, h3));

        translation_panel.add(new JLabel(""));
        translation_panel.add(new JLabel(""));
        translation_panel.add(new JLabel(""));
        translation_panel.add(new JLabel(""));
        ttx.add(lbl_tx);
        ttx.add(tf_tx);
        translation_panel.add(ttx);
        tty.add(lbl_ty);
        tty.add(tf_ty);
        translation_panel.add(tty);
        tContainer.add(translation_panel, BorderLayout.CENTER);

		//Normal Rotation
        nRotation_panel = new JPanel(new GridLayout(10, 1));
        nRotation_panel.setBackground(whitish_gray);

        JPanel nr = new JPanel(new FlowLayout());
        nr.setBackground(whitish_gray);
        JLabel lbl_normalRotationDeg = new JLabel(" Rotation(Degrees) :");
        lbl_normalRotationDeg.setFont(new Font("Arial", Font.BOLD, h3));
        tf_normalRotationDeg = new JTextField(6);

        nRotation_panel.add(new JLabel(""));
        nRotation_panel.add(new JLabel(""));
        nRotation_panel.add(new JLabel(""));
        nRotation_panel.add(new JLabel(""));
        nr.add(lbl_normalRotationDeg);
        nr.add(tf_normalRotationDeg);
        nRotation_panel.add(nr);

		
		//Rotation about a Fixed Point
        fpRotation_panel = new JPanel(new GridLayout(10, 1));
        fpRotation_panel.setBackground(whitish_gray);

        JPanel fpr = new JPanel(new FlowLayout());
        fpr.setBackground(whitish_gray);
        JPanel xr = new JPanel(new FlowLayout());
        xr.setBackground(whitish_gray);
        JPanel yr = new JPanel(new FlowLayout());
        yr.setBackground(whitish_gray);

        JLabel lbl_fpRotationDeg = new JLabel(" Rotation(Degrees) :");
        lbl_fpRotationDeg.setFont(new Font("Arial", Font.BOLD, h3));
        tf_fpRotationDeg = new JTextField(6);
        JLabel lbl_fpRotationPtSelection = new JLabel("Fixed Point:", SwingConstants.CENTER);
        lbl_fpRotationPtSelection.setFont(new Font("Arial", Font.PLAIN, h3));
        JLabel lbl_fpRotationX = new JLabel("   X ");
        lbl_fpRotationX.setFont(new Font("Arial", Font.BOLD, h3));
        tf_fpRotationX = new JTextField(6);
        JLabel lbl_fpRotationY = new JLabel("   Y ");
        lbl_fpRotationY.setFont(new Font("Arial", Font.BOLD, h3));
		tf_fpRotationY = new JTextField(6);

        fpr.add(lbl_fpRotationDeg);
        fpr.add(tf_fpRotationDeg);
        xr.add(lbl_fpRotationX);
        xr.add(tf_fpRotationX);
        yr.add(lbl_fpRotationY);
        yr.add(tf_fpRotationY);

        fpRotation_panel.add(new JLabel(""));
        fpRotation_panel.add(new JLabel(""));
        fpRotation_panel.add(new JLabel(""));
        fpRotation_panel.add(new JLabel(""));
        fpRotation_panel.add(fpr);
        fpRotation_panel.add(lbl_fpRotationPtSelection);
        fpRotation_panel.add(xr);
        fpRotation_panel.add(yr);
		
		//Normal Scaling
        nScaling_panel = new JPanel(new GridLayout(10, 1));
        nScaling_panel.setBackground(whitish_gray);

        JPanel sx = new JPanel(new FlowLayout());
        sx.setBackground(whitish_gray);
        JPanel sy = new JPanel(new FlowLayout());
        sy.setBackground(whitish_gray);

        JLabel lbl_normalScalingSx = new JLabel("Sx :");
        lbl_normalScalingSx.setFont(new Font("Arial", Font.BOLD, h3));
        tf_normalScalingSx = new JTextField(6);
        JLabel lbl_normalScalingSy = new JLabel("Sy :");
        lbl_normalScalingSy.setFont(new Font("Arial", Font.BOLD, h3));
        tf_normalScalingSy = new JTextField(6);

        sx.add(lbl_normalScalingSx);
        sx.add(tf_normalScalingSx);
        sy.add(lbl_normalScalingSy);
        sy.add(tf_normalScalingSy);

        nScaling_panel.add(new JLabel(""));
        nScaling_panel.add(new JLabel(""));
        nScaling_panel.add(new JLabel(""));
        nScaling_panel.add(new JLabel(""));
        nScaling_panel.add(sx);
        nScaling_panel.add(sy);
		
		//Scaling about a Fixed Point
        fpScaling_panel = new JPanel(new GridLayout(10, 1));
        fpScaling_panel.setBackground(whitish_gray);

        JPanel fpsx = new JPanel(new FlowLayout());
        fpsx.setBackground(whitish_gray);
        JPanel fpsy = new JPanel(new FlowLayout());
        fpsy.setBackground(whitish_gray);
        JPanel fpsxp = new JPanel(new FlowLayout());
        fpsxp.setBackground(whitish_gray);
        JPanel fpsyp = new JPanel(new FlowLayout());
        fpsyp.setBackground(whitish_gray);

        JLabel lbl_fpScalingSx = new JLabel("  Sx :");
        lbl_fpScalingSx.setFont(new Font("Arial", Font.BOLD, h3));
        tf_fpScalingSx = new JTextField(6);
        JLabel lbl_fpScalingSy = new JLabel("  Sy :");
        lbl_fpScalingSy.setFont(new Font("Arial", Font.BOLD, h3));
        tf_fpScalingSy = new JTextField(6);
        JLabel lbl_fpScalingPtSelection = new JLabel("Fixed Point:", SwingConstants.CENTER);
        lbl_fpScalingPtSelection.setFont(new Font("Arial", Font.PLAIN, h3));
		JLabel lbl_fpScalingX = new JLabel("   X ");
        lbl_fpScalingX.setFont(new Font("Arial", Font.BOLD, h3));
		tf_fpScalingX = new JTextField(6);
        JLabel lbl_fpScalingY = new JLabel("   Y ");
        lbl_fpScalingY.setFont(new Font("Arial", Font.BOLD, h3));
		tf_fpScalingY = new JTextField(6);

        fpsx.add(lbl_fpScalingSx);
        fpsx.add(tf_fpScalingSx);
        fpsy.add(lbl_fpScalingSy);
        fpsy.add(tf_fpScalingSy);
        fpsxp.add(lbl_fpScalingX);
        fpsxp.add(tf_fpScalingX);
        fpsyp.add(lbl_fpScalingY);
        fpsyp.add(tf_fpScalingY);

		fpScaling_panel.add(new JLabel(""));
        fpScaling_panel.add(new JLabel(""));
        fpScaling_panel.add(new JLabel(""));
        fpScaling_panel.add(new JLabel(""));
        fpScaling_panel.add(fpsx);
        fpScaling_panel.add(fpsy);
        fpScaling_panel.add(lbl_fpScalingPtSelection);
        fpScaling_panel.add(fpsxp);
        fpScaling_panel.add(fpsyp);
		
		//Shearing
        shear_panel = new JPanel(new GridLayout(10, 1));
        shear_panel.setBackground(whitish_gray);

        JPanel s1 = new JPanel(new FlowLayout());
        s1.setBackground(whitish_gray);
        JPanel s2 = new JPanel(new FlowLayout());
        s2.setBackground(whitish_gray);

        JLabel lbl_shearOptions = new JLabel("Shear Option :");
        lbl_shearOptions.setFont(new Font("Arial", Font.BOLD, h3));
		String[] shearOptions = {"Along X-axis", "Along Y-axis"};
		jcb_shearOptions = new JComboBox<String>(shearOptions);
        JLabel lbl_shearFactor = new JLabel("Shear Factor :");
        lbl_shearFactor.setFont(new Font("Arial", Font.BOLD, h3));
		tf_shearFactor = new JTextField(5);

        s1.add(lbl_shearOptions);
        s1.add(jcb_shearOptions);
        s2.add(lbl_shearFactor);
        s2.add(tf_shearFactor);

        shear_panel.add(new JLabel(""));
        shear_panel.add(new JLabel(""));
        shear_panel.add(new JLabel(""));
        shear_panel.add(new JLabel(""));
        shear_panel.add(s2);
        shear_panel.add(s1);

		//Reflections
        reflection_panel = new JPanel(new GridLayout(10, 1));
        reflection_panel.setBackground(whitish_gray);

        JPanel rf = new JPanel(new FlowLayout());
        rf.setBackground(whitish_gray);

        JLabel lbl_reflectionOptions = new JLabel("Reflection Option :");
        lbl_reflectionOptions.setFont(new Font("Arial", Font.BOLD, h3));
        String[] reflectionOptions = {"Along X-axis", "Along Y-axis", "Along line Y=X", "Along line Y=-X", "Along the Origin"};
        jcb_reflectionOptions = new JComboBox<String>(reflectionOptions);

        rf.add(lbl_reflectionOptions);
        rf.add(jcb_reflectionOptions);

        reflection_panel.add(new JLabel(""));
        reflection_panel.add(new JLabel(""));
        reflection_panel.add(new JLabel(""));
        reflection_panel.add(new JLabel(""));
        reflection_panel.add(rf);
    }

    public class btnApplyTransHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			drawing_panel.removeAll();
			switch (jcb_transformations.getSelectedIndex()) {
			case 0:
			{
				drawing_panel.add(new Translation(ds.getXPoints(), ds.getYPoints(), Integer.parseInt(tf_tx.getText()), Integer.parseInt(tf_ty.getText()), ds.getShape()));
				break;
			}
			case 1:
			{
				drawing_panel.add(new Rotation(ds.getXPoints(), ds.getYPoints(), drawing_panel.getWidth()/2, drawing_panel.getHeight()/2, Integer.parseInt(tf_normalRotationDeg.getText()), ds.getShape()));
				break;
			}
			case 2:
			{
				drawing_panel.add(new Rotation(ds.getXPoints(), ds.getYPoints(), drawing_panel.getWidth()/2 + Integer.parseInt(tf_fpRotationX.getText()), drawing_panel.getHeight()/2 - Integer.parseInt(tf_fpRotationY.getText()), Integer.parseInt(tf_fpRotationDeg.getText()), ds.getShape()));
				break;
			}
			case 3:
			{
				drawing_panel.add(new Scaling(ds.getXPoints(), ds.getYPoints(), drawing_panel.getWidth()/2, drawing_panel.getHeight()/2, Double.parseDouble(tf_normalScalingSx.getText()), Double.parseDouble(tf_normalScalingSy.getText()), ds.getShape()));
				break;
			}
			case 4:
			{
				drawing_panel.add(new Scaling(ds.getXPoints(), ds.getYPoints(), drawing_panel.getWidth()/2 + Integer.parseInt(tf_fpScalingX.getText()), drawing_panel.getHeight()/2 - Integer.parseInt(tf_fpScalingY.getText()), Double.parseDouble(tf_fpScalingSx.getText()), Double.parseDouble(tf_fpScalingSy.getText()), ds.getShape()));
				break;
			}
			case 5:
            {
				if (jcb_shearOptions.getSelectedIndex()==0) {
					drawing_panel.add(new Shear(ds.getXPoints(), ds.getYPoints(), -(Double.parseDouble(tf_shearFactor.getText())), 0, 0, drawing_panel.getHeight()/2, ds.getShape()));
				}
				else if (jcb_shearOptions.getSelectedIndex()==1) {
					drawing_panel.add(new Shear(ds.getXPoints(), ds.getYPoints(), 0, -(Double.parseDouble(tf_shearFactor.getText())), drawing_panel.getWidth()/2, 0, ds.getShape()));
				}
				break;
			}
			case 6:
			{
				drawing_panel.add(new Reflection(ds.getXPoints(), ds.getYPoints(), jcb_reflectionOptions.getSelectedIndex(), drawing_panel.getWidth()/2, drawing_panel.getHeight()/2, ds.getShape()));
				break;
			}
		}
		revalidate();
		repaint();
	    }
    }
		
    public void HoverEffectsMenu(){}

    public void HoverEffectsTransform(){}

    public static void main(String[] args) {
        new MainWindow2();
    }

}
