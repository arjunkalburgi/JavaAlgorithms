import java.util.LinkedList;


public class SkipList<Key extends Comparable<? super Key>, Value> {
	
	public static final double P = 0.5;
	public static final int MAX_LEVEL = 6;
	public final SkipNode<Key, Value> header = new SkipNode<Key, Value>(MAX_LEVEL, null, null);
    public int level = 0;
	
	public static int randomLevel() {
	    int lvl = (int)(Math.log(1.-Math.random())/Math.log(1.-P));
	    return Math.min(lvl, MAX_LEVEL);
	} 
	
	class SkipNode<Key extends Comparable<? super Key>, Value>
	{
	    public final Key key;
	    public Value value; 
	    public SkipNode<Key,Value>[] forward; // array of pointers

	    SkipNode(int lvl, Key key, Value val) {
	    	this.forward = new SkipNode[lvl + 1]; 
	    	this.value = val;
	    	this.key = key; 
	    }
	}
	
	public void insert(Key key, Value val) {
		SkipNode<Key, Value> x = header; 
		SkipNode<Key, Value>[] update = new SkipNode[MAX_LEVEL + 1]; 
		
		for (int i=level; i >= 0; i--) {
			while (x.forward[i] != null && x.forward[i].key.compareTo(key) < 0) {
				x = x.forward[i]; 
			}
			update[i] = x; 
		}
		x = x.forward[0]; 
		
		if (x == null || !x.key.equals(key)) {
			int lvl = randomLevel(); 
			
			if (lvl > level) {
				for (int i=level+1; i<=lvl; i++) {
					update[i] = header; 
				}
			}
			level = lvl;
			
			x = new SkipNode<Key, Value> (lvl, key, val); 
			for (int i=0; i<=lvl; i++) {
				x.forward[i] = update[i].forward[i]; 
				update[i].forward[i] = x; 
			}
		}
		
	}

	public void remove(Key key) {
	    SkipNode<Key, Value> x = header;
	    SkipNode<Key, Value>[] update = new SkipNode[MAX_LEVEL + 1];
	    
		for (int i=level; i >= 0; i--) {
			while (x.forward[i] != null && x.forward[i].key.compareTo(key) < 0) {
				x = x.forward[i]; 
			}
			update[i] = x; 
		}
		x = x.forward[0]; 

	    if (x.key.equals(key)) {
	    	for (int i = 0; i <= level; i++) {
	    	    if (update[i].forward[i] != x)
	    	        break;
	    	    update[i].forward[i] = x.forward[i];
	    	}
	    	
	    	while (level > 0 && header.forward[level] == null) {
	    	    level--;
	    	}
	    }
	}

	public Value find(Key searchKey) {
	    SkipNode<Key, Value> x = header;
	    for (int i = level; i >= 0; i--) {
	        while (x.forward[i] != null && x.forward[i].key.compareTo(searchKey) < 0) {
	            x = x.forward[i];
	        }
	    }
	    x = x.forward[0];
	    return (x==null) ? null : x.value;
	}

	public void clear() {
		SkipNode<Key, Value> x = header; 
		x.forward[0] = null; 
		this.level = 0;
	}
	
	public int size() {
	    SkipNode<Key, Value> x = header.forward[0];
	    int size = 0;
	    while (x != null) {
	        size += 1; 
	        x = x.forward[0];
	    }
	    return size;
	}
	
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("{");
	    SkipNode<Key, Value> x = header.forward[0];
	    while (x != null) {
	        sb.append(x.key);
	        x = x.forward[0];
	        if (x != null)
	            sb.append(",");
	    }    
	    sb.append("}");
	    return sb.toString();		
	}
	
	public static void main(String[] args) {

	    SkipList<Integer, String> ss = new SkipList<Integer, String>();
	    System.out.println(ss);

	    ss.insert(5,"a");
	    ss.insert(10,"b");
	    ss.insert(7,"c");
	    ss.insert(7,"d");
	    ss.insert(6,"e");

	    System.out.println(ss + "-> size: " + ss.size());
	    
	    
	    ss.remove(5); 

	    System.out.println(ss);
	    
	    ss.clear(); 

	    System.out.println(ss);
	    	    
	    System.out.println(ss.find(9)); 
	}

	
}
