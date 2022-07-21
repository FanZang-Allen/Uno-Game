/**
 * This file is part of Uno
 *
 * Start frame appear before game start.
 * Wait for user to enter number of players.
 *
 */
package Uno.Gui;

import Uno.Gui.ActionListener.StartFrameListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static Uno.UnoSetting.GameConstant.*;
import static Uno.UnoSetting.GuiConstant.*;

public class StartFrame extends UnoFrame {
    /**< Link to startGame() in Game class*/
    public JButton startButton;

    /**< UNO logo in the center of background picture */
    private JLabel logoLabel;

    /**< Background picture of frame */
    private JLabel backgroundLabel;

    /**< Tell users to enter number of players */
    public JLabel instructionLabel;

    /**< For user to set number of different kinds of player in game */
    private JPanel settingPanel;

    /**< Inside settingPanel */
    public JSpinner normalPlayerSpinner;

    /**< Inside settingPanel */
    public JSpinner baselineAISpinner;

    /**< Inside settingPanel */
    public JSpinner strategicAISpinner;

    /**< Listener created in constructor */
    private StartFrameListener listener;

    public StartFrame() {
        super();
        this.setLayout(null);
        initBackgroundLabel();
        initLogoLabel();
        initInstructionLabel();
        initStartButton();
        initSettingPanel();
        this.add(backgroundLabel);
        backgroundLabel.add(logoLabel);
        backgroundLabel.add(instructionLabel);
        backgroundLabel.add(settingPanel);
        backgroundLabel.add(startButton);
        listener = new StartFrameListener(this);
        startButton.addActionListener(listener);
        reloadJFrame(this, UNO_FRAME_WIDTH, UNO_FRAME_HEIGHT);

    }

    /**
     *  Add background picture to a label and set layout to null.
     */
    public void initBackgroundLabel() {
        ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("GuiResource/StartBackground.png")));
        backgroundImage = resizeImageIcon(backgroundImage, UNO_FRAME_WIDTH,UNO_FRAME_HEIGHT);
        backgroundLabel = new JLabel();
        backgroundLabel.setIcon(backgroundImage);
        backgroundLabel.setBounds(0,0,UNO_FRAME_WIDTH,UNO_FRAME_HEIGHT);
        backgroundLabel.setHorizontalAlignment(JLabel.LEFT);
        backgroundLabel.setVerticalAlignment(JLabel.TOP);
        backgroundLabel.setLayout(null);
    }

    /**
     *  Add logo label to the center of background image.
     */
    public void initLogoLabel() {
        ImageIcon logoImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("GuiResource/UNO_Logo.png")));
        logoImage = resizeImageIcon(logoImage, 400,281);
        logoLabel = new JLabel();
        logoLabel.setIcon(logoImage);
        logoLabel.setHorizontalAlignment(JLabel.LEFT);
        logoLabel.setVerticalAlignment(JLabel.TOP);
        logoLabel.setBounds(420,150, 400,281);
    }

    /**
     *  Add text label to tell users enter number of players.
     */
    public void initInstructionLabel() {
        instructionLabel = new JLabel();
        instructionLabel.setText("Enter the number of player to start the game!");
        instructionLabel.setFont(new Font("monospaced",Font.PLAIN, 20));
        instructionLabel.setHorizontalAlignment(JLabel.LEFT);
        instructionLabel.setVerticalAlignment(JLabel.TOP);
        instructionLabel.setBounds(370,530, 800,200);
        instructionLabel.setVisible(false);
    }


    /**
     *  Add game setting ui for users to set number of players
     */
    public void initSettingPanel() {
        JLabel normalPlayerText = initTextLabel("Player Num:", 15);
        normalPlayerSpinner = new JSpinner(new SpinnerNumberModel(0, 0, GAME_MAX_PLAYER_COUNT, 1));
        normalPlayerSpinner.setPreferredSize(new Dimension(100,40));

        JLabel baselineAIText = initTextLabel("Simple AI Num:", 15);
        baselineAISpinner = new JSpinner(new SpinnerNumberModel(0, 0, GAME_MAX_PLAYER_COUNT, 1));
        baselineAISpinner.setPreferredSize(new Dimension(100,40));

        JLabel strategicAIText = initTextLabel("Hard AI Num:", 15);
        strategicAISpinner = new JSpinner(new SpinnerNumberModel(0, 0, GAME_MAX_PLAYER_COUNT, 1));
        strategicAISpinner.setPreferredSize(new Dimension(100,40));

        settingPanel = initPanel(Color.lightGray, 700, 50);
        settingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        settingPanel.setBounds(320,570, 670,50);
        settingPanel.add(normalPlayerText);
        settingPanel.add(normalPlayerSpinner);
        settingPanel.add(baselineAIText);
        settingPanel.add(baselineAISpinner);
        settingPanel.add(strategicAIText);
        settingPanel.add(strategicAISpinner);
    }

    /**
     *  Add start button to wait for the pressing event.
     */
    public void initStartButton() {
        startButton = initColorButton(Color.RED, "Start", 200, 50);
        startButton.setBounds(530, 630, 200,50);
    }

    public StartFrameListener getListener() {
        return listener;
    }
}
