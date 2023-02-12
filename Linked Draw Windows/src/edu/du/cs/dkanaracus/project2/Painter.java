package edu.du.cs.dkanaracus.project2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class Painter extends JFrame implements ActionListener, MouseListener, MouseMotionListener, Serializable {
	private static final long serialVersionUID = 1L;

	private Color drawColor = Color.RED;// base color set to red

	private enum Shapes {
		line, circle
	};

	private Shapes shape=Shapes.circle;//base shape set to circle
	private Point p1 = new Point();
	private Point p2 = new Point();
	private PaintingPanel centerPanel;
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private JTextArea ta;
	private JTextArea l;
	private String name;

	public Painter() {
		try {
			name = JOptionPane.showInputDialog("Enter your name");//name pop up
			if (name == null || name.equals("null")) {//to avoid names being null
				name = "no name";
			}
			s = new Socket("localhost", 7000);

			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			//sets up center and messages with info to match other painters
			centerPanel = (PaintingPanel) ois.readObject();
			l = (JTextArea) ois.readObject();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setSize(500, 500);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JPanel holder = new JPanel();
		holder.setLayout(new BorderLayout());
		holder.setName(name);
		
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

		leftPanel.add(redPaint); // Added in next open cell in the grid

		// add blue paint button
		JButton bluePaint = new JButton();
		bluePaint.setBackground(Color.BLUE);
		bluePaint.setOpaque(true);
		bluePaint.setBorderPainted(false);
		bluePaint.addActionListener(this);
		bluePaint.setActionCommand("blue");

		leftPanel.add(bluePaint); // Added in next open cell in the grid

		// add green paint button
		JButton greenPaint = new JButton();
		greenPaint.setBackground(Color.GREEN);
		greenPaint.setOpaque(true);
		greenPaint.setBorderPainted(false);
		greenPaint.addActionListener(this);
		greenPaint.setActionCommand("green");

		leftPanel.add(greenPaint); // Added in next open cell in the grid

		// add the panels to the overall panel, holder
		holder.add(leftPanel, BorderLayout.WEST);

		//circle and line button
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 2)); // 1 by 2

		// add circle
		JButton circleButton = new JButton("Circle");
		topPanel.add(circleButton);
//		topPanel.addActionListener(this);
		circleButton.addActionListener(this);
		circleButton.setActionCommand("circle");
		//add line
		JButton lineButton = new JButton("Line");
		topPanel.add(lineButton);
		lineButton.setActionCommand("line");
		lineButton.addActionListener(this);

		holder.add(topPanel, BorderLayout.NORTH);

		//center panel
		holder.add(centerPanel, BorderLayout.CENTER);
		centerPanel.addMouseListener(this);
		centerPanel.addMouseMotionListener(this);

		//chat panel
		JPanel bottomPanel = new JPanel(new GridLayout(2, 0));
		JPanel temp = new JPanel();
		ta = new JTextArea(1, 40);
		temp.add(ta, BorderLayout.NORTH);

		JButton submitButton = new JButton("submit");
		temp.add(submitButton);

		submitButton.setActionCommand("submit");
		submitButton.addActionListener(this);

		
		l.setEditable(false);
		JScrollPane jsp = new JScrollPane(l);
		bottomPanel.add(temp, BorderLayout.NORTH);
		bottomPanel.add(jsp);

		holder.add(bottomPanel, BorderLayout.SOUTH);

		// thread to recive info from hub
		PainterComs pc = new PainterComs(oos, ois, centerPanel, l);
		Thread th = new Thread(pc);
		th.start();

		// Lastly, connect the holder to the JFrame
		setContentPane(holder);

		// And make it visible to layout all the components on the screen
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//find correct buton press and preform action
		if (e.getActionCommand() != null) {
			if (e.getActionCommand() == "red") {
				drawColor = Color.RED;
			} else if (e.getActionCommand() == "blue") {
				drawColor = Color.BLUE;
			} else if (e.getActionCommand() == "green") {
				drawColor = Color.GREEN;
			} else if (e.getActionCommand() == "circle") {

				shape = Shapes.circle;
			} else if (e.getActionCommand() == "line") {
				shape = Shapes.line;
			} else if (e.getActionCommand() == "submit") {
				//copy all writen so far plus add new message to own board
				String text = ta.getText();
				String temp = "\n" + name + ": " + text;
				l.setText(l.getText() + temp);
				//pass on only new message
				try {
					oos.writeObject(temp);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {//logic for real time draw
		p2.x = (int) centerPanel.getMousePosition().x;
		p2.y = (int) centerPanel.getMousePosition().y;
		if (shape == Shapes.circle) {
			PaintingPrimitive temp = new Circle(drawColor, p1, p2);
			centerPanel.changeTempShape(temp);
			centerPanel.repaint();

		} else if (shape == Shapes.line) {
			PaintingPrimitive temp = new Line(drawColor, p1, p2);
			centerPanel.changeTempShape(temp);
			centerPanel.repaint();

		}
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
	public void mousePressed(MouseEvent e) {//record initial press
		p1.x = (int) centerPanel.getMousePosition().x;
		p1.y = (int) centerPanel.getMousePosition().y;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		centerPanel.changeTempShape(null);//stop drawing reactive shape
		p2.x = (int) centerPanel.getMousePosition().x;
		p2.y = (int) centerPanel.getMousePosition().y;
		if (shape == Shapes.circle) {
			//copy of points
			Point np1 = new Point(p1);
			Point np2 = new Point(p2);
			//add to own
			PaintingPrimitive temp = new Circle(drawColor, np1, np2);
			centerPanel.addPrimitive(temp);
			centerPanel.repaint();
			//pass to hub to distribute
			try {
				oos.writeObject(temp);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (shape == Shapes.line) {
			Point np1 = new Point(p1);
			Point np2 = new Point(p2);
			//add to own
			PaintingPrimitive temp = new Line(drawColor, np1, np2);
			centerPanel.addPrimitive(temp);
			centerPanel.repaint();
			//send to hub to distribute
			try {
				oos.writeObject(temp);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

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
