package collision;

import entities.*;
import graphics.GamePanel;

import static utils.Constants.*;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = (int)entity.worldX + entity.solidArea.x;
        int entityRightWorldX = (int)entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = (int)entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = (int)entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / tileSize;
        int entityRightCol = entityRightWorldX / tileSize;
        int entityTopRow = entityTopWorldY / tileSize;
        int entityBottomRow = entityBottomWorldY / tileSize;

        int tileNum1, tileNum2;

        if (entity instanceof Player || entity instanceof Slime || entity instanceof Skeleton) {
            switch (entity.currentAnimationState) {
                case Entity.AnimationState.WALKING_UP:
                    entityTopRow = (entityTopWorldY - entity.speed) / tileSize;
                    tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                    if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    break;
                case Entity.AnimationState.WALKING_DOWN:
                    entityBottomRow = (entityBottomWorldY + entity.speed) / tileSize;
                    tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                    tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                    if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    break;
                case Entity.AnimationState.WALKING_LEFT:
                    entityLeftCol = (entityLeftWorldX - entity.speed) / tileSize;
                    tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                    if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    break;
                case Entity.AnimationState.WALKING_RIGHT:
                    entityRightCol = (entityRightWorldX + entity.speed) / tileSize;
                    tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                    tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                    if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    break;
            }
        }
        else if (entity instanceof Projectiles)
        {
            // Centrul proiectilului
            int centerX = (int) (entity.worldX + entity.solidArea.x + entity.solidArea.width / 2);
            int centerY = (int) (entity.worldY + entity.solidArea.y + entity.solidArea.height / 2);

            int col = centerX / tileSize;
            int row = centerY / tileSize;

            // Verificăm dacă e în afara hărții
            if (col < 0 || row < 0 || col >= maxWorldCol || row >= maxWorldRow) {
                entity.collisionOn = true;
                return;
            }

            int tileNum = gp.tileManager.mapTileNum[col][row];
            if (tileNum != -1 && gp.tileManager.tiles[tileNum].collision) {
                entity.collisionOn = true;
            }
        }
    }
}