import java.io.File;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

public class ACC {
	
	
	public String code;
	public int TIMELINE=0;
	public Airport[] Airports= new Airport[1000];
	public ArrayList<Flight> allFlightsForAcc = new ArrayList<>();
	public ReadyQueue AccReadyQueue = new ReadyQueue();
	public WaitingsList AccWaitingsList = new WaitingsList();
	public ArrayList<Flight> candidatesForReadyQueue = new ArrayList<>();
	public ArrayList<Airport> allAirports = new ArrayList<>();
	public int incompleteFlights=0;
	
	
	public ACC(String accCode) {
		 this.code=accCode;
	}
	
	
	
	public Flight notCompletedInSlice() throws Exception{
		Flight notCompleted = null;
		if(this.AccReadyQueue.getSize()>0) {
			Flight currentFlight = this.AccReadyQueue.peek();
			if((currentFlight.getCurrentOperation().spentTime!=0) && currentFlight.getCurrentOperation().spentTime%30==0 && currentFlight.getCurrentOperation().remainingTime>0 ) {
				notCompleted = this.AccReadyQueue.peek();
			}
		}
		return notCompleted;
		
	}

	
	public ArrayList<Flight> extractedNewFlights() {
		ArrayList<Flight> readyFlights = new ArrayList<Flight>();
		
		for (Flight flight : this.allFlightsForAcc) {
			if (flight.admissionTime==TIMELINE) {
				readyFlights.add(flight);
			}
		}
		
		if(readyFlights.size()>0) {
			for (Flight flight : readyFlights) {
			allFlightsForAcc.remove(flight);
			}
		}
		return readyFlights;

	}
		
	public ArrayList<Flight> extractedFromWaitingQueue() {
		ArrayList<Flight> readyFlights = this.AccWaitingsList.extractReadyFlights();
		for (Flight flight : readyFlights) {
			flight.remainingOperations.remove(flight.remainingOperations.size()-1);
		} 
		return readyFlights;
	}
	
	
	public void sendToWaitingQueue(Flight flight) throws Exception {
		flight.remainingOperations.remove(flight.remainingOperations.size()-1);
		this.AccWaitingsList.addToQueue(this.AccReadyQueue.popFromQueue());
	}
	
	public void addFlightToAllFlights(Flight f) {
		this.allFlightsForAcc.add(f);
		this.incompleteFlights++;
	}
	

	
	public void terminateFlight() throws Exception {
		this.AccReadyQueue.popFromQueue();
		this.incompleteFlights--;
	}
	
	public String paddedVersion(int num) {
		return String.format("%03d", num);
	}
	

	
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
		ATC atcOfThatAirport = new ATC(airportToBeAddded.code+paddedVersion(slot), this);
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
		FileWriter test_writer = new FileWriter("C:\\\\Users\\\\Hüseyin Karataþ\\\\Desktop\\\\project3_test.txt");
		while (this.incompleteFlights>0) {
		
			
			
			ArrayList<Flight> fromWaiting  =extractedFromWaitingQueue();
			ArrayList<Flight> newFlights = extractedNewFlights();

			for (ATC atc : allATCs) {
				atc.runTime();
			}			
			for (Flight flight : newFlights) {
				candidatesForReadyQueue.add(flight);
				flight.isNew=false;
			}
			for (Flight flight : fromWaiting) {
				candidatesForReadyQueue.add(flight);
			}
			candidatesForReadyQueue.sort(fc);
			
			if(notCompletedInSlice()!=null) {
				this.candidatesForReadyQueue.add(this.candidatesForReadyQueue.size(),notCompletedInSlice());
				this.AccReadyQueue.popFromQueue();
			}
			
			
			
			if(candidatesForReadyQueue.size()>0) {
				for (int i = 0; i < candidatesForReadyQueue.size(); i++) {
					
					this.AccReadyQueue.addToQueue(candidatesForReadyQueue.get(i));
					candidatesForReadyQueue.get(i).getCurrentOperation().spentTime=0;
				}
			}
			
			this.candidatesForReadyQueue.clear();
		


	
			


					
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
//						currentFlight.remainingOperations.remove(currentFlight.remainingOperations.size()-1);
						this.sendToWaitingQueue(currentFlight);
					}
					else if(currentFlight.getCurrentOperation().operationNumber==21) {
						terminateFlight();
						if(incompleteFlights==0) {
							break;
						}
					}
					
				}
				
			}
		

	

			TIMELINE++;
			
		

			
			if(this.AccWaitingsList.getSize()>0) {
				for (Flight waitingFlight : this.AccWaitingsList.getElements()) {
					if (waitingFlight.getCurrentOperation().remainingTime>0) {						
						waitingFlight.getCurrentOperation().remainingTime--;
					}
				}
			}


			
			Flight current = this.AccReadyQueue.peek();
			if(current!=null) {		
				if(current.getCurrentOperation().remainingTime>0) {					
					current.getCurrentOperation().remainingTime--;
					current.getCurrentOperation().spentTime++;
				}
			}

		}
		test_writer.close();

		
		
			
			
			
		}
		
	
		
		
	}
	
	
	





