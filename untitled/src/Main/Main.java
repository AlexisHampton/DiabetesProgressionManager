package Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<Info> allInfo = new ArrayList<Info>();
    private static ArrayList<Patient> patients = new ArrayList<>();

    public  static RandomForest randomForest;

    private static String userFileName = "userDB.txt";


    public static void main(String[] args) {

        ParseData();
/*
        for(int i = 0; i < allInfo.size(); i++)
            System.out.println(allInfo.geta(i).toString());
*/
        ArrayList<User> users = Authorization.getUsers();
        GUI gui = new GUI(new Authorization(users));

        if (gui.RenderLogin()) {

           //parse diabetes info into info
            ParseData();
            assignPatientsToUsers(allInfo, users);
            gui.RenderPatientSearch();

            User currentUser = gui.getCurrentUser();
            int patientToSearch = GUI.promptInt("Enter Main.Patient ID to search for a patient: ");
            Patient foundPatient = gui.searchPatient(currentUser, patientToSearch);

            if (foundPatient != null) {
                //create tree
                randomForest = new RandomForest(allInfo);
                randomForest.CreateRandomForest();

                gui.renderPatientInfo(foundPatient);
                gui.renderWarning(foundPatient);
            } else {
                System.out.println("Main.Patient not found.");
            }

        }      



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

            while (scanner.hasNextLine()) {
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
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    //Adds the info from a specified userID of a specified patientID and info object to the database
    public static void AddInfoTODB(int userId, int patientID, Info info) {
        String data = userId + " " + patientID + " ";
        for(int i = 0; i < info.data.size();i++)
            data += info.data.get(i) + " ";
        data += info.isDiseaseProgressionGood +  "\n";
        try{
            //appends to the end of the file
            FileWriter writer = new FileWriter(userFileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
           bufferedWriter.write(data);
            bufferedWriter.close();
            System.out.println("Successfully wrote patient: " + patientID + "\'s info to the database");
        }
        catch (IOException e)
        {
            System.out.println("An error occurred when writing patient: "+ patientID + "\'s info to " + userFileName);
            System.out.println(e.getMessage());
        }

    }


    //Returns the info from a specifiec userID of a specified patientID
    public static Info GetInfoFromDB(int userID, int patientID) {
        try{
            File file = new File(userFileName);
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine())
            {
                //splits on spaces
                String[] data = scanner.nextLine().split("\\s+");
                //if the ids match
                if(userID == Integer.parseInt(data[0]) && patientID == Integer.parseInt(data[1]))
                {
                    Info info = new Info(
                            (int) Float.parseFloat(data[2]), //age
                            (int) Float.parseFloat(data[3]), //sex
                            Float.parseFloat(data[4]), //bmi
                            Float.parseFloat(data[5]), //blood pressure
                            (int) Float.parseFloat(data[6]), //total serum cholesterol , s1
                            Float.parseFloat(data[7]), //low-density lipoproteins, s2
                            Float.parseFloat(data[8]), //high-density lipoproteins, s3
                            Float.parseFloat(data[9]), //total cholesterol / HDL, s4
                            Float.parseFloat(data[10]), //possibly log of serum triglycerides level, s5
                            (int) Float.parseFloat(data[11]), //blood sugar level, s6
                            Boolean.parseBoolean(data[12])//is disease progression good, y
                    );
                    System.out.println(info);
                    return info;
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        //if the data does not exist or the file is not found, there is nothing to return
        return null;
    }

    //Returns an arraylist of patientInfos from a specified userID
    //This gets all the patientInfos for one specific user
    public static ArrayList<PatientInfo> GetInfoFromDB(int userID) {
        try{
            File file = new File(userFileName);
            Scanner scanner = new Scanner(file);
            ArrayList<PatientInfo> patientInfos = new ArrayList<>();

            while(scanner.hasNextLine())
            {
                //splits on spaces
                String[] data = scanner.nextLine().split("\\s+");
                if(userID == Integer.parseInt(data[0]))
                {

                    PatientInfo patientInfo = new PatientInfo(
                            Integer.parseInt(data[1]), //patientID
                            (int) Float.parseFloat(data[2]), //age
                            (int) Float.parseFloat(data[3]), //sex
                            Float.parseFloat(data[4]), //bmi
                            Float.parseFloat(data[5]), //blood pressure
                            (int) Float.parseFloat(data[6]), //total serum cholesterol , s1
                            Float.parseFloat(data[7]), //low-density lipoproteins, s2
                            Float.parseFloat(data[8]), //high-density lipoproteins, s3
                            Float.parseFloat(data[9]), //total cholesterol / HDL, s4
                            Float.parseFloat(data[10]), //possibly log of serum triglycerides level, s5
                            (int) Float.parseFloat(data[11]), //blood sugar level, s6
                            Boolean.parseBoolean(data[12])//is disease progression good, y
                    );
                    patientInfos.add(patientInfo);
                    System.out.println(patientInfo);
                }
            }
            return patientInfos;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        //if the data does not exist or the file is not found, there is nothing to return
        return null;
    }

}