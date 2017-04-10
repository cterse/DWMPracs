package code;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Class that represents a Graph
 * @author Roque E. Lopez
 */
public class Graph {
	private HashMap<Vertex, ArrayList<Edge>> adjacents;
	private ArrayList<Vertex> vertexes;

	public Graph() {
		adjacents = new HashMap<Vertex, ArrayList<Edge>>();
		vertexes = new ArrayList<Vertex>();
	}

	/** Create a new Vertex */
	private void addVertex(int id, double x, double y){
		Vertex node = new Vertex(id, x, y);
		adjacents.put(node, new ArrayList<Edge>());
		vertexes.add(node);
	}

	/** Create a new Edge between two vertexes */
	private void addEdge(Vertex a, Vertex b){
		double distance = euclidianDistance(a, b);
		adjacents.get(a).add(new Edge(a,b, distance));
		adjacents.get(b).add(new Edge(b, a, distance));
	}

	/** Create edges for all the vertexes */
	private void generateEdges(){
		for(int i = 0; i < vertexes.size(); i++){
			for(int j = i+1; j < vertexes.size(); j++){
				addEdge(vertexes.get(i), vertexes.get(j));
			}
		}
	}

	/** Create a graph from a file */
	public void generateGraph(String fileName) {
		int cont = 0;
		FileReader fr = null; 
		try{
			fr = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner reader = new Scanner(fr);
		String pattern = "[\\s]+";
		Pattern splitter = Pattern.compile(pattern);

		while (reader.hasNext()) {
			String[] result = splitter.split(reader.nextLine());
			addVertex(cont, Double.parseDouble(result[0]), Double.parseDouble(result[1]));
			cont += 1;
		}
		generateEdges();
	}

	public ArrayList<Vertex> getVertexList(){
		return vertexes;
	}

	/** Return a list of all edges */
	public ArrayList<Edge> getEdgeList(){
		ArrayList<Edge> tmp = new ArrayList<Edge>();
		for(int i = 0; i < vertexes.size(); i++){
			tmp.addAll(adjacents.get(vertexes.get(i)));
		}
		return tmp;
	}

	public Vertex getRandomVertex(){
		return vertexes.get(0);
	}

	public Vertex getVertexById(int i){
		return vertexes.get(i);
	}

	public ArrayList<Edge> getAdjacents(Vertex v){
		return adjacents.get(v);
	}
	
	/** Return the edge of two vertexes */
	public Edge getEdge(Vertex a, Vertex b){
		ArrayList<Edge> tmp = adjacents.get(a);
		for(int i = 0; i < tmp.size(); i++){
			if(tmp.get(i).getAdjacent().equals(b))
				return tmp.get(i);
		}
		return null;
	}

	public int getGraphSize(){
		return vertexes.size();
	}

	/** Return the Euclidian distance between two vertexes */
	public double euclidianDistance(Vertex a, Vertex b){
		return Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
	}

	public void printGraph(){
		for(int i = 0; i < vertexes.size(); i++){
			System.out.println("Vertex" + vertexes.get(i).getId());
			ArrayList<Edge> tmp = adjacents.get(vertexes.get(i));
			for(int j = 0; j < tmp.size(); j++){
				System.out.print(tmp.get(j).getAdjacent().getId() + " " +tmp.get(j).getDistance()+ "; ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Class that represents a vertex in the graph
	 */
	public class Vertex{
		private int id;
		private double x, y, priority;// the priority attribute is used in the Heap

		public Vertex(int id, double x, double y){
			this.id = id;
			this.x = x;
			this.y = y;
			priority = Double.POSITIVE_INFINITY;
		}

		@Override
		public boolean equals(Object obj){
			if(obj == null) 
				return false;
			if (this.getClass() == obj.getClass()){
				Vertex myValueObject = (Vertex) obj;
				if (myValueObject.getId() == this.getId())
					return true;
			}
			return false;
		}

		public int getId(){
			return id;
		}

		public double getX(){
			return x;
		}

		public double getY(){
			return y;
		}

		public double getPriority(){
			return priority;
		}

		public void setPriority(double newPriority){
			priority = newPriority;
		}
	}

	/**
	 * Class that represents a edge in the graph
	 */
	public class Edge implements Comparable<Edge>{
		private Vertex begin;
		private Vertex adjacent;
		private double distance;
		private boolean labeled = false;

		public Edge(Vertex begin, Vertex adjacent, double distance) {
			this.begin = begin;
			this.adjacent = adjacent;
			this.distance = distance;
		}

		public Vertex getAdjacent(){
			return adjacent;
		}

		public Vertex getBegin(){
			return begin;
		}

		public double getDistance(){
			return distance;
		}

		public boolean isLabeled(){
			return labeled;
		}

		public void setLabeled(){
			labeled = true;
		}

		@Override
		public int compareTo(Edge arg) {
			if(this.getDistance() < arg.getDistance())
				return -1;
			if(this.getDistance() > arg.getDistance())
				return 1;
			return 0;
		}
	}
}
