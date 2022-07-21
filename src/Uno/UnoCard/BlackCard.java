/**
 * This file is part of Uno
 *
 * Class that represent real uno cards with changeable colors.
 * Subclass of abstract Card class.
 *
 */
package Uno.UnoCard;

import static Uno.UnoSetting.GameConstant.*;

public abstract class BlackCard extends Card{
    private int order;

    /**
     * Constructor to set default color and default order for a black card.
     * Color in black card represent the color that player picked during actual game.
     * Order in black card represent the order that player picked during actual game.
     */
    public BlackCard() {
        super(CardColor.Red);
        this.order = CLOCK_WISE_DIRECTION;
    }

    /**
     * Function to set color of card. Used for Uno.Player to pick color.
     */
    public void setColor(CardColor color) {
        this.color = color;
    }

    /**
     * Function to set order of card. Used for Uno.Player to pick order.
     * Custom rule: Reverse on Black implemented here.
     */
    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
