package entities;

import graphics.GamePanel;
import utils.ResourceLoader;

import java.awt.*;

import static utils.Constants.*;

public class Slime extends Entity {

    private boolean movingLeft = true;
    private final int maxHP = 20;
    private int currentHP = maxHP;

    public Slime(double spawnX, double spawnY) {
        this.worldX = spawnX;
        this.worldY = spawnY;

        this.speed = 2;

        // Ex: spritesheet cu slime
        res = new ResourceLoader("/enemys/slimes/slime.png", 3, 3); // adaptează path-ul

        // Zona de coliziune
        solidArea = new Rectangle(10, 10, tileSize - 20, tileSize - 20);
    }

    public void update(GamePanel gp) {
        collisionOn = false;

        if (movingLeft) {
            currentAnimationState = AnimationState.WALKING_LEFT;
            worldX -= speed;
        } else {
            currentAnimationState = AnimationState.WALKING_RIGHT;
            worldX += speed;
        }

        gp.collisionChecker.checkTile(this);

        // Dacă s-a lovit de ceva, schimbă direcția și dă un pas înapoi
        if (collisionOn) {
            if (movingLeft) {
                worldX += speed;
            } else {
                worldX -= speed;
            }
            movingLeft = !movingLeft;
        }

        updateAnimation(3);
    }

    public void draw(Graphics g, Player p) {
        double screenX = worldX - p.worldX + p.screenX;
        double screenY = worldY - p.worldY + p.screenY;

        res.drawImage(g, (int) screenX, (int) screenY, 2, tileSize, animationRow(), currentFrame);
    }

    private int animationRow() {
        return switch (currentAnimationState) {
            case WALKING_LEFT -> 1;
            case WALKING_RIGHT -> 1;
            default -> 2;
        };
    }

    public boolean isDead() {
        return currentHP <= 0;
    }

    public void takeDamage(int dmg) {
        currentHP -= dmg;
    }

    public Rectangle getHitbox() {
        return new Rectangle((int) worldX + solidArea.x, (int) worldY + solidArea.y, solidArea.width, solidArea.height);
    }
}
