package Tests;

import Main.ContinuousNode;
import Main.Info;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestContinuousNode {

    @Test
    public void TestCalculateTotalGIForTempLeaves(){
        ArrayList<Info> data = new ArrayList<>(List.of(TestData.data));
        ContinuousNode continuousNode = new ContinuousNode(data);
        float value = continuousNode.CalculateTotalGiniImpurity(TestData.continuousFeature);

        Assert.assertEquals(0, value, 0);
    }

    @Test
    public void TestBranch(){
        ArrayList<Info> data = new ArrayList<>(List.of(TestData.data));
        ContinuousNode continuousNode = new ContinuousNode(data);
        continuousNode.SetFeature(3);
        continuousNode.SetSplitCriteria(95);
        ArrayList<Main.DecisionTreeNode> children = continuousNode.Branch();

        ArrayList<Main.DecisionTreeNode> expected = new ArrayList<>();
        expected.add(null);
        expected.add(null);

        Assert.assertEquals(expected, children);
    }

    @Test
    public void TestGetChildIndex()
    {
        ArrayList<Info> data = new ArrayList<>(List.of(TestData.data));
        ContinuousNode continuousNode = new ContinuousNode(data);
        continuousNode.SetFeature(3);
        continuousNode.SetSplitCriteria(95);
        int value = continuousNode.GetChildIndex(TestData.c);
        Assert.assertEquals(1, value);
    }

}
