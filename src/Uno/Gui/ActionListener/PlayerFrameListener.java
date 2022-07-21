/**
 * This file is part of Uno
 *
 * Action Listener for Player Frame
 * Interact with NormalPlayer class through buttons in PlayerFrame
 * Define different events after buttons are clicked
 *
 */
package Uno.Gui.ActionListener;

import Uno.Game;
import Uno.Gui.PlayerFrame;
import Uno.UnoPlayer.NormalPlayer;
import Uno.UnoPlayer.Player;
import Uno.UnoSetting.GameConstant.*;
import static Uno.UnoSetting.GuiConstant.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayerFrameListener implements ActionListener {
    /**< Controlled frame */
    private PlayerFrame playerFrame;

    /**< Current running game model */
    private Game game;

    /**< Get from game class */
    private ArrayList<Player> playerList;

    /**
     * Initialize all private variables
     * @param game Game object to get all current game info
     * @param playerFrame current frame showed
     */
    public PlayerFrameListener(Game game, PlayerFrame playerFrame) {
        this.game = game;
        this.playerFrame = playerFrame;
        this.playerList = game.getPlayerList();
    }

    /**
     * Give different hint to players according to processIndex.
     * @param processIndex index stored in normal player class
     */
    public void addHint(int processIndex) {
        switch(processIndex) {
            case 0:
                playerFrame.addHintToPanel(SYSTEM_RESPONSE_HINT, HINT_TEXT_SIZE);
                break;
            case 1:
                playerFrame.addHintToPanel(PICK_CARD_OR_SKIP_HINT,HINT_TEXT_SIZE);
                break;
            case 2:
                playerFrame.addHintToPanel(PICK_COLOR_HINT,HINT_TEXT_SIZE);
                break;
            case 3:
                playerFrame.addHintToPanel(PICK_ORDER_HINT,HINT_TEXT_SIZE);
                break;
            case 4:
                playerFrame.addHintToPanel(DRAW_CARD_HINT,HINT_TEXT_SIZE);
                break;
            default:
                break;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Player player = playerList.get(game.getGameState().getNextPlayerIndex());
        /* Buttons are available only when current player is not AI*/
        if (player instanceof NormalPlayer) {
            NormalPlayer normalPlayer = (NormalPlayer) player;

            /* Hide hand card button */
            if (e.getSource() == playerFrame.getHideHandCardButton()) {
                playerFrame.hideHandCard();
            }

            /* Show hand card button */
            if (e.getSource() == playerFrame.getShowHandCardButton()) {
                playerFrame.showHandCard();
            }

            /* Skip this turn button */
            if (e.getSource() == playerFrame.getSkipButton()) {
                if (normalPlayer.getProcessIndex() == 1) {
                    normalPlayer.setPickedCardIndex(-1);
                    normalPlayer.cardSemaphore.release();
                } else {
                    addHint(normalPlayer.getProcessIndex());
                }
            }

            /* Draw Pile button */
            if (e.getSource() == playerFrame.getDrawPileButton()) {
                if (normalPlayer.getProcessIndex() == 4) {
                    normalPlayer.drawSemaphore.release();
                    normalPlayer.setProcessIndex(0);  /* For test */
                } else {
                    addHint(normalPlayer.getProcessIndex());
                }
            }

            /* All hand card buttons */
            ArrayList<JButton> handCardList = playerFrame.getHandCardButtonList();
            for (int i = 0; i < handCardList.size(); i++) {
                if (e.getSource() == handCardList.get(i)) {
                    if (normalPlayer.getProcessIndex() == 1) {
                        normalPlayer.setPickedCardIndex(i);
                        normalPlayer.cardSemaphore.release();
                    } else {
                        addHint(normalPlayer.getProcessIndex());
                    }
                }
            }

            /* All pick color buttons */
            ArrayList<JButton> colorButtonList = playerFrame.getColorButtonList();
            for (int i = 0; i < colorButtonList.size(); i++) {
                if (e.getSource() == colorButtonList.get(i)) {
                    if (normalPlayer.getProcessIndex() == 2) {
                        switch (i) {
                            case 0:
                                normalPlayer.setPickedColor(CardColor.Red);
                                break;
                            case 1:
                                normalPlayer.setPickedColor(CardColor.Yellow);
                                break;
                            case 2:
                                normalPlayer.setPickedColor(CardColor.Green);
                                break;
                            case 3:
                                normalPlayer.setPickedColor(CardColor.Blue);
                                break;
                            default:
                                break;
                        }
                        playerFrame.highlightButton(colorButtonList.get(i));
                        normalPlayer.colorSemaphore.release();
                    } else {
                        addHint(normalPlayer.getProcessIndex());
                    }
                }
            }

            /* All pick order buttons */
            ArrayList<JButton> orderButtonList = playerFrame.getOrderButtonList();
            for (int i = 0; i < orderButtonList.size(); i++) {
                if (e.getSource() == orderButtonList.get(i)) {
                    if (normalPlayer.getProcessIndex() == 3) {
                        switch (i) {
                            case 0:
                                normalPlayer.setPickedOrder(1);
                                break;
                            case 1:
                                normalPlayer.setPickedOrder(-1);
                                break;
                            default:
                                break;
                        }
                        playerFrame.highlightButton(orderButtonList.get(i));
                        normalPlayer.orderSemaphore.release();
                    } else {
                        addHint(normalPlayer.getProcessIndex());
                    }
                }
            }

        }
    }
}
