/**
 * This file is part of Uno
 *
 * Abstract class for all kinds of cards in Uno.
 * Each card should at least have two properties: CardColor and CardType
 * (See Details in UnoSetting.GameConstant)
 * With function isValidAfter() to check validity of this card during game.
 * With function printCardInfo() for test
 *
 */
package Uno.UnoCard;

import Uno.UnoSetting.GameConstant.*;

public abstract class Card {

    /**
     * Card color. Details in UnoSetting.GameConstant.
     */
    protected CardColor color;

    /**
     * Card type. Details in UnoSetting.GameConstant.
     */
    protected CardType type;

    public CardColor getColor() {
        return color;
    }

    public CardType getType() {
        return type;
    }

    /**
     * Constructor to set card color. Card type is different and set in child class.
     */
    public Card(CardColor color) {
        this.color = color;
    }

    /**
     * Abstract functions to require all kinds of cards to check validity
     * according to last card and addStack (number of cards that players need to draw as punishment)
     * addStack = 0 means that last player has received punishment or not played a punishment card
     */
    /**
     * Abstract functions to require all kinds of cards to check validity
     * according to last card and addStack.
     * addStack = 0 means that last player has received punishment or not played a punishment card
     * @param lastCard Last played card object
     * @param addStack number of cards that players need to draw as punishment
     * @return if this card is a legal card after lastCard
     */
    public abstract boolean isValidAfter(Card lastCard, int addStack);

    /**
     * Print card information for test without GUI.
     */
    public abstract void printCardInfo();

}


