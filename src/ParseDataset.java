package attributerelevanceanalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ParseDataset {
    private Map<String, List<String>> values = new HashMap<String, List<String>>();    
    private int numOfRecords;
    private String[] attributes;
    private String datasetPath; 
    
    ParseDataset(String datasetPath) {
        this.datasetPath = datasetPath;
        values = parseDataset();
    }
    
    public Map<String, List<String>> getParsedDataset() {
        if( values == null ) {
            System.out.println("set mapping first. Returning null.");
        }
        return values;
    }
    
    private Map<String, List<String>> parseDataset() {
        if( this.datasetPath == null ) {
            System.out.println("Enter the dataset path first.\nReturning null.");
            return null;
        }
        File dataset = new File(this.datasetPath);
        Scanner t = null;
        try {
            t = new Scanner(dataset);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //get names of attributes
        String attributeCSV = t.nextLine();
        //System.out.println("Attributes: "+attributeCSV);
        this.attributes = attributeCSV.split(",");
        //the array "attributes" now contain the attributes
        
        //Storage of values in Map
        //First, create mapping in Map by adding attributes as map keys and creating Arraylists as elements.
        for(int i=0; i<attributes.length; i++) {
            values.put(attributes[i], new ArrayList());
        }
        //Store the values if the attributes
        while( t.hasNextLine() ) {
            String[] temp = t.nextLine().split(",");
            for(int i=0; i<temp.length; i++) {
                    values.get(attributes[i]).add(temp[i]);
            }
        }
        
        numOfRecords = values.get(attributes[0]).size();
        
        return values;
    }
    
    public List<String> getAttributesAsList() {
        //Get attributes as an ArrayList
        List<String> attributesList = null;
        if( attributes!=null ) {
            attributesList = new ArrayList<String>();
            for(int i=0; i<attributes.length; i++)
                attributesList.add(attributes[i]);
        }
        return attributesList;
    }
    
    public String getDatasetClass() {
        String datasetClass = null;
        if( attributes!=null ) {
            datasetClass = attributes[attributes.length-1];
        }
        return datasetClass;
    }
    
    public List<String> typesOfClasses() {
        //get different types of class values
        if( values == null ) {
            System.out.println("Set mapping first. Returning null");
            return null;
        }
        List<String> classValues = new ArrayList<String>();
        classValues.add( values.get(attributes[attributes.length-1]).get(0) );
        for(int i=0; i<numOfRecords; i++) {
                if( !classValues.contains( values.get(attributes[attributes.length-1]).get(i) ) ) {
                        classValues.add( values.get(attributes[attributes.length-1]).get(i) );
                }
        }
        return classValues;
    }
    
    public void printDataset() {
        for(int i=0; i<attributes.length; i++) {
            if(i == attributes.length-1)
                    System.out.print("CLASS:");
            System.out.println(attributes[i]+" = "+values.get(attributes[i]));
        }
    }
    
    public int getRecordsCount() {
        int recordsCount = -1;
        if(values!=null) {
            return values.get(attributes[0]).size();
        }
        return recordsCount;
    }
    
    public double getDatasetEntropy() {
        double entropy = -1;
        if(values!=null) {
            entropy = 0;
            List<String> classesTypes = this.typesOfClasses();
            Map<String, Integer> classesCount = new HashMap<String, Integer>();
            for(int i=0; i<classesTypes.size(); i++) {
                classesCount.put(classesTypes.get(i), 0);
            }
            for(int i=0; i<this.getRecordsCount(); i++) {
                String currentClass = values.get(this.getDatasetClass()).get(i);
                int temp = classesCount.get( currentClass );
                classesCount.put(currentClass, ++temp);
            }
            for(int i=0; i<classesTypes.size(); i++) {
                double temp1 = classesCount.get(classesTypes.get(i));
                double temp2 = this.getRecordsCount();
                double temp = temp1/temp2;
                entropy += -(temp)*(Math.log(temp) / Math.log(2));
            }
        }
        return entropy;
    }

//    
//    public static void main(String[] args) {
//            // TODO Auto-generated method stub
//            File dataset = new File("Dataset/ds1.csv");
//            Scanner t = null;
//            try {
//                    t = new Scanner(dataset);
//            } catch (FileNotFoundException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//            }
//
//            //get names of attributes
//            String attributeCSV = t.nextLine();
//            System.out.println("Attributes: "+attributeCSV);
//            String[] attributes = attributeCSV.split(",");
//            //the array "attributes" now contain the attributes
//
//            //Storage of the values in the dataset
//            Map<String, List<String>> values = new HashMap<String, List<String>>();
//            for(int i=0; i<attributes.length; i++) {
//                    values.put(attributes[i], new ArrayList());
//            }
//            while( t.hasNextLine() ) {
//                    String[] temp = t.nextLine().split(",");
//                    for(int i=0; i<temp.length; i++) {
//                            values.get(attributes[i]).add(temp[i]);
//                    }
//            }
//            for(int i=0; i<attributes.length; i++) {
//                    if(i == attributes.length-1)
//                            System.out.print("CLASS:");
//                    System.out.println(attributes[i]+" = "+values.get(attributes[i]));
//            }
//            int numOfRecords = values.get(attributes[0]).size();
//            System.out.println("Number of total records = "+numOfRecords);
//
//            //get different types of class values
//            List<String> classValues = new ArrayList<String>();
//            classValues.add( values.get(attributes[attributes.length-1]).get(0) );
//            for(int i=0; i<numOfRecords; i++) {
//                    if( !classValues.contains( values.get(attributes[attributes.length-1]).get(i) ) ) {
//                            classValues.add( values.get(attributes[attributes.length-1]).get(i) );
//                    }
//            }
//            System.out.println("Different types of classes = "+classValues+" ("+classValues.size()+")");
//
//            //print records classification
//            for(int i=0; i<classValues.size(); i++) {
//                    System.out.print("The class value is "+ classValues.get(i) +" for RID = ");
//                    for(int j=0; j<values.get(attributes[1]).size(); j++) {
//                            if( values.get(attributes[attributes.length-1]).get(j).equalsIgnoreCase( classValues.get(i) ) ) {
//                                    System.out.print( values.get(attributes[0]).get(j) +" ");
//                            }
//                    }
//                    System.out.println();
//            }
//
//    }
}
