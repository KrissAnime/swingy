package wethinkcode.com.swingy.Model.Inventory;

import lombok.Getter;
import lombok.Setter;
import wethinkcode.com.swingy.Model.Stats.Artifact;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private @Getter @Setter List<Artifact> equippedItems;

    public Inventory(){
    }

    public void equipArtifact(Artifact artifact) {
        equippedItems.add(artifact);
    }

    public void removeArtifact(Artifact artifact) {
        equippedItems.remove(artifact);
    }

    public void destroyArtifact(Artifact artifact) {
        equippedItems.remove(artifact);
    }

    public void ScrapEquipment(){
        equippedItems = null;
        StartingEquipment();
    }

    public void StartingEquipment(){
        equippedItems = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder equipment = new StringBuilder();
        for (Artifact artifact: equippedItems) {
            equipment.append(artifact.getArtifactName());
            equipment.append('\n');
        }
        return equipment.toString();
    }
}
