package edu.du.cs.dkanaracus.project2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;

public class Hub {
	private int numPainters;
	private ArrayList<Thread> ths;
	private ArrayList<ObjectInputStream> inputStreams;
	private ArrayList<ObjectOutputStream> outputStreams;
	private PaintingPanel centerPanel;
	private JTextArea l;
	
	
	public Hub() {
		numPainters = 0;
		ArrayList<Socket> painters = new ArrayList<Socket>();
		inputStreams = new ArrayList<ObjectInputStream>();
		outputStreams = new ArrayList<ObjectOutputStream>();
		ths = new ArrayList<Thread>();


		try {
			//up to date center panel and text chat that is sent to new painters
			centerPanel = new PaintingPanel();
			l=new JTextArea(3,40);
			
			ServerSocket ss = new ServerSocket(7000);


			while (true) {//loop for adding and setting up painters
				//System.out.println("Waiting for a call");
				Socket s = ss.accept();
				//System.out.println("Accepted");
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

				//set up center panel and text with previous info
				oos.writeObject(centerPanel);
				oos.writeObject(l);

				// adds socked input steam and output stream to arrays
				inputStreams.add(ois);
				outputStreams.add(oos);

				// makes threads that recive data from painters
				HubComs hc = new HubComs(ths, inputStreams, outputStreams, numPainters, numPainters, this);
				Thread th = new Thread(hc);
				ths.add(th);

				th.start();
				
				numPainters++;

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	synchronized public void distribute(Object value,int thNum) {
		//updates a versoin of center panel and conversation
		if(value instanceof PaintingPrimitive) {
			this.centerPanel.addPrimitive((PaintingPrimitive) value);
			//this.centerPanel.repaint();
		}else if(value instanceof String) {
			this.l.setText(l.getText()+((String) value));
			
		}
		//distributes new message/new painted object
		for(int i=0;i<numPainters;i++) {
			if(ths.get(i)!=null&&i!=thNum) {
				try {
					outputStreams.get(i).writeObject(value);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public static void main(String[] args) {
		new Hub();

	}
	


}
