package wethinkcode.com.swingy.Model.Units.Enemies;

import wethinkcode.com.swingy.Model.Stats.UnitStats;
import wethinkcode.com.swingy.Model.Units.GameCharacters;
import wethinkcode.com.swingy.Model.Units.MapPosition;

public abstract class EnemyFactory {
    public EnemyFactory(){}

    public static GameCharacters newEnemy(String name, String enemyClass, UnitStats unitStats, MapPosition mapPosition, int turn) {

        switch (enemyClass) {
            case "Berserker":
                return new Berserker(name, unitStats, mapPosition, enemyClass, turn);
            case "Warlock":
                return new Warlock(name, unitStats, mapPosition, enemyClass, turn);
        }
        return null;
    }
}
