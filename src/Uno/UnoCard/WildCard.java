/**
 * This file is part of Uno
 *
 * Class that represent wild card in Uno.
 * Able to change color.
 */
package Uno.UnoCard;

import Uno.UnoSetting.GameConstant.*;

public class WildCard extends BlackCard{
    public WildCard() {
        super();
        this.type = CardType.Wild;
    }

    @Override
    public boolean isValidAfter(Card lastCard, int addStack) {
        /* Wild card is not valid when stack is not 0 */
        if (addStack != 0 && (lastCard.type == CardType.DrawFour || lastCard.type == CardType.DrawTwo)) {
            return false;
        }
        /* Wild card is always valid when stack is 0 */
        return true;
    }

    @Override
    public void printCardInfo() {
        System.out.println(this.getType() + "Card with picked color " + this.getColor());
    }
}
