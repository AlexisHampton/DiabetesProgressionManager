import java.util.*;

public class DecisionTree {
    ArrayList<Info> trainingData;
    int maxDepth = 6;
    int maxLeaves = 5;
    int leaves, depth = 0;
    int[] featuresIndicies;
    ArrayList<Integer> featuresUsed = new ArrayList<Integer>();

    DecisionTreeNode root;

    public DecisionTree(int[] fi, ArrayList<Info> td) {
        featuresIndicies = fi;
        trainingData = td;
    }

    public boolean GetPrediction(Info info) {
        boolean prediction = false;
        DecisionTreeNode curr = root;
        while (curr != null) {
            //System.out.println("Jimin");
            //check children null here
            int index = 0;
            //leaves null here
            if (curr instanceof CategoricalNode)
                index = (int) (info.data.get(1) - 1);
            else if (curr instanceof  ContinuousNode)
                index = ((ContinuousNode) curr).GetChildIndex(info);
            else
                return curr.LeafPrediction();

            if(curr.children.get(index) == null) {
                for (int i = 0; i < curr.leaves.size(); i++)
                    if (curr.leaves.get(i).index == index)
                        return curr.leaves.get(i).isDiseaseProgressionGood;
                return curr.LeafPrediction();
            }
            else {
                curr = curr.children.get(index);
                //System.out.println("Is Yoongi gettting a child? " + index + "what kind: " + (curr instanceof ContinuousNode) + " " + (curr instanceof  CategoricalNode));
            }

        }

        return prediction;
    }


    public DecisionTree BuildTree() {
        PriorityQueue<DecisionTreeNode> heap = new PriorityQueue<DecisionTreeNode>();

        root = new DecisionTreeNode(trainingData);
        root = FindBestFeature(root);
        Branch(root, heap);
        //System.out.println("rc: " + root.children.size());
        //System.out.println("heap b4 branch: " + heap.size());

        //make root cat or cont
        while (!heap.isEmpty() && maxLeaves != leaves && maxDepth != depth) {
            featuresIndicies = DecisionTreeManager.GetFeatureIndicies(featuresUsed);
            //System.out.println("heap b4 branch: " + heap.size());
            DecisionTreeNode node = heap.poll();
            DecisionTreeNode bestNode = FindBestFeature(node);
            //System.out.println("b4 branch null p: " + (bestNode.parent == null));
            Branch(bestNode, heap);
            //System.out.println("aft branch null p: " + (bestNode.parent == null));
            //System.out.println("heap aft branch: " + heap.size());
            UpdateChildren(bestNode.parent, bestNode);

        }
        return this;
    }

    DecisionTreeNode FindBestFeature(DecisionTreeNode treeNode) {
        //Branches on each feature and calculates impurity for each branch
        DecisionTreeNode bestTreeNode = null;
        float bestImpurity = Float.MAX_VALUE;
        Dictionary<DecisionTreeNode, Float> impurities = new Hashtable<DecisionTreeNode, Float>();
        //ArrayList<Float> impurities = new ArrayList<Float>();
        for (int f = 0; f < featuresIndicies.length; f++) {
            //need to create leaves depending on wheter dp is cat or cont
            if (DecisionTreeManager.catFeatures.contains(featuresIndicies[f])) {
                CategoricalNode catNode = new CategoricalNode(treeNode);
                impurities.put(catNode, catNode.CalculateTotalGIForTempLeaves(featuresIndicies[f]));
               // System.out.println("catfeat: " + f + " tgi: " + catNode.CalculateTotalGIForTempLeaves(f));
            } else {
                ContinuousNode contNode = new ContinuousNode(treeNode);
                impurities.put(contNode, contNode.CalculateTotalGiniImpurity(featuresIndicies[f]));
               // System.out.println("contfeat: " + f + " tgi: " + contNode.CalculateTotalGiniImpurity(f));

            }
        }

        Enumeration<DecisionTreeNode> n = impurities.keys();
        while (n.hasMoreElements()) {
            DecisionTreeNode key = n.nextElement();
            if (impurities.get(key) < bestImpurity) {
                bestImpurity = impurities.get(key);
                bestTreeNode = key;
            }
        }

        return bestTreeNode;
    }

    void Branch(DecisionTreeNode bestNode, PriorityQueue<DecisionTreeNode> heap){
        ArrayList<DecisionTreeNode> children = new ArrayList<DecisionTreeNode>();
        //String meow = "Build Tree: bestNode ";
        if (bestNode instanceof CategoricalNode) {
            featuresUsed.add(1);
           // System.out.println(meow + ((CategoricalNode) bestNode).toString());
            children = ((CategoricalNode) bestNode).Branch();
        } else {
            featuresUsed.add(((ContinuousNode) bestNode).currentFeature);
            //System.out.println(meow + ((ContinuousNode) bestNode).toString());
            children = ((ContinuousNode) bestNode).Branch();
        }

        for (int i = 0; i < children.size(); i++)
            if (children.get(i) != null)
                heap.add(children.get(i));
            else
                leaves++;
        if (!children.isEmpty()) depth++;
    }

    void UpdateChildren(DecisionTreeNode parent, DecisionTreeNode child){
        parent.children.set(child.childIndex,child);
    }

    public void Validate() {

    }


    int CalculateError() {
        return 0;
    }


}
