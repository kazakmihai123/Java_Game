package entities;

import graphics.GamePanel;
import utils.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.tileSize;

public class FinalBoss extends Entity {

    private final int maxHP = 200;
    private int currentHP = maxHP;
    private int phase = 1;
    private final double detectionRange = tileSize * 10; // ~320px
    private boolean rageModeActivated = false;

    public FinalBoss(double spawnX, double spawnY) {
        this.worldX = spawnX;
        this.worldY = spawnY;

        this.speed = 2;

        res = new ResourceLoader("/enemys/boss/mage-dark.png", 4, 3); // Înlocuiește cu sprite-ul tău

        solidArea = new Rectangle(0, -16, 64, 120);
    }

    public void update(GamePanel gp) {
        collisionOn = false;

        double dx = gp.player.worldX - worldX;
        double dy = gp.player.worldY - worldY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        double nextX = worldX;
        double nextY = worldY;

        // Rage Mode logic
        if (currentHP <= maxHP / 2 && !rageModeActivated) {
            rageModeActivated = true;
            phase = 2;
            speed += 1;
            System.out.println("FinalBoss RAGE MODE!");
        }

        if (distance <= detectionRange) {
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    currentAnimationState = AnimationState.WALKING_RIGHT;
                    nextX += speed;
                } else {
                    currentAnimationState = AnimationState.WALKING_LEFT;
                    nextX -= speed;
                }
            } else {
                if (dy > 0) {
                    currentAnimationState = AnimationState.WALKING_DOWN;
                    nextY += speed;
                } else {
                    currentAnimationState = AnimationState.WALKING_UP;
                    nextY -= speed;
                }
            }
        }

        double originalX = worldX;
        double originalY = worldY;

        worldX = nextX;
        worldY = nextY;

        gp.collisionChecker.checkTile(this);

        if (collisionOn) {
            worldX = originalX;
            worldY = originalY;
        }

        // ATAC
        if (getHitbox().intersects(gp.player.getHitbox())) {
            if (phase == 2 && Math.random() < 0.3) {
                System.out.println("FinalBoss ATAC SPECIAL!");
                gp.player.takeDamage(30);
            } else {
                gp.player.takeDamage(15);
            }
        }

        updateAnimation(3);
    }

    public void draw(Graphics g, Player p) {
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(new Color(255, 0, 0, 150)); // roșu semi-transparent
//        Rectangle hitbox = getHitbox();
//
//        // Convertește worldX/Y la screenX/Y ca să-l vezi corect
//        double screenXHitbox = worldX - p.worldX + p.screenX + solidArea.x;
//        double screenYHitbox = worldY - p.worldY + p.screenY + solidArea.y;
//
//        g2d.drawRect((int) screenXHitbox, (int) screenYHitbox, solidArea.width, solidArea.height);

        double screenX = worldX - p.worldX + p.screenX;
        double screenY = worldY - p.worldY + p.screenY;

        int animationRow = switch (currentAnimationState) {
            case WALKING_UP -> 0;
            case WALKING_RIGHT -> 1;
            case WALKING_DOWN -> 2;
            case WALKING_LEFT -> 3;
            default -> 2;
        };

        BufferedImage img;
        
        img = res.getFrame(animationRow, currentFrame);

        if (currentAnimationState == AnimationState.IDLE)
            img = res.getFrame(2, 1);

        res.drawFrame(g, img, screenX, screenY, 3, tileSize);
    }

    public void takeDamage(int dmg) {
        currentHP -= dmg;
        if (currentHP < 0) {
            currentHP = 0;
            solidArea = new Rectangle(0, 0, 0, 0);
        }
    }

    public boolean isDead() {
        return currentHP <= 0;
    }

    public Rectangle getHitbox() {
        return new Rectangle((int) worldX + solidArea.x, (int) worldY + solidArea.y, solidArea.width, solidArea.height);
    }
}
