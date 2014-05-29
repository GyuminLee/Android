package com.main;

import java.util.ArrayList;

import com.drawable.Drawable;
import com.drawable.EndDrawable;

public class DrawRunnable implements Runnable {

	boolean isRunning = false;
	ArrayList<Drawable> mQueue = new ArrayList<Drawable>();
	
	public void enqueue(Drawable d) {
		mQueue.add(d);
		synchronized (mQueue) {
			mQueue.notify();
		}
	}
	
	public Drawable dequeue() {
		while(mQueue.size() == 0) {
			synchronized (mQueue) {
				try {
					mQueue.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		Drawable d = mQueue.remove(0);
		return d;
	}
	
	
	public void start() {
		new Thread(this).start();
	}
	
	public void end() {
		EndDrawable d = new EndDrawable();
		enqueue(d);
	}
	
	@Override
	public void run() {
		while(true) {
			Drawable d = dequeue();
			if (d instanceof EndDrawable) {
				break;
			}
			d.draw(System.out);
		}
	}
}
