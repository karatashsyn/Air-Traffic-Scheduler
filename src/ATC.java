import java.util.ArrayList;
import java.util.Iterator;

public class ATC {
	public String code;
	private ReadyQueue atcReadyQueue = new ReadyQueue();
	private WaitingsList atcWaitingsList = new WaitingsList();
	public ArrayList<Flight> candidatesForReadyQueue = new ArrayList<>();
	public ACC parentAcc;
	
	public ATC (String atcCode, ACC parentAcc) {
		this.code= atcCode;
		this.parentAcc = parentAcc;
	}
	
	
	
	
	public ReadyQueue getReadyQueue() {
		return this.atcReadyQueue;
	}
	
	public WaitingsList getWaitingQueue() {
		return this.atcWaitingsList;
		
	}
	
	public void sendToWaitingQueue() throws Exception{
		Flight flightToBeSended = this.atcReadyQueue.popFromQueue();
		flightToBeSended.remainingOperations.remove(flightToBeSended.remainingOperations.size()-1);
		this.atcWaitingsList.addToQueue(flightToBeSended);
	}
	
	public void extractFromWaiting() {
		ArrayList<Flight> readyFlights = this.atcWaitingsList.extractReadyFlights();
		for (Flight flight : readyFlights) {
			flight.remainingOperations.remove(flight.remainingOperations.size()-1);
			this.candidatesForReadyQueue.add(flight);
		}
	}
	
	
	public void runTime() throws Exception {
		if(this.atcReadyQueue.getSize()==0 && this.atcWaitingsList.getSize()==0 && candidatesForReadyQueue.size()==0) {
			return;
		}
	
		

				
		

		FlightComparator fc = new FlightComparator();
		extractFromWaiting();
		
		
		if(candidatesForReadyQueue.size()>0) {
			this.candidatesForReadyQueue.sort(fc);
			for (Flight flight : candidatesForReadyQueue) {
				this.atcReadyQueue.addToQueue(flight);
			}
		}
		
		
		candidatesForReadyQueue.clear();
		
		for (Flight flight : atcWaitingsList.getElements()) {
			if(flight.getCurrentOperation().remainingTime>0) {
					flight.getCurrentOperation().remainingTime--;
			}
		}
		Flight current = this.atcReadyQueue.peek();
		if(current!=null) {
			if(current.getCurrentOperation().remainingTime>0) {				
				current.getCurrentOperation().remainingTime--;
			}
		}
		
		
		if(this.atcReadyQueue.getSize()>0) {
			Flight currentFlight = this.atcReadyQueue.peek();
			Operation currentOperation = currentFlight.getCurrentOperation();
			
			if(currentOperation.remainingTime==0) {
				
				if(currentOperation.operationNumber==10 || currentOperation.operationNumber==20) {
					currentFlight.remainingOperations.remove(currentFlight.remainingOperations.size()-1);
					this.parentAcc.candidatesForReadyQueue.add(this.atcReadyQueue.popFromQueue());
				}
				
				
				
				else {
					
					sendToWaitingQueue();
				}
				
				
				
			}
			else {
				
			}
		}
		

		
		
	

		
	}

	
	
}
