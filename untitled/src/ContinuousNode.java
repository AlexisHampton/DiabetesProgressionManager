import java.util.ArrayList;
import java.util.Comparator;

public class ContinuousNode extends DecisionTreeNode {
    float splitCriteria;
    int currentFeature = 0;

    public ContinuousNode(ArrayList<Info> dp) {
        super(dp);
    }

    public ContinuousNode(DecisionTreeNode node)
    {
        super(node.dataPoints);
        super.children = node.children;
        super.leaves = node.leaves;
        super.tempLeaves = node.tempLeaves;
        super.parent = node.parent;
        super.childIndex = node.childIndex;
    }

    public ArrayList<DecisionTreeNode> Branch() {

        //System.out.println("split:" + splitCriteria);
        for (int i = 0; i < 2; i++) {
            DecisionTreeNode node = new DecisionTreeNode(new ArrayList<Info>());
            node.childIndex = i;
            node.parent = this;
            super.children.add(node);
        }

        //add in the datapoints
        for (int i = 0; i < super.dataPoints.size(); i++) {
            int nodeIndex = super.dataPoints.get(i).data.get(currentFeature) < splitCriteria ? 0 : 1;
            super.children.get(nodeIndex).dataPoints.add(super.dataPoints.get(i));
        }

        //System.out.println("CN children first "+ super.children.get(0).dataPoints.size() + " second " + super.children.get(1).dataPoints.size());

        //make leaves if neccessary
        super.CreateLeaves();

        return super.children;
    }


    class DataPointComparator implements Comparator<Info> {

        public int compare(Info a, Info b) {
            return a.data.get(currentFeature).compareTo(b.data.get(currentFeature));
        }

    }


    public float CalculateTotalGiniImpurity(int feature) {
        return CreateTempLeaves(feature);
    }

    float CreateTempLeaves(int feature) {
        super.tempLeaves.clear();
        currentFeature = feature;
        super.dataPoints.sort(new DataPointComparator());

        ArrayList<Float> averageDatapoints = new ArrayList<>();

        //get the averages of every 2 ages.
        if(super.dataPoints.size() == 1)
            averageDatapoints.add(super.dataPoints.get(0).data.get(feature));
        for (int i = 1; i < super.dataPoints.size(); i++) {
            float total = super.dataPoints.get(i - 1).data.get(feature) + super.dataPoints.get(i).data.get(feature);
            averageDatapoints.add(total / 2);
        }

        ArrayList<Float> totalGiniImpurities = new ArrayList<>();
        //create temp leaves for each average
        for (int i = 0; i < averageDatapoints.size(); i++) {
            totalGiniImpurities.add(CalculateTotalGIForTempLeaves(averageDatapoints.get(i)));
            super.tempLeaves.clear();
        }

        // return where to split using the bestImpurity Index
        int bestImpurityIndex = 0;
        float bestImpurity = Float.MAX_VALUE;
        for (int i = 0; i < totalGiniImpurities.size(); i++)
            if (totalGiniImpurities.get(i) < bestImpurity) {
                bestImpurity = totalGiniImpurities.get(i);
                bestImpurityIndex = i;
            }

        splitCriteria = averageDatapoints.get(bestImpurityIndex);
        //System.out.println("real sc: " + splitCriteria);
        return bestImpurity;
    }

    float CalculateTotalGIForTempLeaves(float splitC) {

        //need only two branches because its continuous
        for (int i = 0; i < 2; i++) {
            TempLeaf leaf = new TempLeaf(0, 0);
            super.tempLeaves.add(leaf);
        }

        //for each datapoint, calculate the
        for (int i = 0; i < super.dataPoints.size(); i++) {
            int tempLeafIndex = super.dataPoints.get(i).data.get(currentFeature) < splitC ? 0 : 1;
            //System.out.println("cn: split: " + splitC + super.dataPoints.get(i).data.get(currentFeature) + " < splitc? " + (super.dataPoints.get(i).data.get(currentFeature) < splitC) + " in:" + tempLeafIndex);
            if (super.dataPoints.get(i).isDiseaseProgressionGood)
                super.tempLeaves.get(tempLeafIndex).yes++;
            else
                super.tempLeaves.get(tempLeafIndex).no++;
            //System.out.println("cn: tl" + tempLeafIndex + " y:" + super.tempLeaves.get(tempLeafIndex).yes + " n:" + super.tempLeaves.get(tempLeafIndex).no);

        }
        //System.out.println( "curr fea: "+ currentFeature + " splitC: " + splitC + " tgi: " + super.CalculateTotalGiniImpurity());
        return super.CalculateTotalGiniImpurity();
    }

    int GetChildIndex(Info info)
    {
        return info.data.get(currentFeature) < splitCriteria ? 0:1;
    }


    @Override
    public String toString() {
        return "continous sc " + splitCriteria + " c:" + super.children.size() + " l:" + super.leaves.size() + " d:" + super.dataPoints.size();
    }
}
