package code;


import java.util.ArrayList;
import code.Graph.Vertex;


/**
 * Class that represents a Heap of vertexes
 * @author Roque E. Lopez
 */
public class Heap{
	ArrayList<Vertex> array;

	public Heap(){
		array = new ArrayList<Vertex>();
		array.add(null);// the array is used since the position 1
	}
	
	/** Insert a new vertex in the heap */
	public void insert(Vertex element){
		array.add(element);
		if (array.size() > 2)
			bubblingUp(array.size()-1);
	}

	/** Update the vertexes positions(a new vertex was inserted or decreases its priority) */
	private void bubblingUp(int indexElement){
		int parent;
		Vertex tmp;

		while(true){
			parent = indexElement / 2;
			if(parent > 0 && array.get(parent).getPriority() > array.get(indexElement).getPriority()){
				tmp  = array.get(parent);
				array.set(parent, array.get(indexElement));
				array.set(indexElement, tmp);
				indexElement = parent;
			}
			else 
				return;			 
		}
	}

	/** Update the vertexes positions(the root was deleted) */
	private void bubblingDown(){
		array.set(1, array.get(array.size() - 1));
		array.remove(array.size() - 1);
		int parent = 1;
		int leftChild, rightChild, minIndex;
		Vertex tmp;

		while(true){
			leftChild = parent * 2;
			rightChild = parent * 2 + 1;
			minIndex = parent;
			if(array.size() > leftChild && array.get(minIndex).getPriority() > array.get(leftChild).getPriority())
				minIndex = leftChild;
			if(array.size() > rightChild && array.get(minIndex).getPriority() > array.get(rightChild).getPriority())
				minIndex = rightChild;

			if (parent != minIndex){
				tmp  = array.get(parent);
				array.set(parent, array.get(minIndex));
				array.set(minIndex, tmp);
				parent = minIndex;
			}
			else 
				return;			 
		}
	}

	/** Return the root of the heap */
	public Vertex extractMin(){
		Vertex root = array.get(1);
		bubblingDown();
		return root;

	}

	/** Decrease the priority of vertex with a new value */
	public void decreaseKey(Vertex v, double value){
		v.setPriority(value);
		int index = array.indexOf(v);
		array.set(index, v);
		bubblingUp(index);
	}

	public boolean isEmpty(){
		if(array.size() == 1)
			return true;
		return false;
	}

	/** Verify if the heap contains the vertex */
	public boolean contains(Vertex v){
		if(array.indexOf(v) == -1)
			return false;
		return true;
	}

	public void printHeap(){
		for(int i = 0; i < array.size(); i++){
			System.out.println(i + " " +array.get(i) );
		}
	}
}
