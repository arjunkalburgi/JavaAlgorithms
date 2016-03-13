import java.util.ArrayList;
import java.util.Collections;

public class Heap<T extends Comparable<T>> {

    private int length;
    private ArrayList<T> heap;

	public static void main(String[] args) {
    	Heap<Integer> foo = new Heap<Integer>(); 
    	foo.insertIntoHeap(6); 
    	foo.insertIntoHeap(5); 
    	foo.insertIntoHeap(3); 
    	foo.insertIntoHeap(1); 
    	foo.insertIntoHeap(8); 
    	foo.insertIntoHeap(7); 
    	foo.insertIntoHeap(2); 
    	foo.insertIntoHeap(4); 
    	
    	foo.HeapSort();
    	foo.printHeap();
    }
	
	public Heap() {
		heap  = new ArrayList<T>(); 
	}
	
    private void insertIntoHeap(T a) {
    	heap.add(a); 
    	 
    	buildheap(); 
    }
    
    private void maxheap(ArrayList<T> a, int mid) {
        int largest = mid;

        int left = 2*mid;
        int right = 2*mid + 1;

        if (left < length && right < length) {

            if (a.get(left).compareTo(a.get(mid)) == 1) {
                largest = left;
            }

            if (a.get(right).compareTo(a.get(largest)) == 1) {
                largest = right;
            }

            if (a.get(largest).compareTo(a.get(mid)) != 0) {
                Collections.swap(a, mid, largest);
                maxheap(a, largest);
            }
        }
    }

    private void buildheap() {
        int size = heap.size() - 1;

        for (int i = size/2; i>=0; i--) {
            maxheap(heap, i);
        }
    }
    
    private void printHeap() {
        for (T i : heap) {
            System.out.print(i + ", ");
        }
        System.out.println("");
        
        for (T i : heap) {
            System.out.println(i);
        }
    }

    private void HeapSort() {
        for (int i=length; i>0; i--) {
            Collections.swap(heap, 0, i);
            length = length - 1;
            maxheap(heap, 0);
        }
    }
}

