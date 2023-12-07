package Main;

import java.util.ArrayList;
import java.util.Random;

//class Main.RandomForest creates a randomForest by creating DecisionTrees and then reporting their aggregate prediction
public class RandomForest {

    static ArrayList<DecisionTree> decisionTrees = new ArrayList<DecisionTree>();

    int numTrees = 100;
    static int maxFeatures = 5;
    int bsDataSize = 380;

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

    //adds the ability to set the number of trees
    public RandomForest(ArrayList<Info> ad, int trees, int bsd) {
        allData = ad;
        numTrees = trees;
        bsDataSize = bsd;
        catFeatures.add(1);
        catFeatures.add(10);
    }

    //Returns the average of all the dataPoints for a given feature
    public static float GetAverage(int featureIndex) {
        //since there's only one categorical feature, we can cheat
        //otherwise we'd need more space to create an array and whatnot
        if (catFeatures.contains(featureIndex)) {
            int numFemales = 0;
            for (int i = 0; i < allData.size(); i++)
                if (allData.get(i).data.get(featureIndex) == 2)
                    numFemales++;
            return numFemales >= allData.size() - numFemales ? 2 : 1; //returns the mode
        } else {
            float total = 0;
            for (int i = 0; i < allData.size(); i++)
                total += allData.get(i).data.get(featureIndex);
            return total / allData.size(); //returns an actual average
        }
    }


    //Creates all of the DecisionTrees
    public void CreateRandomForest() {
        System.out.println("Creating the randomForest");
        System.out.println("Please do not exit the program");
        //splits the data into 5 segments
        ArrayList<ArrayList<Info>> dataSegments = SplitData(5);
        for (int i = 0; i < numTrees; i++) {
            ArrayList<Info> trainingData = GetBootStrappedData(bsDataSize);
            ArrayList<Info> testingData = GetTestingData(trainingData);

            //create tree
            DecisionTree dt = new DecisionTree(maxFeatures, trainingData, testingData);
            dt.TestDecisionTree();

            //put tree in forest
            decisionTrees.add(dt);
        }
    }


    //Returns an aggregate prediction from all the decision trees based on a specified Main.Info
    public boolean GetPrediction(Info info) {
        int numFalse = 0;
        int numTrue = 0;

        for (int i = 0; i < decisionTrees.size(); i++)
            if (decisionTrees.get(i).GetPrediction(info))
                numTrue++;
            else
                numFalse++;
        return numTrue >= numFalse ? true : false; //returns the biggest
    }

    public ArrayList<Info> GetBootStrappedData(int size) {
        ArrayList<Info> trainingData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int index = random.nextInt(allData.size());
            trainingData.add(allData.get(index));
        }
        return trainingData;
    }

    public ArrayList<Info> GetTestingData(ArrayList<Info> trainingData) {
        ArrayList<Info> testingData = new ArrayList<>();
        for (int i = 0; i < allData.size(); i++)
            if (!trainingData.contains(allData.get(i)))
                testingData.add(allData.get(i));
        return testingData;
    }

    //returns if the random forest is correct or not when it predicted
    private boolean TestRandomForest(Info info) {
        boolean prediction = GetPrediction(info);
        return prediction == info.isDiseaseProgressionGood ? true : false;
    }

    public void Validate() {
        //split the data into 5 groups
        ArrayList<ArrayList<Info>> dataSegments = SplitData(5);
        ArrayList<Integer> correctness = new ArrayList<>();
        //train and test trees for each
        for (int split = 0; split < 5; split++) {
            ArrayList<Info> trainingData = new ArrayList<>();
            for (int j = 0; j < dataSegments.size(); j++) {
                if(j == split) continue;
                trainingData.addAll(dataSegments.get(j));
            }
            ArrayList<Info> testingData = dataSegments.get(split);
            //create the random forest and test it.
            for (int i = 0; i < numTrees; i++) {

                //create tree
                DecisionTree dt = new DecisionTree(maxFeatures, trainingData, testingData);

                //put tree in forest
                decisionTrees.add(dt);


            }
            //calclates how correct the random forest is
            int correct = 0;
            for(int info = 0; info < testingData.size();info++)
                if(TestRandomForest(testingData.get(info)))
                    correct++;
            correctness.add(correct);
            System.out.println("correct: " + correct + "total: " + testingData.size() );
        }

    }

    public ArrayList<ArrayList<Info>> SplitData(int split) {
        ArrayList<ArrayList<Info>> dataSegments = new ArrayList<>();
        //initialize inner lists
        for (int i = 0; i < split; i++) {
            ArrayList<Info> infoData = new ArrayList<>();
            dataSegments.add(infoData);
        }

        //populates data and alternates between data segments
        for (int i = 0; i < allData.size(); i++)
            dataSegments.get(i % split).add(allData.get(i));
        return dataSegments;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < decisionTrees.size(); i++)
            str += decisionTrees.get(i).toString() + "\n";
        return str;
    }
}
