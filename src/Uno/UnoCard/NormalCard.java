/**
 * This file is part of Uno
 *
 * Class that represent normal card with color and number in Uno.
 */
package Uno.UnoCard;

import Uno.UnoSetting.GameConstant.*;

public class NormalCard extends Card {

    private int number;

    /**
     * Constructor to set default color, type Normal and number.
     */
    public NormalCard(CardColor color, int number) {
        super(color);
        this.number = number;
        this.type = CardType.Normal;
    }

    /**
     * Function to get number of this card.
     */
    public int getNum() {
        return this.number;
    }

    @Override
    public boolean isValidAfter(Card lastCard, int addStack) {
        /* Normal card is not valid when stack is not 0 */
        if (addStack != 0 && (lastCard.type == CardType.DrawFour || lastCard.type == CardType.DrawTwo)) {
            return false;
        }
        /* Normal card is valid if color or number match when stack is 0 */
        if (lastCard.color == this.color) {
            return true;
        }
        if (lastCard.type == CardType.Normal) {
            NormalCard downcast_normal = (NormalCard) lastCard;
            return downcast_normal.getNum() == this.number;
        }
        return false;
    }

    @Override
    public void printCardInfo() {
        System.out.println(this.getType() + "Card with color " + this.getColor() + "and number " + this.getNum());
    }
}
