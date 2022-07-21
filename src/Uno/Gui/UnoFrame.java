/**
 * This file is part of Uno
 *
 * Super class of all other frame classes in Uno game.
 * UnoFrame class contains useful function that
 * could be used in all other frame classes,
 * including label, panel, button initialization function.
 *
 */
package Uno.Gui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import static Uno.UnoSetting.GuiConstant.*;

public class UnoFrame extends JFrame {

    /**< Icon image of all uno frames */
    private ImageIcon frameIcon;

    /**
     * Constructor that initialize basic settings of a frame.
     */
    public UnoFrame() {
        frameIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("GuiResource/Frame-Logo.png")));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("UNO");
        this.setIconImage(frameIcon.getImage());
        this.setSize(UNO_FRAME_WIDTH,UNO_FRAME_HEIGHT);
        this.setVisible(true);

    }

    /**
     * Useful function to smoothly resize an image icon suitable for a component
     * @param before the image that needs to be resized
     * @param width width of resized image
     * @param height height of resized image
     * @return smoothly resized image icon object
     */
    public ImageIcon resizeImageIcon(ImageIcon before, int width, int height) {
        Image oldImage = before.getImage();
        Image newImage = oldImage.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }

    /**
     * Reload the given panel, should be called whenever there's a change in panel
     * @param panel JPanel that needs to be reloaded
     */
    public void reloadPanel(JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Reload the given JFrame, should be called whenever there's a change in frame
     * @param frame JFrame that needs to be reloaded
     * @param frameWidth width of frame to help reload process
     * @param frameHeight height of frame to help reload process
     */
    public void reloadJFrame(JFrame frame, int frameWidth, int frameHeight) {
        frame.setSize(frameWidth - 1,frameHeight - 1);
        frame.setSize(frameWidth,frameHeight);
    }

    /**
     * Initialize a button based on given information.
     * Default button background color is gray.
     * Default text size: 17
     * @param textColor Color of text on button
     * @param buttonText Text in String
     * @param buttonWidth Width of button
     * @param buttonHeight Height of button
     * @return JButton object created
     */
    public JButton initColorButton(Color textColor, String buttonText, int buttonWidth, int buttonHeight) {
        JButton outputButton = new JButton();
        outputButton.setText(buttonText);
        outputButton.setBackground(Color.gray);
        outputButton.setFont(new Font("monospaced",Font.PLAIN, 17));
        outputButton.setForeground(textColor);
        outputButton.setOpaque(true);
        outputButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        return outputButton;
    }

    /**
     * Initialize a button based on given information.
     * Default button background color is gray.
     * Default text size: 13
     * @param imagePath Relative path to image file
     * @param imageWidth width to be set
     * @param imageHeight height to be set
     * @param buttonText text in string to be set
     * @param buttonWidth width to be set
     * @param buttonHeight height to be set
     * @return JButton object created
     */
    public JButton initButtonWithImage(String imagePath, int imageWidth, int imageHeight, String buttonText, int buttonWidth, int buttonHeight) {
        ImageIcon image = new ImageIcon(Objects.requireNonNull(this.getClass().getResource(imagePath)));
        image = resizeImageIcon(image, imageWidth,imageHeight);

        JButton outputButton = new JButton();
        outputButton.setText(buttonText);
        outputButton.setBackground(Color.gray);
        outputButton.setFont(new Font("monospaced",Font.PLAIN, 13));
        outputButton.setIcon(image);
        outputButton.setHorizontalTextPosition(JButton.CENTER);
        outputButton.setVerticalTextPosition(JButton.BOTTOM);
        outputButton.setIconTextGap(-5);
        outputButton.setOpaque(true);
        outputButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        return outputButton;
    }

    /**
     * Initialize a panel based on given information.
     * Default layout: FlowLayout
     * @param backgroundColor color of panel background
     * @param panelWidth width to be set
     * @param panelHeight height to be set
     * @return JPanel object created
     */
    public JPanel initPanel(Color backgroundColor, int panelWidth, int panelHeight) {
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new FlowLayout());
        outputPanel.setBackground(backgroundColor);
        outputPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        return outputPanel;
    }

    /**
     * Initialize a panel with text based on given information.
     * @param text text in string
     * @param textSize size of text to be set
     * @return JLabel with text created
     */
    public JLabel initTextLabel(String text, int textSize) {
        JLabel outputLabel = new JLabel();
        outputLabel.setText(text);
        outputLabel.setFont(new Font("monospaced",Font.PLAIN, textSize));
        outputLabel.setHorizontalAlignment(JLabel.LEFT);
        outputLabel.setVerticalAlignment(JLabel.TOP);
        return outputLabel;
    }

    /**
     * Initialize a button with given card image.
     * Add text below if needed
     * @param imagePath  Relative path to image file
     * @param buttonWidth Width to be set
     * @param buttonHeight Height to be set
     * @param buttonText String in text
     * @return JButton object created
     */
    public JButton initCardButton(String imagePath, int buttonWidth, int buttonHeight, String buttonText) {
        ImageIcon image = new ImageIcon(Objects.requireNonNull(this.getClass().getResource(imagePath)));
        image = resizeImageIcon(image, buttonWidth,buttonHeight);

        JButton outputButton = new JButton();
        outputButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        outputButton.setIcon(image);
        outputButton.setMargin(new Insets(0,0,0,0));

        if (buttonText != null) {
            outputButton.setText(buttonText);
            outputButton.setBorderPainted(false);
            outputButton.setFont(new Font("monospaced",Font.BOLD, 25));
            outputButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            outputButton.setHorizontalTextPosition(SwingConstants.CENTER);
            outputButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight + 100));
        }

        return outputButton;
    }

    /**
     * Change image of button Image. Used to update the hand card panel in player frame.
     * @param imagePath Relative path to image file
     * @param button JButton object that need to be changed
     * @param buttonWidth new width to be set
     * @param buttonHeight new height to be set
     */
    public void changeButtonImage(String imagePath, JButton button,int buttonWidth, int buttonHeight) {
        ImageIcon image = new ImageIcon(Objects.requireNonNull(this.getClass().getResource(imagePath)));
        image = resizeImageIcon(image, buttonWidth,buttonHeight);

        button.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        button.setIcon(image);
        button.setMargin(new Insets(0,0,0,0));
        button.setPreferredSize(new Dimension(buttonWidth,buttonHeight + 100));
    }

    /**
     * Blank panel initialization function. Often used to leave blank space between two visible panels.
     * @param width width of blank panel
     * @param height height of blank panel
     * @return JPanel object created
     */
    public JPanel initBlankPanel(int width, int height) {
        JPanel blankPanel = new JPanel();
        blankPanel.setBackground(Color.white);
        blankPanel.setPreferredSize(new Dimension(width, height));
        return blankPanel;
    }

    /**
     * Add red border to highlight a button
     * @param button JButton that need to be highlighted
     */
    public void highlightButton(JButton button) {
        button.setBorder(BorderFactory.createLineBorder(Color.red, 10));
    }
}
