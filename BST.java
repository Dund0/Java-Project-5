//Daniel Sanandaj
//YoungJin Seo
//Tony Tong
import java.util.*;
public class BST implements DataStrucInterface{

	Node root;
	Node current;
	
	public BST() {} //create default empty tree
	
	//insert word from user input
	@Override
	public boolean insert(String word, String files) {
		Node newWord = Node.createNode(word, files);
		
		if(root == null) {
			root = newWord;
			return true;
		}
		
		current = root;
		Node parent = null;
		while(current != null) {
			if(current.word.compareTo(newWord.word) < 0) {
				parent = current;
				current = current.right;
			} 
			else if (current.word.compareTo(newWord.word) > 0) {
				parent = current;
				current = current.left;
			} 
			else {
				return false;
			}
		}
		if(parent.word.compareTo(newWord.word) > 0) {
			parent.left = newWord;
			return true;
		} else {
			parent.right = newWord;
			return true;
		}
	}

	//find word and return the node
	@Override
	public Node find(String word, boolean b) {
		if(root.word.compareTo(word) == 0) {
			return root;
		}
		
		if(root == null) {
			return null;
		}
		
		current = root;
		while(current != null) {
			if(current.word.compareTo(word) < 0) {
				current = current.right;
			} 
			else if (current.word.compareTo(word) > 0) {
				current = current.left;
			} 
			else {
				return current;
			}
		}
		return null;
	}

	//delete files from word
	@Override
	public boolean delete(String word, String files) {
		if(search(word) == false)
			return false;
		StringTokenizer fileList = new StringTokenizer(files,",",false);
		String token;
		current = find(word, false);
		while(fileList.hasMoreTokens()) {
			token = fileList.nextToken();
			switch(token) {
			case("1"):
			case "1 ":
				if(current.files[0] == false)
					return false;
				current.files[0] = false;
				break;
			case("2"):
			case "2 ":
				if(current.files[1] == false)
					return false;
				current.files[1] = false;
				break;
			case("3"):
			case "3 ":
				if(current.files[2] == false)
					return false;
				current.files[2] = false;
				break;
			case("4"):
			case "4 ":
				if(current.files[3] == false)
					return false;
				current.files[3] = false;
				break;
			}
		}
		
		Node temp = find(word, false);
		
		if(temp.files[0] == false && temp.files[1] == false && temp.files[2] == false  && temp.files[3] == false )
			remove(temp);
		
		return true;
	}

	//if all files false remove node and replace
	private void remove(Node temp) {
		if(temp.left == null && temp.right == null) { //this is a leaf
			Node tempParent = findParent(temp);
			if(tempParent.left == temp)
				tempParent.left = null;
			else if(tempParent.right == temp)
				tempParent.right = null;
		}
		else if(temp.left == null) {
			current = temp.right;
			while(current != null && current.left != null) {
				current = current.left;
			}
			Node tempCurrent = current;
			findParent(current).left = tempCurrent.right;
			tempCurrent.right = temp.right;
			tempCurrent.left = temp.left;
			if(temp == root)
				root = tempCurrent;
			temp = tempCurrent;
		}
		else if(temp.right == null) {
			current = temp.left;
			while(current != null && current.right != null) {
				current = current.right;
			}
			Node tempCurrent = current;
			findParent(current).right = tempCurrent.left;
			tempCurrent.left = temp.left;
			tempCurrent.right = temp.right;
			if(temp == root)
				root = tempCurrent;
			temp = tempCurrent;
		} else {
			current = temp.right;
			while(current != null && current.left != null) {
				current = current.left;
			}
			Node tempCurrent = current;
			findParent(current).left = tempCurrent.right;
			tempCurrent.right = temp.right;
			tempCurrent.left = temp.left;
			if(temp == root)
				root = tempCurrent;
			temp = tempCurrent;
		}
	}

	//if word is in tree return true
	@Override
	public boolean search(String word) {
		if(root == null) {
			return false;
		}
		
		if(root.word.compareTo(word) == 0) {
			return true;
		}
		
		current = root;
		while(current != null) {
			if(current.word.compareTo(word) < 0) {
				current = current.right;
			} 
			else if (current.word.compareTo(word) > 0) {
				current = current.left;
			} 
			else {
				return true;
			}
		}
		return false;
	}

	//insert word from file
	@Override
	public void insert(Node node) {
		if(root == null) {
			root = node;
			return;
		}
		
		current = root;
		Node parent = null;
		while(current != null) {
			if(current.word.compareTo(node.word) < 0) {
				parent = current;
				current = current.right;
			} 
			else if (current.word.compareTo(node.word) > 0) {
				parent = current;
				current = current.left;
			} 
			else {
				return;
			}
		}
		if(parent.word.compareTo(node.word) > 0) {
			parent.left = node;
		} else {
			parent.right = node;
		}
	}

	//update word from file
	@Override
	public void update(String word, int file) {
		Node updateNode = find(word, true);
		updateNode.files[file] = true;
		
	}

	//update word from user input
	public void update(String word, String files) {
		StringTokenizer fileList = new StringTokenizer(files,",",false);
		String token;
		current = find(word, false);
		while(fileList.hasMoreTokens()) {
			token = fileList.nextToken();
			switch(token) {
			case("1"):
			case " 1":
				if(current.files[0] == false)
				current.files[0] = true;
				break;
			case("2"):
			case " 2":
				if(current.files[1] == false)
				current.files[1] = true;
				break;
			case("3"):
			case " 3":
				if(current.files[2] == false)
				current.files[2] = true;
				break;
			case("4"):
			case " 4":
				if(current.files[3] == false)
				current.files[3] = true;
				break;
			}
		}
	}
	
	//find parent of node
	public Node findParent(Node node) {
		if(root.word.compareTo(node.word) == 0) {
			return root;
		}
		
		if(root == null) {
			return null;
		}
		
		current = root;
		while(current != null) {
			if(current.left == node || current.right == node) {
				return current;
			} 
			else if (current.word.compareTo(node.word) > 0) {
				current = current.left;
			} 
			else if(current.word.compareTo(node.word) < 0) {
				current = current.right;
			}
			else {
				return current;
			}
		}
		return null;
	}
}
