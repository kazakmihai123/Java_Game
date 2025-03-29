package inputs;

import javax.swing.*;
import java.awt.event.*;

public class KeyboardInput extends KeyAdapter implements MouseListener {

    private boolean
            upPressed,
            downPressed,
            leftPressed,
            rightPressed,
            leftClickPressed,
            leftClickHandled,
            escapeClick;

    private int mouseX, mouseY;

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_S:
                downPressed = true;
                break;
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            case KeyEvent.VK_ESCAPE:
                escapeClick = true;
                break;
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
            case KeyEvent.VK_ESCAPE:
                escapeClick = true;
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
