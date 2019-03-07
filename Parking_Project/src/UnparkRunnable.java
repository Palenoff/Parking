import Entities.Parking;

public class UnparkRunnable implements Runnable {

	private int number;
	
	public UnparkRunnable(int number) {
		this.number = number;
	}
	
	@Override
	public void run() {
		Parking.unpark(number);
	}



}
