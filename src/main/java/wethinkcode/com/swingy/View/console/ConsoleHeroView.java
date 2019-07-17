package wethinkcode.com.swingy.View.console;

import wethinkcode.com.swingy.Model.Inventory.Inventory;
import wethinkcode.com.swingy.Model.Stats.Artifact;
import wethinkcode.com.swingy.Model.Units.GameCharacters;

class ConsoleHeroView {
    ConsoleHeroView(){}

    void DisplayBasicInfo(GameCharacters Hero) {
        System.out.println("Hero name: " + Hero.getName());
        System.out.println("Location: X: " + Hero.getMapPosition().getXPosition() + ", Y: " + Hero.getMapPosition().getYPosition());
        System.out.println();
    }

    public void DisplayHeroMenu(){
        System.out.println("1.  Main Menu");
        System.out.println("2.  Save Game");
        System.out.println("3.  Move North");
        System.out.println("4.  Move East");
        System.out.println("5.  Move South");
        System.out.println("6.  Move West");
        System.out.println("7.  Fight");
        System.out.println("8.  Flee");
        System.out.println("9.  Equip Artifact");
        System.out.println("10. Ignore Artifact");
        System.out.println("11. View Stats");
    }

    public void DisplayHeroStats(Inventory inventory){
        for (Artifact artifact: inventory.getEquippedItems()) {
            switch (artifact.getArtifactClass()) {
                case "Weapon":
                    System.out.println("Weapon: " + artifact.getArtifactName() + " " + String.valueOf(artifact.getArtifactStat()) + " Attack");
                    break;
                case "Armor":
                    System.out.println("Armor: " + artifact.getArtifactName() + " " + String.valueOf(artifact.getArtifactStat()) + " Defence");
                    break;
                case "Helmet":
                    System.out.println("Helmet: " + artifact.getArtifactName() + " " + String.valueOf(artifact.getArtifactStat()) + " HP");
                    break;
            }
        }
        if (inventory.getEquippedItems().size() == 0) {
            System.out.println("Weapon: 0 Attack");
            System.out.println("Armor: 0 Defence");
            System.out.println("Helmet: 0 HP");
            System.out.println();
        }
    }
}
