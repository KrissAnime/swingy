package wethinkcode.com.swingy.Model.Stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public @Getter @Setter @AllArgsConstructor class Artifact {
    private String artifactName;
    private String artifactRarity;
    private String artifactClass;
    private int artifactStat;
}
