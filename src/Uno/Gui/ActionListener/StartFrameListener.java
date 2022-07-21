/**
 * This file is part of Uno
 *
 * Action Listener for Start Frame
 * Interact with user to get player numbers
 * Start the game after start button is clicked
 *
 */
package Uno.Gui.ActionListener;

import Uno.Game;
import Uno.Gui.PlayerFrame;
import Uno.Gui.StartFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Uno.UnoSetting.GameConstant.*;

public class StartFrameListener implements ActionListener {
    /**< Controlled frame */
    private StartFrame startFrame;

    /**< Get from normalPlayerSpinner in startFrame */
    private int normalPlayerCount;

    /**< Get from baselineAISpinner in startFrame */
    private int simpleAICount;

    /**< Get from strategicAISpinner in startFrame */
    private int hardAICount;

    /**< Control the delay time of each process during game(for test) */
    private int timeDelay;

    /**< For test to check if a game has started */
    private Game game;

    public StartFrameListener(StartFrame startFrame) {
        this.startFrame = startFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startFrame.startButton) {
            normalPlayerCount = (Integer) startFrame.normalPlayerSpinner.getValue();
            simpleAICount = (Integer) startFrame.baselineAISpinner.getValue();
            hardAICount = (Integer) startFrame.strategicAISpinner.getValue();
            if ((normalPlayerCount + simpleAICount + hardAICount) > GAME_MAX_PLAYER_COUNT) {
                startFrame.instructionLabel.setText("Total number of players: 2 - 9.");
                startFrame.instructionLabel.setVisible(true);
            } else {
                startPlayerFrame();
            }
        }
    }

    /**
     * Start both game model and player frame for players
     */
    public void startPlayerFrame() {
        Game game = new Game();
        this.game = game;
        game.addPlayer(normalPlayerCount,simpleAICount,hardAICount);
        game.setDelayTime(timeDelay);
        startFrame.dispose();
        PlayerFrame playerFrame = new PlayerFrame(game);
        game.setPlayerFrame(playerFrame);

        /* Thanks for the explanation about java thread from XinYu Qiu (Netid: xingyuq2) */
        new Thread() {
            public void run() {
                game.startGame();
            }
        }.start();
    }

    public void setTimeDelay(int time) {
        this.timeDelay = time;
    }

    public Game getGame() {
        return game;
    }
}
