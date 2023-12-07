package Tests;

import Main.Info;
import Main.RandomForest;
import org.junit.Assert;
import org.junit.Test;

import static Main.Main.allInfo;

public class TestInfo {

    @Test
    public void TestMissingData()
    {
        Main.Main.ParseData();
        RandomForest randomForest = new RandomForest(allInfo);

        Info info = new Info(50, 2, 0, 97, 0, 105.4f, 0, 0, 5.0626f, 88, false);
        Info expected = new Info(50, 2, 26.375778f, 97, 189.14027f, 105.4f, 49.78846f, 4.070249f, 5.0626f, 88, false);
        Assert.assertEquals(expected, info);
    }

    @Test
    public void TestMissingDataNegatives()
    {
        Main.Main.ParseData();
        RandomForest randomForest = new RandomForest(allInfo);

        Info info = new Info(50, 2, 26.2f,-1, -186, 105.4f, 49, 4, 5.0626f,88, false);
        Info expected = new Info(50, 2, 26.2f, 94.647026f, 189.14027f, 105.4f, 49, 4, 5.0626f, 88, false);
        Assert.assertEquals(expected, info);
    }


}
