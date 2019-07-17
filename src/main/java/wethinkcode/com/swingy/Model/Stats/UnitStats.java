package wethinkcode.com.swingy.Model.Stats;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public @Getter @Setter @AllArgsConstructor class UnitStats {
    protected @NotNull int level;
    protected @NotNull int experience;
    protected @NotNull int attack;
    protected @NotNull int defence;
    protected @NotNull int hitPoints;
    protected @NotNull int maxHitPoints;
    protected @NotNull int luck;
    protected @NotNull int potions;
}
