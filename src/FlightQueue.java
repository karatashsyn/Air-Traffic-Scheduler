import java.util.ArrayList;

public class FlightQueue {
	private ArrayList<Flight> queue;
	
	
	public void sendBackInQueue() throws Exception {
		if(this.queue.size()<1) {throw new Exception("SEND BACK IN QUEUE ERROR: There is no element in the queue");}
		
		
		else if(this.queue.size()==1) {
			return;
		}
		else {
			int size = this.queue.size();
			Flight sendedElement = this.queue.get(size-1);
			this.queue.remove(size-1);
			this.queue.add(0, sendedElement);
		}
		
	}
	
	public Flight popFromQueue() throws Exception {
		if(this.queue.size()<1) {throw new Exception("POP FROM QUEUE ERROR: There is no element in the queue");}
		
		
		else {
			Flight poppedElement = this.queue.get(this.queue.size()-1);
			this.queue.remove(this.queue.size()-1);
			return poppedElement;
		}
	}
	
	public Flight peek() throws Exception{
		if(this.queue.size()<1) {throw new Exception("PEEK ERROR: There is no element in the queue");}
		
		else {
			return this.queue.get(this.queue.size()-1);
		}
	}
	
//	public void addNewFlights(ArrayList<Flight> flights) throws Exception {
//		if(flights.size()<1) {throw new Exception("ADD NEW FLIGHTS: There is no flight in the array you are trying to add");}
//		
//		else if(flights.size()==1) {
//			this.queue.add(0, flights.get(0));
//		}
//		
//		else {
//			flights.sort(flightComparator);
//		}
//		
//	}
}