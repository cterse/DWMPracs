package attributerelevanceanalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AttributeRelevanceAnalysis {
    public static void main(String[] args) {
        Scanner t = new Scanner(System.in);
        System.out.print("Path to dataset: ");
        String datasetPath = t.next();
        
        ParseDataset parse = new ParseDataset(datasetPath);
        
        //System.out.println(data);
        
        //parse.printDataset();
        
        double datasetEntropy = calculateEntropy(parse, parse.getDatasetClass());
        System.out.println("Entropy of dataset = "+datasetEntropy);
        //System.out.println("API entropy = "+parse.getDatasetEntropy());
        
        
        
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
}
