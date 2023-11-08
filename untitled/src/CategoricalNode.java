import java.util.ArrayList;

public class CategoricalNode extends DecisionTreeNode {

    int numCatFeatures = 2;

    public CategoricalNode(ArrayList<Info> dp) {
        super(dp);
    }

    public CategoricalNode(DecisionTreeNode node)
    {
        super(node.dataPoints);
        super.children = node.children;
        super.leaves = node.leaves;
        super.tempLeaves = node.tempLeaves;
        super.parent = node.parent;
        super.childIndex = node.childIndex;
    }


    public ArrayList<DecisionTreeNode> Branch() {
        for (int i = 0; i < numCatFeatures; i++) {
            DecisionTreeNode node = new DecisionTreeNode(new ArrayList<Info>());
            node.childIndex = i;
            node.parent = this;
            super.children.add(node);
        }

        //add in the datapoints
        for (int i = 0; i < super.dataPoints.size(); i++) {
            for (int j = 1; j < numCatFeatures + 1; j++) {
                //System.out.println( super.dataPoints.get(i).data.get(1) + " == " + j + ": " + (super.dataPoints.get(i).data.get(1) == j));
                if (super.dataPoints.get(i).data.get(1) == (float) j) {
                    super.children.get(j - 1).dataPoints.add(super.dataPoints.get(i));
                    break;
                }
            }
        }


        //create leaves
        super.CreateLeaves();
        return super.children;
    }

    public float CalculateTotalGIForTempLeaves(int feature) {
        return CreateTempLeaves(feature);
    }

    float CreateTempLeaves(int feature) {
        super.tempLeaves.clear();
        //initialize tempLeaves for each branch
        for (int i = 0; i < numCatFeatures; i++) {
            TempLeaf leaf = new TempLeaf(0, 0);
            super.tempLeaves.add(leaf);
        }

        //for each dataPoint, update the tempLeaf
        for (int i = 0; i < super.dataPoints.size(); i++) {
            for (int j = 1; j < super.tempLeaves.size() + 1; j++) {
                //System.out.println( super.dataPoints.get(i).data.get(feature)+ " == " + j + " " + (super.dataPoints.get(i).data.get(feature) == j));
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

    @Override
    public String toString() {
        return "categorical nc" + numCatFeatures + " c:" + super.children.size() + " l:" + super.leaves.size() + " d:" +  super.dataPoints.size();
    }

}
