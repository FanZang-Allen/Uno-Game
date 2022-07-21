package Test;

import Uno.UnoCard.*;
import Uno.UnoSetting.GameConstant.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AllCardTest {

    @Test
    void getColor() {
        NormalCard normalCard = new NormalCard(CardColor.Blue, 1);
        assertEquals(normalCard.getColor(), CardColor.Blue);
    }

    @Test
    void getType() {
        NormalCard normalCard = new NormalCard(CardColor.Blue, 1);
        assertEquals(normalCard.getType(), CardType.Normal);
    }

    @Test
    void drawFourCardValidTest() {
        DrawFourCard drawFourCard = new DrawFourCard();
        DrawTwoCard drawTwoCard = new DrawTwoCard(CardColor.Red);
        NormalCard normalCard = new NormalCard(CardColor.Red, 1);
        ReverseCard reverseCard = new ReverseCard(CardColor.Red);

        int addStack = 0;
        /* DrawFour Card is valid all the time when stack is 0 */
        assertTrue(drawFourCard.isValidAfter(drawFourCard, addStack));
        assertTrue(drawFourCard.isValidAfter(drawTwoCard, addStack));
        assertTrue(drawFourCard.isValidAfter(normalCard, addStack));
        assertTrue(drawFourCard.isValidAfter(reverseCard, addStack));

        /* add stack is not 0 only if last card is DrawFour or DrawTwo card*/
        addStack = 1;

        /* DrawFour Card is valid if last card is not draw four when stack is not 0 */
        assertFalse(drawFourCard.isValidAfter(drawFourCard, addStack));
        assertTrue(drawFourCard.isValidAfter(drawTwoCard, addStack));
    }

    @Test
    void drawTwoCardValidTest() {
        DrawFourCard drawFourCard = new DrawFourCard();
        DrawTwoCard drawTwoCard = new DrawTwoCard(CardColor.Red);
        DrawTwoCard secondDrawTwoCard = new DrawTwoCard(CardColor.Blue);

        int addStack = 0;

        /* DrawTwo Card is valid if color or type match when add stack is 0 */
        drawFourCard.setColor(CardColor.Red);
        assertTrue(drawTwoCard.isValidAfter(drawFourCard, addStack));
        drawFourCard.setColor(CardColor.Blue);
        assertFalse(drawTwoCard.isValidAfter(drawFourCard, addStack));
        assertTrue(drawTwoCard.isValidAfter(secondDrawTwoCard, addStack));

        addStack = 1;

        /* DrawTwo Card is valid only if type match when add stack is not 0 */
        assertFalse(drawTwoCard.isValidAfter(drawFourCard, addStack)); /* BLACK is KING test here*/
        assertTrue(drawTwoCard.isValidAfter(secondDrawTwoCard, addStack));
    }

    @Test
    void normalCardValidTest() {
        DrawFourCard drawFourCard = new DrawFourCard();
        DrawTwoCard drawTwoCard = new DrawTwoCard(CardColor.Red);
        NormalCard normalCard = new NormalCard(CardColor.Red, 1);
        NormalCard secondNormalCard = new NormalCard(CardColor.Blue, 1);
        NormalCard thirdNormalCard = new NormalCard(CardColor.Blue, 2);

        int addStack = 0;
        /* Normal card is valid if color or number match when stack is 0 */
        drawFourCard.setColor(CardColor.Red);
        assertTrue(normalCard.isValidAfter(drawFourCard, addStack));
        drawFourCard.setColor(CardColor.Blue);
        assertFalse(normalCard.isValidAfter(drawFourCard, addStack));
        assertTrue(normalCard.isValidAfter(secondNormalCard, addStack));
        assertFalse(normalCard.isValidAfter(thirdNormalCard, addStack));

        addStack = 1;
        /* Normal card is not valid when stack is not 0 */
        assertFalse(normalCard.isValidAfter(drawFourCard, addStack));
        assertFalse(normalCard.isValidAfter(drawTwoCard, addStack));
    }

    @Test
    void reverseCardValidTest() {
        DrawFourCard drawFourCard = new DrawFourCard();
        DrawTwoCard drawTwoCard = new DrawTwoCard(CardColor.Red);
        NormalCard normalCard = new NormalCard(CardColor.Red, 1);
        NormalCard secondNormalCard = new NormalCard(CardColor.Blue, 1);
        ReverseCard reverseCard = new ReverseCard(CardColor.Red);
        ReverseCard secondReverseCard = new ReverseCard(CardColor.Blue);

        int addStack = 0;

        /* Reverse card is valid if color/type match when stack is 0 */
        assertTrue(reverseCard.isValidAfter(normalCard, addStack));
        assertFalse(reverseCard.isValidAfter(secondNormalCard, addStack));
        assertTrue(reverseCard.isValidAfter(secondReverseCard, addStack));

        addStack = 1;
        /* Reverse card is not valid when stack is not 0 */
        assertFalse(reverseCard.isValidAfter(drawFourCard, addStack));
        assertFalse(reverseCard.isValidAfter(drawTwoCard, addStack));
    }

    @Test
    void skipCardValidTest() {
        DrawFourCard drawFourCard = new DrawFourCard();
        DrawTwoCard drawTwoCard = new DrawTwoCard(CardColor.Red);
        NormalCard normalCard = new NormalCard(CardColor.Red, 1);
        NormalCard secondNormalCard = new NormalCard(CardColor.Blue, 1);
        SkipCard skipCard = new SkipCard(CardColor.Red);
        SkipCard secondSkipCard = new SkipCard(CardColor.Blue);

        int addStack = 0;

        /* Skip card is valid if color/type match when stack is 0 */
        assertTrue(skipCard.isValidAfter(normalCard, addStack));
        assertFalse(skipCard.isValidAfter(secondNormalCard, addStack));
        assertTrue(skipCard.isValidAfter(secondSkipCard, addStack));

        addStack = 1;

        /* Skip card is not valid when stack is not 0 */
        assertFalse(skipCard.isValidAfter(drawFourCard, addStack));
        assertFalse(skipCard.isValidAfter(drawTwoCard, addStack));
    }

    @Test
    void wildCardValidTest() {
        DrawFourCard drawFourCard = new DrawFourCard();
        DrawTwoCard drawTwoCard = new DrawTwoCard(CardColor.Red);
        NormalCard normalCard = new NormalCard(CardColor.Red, 1);
        ReverseCard reverseCard = new ReverseCard(CardColor.Red);
        WildCard wildCard = new WildCard();

        int addStack = 0;
        /* Wild card is always valid when stack is 0 */
        assertTrue(wildCard.isValidAfter(drawFourCard, addStack));
        assertTrue(wildCard.isValidAfter(drawTwoCard, addStack));
        assertTrue(wildCard.isValidAfter(normalCard, addStack));
        assertTrue(wildCard.isValidAfter(reverseCard, addStack));

        addStack = 1;
        /* Wild card is not valid when stack is not 0 */
        assertFalse(wildCard.isValidAfter(drawFourCard, addStack));
        assertFalse(wildCard.isValidAfter(drawTwoCard, addStack));
    }

    @Test
    void printCardInfo() {
        DrawFourCard drawFourCard = new DrawFourCard();
        drawFourCard.printCardInfo();
        DrawTwoCard drawTwoCard = new DrawTwoCard(CardColor.Red);
        drawTwoCard.printCardInfo();
        NormalCard normalCard = new NormalCard(CardColor.Blue, 1);
        normalCard.printCardInfo();
        ReverseCard reverseCard = new ReverseCard(CardColor.Green);
        reverseCard.printCardInfo();
        SkipCard skipCard = new SkipCard(CardColor.Yellow);
        skipCard.printCardInfo();
        WildCard wildCard = new WildCard();
        wildCard.printCardInfo();
        assertTrue(true);
    }


}