/**
 * This file is part of Uno
 *
 * The main game window which switch between players as game continue.
 * Contains all buttons for player to play this game.
 * Update according Player and GameState object.
 *
 */
package Uno.Gui;

import Uno.Game;
import Uno.GameState;
import Uno.Gui.ActionListener.PlayerFrameListener;
import Uno.UnoPlayer.NormalPlayer;
import Uno.UnoPlayer.Player;
import Uno.UnoCard.Card;
import Uno.UnoCard.NormalCard;

import static Uno.UnoSetting.GameConstant.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


import static Uno.UnoSetting.GuiConstant.*;

public class PlayerFrame extends UnoFrame{
    /**< For listener to get game info */
    private Game game;

    /**< Panel to show player's hand card*/
    private JPanel handCardPanel;

    /**< Array list of hand card button to help track what cards are inside handCardPanel right now*/
    private ArrayList<JButton> handCardButtonList;

    /**< Array list of color button for user to pick color after playing a black card*/
    private ArrayList<JButton> colorButtonList;

    /**< Array list of order button for user to pick order after playing a black card*/
    private ArrayList<JButton> orderButtonList;

    /**< Array list of hint text label to help track what hints are inside hintPanel right now*/
    private ArrayList<JLabel> hintLabelList;

    /**< Panel to contain all game options for players, like color,order pick button, skip button， hint panel...*/
    private JPanel optionPanel;

    /**< Inside optionPanel */
    private JPanel pickColorPanel;

    /**< Inside optionPanel */
    private JLabel pickColorTextLabel;

    /**< Inside optionPanel */
    private JPanel pickOrderPanel;

    /**< Inside optionPanel */
    private JLabel pickOrderTextLabel;

    /**< Inside optionPanel, Contain buttons that always show in option panel*/
    private JPanel perpetualButtonPanel;

    /**< Inside perpetualButtonPanel */
    private JButton skipButton;

    /**< Inside perpetualButtonPanel */
    private JButton hideHandCardButton;

    /**< Inside perpetualButtonPanel */
    private JButton showHandCardButton;

    /**< Inside optionPanel */
    private JPanel hintPanel;

    /**< Panel to contain information about current turn */
    private JPanel mainPanel;

    /**< Inside mainPanel */
    private JButton lastCardButton;

    /**< Inside mainPanel */
    private JButton drawPileButton;

    /**< Inside mainPanel */
    private JPanel gameStatePanel;

    /**< Inside gameStatePanel */
    private JLabel colorStateLabel;

    /**< Inside gameStatePanel */
    private JLabel typeStateLabel;

    /**< Inside gameStatePanel */
    private JLabel numberStateLabel;

    /**< Inside gameStatePanel */
    private JLabel stackStateLabel;

    /**< Inside gameStatePanel */
    private JLabel orderStateLabel;

    /**< Control the button reaction */
    private PlayerFrameListener listener;

    public PlayerFrame(Game game) {
        super();
        this.game = game;
        this.setLayout(new BorderLayout());
        this.handCardButtonList = new ArrayList<JButton>(100);
        this.hintLabelList = new ArrayList<JLabel>(5);
        this.colorButtonList = new ArrayList<JButton>(4);
        this.orderButtonList = new ArrayList<JButton>(2);
        listener = new PlayerFrameListener(game, this);
        initHandCardPanel();
        initOptionPanel();
        initMainPanel();
        this.add(handCardPanel, BorderLayout.SOUTH);
        this.add(optionPanel, BorderLayout.EAST);
        this.add(mainPanel, BorderLayout.CENTER);

    }

