
@SuppressWarnings("serial")
public class DifferentLengthException extends Exception {
	
	private static int Cumulative_number = 0;
	
	public DifferentLengthException(String message) {
		Cumulative_number ++;
	}
	
	public int getCumulativeNumber() {
		return Cumulative_number;
	}
}
