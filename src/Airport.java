


public class Airport {
	public String code;
	public int slotNumber;
	private ATC Atc;
	
	public Airport(String code){
		this.code = code;
	}
	public ATC getAtc () {
		return this.Atc;
	}
	
	public void setAtc(ATC atc) {
		this.Atc = atc;
	}
}
