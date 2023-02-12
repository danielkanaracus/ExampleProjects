package edu.du.cs.dkanaracus.project2;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

abstract  class PaintingPrimitive implements Serializable {
	private Color color;
	public PaintingPrimitive(Color color){
		this.color=color;
	}
	public final void draw(Graphics g) {
	    g.setColor(this.color);
	    drawGeometry(g);
	}
	protected abstract void drawGeometry(Graphics g);


}
