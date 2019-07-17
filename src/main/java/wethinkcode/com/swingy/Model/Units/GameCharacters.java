package wethinkcode.com.swingy.Model.Units;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import wethinkcode.com.swingy.Model.Stats.UnitStats;

public @AllArgsConstructor class GameCharacters {
    private @Getter @Setter @NonNull String name;
    private @Getter @Setter @NonNull UnitStats unitStats;
    private @Getter @Setter @NonNull MapPosition mapPosition;
    private @Getter @Setter @NonNull String heroClass;
    private @Getter @Setter @NonNull int heroTurn;


    public boolean expGained(int exp) {
        getUnitStats().setExperience(getUnitStats().getExperience() + exp);
        if (((getUnitStats().getLevel() + 1) * 1000 + ((getUnitStats().getLevel() - 1) * (getUnitStats().getLevel() - 1)) * 450) <= getUnitStats().getExperience()) {
            levelUp();
            return true;
        }
        return false;
    }

    public void levelUp() {
        getUnitStats().setLevel(getUnitStats().getLevel() + 1);
        levelUpStatsIncrease();
    }

    private void levelUpStatsIncrease() {
        unitStats.setAttack(unitStats.getAttack() + 10);
        unitStats.setDefence(unitStats.getDefence() + 10);
        unitStats.setMaxHitPoints(unitStats.getMaxHitPoints() + 25);
        unitStats.setHitPoints(unitStats.getMaxHitPoints());
        if (getUnitStats().getLevel() >= 5) {
            unitStats.setLuck(getUnitStats().getLuck() + 3);
        } else if (getUnitStats().getLevel() >= 3) {
            unitStats.setLuck(getUnitStats().getLuck() + 1);
        }
    }

    public void ResetPlayerPosition(){
        mapPosition.setMapSize((unitStats.getLevel() - 1) * 5 + 10 - (unitStats.getLevel() % 2));
        mapPosition.setXPosition(mapPosition.getMapSize() / 2);
        mapPosition.setYPosition(mapPosition.getMapSize() / 2);
        heroTurn = 0;
    }

    public void LostExp(){
        unitStats.setExperience((unitStats.getLevel() * 1000) + ((getUnitStats().getLevel() - 1) * (getUnitStats().getLevel() - 1)) * 450);
        unitStats.setHitPoints(unitStats.getMaxHitPoints());
        ResetPlayerPosition();
    }
}
