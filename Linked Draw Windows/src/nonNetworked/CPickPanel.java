package nonNetworked;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class CPickPanel extends JPanel {
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("CPickPanel");

		g.setColor(Color.BLUE);
	}
	
}
