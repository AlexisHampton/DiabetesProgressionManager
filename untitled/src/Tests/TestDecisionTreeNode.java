package Tests;

import Main.CategoricalNode;
import Main.DecisionTreeNode;
import Main.Info;
import Main.TempLeaf;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestDecisionTreeNode {

    ArrayList<Info> data = new ArrayList<>(List.of(TestData.data));

    @Test
    public void TestLeafPrediction()
    {
        DecisionTreeNode node = new DecisionTreeNode(data);
        boolean value = node.LeafPrediction();

        Assert.assertEquals(false, value);
    }

    @Test
    public void TestCanMakeLeaf(){
        DecisionTreeNode node = new DecisionTreeNode(data);
        int actual = node.CanMakeLeaf();

        Assert.assertEquals(0, actual);
    }

    @Test
    public void TestCanMakeLeaf2(){

        ArrayList<Info> newData = new ArrayList<>();
        newData.add(new Info(50, 2, 26.2f, 97, 186, 105.4f, 49, 4, 5.0626f, 88, true));
        newData.add(new Info(61, 1, 24, 91, 202, 115.4f, 72, 3, 4.2905f, 73, true));
        newData.add(new Info(34, 2, 24.7f, 118, 254, 184.2f, 39, 7, 5.037f, 81, true));
        newData.add(new Info(47, 1, 30.3f, 109, 207, 100.2f, 70, 3, 5.2149f, 98, true));

        DecisionTreeNode node = new DecisionTreeNode(newData);
        int actual = node.CanMakeLeaf();

        Assert.assertEquals(1, actual);
    }

    @Test
    public void TestCanMakeLeaf3(){

        ArrayList<Info> newData = new ArrayList<>();

        DecisionTreeNode node = new DecisionTreeNode(newData);
        int actual = node.CanMakeLeaf();

        Assert.assertEquals(-1, actual);
    }

}
