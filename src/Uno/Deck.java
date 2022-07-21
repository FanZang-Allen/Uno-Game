/**
 * This file is part of Uno
 *
 * Class that represent a deck of cards in Uno.
 * Such as Draw pile, Discard pile and players' hand card.
 * Use Array List as data structure to store Card objects.
 * (See Details in Uno.UnoCard)
 * Contain operation functions of a deck.
 *
 */
package Uno;

import java.util.ArrayList;
import java.util.Collections;

import Uno.UnoCard.*;
import static Uno.UnoSetting.GameConstant.*;


public class Deck {

    /**< Should be same as pile.size() */
    private int numCardInDeck;

    /**< Array List of Card objects */
    private ArrayList<Card> pile;

    /**
     * Constructor that initialize the arraylist and its size.
     */
    public Deck() {
        pile = new ArrayList<Card>(DECK_MAX_CAPACITY);
        this.numCardInDeck = 0;
    }
    /**
     * Functions that add all required cards to an empty deck.
     */
    public boolean initDrawPile() {
        if (numCardInDeck != 0) {
            return false;
        }
        /* Add Wild Card*/
        for (int i = 0; i < WILD_CARD_NUM; i++) {
            pile.add(new WildCard());
            numCardInDeck++;
        }
        /* Add Draw Four Card*/
        for (int i = 0; i < DRAW_FOUR_CARD_NUM; i++) {
            pile.add(new DrawFourCard());
            numCardInDeck++;
        }
        /* Add Card with Color*/
        for (CardColor color: CardColor.values()) {
            /* Add Normal Card with Number*/
            for (CardNumber numType:CardNumber.values()) {
                for (int i = 0; i < numType.getNumPerColor(); i++) {
                    pile.add(new NormalCard(color,numType.getRepresentNumber()));
                    numCardInDeck++;
                }
            }
            /* Add Skip Card*/
            for (int i = 0; i < SKIP_CARD_PER_COLOR; i++) {
                pile.add(new SkipCard(color));
                numCardInDeck++;
            }
            /* Add Reverse Card*/
            for (int i = 0; i < REVERSE_CARD_PER_COLOR; i++) {
                pile.add(new ReverseCard(color));
                numCardInDeck++;
            }
            /* Add Draw Two Card*/
            for (int i = 0; i < DRAW_TWO_CARD_PER_COLOR; i++) {
                pile.add(new DrawTwoCard(color));
                numCardInDeck++;
            }
        }
        return true;
    }

    /**
     * Functions that return size of deck.
     */
    public int getNumCardInDeck() {
        return numCardInDeck;
    }

    /**
     * Functions that return the information of a card in deck using index.
     */
    public Card checkCardUseIndex(int index) {
        return pile.get(index);
    }

    /**
     * Functions that return the information of the first card in deck.
     */
    public Card checkTopCard() {
        return pile.get(numCardInDeck - 1);
    }

    /**
     * Functions that remove(draw) one card from deck using index
     * @param index index of card
     * @return Card drawn from deck
     */
    public Card drawCardUseIndex(int index) {
        if (index >= numCardInDeck) {
            return null;
        }
        Card outputCard = pile.get(index);
        pile.remove(index);
        numCardInDeck--;
        return outputCard;
    }

    /**
     * Functions that remove(draw) the first card from deck
     */
    public Card drawOneTopCard() {
        return drawCardUseIndex(numCardInDeck - 1);
    }

    /**
     * Functions that put one card on top of the deck.
     * @param inCard Card to be added
     */
    public void addOneTopCard(Card inCard) {
        pile.add(inCard);
        numCardInDeck++;
    }

    /**
     * Functions that tell if deck is empty.
     */
    public boolean isEmpty() {
        return numCardInDeck == 0;
    }

    /**
     * Functions that shuffle all cards in deck.
     */
    public void shuffle() {
        Collections.shuffle(pile);
    }

    /**
     * Functions that iterate the deck to find if there is a valid card to play
     * using last played card and addStack from GameState (See Uno.GameState)
     * @param lastCard Last played card object
     * @param addStack number of cards that players need to draw as punishment
     * @return true if deck contain valid card
     */
    public boolean containValidCard(Card lastCard, int addStack) {
        for (Card c:pile) {
            if (c.isValidAfter(lastCard, addStack)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Functions that iterate the deck to find if there is one card (Except DrawFour Card)
     * has the same color matching last played card.
     * Used to check validity of DrawFour Card. (Bonus point)
     * @param lastCard Last played card object
     * @return true if black card is able to play
     */
    public boolean containColorMatchCard(Card lastCard) {
        for (Card c:pile) {
            if (c.getType() == CardType.DrawFour) {
                continue;
            }
            if (c.getType() == CardType.Wild || c.getColor() == lastCard.getColor()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Card> getPile() {
        return pile;
    }
}
