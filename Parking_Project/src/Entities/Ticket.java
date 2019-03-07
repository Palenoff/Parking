package Entities;

public class Ticket {
	private Car car;
	private int number;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Ticket(int number) {
		this.number = number;
	}
	
	
}
