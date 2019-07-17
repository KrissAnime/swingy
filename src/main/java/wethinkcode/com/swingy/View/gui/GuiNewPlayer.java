package wethinkcode.com.swingy.View.gui;

import lombok.Getter;
import wethinkcode.com.swingy.Model.Stats.UnitStats;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class GuiNewPlayer extends JPanel {
    private static String[] heroClasses = {"Mage", "Tank", "Support"};

    private JButton menuButton;
    private JButton startButton;
    private JButton switchViewNewPlayer;

    private @Getter JComboBox heroClassSelection;

    private JLabel heroNameLabel;
    private JLabel heroClassLabel;
    private JLabel heroHealthLabel;
    private JLabel heroLuckLabel;
    private JLabel heroAttackLabel;
    private JLabel heroDefenceLabel;
    private JLabel heroPotionsLabel;

    private @Getter JTextField heroName;
    private JTextField heroHealth;
    private JTextField heroLuck;
    private JTextField heroAttack;
    private JTextField heroDefence;
    private JTextField heroPotions;

    GuiNewPlayer(){

        Init();

        Border innerBorder = BorderFactory.createTitledBorder("New Player");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        //////HERO STATS VIEW//////

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        add(heroHealthLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(heroHealth, gridBagConstraints);

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        add(heroAttackLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(heroAttack, gridBagConstraints);

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        add(heroDefenceLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(heroDefence, gridBagConstraints);

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        add(heroLuckLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(heroLuck, gridBagConstraints);

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        add(heroPotionsLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(heroPotions, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        add(heroNameLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(heroName, gridBagConstraints);

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        add(heroClassLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(heroClassSelection, gridBagConstraints);

        ///////////////////////////////////////////

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        add(menuButton, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(startButton, gridBagConstraints);

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(switchViewNewPlayer, gridBagConstraints);
    }

    private void Init(){
        heroNameLabel = new JLabel("Hero Name: ");
        heroName = new JTextField(10);
        heroName.setEditable(true);
        heroClassLabel = new JLabel("Hero Class: ");
        heroClassSelection = new JComboBox(heroClasses);
        heroClassSelection.setSelectedItem(null);

        menuButton = new JButton("Main Menu");
        startButton = new JButton("Start Game");
        switchViewNewPlayer = new JButton("Switch View");

        heroHealthLabel = new JLabel("Health: ");
        heroLuckLabel = new JLabel("Luck: ");
        heroAttackLabel = new JLabel("Attack: ");
        heroDefenceLabel = new JLabel("Defence: ");
        heroPotionsLabel = new JLabel("Potions: ");

        heroHealth = new JTextField(10);
        heroLuck = new JTextField(10);
        heroAttack = new JTextField(10);
        heroDefence = new JTextField(10);
        heroPotions = new JTextField(10);

        heroLuck.setEditable(false);
        heroHealth.setEditable(false);
        heroAttack.setEditable(false);
        heroDefence.setEditable(false);
        heroPotions.setEditable(false);
    }

    public void ChangeHero(UnitStats unitStats) {
        heroHealth.setText(String.valueOf(unitStats.getMaxHitPoints()));
        heroLuck.setText(String.valueOf(unitStats.getLuck()));
        heroAttack.setText(String.valueOf(unitStats.getAttack()));
        heroDefence.setText(String.valueOf(unitStats.getDefence()));
        heroPotions.setText(String.valueOf(unitStats.getPotions()));
    }

    public void MainMenu(ActionListener actionListener) {
        menuButton.addActionListener(actionListener);
    }

    public void StartGame(ActionListener actionListener) {
        startButton.addActionListener(actionListener);
    }

    public void ChooseClass(ActionListener actionListener) { heroClassSelection.addActionListener(actionListener);}

    public void SwitchViewNewPlayer(ActionListener actionListener) { switchViewNewPlayer.addActionListener(actionListener); }
}
