
public class Operation {
	String operationType;
	String controllerType;
	int remainingTime;
	int operationNumber;
	
	
	public Operation(int operationNumber, String ot,String ct, int d) throws Exception {
		
		if ((ot.compareTo("WAITING")!=0) && (ot.compareTo("RUNNING")!=0)) {
			throw new Exception("You should enter on of the WAITING or RUNNING string as operation type.");
		}
		
		if ((ct.compareTo("ACC")!=0) && (ct.compareTo("ATC")!=0)) {
			throw new Exception("You should enter on of the ACC or ATC string as controller type.");
		}
		
		this.operationType = ot;
		this.controllerType=ct;
		this.remainingTime=d;
		this.operationNumber=operationNumber;
	}
}
