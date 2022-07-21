package Test;

import Uno.GameState;
import static Uno.UnoSetting.GameConstant.*;

import Uno.UnoCard.Card;
import Uno.UnoCard.DrawFourCard;
import Uno.UnoCard.DrawTwoCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStateTest {

    @Test
    void startNewGameState() {
        GameState testGameState = new GameState(3);

        /* Test size of initial draw pile before dealing card to players */
        assertEquals(testGameState.getDrawPile().getNumCardInDeck(), DECK_MAX_CAPACITY - 1);

        /* Test type of the first discard card */
        assertEquals(testGameState.getLastCard().getType(), CardType.Normal);

        /* Test initial addStack */
        assertEquals(testGameState.getAddStack(), 0);

        /* Test initial player index */
        assertTrue(testGameState.getNextPlayerIndex() < 3);
        assertTrue(testGameState.getNextPlayerIndex() >= 0);
    }

    @Test
    void continueAndClearStack() {
        GameState testGameState = new GameState(3);
        Card testCard = new DrawFourCard();

        testGameState.updateState(testCard);
        assertEquals(testGameState.getAddStack(), 4);

        int playerIndex = testGameState.getNextPlayerIndex();
        int order = testGameState.getOrder();

        /* Test clear addStack part */
        testGameState.continueAndClearStack();
        assertEquals(testGameState.getAddStack(), 0);

        int expectedNextIndex = (playerIndex + order) % 3;
        if (expectedNextIndex == -1) {
            expectedNextIndex = 0;
        }

        /* Test move index forward part */
        assertEquals(testGameState.getNextPlayerIndex(), expectedNextIndex);

    }

    @Test
    void updateState() {
        GameState testGameState = new GameState(3);
        int currPlayerIndex = testGameState.getNextPlayerIndex();
        int currAddStack = testGameState.getAddStack();

        DrawFourCard testCard = new DrawFourCard();
        testGameState.updateState(testCard);

        int currOrder = testGameState.getOrder();
        /* Test Order change */
        assertEquals(testCard.getOrder(), currOrder);

        /* Test player index change */
        int expectedNextIndex = (currPlayerIndex + currOrder) % 3;
        if (expectedNextIndex == -1) {
            expectedNextIndex = 0;
        }
        assertEquals(testGameState.getNextPlayerIndex(), expectedNextIndex);

        /* Test addStack change */
        assertEquals(testGameState.getAddStack(), currAddStack + 4);

        /* Test last card change */
        assertEquals(testGameState.getLastCard(), testCard);
    }


    @Test
    void reuseDiscardPile() {
        GameState testGameState = new GameState(3);
        for (int i = 0; i < DECK_MAX_CAPACITY - 1; i++) {
            /* Draw all the cards from draw pile*/
            testGameState.getDrawPile().drawOneTopCard();
        }
        assertEquals(testGameState.getDrawPile().getNumCardInDeck(), 0);

        Card testCard = new DrawFourCard();
        testGameState.updateState(testCard);
        testGameState.reuseDiscardPile();
        assertEquals(testGameState.getDrawPile().getNumCardInDeck(), 1);

    }
}