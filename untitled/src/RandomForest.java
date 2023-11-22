import java.util.ArrayList;
import java.util.Random;

//class RandomForest creates a randomForest by creating DecisionTrees and then reporting their aggregate prediction
public class RandomForest {

    static ArrayList<DecisionTree> decisionTrees = new ArrayList<DecisionTree>();

    int numTrees = 100;
    static int maxFeatures = 5;

    //the data the rf is trained on
    static ArrayList<Info> allData;

    static Random random = new Random(); //for picking features

    //keeps track of the categorical features so we don't have to make any weird classes to recognize if a feature is cat or cont
    public static ArrayList<Integer> catFeatures = new ArrayList<>();

    //On initilization, adds the known catFeatures (even though the last is unused)
    // and populates the entire training data dataset
    public RandomForest(ArrayList<Info> ad) {
        allData = ad;
        catFeatures.add(1);
        catFeatures.add(10);
    }

    //Returns the average of all the dataPoints for a given feature
    public static float GetAverage(int featureIndex) {
        //since there's only one categorical feature, we can cheat
        //otherwise we'd need more space to create an array and whatnot
        if(catFeatures.contains(featureIndex))
        {
            int numFemales = 0;
            for(int i = 0; i < allData.size();i++)
                if(allData.get(i).data.get(featureIndex) == 2)
                    numFemales++;
            return numFemales >= allData.size() - numFemales ? 2 :1; //returns the mode
        }
        else{
            float total = 0;
            for(int i = 0; i < allData.size();i++)
                total += allData.get(i).data.get(featureIndex);
            return total / allData.size(); //returns an actual average
        }
    }


    //Creates all of the DecisionTrees
    void CreateRandomForest() {
        System.out.println("Creating the randomForest");
        System.out.println("Please do not exit the program");
        for (int i = 0; i < numTrees; i++) {
            ArrayList<Info> trainingData = new ArrayList<Info>();
            ArrayList<Integer> trainingDataIndicies = new ArrayList<>();

            //gets random data for as many datapoints as the tree will require
            for (int j = 0; j < 380; j++) {
                //get rand data and stores it in training data, as well as the index
                int randDataPoint = random.nextInt(allData.size());
                Info info = allData.get(randDataPoint);
                trainingData.add(info);
                trainingDataIndicies.add(randDataPoint);
            }

            //create tree
            DecisionTree dt = new DecisionTree(maxFeatures, trainingData, trainingDataIndicies);
            dt.TestDecisionTree();

            //put tree in forest
            decisionTrees.add(dt);
        }
    }

    //Returns an aggregate prediction from all the decision trees based on a specified Info
    static boolean GetPrediction(Info info)
    {
        int numFalse  = 0;
        int numTrue = 0;

        for(int i = 0; i < decisionTrees.size(); i++)
            if(decisionTrees.get(i).GetPrediction(info))
                numTrue++;
            else
                numFalse++;
        return numTrue >= numFalse ? true : false; //returns the biggest
    }


}
