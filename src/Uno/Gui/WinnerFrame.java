/**
 * This file is part of Uno
 *
 * Winner frame appear after game ends
 * Show winner id with congratulation picture.
 *
 */
package Uno.Gui;

import Uno.UnoPlayer.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static Uno.UnoSetting.GuiConstant.*;

public class WinnerFrame extends UnoFrame{
    /**< winner from Game class*/
    private Player winner;

    /**< background picture */
    private JLabel backgroundLabel;

    /**< Inside  backgroundLabel */
    private JLabel winnerLabel;

    /**
     * Initialize the winner frame with winner info.
     * @param player Player object containing winner info.
     */
    public WinnerFrame(Player player) {
        super();
        this.winner = player;
        this.setLayout(null);
        initBackgroundLabel();
        initWinnerLabel();
        this.add(backgroundLabel);
        backgroundLabel.add(winnerLabel);
        reloadJFrame(this, UNO_FRAME_WIDTH, UNO_FRAME_HEIGHT);
    }

    /**
     *  Add background to a label and set layout to null.
     */
    public void initBackgroundLabel() {
        ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("GuiResource/WinnerBackground.png")));
        backgroundImage = resizeImageIcon(backgroundImage, UNO_FRAME_WIDTH,UNO_FRAME_HEIGHT);
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0,0,UNO_FRAME_WIDTH,UNO_FRAME_HEIGHT);
        backgroundLabel.setHorizontalAlignment(JLabel.LEFT);
        backgroundLabel.setVerticalAlignment(JLabel.TOP);
        backgroundLabel.setLayout(null);
    }

    /**
     *  Add congratulation text based on winner id.
     */
    public void initWinnerLabel() {
        String winnerText = winner.getName() + " is the winner!";
        winnerLabel = new JLabel(winnerText);
        winnerLabel.setFont(new Font("monospaced",Font.PLAIN, 40));
        winnerLabel.setHorizontalAlignment(JLabel.LEFT);
        winnerLabel.setVerticalAlignment(JLabel.TOP);
        winnerLabel.setBounds(350,150, 800,200);
    }


}
