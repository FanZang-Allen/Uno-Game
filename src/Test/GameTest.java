package Test;

import Uno.Game;
import Uno.Gui.PlayerFrame;
import Uno.UnoCard.Card;
import Uno.UnoCard.DrawFourCard;
import Uno.UnoCard.NormalCard;
import org.junit.jupiter.api.Test;

import static Uno.UnoSetting.GameConstant.*;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    /* Note: this class involve GUI part test may take up to 20 seconds*/
    @Test
    void addPlayer() {
        Game testGame = new Game();
        PlayerFrame playerFrame = new PlayerFrame(testGame);
        playerFrame.setVisible(false);
        testGame.setPlayerFrame(playerFrame);
        testGame.setDelayTime(0);

        testGame.addPlayer(1, 1, 1);
        assertEquals(testGame.getPlayerCount(), 3);
    }

    @Test
    void setInitGameState() {
        Game testGame = new Game();
        PlayerFrame playerFrame = new PlayerFrame(testGame);
        playerFrame.setVisible(false);
        testGame.setPlayerFrame(playerFrame);

        testGame.addPlayer(1, 1, 1);
        testGame.setInitGameState();
        assertEquals(testGame.getPlayerList().get(0).getHandCard().getNumCardInDeck(), PLAYER_INIT_CARD_COUNT);
        assertEquals(testGame.getPlayerList().get(1).getHandCard().getNumCardInDeck(), PLAYER_INIT_CARD_COUNT);
    }

    @Test
    void startGame() {
        Game testGame = new Game();
        PlayerFrame playerFrame = new PlayerFrame(testGame);
        playerFrame.setVisible(false);
        testGame.setPlayerFrame(playerFrame);
        testGame.setDelayTime(0);

        testGame.addPlayer(0, 2, 1);
        testGame.startGame();
        /* Game should finish main loop with only AI */
        assertTrue(true);
    }

    @Test
    void givePunishmentTo() {
        Game testGame = new Game();
        PlayerFrame playerFrame = new PlayerFrame(testGame);
        playerFrame.setVisible(false);
        testGame.setPlayerFrame(playerFrame);
        testGame.setDelayTime(0);

        testGame.addPlayer(0, 2, 1);
        testGame.setInitGameState();

        /* test add stack greater than 0. Note: test function has some delay for actual game */
        Card testCard = new DrawFourCard();
        testGame.getGameState().updateState(testCard);
        testGame.givePunishmentTo(0);
        assertEquals(testGame.getPlayerList().get(0).getHandCard().getNumCardInDeck(), PLAYER_INIT_CARD_COUNT + 4);

        /* test add stack equal 0 , player should play the draw card if it is valid, Note: test function has some delay for actual game */
        testCard = new NormalCard(CardColor.Blue, 1);
        Card secondCard = new NormalCard(CardColor.Blue, 2);
        testGame.getGameState().updateState(testCard);
        testGame.getGameState().getDrawPile().addOneTopCard(secondCard);
        testGame.givePunishmentTo(1);
        assertEquals(testGame.getPlayerList().get(1).getHandCard().getNumCardInDeck(), PLAYER_INIT_CARD_COUNT);
    }


}