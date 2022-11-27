import java.io.Reader;
import java.util.ArrayList;

public class ACC {
	
	
	public String code;
	private int TIMELINE=0;
	public Airport[] Airports= new Airport[1000];
	public ArrayList<Flight> allFlightsForAcc = new ArrayList<>();
	public ReadyQueue AccReadyQueue = new ReadyQueue();
	public WaitingsList AccWaitingsList = new WaitingsList();
	public ArrayList<Flight> candidatesForReadyQueue = new ArrayList<>();
	public ArrayList<Airport> allAirports = new ArrayList<>();
	public int incompleteFlights=0;
	
	public int getTimeLine() {
		return this.TIMELINE;
	}
	
	public ACC(String accCode) {
		 this.code=accCode;
	}
	
	public void extractNotComplete() throws Exception{
		if(this.AccReadyQueue.getSize()>0) {
			Flight currentFlight = this.AccReadyQueue.peek();
			if(TIMELINE%30==0 && currentFlight.getCurrentOperation().remainingTime!=0) {			
				this.candidatesForReadyQueue.add(this.AccReadyQueue.popFromQueue());
			}
		}
		
	}
	
	public void extractFromAllFlights() {
		ArrayList<Flight> readyFlights = new ArrayList<Flight>();
		for (Flight flight : this.allFlightsForAcc) {
			if (flight.admissionTime==TIMELINE) {
				readyFlights.add(flight);
			}
		}
		
		if(readyFlights.size()>0) {
			for (Flight flight : readyFlights) {
				this.candidatesForReadyQueue.add(flight);
				this.allFlightsForAcc.remove(flight);
			}
		}

	}
		
	public void extractFromWaitingQueue() {
		ArrayList<Flight> readyFlights = this.AccWaitingsList.extractReadyFlights();
		for (Flight flight : readyFlights) {
			this.candidatesForReadyQueue.add(flight);
		}
	}
	
	
	public void sendToWaitingQueue(Flight flight) throws Exception {
		this.AccWaitingsList.addToQueue(this.AccReadyQueue.popFromQueue());
	}
	
	public void addFlightToAllFlights(Flight f) {
		System.out.println(f.code);
		this.allFlightsForAcc.add(f);
		this.incompleteFlights++;
	}
	
//	public ReadyQueue getReadyQueue() {
//		return this.AccReadyQueue;
//	}
	
	public void terminateFlight() throws Exception {
		this.AccReadyQueue.popFromQueue();
		this.incompleteFlights--;
	}
	
	
	
//	private int getIndex(ArrayList<Flight> flightsList, Flight flight) {
//		for (int i = 0; i < flightsList.size(); i++) {
//			if(flightsList.get(i)==flight) {
//				return i;
//			}
//		}
//		return -1;
//		
//	}
	
	public void addAirport(String airportCode) {
		int slot = 0;
		for (int i = 0; i < airportCode.length(); i++) {
			char currentChar = airportCode.charAt(i);
			int asciiValue = currentChar;
			slot+=asciiValue*Math.pow(31,i);
		}
		slot = slot%1000;
		while(Airports[slot]!=null) {
			slot=(slot+1)%1000;
		}
		
		Airport airportToBeAddded = new Airport(airportCode);
		ATC atcOfThatAirport = new ATC(this.code+slot, this);
		airportToBeAddded.setAtc(atcOfThatAirport);
		Airports[slot] = airportToBeAddded;
		allAirports.add(airportToBeAddded);
	}
	
	public Airport getAirport(String airportCode) {
		int slot = 0;
		for (int i = 0; i < airportCode.length(); i++) {
			char currentChar = airportCode.charAt(i);
			int asciiValue = currentChar;
			slot+=asciiValue*Math.pow(31,i);
		}
		slot = slot%1000;
		while(Airports[slot].code.compareTo(airportCode)!=0) {
			slot=(slot+1)%1000;
		}
		return Airports[slot];
	}
	
	

	
	public void increaseTime() throws Exception {	
		
		
		ArrayList<ATC> allATCs = new ArrayList<>();
		for (Airport airport : allAirports ) {
			allATCs.add(airport.getAtc());
		}

		FlightComparator fc = new FlightComparator();
		while (this.incompleteFlights!=0) {
			for (ATC atc : allATCs) {
				atc.runTime();
			}
			if(this.AccWaitingsList.getSize()>0) {
				for (Flight waitingFlight : this.AccWaitingsList.getElements()) {
					waitingFlight.getCurrentOperation().remainingTime--;
				}
			}
			extractFromAllFlights();
			extractFromWaitingQueue();
			extractNotComplete();
			
			if(this.candidatesForReadyQueue.size() > 0) {
				this.candidatesForReadyQueue.sort(fc);
				for (Flight f : candidatesForReadyQueue) {
					this.AccReadyQueue.addToQueue(f);
					System.out.println(AccReadyQueue.getSize());
				}
				this.candidatesForReadyQueue.clear();
			}
			
			
			if(AccReadyQueue.getSize()>0) {
				//Check the current running flight. Terminate it or send to waiting queue if needed.
				Flight currentFlight = this.AccReadyQueue.peek();
				if(currentFlight.getCurrentOperation().remainingTime==0) {
					if(currentFlight.getCurrentOperation().operationNumber==3) {
						currentFlight.remainingOperations.remove(currentFlight.remainingOperations.size()-1);
						currentFlight.departureAirport.getAtc().candidatesForReadyQueue.add(this.AccReadyQueue.popFromQueue());
						//SEND TO DEPARTURE ATC
						
					}
					else if(currentFlight.getCurrentOperation().operationNumber==13){
						currentFlight.remainingOperations.remove(currentFlight.remainingOperations.size()-1);
						currentFlight.arrivalAirport.getAtc().candidatesForReadyQueue.add(this.AccReadyQueue.popFromQueue());
						//SEND TO LANDING ATC
					}
					else if(currentFlight.getCurrentOperation().operationNumber<21) {
						currentFlight.remainingOperations.remove(currentFlight.remainingOperations.size()-1);
						this.sendToWaitingQueue(currentFlight);
					}
					else if(currentFlight.getCurrentOperation().operationNumber==21) {
						terminateFlight();
					}
					
				}
				else {
					currentFlight.getCurrentOperation().remainingTime--;
				}
				
			}
			
			TIMELINE++;
		}
		
	
		
		
	}
	
	
	
}




