package wethinkcode.com.swingy.View.console;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import wethinkcode.com.swingy.Model.Inventory.Inventory;
import wethinkcode.com.swingy.Model.Stats.UnitStats;
import wethinkcode.com.swingy.Model.Units.GameCharacters;

import java.util.ArrayList;


public class ConsoleGameView {
    ConsolePlayerSelectView consolePlayerSelectView;
    ConsolePlayerSelectDetails consolePlayerSelectDetails = new ConsolePlayerSelectDetails();
    ConsoleHeroView consoleHeroView = new ConsoleHeroView();
    ConsoleNewPlayer consoleNewPlayer;

    public enum CurrentScreen{
        MainMenuScreen,
        NewPlayerScreen,
        GamingScreen
    }

    private @Getter @Setter int screenIndex = 0;
    private @Setter @Getter String heroName = null;
    private @Getter @Setter int heroClassIndex = 8;

    public ConsoleGameView() {
        AddGameTitle();
    }

    public void MainMenu(ArrayList playerNames){
        consolePlayerSelectView = new ConsolePlayerSelectView(playerNames);
    }

    public void PlayGame(GameCharacters Hero){
        consoleHeroView.DisplayBasicInfo(Hero);
        consoleHeroView.DisplayHeroMenu();
    }

    public void DisplayHeroStats(GameCharacters Hero, Inventory inventory) {
        consoleHeroView.DisplayHeroStats(inventory);
        consolePlayerSelectDetails.DisplayHero(Hero);

    }

    public void NewGame(){
        consoleNewPlayer = new ConsoleNewPlayer();
        consoleNewPlayer.EnterPlayerName(heroName);
    }

    private void AddGameTitle(){
        System.out.println("S => Switch\t\tSwingy RPG\n");
    }

    public void ClearScreen(){
        for (int i = 0; i < 80; i++) {
            System.out.println();
        }
        AddGameTitle();
    }

    public void ShowSelectedHeroDetails(JSONObject Hero){
        consolePlayerSelectDetails.DisplayJSONHero(Hero);
    }

    public void NewPlayerDisplayClassStats(UnitStats unitStats) {
//        consoleNewPlayer = new ConsoleNewPlayer();
//        consoleNewPlayer.EnterPlayerName(heroName);
        consoleNewPlayer.SelectPlayerClass();
        if (unitStats != null) {
            consoleNewPlayer.DisplayHero(unitStats);
        }
    }

}
