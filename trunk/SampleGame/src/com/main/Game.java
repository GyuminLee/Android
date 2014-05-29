package com.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import com.annotation.Enemy;
import com.annotation.Friend;
import com.drawable.Drawable;

public class Game {
	ArrayList<Drawable> friendDrawable = new ArrayList<Drawable>();
	ArrayList<Drawable> enemyDrawable = new ArrayList<Drawable>();
	ArrayList<Drawable> allDrawable = new ArrayList<Drawable>();
	Random sR = new Random();
	DrawRunnable drawRunnable;

	public Game() {
		drawRunnable = new DrawRunnable();
		drawRunnable.start();
	}

	public boolean isCollision(Drawable d1, Drawable d2) {
		if ( (d1.getClass().isAnnotationPresent(Friend.class) && 
				d2.getClass().isAnnotationPresent(Enemy.class))
				|| (d1.getClass().isAnnotationPresent(Enemy.class) && 
						d2.getClass().isAnnotationPresent(Friend.class)) ) {
			return true;
		}
		return false;
	}

	public void setDrawable(Drawable d) {
		allDrawable.add(d);
		classifyDrawable(d);
		drawRunnable.enqueue(d);
	}

	public void classifyDrawable(Drawable d) {
		if (d.getClass().isAnnotationPresent(Friend.class)) {
			friendDrawable.add(d);
		}

		if (d.getClass().isAnnotationPresent(Enemy.class)) {
			enemyDrawable.add(d);
		}
	}

	public void moveDrawable() {
		int size = allDrawable.size();
		if (size > 0) {
			int target = sR.nextInt(size);
			Drawable d = allDrawable.get(target);
			d.incPosition();
			drawRunnable.enqueue(d);
		}
	}

	public void saveDrawable(ObjectOutputStream out) {
		for (Drawable d : allDrawable) {
			try {
				out.writeObject(d);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void loadDrawable(ObjectInputStream in) {
		try {
			while (true) {
				Drawable d = (Drawable) in.readObject();
				if (d == null) {
					break;
				}
				classifyDrawable(d);
				drawRunnable.enqueue(d);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
