/**
 * This file is part of Uno
 *
 * Strategic AI (also called hard AI in game UI) implement
 * more advanced pick logic than simple AI.
 *
 */
package Uno.UnoPlayer;

import Uno.UnoCard.BlackCard;
import Uno.UnoCard.Card;
import static Uno.UnoSetting.GameConstant.*;

import java.util.*;


public class StrategicAI extends Player{
    /**< List containing all legal black card index for this turn */
    private List<Integer> validCardIndexList;

    /**< Order that maximize AI's advantage */
    private int beneficialOrder;

    /**< Array to keep track number of each color card in hand */
    private int[] colorCountArr;

    /**
     * Initialize the name, list objects.
     * @param id Unique id for this type of player
     * @param index Index of this player in player list in Game class.
     */
    public StrategicAI(int id, int index) {
        super(id, index);
        this.name = "HardAI_" + id;
        validCardIndexList = new ArrayList<Integer>(DECK_MAX_CAPACITY);
        colorCountArr = new int[CardColor.values().length];
        Arrays.fill(colorCountArr,0);
    }

    /**
     * Pick normal card first as long as there is a chance
     * Pick random black cards if AI has more than 1 black card.
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
                if (!(currCard instanceof BlackCard)) {
                    /* Play NonBlack card as long as it is valid */
                    colorCountArr[getColorIndex(currCard.getColor())] -= 1;
                    return i;
                }
                validCardIndexList.add(i);
            }
        }
        if (validCardIndexList.size() == 0) {
            return -1;
        } else {
            /* Pick random black card if more than one card */
            Collections.shuffle(validCardIndexList);
            return validCardIndexList.get(0);
        }
    }

    /**
     * Pick color based on color count array to choose a color that
     * maximize AI's advantage
     * @param playedCard black card that need to set a color
     */
    @Override
    public void pickColor(BlackCard playedCard) {
        CardColor popularColor = null;
        int maxColorCount = -1;
        for (int i = 0; i < CardColor.values().length; i++) {
            if (colorCountArr[i] > maxColorCount) {
                maxColorCount = colorCountArr[i];
                popularColor = CardColor.values()[i];
            }
        }
        playedCard.setColor(popularColor);
    }

    /**
     * Set the black card order to beneficialOrder
     * @param playedCard black card that need to set a order
     */
    @Override
    public void pickOrder(BlackCard playedCard) {
        playedCard.setOrder(beneficialOrder);
    }

    /**
     * Overwrite super class function in order to update color count array.
     * @param inCard Card to be added to hand card list.
     */
    public void drawOneCard(Card inCard) {
        handCard.addOneTopCard(inCard);
        if (!(inCard instanceof BlackCard)) {
            colorCountArr[getColorIndex(inCard.getColor())] += 1;
        }
    }

    /**
     * Used in Game class to get BeneficialOrder of current turn
     * @param leftPlayerCardNum number of hand cards of left player, provided by Game class
     * @param rightPlayerCardNum number of hand cards of right player, provided by Game class
     */
    public void getBeneficialOrder(int leftPlayerCardNum, int rightPlayerCardNum) {
        beneficialOrder = leftPlayerCardNum < rightPlayerCardNum ? -1 : 1;
    }

    private int getColorIndex(CardColor color) {
        int index = 0;
        for (CardColor c: CardColor.values()) {
            if (color == c) {
                return index;
            }
            index++;
        }
        return 0;
    }
}
