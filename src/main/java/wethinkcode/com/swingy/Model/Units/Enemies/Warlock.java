package wethinkcode.com.swingy.Model.Units.Enemies;

import wethinkcode.com.swingy.Model.Stats.UnitStats;
import wethinkcode.com.swingy.Model.Units.GameCharacters;
import wethinkcode.com.swingy.Model.Units.MapPosition;

public class Warlock extends GameCharacters {
    public Warlock(String name, UnitStats unitStats, MapPosition mapPosition, String heroClass, int turn) {
        super(name, unitStats, mapPosition, heroClass, turn);
    }

}
