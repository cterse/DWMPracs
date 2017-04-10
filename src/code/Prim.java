package code;




import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import code.Graph.Vertex;
import code.Graph.Edge;


/**
 * Class that implement the Prim’s algorithm
 * @author Roque E. Lopez
 */
public class Prim{
	ArrayList<Vertex> initials;// vertexes without parents

	public Prim(){
		initials = new ArrayList<Vertex>();
	}

	/** Return the Minimum Spanning Tree */
	public ArrayList<Edge> getMST(Graph graph, Vertex initial){
		HashMap <Vertex, Double> keys = new HashMap<Vertex, Double>();
		HashMap <Vertex, Edge> parents = new HashMap <Vertex, Edge>();
		ArrayList<Edge> mst = new ArrayList<Edge>();
		Heap heap = new Heap();

		for(Vertex v : graph.getVertexList()){
			keys.put(v, Double.POSITIVE_INFINITY);
			parents.put(v, null);
			heap.insert(v);
		}
		keys.put(initial, 0.0);
		heap.decreaseKey(initial, 0);

		while(!heap.isEmpty()){
			Vertex min = heap.extractMin();
			for(Edge e : graph.getAdjacents(min)){
				Vertex v = e.getAdjacent();
				double distance = e.getDistance();
				if (heap.contains(v) && keys.get(v) > distance){
					keys.put(v, distance);
					heap.decreaseKey(v, distance);
					parents.put(v, graph.getEdge(v, min));					
				}
			}
		}
		// add the edges to a list
		for (Entry<Vertex, Edge> entry : parents.entrySet()){
			if(entry.getValue() != null)
				mst.add(entry.getValue());
			else
				initials.add(entry.getKey());
		}
		
		return mst;
	}

	/** Remove the k-1 edges with greatest distances */
	public ArrayList<Edge> removeEdges(ArrayList<Edge> mst, int k){	 
		Collections.sort(mst); // sort the edges in ascending order
		for (int i = 0; i < k-1 ;i++) { 	
			initials.add(mst.get(mst.size()-1).getBegin());
			mst.remove(mst.size()-1); // remove the k-1 last edges	
		}
		return mst;
	}

	/** Return the vertexes without parents */
	public ArrayList<Vertex> getInitials(){
		return initials;
	}
}