import java.util.ArrayList;

//Class DecisionTreeNode houses its children, its dataPoints, its leaves, and its parent
//It calculates its TotalGiniUImpurity, creates leaves and generates a prediction if it hasn't been processed yet
// (that is, it hasn't become a cat or cont node)
public class DecisionTreeNode implements Comparable<DecisionTreeNode>{

    ArrayList<Info> dataPoints = new ArrayList<Info>();
    ArrayList<DecisionTreeNode> children = new ArrayList<>();
    ArrayList<DecisionTreeLeaf> leaves = new ArrayList<DecisionTreeLeaf>();
    ArrayList<TempLeaf> tempLeaves = new ArrayList<TempLeaf>();
    DecisionTreeNode parent = null;
    int childIndex;

    //Initializes a DecisionTreeNode based on a given list of Info points
    public DecisionTreeNode(ArrayList<Info> dp) {
        dataPoints = new ArrayList<Info>(dp);
    }

    //returns the totalGiniImpurity, calulated using the totalGiniImpurity formula
    float CalculateTotalGiniImpurity() {
        float weightedImpurities = 0;

        for (int i = 0; i < tempLeaves.size(); i++) {
            weightedImpurities += ((tempLeaves.get(i).yes + tempLeaves.get(i).no) / dataPoints.size()) * tempLeaves.get(i).CalculateImpurity();
        }
        return weightedImpurities;
    }

    //returns the impurity of a node
    //Used to sort the nodes into the heap
    public float CalculateImpurity() {
        int yes = CountOutput(), no = dataPoints.size() - CountOutput();
        float total = dataPoints.size();
        yes /= total;
        no /= total;
        return 1- yes*yes - no*no;
    }

    //Creates Leaves that replace each child
    void CreateLeaves() {
        for (int i = 0; i < children.size(); i++) {
            int canLeaf = children.get(i).CanMakeLeaf();
            if ( canLeaf == 1) {
                DecisionTreeLeaf decisionTreeLeaf = new DecisionTreeLeaf(children.get(i).dataPoints.get(0).isDiseaseProgressionGood, i);
                leaves.add(decisionTreeLeaf);
                //if we set it to null instead of removing it, we can keep the index
                //this is purely for making traversal easier
                children.set(i, null);
            }
            //if dataPoints is empty then we don't have a child
            else if(canLeaf == -1)
            {
                children.set(i, null);
            }
        }
    }

    //returns an int that determines whether or not it can make leaves
    //-1 if dataPoints is empty
    //0 if it can't
    //1 if it can
    int CanMakeLeaf() {
        int yes = CountOutput();
        int no = dataPoints.size() - yes;
        if(yes == 0 && no == 0) return -1;
        if(yes == 0 || no == 0) return 1;
        return 0;
    }

    //returns the total amount of isDiseaseProgressionGood = true, for all datapoints
    int CountOutput()
    {
        int yes = 0;
        for (int i = 0; i < dataPoints.size(); i++)
            if (dataPoints.get(i).isDiseaseProgressionGood)
                yes++;
        return yes;
    }

    //returns a bool based on the majority vote
    public boolean LeafPrediction()
    {
        int yes = CountOutput();
        int no = dataPoints.size() - yes;
        return yes > no ? true : false;
    }



    //Compares this DTN to another DTN based on the impurity of the node
    @Override
    public int compareTo(DecisionTreeNode o) {
        return Float.compare(CalculateImpurity(), o.CalculateImpurity());
    }
}
