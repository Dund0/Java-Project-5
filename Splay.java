//Daniel Sanandaj
//YoungJin Seo
//Tony Tong
import java.util.StringTokenizer;

public class Splay implements DataStrucInterface{

	Node root;
	Node current;
	
	public Splay() {} //create default empty tree
	
	//insert into file with string files
	@Override
	public boolean insert(String word, String files) {
		Node newWord = Node.createNode(word, files);
		
		if(root == null) {
			root = newWord;
			return true;
		}
		
		//find location
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
			newWord.parent = parent;
			splay(newWord); //splay when added
			return true;
		} else {
			parent.right = newWord;
			newWord.parent = parent;
			splay(newWord); //splay when added
			return true;
		}
	}


	//find node and return it
	@Override
	public Node find(String word, boolean splay) {
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
				if(splay)
					splay(current); //splay if using find command
				return current;
			}
		}
		return null;
	}

	//delete files from string
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
		
		//if all false remove node emtirely
		if(temp.files[0] == false && temp.files[1] == false && temp.files[2] == false  && temp.files[3] == false )
			remove(temp);
		
		return true;
	}

	//remove if all false
	private void remove(Node temp) {
		if(temp.left == null && temp.right == null) { //this is a leaf
			if(temp.parent.left == temp)
				temp.parent.left = null;
			else if(temp.parent.right == temp)
				temp.parent.right = null;
		}
		else if(temp.left == null) { //if left is null get least on right tree
			current = temp.right;
			while(current != null && current.left != null) {
				current = current.left;
			}
			Node tempCurrent = current;
			current.parent.left = tempCurrent.right;
			current.parent.left.parent = current.parent;
			tempCurrent.right = temp.right;
			tempCurrent.left = temp.left;
			if(temp == root) {
				root = tempCurrent;
				tempCurrent.parent = null;
			}
			temp = tempCurrent;
		}
		else if(temp.right == null) { //if left is null get greatest from left tree
			current = temp.left;
			while(current != null && current.right != null) {
				current = current.right;
			}
			Node tempCurrent = current;
			current.parent.right = tempCurrent.left;
			current.parent.right.parent = current.parent;
			tempCurrent.left = temp.left;
			tempCurrent.right = temp.right;
			if(temp == root) {
				root = tempCurrent;
				tempCurrent.parent = null;
			}
			temp = tempCurrent;
		} else { //defualt get lease from right tree
			current = temp.right;
			while(current != null && current.left != null) {
				current = current.left;
			}
			Node tempCurrent = current;
			current.parent.left = tempCurrent.right;
			current.parent.left.parent = current.parent;
			tempCurrent.right = temp.right;
			tempCurrent.left = temp.left;
			if(temp == root) {
				root = tempCurrent;
				tempCurrent.parent = null;
			}
			temp = tempCurrent;
		}
	}

	//return true if node is in tree
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

	//insert from files
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
			node.parent = parent;
			splay(node);
		} else {
			parent.right = node;
			node.parent = parent;
			splay(node);
		}
	}

	// update word from file
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
				splay(current);
				break;
			case("2"):
			case " 2":
				if(current.files[1] == false)
				current.files[1] = true;
				splay(current);
				break;
			case("3"):
			case " 3":
				if(current.files[2] == false)
				current.files[2] = true;
				splay(current);
				break;
			case("4"):
			case " 4":
				if(current.files[3] == false)
				current.files[3] = true;
				splay(current);
				break;
			}
		}
	}

	//find the parent of a node
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

	//splay the tree
	private void splay(Node node) {
        while (node.parent != null)
        {
            Node Parent = node.parent;
            Node GrandParent = Parent.parent;
            if (GrandParent == null)
            {
            	if (node == Parent.left)
            		rightRotate(node, Parent);
                else
                	leftRotate(node, Parent);
            } 
            else
            {
                if (node == Parent.left)
                {
                    if (Parent == GrandParent.left)
                    {
                    	rightRotate(Parent, GrandParent);
                    	rightRotate(node, Parent);
                    }
                    else 
                    {
                    	rightRotate(node, node.parent);
                    	leftRotate(node, node.parent);
                    }
                }
                else 
                {
                    if (Parent == GrandParent.left)
                    {
                    	leftRotate(node, node.parent);
                        rightRotate(node, node.parent);
                    } 
                    else 
                    {
                    	leftRotate(Parent, GrandParent);
                    	leftRotate(node, Parent);
                    }
                }
            }
        }
        root = node;
	}

	//rotate right
	private void rightRotate(Node node, Node parent) {
        if (parent.parent != null)
        {
            if (parent == parent.parent.left)
            	parent.parent.left = node;
            else 
            	parent.parent.right = node;
        }
        if (node.right != null)
        	node.right.parent = parent;
        node.parent = parent.parent;
        parent.parent = node;
        parent.left = node.right;
        node.right = parent;
	}

	//rotate left
	private void leftRotate(Node node, Node parent) {
        if (parent.parent != null)
        {
            if (parent == parent.parent.left)
            	parent.parent.left = node;
            else
            	parent.parent.right = node;
        }
        if (node.left != null)
        	node.left.parent = parent;
        node.parent = parent.parent;
        parent.parent = node;
        parent.right = node.left;
        node.left = parent;
	}
}
