public class Patient {

    private Info info;
    private int patientID;

    Info GetInfo(){
        return info;
    }

    void SetInfo(Info i){
        info = i;
    }

    boolean PredictDiseaseProgression()
    {
        return DecisionTreeManager.randomForest.GetPrediction(info);
    }

    void FillMissingData()
    {
        info.FillMissingData();
    }


}
