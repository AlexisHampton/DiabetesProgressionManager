import java.util.ArrayList;

//CategoricalNode can Branch, calculate its own impurity, and create Children if it needs
public class CategoricalNode extends DecisionTreeNode {

    int numCatFeatures = 2; //since we only have one categorical node we can cheat

    //Initalizes a new cat node with a given info list
    public CategoricalNode(ArrayList<Info> dp) {
        super(dp);
    }

    //Creates a categorical node out of an existing decisionTreeNode
    public CategoricalNode(DecisionTreeNode node)
    {
        super(node.dataPoints);
        super.children = node.children;
        super.leaves = node.leaves;
        super.tempLeaves = node.tempLeaves;
        super.parent = node.parent;
        super.childIndex = node.childIndex;
    }

    //Returns a list of DecisionTreeNodes after it branches
    public ArrayList<DecisionTreeNode> Branch() {

        //Creates children with an index and a parent
        for (int i = 0; i < numCatFeatures; i++) {
            DecisionTreeNode node = new DecisionTreeNode(new ArrayList<Info>());
            node.childIndex = i;
            node.parent = this;
            super.children.add(node);
        }


        //add in the datapoints for each child
        for (int i = 0; i < super.dataPoints.size(); i++) {
            //j starts at one to simulate the sex being either 1 or 2
            for (int j = 1; j < numCatFeatures + 1; j++) {
                if (super.dataPoints.get(i).data.get(1) == (float) j) {
                    super.children.get(j - 1).dataPoints.add(super.dataPoints.get(i));
                    break;
                }
            }
        }

        //create leaves if it can
        super.CreateLeaves();
        return super.children;
    }

    //returns the totalGiniImpurity based off of the currentFeature
    public float CalculateTotalGiniImpurity(int feature) {
        return CreateTempLeaves(feature);
    }

    //returns the total GiniImpurity based off the current feature and the tempLeaves it made out of it
    float CreateTempLeaves(int feature) {
        super.tempLeaves.clear(); //to make sure no data gets corrupted

        //initialize tempLeaves for each branch
        for (int i = 0; i < numCatFeatures; i++) {
            TempLeaf leaf = new TempLeaf(0, 0);
            super.tempLeaves.add(leaf);
        }

        //for each dataPoint, update the tempLeaf
        for (int i = 0; i < super.dataPoints.size(); i++) {
            for (int j = 1; j < super.tempLeaves.size() + 1; j++) { //starting at one to simulate sex being 1 or 2
                if (super.dataPoints.get(i).data.get(feature) == j) {
                    if (super.dataPoints.get(i).isDiseaseProgressionGood)
                        super.tempLeaves.get(j-1).yes++;
                    else
                        super.tempLeaves.get(j-1).no++;
                    break;
                }
            }
        }

        return super.CalculateTotalGiniImpurity();
    }

    //created for debugging purposes
    //but stays just in case
    @Override
    public String toString() {
        return "categorical nc" + numCatFeatures + " c:" + super.children.size() + " l:" + super.leaves.size() + " d:" +  super.dataPoints.size();
    }

}
