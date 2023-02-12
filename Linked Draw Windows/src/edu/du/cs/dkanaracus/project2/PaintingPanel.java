package edu.du.cs.dkanaracus.project2;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PaintingPanel extends JPanel {
	ArrayList<PaintingPrimitive> primitives;
	PaintingPrimitive tempShape;

	public PaintingPanel() {
		primitives = new ArrayList<PaintingPrimitive>();
		this.setBackground(Color.WHITE);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(tempShape!=null) {
			tempShape.draw(g);
		}
		for(int i=0;i<primitives.size();i++) {
			primitives.get(i).draw(g);
		}
	}

	public void addPrimitive(PaintingPrimitive obj) {
		this.primitives.add(obj);
	}
	public void changeTempShape(PaintingPrimitive obj) {
		this.tempShape=obj;
	}

}
