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

    public ArrayList<Float> data = new ArrayList<Float>();

    public Info(int age, int sex, float BMI, float bloodPressure,
                float totalSerumCholesterol, float lowDensityLipoproteins, float highDensityLipoproteins,
                float totalCholesterol, float possibilityLogOfSerumTriglyceridesLevel, float bloodSugarLevels,
                boolean isDiseaseProgressionGood)
    {
        this.age = age;
        this.sex = sex;
        this.BMI  = BMI;
        this.bloodPressure = bloodPressure;
        this.totalSerumCholesterol = totalSerumCholesterol;
        this.lowDensityLipoproteins = lowDensityLipoproteins;
        this.highDensityLipoproteins = highDensityLipoproteins;
        this.totalCholesterol = totalCholesterol;
        this.possibilityLogOfSerumTriglyceridesLevel = possibilityLogOfSerumTriglyceridesLevel;
        this.bloodSugarLevels = bloodSugarLevels;
        this.isDiseaseProgressionGood = isDiseaseProgressionGood;

        data.add((float) age);
        data.add((float) sex);
        data.add( BMI);
        data.add(bloodPressure);
        data.add(totalCholesterol);
        data.add(lowDensityLipoproteins);
        data.add(highDensityLipoproteins);
        data.add(totalCholesterol);
        data.add( possibilityLogOfSerumTriglyceridesLevel);
        data.add(bloodSugarLevels);
    }

    public void FillMissingData()
    {
        //check for 0 values
        for(int i = 0; i < data.size();i++)
            if(data.get(i) == 0)
                //replace 0s with the median
                data.set(i, DecisionTreeManager.GetAverage(i));
    }



    @Override
    public String toString() {
        return age + " " + sex + " " + BMI + " " + bloodPressure + " " + totalSerumCholesterol + " " + lowDensityLipoproteins
                + " " + highDensityLipoproteins + " " + totalCholesterol + " " + possibilityLogOfSerumTriglyceridesLevel +
                " " + bloodSugarLevels + " " + isDiseaseProgressionGood;
    }
}
