package wethinkcode.com.swingy.View.gui;

import javax.swing.*;
import java.awt.*;

public class GuiGameOutput extends JPanel {
    private JTextArea gameMessages;

    public GuiGameOutput(){
        gameMessages = new JTextArea("");

        setLayout(new BorderLayout());

        add(new JScrollPane(gameMessages), BorderLayout.CENTER);
    }

    public void UpdateScreen(String update){
        if (!update.contains("\n")) {
            gameMessages.append(update + "\n");
        } else {
            gameMessages.append(update);
        }
    }
}
