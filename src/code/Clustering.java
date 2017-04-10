package code;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

import code.Graph.Edge;
import code.Graph.Vertex;

public class Clustering {
	private HashMap <Integer, ArrayList<Vertex>> clusters;
	private ArrayList<Edge> mst;

	public Clustering(ArrayList<Edge> mst){
		this.mst = mst;
		clusters = new HashMap <Integer, ArrayList<Vertex>>();
	}

	/** Create the clusters	 */
	public void clusterization(ArrayList<Vertex> initials){
		for(int i = 0; i < initials.size(); i++){
			Vertex initial = initials.get(i);
			clusters.put(i, new ArrayList<Vertex>());
			createCluster(initial, i);
		}
	}

	/** Grouped the elements of one cluster	 */
	private void createCluster(Vertex v, int cluster){
		clusters.get(cluster).add(v);
		for( int i = 0; i < mst.size(); i++ ){
			if(! mst.get(i).isLabeled()){
				if(v.equals(mst.get(i).getAdjacent())){
					mst.get(i).setLabeled();
					createCluster(mst.get(i).getBegin(), cluster);
				}
				else if(v.equals(mst.get(i).getBegin())){
					mst.get(i).setLabeled();
					createCluster(mst.get(i).getAdjacent(), cluster);
				}
			}
		}
	}
	
	/** Plot the clusters */
	public void plotClusters(){
		Plotter plotter = new Plotter();
		JFrame f = new JFrame();
		f.setTitle("Clusters");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(plotter);
		f.setSize(400,400);
		f.setLocation(200,200);
		f.setVisible(true);
	}

	public void printClustering(){
		int cont = 0;
		System.out.println("Number of clusters = "+clusters.size());
		for(int i = 0; i < clusters.size(); i++){
			ArrayList<Vertex> tmp = clusters.get(i);
			for (Vertex v : tmp) {
				cont += 1;
				System.out.println(v.getId() +"	cluster "+ i);
			}
		}
		System.out.println("Number of elements ="+cont);
	}

	/**
	 * Plot the points of each cluster
	 */
	class Plotter extends JPanel{
		private int BORDER = 5;
		private int POINT_WIDTH = 3;
		private Color COLORS[] = {Color.RED, Color.BLUE, Color.PINK, Color.BLACK, Color.YELLOW, Color.GREEN, Color.ORANGE};

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;

			double xScale = ((double) getWidth() - 2 * BORDER) / 40;
			double yScale = ((double) getHeight() - 2 * BORDER) / 30;

			for (int i = 0; i < clusters.size(); i++) {
				g2.setPaint(COLORS[i]);
				ArrayList<Vertex> elements = clusters.get(i);
				for (int j = 0; j < elements.size(); j++) {
					double x = BORDER + elements.get(j).getX()*xScale;
					double y = getHeight() - BORDER - elements.get(j).getY()*yScale;
					g2.fill(new Ellipse2D.Double(x, y, POINT_WIDTH, POINT_WIDTH));
				}
			}
		}
	}
}
