/**
 * This file is part of Uno
 *
 * Normal Player (also called human player).
 * Pick functions use semaphore to control the stop & continuity of game progress.
 * Semaphore are released in PlayerFrameListener Class when user click corresponding buttons
 *
 */
package Uno.UnoPlayer;

import Uno.UnoCard.BlackCard;
import Uno.UnoCard.Card;
import Uno.UnoSetting.GameConstant.*;

import java.util.concurrent.Semaphore;

public class NormalPlayer extends Player{
    /**< Control the pick card function */
    public Semaphore cardSemaphore;

    /**< Control the pick color function */
    public Semaphore colorSemaphore;

    /**< Control the pick order function */
    public Semaphore orderSemaphore;

    /**< Control the drawOneCard function */
    public Semaphore drawSemaphore;

    /**< Set when any valid hand card button is clicked */
    private int pickedCardIndex;

    /**< Set when any color button is clicked */
    private CardColor pickedColor;

    /**< Set when any order button is clicked */
    private int pickedOrder;

    /**< Help indicate which process this player is in */
    private int processIndex;

    /**
     * Initialize all semaphores and variables.
     * @param id Unique id for this type of player
     * @param index Index of this player in player list in Game class.
     */
    public NormalPlayer(int id, int index) {
        super(id, index);
        this.name = "NormalPlayer_" + id;
        cardSemaphore = new Semaphore(0);
        colorSemaphore = new Semaphore(0);
        orderSemaphore = new Semaphore(0);
        drawSemaphore = new Semaphore(0);
        processIndex = 0;
    }

    /**
     * Function that is called after player pick a card (also color & order if required).
     * Draw this card from hand card and give it back to Game class.
     * Overwrite the same function in Player function since all pick process are
     * called separately in Game class.
     * @param lastCard Last played card object
     * @param addStack number of cards that players need to draw as punishment
     * @return Played card object, null if player choose to skip.
     */
    @Override
    public Card getPlayedCard(Card lastCard, int addStack) {
        if (pickedCardIndex == -1) {
            return null;
        }
        Card playedCard = handCard.drawCardUseIndex(pickedCardIndex);
        return playedCard;
    }

    /**
     * Process index is 1 => player needs to click a card button or skip button right now.
     * pickedCardIndex is set by PlayerFrameListener.
     * @param lastCard Last played card object
     * @param addStack number of cards that players need to draw as punishment
     * @return index of card to played in hand card list
     */
    @Override
    public int pickCard(Card lastCard, int addStack){
        processIndex = 1;
        /* Semaphore is release in PlayerFrameListener after user click a hand card button or skip button */
        try{
            cardSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        processIndex = 0;
        return pickedCardIndex;
    }

    /**
     * Process index is 2 => player needs to click a color button right now.
     * pickedColor is set by PlayerFrameListener.
     * @param playedCard black card that need to set a color
     */
    @Override
    public void pickColor(BlackCard playedCard) {
        processIndex = 2;
        /* Semaphore is release in PlayerFrameListener after user click a color button */
        try{
            colorSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playedCard.setColor(pickedColor);
        processIndex = 0;
    }

    /**
     * Process index is 3 => player needs to click an order button right now.
     * pickedOrder is set by PlayerFrameListener.
     * @param playedCard black card that need to set a order
     */
    @Override
    public void pickOrder(BlackCard playedCard) {
        processIndex = 3;
        /* Semaphore is release in PlayerFrameListener after user click an order button */
        try{
            orderSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playedCard.setOrder(pickedOrder);
        processIndex = 0;
    }

    /**
     * Process index is 4 => player needs to click draw pile button right now.
     */
    public void drawOneCard() {
        processIndex = 4;
        /* Semaphore is release in PlayerFrameListener after user click the draw pile button */
        try{
            drawSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        processIndex = 0;
    }

    public void setPickedCardIndex(int index) {
        pickedCardIndex = index;
    }

    public void setPickedColor(CardColor color) {
        pickedColor = color;
    }

    public void setPickedOrder(int order) {
        pickedOrder = order;
    }

    public int getPickedCardIndex() {
        return pickedCardIndex;
    }

    public int getPickedOrder() {
        return pickedOrder;
    }

    public CardColor getPickedColor() {
        return pickedColor;
    }

    public int getProcessIndex() {
        return processIndex;
    }

    public void setProcessIndex(int i) {
        processIndex = i;
    }
}
