import java.util.ArrayList;
public class Patient {

    private Info info;
    private int patientID;
    private User user;

    public Patient(Info info, User user) {
        this.info = info;
        this.user = user;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getPatientID () {
        return patientID;
    }


    Info getInfo(){
        return info;
    }

    void setInfo(Info i){
        info = i;
    }

    boolean predictDiseaseProgression()
    {
        return RandomForest.GetPrediction(info);
    }

    void FillMissingData()
    {
        info.FillMissingData();
    }


}
