package wethinkcode.com.swingy.Model.Artifacts;

import wethinkcode.com.swingy.Model.Artifacts.Types.Armor;
import wethinkcode.com.swingy.Model.Artifacts.Types.Helmet;
import wethinkcode.com.swingy.Model.Artifacts.Types.Weapons;
import wethinkcode.com.swingy.Model.Stats.Artifact;

public abstract class ArtifactFactory {
    public ArtifactFactory(){}

    public static Artifact newArtifact(String artifactClass, String artifactName, String artifactRarity, int artifactStat){
        switch (artifactClass) {
            case "Weapon":
                return new Weapons(artifactName, artifactRarity, artifactClass, artifactStat);
            case "Armor":
                return new Armor(artifactName, artifactRarity, artifactClass, artifactStat);
            case "Helmet":
                return new Helmet(artifactName, artifactRarity, artifactClass, artifactStat);
        }
        return null;
    }
}
