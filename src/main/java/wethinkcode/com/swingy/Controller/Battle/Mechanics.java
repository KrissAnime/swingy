package wethinkcode.com.swingy.Controller.Battle;

import wethinkcode.com.swingy.Controller.GameOutputMessage;
import wethinkcode.com.swingy.Model.Units.GameCharacters;
import wethinkcode.com.swingy.View.console.ConsoleGameView;
import wethinkcode.com.swingy.View.gui.GuiGameView;

import java.util.concurrent.ThreadLocalRandom;

public class Mechanics {
    public Mechanics() {}

    public String DeathBattle(GameCharacters enemy, GameCharacters hero, boolean attemptToFlee, GuiGameView guiGameView, ConsoleGameView consoleGameView, String screenDisplay){
        try {
            if (attemptToFlee) {
                if (FleeBattle()) {
                    return "You managed to escape battle!";
                }
                GameOutputMessage.OutputToScreen("You couldn't escape, now the enemy has initiative!", screenDisplay, guiGameView, consoleGameView);
            } else {
                GameOutputMessage.OutputToScreen("You decided not to run, you have the initiative!", screenDisplay, guiGameView, consoleGameView);
            }
            int heroTurn = -1;
            if (!attemptToFlee) {
                heroTurn = 1;
            }
            while (enemy.getUnitStats().getHitPoints() >= 0 && hero.getUnitStats().getHitPoints() >= 0) {
                int enemyStrategy = Strategies(2);
                int heroStrategy = Strategies(2);
                switch (heroTurn) {
                    case 1:
                        switch (enemyStrategy) {
                            case 3:
                                if (MissAttack(enemy.getUnitStats().getLuck())) {
                                    Attack(hero, enemy, guiGameView, consoleGameView, screenDisplay);
                                } else {
                                    GameOutputMessage.OutputToScreen(enemy.getName() + " missed their attack!", screenDisplay, guiGameView, consoleGameView);
                                }
                                break;
                            case 1:
                                Attack(hero, enemy, guiGameView, consoleGameView, screenDisplay);
                                break;
                            case 2:
                                Defend(enemy, hero, guiGameView, consoleGameView, screenDisplay);
                                if (enemy.getUnitStats().getHitPoints() < enemy.getUnitStats().getMaxHitPoints() / 3) {
                                    UsePotion(enemy, guiGameView, consoleGameView, screenDisplay);
                                }
                                break;
                        }
                        break;
                    case -1:
                        switch (heroStrategy) {
                            case 3:
                                if (MissAttack(hero.getUnitStats().getLuck())) {
                                    Attack(enemy, hero, guiGameView, consoleGameView, screenDisplay);
                                    break;
                                } else {
                                    GameOutputMessage.OutputToScreen(hero.getName() + " missed their attack!", screenDisplay, guiGameView, consoleGameView);
                                }
                            case 1:
                                Attack(enemy, hero, guiGameView, consoleGameView, screenDisplay);
                                break;
                            case 2:
                                Defend(hero, enemy, guiGameView, consoleGameView, screenDisplay);
                                if (hero.getUnitStats().getHitPoints() < hero.getUnitStats().getMaxHitPoints() / 2) {
                                    UsePotion(hero, guiGameView, consoleGameView, screenDisplay);
                                }
                                break;
                        }
                }
                heroTurn *= -1;
            }
            if (hero.getUnitStats().getHitPoints() <= 0) {
                return "The hero " + hero.getName() + " has been defeated";
            } else {
                GameOutputMessage.OutputToScreen("The hero " + hero.getName() + " has won the battle!", screenDisplay, guiGameView, consoleGameView);
                GameOutputMessage.OutputToScreen("Gained " + enemy.getUnitStats().getExperience() + "XP!", screenDisplay, guiGameView, consoleGameView);
                RecoverHitPoints(hero);
                if (hero.expGained(enemy.getUnitStats().getExperience())) {
                    GameOutputMessage.OutputToScreen("You have gained enough XP to level UP!", screenDisplay, guiGameView, consoleGameView);
                }
                return "won";
            }
        } catch (Exception e) {
            System.out.println("Your battle was interrupted");
            e.printStackTrace();
            return null;
        }
    }

