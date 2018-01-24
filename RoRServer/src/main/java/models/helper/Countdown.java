package models.helper;

import models.game.Sensor;

/**
 * Falls ein Countdown benötigt wird
 * @author apoeh001
 *
 */
public class Countdown implements Runnable {

	private int seconds;
	
	public Countdown(int seconds) {
		this.seconds = seconds;
	}
	
	@Override
	public void run() {
		this.startCountdown();
		
	}
	
	private void startCountdown() {
		while(seconds > 0) {
			try {
				Thread.sleep(Sensor.SECOND);
				System.out.println("Time left: " + seconds);
				seconds--;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
