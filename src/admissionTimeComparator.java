
import java.util.Comparator;
public class admissionTimeComparator implements Comparator<Flight> {

		@Override
		public int compare (Flight o1, Flight o2) {
			if(o1.admissionTime<o2.admissionTime) {
				return -1;
			}
			else if (o1.admissionTime>o2.admissionTime) {
				return 1;
			}
			return 0;
		}
}


