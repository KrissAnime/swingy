package wethinkcode.com.swingy.View.gui;

import wethinkcode.com.swingy.Model.Inventory.Inventory;
import wethinkcode.com.swingy.Model.Stats.Artifact;
import wethinkcode.com.swingy.Model.Units.GameCharacters;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class GuiHeroView extends JPanel {
    private JLabel heroNameLabel;
    private JLabel heroLevelLabel;
    private JLabel heroExperienceLabel;
    private JLabel heroHealthLabel;
    private JLabel heroLocationLabel;
    private JLabel heroAttackLabel;
    private JLabel heroDefenceLabel;
    private JLabel heroTurnLabel;
    private JLabel heroWeaponLabel;
    private JLabel heroArmorLabel;
    private JLabel heroHelmetLabel;

    private JLabel heroTurn;
    private JLabel heroWeapon;
    private JLabel heroWeaponStat;
    private JLabel heroArmor;
    private JLabel heroArmorStat;
    private JLabel heroHelmet;
    private JLabel heroHelmetStat;
    private JLabel heroName;
    private JLabel heroLevel;
    private JLabel heroExperience;
    private JLabel heroHealth;
    private JLabel heroLocation;
    private JLabel heroAttack;
    private JLabel heroDefence;

    private JButton moveNorthButton;
    private JButton moveEastButton;
    private JButton moveSouthButton;
    private JButton moveWestButton;
    private JButton fightButton;
    private JButton fleeButton;
    private JButton equipButton;
    private JButton ignoreButton;


    GuiHeroView(GameCharacters Hero, Inventory inventory){
        Dimension dim = getPreferredSize();
        dim.width = 400;
        dim.height = 100;
        setPreferredSize(dim);

        InitVariables(Hero, inventory);

        Border innerBorder = BorderFactory.createTitledBorder("Hero Stats");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        InitHeroStatsView(gridBagConstraints);
        InitHeroActions(gridBagConstraints);
        InitHeroArtifacts(gridBagConstraints);

    }

    private void InitVariables(GameCharacters Hero, Inventory inventory){
        heroNameLabel = new JLabel("Name: ");
        heroLevelLabel = new JLabel("Level: ");
        heroExperienceLabel = new JLabel("Exp: ");
        heroHealthLabel = new JLabel("Health: ");
        heroLocationLabel = new JLabel("Location: ");
        heroAttackLabel = new JLabel("Attack: ");
        heroDefenceLabel = new JLabel("Defence: ");
        heroTurnLabel = new JLabel("Turn: ");
        heroWeaponLabel = new JLabel("Weapon: ");
        heroArmorLabel = new JLabel("Armor: ");
        heroHelmetLabel = new JLabel("Helmet: ");

        heroName = new JLabel(Hero.getName());
        heroLevel = new JLabel(String.valueOf(Hero.getUnitStats().getLevel()));
        heroExperience = new JLabel(String.valueOf(Hero.getUnitStats().getExperience()));
        heroHealth = new JLabel(String.valueOf(Hero.getUnitStats().getHitPoints()) + "/" + String.valueOf(Hero.getUnitStats().getMaxHitPoints()));
        heroLocation = new JLabel("y: " + String.valueOf(Hero.getMapPosition().getYPosition()) + ", x: " + String.valueOf(Hero.getMapPosition().getXPosition()));
        heroAttack = new JLabel(String.valueOf(Hero.getUnitStats().getAttack()));
        heroDefence = new JLabel(String.valueOf(Hero.getUnitStats().getDefence()));
        heroTurn = new JLabel(String.valueOf(Hero.getHeroTurn()));

        heroWeapon = new JLabel("");
        heroWeaponStat = new JLabel("0 Attack Buff");
        heroHelmet = new JLabel("");
        heroHelmetStat = new JLabel("0 HP Buff");
        heroArmor = new JLabel("");
        heroArmorStat = new JLabel("0 Defence Buff");

        for (Artifact artifact: inventory.getEquippedItems()) {
            switch (artifact.getArtifactClass()) {
                case "Weapon":
                    heroWeapon.setText(artifact.getArtifactName());
                    heroWeaponStat.setText(String.valueOf(artifact.getArtifactStat()) + " Attack");
                    break;
                case "Helmet":
                    heroHelmet.setText(artifact.getArtifactName());
                    heroHelmetStat.setText(String.valueOf(artifact.getArtifactStat()) + " HP");
                    break;
                case "Armor":
                    heroArmor.setText(artifact.getArtifactName());
                    heroArmorStat.setText(String.valueOf(artifact.getArtifactStat()) + " Defence");
                    break;
            }
        }


        moveNorthButton = new JButton("North");
        moveEastButton = new JButton("East");
        moveSouthButton = new JButton("South");
        moveWestButton = new JButton("West");
        fightButton = new JButton("Fight");
        fleeButton = new JButton("Flee");
        ignoreButton = new JButton("Ignore Artifact");
        equipButton = new JButton("Equip Artifact");
    }

    private void InitHeroStatsView(GridBagConstraints gridBagConstraints) {
        //////First Row///////

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(heroNameLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(heroName, gridBagConstraints);


        //////Second Row///////
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroLevelLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroLevel, gridBagConstraints);

        //////Third Row///////
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroExperienceLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroExperience, gridBagConstraints);

        //////Fourth Row///////
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroHealthLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroHealth, gridBagConstraints);

        //////Fifth Row///////
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroAttackLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroAttack, gridBagConstraints);

        //////Sixth Row///////
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroDefenceLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroDefence, gridBagConstraints);

        //////Seventh Row///////
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroLocationLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroLocation, gridBagConstraints);

        //////Eighth Row///////
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroTurnLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroTurn, gridBagConstraints);
    }

    private void InitHeroActions(GridBagConstraints gridBagConstraints) {

        //////Movement Actions///////
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        add(moveNorthButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        add(moveWestButton, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        add(moveEastButton, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        add(moveSouthButton, gridBagConstraints);

        //////Fight Actions///////

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        add(fightButton, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        add(fleeButton, gridBagConstraints);
    }

    private void InitHeroArtifacts(GridBagConstraints gridBagConstraints) {
        //////Inventory List Actions///////

        ////////Weapon Artifact////////
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        add(heroWeaponLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        add(heroWeapon, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        add(heroWeaponStat, gridBagConstraints);

        ////////Helmet Artifact////////
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        add(heroHelmetLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        add(heroHelmet, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        add(heroHelmetStat, gridBagConstraints);

        ////////Armor Artifact////////
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        add(heroArmorLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        add(heroArmor, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        add(heroArmorStat, gridBagConstraints);

        //////Artifact Buttons///////
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        add(equipButton, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        add(ignoreButton, gridBagConstraints);
    }

    public void RefreshHeroStats(GameCharacters Hero, Inventory inventory){
        heroLevel.setText(String.valueOf(Hero.getUnitStats().getLevel()));
        heroExperience.setText(String.valueOf(Hero.getUnitStats().getExperience()));
        heroHealth.setText(String.valueOf(Hero.getUnitStats().getHitPoints()) + "/" + String.valueOf(Hero.getUnitStats().getMaxHitPoints()));
        heroAttack.setText(String.valueOf(Hero.getUnitStats().getAttack()));
        heroDefence.setText(String.valueOf(Hero.getUnitStats().getDefence()));
        heroTurn.setText(String.valueOf(Hero.getHeroTurn()));
        heroLocation.setText("y: " + String.valueOf(Hero.getMapPosition().getYPosition()) + ", x: " + String.valueOf(Hero.getMapPosition().getXPosition()));
        for (Artifact artifact: inventory.getEquippedItems()) {
            switch (artifact.getArtifactClass()) {
                case "Weapon":
                    heroWeapon.setText(artifact.getArtifactName());
                    heroWeaponStat.setText(String.valueOf(artifact.getArtifactStat()) + " Attack");
                    break;
                case "Armor":
                    heroArmor.setText(artifact.getArtifactName());
                    heroArmorStat.setText(String.valueOf(artifact.getArtifactStat()) + " Defence");
                    break;
                case "Helmet":
                    heroHelmet.setText(artifact.getArtifactName());
                    heroHelmetStat.setText(String.valueOf(artifact.getArtifactStat()) + " HP");
                    break;
            }
        }
        if (inventory.getEquippedItems().size() == 0) {
            heroWeapon.setText("       ");
            heroWeaponStat.setText("0 Attack");
            heroArmor.setText("       ");
            heroArmorStat.setText("0 Defence");
            heroHelmet.setText("       ");
            heroHelmetStat.setText("0 HP");
        }
    }

    public void MovePlayerNorth(ActionListener actionListener) {
        moveNorthButton.addActionListener(actionListener);
    }

    public void MovePlayerEast(ActionListener actionListener) { moveEastButton.addActionListener(actionListener); }

    public void MovePlayerSouth(ActionListener actionListener) { moveSouthButton.addActionListener(actionListener); }

    public void MovePlayerWest(ActionListener actionListener) {
        moveWestButton.addActionListener(actionListener);
    }


    public void FightMonster(ActionListener actionListener) {
        fightButton.addActionListener(actionListener);
    }

    public void FleeMonster(ActionListener actionListener) {
        fleeButton.addActionListener(actionListener);
    }

    public void EquipArtifact(ActionListener actionListener) { equipButton.addActionListener(actionListener); }

    public void IgnoreArtifact(ActionListener actionListener) { ignoreButton.addActionListener(actionListener); }
}
