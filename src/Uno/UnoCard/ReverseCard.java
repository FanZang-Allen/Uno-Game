/**
 * This file is part of Uno
 *
 * Class that represent reverse card in Uno.
 * Able to reverse the game order.
 *
 */
package Uno.UnoCard;

import Uno.UnoSetting.GameConstant.*;

public class ReverseCard extends Card{
    /**
     * Constructor to set color and type Reverse.
     */
    public ReverseCard(CardColor color) {
        super(color);
        this.type = CardType.Reverse;
    }

    @Override
    public boolean isValidAfter(Card lastCard, int addStack) {
        /* Reverse card is not valid when stack is not 0 */
        if (addStack != 0 && (lastCard.type == CardType.DrawFour || lastCard.type == CardType.DrawTwo)) {
            return false;
        }
        /* Reverse card is valid if color match when stack is 0 */
        return lastCard.color == this.color || lastCard.getType() == this.type;
    }

    @Override
    public void printCardInfo() {
        System.out.println(this.getType() + "Card with color " + this.getColor());
    }
}
