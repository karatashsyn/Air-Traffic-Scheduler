import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.System.Logger;
import java.net.SecureCacheResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.w3c.dom.ls.LSOutput;

public class Main {
	static  ArrayList<ACC> ALL_ACCs = new ArrayList<>();
	
	public static String[] LineParser(String lineContent) {
		return lineContent.split(" ");
	}
	
	
	public static String paddedVersion(int num) {
		return String.format("%03d", num);
	}
	
	public static int convertToInt(String val) {
		return Integer.parseInt(val);
	}
	
//	public static int getAsciiVal(char c) {
//		int asciiVal = c;
//		return asciiVal;
//	}
	
	
	
	public static void main(String[] args) throws IOException{
		//IO Instances
		File file = new File("C:\\Users\\Hüseyin Karataþ\\Desktop\\case6_input.txt");
		FileWriter wrtr = new FileWriter("C:\\Users\\Hüseyin Karataþ\\Desktop\\denemeoutput.txt");
		Scanner sc = new Scanner(file);
		FlightComparator fc = new FlightComparator();
		
		//TAKE THE NUMBER OF ACCS AND FLIGHTS
		String[] firstLineContent = LineParser(sc.nextLine());
		int numberOfACCs = Integer.parseInt(firstLineContent[0]);
		int numberOfFlights = Integer.parseInt(firstLineContent[1]);
		
		//TAKE THE ACCS AND AIRPORTS CONNECTED TO THEM FROM THE INPUT
		for (int i = 0; i < numberOfACCs; i++) {
			System.out.println("number : " + numberOfACCs);
			String[] currentLineContent = LineParser(sc.nextLine());
			String newAccCode = currentLineContent[0];
			ACC newAcc = new ACC(newAccCode);
			
			//Connect airports to thist new acc
			for (int j = 1; j < currentLineContent.length; j++) {
				newAcc.addAirport(currentLineContent[j]);
			}
			ALL_ACCs.add( newAcc);
		}
		System.out.println(ALL_ACCs);
		
		//ADD FLIGHTS TO APPROPRIATE ACCs
		for(int i=0; i<numberOfFlights; i++ ) {
			String[] currentLineContent = LineParser(sc.nextLine());
			int admissionTime = convertToInt(currentLineContent[0]);
			String flightCode = currentLineContent[1];
			ACC accOfFlight = new ACC(flightCode);
			for (ACC acc : ALL_ACCs) {
				if(acc.code.compareTo(currentLineContent[2])==0) {
					accOfFlight= acc;
				}
			}
			Airport departureAirport = accOfFlight.getAirport(currentLineContent[3]);
			Airport arrivalAirport = accOfFlight.getAirport(currentLineContent[4]);
			ArrayList<Operation> operations = new ArrayList<>();
			//Get operations of that flight which will be added
			try {
				operations.add(0,new Operation(1,"RUNNING", "ACC", convertToInt(currentLineContent[5])));
				operations.add(0,new Operation(2,"WAITING", "ACC", convertToInt(currentLineContent[6])));
				operations.add(0,new Operation(3,"RUNNING", "ACC", convertToInt(currentLineContent[7])));
				operations.add(0,new Operation(4,"RUNNING", "ATC", convertToInt(currentLineContent[8])));
				operations.add(0,new Operation(5,"WAITING", "ATC", convertToInt(currentLineContent[9])));
				operations.add(0,new Operation(6,"RUNNING", "ATC", convertToInt(currentLineContent[10])));
				operations.add(0,new Operation(7,"WAITING", "ATC", convertToInt(currentLineContent[11])));
				operations.add(0,new Operation(8,"RUNNING", "ATC", convertToInt(currentLineContent[12])));
				operations.add(0,new Operation(9,"WAITING", "ATC", convertToInt(currentLineContent[13])));
				operations.add(0,new Operation(10,"RUNNING", "ATC", convertToInt(currentLineContent[14])));
				operations.add(0,new Operation(11,"RUNNING", "ACC", convertToInt(currentLineContent[15])));
				operations.add(0,new Operation(12,"WAITING", "ACC", convertToInt(currentLineContent[16])));
				operations.add(0,new Operation(13,"RUNNING", "ACC", convertToInt(currentLineContent[17])));
				operations.add(0,new Operation(14,"RUNNING", "ATC", convertToInt(currentLineContent[18])));
				operations.add(0,new Operation(15,"WAITING", "ATC", convertToInt(currentLineContent[19])));
				operations.add(0,new Operation(16,"RUNNING", "ATC", convertToInt(currentLineContent[20])));
				operations.add(0,new Operation(17,"WAITING", "ATC", convertToInt(currentLineContent[21])));
				operations.add(0,new Operation(18,"RUNNING", "ATC", convertToInt(currentLineContent[22])));
				operations.add(0,new Operation(19,"WAITING", "ATC", convertToInt(currentLineContent[23])));
				operations.add(0,new Operation(20,"RUNNING", "ATC", convertToInt(currentLineContent[24])));
				operations.add(0,new Operation(21,"RUNNING", "ACC", convertToInt(currentLineContent[25])));
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			//Finally, add that flight to allFlights array of the proper ACC (which we reached by the input line.)
			accOfFlight.addFlightToAllFlights(new Flight(flightCode, admissionTime, operations, departureAirport, arrivalAirport));
			
			
		}		
					
			

			for (ACC acc : ALL_ACCs) {
				try {
					acc.increaseTime();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		

		
		
	}
		
}
