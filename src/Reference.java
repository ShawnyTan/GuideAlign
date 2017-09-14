import java.io.*;
import java.util.*;

public class Reference {
	
	private HashMap<String,String> ref;
	public String file_name;
	
	public Reference(String file) {
		ref = new HashMap<String,String>();
		try{
			file_name = file;
			Scanner scan = new Scanner(new File(file));
		    while(scan.hasNextLine()){
		        String line = scan.nextLine();
		        String[] line_split = line.split("\\t");
		        ref.put(line_split[0],line_split[1]);
		    }
		    scan.close();
		    System.out.println(ref.size() + " reference sequences loaded.");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage()); // handle exception
		}
	}
	
	public HashMap<String,String> getRef() {
		return ref;
	}
	
	public void Align(String fastq_file, String output_file, int start, int end, int max_mis) throws Exception {
		String suffix = fastq_file.substring(fastq_file.lastIndexOf('.')+1);
		if(!suffix.equalsIgnoreCase("fastq")) 
			throw new Exception("Input file is not fastq!");
		
		int line_num = 0;
		int no_match = 0, unique_match = 0, multi_match = 0;
		HashMap<String,Integer> count = new HashMap<String,Integer>(ref.size());
		
		try {
			Scanner scan = new Scanner(new File(fastq_file));
			
			while(scan.hasNextLine()){
		        String line = scan.nextLine();
		        line_num++;
		        if(line_num%4==2) {
		        	int align = 0;
		        	for (String guide : ref.keySet()) {
		        		String ref_s = ref.get(guide);
		        		
		        		int dist = Integer.MAX_VALUE;
		        		try {
		        			dist = HammingDistance.Hammingdist(line.substring(start,end+1),ref_s,true);
		        		}
		        		catch(Exception e) {
		        			e.printStackTrace();
		        			System.err.println(e.getMessage()); // handle exception
		        		}
		        		
		        		if(dist<=max_mis) {
		        			align++;
		        			if(count.containsKey(guide)) {
		        				if (count.replace(guide, count.get(guide)+1)==null) 
		        					throw new Exception(guide + " count+1 failed!");
		        			}
		        			else count.put(guide, 1);	
		        		}
		        	}
		        	
		        	if (align == 0) no_match++;
	        		else if (align == 1) unique_match++;
	        		else multi_match++;
		        }
		        else continue;
		    }
			scan.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		//output to file
	    try {
	    	FileWriter fileWriter = new FileWriter(output_file);
	        PrintWriter printWriter = new PrintWriter(fileWriter);
	        printWriter.println("Fastq file: " + fastq_file);
	        printWriter.println("Reference file: " + file_name);
	        printWriter.println("Hamming distance allowed: " + max_mis);
	        printWriter.println();
	        printWriter.println("Alignment result:");
	        printWriter.println("No match: " + no_match + "; Unique match: " + unique_match + "; Multiple match: " + multi_match +";");
	        for(String guide : count.keySet()) {
	        	printWriter.println(guide + "\t" + count.get(guide));
	        }
	        printWriter.close();
	    }
	    catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage()); // handle exception
		}
	}
}
