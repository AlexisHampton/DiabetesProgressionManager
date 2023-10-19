package User;

public class Info {
    int age;
    int sex;
    float bloodSugarLevels;
    float BMI;
    float totalSerumCholesterol;
    float lowDensityLipoproteins;
    float totalCholesterol;
    float possibilityLogOfSerumTriglyceridesLevel;
    boolean isDiseaseProgressionGood;

    public Info(int age, int sex, float bloodSugarLevels, float BMI,
                float totalSerumCholesterol, float lowDensityLipoproteins,
                float totalCholesterol, float possibilityLogOfSerumTriglyceridesLevel,
                boolean isDiseaseProgressionGood)
    {
        this.age = age;
        this.sex = sex;
        this.bloodSugarLevels = bloodSugarLevels;
        this.BMI  = BMI;
        this.totalSerumCholesterol = totalSerumCholesterol;
        this.lowDensityLipoproteins = lowDensityLipoproteins;
        this.totalCholesterol = totalCholesterol;
        this.possibilityLogOfSerumTriglyceridesLevel = possibilityLogOfSerumTriglyceridesLevel;
        this.isDiseaseProgressionGood = isDiseaseProgressionGood;
    }


}
