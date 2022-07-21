/**
 * This file is part of Uno
 *
 * Class that represent skip card in Uno.
 * Able to skip next player's turn.
 */
package Uno.UnoCard;

import Uno.UnoSetting.GameConstant.*;

public class SkipCard extends Card {
    /**
     * Constructor to set color and type Skip.
     */
    public SkipCard(CardColor color) {
        super(color);
        this.type = CardType.Skip;
    }

    @Override
    public boolean isValidAfter(Card lastCard, int addStack) {
        /* Skip card is not valid when stack is not 0 */
        if (addStack != 0 && (lastCard.type == CardType.DrawFour || lastCard.type == CardType.DrawTwo)) {
            return false;
        }
        /* Skip card is valid if color match when stack is 0 */
        return lastCard.color == this.color || lastCard.getType() == this.type;
    }

    @Override
    public void printCardInfo() {
        System.out.println(this.getType() + "Card with color " + this.getColor());
    }
}
