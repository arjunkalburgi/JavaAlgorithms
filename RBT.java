
public class RBT <Key extends Comparable<Key>, Value> {
	
	private enum Color {RED, BLACK};
	
	final private Node EMPTY = new Empty();
	
	private static final int LESS    = -1;
	private static final int GREATER = +1;
	
	private Node root;
	
	public void rotate_left(Node p){
		Node n = p.right;
//		Node saved_p=p;
//		Node saved_left_n=n.left;
		if (p == root){
			root = n;
		}
		p.right = n.left;
		n.left.parent = p;
		n.left = p;
		n.parent = p.parent;
		p.parent = n;
		if (n.parent != EMPTY){
			if (n.parent.left == p){
				n.parent.left = n;
			}
			else if (n.parent.right == p){
				n.parent.right = n;
			}
		}
//		p=n; 
//		n.left=saved_p;
//		saved_p.right=saved_left_n;
	}
	
	public void rotate_right(Node p){
		Node n = p.left;
//		Node saved_p=p;
//		Node saved_right_n=n.right;
		if (p == root){
			root = n;
		}
		p.left = n.right;
		n.right.parent = p;
		n.right = p;
		n.parent = p.parent;
		p.parent = n;
		if (n.parent != EMPTY){
			if (n.parent.left == p){
				n.parent.left = n;
			}
			else if (n.parent.right == p){
				n.parent.right = n;
			}
		}
//		p=n; 
//		n.right=saved_p;
//		saved_p.left=saved_right_n;
	}
	
	public Node grandparent(Node n)
	{
		if ((n != EMPTY) && (n.parent != EMPTY))
			return n.parent.parent;
			else
				return EMPTY;
	}

	public Node uncle(Node n)
	{
		Node g = grandparent(n);
		if (g == EMPTY)
			return EMPTY; // No grandparent means no uncle
		if (n.parent == g.left)
			return g.right;
			else
				return g.left;
	}

	void insert_case1(Node n)
	{
		if (n.parent == EMPTY){
			//System.out.println("huh");
			n.color = Color.BLACK;
		}	
		else{
			insert_case2(n);
		}
	}

	void insert_case2(Node n)
	{
		if (n.parent.color == Color.BLACK)
			return; 
		else
			insert_case3(n);
	}

	void insert_case3(Node n)
	{
		Node u = uncle(n), g;

		if ((u != EMPTY) && (u.color == Color.RED)) {
			n.parent.color = Color.BLACK;
			u.color = Color.BLACK;
			g = grandparent(n);
			g.color = Color.RED;
			insert_case1(g);
		} else {
			insert_case4(n);
		}
	}

	void insert_case4(Node n)
	{
		Node g = grandparent(n);

		if ((n == n.parent.right) && (n.parent == g.left)) {
			rotate_left(n.parent);

			n = n.left;

		} else if ((n == n.parent.left) && (n.parent == g.right)) {
			rotate_right(n.parent);

			n = n.right; 
		}
		insert_case5(n);
	}

	void insert_case5(Node n)
	{
		Node g = grandparent(n);

		n.parent.color = Color.BLACK;
		g.color = Color.RED;
		if (n == n.parent.left)
			rotate_right(g);
		else
			rotate_left(g);
	}

	public Node sibling(Node n)
	{
		if (n == n.parent.left)
			return n.parent.right;
			else
				return n.parent.left;
	}

	boolean is_leaf(Node n){
		if(n == EMPTY){
			return true;
		}
		return false;
	}

	void replace_Node(Node n, Node child){
		n = child;
		if (n == root){
			root = child;
		}
	}
	
	void delete_one_child(Node n)
	{
		
		Node child = is_leaf(n.right) ? n.left : n.right;

		replace_Node(n, child);
		if (n.color == Color.BLACK) {
			if (child.color == Color.RED)
				child.color = Color.BLACK;
				else
					delete_case1(child);
		}
	}

	void delete_case1(Node n)
	{
		if (n.parent != EMPTY)
			delete_case2(n);
	}

	void delete_case2(Node n)
	{
		Node s = sibling(n);

		if (s.color == Color.RED) {
			n.parent.color = Color.RED;
			s.color = Color.BLACK;
			if (n == n.parent.left)
				rotate_left(n.parent);
			else
				rotate_right(n.parent);
		}
		delete_case3(n);
	}

	void delete_case3(Node n)
	{
		Node s = sibling(n);
		if ((n.parent.color == Color.BLACK) &&
				(s.color == Color.BLACK) &&
				(s.left.color == Color.BLACK) &&
				(s.right.color == Color.BLACK)) {
			s.color = Color.RED;
			delete_case1(n.parent);
		} else
			delete_case4(n);
	}

	void delete_case4(Node n)
	{
		Node s = sibling(n);

		if ((n.parent.color == Color.RED) &&
				(s.color == Color.BLACK) &&
				(s.left.color == Color.BLACK) &&
				(s.right.color == Color.BLACK)) {
			s.color = Color.RED;
			n.parent.color = Color.BLACK;
		} else
			delete_case5(n);
	}

	void delete_case5(Node n)
	{
		Node s = sibling(n);

		
		if  (s.color == Color.BLACK) { 
			if ((n == n.parent.left) &&
					(s.right.color == Color.BLACK) &&
					(s.left.color == Color.RED)) { 
				s.color = Color.RED;
				s.left.color = Color.BLACK;
				rotate_right(s);
			} else if ((n == n.parent.right) &&
					(s.left.color == Color.BLACK) &&
					(s.right.color == Color.RED)) {
				s.color = Color.RED;
				s.right.color = Color.BLACK;
				rotate_left(s);
			}
		}
		delete_case6(n);
	}

