package edu.du.cs.dkanaracus.project2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class HubComs implements Runnable {
	private ArrayList<Thread> ths;
	private ArrayList<ObjectInputStream> inputStreams;
	private ArrayList<ObjectOutputStream> outputStreams;
	private int thNum;
	private int numPainters;
	private Hub h;

	public HubComs(ArrayList<Thread> ths, ArrayList<ObjectInputStream> inputStreams,
			ArrayList<ObjectOutputStream> outputStreams, int thNum, int numPainters, Hub h) {
		this.ths = ths;
		this.inputStreams = inputStreams;
		this.outputStreams = outputStreams;
		this.thNum = thNum;
		this.numPainters = numPainters;
		this.h=h;

	}

	@Override
	synchronized public void run() {
		try {
			while (true) {//if object is recieved this sends it to be distributed
				Object temp = inputStreams.get(thNum).readObject();
				h.distribute(temp,thNum);

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			ths.set(thNum, null);//sets thread to null in array which is check elsewhere so the conected painter is never atmpted to access again.
		}

	}

}
