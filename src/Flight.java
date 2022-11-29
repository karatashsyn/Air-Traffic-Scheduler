import java.util.ArrayList;

public class Flight {
	public int admissionTime;
	public String type = "NEW";
	public String code;
	public ArrayList<Operation> remainingOperations;
	public Airport departureAirport;
	public Airport arrivalAirport;
	public boolean isNew;
	
	public Flight(String c, int admissionTime) {
		this.code = c;
		this.admissionTime=admissionTime;
	}
	
	
	
	
	public Flight(String c, int admissionTime, ArrayList<Operation> operationsList, Airport departure, Airport arrival) {
		this.code = c;
		this.admissionTime=admissionTime;
		this.remainingOperations=operationsList;
		this.departureAirport = departure;
		this.arrivalAirport = arrival;
		this.isNew = true;
	}
	
	
	public Operation getCurrentOperation() {
		return this.remainingOperations.get(this.remainingOperations.size()-1);
	}
}