	void delete_case6(Node n)
	{
		Node s = sibling(n);
		
		s.color = n.parent.color;
		n.parent.color = Color.BLACK;

		if (n == n.parent.left) {
			s.right.color = Color.BLACK;
			rotate_left(n.parent);
		} else {
			s.left.color = Color.BLACK;
			rotate_right(n.parent);
		}
	}
	
	public String getNodeString(Node node) {
		String str = "";
		if (node.left != EMPTY){
			str += getNodeString(node.left);
		}
			
		str += node.value.toString().trim() + "-(" + (node.color == Color.RED ? "R" : "B") + ") ";
		if (node.right != EMPTY){
			str += getNodeString(node.right);
		}
		return str;
	}
	
	private class Node{
		public Key key; // data stored at this node
		public Value value;
		public Node left; // reference to left subtree
		public Node right; // reference to right subtree
		public Node parent;
		public Color color;

		protected Node() {
			assert EMPTY == null;
		}

		
		// Constructs a leaf node with the given data.
		public Node(Key key, Value value) {
			this(key, value, EMPTY, EMPTY, EMPTY, Color.RED);
		}

		// Constructs a branch node with the given data and links.
		public Node(Key key, Value value, Node left, Node right, Node parent, Color color) {
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
			this.color = color;
		}

		public void replaceWith(Node replacement) {
			if (parent == EMPTY)
				return;
			if (this == parent.left)
				parent.setLeftChild(replacement);
			else
				parent.setRightChild(replacement);
		}

		public void setLeftChild(Node child) {
			left = child;
			if (child != EMPTY) {
				child.parent = this;
			}
		}

		public void setRightChild(Node child) {
			right = child;
			if (child != EMPTY) {
				child.parent = this;
			}
		}
		
		public Node getNode(Key k) {
			switch (k.compareTo(key)) {
			case LESS:
				return left.getNode(k);

			case GREATER:
				return right.getNode(k);

			default: // EQUAL
			return this;
			}
		}
	}

	/** The empty node used at leaves */
	private class Empty extends Node {

		public Empty() {
			color = Color.BLACK;
			assert EMPTY == null : "Should only make one empty node instance!";
		}

		public Node getNode(Key key) {
			return EMPTY;
		}
	}

	public void simpleInsert(Node inserted, Node compared, String position, Node lastNode){
		if (compared == EMPTY){
			if(lastNode != EMPTY){
				if(position == "left"){
					lastNode.left = inserted;
				}
				else if (position == "right"){
					lastNode.right = inserted;
				}
				inserted.parent = lastNode;
			}
			compared = inserted;
		}
		else if(inserted.key.compareTo(compared.key) < 0){
			simpleInsert(inserted, compared.left, "left", compared);
		}
		else if (inserted.key.compareTo(compared.key) > 0){
			simpleInsert(inserted, compared.right, "right", compared);
		}
	}
	
	public static void main(String[] args) {
		
		RedBlackTree<Integer, String> rbt = new RedBlackTree<Integer, String>();
		rbt.insert(13, 13 + "");
		rbt.insert(8, 8 + "");
		rbt.insert(1, 1 + "");
		rbt.insert(6, 6 + "");
		rbt.insert(11, 11 + "");
		rbt.insert(17, 17 + "");
		rbt.insert(15, 15 + "");
		rbt.insert(25, 25 + "");
		rbt.insert(22, 22 + "");
		rbt.insert(26, 26 + "");
		
		rbt.printTree();
		System.out.println(""); 
		
		rbt.delete(13);
		rbt.printTree();
		
		rbt.clearTree();
	}
	
	public RBT() {
		// TODO: initialize the tree
		root = EMPTY;
	}
	
	void insert(Key k, Value v) {
		// TODO: insert value v using key k
		Node n = new Node(k, v);
		if (this.isEmpty()){
			root = n;
		}
		simpleInsert(n, root, "no position", EMPTY);
		insert_case1(n);
	}
	
	Node findHeir(Node n){
		Node heir = n.left;
		while (heir.right != EMPTY){
			heir = heir.right;
		}
		return heir;
	}
	
	void delete(Key k) {
		// TODO: delete the Node associated with key k
		if (this.isEmpty()){
			return;
		}
		Node n = root.getNode(k);
		Node n2 = n;
		if (n != EMPTY){
			if (n.left != EMPTY && n.right != EMPTY) {
				Node heir = findHeir(n);
				n.value = heir.value;
				n.key = heir.key;
				n2 = heir;
			}
			if (n2.left == EMPTY && n2.right == EMPTY){
				if (n2.color == Color.RED){
					n2.replaceWith(EMPTY);
					return;
				}
				
				delete_case1(n2);
				n2.replaceWith(EMPTY);
				return;
			}
			else if (n2.left == EMPTY && n2.right != EMPTY){
				if (n2.color == Color.RED){
					n2.replaceWith(n2.right);
					return;
				}
				if (n2.right.color == Color.RED){
					n2.right.color = Color.BLACK;
					n2.replaceWith(n2.right);
					return;
				}
			}
			else if (n2.left != EMPTY && n2.right == EMPTY){
				if (n2.color == Color.RED){
					n2.replaceWith(n2.left);
					return;
				}
				if (n2.left.color == Color.RED){
					n2.left.color = Color.BLACK;
					n2.replaceWith(n2.left);
					return;
				}
			}
			
		}
	}
	
	boolean isEmpty(){
		if (root == EMPTY){
			return true;
		}
		return false;
		// TODO: Check if is empty. Return true if empty; else false
	}
	
	void clearTree(){
		// TODO: Remove all items
		root = EMPTY;
	}
	
	void printTree(){
		// TODO: print all items
		if (this.isEmpty()){
			return;
		}
		System.out.println(getNodeString(root));
		System.out.println(""); 
	}
}