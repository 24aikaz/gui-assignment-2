
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MainWindow extends JFrame {
	
	JButton btnClose;
	JButton btnRectangle, btnCircle, btnLine, btnPolygon;
	JButton btnTransform, btnFill, btnClip;
	JButton btnSelectColor, btnApplyFill;
	JButton btnClippingWindow, btnApplyClip;
	JButton btnApplyTransformation;
	JButton btnminimize, btnrestore;
	
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
	
	JPanel jp_optionsOptions, jp_transformation, jp_fill, jp_clip;
	JPanel jp_transformationContainer, jp_translation, jp_normalRotation, jp_rotationFixedPoint, jp_normalScaling, jp_fpScaling, jp_shear, jp_reflection;
	JPanel drawingPanel;
	
	drawShape ds;
	drawClippingWindow cw;
	Color selectedColor;
	
	public void createGUI() {
		
		Font fb = null;
		Font fm = null;
		try {
			fb = Font.createFont(Font.TRUETYPE_FONT, new File("font/Montserrat/Montserrat-Bold.ttf"));
			fm = Font.createFont(Font.TRUETYPE_FONT, new File("font/Montserrat/Montserrat-Medium.ttf"));

			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(fb);
			ge.registerFont(fm);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		JPanel jp_main = new JPanel(new BorderLayout());
		
		//Top and Bottom Borders---------------------------------------------------------------------------
		btnClose = new JButton(new ImageIcon("icons/close.png"));
		btnrestore = new JButton (new ImageIcon(new ImageIcon ("icons/restore.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
		btnminimize = new JButton(new ImageIcon(new ImageIcon("icons/window-minimize-solid.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
		JButton b1 = new JButton(new ImageIcon(new ImageIcon("icons/eye-regular.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
		JButton b = new JButton(new ImageIcon(new ImageIcon("icons/eye-slash-regular.png").getImage().getScaledInstance(40, 30, Image.SCALE_DEFAULT)));
		JPanel jp_top = new JPanel();
		jp_top.setLayout(null);
		
		
		btnClose.setBounds(1275,0,100,60);
		 btnminimize.setBounds(1175,3,95,55);
		 b.setBounds(100,0,180,50);
		b1.setBounds(100,0,180,50);
		jp_top.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 60));
		jp_top.setBackground(Color.decode("#400000"));
		
		
		//Adding Minimize button
		
		btnminimize.setBackground(Color.blue);
		btnminimize.setPreferredSize(new Dimension(60,60));
		btnminimize.setFocusable(false);
		btnminimize.setBorderPainted(false);
		btnminimize.addActionListener(new btnCloseHandler());
		jp_top.add(btnminimize);
		
		//Adding restore down button
		btnrestore.setBounds(1075, 3, 95, 55);
		btnrestore.setBackground(Color.blue); // Customize the color
		btnrestore.setFocusable(false);
		btnrestore.setBorderPainted(false);
		btnrestore.addActionListener(new btnCloseHandler());
		jp_top.add(btnrestore);
		
		//Adding a close button
		
		btnClose.setBackground(Color.red);
		btnClose.setPreferredSize(new Dimension(60, 60));
		btnClose.setFocusable(false);
		btnClose.setBorderPainted(false);
		btnClose.addActionListener(new btnCloseHandler());
		jp_top.add(btnClose);
		
		JPanel jp_bottom = new JPanel();
		jp_bottom.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 60));
		jp_bottom.setBackground(Color.decode("#400000"));
		jp_main.add(jp_bottom);
		
		jp_main.add(jp_top, BorderLayout.NORTH);
		jp_main.add(jp_bottom, BorderLayout.SOUTH);
		//End------------------------------------------------------------------------------------------------

		//Control Panel--------------------------------------------------------------------------------------
		JPanel jp_controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 12));
		jp_controls.setPreferredSize(new Dimension(700, Toolkit.getDefaultToolkit().getScreenSize().height-120));
		jp_controls.setBackground(Color.decode("#c58080"));
		
		//Drawing Tools------------------------------
		JPanel jp_drawingTools = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
		jp_drawingTools.setPreferredSize(new Dimension(600, 230));
		jp_drawingTools.setOpaque(false);
		JLabel lbl_drawingTools = new JLabel("  Drawing Tools");
		lbl_drawingTools.setFont(fb.deriveFont(30f));
		JPanel jp_drawingToolsOptions = new JPanel(new GridLayout(2, 2, 100, 30));
		jp_drawingToolsOptions.setPreferredSize(new Dimension(600, 180));
		jp_drawingToolsOptions.setOpaque(false);
		jp_drawingToolsOptions.setBorder(new RoundedBorder(25, Color.WHITE));
		
		//Rectangle
		JPanel jp_rectangle = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
		jp_rectangle.setOpaque(false);
		btnRectangle = new JButton(new ImageIcon(this.getClass().getResource("icons/stop.png")));
		btnRectangle.setBackground(Color.WHITE);
		btnRectangle.setPreferredSize(new Dimension(45, 45));
		btnRectangle.setFocusable(false);
		btnRectangle.setBorder(new RoundedBorder(10, Color.BLACK, false));
		btnRectangle.addActionListener(new drawingToolHandler());
		JLabel lbl_rectangle = new JLabel("Rectangle");
		lbl_rectangle.setFont(fm.deriveFont(18f));
		jp_rectangle.add(btnRectangle);
		jp_rectangle.add(lbl_rectangle);
		jp_drawingToolsOptions.add(jp_rectangle);
		
		//Circle
		JPanel jp_circle = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
		jp_circle.setOpaque(false);
		btnCircle = new JButton(new ImageIcon(this.getClass().getResource("icons/dry-clean.png")));
		btnCircle.setBackground(Color.WHITE);
		btnCircle.setPreferredSize(new Dimension(45, 45));
		btnCircle.setFocusable(false);
		btnCircle.setBorder(new RoundedBorder(10, Color.BLACK, false));
		btnCircle.addActionListener(new drawingToolHandler());
		JLabel lbl_circle = new JLabel("Circle");
		lbl_circle.setFont(fm.deriveFont(18f));
		jp_circle.add(btnCircle);
		jp_circle.add(lbl_circle);
		jp_drawingToolsOptions.add(jp_circle);
		
		//Line
		JPanel jp_line = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
		jp_line.setOpaque(false);
		btnLine = new JButton(new ImageIcon(this.getClass().getResource("icons/diagonal-line.png")));
		btnLine.setBackground(Color.WHITE);
		btnLine.setPreferredSize(new Dimension(45, 45));
		btnLine.setFocusable(false);
		btnLine.setBorder(new RoundedBorder(10, Color.BLACK, false));
		btnLine.addActionListener(new drawingToolHandler());
		JLabel lbl_line = new JLabel("Line");
		lbl_line.setFont(fm.deriveFont(18f));
		jp_line.add(btnLine);
		jp_line.add(lbl_line);
		jp_drawingToolsOptions.add(jp_line);
		
		//Erase
		JPanel jp_polygon = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
		jp_polygon.setOpaque(false);
		btnPolygon = new JButton(new ImageIcon(this.getClass().getResource("icons/hexagon.png")));
		btnPolygon.setBackground(Color.WHITE);
		btnPolygon.setPreferredSize(new Dimension(45, 45));
		btnPolygon.setFocusable(false);
		btnPolygon.setBorder(new RoundedBorder(10, Color.BLACK, false));
		btnPolygon.addActionListener(new drawingToolHandler());
		JLabel lbl_polygon = new JLabel("Polygon");
		lbl_polygon.setFont(fm.deriveFont(18f));
		jp_polygon.add(btnPolygon);
		jp_polygon.add(lbl_polygon);
		jp_drawingToolsOptions.add(jp_polygon);

		jp_drawingTools.add(lbl_drawingTools);
		jp_drawingTools.add(jp_drawingToolsOptions);
		jp_controls.add(jp_drawingTools);
		//Drawing Tools End------------------------------
		
		//Options------------------------------
		JPanel jp_options = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
		jp_options.setPreferredSize(new Dimension(600, 470));
		jp_options.setOpaque(false);
		JLabel lbl_options = new JLabel("  Options");
		lbl_options.setFont(fb.deriveFont(30f));
		jp_optionsOptions = new JPanel(new FlowLayout());
		jp_optionsOptions.setPreferredSize(new Dimension(600, 420));
		jp_optionsOptions.setOpaque(false);
		jp_optionsOptions.setBorder(new RoundedBorder(25, Color.WHITE));
		
		//Button Nav
		JPanel jp_nav = new JPanel(new GridLayout(1, 3));
		jp_nav.setPreferredSize(new Dimension(570, 55));
		jp_nav.setOpaque(false);
		btnTransform = new JButton("Transform");
		btnTransform.setFont(fm.deriveFont(20f));
		btnTransform.setBackground(Color.decode("#400000"));
		btnTransform.setForeground(Color.WHITE);
		btnTransform.setFocusable(false);
		btnTransform.setBorderPainted(false);
		btnTransform.addActionListener(new btnOptionsHandler());
		btnTransform.addMouseListener(new btnOptionsHoverHandler());
		btnFill = new JButton("Fill");
		btnFill.setFont(fm.deriveFont(20f));
		btnFill.setBackground(Color.decode("#c58080"));
		btnFill.setForeground(Color.WHITE);
		btnFill.setFocusable(false);
		btnFill.setBorderPainted(false);
		btnFill.addActionListener(new btnOptionsHandler());
		btnFill.addMouseListener(new btnOptionsHoverHandler());
		btnClip = new JButton("Clip");
		btnClip.setFont(fm.deriveFont(20f));
		btnClip.setBackground(Color.decode("#c58080"));
		btnClip.setForeground(Color.WHITE);
		btnClip.setFocusable(false);
		btnClip.setBorderPainted(false);
		btnClip.addActionListener(new btnOptionsHandler());
		btnClip.addMouseListener(new btnOptionsHoverHandler());
		jp_nav.add(btnTransform);
		jp_nav.add(btnFill);
		jp_nav.add(btnClip);
		
		jp_optionsOptions.add(jp_nav);
		jp_optionsOptions.setOpaque(false);
		
		//Transformation Panel
		jp_transformation = new JPanel(new FlowLayout());
		jp_transformation.setOpaque(false);
		jp_transformation.setPreferredSize(new Dimension(570, 320));
		JPanel jp_transformationSelection = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 3));
		jp_transformationSelection.setOpaque(false);
		jp_transformationSelection.setPreferredSize(new Dimension(435, 30));
		JLabel lbl_transformation = new JLabel("Transformation :");
		lbl_transformation.setPreferredSize(new Dimension(200, 20));
		lbl_transformation.setFont(fm.deriveFont(18f));
		String[] transformationOptions = {"Translation", "Normal Rotation", "Rotation about a Fixed Point", "Normal Scaling", "Scaling about a Fixed Point", "Shear", "Reflections"};
		jcb_transformations = new JComboBox<String>(transformationOptions);
		jcb_transformations.setFont(fm.deriveFont(14f));
		jcb_transformations.setBackground(Color.WHITE);
		jcb_transformations.setFocusable(false);
		jcb_transformations.addActionListener(new jcbTransformationHandler());
		jp_transformationContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		jp_transformationContainer.setPreferredSize(new Dimension(435, 150));
		jp_transformationContainer.setOpaque(false);
		jp_transformationContainer.setBorder(new EmptyBorder(30, 0, 0, 0));
		
		//Translation
		jp_translation = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
		jp_translation.setPreferredSize(new Dimension(435, 220));
		jp_translation.setOpaque(false);
		JPanel jp_translationTx = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jp_translationTx.setPreferredSize(new Dimension(435, 30));
		jp_translationTx.setOpaque(false);
		JLabel lbl_tx = new JLabel("Tx :");
		lbl_tx.setPreferredSize(new Dimension(200, 30));
		lbl_tx.setFont(fm.deriveFont(18f));
		tf_tx = new JTextField(5);
		tf_tx.setFont(fm.deriveFont(16f));
		tf_tx.setHorizontalAlignment(SwingConstants.CENTER);
		jp_translationTx.add(lbl_tx);
		jp_translationTx.add(tf_tx);
		JPanel jp_translationTy = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jp_translationTy.setOpaque(false);
		jp_translationTy.setPreferredSize(new Dimension(435, 30));
		JLabel lbl_ty = new JLabel("Ty :");
		lbl_ty.setPreferredSize(new Dimension(200, 30));
		lbl_ty.setFont(fm.deriveFont(18f));
		tf_ty = new JTextField(5);
		tf_ty.setFont(fm.deriveFont(16f));
		tf_ty.setHorizontalAlignment(SwingConstants.CENTER);
		jp_translationTy.add(lbl_ty);
		jp_translationTy.add(tf_ty);
		jp_translation.add(jp_translationTx);
		jp_translation.add(jp_translationTy);
		jp_transformationContainer.add(jp_translation);
		
		//Normal Rotation
		jp_normalRotation = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
		jp_normalRotation.setPreferredSize(new Dimension(435, 220));
		jp_normalRotation.setOpaque(false);
		JPanel jp_normalRotationInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jp_normalRotationInput.setPreferredSize(new Dimension(435, 30));
		jp_normalRotationInput.setOpaque(false);
		JLabel lbl_normalRotationDeg = new JLabel("Rotation(Degrees) :");
		lbl_normalRotationDeg.setPreferredSize(new Dimension(200, 30));
		lbl_normalRotationDeg.setFont(fm.deriveFont(18f));
		tf_normalRotationDeg = new JTextField(5);
		tf_normalRotationDeg.setFont(fm.deriveFont(16f));
		tf_normalRotationDeg.setHorizontalAlignment(SwingConstants.CENTER);
		jp_normalRotationInput.add(lbl_normalRotationDeg);
		jp_normalRotationInput.add(tf_normalRotationDeg);
		jp_normalRotation.add(jp_normalRotationInput);
		//jp_transformationContainer.add(jp_normalRotation);
		
		//Rotation about a Fixed Point
		jp_rotationFixedPoint = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
		jp_rotationFixedPoint.setPreferredSize(new Dimension(435, 220));
		jp_rotationFixedPoint.setOpaque(false);
		JPanel jp_fpRotationDeg = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jp_fpRotationDeg.setPreferredSize(new Dimension(435, 30));
		jp_fpRotationDeg.setOpaque(false);
		JLabel lbl_fpRotationDeg = new JLabel("Rotation(Degrees) :");
		lbl_fpRotationDeg.setPreferredSize(new Dimension(200, 30));
		lbl_fpRotationDeg.setFont(fm.deriveFont(18f));
		tf_fpRotationDeg = new JTextField(5);
		tf_fpRotationDeg.setFont(fm.deriveFont(16f));
		tf_fpRotationDeg.setHorizontalAlignment(SwingConstants.CENTER);
		jp_fpRotationDeg.add(lbl_fpRotationDeg);
		jp_fpRotationDeg.add(tf_fpRotationDeg);
		JPanel jp_fpRotationPtSelection = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jp_fpRotationPtSelection.setPreferredSize(new Dimension(435, 60));
		jp_fpRotationPtSelection.setOpaque(false);
		JLabel lbl_fpRotationPtSelection = new JLabel("Fixed Point :");
		lbl_fpRotationPtSelection.setPreferredSize(new Dimension(200, 30));
		lbl_fpRotationPtSelection.setFont(fm.deriveFont(18f));
		JLabel lbl_fpRotationX = new JLabel("X ");
		lbl_fpRotationX.setFont(fm.deriveFont(18f));
		tf_fpRotationX = new JTextField(4);
		tf_fpRotationX.setFont(fm.deriveFont(16f));
		tf_fpRotationX.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lbl_fpRotationY = new JLabel("   Y ");
		lbl_fpRotationY.setFont(fm.deriveFont(18f));
		tf_fpRotationY = new JTextField(4);
		tf_fpRotationY.setFont(fm.deriveFont(16f));
		tf_fpRotationY.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel jp_selectPointRotation = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		jp_selectPointRotation.setOpaque(false);
		jp_selectPointRotation.add(lbl_fpRotationX);
		jp_selectPointRotation.add(tf_fpRotationX);
		jp_selectPointRotation.add(lbl_fpRotationY);
		jp_selectPointRotation.add(tf_fpRotationY);
		JPanel jp_gridSelectPointRotation = new JPanel(new GridLayout(1, 1));
		jp_gridSelectPointRotation.setOpaque(false);
		jp_gridSelectPointRotation.add(jp_selectPointRotation);
		jp_fpRotationPtSelection.add(lbl_fpRotationPtSelection);
		jp_fpRotationPtSelection.add(jp_gridSelectPointRotation);
		jp_rotationFixedPoint.add(jp_fpRotationDeg);
		jp_rotationFixedPoint.add(jp_fpRotationPtSelection);
		//jp_transformationContainer.add(jp_rotationFixedPoint);
		
		//Normal Scaling
		jp_normalScaling = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
		jp_normalScaling.setPreferredSize(new Dimension(435, 220));
		jp_normalScaling.setOpaque(false);
		JPanel jp_normalScalingSx = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jp_normalScalingSx.setPreferredSize(new Dimension(435, 30));
		jp_normalScalingSx.setOpaque(false);
		JLabel lbl_normalScalingSx = new JLabel("Sx :");
		lbl_normalScalingSx.setPreferredSize(new Dimension(200, 30));
		lbl_normalScalingSx.setFont(fm.deriveFont(18f));
		tf_normalScalingSx = new JTextField(5);
		tf_normalScalingSx.setFont(fm.deriveFont(16f));
		tf_normalScalingSx.setHorizontalAlignment(SwingConstants.CENTER);
		jp_normalScalingSx.add(lbl_normalScalingSx);
		jp_normalScalingSx.add(tf_normalScalingSx);
		JPanel jp_normalScalingSy = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jp_normalScalingSy.setPreferredSize(new Dimension(435, 30));
		jp_normalScalingSy.setOpaque(false);
		JLabel lbl_normalScalingSy = new JLabel("Sy :");
		lbl_normalScalingSy.setPreferredSize(new Dimension(200, 30));
		lbl_normalScalingSy.setFont(fm.deriveFont(18f));
		tf_normalScalingSy = new JTextField(5);
		tf_normalScalingSy.setFont(fm.deriveFont(16f));
		tf_normalScalingSy.setHorizontalAlignment(SwingConstants.CENTER);
		jp_normalScalingSy.add(lbl_normalScalingSy);
		jp_normalScalingSy.add(tf_normalScalingSy);
		jp_normalScaling.add(jp_normalScalingSx);
		jp_normalScaling.add(jp_normalScalingSy);
		//jp_transformationContainer.add(jp_normalScaling);
		
		//Scaling about a Fixed Point
		jp_fpScaling = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
		jp_fpScaling.setPreferredSize(new Dimension(435, 220));
		jp_fpScaling.setOpaque(false);
		JPanel jp_fpScalingSx = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jp_fpScalingSx.setPreferredSize(new Dimension(435, 30));
		jp_fpScalingSx.setOpaque(false);
		JLabel lbl_fpScalingSx = new JLabel("Sx :");
		lbl_fpScalingSx.setPreferredSize(new Dimension(200, 30));
		lbl_fpScalingSx.setFont(fm.deriveFont(18f));
		tf_fpScalingSx = new JTextField(5);
		tf_fpScalingSx.setFont(fm.deriveFont(16f));
		tf_fpScalingSx.setHorizontalAlignment(SwingConstants.CENTER);
		jp_fpScalingSx.add(lbl_fpScalingSx);
		jp_fpScalingSx.add(tf_fpScalingSx);
		JPanel jp_fpScalingSy = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jp_fpScalingSy.setPreferredSize(new Dimension(435, 30));
		jp_fpScalingSy.setOpaque(false);
		JLabel lbl_fpScalingSy = new JLabel("Sy :");
		lbl_fpScalingSy.setPreferredSize(new Dimension(200, 30));
		lbl_fpScalingSy.setFont(fm.deriveFont(18f));
		tf_fpScalingSy = new JTextField(5);
		tf_fpScalingSy.setFont(fm.deriveFont(16f));
		tf_fpScalingSy.setHorizontalAlignment(SwingConstants.CENTER);
		jp_fpScalingSy.add(lbl_fpScalingSy);
		jp_fpScalingSy.add(tf_fpScalingSy);
		jp_fpScaling.add(jp_fpScalingSx);
		jp_fpScaling.add(jp_fpScalingSy);
		JPanel jp_fpScalingPtSelection = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jp_fpScalingPtSelection.setPreferredSize(new Dimension(435, 60));
		jp_fpScalingPtSelection.setOpaque(false);
		JLabel lbl_fpScalingPtSelection = new JLabel("Fixed Point :");
		lbl_fpScalingPtSelection.setPreferredSize(new Dimension(200, 30));
		lbl_fpScalingPtSelection.setFont(fm.deriveFont(18f));
		JLabel lbl_fpScalingX = new JLabel("X ");
		lbl_fpScalingX.setFont(fm.deriveFont(18f));
		tf_fpScalingX = new JTextField(4);
		tf_fpScalingX.setFont(fm.deriveFont(16f));
		tf_fpScalingX.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lbl_fpScalingY = new JLabel("   Y ");
		lbl_fpScalingY.setFont(fm.deriveFont(18f));
		tf_fpScalingY = new JTextField(4);
		tf_fpScalingY.setFont(fm.deriveFont(16f));
		tf_fpScalingY.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel jp_selectPointScaling = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		jp_selectPointScaling.setOpaque(false);
		jp_selectPointScaling.add(lbl_fpScalingX);
		jp_selectPointScaling.add(tf_fpScalingX);
		jp_selectPointScaling.add(lbl_fpScalingY);
		jp_selectPointScaling.add(tf_fpScalingY);
		JPanel jp_gridSelectPointScaling = new JPanel(new GridLayout(1, 1));
		jp_gridSelectPointScaling.setOpaque(false);
		jp_gridSelectPointScaling.add(jp_selectPointScaling);
		jp_fpScalingPtSelection.add(lbl_fpScalingPtSelection);
		jp_fpScalingPtSelection.add(jp_gridSelectPointScaling);
		jp_fpScaling.add(jp_fpScalingPtSelection);
		//jp_transformationContainer.add(jp_fpScaling);
		
		//Shearing
		jp_shear = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
		jp_shear.setPreferredSize(new Dimension(435, 220));
		jp_shear.setOpaque(false);
		JPanel jp_shearOptions = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jp_shearOptions.setPreferredSize(new Dimension(435, 30));
		jp_shearOptions.setOpaque(false);
		JLabel lbl_shearOptions = new JLabel("Shear Option :");
		lbl_shearOptions.setPreferredSize(new Dimension(200, 30));
		lbl_shearOptions.setFont(fm.deriveFont(18f));
		String[] shearOptions = {"Along X-axis", "Along Y-axis"};
		jcb_shearOptions = new JComboBox<String>(shearOptions);
		jcb_shearOptions.setFont(fm.deriveFont(14f));
		jcb_shearOptions.setBackground(Color.WHITE);
		jcb_shearOptions.setFocusable(false);
		jp_shearOptions.add(lbl_shearOptions);
		jp_shearOptions.add(jcb_shearOptions);
		JPanel jp_shearFactor = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jp_shearFactor.setPreferredSize(new Dimension(435, 30));
		jp_shearFactor.setOpaque(false);
		JLabel lbl_shearFactor = new JLabel("Shear Factor :");
		lbl_shearFactor.setPreferredSize(new Dimension(200, 30));
		lbl_shearFactor.setFont(fm.deriveFont(18f));
		tf_shearFactor = new JTextField(5);
		tf_shearFactor.setFont(fm.deriveFont(16f));
		tf_shearFactor.setHorizontalAlignment(SwingConstants.CENTER);
		jp_shearFactor.add(lbl_shearFactor);
		jp_shearFactor.add(tf_shearFactor);
		jp_shear.add(jp_shearOptions);
		jp_shear.add(jp_shearFactor);
		//jp_transformationContainer.add(jp_shear);
		
		//Reflections
		jp_reflection = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
		jp_reflection.setPreferredSize(new Dimension(435, 220));
		jp_reflection.setOpaque(false);
		JPanel jp_reflectionOptions = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jp_reflectionOptions.setPreferredSize(new Dimension(435, 30));
		jp_reflectionOptions.setOpaque(false);
		JLabel lbl_reflectionOptions = new JLabel("Reflection Option :");
		lbl_reflectionOptions.setPreferredSize(new Dimension(200, 30));
		lbl_reflectionOptions.setFont(fm.deriveFont(18f));
		String[] reflectionOptions = {"Along X-axis", "Along Y-axis", "Along line Y=X", "Along line Y=-X", "Along the Origin"};
		jcb_reflectionOptions = new JComboBox<String>(reflectionOptions);
		jcb_reflectionOptions.setFont(fm.deriveFont(14f));
		jcb_reflectionOptions.setBackground(Color.WHITE);
		jcb_reflectionOptions.setFocusable(false);
		jp_reflectionOptions.add(lbl_reflectionOptions);
		jp_reflectionOptions.add(jcb_reflectionOptions);
		jp_reflection.add(jp_reflectionOptions);
		//jp_transformationContainer.add(jp_reflection);
		
		
		jp_transformationSelection.add(lbl_transformation);
		jp_transformationSelection.add(jcb_transformations);
		jp_transformation.add(jp_transformationSelection);
		jp_transformation.add(jp_transformationContainer);
		JPanel jp_btnApplyTransformation = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		jp_btnApplyTransformation.setPreferredSize(new Dimension(435, 55));
		jp_btnApplyTransformation.setOpaque(false);
		btnApplyTransformation = new JButton("Apply");
		btnApplyTransformation.setFont(fm.deriveFont(14f));
		btnApplyTransformation.setBackground(Color.WHITE);
		btnApplyTransformation.setBorder(new RoundedBorder(15, Color.BLACK, false));
		btnApplyTransformation.setFocusable(false);
		btnApplyTransformation.addActionListener(new btnApplyTransHandler());
		jp_btnApplyTransformation.add(btnApplyTransformation);
		jp_transformation.add(jp_btnApplyTransformation);
		
		jp_optionsOptions.add(jp_transformation);
		//End Transformation Panel
		
		//Fill Panel
		jp_fill = new JPanel(new GridBagLayout());
		jp_fill.setOpaque(false);
		jp_fill.setPreferredSize(new Dimension(570, 320));
		JPanel jp_gridFill = new JPanel(new GridLayout(2, 1, 0, 25));
		jp_gridFill.setOpaque(false);
		btnSelectColor = new JButton("Select Color");
		btnSelectColor.setFont(fm.deriveFont(14f));
		btnSelectColor.setBackground(Color.WHITE);
		btnSelectColor.setBorder(new RoundedBorder(15, Color.BLACK, false));
		btnSelectColor.setFocusable(false);
		btnSelectColor.addActionListener(new btnColorPickerHandler());
		btnApplyFill = new JButton("Fill");
		btnApplyFill.setFont(fm.deriveFont(14f));
		btnApplyFill.setBackground(Color.WHITE);
		btnApplyFill.setBorder(new RoundedBorder(15, Color.BLACK, false));
		btnApplyFill.setFocusable(false);
		btnApplyFill.addActionListener(new btnFillHandler());
		
		jp_gridFill.add(btnSelectColor);
		jp_gridFill.add(btnApplyFill);
		jp_fill.add(jp_gridFill);
		//jp_optionsOptions.add(jp_fill);
		
		//Clip Panel
		jp_clip = new JPanel(new GridBagLayout());
		jp_clip.setOpaque(false);
		jp_clip.setPreferredSize(new Dimension(570, 320));
		JPanel jp_gridClip = new JPanel(new GridLayout(2, 1, 0, 25));
		jp_gridClip.setOpaque(false);
		cw = new drawClippingWindow(null, null, null);
		btnClippingWindow = new JButton("Draw Clipping Window");
		btnClippingWindow.setFont(fm.deriveFont(14f));
		btnClippingWindow.setBackground(Color.WHITE);
		btnClippingWindow.setBorder(new RoundedBorder(15, Color.BLACK, false));
		btnClippingWindow.setFocusable(false);
		btnClippingWindow.addActionListener(new btnClippingHandler());
		btnApplyClip = new JButton("Clip");
		btnApplyClip.setFont(fm.deriveFont(14f));
		btnApplyClip.setBackground(Color.WHITE);
		btnApplyClip.setBorder(new RoundedBorder(15, Color.BLACK, false));
		btnApplyClip.setFocusable(false);
		btnApplyClip.addActionListener(new btnClippingHandler());
		
		jp_gridClip.add(btnClippingWindow);
		jp_gridClip.add(btnApplyClip);
		jp_clip.add(jp_gridClip);
		//jp_optionsOptions.add(jp_clip);
		
		jp_options.add(lbl_options);
		jp_options.add(jp_optionsOptions);
		jp_controls.add(jp_options);
		//Options End------------------------------
		
		jp_main.add(jp_controls, BorderLayout.WEST);
		//Control Panel End------------------------------------------------------------------------------------------------
		
		//Drawing Panel------------------------------------------------------------------------------------------------
		drawingPanel = new JPanel(new GridLayout(0, 1, 0, 0));
		ds = new drawShape("rectangle");
		drawingPanel.add(ds);
		jp_main.add(drawingPanel, BorderLayout.CENTER);
		//Drawing Panel End------------------------------------------------------------------------------------------------
		
	    /*JButton b = new JButton("HIDE");
		JButton b1 = new JButton("Show");*/
	    b.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        	jp_controls.setVisible(false);
	       	 jp_top.remove(b);
	       	jp_top.add(b1);
	        	drawingPanel.setVisible(false);
	        	drawingPanel.add(ds);
	        	repaint();
				revalidate();
	        	drawingPanel.setVisible(true);
	        }
	    });
	   
	    
	    b1.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        	drawingPanel.setVisible(false);	
	        	 jp_top.remove(b1);
	        		jp_top.add(b);
	        	jp_controls.setVisible(true);
	        	drawingPanel.add(ds);
	        	repaint();
				revalidate();
	        	drawingPanel.setVisible(true);
	        }
	    });

		

	  //  jp_fill.add(b);
	   // jp_controls.add(b);
	    jp_top.add(b);
	    //jp_top.add(b);
	   // jp_top.add(b1);
		add(jp_main);
		setTitle("Assignment Part 2");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setBackground(new Color(255,255,255));
		setUndecorated(true);
		//setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	
	public class btnCloseHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==btnClose) {
			  dispose();
			}
			if (e.getSource()==btnminimize) {
			setState(Frame.ICONIFIED);
			}
		    //setState(Frame.NORMAL);
		}
		
	}
	
	public class btnColorPickerHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JColorChooser colorChooser = new JColorChooser();
			MainWindow.this.selectedColor = JColorChooser.showDialog(null, "Choose fill color", selectedColor);
		}
		
	}

	public class btnOptionsHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==btnTransform) {
				btnTransform.setBackground(Color.decode("#400000"));
				btnFill.setBackground(Color.decode("#c58080"));
				btnClip.setBackground(Color.decode("#c58080"));
				jp_optionsOptions.remove(jp_clip);
				jp_optionsOptions.remove(jp_fill);
				jp_optionsOptions.add(jp_transformation);
				btnCircle.setEnabled(true);
				btnLine.setEnabled(true);
			}
			else if (e.getSource()==btnFill) {
				btnTransform.setBackground(Color.decode("#c58080"));
				btnFill.setBackground(Color.decode("#400000"));
				btnClip.setBackground(Color.decode("#c58080"));
				jp_optionsOptions.remove(jp_clip);
				jp_optionsOptions.remove(jp_transformation);
				jp_optionsOptions.add(jp_fill);
				btnCircle.setEnabled(false);
				btnLine.setEnabled(false);
				if (ds.getShape()=="circle" || ds.getShape()=="line") {
					drawingPanel.removeAll();
					drawingPanel.add(new drawShape("rectangle"));
				}
			}
			else if (e.getSource()==btnClip) {
				btnTransform.setBackground(Color.decode("#c58080"));
				btnFill.setBackground(Color.decode("#c58080"));
				btnClip.setBackground(Color.decode("#400000"));
				jp_optionsOptions.remove(jp_transformation);
				jp_optionsOptions.remove(jp_fill);
				jp_optionsOptions.add(jp_clip);
				btnCircle.setEnabled(false);
				btnLine.setEnabled(true);
				if (ds.getShape()=="circle") {
					drawingPanel.removeAll();
					drawingPanel.add(new drawShape("rectangle"));
				}
			}
			revalidate();
			repaint();
		}
		
	}
	
	public class btnOptionsHoverHandler implements MouseListener {

		public void mouseEntered(MouseEvent e) {
			JButton btnHover = (JButton) e.getSource();
			if (btnHover.getBackground().getRGB()!=Color.decode("#400000").getRGB()) {
				btnHover.setBackground(Color.decode("#0e0000"));
			}
		}

		public void mouseExited(MouseEvent e) {
			JButton btnHover = (JButton) e.getSource();
			if (btnHover.getBackground().getRGB()!=Color.decode("#400000").getRGB()) {
				btnHover.setBackground(Color.decode("#c58080"));
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			//System.out.print("Hi 1");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			/*JButton btnHover = (JButton) e.getSource();
			btnHover.setBackground(new Color(0,255,255));*/
			//System.out.print("Hi 2");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			/*JButton btnHover = (JButton) e.getSource();
			btnHover.setBackground(new Color(0,255,255));*/
			//System.out.print("Hi 3");
			
		}
		
	}
	
	public class drawingToolHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			drawingPanel.removeAll();
			if (e.getSource()==btnRectangle) {
				ds = new drawShape("rectangle");
			}
			else if (e.getSource()==btnCircle) {
				ds = new drawShape("circle");
			}
			else if (e.getSource()==btnLine) {
				ds = new drawShape("line");
			}
			else if (e.getSource()==btnPolygon) {
				JOptionPane.showMessageDialog(MainWindow.this, "Left-click to place vertices.\nWhen you are done, Right-click to stop and complete the polygon.");
				ds = new drawShape("polygon");
			}
			drawingPanel.add(ds);
			repaint();
			revalidate();
		}
		
	}
	
	public class jcbTransformationHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			jp_transformationContainer.removeAll();
			JComboBox jcb_transformation = (JComboBox) e.getSource();
			switch (jcb_transformation.getSelectedIndex()) {
				case 0:
				{
					jp_transformationContainer.add(jp_translation);
					btnCircle.setEnabled(true);
					break;
				}
				case 1:
				{
					jp_transformationContainer.add(jp_normalRotation);
					btnCircle.setEnabled(true);
					break;
				}
				case 2:
				{
					jp_transformationContainer.add(jp_rotationFixedPoint);
					btnCircle.setEnabled(true);
					break;
				}
				case 3:
				{
					jp_transformationContainer.add(jp_normalScaling);
					btnCircle.setEnabled(false);
					if (ds.getShape()=="circle") {
						drawingPanel.removeAll();
						drawingPanel.add(new drawShape("rectangle"));
					}
					break;
				}
				case 4:
				{
					jp_transformationContainer.add(jp_fpScaling);
					btnCircle.setEnabled(false);
					if (ds.getShape()=="circle") {
						drawingPanel.removeAll();
						drawingPanel.add(new drawShape("rectangle"));
					}
					break;
				}
				case 5:
				{
					jp_transformationContainer.add(jp_shear);
					btnCircle.setEnabled(false);
					if (ds.getShape()=="circle") {
						drawingPanel.removeAll();
						drawingPanel.add(new drawShape("rectangle"));
					}
					break;
				}
				case 6:
				{
					jp_transformationContainer.add(jp_reflection);
					btnCircle.setEnabled(true);
					break;
				}
			}
			revalidate();
			repaint();
		}
		
	}
	
	public class btnClippingHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnClippingWindow) {
				drawingPanel.removeAll();
				cw = new drawClippingWindow(ds.getXPoints(), ds.getYPoints(), ds.getShape());
				drawingPanel.add(cw);
			}
			if (e.getSource() == btnApplyClip) {
				if (cw.getxMin()==-1 || cw.getyMin()==-1 || cw.getxMax()==-1 || cw.getyMax()==-1) {
					JOptionPane.showMessageDialog(MainWindow.this, "Clipping Window should be drawn first.");
				}
				else {
					drawingPanel.removeAll();
					drawingPanel.add(new LineClippingPanel(cw.getxMin(), cw.getyMin(), cw.getxMax(), cw.getyMax(), ds.getXLines(),ds.getYLines()));
					cw.initXY();
				}
			}
			drawingPanel.revalidate();
			drawingPanel.repaint();
			
		}
		
		
	}
	
	public class btnApplyTransHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			drawingPanel.removeAll();
			switch (jcb_transformations.getSelectedIndex()) {
			case 0:
			{
				drawingPanel.add(new Translation(ds.getXPoints(), ds.getYPoints(), Integer.parseInt(tf_tx.getText()), Integer.parseInt(tf_ty.getText()), ds.getShape()));
				break;
			}
			case 1:
			{
				drawingPanel.add(new Rotation(ds.getXPoints(), ds.getYPoints(), drawingPanel.getWidth()/2, drawingPanel.getHeight()/2, Integer.parseInt(tf_normalRotationDeg.getText()), ds.getShape()));
				break;
			}
			case 2:
			{
				drawingPanel.add(new Rotation(ds.getXPoints(), ds.getYPoints(), drawingPanel.getWidth()/2 + Integer.parseInt(tf_fpRotationX.getText()), drawingPanel.getHeight()/2 - Integer.parseInt(tf_fpRotationY.getText()), Integer.parseInt(tf_fpRotationDeg.getText()), ds.getShape()));
				break;
			}
			case 3:
			{
				drawingPanel.add(new Scaling(ds.getXPoints(), ds.getYPoints(), drawingPanel.getWidth()/2, drawingPanel.getHeight()/2, Double.parseDouble(tf_normalScalingSx.getText()), Double.parseDouble(tf_normalScalingSy.getText()), ds.getShape()));
				break;
			}
			case 4:
			{
				drawingPanel.add(new Scaling(ds.getXPoints(), ds.getYPoints(), drawingPanel.getWidth()/2 + Integer.parseInt(tf_fpScalingX.getText()), drawingPanel.getHeight()/2 - Integer.parseInt(tf_fpScalingY.getText()), Double.parseDouble(tf_fpScalingSx.getText()), Double.parseDouble(tf_fpScalingSy.getText()), ds.getShape()));
				break;
			}
			case 5:
			{
				if (jcb_shearOptions.getSelectedIndex()==0) {
					drawingPanel.add(new Shear(ds.getXPoints(), ds.getYPoints(), -(Double.parseDouble(tf_shearFactor.getText())), 0, 0, drawingPanel.getHeight()/2, ds.getShape()));
				}
				else if (jcb_shearOptions.getSelectedIndex()==1) {
					drawingPanel.add(new Shear(ds.getXPoints(), ds.getYPoints(), 0, -(Double.parseDouble(tf_shearFactor.getText())), drawingPanel.getWidth()/2, 0, ds.getShape()));
				}
				break;
			}
			case 6:
			{
				drawingPanel.add(new Reflection(ds.getXPoints(), ds.getYPoints(), jcb_reflectionOptions.getSelectedIndex(), drawingPanel.getWidth()/2, drawingPanel.getHeight()/2, ds.getShape()));
				break;
			}
		}
		revalidate();
		repaint();
			
		}
		
	}
	
	public class btnFillHandler implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				drawingPanel.removeAll();
				drawingPanel.add(new ScanLine(ds.getXPoints(), ds.getYPoints(), selectedColor));
				drawingPanel.repaint();
				drawingPanel.revalidate();
			}
			
	}
	
	public MainWindow() {
		
		selectedColor = Color.BLACK;
		createGUI();

	}

	public static void main(String[] args){
		new MainWindow();
	}

}
