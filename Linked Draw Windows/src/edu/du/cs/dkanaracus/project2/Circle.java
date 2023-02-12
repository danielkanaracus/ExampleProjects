package edu.du.cs.dkanaracus.project2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Circle extends PaintingPrimitive {
	private Point center;
	private Point radiusPoint;

	public Circle(Color color, Point center, Point radiusPoint) {
		super(color);
		this.center = center;
		this.radiusPoint = radiusPoint;
	}

	public void drawGeometry(Graphics g) {
		int radius = (int) Math.abs(center.distance(radiusPoint));
		g.drawOval(center.x - radius, center.y - radius, radius * 2, radius * 2);
	}
	
}
