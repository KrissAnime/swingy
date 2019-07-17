package wethinkcode.com.swingy.View.gui;

import lombok.Getter;
import lombok.Setter;
import wethinkcode.com.swingy.Model.Inventory.Inventory;
import wethinkcode.com.swingy.Model.Units.GameCharacters;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GuiGameView extends JFrame {

    private @Getter
    GuiMenuAccess guiMenuAccess;
    private @Getter
    GuiHeroView guiHeroView;
    private @Getter
    GuiGameOutput guiGameOutput = new GuiGameOutput();
    private @Getter
    GuiPlayerSelectView guiPlayerSelectView;
    private @Getter
    GuiNewPlayer guiNewPlayer;
    private @Getter
    GuiPlayerSelectDetails selectedPlayerDetails;
    private @Setter @Getter boolean mainMenuScreen = true;
    private @Setter @Getter boolean gamingScreen = false;
    private @Setter @Getter boolean newPlayerScreen = false;
    private JButton switchGameView;

    public GuiGameView(ArrayList playerNames){
        super("Swingy RPG");
        switchGameView = new JButton("Switch View");

        setLayout(new BorderLayout());

        MainMenu(playerNames);
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    public void StartGame(GameCharacters hero, Inventory inventory) {
        remove(guiNewPlayer);

        guiNewPlayer = null;

        newPlayerScreen = false;

        PlayGame(hero, inventory);
    }

    public void ContinueGame(GameCharacters hero, Inventory inventory){
        remove(guiPlayerSelectView);
        remove(selectedPlayerDetails);

        guiPlayerSelectView = null;
        selectedPlayerDetails = null;

        newPlayerScreen = false;

        PlayGame(hero, inventory);
    }

    public void MenuFromGame(ArrayList playerNames){
        remove(guiGameOutput);
        remove(guiHeroView);
        remove(guiMenuAccess);

        guiGameOutput = null;
        guiHeroView = null;
        guiMenuAccess = null;

        gamingScreen = false;
        mainMenuScreen = true;

        MainMenu(playerNames);
    }

    public void MenuFromNewPlayer(ArrayList playerNames){
        remove(guiNewPlayer);

        guiNewPlayer = null;

        mainMenuScreen = true;
        newPlayerScreen = false;

        MainMenu(playerNames);
    }

    private void PlayGame(GameCharacters hero, Inventory inventory){
        guiHeroView = new GuiHeroView(hero, inventory);
        guiMenuAccess = new GuiMenuAccess();
        guiGameOutput = new GuiGameOutput();

        add(guiHeroView, BorderLayout.WEST);
        add(guiMenuAccess, BorderLayout.NORTH);
        add(guiGameOutput, BorderLayout.CENTER);

        mainMenuScreen = false;
        gamingScreen = true;

        RefreshPanel();
    }

    private void MainMenu(ArrayList playerNames) {
        guiPlayerSelectView = new GuiPlayerSelectView(playerNames);
        selectedPlayerDetails = new GuiPlayerSelectDetails();

        add(guiPlayerSelectView, BorderLayout.WEST);
        add(selectedPlayerDetails, BorderLayout.CENTER);

        mainMenuScreen = true;

        RefreshPanel();
    }

    public void NewPlayer() {
        remove(guiPlayerSelectView);
        remove(selectedPlayerDetails);

        guiPlayerSelectView = null;
        selectedPlayerDetails = null;

        guiNewPlayer = new GuiNewPlayer();

        add(guiNewPlayer, BorderLayout.NORTH);

        mainMenuScreen = false;
        newPlayerScreen = true;

        RefreshPanel();
    }

    private void RefreshPanel(){
        revalidate();
        repaint();
    }

    public void ScreenPopupMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }
}
