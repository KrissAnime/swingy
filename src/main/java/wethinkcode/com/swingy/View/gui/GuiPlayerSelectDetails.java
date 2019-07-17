package wethinkcode.com.swingy.View.gui;

import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GuiPlayerSelectDetails extends JPanel {
    private JLabel heroNameLabel;
    private JLabel heroLevelLabel;
    private JLabel heroExperienceLabel;
    private JLabel heroHealthLabel;
    private JLabel heroLocationLabel;
    private JLabel heroAttackLabel;
    private JLabel heroDefenceLabel;
    private JLabel heroClassLabel;
    private JLabel heroPotionsLabel;
    private JTextField heroClass;
    private JTextField heroName;
    private JTextField heroLevel;
    private JTextField heroExperience;
    private JTextField heroHealth;
    private JTextField heroLocation;
    private JTextField heroAttack;
    private JTextField heroDefence;
    private JTextField heroPotions;


    public GuiPlayerSelectDetails() {
        Dimension dim = getPreferredSize();
        dim.width = 250;
        dim.height = 100;
        setPreferredSize(dim);

        InitVariables();

        Border innerBorder = BorderFactory.createTitledBorder("Hero Stats");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        InitHeroStatsView(gridBagConstraints);
    }

    private void InitVariables() {
        heroNameLabel = new JLabel("Name: ");
        heroLevelLabel = new JLabel("Level: ");
        heroExperienceLabel = new JLabel("Exp: ");
        heroHealthLabel = new JLabel("Health: ");
        heroLocationLabel = new JLabel("Location: ");
        heroAttackLabel = new JLabel("Attack: ");
        heroDefenceLabel = new JLabel("Defence: ");
        heroClassLabel = new JLabel("Class: ");
        heroPotionsLabel = new JLabel("Potions: ");

        heroName = new JTextField(10);
        heroLevel = new JTextField(10);
        heroExperience = new JTextField(10);
        heroHealth = new JTextField(10);
        heroLocation = new JTextField(10);
        heroAttack = new JTextField(10);
        heroDefence = new JTextField(10);
        heroClass = new JTextField(10);
        heroPotions = new JTextField(10);

        heroName.setEditable(false);
        heroLevel.setEditable(false);
        heroExperience.setEditable(false);
        heroHealth.setEditable(false);
        heroLocation.setEditable(false);
        heroAttack.setEditable(false);
        heroDefence.setEditable(false);
        heroClass.setEditable(false);
        heroPotions.setEditable(false);
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
        add(heroClassLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroClass, gridBagConstraints);

        //////Eighth Row///////
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 2;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroPotionsLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroPotions, gridBagConstraints);
    }

    public void ChangeHero(JSONObject Hero) {
        heroName.setText(Hero.getString("hero_name"));
        heroLevel.setText(Hero.getString("level"));
        heroExperience.setText(Hero.getString("experience"));
        heroHealth.setText(Hero.getString("hitPoints") + "/" + Hero.getString("maxHitPoints"));
        heroLocation.setText(Hero.getString("x_position") + ", " + Hero.getString("y_position"));
        heroAttack.setText(Hero.getString("attack"));
        heroDefence.setText(Hero.getString("defence"));
        heroClass.setText(Hero.getString("hero_class"));
        heroPotions.setText(Hero.getString("potions"));
    }
}
