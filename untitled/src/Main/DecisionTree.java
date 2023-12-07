package Main;

import java.util.*;

//Class Main.DecisionTree trains a Main.DecisionTree, Traverses it to get a prediction, and then Tests it to see its correctness
public class DecisionTree {

    ArrayList<Info> trainingData;
    ArrayList<Info> testingData = new ArrayList<Info>();

    int maxDepth = 45;
    int maxLeaves = 50;
    int leaves, depth = 0;
    int[] featuresIndicies; //the features that have been selected to train the dt

    int maxFeatures;
    static Random random = new Random();
    ArrayList<Integer> featuresUsed = new ArrayList<Integer>();
    DecisionTreeNode root;

    //for Junit testing:
    boolean testing = false;
    int testFeatureIndex = -1;
    ArrayList<int[]> testingFeatures = new ArrayList<>();


    //Initializes a Main.DecisionTree with the max features, training Data and testing Data, and builds the tree
    public DecisionTree(int mf, ArrayList<Info> td, ArrayList<Info> testd) {
        maxFeatures = mf;
        trainingData = td;
        testingData = testd;
        featuresIndicies = GetFeatureIndicies(new ArrayList<>());
        BuildTree();
    }

    public DecisionTree(ArrayList<int[]> features, ArrayList<Info> td, ArrayList<Info> testd, DecisionTreeNode r) {
        testing = true;
        testingFeatures = features;
        trainingData = td;
        testingData = testd;
        featuresIndicies = GetFeatureIndicies(null);
        root = r;
    }


    //returns a boolen prediction from the decision tree using a specified Main.Info
    public boolean GetPrediction(Info info) {
        boolean prediction = false;
        DecisionTreeNode curr = root;

        while (curr != null) {
            int index = 0;

            if (curr instanceof CategoricalNode)
                index = (int) (info.data.get(1) - 1); //gets the sex of the patient and subtracts one to get the index
            else if (curr instanceof ContinuousNode)
                index = ((ContinuousNode) curr).GetChildIndex(info);
            else //curr is a Main.DecisionTreeNode that hasn't been processed
                return curr.LeafPrediction();

            //if current node does not have this child, then check the leaves
            if (curr.children.get(index) == null) {
                for (int i = 0; i < curr.leaves.size(); i++)
                    if (curr.leaves.get(i).index == index)
                        return curr.leaves.get(i).isDiseaseProgressionGood;
                //if neither leaf exists, curr likely wasn't processed yet, so return its prediction
                return curr.LeafPrediction();
            } else { //otherwise continue traversing through the children
                curr = curr.children.get(index);
            }

        }
        //if curr is somehow null but likelier to not get a runtime error:
        return prediction;
    }

    //Builds and returns a Main.DecisionTree based on the best features
    public DecisionTree BuildTree() {
        PriorityQueue<DecisionTreeNode> heap = new PriorityQueue<DecisionTreeNode>();

        root = new DecisionTreeNode(trainingData);
        root = FindBestFeature(root);
        Branch(root, heap);

        while (!heap.isEmpty() && maxLeaves != leaves && maxDepth != depth) {
            //generates new feature Indicies for each node so long as they haven't been used
            featuresIndicies = GetFeatureIndicies(featuresUsed);
            DecisionTreeNode node = heap.poll();
            DecisionTreeNode bestNode = FindBestFeature(node);
            Branch(bestNode, heap);
            UpdateChildren(bestNode.parent, bestNode);
        }

        return this;
    }

