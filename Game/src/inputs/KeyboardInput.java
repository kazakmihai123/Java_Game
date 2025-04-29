package inputs;

import graphics.GamePanel;
import utils.GameSaveManager;

import javax.swing.*;
import java.awt.event.*;

import static utils.Constants.*;

public class KeyboardInput extends KeyAdapter implements MouseListener {

    GamePanel gp;

    private boolean
            upPressed,
            downPressed,
            leftPressed,
            rightPressed,
            leftClickPressed,
            leftClickHandled,
            escapeClick;

    private int mouseX, mouseY;

    // CONSTRUCTOR
    public KeyboardInput(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gp.gameState == titleState) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    gp.ui.commandCnt--;
                    if (gp.ui.commandCnt < 0)
                        gp.ui.commandCnt = 2;
                    break;
                case KeyEvent.VK_S:
                    gp.ui.commandCnt++;
                    if (gp.ui.commandCnt > 2)
                        gp.ui.commandCnt = 0;
                    break;
                case KeyEvent.VK_ENTER:
                    if (gp.ui.commandCnt == 0)
                    {
                        String nextMap = "/maps/map0" + gp.currentLevel + ".txt";
                        gp.levelLoader.loadLevel(nextMap);
                        gp.gameState = playState;
                    }
                    else if (gp.ui.commandCnt == 1)
                    {
                        //to do load
                        GameSaveManager.load(gp);
                        gp.gameState = playState;
                    }
                    else if (gp.ui.commandCnt == 2)
                        System.exit(0);
                    break;
            }
        }
        else if (gp.gameState == deadState) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    gp.ui.commandCnt--;
                    if (gp.ui.commandCnt < 0)
                        gp.ui.commandCnt = 1;
                    break;
                case KeyEvent.VK_S:
                    gp.ui.commandCnt++;
                    if (gp.ui.commandCnt > 1)
                        gp.ui.commandCnt = 0;
                    break;
                case KeyEvent.VK_ENTER:
                    if (gp.ui.commandCnt == 0)
                    {
                        gp.currentLevel = 1;
                        String nextMap = "/maps/map0" + gp.currentLevel + ".txt";
                        gp.levelLoader.loadLevel(nextMap);
                        gp.gameState = playState;
                    }
                    else if (gp.ui.commandCnt == 1)
                        System.exit(0);
                    break;
            }
        }
        else if (gp.gameState == endingState) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    gp.ui.commandCnt--;
                    if (gp.ui.commandCnt < 0)
                        gp.ui.commandCnt = 1;
                    break;
                case KeyEvent.VK_S:
                    gp.ui.commandCnt++;
                    if (gp.ui.commandCnt > 1)
                        gp.ui.commandCnt = 0;
                    break;
                case KeyEvent.VK_ENTER:
                    if (gp.ui.commandCnt == 0)
                    {
                        gp.gameState = titleState;
                    }
                    else if (gp.ui.commandCnt == 1)
                        System.exit(0);
                    break;
            }
        }
        else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    if (gp.gameState == playState) {
                        upPressed = true;
                    }
                    else {
                        gp.ui.commandCnt--;
                        if (gp.ui.commandCnt < 0)
                            gp.ui.commandCnt = 1;
                    }
                    break;
                case KeyEvent.VK_S:
                    if (gp.gameState == playState) {
                        downPressed = true;
                    }
                    else {
                        gp.ui.commandCnt++;
                        if (gp.ui.commandCnt > 1)
                            gp.ui.commandCnt = 0;
                    }
                    break;
                case KeyEvent.VK_A:
                    leftPressed = true;
                    break;
                case KeyEvent.VK_D:
                    rightPressed = true;
                    break;
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
                case KeyEvent.VK_P:
                    if (gp.gameState == playState) {
                        gp.gameState = pauseState;
                        gp.ui.commandCnt = 0;
                    }
                    else if (gp.gameState == pauseState)
                        gp.gameState = playState;
                    break;
                case KeyEvent.VK_K: // Save
                    GameSaveManager.save(gp);
                    break;
                case KeyEvent.VK_ENTER:
                    if (gp.gameState == pauseState) {
                        if (gp.ui.commandCnt == 0) {
                            GameSaveManager.save(gp);
                        } else if (gp.ui.commandCnt == 1)
                            System.exit(0);
                    }
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isEscapeClick() {
        return escapeClick;
    }

    public boolean consumeLeftClick() {
        if (leftClickPressed && !leftClickHandled) {
            leftClickHandled = true;
            return true;
        }
        return false;
    }

    public int getMouseX() {
        return mouseX;
    }
    public int getMouseY() {
        return mouseY;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            leftClickPressed = true;
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }
    @Override public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            leftClickPressed = false;
            leftClickHandled = false; // resetăm la eliberare
        }
    }
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
