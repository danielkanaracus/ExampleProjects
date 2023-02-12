package edu.du.cs.dkanaracus.project2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JTextArea;

public class PainterComs implements Runnable{
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private PaintingPanel centerPanel;
	private JTextArea l;
	
	
	
	public PainterComs(ObjectOutputStream oos, ObjectInputStream ois, PaintingPanel centerPanel, JTextArea l) {
		this.oos=oos;
		this.ois=ois;
		this.centerPanel=centerPanel;
		this.l=l;
		
	}

	@Override
	synchronized public void run() {
		while(true) {
			try {
				Object temp=ois.readObject();//recive from hub
				if(temp instanceof PaintingPrimitive) {//add shape to center
					this.centerPanel.addPrimitive((PaintingPrimitive) temp);
					this.centerPanel.repaint();
				}else if(temp instanceof String) {//add and update messages
					this.l.setText(l.getText()+(String) temp);
					
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
