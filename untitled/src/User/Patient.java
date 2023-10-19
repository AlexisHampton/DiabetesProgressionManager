package User;

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
        //send in information to the DecisionTreeManager and return the prediction
        return false;
    }

    void FillMissingData()
    {
        //for each data labeled as -1, fill in with median data, except for bool obv
    }


}
