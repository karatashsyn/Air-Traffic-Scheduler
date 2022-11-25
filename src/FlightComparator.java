import java.util.Comparator;

public class FlightComparator implements Comparator<Flight>{

	@Override
	public int compare (Flight o1, Flight o2) {
		if(o1.code.compareTo(o2.code)<0) {
			return -1;
		}
		else if (o1.code.compareTo(o2.code)==0) {
			System.out.println("COMPAER ERROR: TWO FLIGHTS'CODE YOU ARE TRYING TO COMPARE ARE SAME.");
		}
		else {
			return 1;
		}
		return 0;
	}
}
