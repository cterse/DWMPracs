package code;

import java.util.ArrayList;
import java.util.Collections;
import code.Graph.Vertex;
import code.Graph.Edge;

/**
 * Class that implement the Kruskal’s algorithm
 * @author Roque E. Lopez
 */
public class Kruskal{
	ArrayList<Vertex> initials;// represent the roots of the subsets of the union-find

	public Kruskal(){
		initials = new ArrayList<Vertex>();
	}

	/** Return the Minimum Spanning Tree */
	public ArrayList<Edge> getMST(Graph graph, int k){
		UnionFind uf = new UnionFind(graph.getGraphSize());
		ArrayList<Edge> edgeList= graph.getEdgeList();
		ArrayList<Edge> mst =  new ArrayList<Edge>();
		Collections.sort(edgeList);// sort the edges in ascending order
		int x;
		int y;
		for(int i = 0; i < edgeList.size(); i++){
			if(k == uf.getNumberSets())// verify if there are k subsets 
				break;
			x = edgeList.get(i).getBegin().getId();
			y = edgeList.get(i).getAdjacent().getId();
			if(uf.find(x) != uf.find(y)){
				uf.union(x, y);
				mst.add(edgeList.get(i));
			}
		}

		ArrayList<Integer> roots = uf.getRoots();
		for(int i = 0; i < roots.size(); i++){
			initials.add(graph.getVertexById(roots.get(i)));// add the roots vertexes
		}
		return mst;
	}

	/** Return the vertexes roots of the subsets of the union-find */
	public ArrayList<Vertex> getInitials(){
		return initials;
	}

}
