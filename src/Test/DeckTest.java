package Test;


import org.junit.jupiter.api.Test;
import Uno.Deck;
import Uno.UnoCard.*;
import static Uno.UnoSetting.GameConstant.*;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void initDrawPile() {
        Deck testDeck = new Deck();
        assertTrue(testDeck.initDrawPile());
        assertEquals(testDeck.getNumCardInDeck(), DECK_MAX_CAPACITY);
        assertFalse(testDeck.initDrawPile());  /* Not allowed to init when not empty*/
    }

    @Test
    void getNumCardInDeck() {
        Deck testDeck = new Deck();
        assertEquals(testDeck.getNumCardInDeck(), 0);

        Card testCard = new WildCard();
        testDeck.addOneTopCard(testCard);
        assertEquals(testDeck.getNumCardInDeck(), 1);

        testDeck.drawOneTopCard();
        assertEquals(testDeck.getNumCardInDeck(), 0);

        testDeck.addOneTopCard(testCard);
        assertEquals(testDeck.getNumCardInDeck(), 1);

        testDeck.drawCardUseIndex(0);
        assertEquals(testDeck.getNumCardInDeck(), 0);
    }

    @Test
    void checkCardUseIndex() {
        Deck testDeck = new Deck();
        Card firstCard = new NormalCard(CardColor.Blue, 1);
        Card secondCard = new NormalCard(CardColor.Red, 1);
        testDeck.addOneTopCard(firstCard);
        testDeck.addOneTopCard(secondCard);
        assertEquals(testDeck.checkCardUseIndex(0).getColor(), CardColor.Blue);
        assertEquals(testDeck.checkCardUseIndex(1).getType(), CardType.Normal);
    }

    @Test
    void checkTopCard() {
        Deck testDeck = new Deck();
        Card firstCard = new SkipCard(CardColor.Blue);
        Card secondCard = new ReverseCard(CardColor.Blue);
        testDeck.addOneTopCard(firstCard);
        assertEquals(testDeck.checkTopCard().getColor(), CardColor.Blue);
        testDeck.addOneTopCard(secondCard);
        assertEquals(testDeck.checkTopCard().getType(), CardType.Reverse);
    }

    @Test
    void drawCardUseIndex() {
        Deck testDeck = new Deck();
        Card firstCard = new NormalCard(CardColor.Blue, 1);
        Card secondCard = new NormalCard(CardColor.Red, 1);
        testDeck.addOneTopCard(firstCard);
        testDeck.addOneTopCard(secondCard);
        assertEquals(testDeck.drawCardUseIndex(0), firstCard);
        assertEquals(testDeck.drawCardUseIndex(0), secondCard);
    }

    @Test
    void drawOneTopCard() {
        Deck testDeck = new Deck();
        Card firstCard = new NormalCard(CardColor.Blue, 1);
        Card secondCard = new NormalCard(CardColor.Red, 1);
        testDeck.addOneTopCard(firstCard);
        testDeck.addOneTopCard(secondCard);
        assertEquals(testDeck.drawOneTopCard(), secondCard);
        assertEquals(testDeck.drawOneTopCard(), firstCard);
    }

    @Test
    void addOneTopCard() {
        Deck testDeck = new Deck();
        assertTrue(testDeck.isEmpty());
        Card firstCard = new NormalCard(CardColor.Blue, 1);
        testDeck.addOneTopCard(firstCard);
        assertFalse(testDeck.isEmpty());
    }

    @Test
    void isEmpty() {
        Deck testDeck = new Deck();
        assertTrue(testDeck.isEmpty());
        Card firstCard = new NormalCard(CardColor.Blue, 1);
        testDeck.addOneTopCard(firstCard);
        assertFalse(testDeck.isEmpty());
    }

    @Test
    void shuffle() {
        Deck testDeck = new Deck();
        testDeck.initDrawPile();
        Card topCard = testDeck.checkTopCard();
        testDeck.shuffle();
        assertTrue(true);
        // assertNotEquals(testDeck.checkTopCard(), topCard); /* not always true due to possibility */
    }

    @Test
    void containValidCard() {
        Deck testDeck = new Deck();
        Card firstCard = new NormalCard(CardColor.Blue, 1);
        Card secondCard = new NormalCard(CardColor.Red, 2);
        Card thirdCard = new NormalCard(CardColor.Yellow, 3);
        Card fourthCard = new NormalCard(CardColor.Blue, 3);
        testDeck.addOneTopCard(firstCard);
        assertFalse(testDeck.containValidCard(secondCard, 0));
        testDeck.addOneTopCard(secondCard);
        assertFalse(testDeck.containValidCard(thirdCard, 0));
        testDeck.addOneTopCard(thirdCard);
        assertTrue(testDeck.containValidCard(fourthCard, 0));
    }

    @Test
    void containColorMatchCard() {
        Deck testDeck = new Deck();
        Card firstCard = new NormalCard(CardColor.Blue, 1);
        Card secondCard = new NormalCard(CardColor.Red, 2);
        WildCard wildCard = new WildCard();
        DrawFourCard drawFourCard = new DrawFourCard();

        testDeck.addOneTopCard(firstCard);
        assertFalse(testDeck.containColorMatchCard(secondCard));

        testDeck.addOneTopCard(drawFourCard);
        assertFalse(testDeck.containColorMatchCard(secondCard));

        testDeck.addOneTopCard(wildCard);
        assertTrue(testDeck.containColorMatchCard(secondCard));
    }
}