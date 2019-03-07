import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import Entities.*;

public class Application {

	private static int carsign = 0;
	
	public static void menu() throws Exception {
		while(true) {
			System.out.println("\n\n������ ������:\n"
					+ "p:N - ������������ ������, ��� N - ���������� ����� �� �����\r\n" + 
					"u:N - ������� � ��������, ��� N - ����� ������������ ������\r\n" + 
					"u:[1..n] - ������� � �������� ���������� �������, ��� � ���������� �������, ����� ������� ���������� ������ ����������� �������\r\n" + 
					"l - ������ �����, ����������� �� ��������\r\n" +
					"c - ���������� ���������� ���� �� ��������\r\n" + 
					"s - ���������\r\n" + 
					"e - ����� �� ����������\r\n" + 
					"������� �������:");
			System.out.print("> ");
			BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
			try {
				String choice = reader.readLine();
				switch (choice.charAt(0)) {
		           case ('p'):
		               if (choice.matches("(p)(:)(\\d+)"))
		               {
		            	   int c = Integer.parseInt(choice.substring(2));
		            	   park(c);
			        	   System.out.println("��� ������ ������� �� ��������!\n");
		               }
		               break;
		           case ('u'):
		               if (choice.matches("(u)(:)(\\d+)")) {
            	   			int c = Integer.parseInt(choice.substring(2));
		            	    new Thread(new UnparkRunnable(c)).start();
		        			Thread.sleep(Parking.getSeconds()*1000);
		               }
		               else if (choice.matches("(u)(:)(\\[)(\\d+)+((\\,)(\\d+))*(\\])")) {
		            	   String [] tickets = choice.substring(3, choice.length() - 1).split(",");
		            	   for (String ticket : tickets) {
		            		   new Thread(new UnparkRunnable(Integer.parseInt(ticket))).start();
		           			   Thread.sleep(Parking.getSeconds()*1000);
		            	   }
		               }		            	   
		               break;
		           case ('l'):
		        	   System.out.println("������ ����� �� ��������!\n");
		           		if (Parking.getUsed_tickets().isEmpty())
		           			System.out.println("����� �� �������� ���:\n");
		           		else
		           			for (int ticket_number : Parking.getUsed_tickets().keySet()) {
		           				System.out.println(Parking.getUsed_tickets().get(ticket_number).getCar().getSign() + " - " + Parking.getUsed_tickets().get(ticket_number).getNumber());
		        		}
		               break;
		           case ('c'):
		        	   System.out.println("���������� ��������� ���� �� ��������: " + Parking.getUnused_tickets().size() + "\n\n");
		               break;
		           case ('e'):
		               System.exit(0);
		               break;
		           case ('s'):
		        	   System.out.println("�������� ��������:\n� - ����������� ��������\ns - ���������� ������ �� �����");
		           	   System.out.print("> ");
		           	   BufferedReader settings_reader =  new BufferedReader(new InputStreamReader(System.in));
		           	   char c = (char) settings_reader.read();
		           	   switch (c) {
		           	   case ('c'):
		           		   System.out.println("����������� ��������: " + Parking.getCapacity() + "\n��������?\n1 - ��\n0 - ���");
		           	   	   System.out.print("> ");
		           	   	   BufferedReader capacity_settings_reader =  new BufferedReader(new InputStreamReader(System.in));
		           	   	   int cc = capacity_settings_reader.read();
		           	   	   switch (cc) {
		           	   	   case ('1'): 
		           	   		   setParkingCapacity();
		           	   	   	   System.out.println("����� ����������� �������� ������!");
		           	   	       break;
		           	   	   case ('0'):
		           	   		   System.out.println("��������� ����������� �������� ��������!");
		           	   		   break;
		           	   	   default:
				               throw new IOException("������� ������� �������");
		           	   	   }
		           		   break;
		           	   case ('s'):
		           		   System.out.println("���������� ������ �� �����: " + Parking.getSeconds()+ "\n��������?\n1 - ��\n0 - ���\n");
		           	   	   System.out.print("> ");
	           	   	   	   BufferedReader seconds_settings_reader =  new BufferedReader(new InputStreamReader(System.in));
		           	   	   int cs = seconds_settings_reader.read();
		           	   	   switch (cs) {
		           	   	   case ('1'): 
		           	   		   setCarTimer();
		           	   	   	   System.out.println("���������� ������ ������");
		           	   	       break;
		           	   	   case ('0'):
		           	   		   System.out.println("��������� ���������� ������ �� ����� ��������!");
		           	   		   break;
		           	   	   default:
				               throw new IOException("������� ������� �������");
		           	   	   }
		           		   break;
		           	   default:
			               throw new IOException("������� ������� �������");
		           	   }
		               break;
		           default:
		               throw new IOException("������� ������� �������");
		       }
			} 
			catch (IOException e) {
				System.out.println("\n\n" + e.getMessage() + "\n\n");
			}
			finally {
				System.out.println("\n��� ����������� ������� Enter");
				new BufferedReader(new InputStreamReader(System.in)).readLine();
			}
		}
	}
	
	public static void park(int N) throws InterruptedException {
		for (int i = carsign; i < carsign + N; i++) {
			new Thread(new ParkRunnable(i)).start();
			Thread.sleep(Parking.getSeconds()*1000);
		}		
	}
	
	public static void setParkingCapacity() {
		System.out.println("�������, ���������� ���������� ��� ��������: ");
		BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
		int parking_capacity;
		boolean is_set_capacity = false;
		do {
				try {
					String parking_capacity_str = reader.readLine();
					parking_capacity = Integer.parseInt(parking_capacity_str);
					if (parking_capacity < 0)
						throw new IllegalArgumentException("���������� ���� �� ����� ���� �������������. ���������� ��� ���: ");
					Parking.setCapacity(parking_capacity);
					is_set_capacity = true;
				}
		        catch (NumberFormatException e) {
		        	System.out.println("���������� ���� �� �������� ������ �������. ���������� ��� ���: ");
		        }
				catch (IllegalArgumentException e) {
		        	System.out.println(e.getMessage());
				}
				catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		while (!is_set_capacity);
	}
	
	public static void setCarTimer() {
		System.out.println("�������, ���������� ������ �� ����� ��� ������: ");
		BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
		int car_seconds;
		boolean is_set_seconds = false;
		do {
				try {
					String car_seconds_str = reader.readLine();
					car_seconds = Integer.parseInt(car_seconds_str);
					if (car_seconds < 0)
						throw new IllegalArgumentException("���������� ������ �� ����� ���� �������������. ���������� ��� ���: ");
					if (car_seconds > 6)
						throw new IllegalArgumentException("���������� ������ �� ����� ���� ����� ����. ���������� ��� ���: ");
					Parking.setSeconds(car_seconds);
					is_set_seconds = true;
				}
		        catch (NumberFormatException e) {
		        	System.out.println("���������� ������ ��� ������ ������ �� �������� ������ �������. ���������� ��� ���: ");
		        }
				catch (IllegalArgumentException e) {
		        	System.out.println(e.getMessage());
				}
				catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		while (!is_set_seconds);
	}
	
	public static void main(String[] args)  {
		try {
		System.out.println("������������!\n����� ���������� � ���������� �������!");
		Parking.setCapacity(100);
		Parking.setSeconds(3);
		menu();		
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