    private void UsePotion(GameCharacters gameCharacters, GuiGameView guiGameView, ConsoleGameView consoleGameView, String screenDisplay) {
        if (gameCharacters.getUnitStats().getPotions() >= 1) {
            if (gameCharacters.getUnitStats().getHitPoints() + (gameCharacters.getUnitStats().getMaxHitPoints() * 0.2) >= gameCharacters.getUnitStats().getMaxHitPoints()) {
                gameCharacters.getUnitStats().setHitPoints(gameCharacters.getUnitStats().getMaxHitPoints());
            } else {
                gameCharacters.getUnitStats().setHitPoints(gameCharacters.getUnitStats().getHitPoints() + (int)(gameCharacters.getUnitStats().getMaxHitPoints() * 0.2));
            }
            gameCharacters.getUnitStats().setPotions(gameCharacters.getUnitStats().getPotions() - 1);
            GameOutputMessage.OutputToScreen(gameCharacters.getName() + " used a health potion to recover 20%HP!", screenDisplay, guiGameView, consoleGameView);
        }
        GameOutputMessage.OutputToScreen(gameCharacters.getName() + " ran out of potions to use!", screenDisplay, guiGameView, consoleGameView);
    }

    private boolean MissAttack(double luck){
        double hitProbability = ThreadLocalRandom.current().nextInt(0, 100) + luck;
        return hitProbability >= 35;
    }

    private void Attack(GameCharacters target, GameCharacters attacker, GuiGameView guiGameView, ConsoleGameView consoleGameView, String screenDisplay) {
        GameOutputMessage.OutputToScreen(attacker.getName() + " decided to attack!", screenDisplay, guiGameView, consoleGameView);
        if ((target.getUnitStats().getDefence() - attacker.getUnitStats().getAttack()) > 10) {
            target.getUnitStats().setHitPoints(target.getUnitStats().getHitPoints() - (attacker.getUnitStats().getAttack() - target.getUnitStats().getDefence()));
            GameOutputMessage.OutputToScreen(target.getName() + " took " + (attacker.getUnitStats().getAttack() - target.getUnitStats().getDefence()) + " damage!", screenDisplay, guiGameView, consoleGameView);
        } else {
            target.getUnitStats().setHitPoints(target.getUnitStats().getHitPoints() - 10);
            GameOutputMessage.OutputToScreen(target.getName() + " took 10 damage!", screenDisplay, guiGameView, consoleGameView);
        }
    }

    private void Defend(GameCharacters defender, GameCharacters attacker, GuiGameView guiGameView, ConsoleGameView consoleGameView, String screenDisplay) {
        GameOutputMessage.OutputToScreen(defender.getName() + " decided to defend!", screenDisplay, guiGameView, consoleGameView);
        if ((attacker.getUnitStats().getAttack() - (defender.getUnitStats().getDefence() * 1.15)) > 10) {
            defender.getUnitStats().setHitPoints((int)(defender.getUnitStats().getHitPoints() - (attacker.getUnitStats().getAttack() - (defender.getUnitStats().getDefence() * 1.15))));
            GameOutputMessage.OutputToScreen(defender.getName() + " took " + (int)(attacker.getUnitStats().getAttack() - (defender.getUnitStats().getDefence() * 1.15)) + " damage!", screenDisplay, guiGameView, consoleGameView);
        } else {
            defender.getUnitStats().setHitPoints(defender.getUnitStats().getHitPoints() - 10);
            GameOutputMessage.OutputToScreen(defender.getName() + " took 10 damage!", screenDisplay, guiGameView, consoleGameView);
        }
    }

    private void RecoverHitPoints(GameCharacters hero){
        hero.getUnitStats().setHitPoints(hero.getUnitStats ().getHitPoints() + (int)(hero.getUnitStats().getMaxHitPoints() * 0.4));
        if (hero.getUnitStats().getHitPoints() >= hero.getUnitStats().getMaxHitPoints()) {
            hero.getUnitStats().setHitPoints(hero.getUnitStats().getMaxHitPoints());
        }
    }

    private boolean FleeBattle() {
        return Strategies(0) == 1;
    }

    private int Strategies(int numberOfStrategies){
        return ThreadLocalRandom.current().nextInt(0, numberOfStrategies + 1);
    }
}
