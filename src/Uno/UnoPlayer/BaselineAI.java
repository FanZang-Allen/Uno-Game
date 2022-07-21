/**
 * This file is part of Uno
 *
 * Baseline AI (also called simple AI in game UI) implement
 * only basic strategy of picking cards, color and orders.
 *
 */
package Uno.UnoPlayer;

import Uno.UnoCard.BlackCard;
import Uno.UnoCard.Card;
import static Uno.UnoSetting.GameConstant.*;

import java.util.*;

public class BaselineAI extends Player{
    /**< List containing all legal card index for this turn */
    private List<Integer> validCardIndexList;

    /**< Random object helps all random generation function  */
    private Random rand;

    /**
     * Initialize the name, list and rand objects.
     * @param id Unique id for this type of player
     * @param index Index of this player in player list in Game class.
     */
    public BaselineAI(int id, int index) {
        super(id, index);
        this.name = "SimpleAI_" + id;
        validCardIndexList = new ArrayList<Integer>(DECK_MAX_CAPACITY);
        rand = new Random();
    }

    /**
     * Pick random legal cards from valid card list.
     * @param lastCard Last played card object
     * @param addStack number of cards that players need to draw as punishment
     * @return index of card to played in hand card list
     */
    @Override
    public int pickCard(Card lastCard, int addStack) {
        Card currCard = null;
        validCardIndexList.clear();
        for (int i = 0; i < handCard.getNumCardInDeck(); i++) {
            currCard = handCard.checkCardUseIndex(i);
            if (currCard.isValidAfter(lastCard,addStack)) {
                if (currCard.getType() == CardType.DrawFour && handCard.containColorMatchCard(lastCard)) {
                    /* drawFour card restriction*/
                    continue;
                }
                validCardIndexList.add(i);
            }
        }
        if (validCardIndexList.size()==0) {
            return -1;
        } else {
            /* Pick random card if more than one legal cards */
            Collections.shuffle(validCardIndexList);
            return validCardIndexList.get(0);
        }

    }

    /**
     * Pick random color if black card is picked.
     * @param playedCard black card that need to set a color
     */
    @Override
    public void pickColor(BlackCard playedCard) {
        int colorIndex = rand.nextInt(4);
        CardColor pickedColor = CardColor.values()[colorIndex];
        playedCard.setColor(pickedColor);
    }

    /**
     * Pick random order if black card is picked.
     * @param playedCard black card that need to set a order
     */
    @Override
    public void pickOrder(BlackCard playedCard) {
        int randomOrder = rand.nextInt(1) == 0 ? 1 : -1;
        playedCard.setOrder(randomOrder);
    }
}
