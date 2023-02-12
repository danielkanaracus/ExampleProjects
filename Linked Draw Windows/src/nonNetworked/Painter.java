package nonNetworked;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Painter extends JFrame implements ActionListener, MouseListener, MouseMotionListener{
	private Color drawColor=Color.RED;
	private enum Shapes{line,circle};
	private Shapes shape; 
	private Point p1=new Point();
	private Point p2=new Point();
	private PaintingPanel centerPanel;

	public Painter() {
		setSize(500,500);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				
		JPanel holder = new JPanel();
		holder.setLayout(new BorderLayout());

		// Create the paints 

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(3, 1)); // 3 by 1

		// add red paint button
		JButton redPaint = new JButton();
		redPaint.setBackground(Color.RED);
		redPaint.setOpaque(true);
		redPaint.setBorderPainted(false);
		redPaint.addActionListener(this);
		redPaint.setActionCommand("red");

		leftPanel.add(redPaint);  // Added in next open cell in the grid

		//add blue paint button
		JButton bluePaint = new JButton();
		bluePaint.setBackground(Color.BLUE);
		bluePaint.setOpaque(true);
		bluePaint.setBorderPainted(false);
		bluePaint.addActionListener(this);
		bluePaint.setActionCommand("blue");
		
		leftPanel.add(bluePaint);  // Added in next open cell in the grid

		
		//add green paint button
		JButton greenPaint = new JButton();
		greenPaint.setBackground(Color.GREEN);
		greenPaint.setOpaque(true);
		greenPaint.setBorderPainted(false);
		greenPaint.addActionListener(this);
		greenPaint.setActionCommand("green");

		leftPanel.add(greenPaint);  // Added in next open cell in the grid

		// add the panels to the overall panel, holder
		// note that holder's layout should be set to BorderLayout
		holder.add(leftPanel, BorderLayout.WEST);

		// use similar code to add topPanel buttons to the NORTH region
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 2)); // 1 by 2
		
		//add circle
		JButton circleButton=new JButton("Circle");
		topPanel.add(circleButton);
//		topPanel.addActionListener(this);
		circleButton.addActionListener(this);
		circleButton.setActionCommand("circle");

		
		JButton lineButton=new JButton("Line");
		topPanel.add(lineButton);
		lineButton.setActionCommand("line");
		lineButton.addActionListener(this);

		
		holder.add(topPanel, BorderLayout.NORTH);
		

		// omit the center panel for now
		// after finishing the PaintingPanel class (described later) add a
		// new object of this class as the CENTER panel
		centerPanel = new PaintingPanel();
		holder.add(centerPanel, BorderLayout.CENTER);
		centerPanel.addMouseListener(this);

		// And later you will add the chat panel to the SOUTH

		// Lastly, connect the holder to the JFrame
		setContentPane(holder);

		// And make it visible to layout all the components on the screen
		setVisible(true);
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()!=null) {
			if(e.getActionCommand()=="red") {
				drawColor=Color.RED;
				System.out.println("Pressed RED");
			}else if(e.getActionCommand()=="blue") {
				drawColor=Color.BLUE;
			}else if(e.getActionCommand()=="green") {
				drawColor=Color.GREEN;
			}else if(e.getActionCommand()=="circle") {
				System.out.println("test");

				shape=Shapes.circle;
			}else if(e.getActionCommand()=="line") {
				shape=Shapes.line;
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("m press");
		
		p1.x=(int)centerPanel.getMousePosition().x;
		p1.y=(int)centerPanel.getMousePosition().y;

	}


	@Override
	public void mouseReleased(MouseEvent e) {
		p2.x=(int)centerPanel.getMousePosition().x;
		p2.y=(int)centerPanel.getMousePosition().y;
		if(shape==Shapes.circle) {
			
			PaintingPrimitive temp=new Circle(drawColor,p1,p2);
			centerPanel.addPrimitive(temp);
			centerPanel.repaint();
		}else if(shape==Shapes.line) {
			PaintingPrimitive temp=new Line(drawColor,p1,p2);
			centerPanel.addPrimitive(temp);
			centerPanel.repaint();

		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	public static void main(String[] args) {
		new Painter();
	}
}
