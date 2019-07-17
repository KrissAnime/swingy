package wethinkcode.com.swingy.View.console;

import wethinkcode.com.swingy.Model.Stats.UnitStats;

class ConsoleNewPlayer {

    ConsoleNewPlayer() {
        System.out.println("New Player");
    }

    void EnterPlayerName(String heroName) {
        if (heroName != null) {
            System.out.println("Hero Name: " + heroName);
        } else {
            System.out.println("Enter Hero Name: ");
        }
    }

    void SelectPlayerClass() {
        System.out.println("1: Tank");
        System.out.println("2: Mage");
        System.out.println("3: Support");
        System.out.println("4: Start Game");
        System.out.println("5: Main Menu");
    }

    void DisplayHero(UnitStats unitStats){
        System.out.println("Health: " + unitStats.getMaxHitPoints());
        System.out.println("Attack: " + unitStats.getAttack());
        System.out.println("Defence: " + unitStats.getDefence());
        System.out.println("Luck: " + unitStats.getLuck());
        System.out.println("Potions: " + unitStats.getPotions());
    }
}
