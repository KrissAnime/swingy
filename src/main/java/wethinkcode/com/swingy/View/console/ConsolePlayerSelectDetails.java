package wethinkcode.com.swingy.View.console;

import org.json.JSONObject;
import wethinkcode.com.swingy.Model.Units.GameCharacters;

class ConsolePlayerSelectDetails {
    ConsolePlayerSelectDetails(){}

    public void DisplayJSONHero(JSONObject Hero){
        System.out.println("Name: " + Hero.getString("hero_name"));
        System.out.println("Level: " + Hero.getString("level"));
        System.out.println("Exp: " + Hero.getString("experience"));
        System.out.println("Health: " + Hero.getString("hitPoints") + "/" + Hero.getString("maxHitPoints"));
        System.out.println("Attack: " + Hero.getString("attack"));
        System.out.println("Defence: " + Hero.getString("defence"));
        System.out.println("Location: " + Hero.getString("x_position") + ", " + Hero.getString("y_position"));
        System.out.println("Class: " + Hero.getString("hero_class"));
        System.out.println("Potions: " + Hero.getString("potions"));
    }

    void DisplayHero(GameCharacters Hero){
        System.out.println("Name: " + Hero.getName());
        System.out.println("Level: " + Hero.getUnitStats().getLevel());
        System.out.println("Exp: " + Hero.getUnitStats().getExperience());
        System.out.println("Health: " + Hero.getUnitStats().getHitPoints() + "/" + Hero.getUnitStats().getMaxHitPoints());
        System.out.println("Attack: " + Hero.getUnitStats().getAttack());
        System.out.println("Defence: " + Hero.getUnitStats().getDefence());
        System.out.println("Location: " + Hero.getMapPosition().getXPosition() + ", " + Hero.getMapPosition().getYPosition());
        System.out.println("Potions: " + Hero.getUnitStats().getPotions());
        System.out.println("Turns: " + Hero.getHeroTurn());
    }
}
