package Entities;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class Parking {
	private static int capacity;
	private static int seconds;
	private static HashMap<Integer,Ticket> used_tickets;
	private static ArrayDeque<Ticket> unused_tickets;
	public static HashMap<Integer,Ticket> getUsed_tickets() {
		return used_tickets;
	}
	public static void setUsed_tickets(HashMap<Integer,Ticket> used_tickets) {
		Parking.used_tickets = used_tickets;
	}
	public static ArrayDeque<Ticket> getUnused_tickets() {
		return unused_tickets;
	}
	public static void setUnused_tickets(ArrayDeque<Ticket> unused_tickets) {
		Parking.unused_tickets = unused_tickets;
	}

	public static int getCapacity() {
		return capacity;
	}
	public static void setCapacity(int capacity) {
		Parking.capacity = capacity;
		used_tickets = new HashMap<Integer,Ticket>(capacity);
		unused_tickets = new ArrayDeque<Ticket>();
		for (int i = 0; i < capacity; i++) {
			unused_tickets.addFirst(new Ticket(i+1));
		}
	}
	
	public static int getSeconds() {
		return seconds;
	}
	public static void setSeconds(int seconds) {
		Parking.seconds = seconds;
	}
	
	synchronized public static void park(Car c)	{
		try {
			Ticket new_ticket = unused_tickets.element();
			if (c!=null) {
				unused_tickets.removeFirst();
				new_ticket.setCar(c);
				used_tickets.put(new_ticket.getNumber(), new_ticket);
			}
		}
		catch (NoSuchElementException e) {
			System.out.println("Ќет свободных мест в паркинге");
		}
	}
	synchronized public static void unpark(int N) {
		try	{
			Ticket t = used_tickets.get(N);
			if (t==null)
				throw new IndexOutOfBoundsException("ѕарковочного билета с номером " + N + " не существует!");
			else {				
				t.setCar(null);
				used_tickets.remove(N);
				unused_tickets.add(t);
			}
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
	}

}
