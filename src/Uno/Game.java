/**
 *  This file is part of Uno
 *
 * The main Class which contain the game loop and act like server.
 * Use arraylist to contain player objects and update game state according to players' behavior
 * Contain the main function to start the game.
 *
 */
package Uno;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Uno.Gui.PlayerFrame;
import Uno.Gui.StartFrame;
import Uno.Gui.WinnerFrame;
import Uno.UnoCard.BlackCard;
import Uno.UnoCard.Card;
import Uno.UnoCard.DrawFourCard;
import Uno.UnoPlayer.*;

import static Uno.UnoSetting.GameConstant.*;
import static Uno.UnoSetting.GuiConstant.*;

public class Game {

    /**< GameState object, update during game */
    private GameState gameState;

    /**< Array list of Player objects  */
    private ArrayList<Player> playerList;

    /**< Number of players in this game */
    private int playerCount;

    /**< Current frame show for user */
    private PlayerFrame playerFrame;

    private int delayTime;

    /**
     * Constructor that initialize the player arraylist and its size.
     */
    public Game() {
        this.playerList = new ArrayList<Player>(GAME_MAX_PLAYER_COUNT);
        this.playerCount = 0;
        this.delayTime = 3;
    }

    /**
     * Functions that add three types of players
     * @param normalPlayerCount See UnoPlayer.NormalPlayer
     * @param simpleAICount See UnoPlayer.BaselineAI
     * @param hardAICount See UnoPlayer.StrategicAI
     */
    public void addPlayer(int normalPlayerCount, int simpleAICount, int hardAICount) {
        for (int i = 0 ; i < normalPlayerCount; i++) {
            playerList.add(new NormalPlayer(i + 1, playerCount));
            playerCount++;
        }
        for (int i = 0 ; i < simpleAICount; i++) {
            playerList.add(new BaselineAI(i + 1, playerCount));
            playerCount++;
        }
        for (int i = 0 ; i < hardAICount; i++) {
            playerList.add(new StrategicAI(i + 1, playerCount));
            playerCount++;
        }
    }

    /**
     * Functions that initialize the game state and give initial hand card to all players.
     * Use in startGame()
     */
    public void setInitGameState() {
        gameState = new GameState(playerCount);
        initDealCard();

        playerFrame.addHintToPanel("All players get their hand cards:", HINT_TEXT_SIZE);
        stopGame(delayTime);

    }

    /**
     * Functions that give initial cards to players
     */
    private void initDealCard() {
        for (int i = 0; i < playerCount; i++) {
            for (int j = 0; j < PLAYER_INIT_CARD_COUNT; j++) {
                dealOneCardTo(i);
            }
        }
    }

    /**
     * Functions that get one card from draw pile and give it to player.
     */
    private void dealOneCardTo(int playerIndex) {
        Card drawCard = gameState.getTopDrawPileCard();
        playerList.get(playerIndex).drawOneCard(drawCard);
    }

    /**
     * The main game function that give information to players and update game state according to
     * played card. Give punishment to player if needed.Check player's hand card to decide winner.
     */
    public void startGame() {
        playerFrame.addHintToPanel("Game Started with " + playerCount +" players.", HINT_TEXT_SIZE);
        stopGame(delayTime);

        setInitGameState();

        while(true) {
            int nextPlayerIndex = gameState.getNextPlayerIndex();
            Player nextPlayer = playerList.get(nextPlayerIndex);
            Card lastCard = gameState.getLastCard();
            int addStack = gameState.getAddStack();

            giveAIOrderInfo(nextPlayerIndex);

            playerFrame.updateFrame(gameState, nextPlayer);
            playerFrame.addHintToPanel(nextPlayer.getName() + " turn", HINT_TEXT_SIZE);

            /* Check if players has valid cards to play */
            if (!nextPlayer.hasValidCard(lastCard,addStack)) {
                /* No valid card Directly give punishment */
                playerFrame.addHintToPanel("No valid card in hand:(", HINT_TEXT_SIZE);

                givePunishmentTo(nextPlayerIndex);
            } else {
                Card pickedCard = null;

                if (nextPlayer instanceof NormalPlayer) {
                    normalPlayerPickCard((NormalPlayer)nextPlayer, lastCard, addStack);
                } else {
                    playerFrame.addHintToPanel(nextPlayer.getName() + " start picking card.", HINT_TEXT_SIZE);
                }

                /* After Players pick a card, let them pick color and order if required */
                pickedCard = nextPlayer.getPlayedCard(lastCard, addStack);
                pickColorOrder(nextPlayerIndex,pickedCard);

                if (pickedCard == null) {
                    /*Player chooses to receive punishment*/
                    playerFrame.addHintToPanel(nextPlayer.getName() + " choose to skip", HINT_TEXT_SIZE);
                    stopGame(delayTime);

                    givePunishmentTo(nextPlayerIndex);
                } else {
                    /*Player played a valid card*/
                    playerFrame.addHintToPanel(nextPlayer.getName() + " plays " + pickedCard.getType() + " card.", HINT_TEXT_SIZE);
                    playerFrame.updateHandCardPanel(nextPlayer);
                    stopGame(delayTime);

                    gameState.updateState(pickedCard);
                }
            }

            /* Check if there is winner */
            if (nextPlayer.getHandCard().isEmpty()) {
                playerFrame.addHintToPanel("You win!", HINT_TEXT_SIZE);
                stopGame(delayTime);
                playerFrame.dispose();
                WinnerFrame winnerFrame = new WinnerFrame(nextPlayer);
                break;
            }
        }
    }

