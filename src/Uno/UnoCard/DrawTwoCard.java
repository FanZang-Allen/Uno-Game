/**
 * This file is part of Uno
 *
 * Class that represent Draw two card in Uno.
 */
package Uno.UnoCard;

import Uno.UnoSetting.GameConstant.*;

public class DrawTwoCard extends Card{
    /**
     * Constructor to set color and type DrawTwo.
     */
    public DrawTwoCard(CardColor color) {
        super(color);
        this.type = CardType.DrawTwo;
    }

    @Override
    public boolean isValidAfter(Card lastCard, int addStack) {
        /* DrawTwo Card is valid if color or type match when add stack is 0 */
        if (addStack == 0 && lastCard.color == this.color) {
            return true;
        }
        /* DrawTwo Card is valid only if type match when add stack is not 0 */
        if (lastCard.type == CardType.DrawTwo) {
            return true;
        }
        return false;
    }

    @Override
    public void printCardInfo() {
        System.out.println(this.getType() + "Card with color " + this.getColor());
    }
}
