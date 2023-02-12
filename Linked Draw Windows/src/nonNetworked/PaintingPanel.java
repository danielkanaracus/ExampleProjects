package nonNetworked;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PaintingPanel extends JPanel {
	ArrayList<PaintingPrimitive> primitives;

	public PaintingPanel() {
		primitives = new ArrayList<PaintingPrimitive>();
		this.setBackground(Color.WHITE);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("t1 " + primitives.size());		

		for(int i=0;i<primitives.size();i++) {
			primitives.get(i).draw(g);
		}
	}

	public void addPrimitive(PaintingPrimitive obj) {
		this.primitives.add(obj);
		System.out.println("t1 " + primitives.size());		
	}

}