    /**
     * Functions that give different punishment to player according to current game state.
     * @param playerIndex index of player that need punishment
     */
    public void givePunishmentTo(int playerIndex) {
        Player punishedPlayer = playerList.get(playerIndex);

        if (gameState.getAddStack() > 0) {
            /* Player draws all stacked punishment and skip his turn */
            playerFrame.addHintToPanel(punishedPlayer.getName() + " draw " + gameState.getAddStack()
                    + " cards & skip.",HINT_TEXT_SIZE);

            for (int i = 0; i < gameState.getAddStack(); i++) {
                dealOneCardTo(playerIndex);
            }

            playerFrame.updateHandCardPanel(punishedPlayer);
            stopGame(delayTime);

            /* move to next player */
            gameState.continueAndClearStack();
        } else {
            /* Player draws one card and use it if it is valid */

            if (punishedPlayer instanceof NormalPlayer) {
                NormalPlayer normalPlayer = (NormalPlayer) punishedPlayer;
                playerFrame.addHintToPanel("Clicked Draw Pile to get 1 card.",HINT_TEXT_SIZE);

                /* Let human player click the draw pile button */
                normalPlayer.drawOneCard();
            } else {
                playerFrame.addHintToPanel(punishedPlayer.getName() + " get 1 card.",HINT_TEXT_SIZE);
            }

            Deck handCard = punishedPlayer.getHandCard();
            dealOneCardTo(playerIndex);
            Card drawCard = handCard.checkTopCard();
            playerFrame.updateHandCardPanel(punishedPlayer);
            stopGame(delayTime);

            if (drawCard.isValidAfter(gameState.getLastCard(), gameState.getAddStack())) {
                playerFrame.addHintToPanel(punishedPlayer.getName() + " draw a valid card.",HINT_TEXT_SIZE);
                playerFrame.highlightHandCard(playerFrame.getHandCardButtonList().size() - 1);

                pickColorOrder(playerIndex, drawCard);

                gameState.updateState(handCard.drawOneTopCard());
                stopGame(delayTime);
                playerFrame.updateHandCardPanel(punishedPlayer);
                playerFrame.addHintToPanel(punishedPlayer.getName() + " plays " + drawCard.getType() + " card.",HINT_TEXT_SIZE);
            } else {
                playerFrame.addHintToPanel(punishedPlayer.getName() + " draw an invalid card.",HINT_TEXT_SIZE);
                gameState.continueAndClearStack();
            }
            stopGame(delayTime);
        }
    }

