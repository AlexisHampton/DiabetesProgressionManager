package Main;

import java.util.ArrayList;

public class Info {
    public int age;
    public int sex;
    public float BMI;
    public float bloodPressure;
    public float totalSerumCholesterol;
    public float lowDensityLipoproteins;
    public float highDensityLipoproteins;
    public float totalCholesterol;
    public float possibilityLogOfSerumTriglyceridesLevel;
    public float bloodSugarLevels;
    public boolean isDiseaseProgressionGood;

    //every variable but in a list
    public ArrayList<Float> data = new ArrayList<Float>();

    //Initializes an Main.Info class based on all of the data neccessary for a patient
    public Info(int age, int sex, float BMI, float bloodPressure,
                float totalSerumCholesterol, float lowDensityLipoproteins, float highDensityLipoproteins,
                float totalCholesterol, float possibilityLogOfSerumTriglyceridesLevel, float bloodSugarLevels,
                boolean isDiseaseProgressionGood)
    {
        //data is used by the decisionTree class
        data.add((float) age);
        data.add((float) sex);
        data.add( BMI);
        data.add(bloodPressure);
        data.add(totalSerumCholesterol);
        data.add(lowDensityLipoproteins);
        data.add(highDensityLipoproteins);
        data.add(totalCholesterol);
        data.add( possibilityLogOfSerumTriglyceridesLevel);
        data.add(bloodSugarLevels);

        FillMissingData();

        //variables might be used in Main.User and Main.Patient classes
        this.age = Math.round(data.get(0));
        this.sex = Math.round(data.get(1));
        this.BMI  = data.get(2);
        this.bloodPressure = data.get(3);
        this.totalSerumCholesterol = data.get(4);
        this.lowDensityLipoproteins = data.get(5);
        this.highDensityLipoproteins = data.get(6);
        this.totalCholesterol = data.get(7);
        this.possibilityLogOfSerumTriglyceridesLevel = data.get(8);
        this.bloodSugarLevels = data.get(9);

        this.isDiseaseProgressionGood = isDiseaseProgressionGood;

    }

    //Replaces each 0 with an average
    public void FillMissingData()
    {
        for(int i = 0; i < data.size();i++)
            if(data.get(i) <= 0)
                data.set(i, RandomForest.GetAverage(i));
    }

    //Returns a string of the class as a string
    //useful for writing to files
    @Override
    public String toString() {
        return age + " " + sex + " " + BMI + " " + bloodPressure + " " + totalSerumCholesterol + " " + lowDensityLipoproteins
                + " " + highDensityLipoproteins + " " + totalCholesterol + " " + possibilityLogOfSerumTriglyceridesLevel +
                " " + bloodSugarLevels + " " + isDiseaseProgressionGood;
    }
}
