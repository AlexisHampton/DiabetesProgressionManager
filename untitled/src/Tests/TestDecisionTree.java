package Tests;

import Main.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class TestDecisionTree {

    public ArrayList<Info> PopulateTrainingData() {
        ArrayList<Info> data = new ArrayList<>(List.of(TestData.a, TestData.b, TestData.c,
                TestData.d, TestData.i, TestData.j, TestData.k, TestData.l, TestData.m, TestData.n));
        return data;
    }

    public ArrayList<Info> PopulateTestingData() {
        ArrayList<Info> data = new ArrayList<>(List.of(TestData.e, TestData.f, TestData.g,
                TestData.g));
        return data;
    }

    @Test
    public void TestBuildTree() {
        ArrayList<Info> trainingData = PopulateTrainingData();
        ArrayList<Info> testingData = PopulateTestingData();
        ArrayList<int[]> features = new ArrayList<>();
        features.add(new int[]{1, 3, 5});
        features.add(new int[]{2, 4, 6});
        features.add(new int[]{8, 9, 7});

        DecisionTree actual = new DecisionTree(features, trainingData, testingData, null);
        actual.BuildTree();
        ArrayList<DecisionTreeNode> rootChildren = new ArrayList<>();
        ArrayList<DecisionTreeNode> rootchild0children = new ArrayList<>();
        ArrayList<DecisionTreeNode> rootchild0child1Children = new ArrayList<>();

        ArrayList<DecisionTreeLeaf> rootLeaves = new ArrayList<>();
        ArrayList<DecisionTreeLeaf> rootChild1Leaves = new ArrayList<>();
        ArrayList<DecisionTreeLeaf> rootChild0Leaves = new ArrayList<>();
        ArrayList<DecisionTreeLeaf> rootChild0Child1Leaves = new ArrayList<>();

        ContinuousNode root = new ContinuousNode(trainingData, null, new ArrayList<>(), new ArrayList<>(), 0, 3, 107.5f);

        rootLeaves.add(new DecisionTreeLeaf(false, 1));

        rootChild1Leaves.add(new DecisionTreeLeaf(true, 0));
        rootChild1Leaves.add(new DecisionTreeLeaf(false, 1));

        rootChild0Leaves.add(new DecisionTreeLeaf(true, 0));

        rootChild0Child1Leaves.add(new DecisionTreeLeaf(false, 0));
        rootChild0Child1Leaves.add(new DecisionTreeLeaf(true, 1));

        rootchild0child1Children.add(null);
        rootchild0child1Children.add(null);

        ContinuousNode rootChild0 = new ContinuousNode(new ArrayList<Info>(List.of(TestData.a, TestData.b, TestData.i, TestData.k, TestData.l, TestData.n)), root, rootchild0children, rootChild1Leaves, 0, 2, 25.150002f);


        rootchild0children.add(0, null);
        rootchild0children.add(1, new ContinuousNode(new ArrayList<Info>(List.of(TestData.a, TestData.l, TestData.k)), rootChild0, rootchild0child1Children, rootChild0Child1Leaves, 1, 9, 90.5f));

        rootChildren.add(0, rootChild0);
        rootChildren.add(1, null);

        root.SetChildren(rootChildren);
        root.SetLeaves(rootLeaves);

        rootChild0.SetChildren(rootchild0children);
        rootChild0.SetLeaves(rootChild0Leaves);

        rootchild0children.get(1).SetLeaves(rootChild0Child1Leaves);
        rootchild0children.get(1).SetChildren(rootchild0child1Children);

        DecisionTree expected = new DecisionTree(features, trainingData, testingData, root);

        //make the tree
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void TestFindBestFeature() {
        //make and test too
        ArrayList<Info> trainingData = PopulateTrainingData();
        ArrayList<Info> testingData = PopulateTestingData();
        ArrayList<int[]> features = new ArrayList<>();
        features.add(new int[]{1, 3, 5});

        DecisionTree dt = new DecisionTree(features, trainingData, testingData, null);
        DecisionTreeNode root = new DecisionTreeNode(trainingData);

        DecisionTreeNode actual = dt.FindBestFeature(root);

        ContinuousNode expected = new ContinuousNode(trainingData, null, new ArrayList<>(), new ArrayList<>(), 0, 3, 107.5f);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void TestBranch() {
        ArrayList<Info> trainingData = PopulateTrainingData();
        ArrayList<Info> testingData = PopulateTestingData();
        ArrayList<int[]> features = new ArrayList<>();
        features.add(new int[]{1, 3, 5});

        ContinuousNode actualRoot = new ContinuousNode(trainingData, null, new ArrayList<>(), new ArrayList<>(), 0, 3, 107.5f);
        ContinuousNode expectedrRoot = new ContinuousNode(trainingData, null, new ArrayList<>(), new ArrayList<>(), 0, 3, 107.5f);

        DecisionTree actual = new DecisionTree(features, trainingData, testingData, actualRoot);
        actual.Branch(actualRoot, new PriorityQueue<>());

        DecisionTree expected = new DecisionTree(features, trainingData, testingData, expectedrRoot);
        ArrayList<DecisionTreeNode> rootChildren = new ArrayList<>();

        rootChildren.add(new DecisionTreeNode(expectedrRoot, 0, new ArrayList<Info>(List.of(TestData.a, TestData.b, TestData.i, TestData.k, TestData.l, TestData.n))));
        rootChildren.add(null);

        ArrayList<DecisionTreeLeaf> leaves = new ArrayList<>();
        leaves.add(new DecisionTreeLeaf(false, 1));

        expectedrRoot.SetChildren(rootChildren);
        expectedrRoot.SetLeaves(leaves);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void TestGetPrediction() {

        ArrayList<Info> trainingData = PopulateTrainingData();
        ArrayList<Info> testingData = PopulateTestingData();
        ArrayList<int[]> features = new ArrayList<>();
        features.add(new int[]{1, 3, 5});
        features.add(new int[]{2, 4, 6});
        features.add(new int[]{8, 9, 7});

        ContinuousNode root = new ContinuousNode(trainingData, null, new ArrayList<>(), new ArrayList<>(), 0, 3, 107.5f);

        ArrayList<DecisionTreeNode> rootChildren = new ArrayList<>();
        ArrayList<DecisionTreeNode> rootchild0children = new ArrayList<>();
        ArrayList<DecisionTreeNode> rootchild0child1Children = new ArrayList<>();

        ArrayList<DecisionTreeLeaf> rootLeaves = new ArrayList<>();
        ArrayList<DecisionTreeLeaf> rootChild1Leaves = new ArrayList<>();
        ArrayList<DecisionTreeLeaf> rootChild0Leaves = new ArrayList<>();
        ArrayList<DecisionTreeLeaf> rootChild0Child1Leaves = new ArrayList<>();


        rootLeaves.add(new DecisionTreeLeaf(false, 1));

        rootChild1Leaves.add(new DecisionTreeLeaf(true, 0));
        rootChild1Leaves.add(new DecisionTreeLeaf(false, 1));

        rootChild0Leaves.add(new DecisionTreeLeaf(true, 0));

        rootChild0Child1Leaves.add(new DecisionTreeLeaf(false, 0));
        rootChild0Child1Leaves.add(new DecisionTreeLeaf(true, 1));

        rootchild0child1Children.add(null);
        rootchild0child1Children.add(null);

        ContinuousNode rootChild0 = new ContinuousNode(new ArrayList<Info>(List.of(TestData.a, TestData.b, TestData.i, TestData.k, TestData.l, TestData.n)), root, rootchild0children, rootChild1Leaves, 0, 2, 25.150002f);


        rootchild0children.add(0, null);
        rootchild0children.add(1, new ContinuousNode(new ArrayList<Info>(List.of(TestData.a, TestData.l, TestData.k)), rootChild0, rootchild0child1Children, rootChild0Child1Leaves, 1, 9, 90.5f));

        rootChildren.add(0, rootChild0);
        rootChildren.add(1, null);

        root.SetChildren(rootChildren);
        root.SetLeaves(rootLeaves);

        rootChild0.SetChildren(rootchild0children);
        rootChild0.SetLeaves(rootChild0Leaves);

        rootchild0children.get(1).SetLeaves(rootChild0Child1Leaves);
        rootchild0children.get(1).SetChildren(rootchild0child1Children);

        DecisionTree tree = new DecisionTree(features, trainingData, testingData, root);
        boolean actual = tree.GetPrediction(TestData.g);

        Assert.assertEquals(true, actual);

    }


}
