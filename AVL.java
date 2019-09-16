//Daniel Sanandaj
//YoungJin Seo
//Tony Tong
import java.lang.*;
import java.util.StringTokenizer;

public class AVL implements DataStrucInterface {

	private Node current;
	private Node root;
	private Node parent;
	private Node inserted;
	private boolean insertLeft, insertRight;
	private boolean check;
	
	public AVL() {} //create default empty tree
	
	//insert from user input
	@Override
	public boolean insert(String word, String files) {
		Node newWord = Node.createNode(word, files);
		inserted = newWord;
		if(root == null) {
			root = newWord;
			updateHeight(newWord);
			return true;
		}
		
		current = root;
		parent = null;
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
		
		if(root.word.compareTo(word) > 0) {
			insertLeft = true;
		} else {
			insertRight = true;
		}
		
		if(parent.word.compareTo(newWord.word) > 0) {
			parent.left = newWord;
			updateHeight(newWord);
			return true;
		} else {
			parent.right = newWord;
			updateHeight(newWord);
			return true;
		}
	}

	//update height from input and check balance
	private void updateHeight(Node node) {
		if(node == root) {
			if(node.left == null && node.right == null) {
				node.height = 0;
				return;
			} else {
				node.height = Math.max(node.left.height, node.right.height) + 1;
			}
		}
		
		parent = findParent(node);
		if(parent.height == 0) {
			parent.height++;
			node.height = 0;
			balanceTraversal(root);
			return;
		} else if(parent.right == node) {
			if(parent.left.height == 0) {
				node.height = 0;
				balanceTraversal(root);
				return;
			}
		} else if(parent.left == node){
			if(parent.right.height == 0) {
				node.height = 0;
				balanceTraversal(root);
				return;
			}
		}
		
		current = root;
		while(current != null) {
			if(current.word.compareTo(node.word) < 0) {
				parent = current;
				parent.height++;
				current = current.right;
			} 
			else if (current.word.compareTo(node.word) > 0) {
				parent = current;
				parent.height++;
				current = current.left;
			} 
			else if(current.left == null && current.right == null) {
				current.height = 0;
				balanceTraversal(root);
				check = false;
				return;
			}
		}
	}

	//set new heights after rotations
	private void setHeight(Node node){
		if(node == root) {
			node.height = Math.max(node.right.height, node.left.height) + 1;
			return;
		} else if(node.left == null && node.right == null) {
			node.height = 0;
		} 
		else if (node.left == null){
			node.height = node.right.height + 1;
		}
		else if (node.right == null){
			node.height = node.left.height + 1;
		} else {
			node.height = Math.max(node.right.height, node.left.height) + 1;
		}
	}
	
	// visit each node and set the new height for each
	private void resetHeight(Node node) {
		if (node != null) {
			node.height = 0;
			resetHeight(node.left); 
			resetHeight(node.right);
            setHeight(node);
        } 
		
	}

	//visit each node in preorder to check balance
	private void balanceTraversal(Node node) {
		if (node != null) {
			balanceTraversal(node.left); 
			balanceTraversal(node.right);
            checkBalance(node);
        } 
	}
	
	//check balance of each node and rotate if necessary
	private void checkBalance(Node node) {
		switch (getBalance(node)) {
        case -2:
          if (getBalance(node.left) <= 0) {
            leftRotate(node, findParent(node)); // Perform LL rotation
            resetHeight(root);
          }
          else {
            leftRightRotate(node, findParent(node)); // Perform LR rotation
            resetHeight(root);
          }
          break;
        case +2:
          if (getBalance(node.right) >= 0) {
            rightRotate(node, findParent(node)); // Perform RR rotation
            resetHeight(root);
          }
          else {
            rightLeftRotate(node, findParent(node)); // Perform RL rotation
            resetHeight(root);
          }
      }
	}
	
	//rotate left
	private void leftRotate(Node x, Node nodeParent) {
		Node y = x.left; // A is left-heavy and B is left-heavy

	    if (x == root) {
	      root = y;
	    }
	    else {
	      if (nodeParent.left == x) {
	    	  nodeParent.left = y;
	      }
	      else {
	    	  nodeParent.right = y;
	      }
	    }

	    x.left = y.right; // Make T2 the left subtree of A
	    y.right = x; // Make A the right child of B 
	}

	//rotate right
	private void rightRotate(Node y, Node nodeParent) {
		Node x = y.right;

	    if (y == root) {
	      root = x;
	    }
	    else {
	      if (nodeParent.right == y) {
	    	  nodeParent.right = x;
	      }
	      else {
	    	  nodeParent.left = x;
	      }
	    }

	    y.right = x.left;
	    x.left = y; 
	}

	//rotate left then right
	private void leftRightRotate(Node x, Node nodeParent) {
		Node y = x.left; // A is left-heavy
	    Node z = y.right; // B is right-heavy

	    if (x == root) {
	      root = z;
	    }
	    else {
	      if (nodeParent.left == x) {
	    	  nodeParent.left = z;
	      }
	      else {
	    	  nodeParent.right = z;
	      }
	    }

	    x.left = z.right; // Make T3 the left subtree of A
	    y.right = z.left; // Make T2 the right subtree of B
	    z.left = y;
	    z.right = x;
	}

	//rotate right then left
	private void rightLeftRotate(Node x, Node nodeParent) {
		Node y = x.right; // A is left-heavy
	    Node z = y.left; // B is right-heavy

	    if (x == root) {
	      root = z;
	    }
	    else {
	      if (nodeParent.right == x) {
	    	  nodeParent.right = z;
	      }
	      else {
	    	  nodeParent.left = z;
	      }
	    }

	    x.right = z.right; // Make T3 the left subtree of A
	    y.left = z.right; // Make T2 the right subtree of B
	    z.left = x;
	    z.right = y;
	}
	
	//returns balance of current node
	private int getBalance(Node node) {
		if(node.left == null && node.right == null)
			return 0;
		else if(node.left == null)
			return node.right.height - -1;
		else if(node.right == null)
			return -1 - node.left.height;
		else
			return node.right.height - node.left.height;
	}
	
	//insert word from file
	@Override
	public void insert(Node node) {
		inserted = node;
		
		if(root == null) {
			root = node;
			updateHeight(node);
			return;
		}
		
		current = root;
		parent = null;
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
		
		if(root.word.compareTo(node.word) > 0) {
			insertLeft = true;
		} else {
			insertRight = true;
		}
		
		if(parent.word.compareTo(node.word) > 0) {
			parent.left = node;
			updateHeight(node);
			return;
		} else {
			parent.right = node;
			updateHeight(node);
			return;
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
	
	//return true if word is in tree
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

	//delete from the tree 
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
	
	//remove node if all files false
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
		
		resetHeight(root);
		balanceTraversal(root);
	}

	//find and return a node
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
			else if(current.word.compareTo(node.word) < 0) {
				current = current.right;
			} 
			else if (current.word.compareTo(node.word) > 0) {
				current = current.left;
			}
			else {
				return current;
			}
		}
		
		return null;
	}
}
