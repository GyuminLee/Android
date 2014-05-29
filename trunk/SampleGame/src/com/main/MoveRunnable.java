package com.main;

public class MoveRunnable implements Runnable {

	Game game;
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	boolean isRunning = false;
	
	public void start() {
		isRunning = true;
		new Thread(this).start();
	}
	@Override
	public void run() {
		while(isRunning) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			game.moveDrawable();
		}
	}

}
