import java.util.ArrayList;
import java.util.Comparator;

//Class ContinuousNode Branches, calulates its totalGiniImpurity, and creates leaves if it needs
public class ContinuousNode extends DecisionTreeNode {

    float splitCriteria;
    int currentFeature = 0; //needed for traversing

    //Initializes a ContinuousNode based on a list of Info
    public ContinuousNode(ArrayList<Info> dp) {
        super(dp);
    }

    //Creates a ContinuousNode out of a DecisionTreeNode
    public ContinuousNode(DecisionTreeNode node) {
        super(node.dataPoints);
        super.children = node.children;
        super.leaves = node.leaves;
        super.tempLeaves = node.tempLeaves;
        super.parent = node.parent;
        super.childIndex = node.childIndex;
    }

    //Returns a list of DecisionTreeNodes after it has branched based off of splitCriteria
    public ArrayList<DecisionTreeNode> Branch() {

        //Creates children
        for (int i = 0; i < 2; i++) {
            DecisionTreeNode node = new DecisionTreeNode(new ArrayList<Info>());
            node.childIndex = i;
            node.parent = this;
            super.children.add(node);
        }

        //add in the datapoints for each child
        //less goes in the first, and greater than equal goes into the second child
        for (int i = 0; i < super.dataPoints.size(); i++) {
            int nodeIndex = super.dataPoints.get(i).data.get(currentFeature) < splitCriteria ? 0 : 1;
            super.children.get(nodeIndex).dataPoints.add(super.dataPoints.get(i));
        }


        //make leaves if neccessary
        super.CreateLeaves();

        return super.children;
    }

    //Class DataPointComparator simply houses a compare function that is used to sort dataPoints in the current feature
    class DataPointComparator implements Comparator<Info> {

        //returns a comparision int for two info based on their current feature
        public int compare(Info a, Info b) {
            return a.data.get(currentFeature).compareTo(b.data.get(currentFeature));
        }

    }

    //returns the totalGiniImpurity based off the specified feature
    public float CalculateTotalGiniImpurity(int feature) {
        return CreateTempLeaves(feature);
    }

    //returns the best totalGiniImpurity after trying all possible splits for a given feature
    float CreateTempLeaves(int feature) {
        super.tempLeaves.clear(); //to avoid data corruption
        currentFeature = feature;
        super.dataPoints.sort(new DataPointComparator());

        ArrayList<Float> averageDatapoints = new ArrayList<>();

        //if the data is unbalanced, the dataPoint becomes the average
        if (super.dataPoints.size() == 1)
            averageDatapoints.add(super.dataPoints.get(0).data.get(feature));
        //otherwise, calculate the average for every 2 datapoints
        for (int i = 1; i < super.dataPoints.size(); i++) {
            float total = super.dataPoints.get(i - 1).data.get(feature) + super.dataPoints.get(i).data.get(feature);
            averageDatapoints.add(total / 2);
        }

        ArrayList<Float> totalGiniImpurities = new ArrayList<>();

        //create temp leaves for each average and calculate its totalGiniImpurity
        for (int i = 0; i < averageDatapoints.size(); i++) {
            totalGiniImpurities.add(CalculateTotalGIForTempLeaves(averageDatapoints.get(i)));
            super.tempLeaves.clear();
        }

        //gets the best totalGiniImpurity (that is, the lowest), and its index
        int bestImpurityIndex = 0;
        float bestImpurity = Float.MAX_VALUE;
        for (int i = 0; i < totalGiniImpurities.size(); i++)
            if (totalGiniImpurities.get(i) < bestImpurity) {
                bestImpurity = totalGiniImpurities.get(i);
                bestImpurityIndex = i;
            }

        //the average with the lowestGiniImpurity becomes the spitCriteria
        splitCriteria = averageDatapoints.get(bestImpurityIndex);
        return bestImpurity;
    }

    //returns the totalGiniImpurity for a given split criteria
    float CalculateTotalGIForTempLeaves(float splitC) {

        //need only two branches because its continuous
        for (int i = 0; i < 2; i++) {
            TempLeaf leaf = new TempLeaf(0, 0);
            super.tempLeaves.add(leaf);
        }

        //for each datapoint, calculate the amounts of yes's and no's
        for (int i = 0; i < super.dataPoints.size(); i++) {
            int tempLeafIndex = super.dataPoints.get(i).data.get(currentFeature) < splitC ? 0 : 1;
            if (super.dataPoints.get(i).isDiseaseProgressionGood)
                super.tempLeaves.get(tempLeafIndex).yes++;
            else
                super.tempLeaves.get(tempLeafIndex).no++;

        }
        return super.CalculateTotalGiniImpurity();
    }

    //returns an index of the child that should be traversed
    //0 for less than
    //1 for greater than equal
    int GetChildIndex(Info info) {
        return info.data.get(currentFeature) < splitCriteria ? 0 : 1;
    }

    //for debugging purposes, might delete when project is finished
    @Override
    public String toString() {
        return "continous sc " + splitCriteria + " c:" + super.children.size() + " l:" + super.leaves.size() + " d:" + super.dataPoints.size();
    }
}
