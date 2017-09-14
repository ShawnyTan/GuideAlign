
public class GuideAlign {
	public static void main(String[] args) {
		
		// process arguments
		// 1st arg: reference file
		// 2nd arg: fastq file
		// 3rd arg: output file
		// 4th arg: start position of guideRNA
		// 5th arg: end position of guideRNA
		// 6th arg: hamming distance allowed
		
		String ref_file = args[0];
		String fastq_file = args[1];
		String output_file = args[2];
		int start = Integer.parseInt(args[3]), end = Integer.parseInt(args[4]);
		int max_mis = Integer.parseInt(args[5]);
		
		Reference ref = new Reference(ref_file);
		try {
			ref.Align(fastq_file, output_file, start, end, max_mis);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
}
