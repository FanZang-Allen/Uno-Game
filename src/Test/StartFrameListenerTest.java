package Test;

import Uno.Gui.ActionListener.StartFrameListener;
import Uno.Gui.StartFrame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StartFrameListenerTest {

    @Test
    void testSpinnerAndStartButton() {
        StartFrame startFrame = new StartFrame();
        startFrame.setVisible(false);
        StartFrameListener listener = startFrame.getListener();
        listener.setTimeDelay(3);
        startFrame.normalPlayerSpinner.setValue(0);
        startFrame.baselineAISpinner.setValue(5);
        startFrame.strategicAISpinner.setValue(6);

        /* Game should not start here since number of player > 9*/
        startFrame.startButton.doClick();
        assertNull(listener.getGame());

        /* Game should start here since number of player < 9*/
        listener.setTimeDelay(0);
        startFrame.baselineAISpinner.setValue(0);
        startFrame.startButton.doClick();
        assertNotNull(listener.getGame());
    }
}