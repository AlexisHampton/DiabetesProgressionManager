import java.util.ArrayList;

public class RandomForest {
    ArrayList<DecisionTreeData> decisionTrees = new ArrayList<DecisionTreeData>();

    boolean GetPrediction(Info info)
    {
        int numFalse  = 0;
        int numTrue = 0;
        for(int i = 0; i < decisionTrees.size(); i++)
            if(decisionTrees.get(i).decisionTree.GetPrediction(info))
                numTrue++;
            else
                numFalse++;
        return numTrue >= numFalse ? true : false;
    }


}
