package wethinkcode.com.swingy.View.gui;

import lombok.Getter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GuiPlayerSelectView extends JPanel {
    private @Getter JList playerList;
    private JButton newGame;
    private @Getter JButton continueGame;
    private JButton switchViewMainMenu;

    public GuiPlayerSelectView(ArrayList playerNames){
        newGame = new JButton("New Game");
        continueGame = new JButton("Continue Game");
        switchViewMainMenu = new JButton("Switch View");
        continueGame.setEnabled(false);
        playerList = new JList();

        DefaultListModel playerModal = new DefaultListModel();
        for (int index = 0; index < playerNames.size(); index++) {
            playerModal.addElement(playerNames.get(index));
        }

        playerList.setModel(playerModal);

        playerList.setPreferredSize(new Dimension(300, 300));

        Border innerBorder = BorderFactory.createTitledBorder("Select Player");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        playerList.setVisibleRowCount(10);
        add(new JScrollPane(playerList), gridBagConstraints);

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(newGame, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(continueGame, gridBagConstraints);

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(switchViewMainMenu, gridBagConstraints);

    }

    public void SelectPlayer(MouseListener mouseListener) {
        playerList.addMouseListener(mouseListener);

    }

    public void NewGame(ActionListener actionListener) {
        newGame.addActionListener(actionListener);
    }

    public void ContinueGame(ActionListener actionListener) {
        continueGame.addActionListener(actionListener);
    }

    public void SwitchMainMenu(ActionListener actionListener) { switchViewMainMenu.addActionListener(actionListener); }
}
