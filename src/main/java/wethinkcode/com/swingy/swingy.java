package wethinkcode.com.swingy;

import wethinkcode.com.swingy.Controller.GamePlay;

public class swingy {
    public static void main(String args[]) {
        try {
            if (!args[0].equals("gui") && !args[0].equals("console")) {
                System.out.println("Invalid argument");
                System.exit(1);
            } else {
                new GamePlay(args[0]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Run the program with either console or gui as arguments.");
        }
    }
}
