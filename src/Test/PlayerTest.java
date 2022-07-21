package Test;

import Uno.UnoPlayer.BaselineAI;
import Uno.UnoPlayer.NormalPlayer;
import Uno.UnoPlayer.Player;
import Uno.UnoCard.*;
import Uno.UnoPlayer.StrategicAI;
import Uno.UnoSetting.GameConstant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    /* Common functions in player class */
    @Test
    void drawOneCard() {
        Player testPlayer = new BaselineAI(1, 0);
        Card testCard = new DrawFourCard();
        assertEquals(testPlayer.getHandCard().getNumCardInDeck(), 0);
        testPlayer.drawOneCard(testCard);
        assertEquals(testPlayer.getHandCard().getNumCardInDeck(), 1);
    }


    @Test
    void hasValidCard() {
        Player testPlayer = new BaselineAI(1, 0);
        DrawTwoCard drawTwoCard = new DrawTwoCard(GameConstant.CardColor.Red);
        NormalCard normalCard = new NormalCard(GameConstant.CardColor.Red, 1);
        ReverseCard reverseCard = new ReverseCard(GameConstant.CardColor.Blue);

        testPlayer.drawOneCard(reverseCard);
        assertEquals(testPlayer.hasValidCard(normalCard, 0),false);

        testPlayer.drawOneCard(drawTwoCard);
        assertEquals(testPlayer.hasValidCard(normalCard, 0),true);
    }

    /* Baseline AI test part  */
    @Test
    void baselineAIPickCardTest() {
        Player testPlayer = new BaselineAI(1, 0);
        DrawTwoCard drawTwoCard = new DrawTwoCard(GameConstant.CardColor.Red);
        NormalCard normalCard = new NormalCard(GameConstant.CardColor.Red, 1);
        ReverseCard reverseCard = new ReverseCard(GameConstant.CardColor.Red);
        SkipCard skipCard = new SkipCard(GameConstant.CardColor.Blue);

        testPlayer.drawOneCard(drawTwoCard);
        testPlayer.drawOneCard(reverseCard);
        testPlayer.drawOneCard(skipCard);

        /* AI should not pick invalid card */
        assertNotEquals(testPlayer.getHandCard().getPile().get(testPlayer.pickCard(normalCard,0)), skipCard);
    }

    @Test
    void baselineAIPickColorTest() {
        /* Base AI pick color randomly */
        Player testPlayer = new BaselineAI(1, 0);
        WildCard wildCard = new WildCard();
        testPlayer.pickColor(wildCard); /* Cannot use assertion to test random function*/
    }

    @Test
    void baselineAIPickOrderTest() {
        /* Base AI pick order randomly */
        Player testPlayer = new BaselineAI(1, 0);
        WildCard wildCard = new WildCard();
        testPlayer.pickOrder(wildCard); /* Cannot use assertion to test random function*/
    }

    /* Strategic AI test part  */
    @Test
    void strategicAIPickCardTest() {
        Player testPlayer = new StrategicAI(1, 0);
        NormalCard normalCard = new NormalCard(GameConstant.CardColor.Red, 1);
        ReverseCard reverseCard = new ReverseCard(GameConstant.CardColor.Red);
        SkipCard skipCard = new SkipCard(GameConstant.CardColor.Blue);
        WildCard wildCard = new WildCard();

        testPlayer.drawOneCard(wildCard);
        testPlayer.drawOneCard(reverseCard);
        testPlayer.drawOneCard(skipCard);

        /* AI should only pick the reverseCard */
        assertEquals(testPlayer.getHandCard().getPile().get(testPlayer.pickCard(normalCard,0)), reverseCard);

        /* AI should not pick black card if it has a not black card that is valid */
        assertNotEquals(testPlayer.getHandCard().getPile().get(testPlayer.pickCard(normalCard,0)), wildCard);
    }

    @Test
    void strategicAIPickColorTest() {
        /* Base AI pick color randomly */
        Player testPlayer = new StrategicAI(1, 0);
        DrawTwoCard drawTwoCard = new DrawTwoCard(GameConstant.CardColor.Red);
        SkipCard skipCard = new SkipCard(GameConstant.CardColor.Blue);
        ReverseCard reverseCard = new ReverseCard(GameConstant.CardColor.Red);
        WildCard wildCard = new WildCard();

        testPlayer.drawOneCard(drawTwoCard);
        testPlayer.drawOneCard(wildCard);
        testPlayer.drawOneCard(reverseCard);
        testPlayer.drawOneCard(skipCard);

        /* AI should pick red color since it has more red card than blue card*/
        testPlayer.pickColor(wildCard);
        assertEquals(GameConstant.CardColor.Red, wildCard.getColor());
    }

    @Test
    void strategicAIPickOrderTest() {
        /* Base AI pick order randomly */
        StrategicAI testPlayer = new StrategicAI(1, 0);
        WildCard wildCard = new WildCard();

        /* AI should pick anticlockwise order since left player has fewer cards */
        testPlayer.getBeneficialOrder(5, 6);
        testPlayer.pickOrder(wildCard);
        assertEquals(-1, wildCard.getOrder());

        /* AI should pick clockwise order since right player has fewer cards */
        testPlayer.getBeneficialOrder(6, 5);
        testPlayer.pickOrder(wildCard);
        assertEquals(1, wildCard.getOrder());
    }

    /* Normal player test are related to action listener. See PlayerFrameListenerTest */


}