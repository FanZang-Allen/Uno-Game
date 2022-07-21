/**
 * This file is part of Uno
 *
 * Class that represent Draw four card in Uno.
 */
package Uno.UnoCard;

import static Uno.UnoSetting.GameConstant.*;

public class DrawFourCard extends BlackCard{
    public DrawFourCard() {
        super();
        this.type = CardType.DrawFour;
    }

    @Override
    public boolean isValidAfter(Card lastCard, int addStack) {
        if (addStack != 0) {
            //Black is King rule added.
            return lastCard.getType() != CardType.DrawFour;
        }
        return true;/* No card restriction */
    }

    @Override
    public void printCardInfo() {
        System.out.println(this.getType() + "Card with picked color " + this.getColor());
    }
}
