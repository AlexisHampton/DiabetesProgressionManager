import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class DecisionTreeManager {

    ArrayList<DecisionTree> decisionTrees = new ArrayList<>();

    int numTrees = 100;
    static int maxFeatures = 5;
    static RandomForest randomForest;
    static ArrayList<Info> allData;
    ArrayList<Info> testingData = new ArrayList<Info>();
    ArrayList<Info> trainingData = new ArrayList<Info>();
    static Random random = new Random();
    public static ArrayList<Integer> catFeatures = new ArrayList<>();

    public DecisionTreeManager(ArrayList<Info> ad) {
        allData = ad;
        catFeatures.add(1);
        catFeatures.add(10);
    }

    //returns the average for a given datapoint
    public static float GetAverage(int featureIndex) {
        if(catFeatures.contains(featureIndex))
        {
            int numFemales = 0;
            for(int i = 0; i < allData.size();i++)
                if(allData.get(i).data.get(featureIndex) == 2)
                    numFemales++;
            return numFemales >= allData.size() - numFemales ? 2 :1;
        }
        else{
            float total = 0;
            for(int i = 0; i < allData.size();i++)
                total += allData.get(i).data.get(featureIndex);
            return total / allData.size();
        }
    }


    void CreateDecisionTrees() {

        int[] featureIndicies = GetFeatureIndicies();
        trainingData = GetTrainingData(380);
        DecisionTree decisionTree = new DecisionTree(featureIndicies, trainingData);
        decisionTree.BuildTree();

    }

    void CreateRandomForest() {
        randomForest = new RandomForest();
        ArrayList<DecisionTreeData> dTDs = new ArrayList<DecisionTreeData>();
        for (int i = 0; i < numTrees; i++) {
            DecisionTreeData dtData = new DecisionTreeData();
            for (int j = 0; j < 380; j++) {
                //get rand data
                int randDataPoint = random.nextInt(allData.size());
                Info info = allData.get(randDataPoint);
                dtData.trainingData.add(info);
                dtData.trainingDataIndicies.add(randDataPoint);
            }
            //get rand features
            int[] randFeatures = GetFeatureIndicies();

            //createtree
            dtData.CreateDecisionTree(randFeatures);
            dtData.TestDecisionTree();
            //put tree in forest
            dTDs.add(dtData);
        }
    }

    static int GetRandomFeature(ArrayList<Integer> featuresUsed) {
        int num = random.nextInt(10);
        if (featuresUsed == null || featuresUsed.contains(num))
            return GetRandomFeature(featuresUsed);
        return random.nextInt(10); //return rand feature index
    }

    ArrayList<Info> GetTrainingData(int split) {

        ArrayList<Info> td = new ArrayList<Info>();
        for (int i = 0; i < split; i++)
            td.add(allData.get(i));
        for (int i = split; i < allData.size(); i++)
            testingData.add(allData.get(i));
        return td;
    }

    static int[] GetFeatureIndicies(ArrayList<Integer> featuresUsed) {
        int[] fi = new int[maxFeatures];
        for (int i = 0; i < maxFeatures; i++)
            fi[i] = GetRandomFeature(featuresUsed);
        return fi;
    }

    static int[] GetFeatureIndicies() {
        int[] fi = new int[maxFeatures];
        for (int i = 0; i < maxFeatures; i++)
            fi[i] = random.nextInt(10);
        return fi;
    }


}
