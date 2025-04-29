package utils;

import entities.FinalBoss;
import entities.Skeleton;
import entities.Slime;
import graphics.GamePanel;

import java.awt.*;
import java.util.ArrayList;

import static utils.Constants.tileSize;

public class EnemySetter {

    GamePanel gp;
    public ArrayList<Slime> slimes = new ArrayList<>();
    public ArrayList<Skeleton> skeletons = new ArrayList<>();
    public FinalBoss finalBoss;

    public EnemySetter(GamePanel gp) {
        this.gp = gp;
    }

    public void load(int currLevel) {
        slimes.clear(); // curățăm inamicii existenți
        skeletons.clear();

        switch (currLevel) {
            case 1:
                //slimes.add(new Slime(13 * tileSize, 8 * tileSize)); // coordonatele sunt în pixeli, țin cont de tileSize
                //slimes.add(new Slime(20 * tileSize, 10 * tileSize));
                //skeletons.add(new Skeleton(12 * tileSize, 8 * tileSize));
                break;

            case 2:
                //slimes.add(new Slime(15 * tileSize, 18 * tileSize));
                skeletons.add(new Skeleton(27 * tileSize, 34 * tileSize));
                skeletons.add(new Skeleton(21 * tileSize, 32 * tileSize));
                skeletons.add(new Skeleton(3 * tileSize, 5 * tileSize));
                skeletons.add(new Skeleton(13 * tileSize, 10 * tileSize));
                skeletons.add(new Skeleton(4 * tileSize, 21 * tileSize));
                skeletons.add(new Skeleton(2 * tileSize, 42 * tileSize));
                skeletons.add(new Skeleton(20 * tileSize, 41 * tileSize));

                slimes.add(new Slime(tileSize, 9 * tileSize));
                slimes.add(new Slime(23 * tileSize, 11 * tileSize));
                break;

            case 3:
                finalBoss = new FinalBoss(9 * tileSize, 8 * tileSize);
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

        skeletons.removeIf(Skeleton::isDead);
        for (Skeleton skeleton : skeletons) {
            skeleton.update(gp);
        }

        if (finalBoss != null && !finalBoss.isDead()) {
            finalBoss.update(gp);
        }
    }

    public void draw(Graphics g) {
        for (Slime slime : slimes) {
            slime.draw(g, gp.player);
        }

        for (Skeleton skeleton : skeletons) {
            skeleton.draw(g, gp.player);
        }

        if (finalBoss != null && !finalBoss.isDead()) {
            finalBoss.draw(g, gp.player);
        }
    }
}
