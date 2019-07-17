package wethinkcode.com.swingy.Model.Units.Enemies;


import wethinkcode.com.swingy.Model.Stats.UnitStats;
import wethinkcode.com.swingy.Model.Units.GameCharacters;
import wethinkcode.com.swingy.Model.Units.MapPosition;

public class Berserker extends GameCharacters {
    public Berserker(String name, UnitStats unitStats, MapPosition mapPosition, String heroClass, int turn) {
        super(name, unitStats, mapPosition, heroClass, turn);
    }
}
