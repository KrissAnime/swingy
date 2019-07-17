package wethinkcode.com.swingy.Controller;

import wethinkcode.com.swingy.View.console.ConsoleGameView;
import wethinkcode.com.swingy.View.gui.GuiGameView;

public abstract class GameOutputMessage {
    public GameOutputMessage(){}

    public static void OutputToScreen(String OutputMessage, String screenDisplay, GuiGameView guiGameView, ConsoleGameView consoleGameView) {
        if (screenDisplay.equals("gui")) {
            guiGameView.getGuiGameOutput().UpdateScreen(OutputMessage);
        } else if (screenDisplay.equals("console")) {
            System.out.println(OutputMessage);
        }
    }
}
