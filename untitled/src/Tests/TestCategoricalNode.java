package Tests;

import Main.CategoricalNode;
import Main.DecisionTree;
import Main.DecisionTreeNode;
import Main.Info;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestCategoricalNode {
    @Test
    public void TestCalculateTotalGiniImpurity()
    {
        ArrayList<Info> data = new ArrayList<>(List.of(TestData.data));
        CategoricalNode categoricalNode = new CategoricalNode(data);
        float value = categoricalNode.CalculateTotalGiniImpurity(1);

        Assert.assertEquals(.25f, value, 0 );
    }

    @Test
    public void TestBranch(){
        ArrayList<Info> data = new ArrayList<>(List.of(TestData.data));
        CategoricalNode categoricalNode = new CategoricalNode(data);
        ArrayList<DecisionTreeNode> actual = categoricalNode.Branch();

        DecisionTreeNode child1 = new DecisionTreeNode(categoricalNode, 0,  new ArrayList<>(List.of(TestData.b, TestData.d)));
        ArrayList<DecisionTreeNode> expected = new ArrayList<>();
        expected.add(child1);
        expected.add(null);

        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

}
