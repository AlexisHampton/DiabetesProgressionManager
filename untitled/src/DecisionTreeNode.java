import java.util.ArrayList;

public class DecisionTreeNode implements Comparable<DecisionTreeNode>{

    ArrayList<Info> dataPoints = new ArrayList<Info>();
    ArrayList<DecisionTreeNode> children = new ArrayList<>();
    ArrayList<DecisionTreeLeaf> leaves = new ArrayList<DecisionTreeLeaf>();
    ArrayList<TempLeaf> tempLeaves = new ArrayList<TempLeaf>();
    DecisionTreeNode parent = null;
    int childIndex;
    public DecisionTreeNode(ArrayList<Info> dp) {
        dataPoints = new ArrayList<Info>(dp);
    }


    float CalculateTotalGiniImpurity() {
        float weightedImpurities = 0;

        for (int i = 0; i < tempLeaves.size(); i++) {
            weightedImpurities += ((tempLeaves.get(i).yes + tempLeaves.get(i).no) / dataPoints.size()) * tempLeaves.get(i).CalculateImpurity();
            //System.out.println("wi " + i + " " + weightedImpurities + " y:" + tempLeaves.get(i).yes + " n:" + tempLeaves.get(i).no);
        }
        return weightedImpurities;
    }

    public float CalculateImpurity() {
        int yes = CountOutput(), no = dataPoints.size() - CountOutput();
        float total = dataPoints.size();
        yes /= total;
        no /= total;
        return 1- yes*yes - no*no;
    }

    void CreateLeaves() {
        for (int i = 0; i < children.size(); i++) {
            int canLeaf = children.get(i).CanMakeLeaf();
            if ( canLeaf == 1) {
                //System.out.println( i + " child size" + children.get(i).dataPoints.size());
                DecisionTreeLeaf decisionTreeLeaf = new DecisionTreeLeaf(children.get(i).dataPoints.get(0).isDiseaseProgressionGood, i);
                leaves.add(decisionTreeLeaf);
                children.set(i, null);
            }
            else if(canLeaf == -1)
            {
                children.set(i, null);
            }
        }
    }

    int CanMakeLeaf() {
        int yes = CountOutput();
        int no = dataPoints.size() - yes;
        System.out.println("y:" + yes + " n:" + no);
        if(yes == 0 && no == 0) return -1;
        if(yes == 0 || no == 0) return 1;
        return 0;

    }

    int CountOutput()
    {
        int yes = 0;
        for (int i = 0; i < dataPoints.size(); i++)
            if (dataPoints.get(i).isDiseaseProgressionGood)
                yes++;
        return yes;
    }

    public boolean LeafPrediction()
    {
        int yes = CountOutput();
        int no = dataPoints.size() - yes;
        return yes > no ? true : false;
    }



    @Override
    public int compareTo(DecisionTreeNode o) {
        return Float.compare(CalculateImpurity(), o.CalculateImpurity());
    }
}
