import java.util.ArrayList;

public class Stack<E> {
	private ArrayList<E> elements;

	public Stack() {
		elements = new ArrayList<E>();  
	}
	
	public int size() {
		return elements.size();
	}

	public void push(E pushValue) {
		elements.add(pushValue);
	}

	public E pop() {
		return this.Empty() ? null : elements.remove(elements.size() - 1);
	}
	
	public boolean Empty() {
		return elements.isEmpty(); 
	}
	
	public static void main(String[] args) {
		 Stack<Integer> intStack = new Stack<Integer>();
		 intStack.push(5);
		 intStack.push(6);
		 System.out.println(intStack.pop()); 
		 
		 Stack<Double> doubleStack = new Stack<Double>(); 
		 doubleStack.push(5.5); 
		 doubleStack.push(6.5); 
		 System.out.println(doubleStack.pop()); 
		 
		 Stack<String> strStack = new Stack<String>(); 
		 strStack.push("five"); 
		 strStack.push("six");
		 System.out.println(strStack.pop()); 
	}
}
