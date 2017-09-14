
public class HammingDistance {
	
	public static int Hammingdist(String s1, String s2, boolean ignoreCase) throws DifferentLengthException  {
		
		if (s1.length()!=s2.length()) {
			throw new DifferentLengthException("Different Lengths when calculating Hamming Distance!");
		}
		
		
		int ans = 0;
		
		if (ignoreCase) {
			s1 = s1.toUpperCase();
			s2 = s2.toUpperCase();
		}
		
		for (int i = 0; i < s1.length(); i++) {
			if(s1.charAt(i)==s2.charAt(i)) ;
			else ans++;
		}
		
		return ans;
	}
	
}
