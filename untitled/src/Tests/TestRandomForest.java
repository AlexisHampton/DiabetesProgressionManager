package Tests;

import Main.Info;
import Main.RandomForest;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestRandomForest {

    ArrayList<Info> PopulateAllData()
    {
        return new ArrayList<>(List.of(TestData.a, TestData.b, TestData.c, TestData.d, TestData.e, TestData.f, TestData.g,
                TestData.h, TestData.i, TestData.j, TestData.k, TestData.l, TestData.m, TestData.n));

    }

    @Test
    public void TestGetAverage(){
        ArrayList<Info> allData = PopulateAllData();
        RandomForest randomForest = new RandomForest(allData);

        float actual = RandomForest.GetAverage(2);

        Assert.assertEquals(25.3357143f, actual, 0.05);
    }

    @Test
    public void TestBootstrapData()
    {
        RandomForest forest = new RandomForest(PopulateAllData());
        ArrayList<Info> bsInfo = forest.GetBootStrappedData(7);
        String str = "";
        for(Info info : bsInfo)
            str += info.toString() + "\n";
        System.out.println(str);
    }

    @Test
    public void TestGetTestingData()
    {
        ArrayList<Info> expected = new ArrayList<>(List.of(TestData.e, TestData.f, TestData.g, TestData.h));
        ArrayList<Info> actualTrainingData = new ArrayList<>(List.of(TestData.a, TestData.b, TestData.c, TestData.d,
                TestData.i, TestData.j, TestData.k, TestData.l, TestData.m, TestData.n));

        RandomForest randomForest = new RandomForest(PopulateAllData());
        ArrayList<Info> actual = randomForest.GetTestingData(actualTrainingData);

        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void TestCreateRandomForest()
    {
        RandomForest forest = new RandomForest(PopulateAllData(), 3, 10);
        forest.CreateRandomForest();
        System.out.println(forest);
    }

    @Test
    public void TestGetPrediction(){
        RandomForest forest = new RandomForest(PopulateAllData(), 3, 10);
        forest.CreateRandomForest();
        System.out.println(forest);
        System.out.println(forest.GetPrediction(TestData.e));
    }

    @Test
    public void TestSplit(){
        ArrayList<ArrayList<Info>> expected = new ArrayList<>();
        expected.add(new ArrayList<>(List.of(TestData.a,TestData.f, TestData.k )));
        expected.add(new ArrayList<>(List.of(TestData.b, TestData.g, TestData.l)));
        expected.add(new ArrayList<>(List.of(TestData.c, TestData.h, TestData.m)));
        expected.add(new ArrayList<>(List.of(TestData.d, TestData.i, TestData.n)));
        expected.add(new ArrayList<>(List.of(TestData.e, TestData.j)));

        RandomForest forest = new RandomForest(PopulateAllData());
        ArrayList<ArrayList<Info>> actual = forest.SplitData(5);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void TestValidation()
    {
        RandomForest forest = new RandomForest(PopulateAllData(), 3, 10);
        forest.Validate();
        System.out.println(forest);
    }

}
