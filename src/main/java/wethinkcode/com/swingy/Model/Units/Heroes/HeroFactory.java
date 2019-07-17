package wethinkcode.com.swingy.Model.Units.Heroes;

import wethinkcode.com.swingy.Model.Stats.UnitStats;
import wethinkcode.com.swingy.Model.Units.GameCharacters;
import wethinkcode.com.swingy.Model.Units.MapPosition;

public abstract class HeroFactory {
    public HeroFactory(){}

    public static GameCharacters newHero(String name, String heroClass, UnitStats unitStats, MapPosition mapPosition, int turn) {
        switch (heroClass) {
            case "Mage":
                return new Mage(name, unitStats, mapPosition, heroClass, turn);
            case "Tank":
                return new Tank(name, unitStats, mapPosition, heroClass, turn);
            case "Support":
                return new Support(name, unitStats, mapPosition, heroClass, turn);
        }
        return null;
    }
}