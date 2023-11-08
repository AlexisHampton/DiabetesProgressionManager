import java.io.File;
import java.util.ArrayList;

public class DecisionTreeManager {

    ArrayList<DecisionTree> decisionTrees = new ArrayList<>();
    RandomForest randomForest;
    ArrayList<Info> allData;
    ArrayList<Info> testingData = new ArrayList<Info>();
    ArrayList<Info> trainingData = new ArrayList<Info>();

    public static ArrayList<Integer> catFeatures = new ArrayList<>();

    public DecisionTreeManager(ArrayList<Info> ad){
        allData = ad;
        catFeatures.add(1);
        catFeatures.add(10);
    }
    public static float GetAverage(int featureIndex) {
        //reutrns the average for a given datapoint
        return 0;
    }

    void TestDecisionTree(DecisionTree dc) {
        //using testing Data
        int correct = 0;
        for(int i = 0; i < testingData.size();i++) {
            boolean pred = dc.GetPrediction(testingData.get(i));
            System.out.println(testingData.get(i) + " p:" + pred);
            if(pred == testingData.get(i).isDiseaseProgressionGood)
                correct++;
        }
        System.out.println("corect: " + correct + " total: " + testingData.size() + " r:" + (correct / (float) testingData.size()));
    }

    void CreateDecisionTrees() {

        float[] featureIndicies = GetFeatureIndicies(10);
        trainingData = GetTrainingData(380);
        DecisionTree decisionTree = new DecisionTree(featureIndicies, trainingData);
        decisionTree.BuildTree();
        TestDecisionTree(decisionTree);

    }

    void CreateRandomForest() {

    }

    void GetRandomDataPoint() {

    }

    int GetRandomFeature() {
        return 0; //return rand feature index
    }

    ArrayList<Info> GetTrainingData(int split) {
        ArrayList<Info> td = new ArrayList<Info>();
        for (int i = 0; i < split; i++)
            td.add(allData.get(i));
        for (int i = split; i < allData.size(); i++)
            testingData.add(allData.get(i));
        return td;
    }

    float[] GetFeatureIndicies(int num) {
        float[] fi = new float[num];
        for (int i = 0; i < num; i++)
            fi[i] = i;
        return fi;
    }


}
