import java.util.ArrayList;

public class RandomForest {
    ArrayList<DecisionTree> decisionTrees;

    boolean GetPrediction(Info info)
    {
        int numFalse  = 0;
        int numTrue = 0;
        for(int i = 0; i < decisionTrees.size(); i++)
            if(decisionTrees.get(i).GetPrediction(info))
                numTrue++;
            else
                numFalse++;
        return numTrue >= numFalse ? true : false;
    }

    void AddTrees(ArrayList<DecisionTree> dt)
    {
        decisionTrees = dt;
    }

}
