import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	static  ArrayList<ACC> ALL_ACCs = new ArrayList<>();
	
	public static String[] LineParser(String lineContent) {
		return lineContent.split(" ");
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
		int inputNumber=10;
		String inputFilePath = "C:\\Users\\Hüseyin Karataþ\\Desktop\\inputs\\"+"case" + inputNumber + ".in";
//		inputFilePath = args[0];
		String outputFileName = "C:\\Users\\Hüseyin Karataþ\\Desktop\\MY_OUTPUT.txt";
//		outputFileName = args[1];
		File input_file = new File(inputFilePath);

		Scanner sc = new Scanner(input_file);
		
		//TAKE THE NUMBER OF ACCS AND FLIGHTS
		String[] firstLineContent = LineParser(sc.nextLine());
		int numberOfACCs = Integer.parseInt(firstLineContent[0]);
		int numberOfFlights = Integer.parseInt(firstLineContent[1]);
		
		//TAKE THE ACCS AND AIRPORTS CONNECTED TO THEM FROM THE INPUT
		for (int i = 0; i < numberOfACCs; i++) {
			String[] currentLineContent = LineParser(sc.nextLine());
			String newAccCode = currentLineContent[0];
			ACC newAcc = new ACC(newAccCode);
			
			//Connect airports to thist new acc
			for (int j = 1; j < currentLineContent.length; j++) {
				newAcc.addAirport(currentLineContent[j]);
			}
			ALL_ACCs.add( newAcc);
		}
		
		
//		System.out.println(ALL_ACCs);
		//ADD FLIGHTS TO APPROPRIATE ACCs
		
		
		for(int i=0; i<numberOfFlights; i++ ) {
			String[] currentLineContent = LineParser(sc.nextLine());
			int admissionTime = convertToInt(currentLineContent[0]);
			String flightCode = currentLineContent[1];
			ACC accOfFlight = null;
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
				for (int j = 1; j <= 21; j++) {
			    Operation newOperation = new Operation(j,"WAITING","ACC", convertToInt(currentLineContent[j+4]));
			    operations.add(0,newOperation);
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			//Finally, add that flight to allFlights array of the proper ACC (which we reached by the input line.)
			accOfFlight.addFlightToAllFlights(new Flight(flightCode, admissionTime, operations, departureAirport, arrivalAirport));
		}		
					
			sc.close();

			for (ACC acc : ALL_ACCs) {
				try {
					acc.increaseTime();
				} catch (Exception e) {
					System.out.println("error");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String resultString = "";
			FileWriter wrtr = new FileWriter(outputFileName);
			for (ACC acc : ALL_ACCs ) {
				resultString+=acc.code + " ";
				resultString+=acc.TIMELINE+"";
				
				for (Airport airport  : acc.Airports ) {
					if(airport!=null) {
						resultString+= " " + airport.getAtc().code;

					}
				}
				resultString+= "\n";
			}
			System.out.println(resultString);
			wrtr.write(resultString);
			wrtr.close();
			
		
	}
		
}