    /**
     * Add hand card panel to the south of border layout. No cards right now.
     */
    public void initHandCardPanel() {
        handCardPanel = new JPanel();
        handCardPanel.setBackground(Color.gray);
        handCardPanel.setPreferredSize(new Dimension(UNO_FRAME_WIDTH, 200));
        handCardPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    /**
     *  Add option panel to the west of border layout.
     *  Also initialize all buttons and text label inside option panel.
     */
    public void initOptionPanel() {
        optionPanel = new JPanel();
        optionPanel.setBackground(Color.gray);
        optionPanel.setPreferredSize(new Dimension(340, UNO_FRAME_HEIGHT));
        optionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        initPickColorPanel();
        initPickColorTextLabel();
        initPickOrderPanel();
        initPickOrderTextLabel();
        initPerpetualButtonPanel();
        initHintPanel();
        hideColorOrderPanel();
        optionPanel.add(pickColorTextLabel);
        optionPanel.add(pickColorPanel);
        optionPanel.add(pickOrderTextLabel);
        optionPanel.add(pickOrderPanel);
        optionPanel.add(perpetualButtonPanel);
        optionPanel.add(hintPanel);
    }

    /**
     *  Add four color button to let user pick color if needed.
     */
    public void initPickColorPanel() {
        JButton redButton = initColorButton(Color.red, "Red",70,70);
        redButton.addActionListener(listener);
        colorButtonList.add(redButton);
        JButton yellowButton = initColorButton(Color.orange, "Yellow",70,70);
        yellowButton.addActionListener(listener);
        colorButtonList.add(yellowButton);
        JButton greenButton = initColorButton(Color.green, "Green",70,70);
        greenButton.addActionListener(listener);
        colorButtonList.add(greenButton);
        JButton blueButton = initColorButton(Color.blue, "Blue",70,70);
        blueButton.addActionListener(listener);
        colorButtonList.add(blueButton);

        pickColorPanel = initPanel(Color.gray, 320, 70);
        pickColorPanel.add(redButton);
        pickColorPanel.add(yellowButton);
        pickColorPanel.add(greenButton);
        pickColorPanel.add(blueButton);
    }

    /**
     *  Add instruction text to remind user to pick color.
     */
    public void initPickColorTextLabel() {
        pickColorTextLabel = initTextLabel(" Pick Color:", 25);
    }

    /**
     *  Add two order buttons to let player pick order if needed.
     */
    public void initPickOrderPanel() {
        JButton clockwiseButton = initButtonWithImage("GuiResource/right-arrow.png",
                80,40,"Clockwise", 140,70);
        clockwiseButton.addActionListener(listener);
        orderButtonList.add(clockwiseButton);
        JButton antiClockwiseButton = initButtonWithImage("GuiResource/left-arrow.png",
                80,40,"AntiClockwise",140,70);
        antiClockwiseButton.addActionListener(listener);
        orderButtonList.add(antiClockwiseButton);

        pickOrderPanel = initPanel(Color.gray, 320, 70);
        pickOrderPanel.add(antiClockwiseButton);
        pickOrderPanel.add(clockwiseButton);
    }

    /**
     *  Add instruction text to remind user to pick order.
     */
    public void initPickOrderTextLabel() {
        pickOrderTextLabel = initTextLabel(" Pick Order:", 25);
    }

    /**
     *  Add skip, hide, show button to option panel.
     */
    public void initPerpetualButtonPanel() {
        initSkipButton();
        initHideHandCardButton();
        initShowHandCardButton();
        perpetualButtonPanel = initPanel(Color.gray, 320, 170);
        perpetualButtonPanel.add(skipButton);
        perpetualButtonPanel.add(showHandCardButton);
        perpetualButtonPanel.add(hideHandCardButton);
    }

    /**
     *  Skip-button for user to skip this turn.
     */
    public void initSkipButton() {
        skipButton = initColorButton(Color.black,
                "Skip", 280, 70);
        skipButton.addActionListener(listener);
    }

    /**
     *  Hide hand card button to avoid others to see the cards.
     */
    public void initHideHandCardButton() {
        hideHandCardButton = initButtonWithImage("GuiResource/hide.png",
                80,70,"HideHandCard", 140,100);
        hideHandCardButton.addActionListener(listener);
    }

    /**
     *  Show hand card button to show current player's hand card.
     */
    public void initShowHandCardButton() {
        showHandCardButton = initButtonWithImage("GuiResource/show.png",
                80,70,"ShowHandCard", 140,100);
        showHandCardButton.addActionListener(listener);
    }

    /**
     *  Add hint panel which contains hints about current game state in option panel.
     */
    public void initHintPanel() {
        hintPanel = initPanel(Color.white, 320, 120);
        hintPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    /**
     *  Add main panel to the center of border layout.
     */
    public void initMainPanel() {
        initDrawPileButton();
        initLastCardButton();
        initGameStatePanel();
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new FlowLayout());
        mainPanel.add(drawPileButton);
        /* Add blank Panel to leave blank space between visible panels */
        JPanel firstBlankPanel = initBlankPanel(50, ORIG_UNO_CARD_HEIGHT);
        JPanel secondBlankPanel = initBlankPanel(50, ORIG_UNO_CARD_HEIGHT);
        mainPanel.add(firstBlankPanel);
        mainPanel.add(lastCardButton);
        mainPanel.add(secondBlankPanel);
        mainPanel.add(gameStatePanel);
    }

    /**
     *  Add last card button showing the last discard pile.
     */
    public void initLastCardButton() {
        lastCardButton = initCardButton("GuiResource/Card Pictures/Deck.png",
                ORIG_UNO_CARD_WIDTH,ORIG_UNO_CARD_HEIGHT, "Last Card");
    }

    /**
     *  Add draw pile button to let player draws card.
     */
    public void initDrawPileButton() {
        drawPileButton = initCardButton("GuiResource/Card Pictures/Deck.png",
                ORIG_UNO_CARD_WIDTH,ORIG_UNO_CARD_HEIGHT, "Draw Pile");
        drawPileButton.addActionListener(listener);
    }

    /**
     *  Game state panel contains all the detail information about current turn.
     */
    public void initGameStatePanel() {
        JLabel introLabel = initTextLabel("    Detailed information", 20);
        colorStateLabel = initTextLabel(COLOR_STATE_FRONT, 17);
        numberStateLabel = initTextLabel(NUM_STATE_FRONT, 17);
        typeStateLabel = initTextLabel(TYPE_STATE_FRONT, 17);
        stackStateLabel = initTextLabel(STACK_STATE_FRONT, 17);
        orderStateLabel = initTextLabel(ORDER_STATE_FRONT, 17);

        gameStatePanel = initPanel(Color.white, 350, 330);
        gameStatePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        gameStatePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        gameStatePanel.add(introLabel);
        gameStatePanel.add(colorStateLabel);
        gameStatePanel.add(numberStateLabel);
        gameStatePanel.add(typeStateLabel);
        gameStatePanel.add(stackStateLabel);
        gameStatePanel.add(orderStateLabel);
    }

    /**
     * Update the whole frame while switching to next player's window
     * @param gameState Current game state instance with info updated each turn
     * @param player Current player instance with hand card list.
     */
    public void updateFrame(GameState gameState, Player player) {
        updateHandCardPanel(player);
        updateMainPanel(gameState);
    }

    /**
     *  Show pick color & order panel when player pick a black card.
     */
    public void showColorOrderPanel() {
        pickColorTextLabel.setVisible(true);
        pickColorPanel.setVisible(true);
        pickOrderTextLabel.setVisible(true);
        pickOrderPanel.setVisible(true);
    }

    /**
     *  Hide pick color & order panel after player finish picking process.
     */
    public void hideColorOrderPanel() {
        pickColorTextLabel.setVisible(false);
        pickColorPanel.setVisible(false);
        pickOrderTextLabel.setVisible(false);
        pickOrderPanel.setVisible(false);
    }

    /**
     * Get the path to card picture using card object.
     * @param card Object that contain all card information(color,number etc.)
     * @return The path to card image file.
     */
    private String getCardImagePath(Card card) {
        String pathFront = "GuiResource/Card Pictures/";
        String pathEnd = ".png";

        String cardColor = card.getColor().name();
        CardType cardType = card.getType();
        switch (cardType) {
            case DrawFour :
                return pathFront + "Wild_Draw" + pathEnd;
            case DrawTwo:
                return pathFront + cardColor + "_Draw" + pathEnd;
            case Normal:
                NormalCard downcastCard = (NormalCard) card;
                String cardNum = Integer.toString(downcastCard.getNum());
                return pathFront + cardColor + "_" + cardNum + pathEnd;
            case Reverse:
                return pathFront + cardColor + "_Reverse" + pathEnd;
            case Skip:
                return pathFront + cardColor + "_Skip" + pathEnd;
            case Wild:
                return pathFront + "Wild" + pathEnd;
            default:
                break;
        }
        return null;
    }

    /**
     * Remove all card buttons in current hand card panel, and add
     * cards button using card objects in array list.
     * @param player Player object that contains hand card list.
     */
    public void updateHandCardPanel(Player player) {
        ArrayList<Card> handCard = player.getHandCard().getPile();

        /* Clear previous player's hand card first */
        for (JButton b: handCardButtonList) {
            handCardPanel.remove(b);
        }
        handCardButtonList.clear();

        int divider = handCard.size() / 10 + 1;     /* Used to set size of button according to num of cards*/
        for (Card c: handCard) {
            JButton currButton = null;
            if (player instanceof NormalPlayer) {
                currButton = initCardButton(getCardImagePath(c),
                        HAND_CARD_WIDTH / divider, HAND_CARD_HEIGHT / divider, null);
            } else {
                /* AI player should not display their cards to user*/
                currButton = initCardButton("GuiResource/Card Pictures/Deck.png",
                        HAND_CARD_WIDTH / divider, HAND_CARD_HEIGHT / divider, null);
            }
            currButton.addActionListener(listener);
            handCardButtonList.add(currButton);
            handCardPanel.add(currButton);
        }
        reloadPanel(handCardPanel);
    }

    /**
     * Update the last card button and detail information panel when game state update.
     * @param gameState Current game state instance with info updated each turn
     */
    public void updateMainPanel(GameState gameState) {
        Card lastCard = gameState.getLastCard();
        changeButtonImage(getCardImagePath(lastCard), lastCardButton, ORIG_UNO_CARD_WIDTH, ORIG_UNO_CARD_HEIGHT);

        String cardType = lastCard.getType().name();
        typeStateLabel.setText(TYPE_STATE_FRONT + cardType);
        String cardColor = lastCard.getColor().name();
        colorStateLabel.setText(COLOR_STATE_FRONT + cardColor);
        if (lastCard.getType() == CardType.Normal) {
            NormalCard downcastCard = (NormalCard) lastCard;
            String cardNum = Integer.toString(downcastCard.getNum());
            numberStateLabel.setText(NUM_STATE_FRONT + cardNum);
        } else {
            numberStateLabel.setText(NUM_STATE_FRONT + "Undefined");
        }

        stackStateLabel.setText(STACK_STATE_FRONT + Integer.toString(gameState.getAddStack()));
        if (gameState.getOrder() == 1) {
            orderStateLabel.setText(ORDER_STATE_FRONT + "Clockwise");
        } else {
            orderStateLabel.setText(ORDER_STATE_FRONT + "AntiClockwise");
        }
        reloadPanel(mainPanel);

    }

    /**
     *  Functions that link to hide button.
     */
    public void hideHandCard() {
        for (JButton b: handCardButtonList) {
            b.setVisible(false);
        }
    }

    /**
     *  Functions that link to show button.
     */
    public void showHandCard() {
        for (JButton b: handCardButtonList) {
            b.setVisible(true);
        }
    }

    /**
     * Call when game state change and add hint about what is changed
     * Call when user press the wrong button, add hint to remind user what to do next.
     * @param hint Text message in string
     * @param textSize size of the text to be added inside panel
     */
    public void addHintToPanel(String hint, int textSize) {
        JLabel hintLabel = initTextLabel(hint, textSize);
        if (hintLabelList.size() == HINT_TEXT_COUNT) {
            hintPanel.remove(hintLabelList.get(0));
            hintLabelList.remove(0);
        }
        hintLabelList.add(hintLabel);
        hintPanel.add(hintLabel);
        reloadPanel(hintPanel);
    }

    /**
     * Highlight the picked card button for user
     * @param index index of card that need to be highlighted in hand card butto list.
     */
    public void highlightHandCard(int index) {
        highlightButton(handCardButtonList.get(index));
    }

    public ArrayList<JButton> getHandCardButtonList() {
        return handCardButtonList;
    }

    public ArrayList<JButton> getColorButtonList() {
        return colorButtonList;
    }

    public ArrayList<JButton> getOrderButtonList() {
        return orderButtonList;
    }

    public JButton getDrawPileButton() {
        return drawPileButton;
    }

    public JButton getHideHandCardButton() {
        return hideHandCardButton;
    }

    public JButton getShowHandCardButton() {
        return showHandCardButton;
    }

    public JButton getSkipButton() {
        return skipButton;
    }

    public PlayerFrameListener getListener() {
        return this.listener;
    }
}
