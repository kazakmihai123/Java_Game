package entities;

import graphics.GamePanel;
import inputs.KeyboardInput;

import java.awt.*;

import static utils.Constants.*;

public class Turret extends Entity {

    int dmg = 10;

    public final int screenX;
    public final int screenY;

    private long lastShotTime = 0;
    private long shootCooldown;

    ShootDirection shootDirection;

    public Turret(int posX, int posY, ShootDirection shootDirection, long cooldown) {
        this.shootDirection = shootDirection;
        shootCooldown = cooldown;

        worldX = tileSize * posX;
        worldY = tileSize * posY;

        screenX = screenWidth / 2 - tileSize / 2;
        screenY = screenHeight / 2 - tileSize / 2;
    }

    public void update(GamePanel gp) {
        long now = System.currentTimeMillis();
        if (now - lastShotTime >= shootCooldown) {
            shootProjectile(gp);
            lastShotTime = now;
        }
    }

    private void shootProjectile(GamePanel gp) {
        double dirX = worldX + (double) (tileSize / 4);
        double dirY = worldY + (double) (tileSize / 4);
        switch(shootDirection)
        {
            case LEFT:
                dirX -= tileSize * 1000;
                break;
            case RIGHT:
                dirX += tileSize * 1000;
                break;
            case UP:
                dirY -= tileSize * 1000;
                break;
            case DOWN:
                dirY += tileSize * 1000;
                break;
            default:
                break;
        }
        gp.addProj(dmg, worldX + (double) (tileSize / 4), worldY + (double) (tileSize / 4), (int)dirX, (int)dirY, false);
    }

}
