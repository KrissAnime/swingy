package wethinkcode.com.swingy.Controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wethinkcode.com.swingy.Controller.Battle.Mechanics;
import wethinkcode.com.swingy.Model.Database.SQLite;
import wethinkcode.com.swingy.Model.Inventory.Inventory;
import wethinkcode.com.swingy.Model.Stats.Artifact;
import wethinkcode.com.swingy.Model.Stats.UnitStats;
import wethinkcode.com.swingy.Model.Units.Enemies.EnemyFactory;
import wethinkcode.com.swingy.Model.Units.GameCharacters;
import wethinkcode.com.swingy.Model.Units.Heroes.HeroFactory;
import wethinkcode.com.swingy.Model.Units.MapPosition;
import wethinkcode.com.swingy.View.console.ConsoleGameView;
import wethinkcode.com.swingy.View.gui.GuiGameView;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GamePlay {
    private static int turnCap = 20;
    private static String screenDisplay;
    private final String alphaNumRegex = "^[a-zA-Z0-9]+$";
    private final Pattern alphaNumPattern = Pattern.compile(alphaNumRegex);
    private final String[] DropItems = {"Potion", "Nothing", "Common", "Rare", "Legendary", "Potion", "Nothing", "Common", "Rare", "Legendary", "Potion", "Nothing", "Common", "Rare", "Legendary",
            "Common", "Rare", "Common", "Common", "Common"};
    private final List<UnitStats> unitStatsList = getUnitStatsList();
    private final SQLite SQLite = new SQLite();
    private boolean artifactChoice = false;
    private boolean enemyEncounter = false;
    private boolean moveHero = false;
    private int playerSelected = -1;

    private String foundEquipmentName;
    private String foundEquipmentClass;
    private GameCharacters playerHero;
    private GameCharacters enemyChallenger;
    private JSONArray GamePlayers;
    private List<GameCharacters> enemies;

    private GuiGameView guiGameView;
    /**
     * Mouse listener for selecting Hero in Main menu screen
     */

    MouseListener mouseListener = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) {
                guiGameView.getGuiPlayerSelectView().getContinueGame().setEnabled(true);
                guiGameView.getSelectedPlayerDetails().ChangeHero(GamePlayers.getJSONObject(guiGameView.getGuiPlayerSelectView().getPlayerList().getSelectedIndex()));
            }
        }
    };
    private ConsoleGameView consoleGameView = null;
    private String gameUpdates;
    private Inventory inventory = new Inventory();

    public GamePlay(String screenDisplay) {
        this.screenDisplay = screenDisplay;
        if (screenDisplay.equals("gui")) {
            guiGameView = new GuiGameView(SQLite.RetrieveNames());
            GuiMenuScreen();
        } else if (screenDisplay.equals("console")) {
            TerminalScreen();
        }
    }

    /**
     * Screen Controller Functions Start
     */


    private void SwitchViews() {
        if (screenDisplay.equals("console")) {
            screenDisplay = "gui";
            consoleGameView.ClearScreen();
            guiGameView = new GuiGameView(SQLite.RetrieveNames());
            if (ConsoleGameView.CurrentScreen.values()[consoleGameView.getScreenIndex()] == ConsoleGameView.CurrentScreen.NewPlayerScreen) {
                guiGameView.NewPlayer();
                GuiNewGameButtonEvents();
            } else if (ConsoleGameView.CurrentScreen.values()[consoleGameView.getScreenIndex()] == ConsoleGameView.CurrentScreen.GamingScreen) {
                guiGameView.ContinueGame(playerHero, inventory);
                GuiGamingScreen();
            } else {
                GuiMenuScreen();
            }

        } else if (screenDisplay.contains("gui")) {
            GamePlayers = SQLite.RetrievePlayers();
            consoleGameView = new ConsoleGameView();
            if (guiGameView.isGamingScreen()) {
                consoleGameView.setScreenIndex(2);
                consoleGameView.PlayGame(playerHero);
            } else if (guiGameView.isNewPlayerScreen()) {
                consoleGameView.setScreenIndex(1);
                consoleGameView.NewGame();
            } else {
                consoleGameView.setScreenIndex(0);
                consoleGameView.MainMenu(SQLite.RetrieveNames());
            }
            screenDisplay = "console";
            guiGameView.setVisible(false);
            TerminalScreen();
        }
    }

    private void TerminalScreen() {
        try {
            GamePlayers = SQLite.RetrievePlayers();
            if (consoleGameView == null) {
                consoleGameView = new ConsoleGameView();
                consoleGameView.MainMenu(SQLite.RetrieveNames());
            }
            Scanner scanner = new Scanner(System.in);
            consoleLoop:
            while (true) {
                ConsoleGameView.CurrentScreen currentScreen = ConsoleGameView.CurrentScreen.values()[consoleGameView.getScreenIndex()];
                String line = scanner.nextLine();

                switch (currentScreen) {
                    case GamingScreen:
                        switch (line) {
                            case "S":
                                break consoleLoop;
                            case "s":
                                break consoleLoop;
                            case "EXIT":
                                System.exit(0);
                            default:
                                ConsoleControlUserActions(line);
                        }
                        break;
                    case MainMenuScreen:
                        switch (line) {
                            case "N":
                                consoleGameView.setScreenIndex(1);
                                consoleGameView.ClearScreen();
                                consoleGameView.NewGame();
                                break;
                            case "n":
                                consoleGameView.setScreenIndex(1);
                                consoleGameView.ClearScreen();
                                consoleGameView.NewGame();
                                break;
                            case "C":
                                ConsoleContinueGame();
                                break;
                            case "c":
                                ConsoleContinueGame();
                                break;
                            case "S":
                                break consoleLoop;
                            case "s":
                                break consoleLoop;
                            case "EXIT":
                                System.exit(0);
                            default:
                                HandleUserInput(line);
                        }
                        break;
                    case NewPlayerScreen:
                        switch (line) {
                            case "S":
                                break consoleLoop;
                            case "s":
                                break consoleLoop;
                            case "EXIT":
                                System.exit(0);
                            default:
                                ConsoleNewPlayerScreen(line);
                        }
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("There was an error trying to read terminal input");
            e.printStackTrace();
        } finally {
            SwitchViews();
        }
    }

    private void GuiMenuScreen() {
        guiGameView.setMainMenuScreen(true);
        guiGameView.setGamingScreen(false);
        guiGameView.setNewPlayerScreen(false);
        guiGameView.getGuiPlayerSelectView().ContinueGame(new GuiContinueGame());
        guiGameView.getGuiPlayerSelectView().NewGame(new GuiNewGame());
        guiGameView.getGuiPlayerSelectView().SwitchMainMenu(new SwitchGameView());
        GamePlayers = SQLite.RetrievePlayers();
        guiGameView.getGuiPlayerSelectView().SelectPlayer(mouseListener);
    }

    private void GuiGamingScreen() {
        guiGameView.setMainMenuScreen(false);
        guiGameView.setGamingScreen(true);
        guiGameView.setNewPlayerScreen(false);
        guiGameView.getGuiHeroView().MovePlayerNorth(new MoveCharacterNorth());
        guiGameView.getGuiHeroView().MovePlayerEast(new MoveCharacterEast());
        guiGameView.getGuiHeroView().MovePlayerSouth(new MoveCharacterSouth());
        guiGameView.getGuiHeroView().MovePlayerWest(new MoveCharacterWest());
        guiGameView.getGuiHeroView().FightMonster(new FightEnemy());
        guiGameView.getGuiHeroView().FleeMonster(new FleeEnemy());
        guiGameView.getGuiHeroView().EquipArtifact(new EquipArtifact());
        guiGameView.getGuiHeroView().IgnoreArtifact(new IgnoreArtifact());
        guiGameView.getGuiMenuAccess().AccessMainMenu(new MainMenu());
        guiGameView.getGuiMenuAccess().SaveGame(new SaveGame());
        guiGameView.getGuiMenuAccess().SwitchViewGamePlay(new SwitchGameView());
        if (enemies == null) {
            enemyGenerator();
        }
    }

    private void GuiNewGameButtonEvents() {
        guiGameView.setMainMenuScreen(false);
        guiGameView.setGamingScreen(false);
        guiGameView.setNewPlayerScreen(true);
        guiGameView.getGuiNewPlayer().MainMenu(new NewGameMainMenu());
        guiGameView.getGuiNewPlayer().StartGame(new GuiStartGame());
        guiGameView.getGuiNewPlayer().ChooseClass(new ChooseClass());
        guiGameView.getGuiNewPlayer().SwitchViewNewPlayer(new SwitchGameView());
    }

    /**
     * Validates the data passed for the new hero to be created
     *
     * @param name      The name of the the new hero
     * @param heroClass The class chosen for the hero to be created
     * @return Boolean return's true if correct data is given otherwise returns false
     */

    private boolean CheckNewPlayerDetails(String name, String heroClass) {
        Matcher matcher = alphaNumPattern.matcher(name);
        if (name.isEmpty()) {
            guiGameView.ScreenPopupMessage("Name can not be empty.");
            return false;
        } else if (name.length() > 30) {
            guiGameView.ScreenPopupMessage("Name max length 30 characters.");
            return false;
        } else if (!matcher.matches()) {
            guiGameView.ScreenPopupMessage("Name can contain Alphanumeric characters only.");
            return false;
        } else if (heroClass == null) {
            guiGameView.ScreenPopupMessage("Select a hero class to start new game.");
            return false;
        }
        for (int i = 0; i < GamePlayers.length(); i++) {
            JSONObject test = GamePlayers.getJSONObject(i);
            if (name.compareToIgnoreCase(test.getString("hero_name")) == 0) {
                guiGameView.ScreenPopupMessage("Hero name already taken, character case does not matter.");
                return false;
            }
        }
        return true;
    }

    /**
     * These functions monitor the hero's position for enemy encounters and buffing the enemy
     */

    private void UpdateHeroPosition() {
        try {
            playerHero.setHeroTurn(playerHero.getHeroTurn() + 1);
            if (screenDisplay.equals("gui")) {
                guiGameView.getGuiHeroView().RefreshHeroStats(playerHero, inventory);
            } else if (screenDisplay.equals("console")) {
                consoleGameView.ClearScreen();
                consoleGameView.PlayGame(playerHero);
            }
            if (!CheckHeroPosition()) {
                if (playerHero.getHeroTurn() > turnCap) {
                    EnemyBuffedUp();
                    turnCap += 20;
                }
            }
        } catch (Exception e) {
            System.out.println("There was an error with UpdateHeroPosition()");
            e.printStackTrace();
        }

    }

    private boolean CheckHeroPosition() {
        if (playerHero.getMapPosition().getXPosition() == playerHero.getMapPosition().getMapSize() || playerHero.getMapPosition().getYPosition() == playerHero.getMapPosition().getMapSize()
                || playerHero.getMapPosition().getXPosition() == 0 || playerHero.getMapPosition().getYPosition() == 0) {
            GameVictory();
            return true;
        } else {
            for (GameCharacters enemy : enemies) {
                if (enemy.getMapPosition().getXPosition() == playerHero.getMapPosition().getXPosition() && enemy.getMapPosition().getYPosition() == playerHero.getMapPosition().getYPosition()) {
                    enemyEncounter = true;
                    enemyChallenger = enemy;
                    GameOutputMessage.OutputToScreen("The hero " + playerHero.getName() + " has encountered an enemy!", screenDisplay, guiGameView, consoleGameView);
                    GameOutputMessage.OutputToScreen("Enemy " + enemy.getName() + " lv: " + enemy.getUnitStats().getLevel() + " exp to gain: " + enemy.getUnitStats().getExperience()
                            , screenDisplay, guiGameView, consoleGameView);
                    gameUpdates = "";
                }
            }
        }
        return false;
    }

    /**
     * Game victory function that is meant to disable buttons, show a popup indicating victory
     * and return to main menu
     */

    private void GameVictory() {
        try {
            GameOutputMessage.OutputToScreen("You Won!", screenDisplay, guiGameView, consoleGameView);
            playerHero.ResetPlayerPosition();
            playerHero.levelUp();
            playerHero.getUnitStats().setExperience((playerHero.getUnitStats().getLevel() * 1000)
                    + ((playerHero.getUnitStats().getLevel() - 1) * (playerHero.getUnitStats().getLevel() - 1)) * 450);
            SQLite.SaveGame(playerHero, inventory);

            enemies = null;
            playerHero = null;
            inventory = new Inventory();

            TimeUnit.SECONDS.sleep(1);
            if (screenDisplay.equals("gui")) {
                guiGameView.ScreenPopupMessage("You Won!");
                guiGameView.MenuFromGame(SQLite.RetrieveNames());
                GuiMenuScreen();
            } else if (screenDisplay.equals("console")) {
                consoleGameView.ClearScreen();
                consoleGameView.setScreenIndex(0);
                consoleGameView.MainMenu(SQLite.RetrieveNames());
            }
            GamePlayers = SQLite.RetrievePlayers();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Enemy controller functions start
     */

    private void EnemyBuffedUp() {
        try {
            GameOutputMessage.OutputToScreen("You spent too much time on the map, all enemies have levelled up!", screenDisplay, guiGameView, consoleGameView);
            for (GameCharacters enemy : enemies) {
                enemy.getUnitStats().setMaxHitPoints((int) (enemy.getUnitStats().getMaxHitPoints() * 1.30));
                enemy.getUnitStats().setHitPoints((enemy.getUnitStats().getMaxHitPoints()));
                enemy.getUnitStats().setExperience(enemy.getUnitStats().getExperience() + 150);
                enemy.getUnitStats().setAttack((int) (enemy.getUnitStats().getAttack() * 1.1));
                enemy.getUnitStats().setDefence((int) (enemy.getUnitStats().getDefence() * 1.08));
                enemy.getUnitStats().setLuck(enemy.getUnitStats().getLuck() + 1);
            }
        } catch (Exception e) {
            System.out.println("There was an error with EnemyBuffedUp()");
            e.printStackTrace();
        }
    }

    private void enemyGenerator() {
        try {
            String[] enemyClassArray = {"Warlock", "Berserker"};
            enemies = new ArrayList<>();
            int enemiesToGenerate = playerHero.getUnitStats().getLevel() + 5;
            int base_experience = 200;
            int base_attack = 20;
            int base_defence = 5;
            int base_hitPoints = 60;
            int base_luck = 1;
            int base_potions = 1;
            int enemyStatsMultiplier = 0;

            while (enemyStatsMultiplier < playerHero.getUnitStats().getLevel()) {
                base_experience += 150;
                base_attack += 8;
                base_defence += 8;
                base_hitPoints += 25;
                base_luck += 1;
                enemyStatsMultiplier++;
            }

            for (int i = 0; i < enemiesToGenerate; i++) {
                UnitStats unitStats;
                unitStats = new UnitStats(enemyStatsMultiplier + 1, base_experience, base_attack, base_defence, base_hitPoints, base_hitPoints, base_luck, base_potions);
                MapPosition enemyPosition = getNewPosition();
                GameCharacters enemy = EnemyFactory.newEnemy("Enemy", enemyClassArray[getRandomNumber(0, 1)], unitStats, enemyPosition, 0);
                enemies.add(enemy);
            }

        } catch (NullPointerException e) {
            System.out.println("There was a problem with the enemy generator, null pointer exception");
        } finally {
            System.out.println("Enemies Generated!");
        }
    }

    private MapPosition getNewPosition() {
        int mapArea = playerHero.getMapPosition().getMapSize();
        boolean empty = false;
        while (true) {
            try {
                while (!empty) {
                    int xPos = getRandomNumber(1, mapArea - 1);
                    int yPos = getRandomNumber(1, mapArea - 1);
                    if (enemies.size() == 0) {
                        if (xPos != playerHero.getMapPosition().getXPosition() && yPos != playerHero.getMapPosition().getYPosition()) {
                            return new MapPosition(xPos, yPos, 0);
                        }
                    } else {
                        empty = true;
                        for (GameCharacters enemy : enemies) {
                            if ((enemy.getMapPosition().getXPosition() == xPos && enemy.getMapPosition().getYPosition() == yPos)
                                    || (playerHero.getMapPosition().getXPosition() == xPos && playerHero.getMapPosition().getYPosition() == yPos)) {
                                empty = false;
                            }
                        }
                        if (empty) {
                            return new MapPosition(xPos, yPos, 0);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("There was an error getting a new enemy position");
                e.printStackTrace();
            }
        }
    }

    /**
     * Loot drop controller
     */

    private void CheckEnemyDrops() {
        try {
            String EnemyDrop = DropItems[getRandomNumber(0, DropItems.length)];
            switch (EnemyDrop) {
                case "Nothing":
                    GameOutputMessage.OutputToScreen("The enemy didn't have anything on them!", screenDisplay, guiGameView, consoleGameView);
                    break;
                case "Potion":
                    playerHero.getUnitStats().setPotions(playerHero.getUnitStats().getPotions() + 1);
                    GameOutputMessage.OutputToScreen("You found a health potion!", screenDisplay, guiGameView, consoleGameView);
                    break;
                default:
                    JSONArray equipmentDrop = SQLite.RetrieveArtifactsByRarity(EnemyDrop);
                    JSONObject equipmentDropJSONObject = equipmentDrop.getJSONObject(getRandomNumber(0, equipmentDrop.length()));
                    foundEquipmentName = equipmentDropJSONObject.getString("artifact_name");
                    foundEquipmentClass = equipmentDropJSONObject.getString("artifact_class");
                    GameOutputMessage.OutputToScreen("You found " + equipmentDropJSONObject.getString("artifact_rarity") + " " +
                            equipmentDropJSONObject.getString("artifact_class") + " " +
                            equipmentDropJSONObject.getString("artifact_name")
                            + "\nWould you like to Equip or Ignore this artifact?", screenDisplay, guiGameView, consoleGameView);
                    switch (equipmentDropJSONObject.getString("artifact_class")) {
                        case "Weapon":
                            GameOutputMessage.OutputToScreen(equipmentDropJSONObject.getInt("artifact_stat") + " Attack", screenDisplay, guiGameView, consoleGameView);
                            break;
                        case "Helmet":
                            GameOutputMessage.OutputToScreen(equipmentDropJSONObject.getInt("artifact_stat") + " HP", screenDisplay, guiGameView, consoleGameView);
                            break;
                        case "Armor":
                            GameOutputMessage.OutputToScreen(equipmentDropJSONObject.getInt("artifact_stat") + " Defence", screenDisplay, guiGameView, consoleGameView);
                            break;
                    }
                    artifactChoice = true;
                    break;
            }
        } catch (Exception e) {
            System.out.println("There was an error with CheckEnemyDrops()");
            e.printStackTrace();
        }
    }

    /**
     * Artifact Controls for adding, removing and changing equipped artifact
     */

    private void AddEquipmentStat(Artifact artifact) {
        try {
            switch (artifact.getArtifactClass()) {
                case "Weapon":
                    playerHero.getUnitStats().setAttack(playerHero.getUnitStats().getAttack() + artifact.getArtifactStat());
                    break;
                case "Helmet":
                    playerHero.getUnitStats().setMaxHitPoints(playerHero.getUnitStats().getMaxHitPoints() + artifact.getArtifactStat());
                    playerHero.getUnitStats().setHitPoints(playerHero.getUnitStats().getHitPoints() + artifact.getArtifactStat());
                    break;
                case "Armor":
                    playerHero.getUnitStats().setDefence(playerHero.getUnitStats().getDefence() + artifact.getArtifactStat());
                    break;
            }
        } catch (Exception e) {
            System.out.println("There was an error with AddEquipmentStat()");
            e.printStackTrace();
        }
    }

    private void RemoveEquipmentStat(Artifact artifact) {
        try {
            switch (artifact.getArtifactClass()) {
                case "Weapon":
                    playerHero.getUnitStats().setAttack(playerHero.getUnitStats().getAttack() - artifact.getArtifactStat());
                    break;
                case "Helmet":
                    playerHero.getUnitStats().setMaxHitPoints(playerHero.getUnitStats().getMaxHitPoints() - artifact.getArtifactStat());
                    playerHero.getUnitStats().setHitPoints(playerHero.getUnitStats().getHitPoints() - artifact.getArtifactStat());
                    break;
                case "Armor":
                    playerHero.getUnitStats().setDefence(playerHero.getUnitStats().getDefence() - artifact.getArtifactStat());
                    break;
            }
        } catch (Exception e) {
            System.out.println("There was an error with RemoveEquipmentStat()");
            e.printStackTrace();
        }
    }

    private void ChangeEquipment() {
        try {
            for (Artifact artifact : inventory.getEquippedItems()) {
                if (artifact.getArtifactClass().equals(foundEquipmentClass)) {
                    RemoveEquipmentStat(artifact);
                    inventory.removeArtifact(artifact);
                    break;
                }
            }

            inventory.equipArtifact(SQLite.RetrieveArtifactForEquipping(foundEquipmentName));
            AddEquipmentStat(SQLite.RetrieveArtifactForEquipping(foundEquipmentName));
            foundEquipmentName = null;
            foundEquipmentClass = null;
            artifactChoice = false;

            if (screenDisplay.equals("gui")) {
                guiGameView.getGuiHeroView().RefreshHeroStats(playerHero, inventory);
            } else if (screenDisplay.equals("console")) {
                consoleGameView.PlayGame(playerHero);
            }

        } catch (Exception e) {
            System.out.println("There was an error with ChangeEquipment()");
            e.printStackTrace();
        }
    }

    private void StartGameControl(String name, String heroClass) {
        try {
            int mapSize = (0 - 1) * 5 + 10 - (0 % 2);
            if (screenDisplay.equals("gui")) {
                if (CheckNewPlayerDetails(name, heroClass)) {
                    MapPosition mapPosition = new MapPosition(mapSize / 2, mapSize / 2, mapSize);
                    UnitStats unitStats = null;
                    switch (heroClass) {
                        case "Tank":
                            unitStats = unitStatsList.get(0);
                            break;
                        case "Mage":
                            unitStats = unitStatsList.get(1);
                            break;
                        case "Support":
                            unitStats = unitStatsList.get(2);
                            break;
                    }
                    playerHero = HeroFactory.newHero(name, heroClass, unitStats, mapPosition, 0);
                    inventory = new Inventory();
                    inventory.StartingEquipment();
                    SQLite.SaveGame(playerHero, inventory);
                    guiGameView.StartGame(playerHero, inventory);
                    GuiGamingScreen();
                    enemyGenerator();
                }
            } else if (screenDisplay.equals("console")) {
                MapPosition mapPosition = new MapPosition(mapSize / 2, mapSize / 2, mapSize);
                UnitStats unitStats = unitStatsList.get(consoleGameView.getHeroClassIndex());

                switch (consoleGameView.getHeroClassIndex()) {
                    case 0:
                        heroClass = "Tank";
                        break;
                    case 1:
                        heroClass = "Mage";
                        break;
                    case 2:
                        heroClass = "Support";
                        break;
                }

                playerHero = HeroFactory.newHero(name, heroClass, unitStats, mapPosition, 0);
                inventory = new Inventory();
                inventory.StartingEquipment();
                SQLite.SaveGame(playerHero, inventory);
                consoleGameView.PlayGame(playerHero);
                enemyGenerator();
            }
        } catch (Exception e) {
            System.out.println("There was an error");
            e.printStackTrace();
        }

    }

    private void HandleUserInput(String userInput) {
        try {
            int userChoice = Integer.valueOf(userInput);
            if (userChoice > GamePlayers.length() || userChoice <= 0) {
                System.out.println("You gave an invalid user number");
            } else {
                playerSelected = userChoice - 1;
                consoleGameView.ShowSelectedHeroDetails(GamePlayers.getJSONObject(playerSelected));
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number option given");
            WaitABit(1);
            consoleGameView.ClearScreen();
            consoleGameView.MainMenu(SQLite.RetrieveNames());
        }
    }

    private void ConsoleFleeEnemy() {
        try {
            consoleGameView.ClearScreen();
            gameUpdates = new Mechanics().DeathBattle(enemyChallenger, playerHero, true, guiGameView, consoleGameView, screenDisplay);
            if (gameUpdates.contains("escape")) {
                enemies.remove(enemyChallenger);
                GameOutputMessage.OutputToScreen("You managed to escape battle!", screenDisplay, guiGameView, consoleGameView);
            } else if (gameUpdates.contains("won")) {
                enemies.remove(enemyChallenger);
                CheckEnemyDrops();
                GameOutputMessage.OutputToScreen("You have won the battle!", screenDisplay, guiGameView, consoleGameView);
            } else if (gameUpdates.contains("defeated")) {
                GameOutputMessage.OutputToScreen("You have lost the battle!", screenDisplay, guiGameView, consoleGameView);

                for (Artifact artifact : inventory.getEquippedItems()) {
                    RemoveEquipmentStat(artifact);
                }

                inventory.ScrapEquipment();
                playerHero.LostExp();
                WaitABit(2);
            }
            enemyEncounter = false;
            enemyChallenger = null;
            consoleGameView.PlayGame(playerHero);
            System.out.println();
        } catch (Exception e) {
            System.out.println("There was an error with ConsoleFleeEnemy()");
            e.printStackTrace();
        }

    }

    private void ConsoleNewPlayerScreen(String userInput) {
        try {
            if (!userInput.isEmpty()) {
                if (consoleGameView.getHeroName() == null) {
                    consoleGameView.ClearScreen();
                    if (userInput.length() > 30) {
                        System.out.println("Player name given was too long, max 30 characters");
                    } else {
                        for (int i = 0; i < GamePlayers.length(); i++) {
                            JSONObject test = GamePlayers.getJSONObject(i);
                            if (userInput.compareToIgnoreCase(test.getString("hero_name")) == 0) {
                                System.out.println("Hero name already taken, character case does not matter.");
                                WaitABit(2);
                                return;
                            }
                        }
                        consoleGameView.setHeroName(userInput);
                        consoleGameView.NewGame();
                        consoleGameView.NewPlayerDisplayClassStats(null);
                    }
                } else {
                    if (userInput.equals("1")) {
                        consoleGameView.ClearScreen();
                        consoleGameView.NewGame();
                        consoleGameView.NewPlayerDisplayClassStats(unitStatsList.get(0));
                        consoleGameView.setHeroClassIndex(0);
                    } else if (userInput.equals("2")) {
                        consoleGameView.ClearScreen();
                        consoleGameView.NewGame();
                        consoleGameView.NewPlayerDisplayClassStats(unitStatsList.get(1));
                        consoleGameView.setHeroClassIndex(1);
                    } else if (userInput.equals("3")) {
                        consoleGameView.ClearScreen();
                        consoleGameView.NewGame();
                        consoleGameView.NewPlayerDisplayClassStats(unitStatsList.get(2));
                        consoleGameView.setHeroClassIndex(2);
                    } else if (userInput.equals("4") && consoleGameView.getHeroClassIndex() != 8) {
                        consoleGameView.ClearScreen();
                        consoleGameView.setScreenIndex(2);
                        StartGameControl(consoleGameView.getHeroName(), null);
                        consoleGameView.setHeroName(null);
                    } else if (userInput.equals("5")) {
                        consoleGameView.ClearScreen();
                        consoleGameView.setScreenIndex(0);
                        consoleGameView.MainMenu(SQLite.RetrieveNames());
                        consoleGameView.setHeroClassIndex(8);
                        consoleGameView.setHeroName(null);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("There was an error with ConsoleNewPlayerScreen()");
            e.printStackTrace();
        }

    }

    private void ConsoleContinueGame() {
        try {
            if (playerSelected >= 0 && playerSelected < GamePlayers.length()) {
                consoleGameView.setScreenIndex(2);
                StartUpGame();
                enemyGenerator();
                consoleGameView.ClearScreen();
                consoleGameView.PlayGame(playerHero);
                playerSelected = 8;
            } else {
                System.out.println("Select a player number");
                consoleGameView.MainMenu(SQLite.RetrieveNames());
            }
        } catch (Exception e) {
            System.out.println("There was an error with ConsoleContinueGame()");
            e.printStackTrace();
        }
    }

    private void StartUpGame() {
        try {
            inventory = new Inventory();
            JSONObject JSONPlayer = null;
            switch (screenDisplay) {
                case "gui":
                    JSONPlayer = GamePlayers.getJSONObject(guiGameView.getGuiPlayerSelectView().getPlayerList().getSelectedIndex());
                    break;
                case "console":
                    JSONPlayer = GamePlayers.getJSONObject(playerSelected);
                    break;
            }

            UnitStats unitStats = new UnitStats(JSONPlayer.getInt("level"), JSONPlayer.getInt("experience"),
                    JSONPlayer.getInt("attack"), JSONPlayer.getInt("defence"), JSONPlayer.getInt("hitPoints"),
                    JSONPlayer.getInt("maxHitPoints"), JSONPlayer.getInt("luck"), JSONPlayer.getInt("potions"));

            MapPosition mapPosition = new MapPosition(JSONPlayer.getInt("x_position"), JSONPlayer.getInt("y_position"),
                    JSONPlayer.getInt("map_size"));

            playerHero = HeroFactory.newHero(JSONPlayer.getString("hero_name"), JSONPlayer.getString("hero_class"), unitStats, mapPosition, JSONPlayer.getInt("turn"));
            inventory.setEquippedItems(SQLite.RetrieveUserEquipment(JSONPlayer.getString("hero_name")));
        } catch (Exception e) {
            System.out.println("There was an error with StartUpGame()");
            e.printStackTrace();
        }

    }

    private void ConsoleControlUserActions(String userInput) {
        try {
            int userChoice = Integer.valueOf(userInput);
            if (userChoice > 11 || userChoice <= 0) {
                System.out.println("You gave an invalid instruction number");
                WaitABit(1);
                consoleGameView.PlayGame(playerHero);
            } else {
                switch (userChoice) {
                    case 1:
                        consoleGameView.ClearScreen();
                        consoleGameView.setScreenIndex(0);
                        consoleGameView.MainMenu(SQLite.RetrieveNames());
                        break;
                    case 2:
                        if (!artifactChoice && !enemyEncounter) {
                            SQLite.SaveGame(playerHero, inventory);
                            System.out.println("Game Saved!");
                            WaitABit(1);
                            consoleGameView.ClearScreen();
                            consoleGameView.PlayGame(playerHero);
                        } else {
                            System.out.println("Invalid Argument");
                        }
                        break;
                    case 3:
                        if (!artifactChoice && !enemyEncounter) {
                            playerHero.getMapPosition().setYPosition(playerHero.getMapPosition().getYPosition() + 1);
                            UpdateHeroPosition();
                        } else {
                            System.out.println("Invalid Argument");
                        }
                        break;
                    case 4:
                        if (!artifactChoice && !enemyEncounter) {
                            playerHero.getMapPosition().setXPosition(playerHero.getMapPosition().getXPosition() + 1);
                            UpdateHeroPosition();
                        } else {
                            System.out.println("Invalid Argument");
                        }
                        break;
                    case 5:
                        if (!artifactChoice && !enemyEncounter) {
                            playerHero.getMapPosition().setYPosition(playerHero.getMapPosition().getYPosition() - 1);
                            UpdateHeroPosition();
                        } else {
                            System.out.println("Invalid Argument");
                        }
                        break;
                    case 6:
                        if (!artifactChoice && !enemyEncounter) {
                            playerHero.getMapPosition().setXPosition(playerHero.getMapPosition().getXPosition() - 1);
                            UpdateHeroPosition();
                        } else {
                            System.out.println("Invalid Argument");
                        }
                        break;
                    case 7:
                        if (enemyEncounter) {
                            ConsoleFightEnemy();
                        } else {
                            System.out.println("Invalid Argument");
                        }
                        break;
                    case 8:
                        if (enemyEncounter) {
                            ConsoleFleeEnemy();
                        } else {
                            System.out.println("Invalid Argument");
                        }
                        break;
                    case 9:
                        if (artifactChoice) {
                            consoleGameView.ClearScreen();
                            ChangeEquipment();
                        } else {
                            System.out.println("Invalid Argument");
                        }
                        break;
                    case 10:
                        if (artifactChoice) {
                            System.out.println("You ignored the equipment");
                            consoleGameView.ClearScreen();
                            consoleGameView.PlayGame(playerHero);
                        } else {
                            System.out.println("Invalid Argument");
                        }
                        break;
                    case 11:
                        if (!enemyEncounter) {
                            consoleGameView.DisplayHeroStats(playerHero, inventory);
                            consoleGameView.PlayGame(playerHero);
                        } else {
                            System.out.println("Invalid Argument");
                        }
                        break;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid integer can not be parsed");
            WaitABit(1);
            consoleGameView.PlayGame(playerHero);
        } catch (Exception exception) {
            System.out.println("There was an error");
            exception.printStackTrace();
        }
    }

    private void ConsoleFightEnemy() {
        try {
            consoleGameView.ClearScreen();
            gameUpdates = new Mechanics().DeathBattle(enemyChallenger, playerHero, false, guiGameView, consoleGameView, screenDisplay);
            if (gameUpdates.contains("won")) {
                enemies.remove(enemyChallenger);
                CheckEnemyDrops();
                GameOutputMessage.OutputToScreen("You have won the battle!", screenDisplay, guiGameView, consoleGameView);
            } else if (gameUpdates.contains("defeated")) {
                GameOutputMessage.OutputToScreen("You have lost the battle!", screenDisplay, guiGameView, consoleGameView);

                for (Artifact artifact : inventory.getEquippedItems()) {
                    RemoveEquipmentStat(artifact);
                }

                playerHero.LostExp();
                inventory.ScrapEquipment();
                WaitABit(2);
            }
            enemyEncounter = false;
            enemyChallenger = null;
            consoleGameView.PlayGame(playerHero);
            System.out.println();
        } catch (Exception e) {
            System.out.println("There was an error");
            e.printStackTrace();
        }
    }

    /**
     * Utility Functions
     */

    private void WaitABit(long sleep) {
        try {
            TimeUnit.SECONDS.sleep(sleep);
        } catch (InterruptedException e) {
            System.out.println("Could not sleep");
        }
    }

    private int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    private List<UnitStats> getUnitStatsList() {
        List<UnitStats> unitStatsList = new ArrayList<>();
        try {

            unitStatsList.add(new UnitStats(0, 0, 14, 15, 140, 140, 3, 1));
            unitStatsList.add(new UnitStats(0, 0, 20, 13, 100, 100, 3, 1));
            unitStatsList.add(new UnitStats(0, 0, 15, 8, 120, 120, 3, 4));

        } catch (Exception e) {
            System.out.println("There was an error with getUnitStatsList()");
            e.printStackTrace();
        }
        return unitStatsList;
    }

    /**
     * Button Action Handler Functions
     */

    class SwitchGameView implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if (!artifactChoice && !enemyEncounter) {
                    SwitchViews();
                }
            } catch (Exception error) {
                System.out.println("Switch screen erroor");
                error.printStackTrace();
            }
        }
    }

    class ChooseClass implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getActionCommand().equals("comboBoxChanged")) {
                    String heroClass = (String) guiGameView.getGuiNewPlayer().getHeroClassSelection().getSelectedItem();
                    if (heroClass != null) {
                        switch (heroClass) {
                            case "Tank":
                                guiGameView.getGuiNewPlayer().ChangeHero(unitStatsList.get(0));
                                break;
                            case "Mage":
                                guiGameView.getGuiNewPlayer().ChangeHero(unitStatsList.get(1));
                                break;
                            case "Support":
                                guiGameView.getGuiNewPlayer().ChangeHero(unitStatsList.get(2));
                                break;
                        }
                    }
                }
            } catch (Exception error) {
                System.out.println("New player class selection had an error");
                error.printStackTrace();
            }
        }
    }

    class MoveCharacterNorth implements ActionListener {
        public void actionPerformed(ActionEvent movementDirection) {
            try {
                if (!enemyEncounter && !artifactChoice) {
                    if (movementDirection.getActionCommand().equals("North")) {
                        playerHero.getMapPosition().setYPosition(playerHero.getMapPosition().getYPosition() + 1);
                        moveHero = true;
                    }
                }
            } catch (Exception exception) {
                System.out.println("There was an error with the move North action!");
                exception.printStackTrace();
            } finally {
                if (moveHero) {
                    UpdateHeroPosition();
                    moveHero = false;
                }
            }
        }
    }

    class MoveCharacterSouth implements ActionListener {
        public void actionPerformed(ActionEvent movementDirection) {
            try {
                if (!enemyEncounter && !artifactChoice) {
                    if (movementDirection.getActionCommand().equals("South")) {
                        playerHero.getMapPosition().setYPosition(playerHero.getMapPosition().getYPosition() - 1);
                        moveHero = true;
                    }
                }
            } catch (Exception exception) {
                System.out.println("There was an error with the move South action!");
                exception.printStackTrace();
            } finally {
                if (moveHero) {
                    UpdateHeroPosition();
                    moveHero = false;
                }
            }
        }
    }

    class MoveCharacterEast implements ActionListener {
        public void actionPerformed(ActionEvent movementDirection) {
            try {
                if (!enemyEncounter && !artifactChoice) {
                    if (movementDirection.getActionCommand().equals("East")) {
                        playerHero.getMapPosition().setXPosition(playerHero.getMapPosition().getXPosition() + 1);
                        moveHero = true;
                    }
                }
            } catch (Exception exception) {
                System.out.println("There was an error with the move East action!");
                exception.printStackTrace();
            } finally {
                if (moveHero) {
                    UpdateHeroPosition();
                    moveHero = false;
                }
            }
        }
    }

    class MoveCharacterWest implements ActionListener {
        public void actionPerformed(ActionEvent movementDirection) {
            try {
                if (!enemyEncounter && !artifactChoice) {
                    if (movementDirection.getActionCommand().equals("West")) {
                        playerHero.getMapPosition().setXPosition(playerHero.getMapPosition().getXPosition() - 1);
                        moveHero = true;
                    }
                }
            } catch (Exception exception) {
                System.out.println("There was an error with the move West action!");
                exception.printStackTrace();
            } finally {
                if (moveHero) {
                    UpdateHeroPosition();
                    moveHero = false;
                }
            }
        }
    }

    class FightEnemy implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                if (enemyEncounter) {
                    gameUpdates = new Mechanics().DeathBattle(enemyChallenger, playerHero, false, guiGameView, consoleGameView, screenDisplay);
                    if (gameUpdates.contains("won")) {
                        enemies.remove(enemyChallenger);
                        CheckEnemyDrops();
                    } else if (gameUpdates.contains("defeated")) {
                        guiGameView.getGuiGameOutput().UpdateScreen("You have lost the battle the battle!");
                        guiGameView.getGuiGameOutput().UpdateScreen("Experience lost and equipment has been scrapped");

                        for (Artifact artifact : inventory.getEquippedItems()) {
                            RemoveEquipmentStat(artifact);
                        }

                        playerHero.LostExp();
                        inventory.ScrapEquipment();
                    }

                    guiGameView.getGuiHeroView().RefreshHeroStats(playerHero, inventory);
                    enemyEncounter = false;
                    enemyChallenger = null;
                }
            } catch (Exception exception) {
                System.out.println("There was an error with the move fight enemy action!");
                exception.printStackTrace();
            }
        }
    }

    class FleeEnemy implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                if (enemyEncounter) {
                    gameUpdates = new Mechanics().DeathBattle(enemyChallenger, playerHero, true, guiGameView, consoleGameView, screenDisplay);
                    if (gameUpdates.contains("escape")) {
                        enemies.remove(enemyChallenger);
                        guiGameView.getGuiGameOutput().UpdateScreen("You managed to escape battle!");
                    } else if (gameUpdates.contains("won")) {
                        enemies.remove(enemyChallenger);
                        CheckEnemyDrops();
                    } else if (gameUpdates.contains("defeated")) {
                        guiGameView.getGuiGameOutput().UpdateScreen("You have lost the battle the battle!\n Experience lost and equipment has been scrapped");

                        for (Artifact artifact : inventory.getEquippedItems()) {
                            RemoveEquipmentStat(artifact);
                        }

                        inventory.ScrapEquipment();
                        playerHero.LostExp();
                    }

                    guiGameView.getGuiHeroView().RefreshHeroStats(playerHero, inventory);
                    enemyEncounter = false;
                    enemyChallenger = null;
                }
            } catch (Exception exception) {
                System.out.println("There was an error with the move West action!");
                exception.printStackTrace();
            }
        }
    }

    class MainMenu implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                guiGameView.MenuFromGame(SQLite.RetrieveNames());
                playerHero = null;
                GuiMenuScreen();
            } catch (Exception exception) {
                System.out.println("There was an error with the MainMenu button");
                exception.printStackTrace();
            }
        }
    }

    class SaveGame implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                if (!artifactChoice && !enemyEncounter) {
                    SQLite.SaveGame(playerHero, inventory);
                    guiGameView.getGuiGameOutput().UpdateScreen("Game has been saved!");
                    guiGameView.getGuiHeroView().RefreshHeroStats(playerHero, inventory);
                }
            } catch (Exception exception) {
                System.out.println("There was an error with the MainMenu button");
                exception.printStackTrace();
            }
        }
    }

    class GuiContinueGame implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                StartUpGame();
                guiGameView.ContinueGame(playerHero, inventory);
                GuiGamingScreen();
            } catch (NullPointerException exception) {
                System.out.println("There was a null pointer exception during game continue");
                exception.printStackTrace();
            } catch (JSONException exception) {
                System.out.println("A json exception has been caught during continue game");
                exception.printStackTrace();
            }
        }
    }

    class GuiNewGame implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                guiGameView.NewPlayer();
                GuiNewGameButtonEvents();
            } catch (Exception exception) {
                System.out.println("There was an error with the Newgame button");
                exception.printStackTrace();
            }
        }
    }

    class NewGameMainMenu implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                guiGameView.MenuFromNewPlayer(SQLite.RetrieveNames());
                GuiMenuScreen();
            } catch (Exception exception) {
                System.out.println("There was an error with the NewGameMainMenu button");
                exception.printStackTrace();
            }
        }
    }

    class EquipArtifact implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                if (artifactChoice) {
                    ChangeEquipment();
                }
            } catch (Exception exception) {
                System.out.println("There was an error with the Equip Artifact button");
                exception.printStackTrace();
            }
        }
    }

    class IgnoreArtifact implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                if (artifactChoice) {
                    artifactChoice = false;
                    guiGameView.getGuiGameOutput().UpdateScreen("You left the artifact " + foundEquipmentName);
                    foundEquipmentClass = null;
                    foundEquipmentName = null;
                }
            } catch (Exception exception) {
                System.out.println("There was an error with the Ignore Artifact button");
                exception.printStackTrace();
            }
        }
    }

    class GuiStartGame implements ActionListener {
        public void actionPerformed(ActionEvent movementDirection) {
            try {
                String name = guiGameView.getGuiNewPlayer().getHeroName().getText();
                String heroClass = (String) guiGameView.getGuiNewPlayer().getHeroClassSelection().getSelectedItem();

                StartGameControl(name, heroClass);
            } catch (IllegalArgumentException e) {
                System.out.println("Parameter cannot be null");
                e.printStackTrace();
            }
        }
    }
}