    //Finds the best Main.DecisionTreeNode to branch on based on it's total Gini Impurity and returns it
    public DecisionTreeNode FindBestFeature(DecisionTreeNode treeNode) {
        DecisionTreeNode bestTreeNode = null;
        float bestImpurity = Float.MAX_VALUE;
        //If we didn't have a dictionary, we'd need two different ArrayLists
        //Also dictionaries are easier to understand
        Dictionary<DecisionTreeNode, Float> impurities = new Hashtable<DecisionTreeNode, Float>();

        for (int f = 0; f < featuresIndicies.length; f++) {
            //if the feature is categorical
            if (RandomForest.catFeatures.contains(featuresIndicies[f])) {
                CategoricalNode catNode = new CategoricalNode(treeNode);
                impurities.put(catNode, catNode.CalculateTotalGiniImpurity(featuresIndicies[f]));
            } else { //if feature is continuous
                ContinuousNode contNode = new ContinuousNode(treeNode);
                impurities.put(contNode, contNode.CalculateTotalGiniImpurity(featuresIndicies[f]));
            }
        }

        //to interate over the dictionary
        Enumeration<DecisionTreeNode> n = impurities.keys();
        //finds the best impurity which is the lowest impurity
        while (n.hasMoreElements()) {
            DecisionTreeNode key = n.nextElement();
            if (impurities.get(key) < bestImpurity) {
                bestImpurity = impurities.get(key);
                bestTreeNode = key;
            }
        }

        return bestTreeNode;
    }

    //Branches on a given node and adds all new DecisionTreeNodes to the heap
    public void Branch(DecisionTreeNode bestNode, PriorityQueue<DecisionTreeNode> heap) {
        ArrayList<DecisionTreeNode> children = new ArrayList<DecisionTreeNode>();
        if (bestNode instanceof CategoricalNode) {
            featuresUsed.add(1);
            children = ((CategoricalNode) bestNode).Branch();
        } else { //then continuous
            featuresUsed.add(((ContinuousNode) bestNode).currentFeature);
            children = ((ContinuousNode) bestNode).Branch();
        }

        for (int i = 0; i < children.size(); i++)
            if (children.get(i) != null)
                heap.add(children.get(i));
            else //if no children, then have leaves
                leaves++;
        if (!children.isEmpty())
            depth++; //this is wrong because it doesn't calulate the actual depth so it does need to be moved
    }

    //Makes parents aware of their new children
    //adds the children to the parent's childrenList
    void UpdateChildren(DecisionTreeNode parent, DecisionTreeNode child) {
        parent.children.set(child.childIndex, child);
    }

    //Tests the decision Tree and prints its accuracy
    public float TestDecisionTree() {
        int correct = 0;
        for (int i = 0; i < testingData.size(); i++) {
            boolean pred = GetPrediction(testingData.get(i));
            if (pred == testingData.get(i).isDiseaseProgressionGood)
                correct++;
        }
        System.out.println("correct: " + correct + " size: " + testingData.size() + " m:" + (correct / (float) testingData.size()));
        return (correct / (float) testingData.size());
    }


    //Returns a random feature index that has not been used already
    int GetRandomFeature(ArrayList<Integer> featuresUsed) {
        int num = random.nextInt(10);
        //if all features have been used, start over
        if (featuresUsed.size() == 10) featuresUsed.clear();
        if (featuresUsed == null || featuresUsed.contains(num))
            return GetRandomFeature(featuresUsed);
        return num; //return rand feature index
    }

    //Returns an array of indicies for features based on the features already used
    int[] GetFeatureIndicies(ArrayList<Integer> featuresUsed) {
        if (testing) return GetTestingFeatureIndicies();
        int[] fi = new int[maxFeatures];
        for (int i = 0; i < maxFeatures; i++)
            fi[i] = GetRandomFeature(featuresUsed);
        return fi;
    }

    int[] GetTestingFeatureIndicies() {
        testFeatureIndex++;
        return testingFeatures.get(testFeatureIndex % testingFeatures.size());
    }

    @Override
    public String toString() {
        String str = "";
        Stack<DecisionTreeNode> nodes = new Stack<DecisionTreeNode>();
        nodes.push(root);
        while (!nodes.isEmpty()) {
            DecisionTreeNode node = nodes.pop();
            if (node == null) continue;
            str +=  node + "\n";
            for (int i = 0; i < node.children.size(); i++)
                nodes.push(node.children.get(i));
            for (int i = 0; i < node.leaves.size(); i++)
                if (node.leaves.get(0) != null)
                    str += node.leaves.get(i) + "\n";
        }

        return str;
    }
}
