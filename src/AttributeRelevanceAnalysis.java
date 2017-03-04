package attributerelevanceanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AttributeRelevanceAnalysis {
    public static void main(String[] args) {
        Scanner t = new Scanner(System.in);
        //System.out.print("Path to dataset: ");
        String datasetPath = "Dataset/ds1.csv";
        
        ParseDataset parse = new ParseDataset(datasetPath);
        
        //parse.printDataset();
        
        double datasetEntropy = calculateEntropy(parse, parse.getDatasetClass());
        System.out.println("Entropy of dataset = "+datasetEntropy);
        //System.out.println("API entropy = "+parse.getDatasetEntropy());
        
        List<String> attributesList = parse.getAttributesAsList();      //Get attributes in a list
        Map<String, Integer> attributesEntropy = new HashMap<String, Integer>();
        Iterator<String> attributesListIt = attributesList.iterator();
        while( attributesListIt.hasNext() ) {
            
            String currentAttribute = attributesListIt.next();
            if( currentAttribute.equalsIgnoreCase(parse.getDatasetClass()) ) {
                //We already got the class entropy, so skip its calculation
                continue;
            }
            if( parse.hasIdAttribute() && currentAttribute.equalsIgnoreCase(parse.getIdAttribute()) ) {
                //If the first attribute is ID attribute, skip its entropy calculation
                continue;
            }
            else {
                attributesEntropy.put(currentAttribute, 0);
                double attrEntropy = calculateEntropy(parse, currentAttribute, null);
                //attributesEntropy.put(currentAttribute, attrEntropy);
            }
        }
        
    }

    static double calculateEntropy(ParseDataset parse, String datasetClass) {
        double entropy = 0;
        List<String> classesTypes = parse.typesOfClasses();
        //System.out.println(classesTypes);
        //System.out.println(parse.getRecordsCount());
        Map<String, List<String>> data = parse.getParsedDataset();
        //System.out.println(data);
        
        //Map for storing the classes and their count
        Map<String, Integer> classesCount = new HashMap<String, Integer>();
        for(int i=0; i<classesTypes.size(); i++) {
            classesCount.put(classesTypes.get(i), 0);
        }
        
        //Populate the above Map with count of classes
        for(int i=0; i<parse.getRecordsCount(); i++) {
            String currentClass = data.get(parse.getDatasetClass()).get(i);
            int temp = classesCount.get( currentClass );
            classesCount.put(currentClass, ++temp);
        }
        System.out.println(classesCount);
        
        //Calculate the entropy of dataset
        for(int i=0; i<classesTypes.size(); i++) {
            double temp1 = classesCount.get(classesTypes.get(i));
            double temp2 = parse.getRecordsCount();
            double temp = temp1/temp2;
            entropy += -(temp)*(Math.log(temp) / Math.log(2));
        }
        
        return entropy;
    }

    static double calculateEntropy(ParseDataset parse, String currentAttribute, List<String> selectedAttrList) {
        double entropy = 0;
        if(selectedAttrList == null) {
            //this is the 1st iteration    
            List<String> attrTypes = parse.getAttributeTypes(currentAttribute); //List to store types of currentAttribute
            //System.out.println(currentAttribute+" = "+attrTypes);
            
        } else {
            //this is not the first iteration and the dataset is split using some attributes given in selectedAttrList
        }
        
        return entropy;
    }

}
