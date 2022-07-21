/**
 * This file is part of Uno
 *
 * Class to store all game state information.
 * Including play order, stacked punishment, index of next player...
 * Contains Draw pile and Discard pile. (See Uno.Deck)
 * Used in Game Class (See Uno.Game) and updated after each turn.
 * Player cannot directly access this class.
 *
 */
package Uno;

import java.util.Random;
import Uno.UnoCard.Card;
import Uno.UnoCard.DrawFourCard;
import Uno.UnoCard.WildCard;

import static Uno.UnoSetting.GameConstant.*;


public class GameState {

    /**< Order of card play */
    private int order;

    /**< Stacked punishment  */
    private int addStack;

    /**< Total number of players */
    private int playerCount;

    /**< Index of next player in player arraylist */
    private int playerIndex;

    /**< Last played card */
    private Card lastCard;

    /**< Deck object where players get cards from */
    private Deck drawPile;

    /**< Deck object containing played card */
    private Deck discardPile;

    /**< Help generate random start order and player */
    private Random rand;

    /**
     * Constructor that initialize number of players and game state.
     * @param playerCount total number of all types of players
     */
    public GameState(int playerCount) {
        this.playerCount = playerCount;
        startNewGameState();
    }

    /**
     * Actual functions that initialize all information based on number of players.
     * Get the start order, start player, draw pile, discard pile, and first played card.
     */
    public void startNewGameState() {
        rand = new Random();
        drawPile = new Deck();
        discardPile = new Deck();
        setRandOrder();
        setRandStartPlayer();
        drawPile.initDrawPile();
        drawPile.shuffle();
        while (drawPile.checkTopCard().getType() != CardType.Normal) {
            drawPile.shuffle();
        }
        /*Set initial discard normal card*/
        lastCard = drawPile.drawOneTopCard();
    }

    /**
     * Functions that set a random play order.
     */
    public void setRandOrder() {
        int intRandom = rand.nextInt(2);
        if (intRandom == 1) {
            order = CLOCK_WISE_DIRECTION;
        } else {
            order = COUNTER_CLOCK_WISE_DIRECTION;
        }
    }

    /**
     * Functions that set a random start player.
     */
    public void setRandStartPlayer() {
        playerIndex = rand.nextInt(playerCount);
    }

    /**
     * Follow the current playing order to get next player index.
     */
    public void moveToNextPlayer() {
        playerIndex = (playerIndex + order) % playerCount;
        if (playerIndex == -1) {
            playerIndex = playerCount - 1;
        }
    }

    /**
     * Clear stacked punishment and move to next player. Used after a player skip his turn.
     */
    public void continueAndClearStack() {
        addStack = 0;
        moveToNextPlayer();
    }

    /**
     * Update game state according to played card from players.
     * Used in Game class to avoid players directly change the game state.
     * @param playedCard Played card of this turn
     */
    public void updateState(Card playedCard) {
        /* Reuse discard pile if needed */
        reuseDiscardPile();

        switch(playedCard.getType()) {
            case Reverse:
                order *= -1;
                break;
            case Wild:
                WildCard downcastWildCard = (WildCard) playedCard;
                order = downcastWildCard.getOrder();
                break;
            case DrawFour:
                DrawFourCard downcastDrawFourCard = (DrawFourCard) playedCard;
                order = downcastDrawFourCard.getOrder();
                addStack += 4;
                break;
            case DrawTwo:
                addStack += 2;
                break;
            case Skip:
                moveToNextPlayer();
                break;
            default:
                break;
        }
        moveToNextPlayer();
        /* Handle Color & Number Change */
        discardPile.addOneTopCard(lastCard);
        lastCard = playedCard;
    }

    /**
     * Function to get the first card of draw pile. Use when addStack is 0 and players have no
     * valid cards to play. Player needs to draw one card and play it if it is valid.
     */
    public Card getTopDrawPileCard() {
        reuseDiscardPile();
        return drawPile.drawOneTopCard();
    }

    /**
     * Functions to reuse the discard pile when draw pile is empty.
     * Shuffle the discard pile is included.
     */
    public void reuseDiscardPile() {
        if (drawPile.isEmpty()) {
            Deck tmpPile = drawPile;
            discardPile.shuffle();
            drawPile = discardPile;
            discardPile = tmpPile;
        }
    }

    public Card getLastCard() {
        return lastCard;
    }

    public int getNextPlayerIndex() {
        return playerIndex;
    }

    public int getAddStack() {
        return addStack;
    }

    public Deck getDrawPile() {
        return drawPile;
    }

    public int getOrder() {
        return order;
    }

    public void setPlayerIndex(int i) {
        playerIndex = i;
    }
}
