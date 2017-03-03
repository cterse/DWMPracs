import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ParseDataset {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File dataset = new File("Dataset/ds1.csv");
		Scanner t = null;
		try {
			t = new Scanner(dataset);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//get names of attributes
		String attributeCSV = t.nextLine();
		System.out.println("Attributes: "+attributeCSV);
		String[] attributes = attributeCSV.split(",");
		//the array "attributes" now contain the attributes
		
		//Storage of the values in the dataset
		Map<String, List<String>> values = new HashMap<String, List<String>>();
		for(int i=0; i<attributes.length; i++) {
			values.put(attributes[i], new ArrayList());
		}
		while( t.hasNextLine() ) {
			String[] temp = t.nextLine().split(",");
			for(int i=0; i<temp.length; i++) {
				values.get(attributes[i]).add(temp[i]);
			}
		}
		for(int i=0; i<attributes.length; i++) {
			if(i == attributes.length-1)
				System.out.print("CLASS:");
			System.out.println(attributes[i]+" = "+values.get(attributes[i]));
		}
		int numOfRecords = values.get(attributes[0]).size();
		System.out.println("Number of total records = "+numOfRecords);
		
		//get different types of class values
		List<String> classValues = new ArrayList<String>();
		classValues.add( values.get(attributes[attributes.length-1]).get(0) );
		for(int i=0; i<numOfRecords; i++) {
			if( !classValues.contains( values.get(attributes[attributes.length-1]).get(i) ) ) {
				classValues.add( values.get(attributes[attributes.length-1]).get(i) );
			}
		}
		System.out.println("Different types of classes = "+classValues+" ("+classValues.size()+")");
		
		//print records classification
		for(int i=0; i<classValues.size(); i++) {
			System.out.print("The class value is "+ classValues.get(i) +" for RID = ");
			for(int j=0; j<values.get(attributes[1]).size(); j++) {
				if( values.get(attributes[attributes.length-1]).get(j).equalsIgnoreCase( classValues.get(i) ) ) {
					System.out.print( values.get(attributes[0]).get(j) +" ");
				}
			}
			System.out.println();
		}
		
	}
}
