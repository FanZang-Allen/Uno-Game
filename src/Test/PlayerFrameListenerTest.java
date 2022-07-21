package Test;

import Uno.Game;
import Uno.Gui.PlayerFrame;
import Uno.UnoPlayer.NormalPlayer;
import Uno.UnoPlayer.Player;
import Uno.UnoSetting.GameConstant;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFrameListenerTest {

    /* Follow manual test plan to test show and hide hand card button */

    @Test
    void testHandCardButton() {
        Game game = new Game();
        game.addPlayer(2, 0, 0);
        game.setDelayTime(0);
        PlayerFrame playerFrame = new PlayerFrame(game);
        game.setPlayerFrame(playerFrame);
        game.setInitGameState();
        game.getGameState().setPlayerIndex(0);

        playerFrame.updateHandCardPanel(game.getPlayerList().get(0));
        NormalPlayer testPlayer = (NormalPlayer) game.getPlayerList().get(0);

        /* Test hand card button correctly set the picked index */
        for (int i = 0; i < testPlayer.getHandCard().getPile().size();i++) {
            testPlayer.setProcessIndex(1);
            playerFrame.getHandCardButtonList().get(i).doClick();
            assertEquals(testPlayer.getPickedCardIndex(), i);
        }
    }

    @Test
    void testSkipButton() {
        Game game = new Game();
        game.addPlayer(2, 0, 0);
        game.setDelayTime(0);
        PlayerFrame playerFrame = new PlayerFrame(game);
        game.setPlayerFrame(playerFrame);
        game.setInitGameState();
        game.getGameState().setPlayerIndex(0);

        playerFrame.updateHandCardPanel(game.getPlayerList().get(0));
        NormalPlayer testPlayer = (NormalPlayer) game.getPlayerList().get(0);
        testPlayer.setPickedCardIndex(0);

        /* Test skip button correctly set picked index to be -1 */
        testPlayer.setProcessIndex(1);
        playerFrame.getSkipButton().doClick();
        assertEquals(testPlayer.getPickedCardIndex(), -1);
    }

    @Test
    void testPickColorButton() {
        Game game = new Game();
        game.addPlayer(2, 0, 0);
        game.setDelayTime(0);
        PlayerFrame playerFrame = new PlayerFrame(game);
        playerFrame.showColorOrderPanel();
        game.setPlayerFrame(playerFrame);
        game.setInitGameState();
        game.getGameState().setPlayerIndex(0);

        playerFrame.updateHandCardPanel(game.getPlayerList().get(0));
        NormalPlayer testPlayer = (NormalPlayer) game.getPlayerList().get(0);

        /* Test pick color button correctly set the picked color */
        testPlayer.setProcessIndex(2);
        playerFrame.getColorButtonList().get(0).doClick();
        assertEquals(testPlayer.getPickedColor(), GameConstant.CardColor.Red);

        testPlayer.setProcessIndex(2);
        playerFrame.getColorButtonList().get(1).doClick();
        assertEquals(testPlayer.getPickedColor(), GameConstant.CardColor.Yellow);

        testPlayer.setProcessIndex(2);
        playerFrame.getColorButtonList().get(2).doClick();
        assertEquals(testPlayer.getPickedColor(), GameConstant.CardColor.Green);

        testPlayer.setProcessIndex(2);
        playerFrame.getColorButtonList().get(3).doClick();
        assertEquals(testPlayer.getPickedColor(), GameConstant.CardColor.Blue);
    }

    @Test
    void testPickOrderButton() {
        Game game = new Game();
        game.addPlayer(2, 0, 0);
        game.setDelayTime(0);
        PlayerFrame playerFrame = new PlayerFrame(game);
        game.setPlayerFrame(playerFrame);
        game.setInitGameState();
        game.getGameState().setPlayerIndex(1);

        playerFrame.updateHandCardPanel(game.getPlayerList().get(1));
        NormalPlayer testPlayer = (NormalPlayer) game.getPlayerList().get(1);
        playerFrame.showColorOrderPanel();

        /* Test pick order button correctly set the picked order */
        testPlayer.setProcessIndex(3);
        playerFrame.getOrderButtonList().get(0).doClick();
        assertEquals(testPlayer.getPickedOrder(), 1);

        testPlayer.setProcessIndex(3);
        playerFrame.getOrderButtonList().get(1).doClick();
        assertEquals(testPlayer.getPickedOrder(), -1);

    }

    @Test
    void testDrawPileButton() {
        Game game = new Game();
        game.addPlayer(2, 0, 0);
        game.setDelayTime(0);
        PlayerFrame playerFrame = new PlayerFrame(game);
        game.setPlayerFrame(playerFrame);
        game.setInitGameState();
        game.getGameState().setPlayerIndex(0);

        playerFrame.updateHandCardPanel(game.getPlayerList().get(0));
        NormalPlayer testPlayer = (NormalPlayer) game.getPlayerList().get(0);

        /* Test draw pile button correctly release semaphore */
        testPlayer.setProcessIndex(4);
        playerFrame.getDrawPileButton().doClick();
        assertEquals(0, testPlayer.getProcessIndex());

    }




}