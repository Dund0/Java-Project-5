//Daniel Sanandaj
//YoungJin Seo
//Tony Tong

public interface DataStrucInterface {
	public boolean insert(String word, String files); //insert a node manually and create a new node
	public void insert(Node node); // insert a node into the tree
	public void update(String word, int file); //updates what file a word is in
	public boolean search(String word); //find a word
	public boolean delete(String word, String files); //delete word from specified files
	public Node find(String word, boolean b); //find what files word is in
	public void update(String word, String files); //update file from insert command
	public Node findParent(Node node); //find and return parent node
}
