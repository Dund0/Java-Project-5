//Daniel Sanandaj
//YoungJin Seo
//Tony Tong
import java.util.StringTokenizer;

public class Node {
	String word;
	boolean[] files;
	Node left;
	Node right;
	Node parent;
	int height;
	
	//empty Node
	public Node() {}
	
	//Node object
	public Node(String setWord, boolean[] setFiles){//boolean setFile1, boolean setFile2, boolean setFile3, boolean setFile4){
		word = setWord;
		files = setFiles;
		left = null;
		right = null;
		height = 0;
	}
	
	//method to create new node from string and current input file
	public static Node createNode(String word, int file) {
		boolean[] fileArray = new boolean[4];
		fileArray[file] = true;
		return new Node(word, fileArray);
	}
	
	//method to create new node from string and given file list
	public static Node createNode(String word, String files) {
		StringTokenizer fileList = new StringTokenizer(files, ",", false);
		boolean[] fileArray = new boolean[4];
			 
		String token;
			for(int i = 0; i < fileArray.length; i++) {
				while(fileList.hasMoreTokens()) {
					token = fileList.nextToken();
					switch(token) {
					case "1":
					 	fileArray[0] = true;
					 	break;
				 	case "2":
					 	fileArray[1] = true;
					 	break;
				 	case "3":
					 	fileArray[2] = true;
					 	break;
				 	case "4":
					 	fileArray[3] = true;
				 		break;
				 	default:
					 	break;
				 	}
				 }
			}
		return new Node(word, fileArray);
	}
	
	//make toString
	public String toString() {
		String output = "";
		for (int i = 0; i < this.files.length; i++) {
			if(this.files[i])
				output = output + (i+1) + ", ";
		}
		output = output.substring(0, output.length() - 2);
		
		if(output == "")
			return "Word not found";
		
		return output;
	}
}
