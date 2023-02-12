package nonNetworked;

import java.awt.Color;
import java.awt.Graphics;

abstract  class PaintingPrimitive {
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
