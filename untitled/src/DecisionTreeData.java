import java.util.ArrayList;

public class DecisionTreeData {
    ArrayList<Info> testingData = new ArrayList<Info>();
    ArrayList<Info> trainingData = new ArrayList<Info>();
    ArrayList<Integer> trainingDataIndicies = new ArrayList<>();
    DecisionTree decisionTree;

    public void CreateDecisionTree(int[] featureIndicies) {
        decisionTree = new DecisionTree(featureIndicies, trainingData);
        decisionTree.BuildTree();
    }

    public void TestDecisionTree() {
        //using testing Data
        GetTestingData();
        int correct = 0;
        for (int i = 0; i < testingData.size(); i++) {
            boolean pred = decisionTree.GetPrediction(testingData.get(i));
            //System.out.println(testingData.get(i) + " p:" + pred);
            if (pred == testingData.get(i).isDiseaseProgressionGood)
                correct++;
        }
        System.out.println("corect: " + correct + " total: " + testingData.size() + " r:" + (correct / (float) testingData.size()));
    }


    void GetTestingData() {
        for (int i = 0; i < DecisionTreeManager.allData.size(); i++) {
            if (!trainingDataIndicies.contains(i)) {
                //Info info = DecisionTreeManager.allData.get(i);
                //System.out.println("ti(i):" + trainingDataIndicies.get(i) + " !contains:" + (!trainingData.contains(info)) + " info: " + info);

                testingData.add(DecisionTreeManager.allData.get(i));

            }
        }
        System.out.println("test size: " + testingData.size());
    }

}
