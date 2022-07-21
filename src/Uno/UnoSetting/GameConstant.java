/**
 * This file is part of Uno
 *
 * Class that contains all constants used in game.
 * Including numbers,color,type of cards, Player limits etc.
 *
 */
package Uno.UnoSetting;

public class GameConstant {

    public enum CardColor {
        Red, Yellow, Green, Blue;
    }

    public enum CardType {
        Normal, Skip, Reverse, DrawTwo, DrawFour, Wild;
    }
    /**
    * CardNumber class for cards that have number. For each element ex. Zero.
    * Zero(numPerColor, representNumber) is its constructor
    * This class is used to set up draw pile initially.
    */
    public enum CardNumber {
        Zero(1,0),One(2,1),
        Two(2,2), Three(2,3),
        Four(2,4),Five(2,5),
        Six(2,6),Seven(2,7),
        Eight(2,8),Nine(2,9);

        private final int numPerColor;
        private final int representNumber;

        CardNumber(int numPerColor, int representNumber) {
            this.numPerColor = numPerColor;
            this.representNumber = representNumber;
        }

        public int getNumPerColor() {
            return numPerColor;
        }

        public int getRepresentNumber() {
            return representNumber;
        }
    }

    public static int WILD_CARD_NUM = 4;

    public static int DRAW_FOUR_CARD_NUM = 4;

    public static int SKIP_CARD_PER_COLOR = 2;

    public static int REVERSE_CARD_PER_COLOR = 2;

    public static int DRAW_TWO_CARD_PER_COLOR = 2;

    public static int DECK_MAX_CAPACITY = 108;
    /**
     * See order in GameState class. Represent order of play.
     */
    public static int CLOCK_WISE_DIRECTION = 1;
    /**
     * See order in GameState class. Represent order of play.
     */
    public static int COUNTER_CLOCK_WISE_DIRECTION = -1;

    public static int GAME_MAX_PLAYER_COUNT = 9;

    public static int GAME_MIN_PLAYER_COUNT = 2;

    public static int PLAYER_INIT_CARD_COUNT = 7;

}
