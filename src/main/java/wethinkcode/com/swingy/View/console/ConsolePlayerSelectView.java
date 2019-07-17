package wethinkcode.com.swingy.View.console;

import java.util.ArrayList;

class ConsolePlayerSelectView {
    ConsolePlayerSelectView(ArrayList playerNames){
        System.out.println("Select Player");
        for (int i = 0; i < playerNames.size(); i++) {
            System.out.println((i + 1) + ". " + playerNames.get(i));
        }
        System.out.println();
        System.out.println("N: New Game");
        System.out.println("C: Continue Game");
    }
}
