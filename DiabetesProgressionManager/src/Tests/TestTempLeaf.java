package Tests;

import Main.TempLeaf;
import org.junit.Assert;
import org.junit.Test;

public class TestTempLeaf {

    @Test
    public void TestCalculateImpurity()
    {
        TempLeaf tempLeaf = new TempLeaf(5, 6);
        float value = tempLeaf.CalculateImpurity();
        Assert.assertEquals( 0.49586776859f, value, 0);
    }

    @Test
    public void TestCalculateImpurityWithZero()
    {
        TempLeaf tempLeaf = new TempLeaf(0, 8);
        float value = tempLeaf.CalculateImpurity();
        Assert.assertEquals(0, value, 0);

    }


}
