package Main;

import java.util.ArrayList;
import java.util.Arrays;

public class Authorization {

    private ArrayList<User> user;

    private static ArrayList<User> users = new ArrayList<>(
            Arrays.asList(
                    new User(1, "user1", "pass1"),
                    new User(2, "user2", "pass2"),
                    new User(3, "user3", "pass3")
            )
    );


    public Authorization (ArrayList<User> users) {

        this.user =users;
    }



    public static User login(String enteredUsername, String enteredPassword) {

        ArrayList<User> users = getUsers();

        // Check if the entered credentials match our users
        for (User user : users) {
            if (user.getUserName().equals(enteredUsername) && user.getPassword().equals(enteredPassword)) {

                return user;  
            }
        }

       
        return null;
    }



    public static ArrayList<User> getUsers() {

        return users;
    }

}
