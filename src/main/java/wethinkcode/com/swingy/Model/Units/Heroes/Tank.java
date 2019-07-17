package wethinkcode.com.swingy.Model.Units.Heroes;


import wethinkcode.com.swingy.Model.Stats.UnitStats;
import wethinkcode.com.swingy.Model.Units.GameCharacters;
import wethinkcode.com.swingy.Model.Units.MapPosition;

public class Tank extends GameCharacters {
    public Tank(String name, UnitStats unitStats, MapPosition mapPosition, String heroClass, int turn) {
        super(name, unitStats, mapPosition, heroClass, turn);
    }
}