    /**
     * Contain the pick card loop for normal players
     * @param normalPlayer current normal player
     * @param lastCard last played card according to game state
     * @param addStack addStack accoridng to game state
     */
    public void normalPlayerPickCard(NormalPlayer normalPlayer, Card lastCard, int addStack) {
        Card pickedCard = null;
        playerFrame.addHintToPanel("Please pick a card.", HINT_TEXT_SIZE);
        normalPlayer.pickCard(lastCard, addStack);

        /* Normal player pick card loop */
        while(true) {
            if (normalPlayer.getPickedCardIndex() == -1) {
                /* Player chooses to skip*/
                break;
            }
            pickedCard = normalPlayer.getHandCard().checkCardUseIndex(normalPlayer.getPickedCardIndex());
            if (pickedCard.isValidAfter(lastCard, addStack)) {
                if (pickedCard instanceof DrawFourCard) {
                    /* Draw four restriction */
                    if (!normalPlayer.getHandCard().containColorMatchCard(lastCard)) {
                        playerFrame.highlightHandCard(normalPlayer.getPickedCardIndex());
                        break;
                    }
                } else {
                    playerFrame.highlightHandCard(normalPlayer.getPickedCardIndex());
                    break;
                }
            }

            /* Player picks an invalid card, let them pick again */
            playerFrame.addHintToPanel("This card is invalid", HINT_TEXT_SIZE);
            normalPlayer.pickCard(lastCard, addStack);
        }
    }

    /**
     * Function that handle the pick color and order logic after player picked a card.
     * @param playerIndex index in player list
     * @param card card that player has decided to play
     */
    public void pickColorOrder(int playerIndex, Card card) {
        Player currPlayer = playerList.get(playerIndex);
        if (card instanceof  BlackCard) {
            if (currPlayer instanceof NormalPlayer) {
                playerFrame.showColorOrderPanel();
            }
            pickColorProcess(playerIndex, (BlackCard) card);
            pickOrderProcess(playerIndex, (BlackCard) card);
            playerFrame.hideColorOrderPanel();
        }
    }

    /**
     * Functions that let player pick a color when they play a black card.
     * @param playerIndex index in player list
     * @param pickedCard black card that player picks
     */
    public void pickColorProcess(int playerIndex, BlackCard pickedCard) {
        Player player = playerList.get(playerIndex);

        if (player instanceof NormalPlayer) {
            playerFrame.addHintToPanel("Please pick a color.", HINT_TEXT_SIZE);
            player.pickColor(pickedCard);
            pickedCard.setColor(((NormalPlayer) player).getPickedColor());
        } else {
            player.pickColor(pickedCard);
        }
    }

    /**
     * Functions that let player pick an order when they play a black card.
     * @param playerIndex index in player list
     * @param pickedCard black card that player picks
     */
    public void pickOrderProcess(int playerIndex, BlackCard pickedCard) {
        giveAIOrderInfo(playerIndex);
        Player player = playerList.get(playerIndex);

        if (player instanceof NormalPlayer) {
            playerFrame.addHintToPanel("Please pick a order.", HINT_TEXT_SIZE);
            player.pickOrder(pickedCard);
            pickedCard.setOrder(((NormalPlayer) player).getPickedOrder());
        } else {
            player.pickOrder(pickedCard);
        }
    }

    /**
     * Give Hard AI the number of hand cards of its left and right player
     * @param playerIndex index of AI
     */
    public void giveAIOrderInfo(int playerIndex) {
        Player player = playerList.get(playerIndex);
        if (player instanceof StrategicAI) {
            int leftPlayerIndex = (playerIndex == 0) ? (playerCount - 1) : (playerIndex - 1);
            int rightPlayerIndex = (playerIndex == playerCount - 1) ? 0 : (playerIndex + 1);
            int leftPlayerCardNum = playerList.get(leftPlayerIndex).getHandCard().getNumCardInDeck();
            int rightPlayerCardNum = playerList.get(rightPlayerIndex).getHandCard().getNumCardInDeck();
            ((StrategicAI) player).getBeneficialOrder(leftPlayerCardNum,rightPlayerCardNum);
        }
    }

    /**
     * Functions that print out players card information for test
     * @param playerIndex index in player list
     */
    public void showPlayerCard(int playerIndex) {
        System.out.println("Player " + playerIndex + "has these cards:");
        Deck handCard = playerList.get(playerIndex).getHandCard();
        Card currCard = null;
        for (int i = 0; i < handCard.getNumCardInDeck(); i++) {
            currCard = handCard.checkCardUseIndex(i);
            currCard.printCardInfo();
        }
    }

    /**
     * Stop game to give user time to see the changing game state
     * @param second stop time
     */
    public void stopGame(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setPlayerFrame(PlayerFrame playerFrame) {
        this.playerFrame = playerFrame;
    }

    public void setDelayTime(int time) {
        this.delayTime = time;
    }

    public static void main(String[] args) {
        StartFrame startFrame = new StartFrame();
        startFrame.getListener().setTimeDelay(3);
    }
}
