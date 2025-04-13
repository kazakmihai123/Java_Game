package utils;

import entities.Slime;
import graphics.GamePanel;

import java.awt.*;
import java.util.ArrayList;

import static utils.Constants.tileSize;

public class EnemySetter {

    GamePanel gp;
    public ArrayList<Slime> slimes = new ArrayList<>();

    public EnemySetter(GamePanel gp) {
        this.gp = gp;
    }

    public void load(int currLevel) {
        slimes.clear(); // curățăm inamicii existenți

        switch (currLevel) {
            case 1:
                slimes.add(new Slime(13 * tileSize, 8 * tileSize)); // coordonatele sunt în pixeli, țin cont de tileSize
                slimes.add(new Slime(20 * tileSize, 10 * tileSize));
                break;

            case 2:
                slimes.add(new Slime(15 * tileSize, 18 * tileSize));
                break;

            default:
                break;
        }
    }

    public void update() {
        slimes.removeIf(Slime::isDead);
        for (Slime slime : slimes) {
            slime.update(gp);
        }
    }

    public void draw(Graphics g) {
        for (Slime slime : slimes) {
            slime.draw(g, gp.player);
        }
    }
}
