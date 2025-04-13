package entities;

import graphics.GamePanel;
import utils.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

public class Entity {

    public double worldX, worldY;
    public int speed;

    ResourceLoader res; /// Pentru sprites
    public AnimationState currentAnimationState = AnimationState.IDLE;

    public enum AnimationState {
        IDLE,
        WALKING_UP,
        WALKING_DOWN,
        WALKING_LEFT,
        WALKING_RIGHT,
        DYING
    }

    int currentFrame = 0;
    long timer = System.currentTimeMillis();
    long frameDelay = 150; // milisecunde între cadre (ajustezi după preferință)

    // Collision
    public Rectangle solidArea;
    public boolean collisionOn = false;

    public void updateAnimation(int length){
        if(System.currentTimeMillis() - timer > frameDelay){
            currentFrame = (currentFrame + 1) % length;
            timer = System.currentTimeMillis();
        }
    }
}
