package objects;

import entities.Player;
import graphics.GamePanel;

import java.awt.*;
import java.util.ArrayList;

import static utils.Constants.tileSize;

public class ObjectManager {

    GamePanel gp;
    public ArrayList<SuperObject> objects = new ArrayList<>();

    public ObjectManager(GamePanel gp) {
        this.gp = gp;
    }

    public void addObject(SuperObject obj, int worldX, int worldY) {
        obj.worldX = worldX;
        obj.worldY = worldY;
        objects.add(obj);
    }

    public void draw(Graphics2D g2, Player player) {
        for (SuperObject obj : objects) {
            if (obj != null) {
                double screenX = obj.worldX - player.worldX + player.screenX;
                double screenY = obj.worldY - player.worldY + player.screenY;

                if (obj.worldX + tileSize > player.worldX - player.screenX &&
                        obj.worldX - tileSize < player.worldX + player.screenX &&
                        obj.worldY + tileSize > player.worldY - player.screenY &&
                        obj.worldY - tileSize < player.worldY + player.screenY) {

                    obj.draw(g2, tileSize, screenX, screenY);
                }
            }
        }
    }

    public void checkObjectCollision(Player player) {
        for (SuperObject obj : objects) {
            if (obj != null && obj.collision) {
                Rectangle playerArea = new Rectangle((int)player.worldX + player.solidArea.x,
                        (int)player.worldY + player.solidArea.y,
                        player.solidArea.width,
                        player.solidArea.height);

                Rectangle objArea = new Rectangle(obj.worldX, obj.worldY, tileSize, tileSize);

                if (playerArea.intersects(objArea)) {
                    if ("tiles/map1/door".equals(obj.name)) {
                        gp.loadNextLevel();
                    }
                }
            }
        }
    }
}
