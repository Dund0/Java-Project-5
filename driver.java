//Daniel Sanandaj
//YoungJin Seo
//Tony Tong
import java.io.*;
import java.util.*;

public class driver {
	public static void main (String[] args) throws IOException{
		Scanner scan = new Scanner(System.in);
		BST bst = new BST(); //create empty bst
		AVL avl = new AVL(); //create empty avl
		Splay splay = new Splay(); //create empty splay
		int i = -1; //int to hold file number
		
		//int to hold what tree is used 1 for bst 2 for avl and 3 for splay
		int tree = -1;
		
		//create array of files
		File[] inFiles = new File[] {new File("file1.txt"), new File("file2.txt"), 
				new File("file3.txt"), new File("file4.txt")};
		Scanner inputFile = null;
		
		try {
			//create string for input word
			String word;
			
			//for loop to loop through files
			for(i = 0; i < inFiles.length; i++) {
				//check if file found
				inputFile = new Scanner(inFiles[i]);
				
				//read words from file
				while(inputFile.hasNext()) {
					word = inputFile.nextLine();
					//if word is in any tree
					//update the files its in
					//else insert the word into all the trees
					
					//remove spaces
					if(word.charAt(word.length()-1) == ' ')
						word = word.substring(0, word.length()-1);
					
					if(bst.search(word)||avl.search(word)||splay.search(word)) {
						bst.update(word, i);
						avl.update(word, i);
						splay.update(word, i);
					} else {
						Node bstNode = Node.createNode(word, i);
						bst.insert(bstNode);
						Node avlNode = Node.createNode(word, i);
						avl.insert(avlNode);
						Node splayNode = Node.createNode(word, i);
						splay.insert(splayNode);
					}
				}
			}
		} catch (FileNotFoundException e) { //see if file is not found
	         //display error message
	         System.out.println("Data file " + i + " not found!");
	         System.exit(0);
	      }
		//menu
		String choice = "";
		
		while (choice != "quit") {
			System.out.print("\nWhat would you like to do? \n>");
	        choice = scan.nextLine();
			
			switch(choice) {
			case "select":
				tree = select();
				break;
			case "help":
				help();
				break;
			case "find":
			case "search":
				find(tree, bst, avl, splay);
				break;
			case "insert":
			case "add":
				insert(tree, bst, avl, splay);
				break;
			case "delete":
			case "remove":
				delete(tree, bst, avl, splay);
				break;
			case "quit":
			case "exit":
			case "stop":
				System.out.println("quitting");
	            System.exit(0);
			default:
	            System.out.println("not a correct command\n");
			
			}
		}
	}

	//help menu option
	private static void help() {
		System.out.println("Your options are:\nselect(select the type of tree)\nfind\ninsert\ndelete\nquit(quits the program)");
	}

	//select what type of tree
	private static int select() {
		Scanner scan = new Scanner(System.in);
		
		String choice = " ";
		while(choice != "") {
			System.out.println("\nWhat type of tree?");
			choice = scan.next();
			
			switch(choice) {
			case "BST":
			case "bst":
				return 1;
			case "AVL":
			case "avl":
				return 2;
			case "Splay":
			case "splay":
				return 3;
			default:
				System.out.println("Please enter AVL, BST, or Splay.");
			}
		}
		return -1;
		
		
	}

	//delete from the trees
	private static void delete(int DeleteTree, BST DeleteBST, AVL DeleteAVL, Splay DeleteSplay) {
		Scanner scan = new Scanner(System.in);

		if(DeleteTree != 1 && DeleteTree != 2 && DeleteTree != 3) {
			System.out.println("Tree not selected correctly!");
			return;
		}
		
		System.out.println("Word?");
		String deleteWord = scan.next();
		
		System.out.println("\nWhich file(s)?");
		String deleteFiles = scan.next();
		
		if (DeleteBST.delete(deleteWord, deleteFiles) && DeleteAVL.delete(deleteWord, deleteFiles)
				 && DeleteSplay.delete(deleteWord, deleteFiles) ) {
			System.out.println("Deletion done!");
		} else 
			System.out.println("The word cannot be found!");
		
	}

// insert into the trees
	private static void insert(int InsertTree, BST InsertBST, AVL InsertAVL, Splay InsertSplay) {
		Scanner scan = new Scanner(System.in);
		
		if(InsertTree != 1 && InsertTree != 2 && InsertTree != 3) {
			System.out.println("Tree not selected correctly!");
			return;
		}
		
		System.out.println("\nWhich file(s)?");
		String insertFiles = scan.next();
		
		System.out.println("Word?");
		String insertWord = scan.next();
		
		if(InsertBST.search(insertWord)||InsertAVL.search(insertWord)||InsertSplay.search(insertWord)) {
			InsertBST.update(insertWord, insertFiles);
			InsertAVL.update(insertWord, insertFiles);
			InsertSplay.update(insertWord, insertFiles);
			System.out.println(insertWord + " was updated!");
			return;
		}
		
		if (InsertBST.insert(insertWord, insertFiles) && InsertAVL.insert(insertWord, insertFiles)
				 && InsertSplay.insert(insertWord, insertFiles) ) {
			System.out.println("Insertion done!");
		} else 
			System.out.println("Insertion failed!");
		
	}

	//method to start find in specific tree
	private static void find(int findTree, BST findBST, AVL findAVL, Splay findSplay) {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Word?");
		String findWord = scan.next();
		
		if (findBST.find(findWord, true) == null || findAVL.find(findWord, true) == null || findSplay.find(findWord, true) == null)
			System.out.println("Word not found in any file.");
		else 
			System.out.println(findSplay.find(findWord, false));
	}
}
