package wethinkcode.com.swingy.View.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GuiMenuAccess extends JPanel {
    private JButton saveGameButton;
    private JButton mainMenuButton;
    private JButton switchViewGamePlay;

    public GuiMenuAccess(){
        saveGameButton = new JButton("Save Game");
        mainMenuButton = new JButton("Main Menu");
        switchViewGamePlay = new JButton("Switch View");


        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(mainMenuButton);
        add(saveGameButton);
        add(switchViewGamePlay);
    }

    public void AccessMainMenu(ActionListener actionListener) {
        mainMenuButton.addActionListener(actionListener);
    }

    public void SaveGame(ActionListener actionListener) {
        saveGameButton.addActionListener(actionListener);
    }

    public void SwitchViewGamePlay(ActionListener actionListener) { switchViewGamePlay.addActionListener(actionListener); }
}
