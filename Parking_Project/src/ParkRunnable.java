import java.util.Random;

import Entities.*;
public class ParkRunnable implements Runnable {
	
	public ParkRunnable(int i) {
		this.i = i;
	}

	private int i;
	
	@Override
	public void run() {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        char letter1 = letters.charAt(rnd.nextInt(letters.length()));
        char letter2 = letters.charAt(rnd.nextInt(letters.length()));
        StringBuilder sb = new StringBuilder();
        sb.append(letter1);
        sb.append(letter2);
        sb.append(Integer.toString(i));
        String sign =  sb.toString();
        Parking.park(new Car(sign));
	}

}
