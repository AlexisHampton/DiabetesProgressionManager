import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<Info> allInfo = new ArrayList<Info>();
    private static ArrayList<Patient> patients = new ArrayList<>();


    public static void main(String[] args) {
        
        ArrayList<User> users = Authorization.getUsers();
        GUI gui = new GUI(new Authorization(users));

        if (gui.RenderLogin()) {

           //parse diabetes info into info
            ParseData();
            assignPatientsToUsers(allInfo, users);
            gui.RenderPatientSearch();

            User currentUser = gui.getCurrentUser();
            int patientToSearch = GUI.promptInt("Enter Patient ID to search for a patient: ");
            Patient foundPatient = gui.searchPatient(currentUser, patientToSearch);
            
            if (foundPatient != null) {
                //create tree
                RandomForest randomForest = new RandomForest(allInfo);
                randomForest.CreateRandomForest();
                gui.renderPatientInfo(foundPatient);
                gui.renderWarning(foundPatient);
            } else {
                System.out.println("Patient not found.");
            }

        }    

/*
        for(int i = 0; i < allInfo.size(); i++)
            System.out.println(allInfo.geta(i).toString());
*/
        
    }



    public static void assignPatientsToUsers(ArrayList<Info> allInfo, ArrayList<User> users) {
        int patientsPerUser = allInfo.size() / users.size();
        int counter = 1;

        for (int i = 0; i < users.size(); i++) {

            User doctor = users.get(i);

            for (int j = i * patientsPerUser; j < (i + 1) * patientsPerUser; j++) {
                Patient patient = new Patient(allInfo.get(j), doctor);
                patient.setPatientID(counter);
                doctor.addPatient(patient);
                counter++;
            }
        }
    }



    public static void ParseData()
    {
        try {
            File file = new File("Assignment3_DiabetesData.txt");
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine())
            {
                String data = scanner.nextLine();
                String[] dataArr = data.split("\\s+");
                Info info = new Info(
                        Integer.parseInt(dataArr[0]), //age
                        Integer.parseInt(dataArr[1]), //sex
                        Float.parseFloat(dataArr[2]), //bmi
                        Float.parseFloat(dataArr[3]), //blood pressure
                        Integer.parseInt(dataArr[4]), //total serum cholesterol , s1
                        Float.parseFloat(dataArr[5]), //low-density lipoproteins, s2
                        Float.parseFloat(dataArr[6]), //high-density lipoproteins, s3
                        Float.parseFloat(dataArr[7]), //total cholesterol / HDL, s4
                        Float.parseFloat(dataArr[8]), //possibly log of serum triglycerides level, s5
                        Integer.parseInt(dataArr[9]), //blood sugar level, s6
                        Integer.parseInt(dataArr[10]) < 150 //is disease progression good, y
                );

                allInfo.add(info);
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

    }

}