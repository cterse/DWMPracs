package code;


import java.util.ArrayList;

/**
 * The Union-Find structure
 * @author Roque E. Lopez
 */
public class UnionFind {
	private int [] ids; // stores the ids of the vertexes
	private int [] sizeSets;// stores the number of its elements, useful to balance the trees

	public UnionFind(int numElements){
		ids = new int[numElements];
		sizeSets = new int[numElements];

		for(int i=0; i < numElements; i++){
			ids[i] = i;
			sizeSets[i] = 1;
		}
	}

	/** Find the root of a element */
	public int find(int x){
		while(x != ids[x]){
			ids[x] = ids[ids[x]]; // path compression
			x = ids[x];
		}
		return x;
	}

	/** Joins two elements if them not belong to the same subset */
	public void union(int x, int y){
		int xRoot = find(x);
		int yRoot = find(y);

		if(xRoot != yRoot){
			if(sizeSets[xRoot] > sizeSets[yRoot]){// to balance the trees
				ids[yRoot] = xRoot;
				sizeSets[xRoot] += sizeSets[yRoot];
			}
			else{
				ids[xRoot] = yRoot;
				sizeSets[yRoot] += sizeSets[xRoot];
			}
		}
	}
	
	/** Return the number of subsets */
	public int getNumberSets() {
		int cont = 0;
		for(int i=0; i < ids.length; i++){
			if(i == ids[i])
				cont += 1;
		}
		return cont;
	}

	/** Return the roots of the subsets of the union-find */
	public ArrayList<Integer> getRoots(){
		ArrayList<Integer> roots = new ArrayList<Integer>();
		for(int i=0; i < ids.length; i++){
			if(i == ids[i])
				roots.add(i);
		}
		return roots;
	}

	public void printUnionfind(){
		for(int i=0; i < ids.length; i++){
			System.out.println("Element "+i+"   Parent "+ids[i]+"   Size "+ sizeSets[i]);
		}
		System.out.println("Number of elements Sets " + getNumberSets());
	}
}

