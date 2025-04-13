package graphics;

import collision.CollisionChecker;
import entities.Player;
import entities.Projectiles;
import entities.Slime;
import inputs.KeyboardInput;
import objects.ObjectManager;
import tiles.TileManager;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static utils.Constants.*;


public class GamePanel extends JPanel {

    /// TILES M.
    public TileManager tileManager = new TileManager();
    /// KEYBOARD
    KeyboardInput keyboardInput = new KeyboardInput(this);
    /// PLAYER
    public Player player; // Player
    /// COLLISIONS
    public CollisionChecker collisionChecker = new CollisionChecker(this); // Collisions
    /// PROJECTILES
    ArrayList<Projectiles> projectiles = new ArrayList<>();
    /// TURRETS
    public TurretSetter turretSetter = new TurretSetter(this);
    /// UI
    public UI ui = new UI();
    /// GAME STATE
    public int gameState;
    /// ENEMY
    public EnemySetter enemySetter = new EnemySetter(this);
    /// OBJECTS
    public ObjectManager objectManager = new ObjectManager(this);
    /// LEVELS
    public int currentLevel = 1;
    public LevelLoader levelLoader = new LevelLoader(this);

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setFocusable(true);
        requestFocus();
        addKeyListener(keyboardInput);
        addMouseListener(keyboardInput);
        setDoubleBuffered(true);

        gameState = titleState;

//        String nextMap = "/maps/map0" + currentLevel + ".txt";
//        levelLoader.loadLevel(nextMap);
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameState == playState || gameState == pauseState){
            tileManager.draw(g, player);

            player.draw(g);

            for (Projectiles projectile : projectiles) {
                projectile.draw(g, player);
            }

            enemySetter.draw(g);

            objectManager.draw((Graphics2D) g, player);

            if (player.getCurrentHP() == 0)
                gameState = deadState;
        }

        ui.draw(g, player, this);
    }

    public void update() {
        if (gameState == playState)
        {
            player.update(keyboardInput, this);

            enemySetter.update();

            // Coliziune slime <-> player
            for (Slime s : enemySetter.slimes) {
                if (s.getHitbox().intersects(player.getHitbox())) {
                    player.takeDamage(10);
                }
            }

            projectiles.removeIf(p -> !p.getActive());
            for (Projectiles p : projectiles) {
                p.update(this);
            }

            turretSetter.update();

            // Proiectile
            for (Projectiles p : projectiles) {
                if (p.getFromWho()) {
                    for (Slime s : enemySetter.slimes) {
                        if (p.getActive() && s.getHitbox().intersects(p.getHitbox())) {
                            s.takeDamage(p.getDmg());
                            p.setInactive();
                        }
                    }
                } else {
                    if (p.getActive() && player.getHitbox().intersects(p.getHitbox())) {
                        player.takeDamage(p.getDmg());
                        p.setInactive();
                    }
                }
            }

            objectManager.checkObjectCollision(player);
        }
    }

    public void addProj(int dmg, double playerX, double playerY, int cursorX, int cursorY, boolean fromPlayer) {
        projectiles.add(new Projectiles(dmg, playerX, playerY, cursorX, cursorY, fromPlayer));
    }

    public void loadNextLevel() {
        currentLevel++;
        String nextMap = "/maps/map0" + currentLevel + ".txt";
        new LevelLoader(this).loadLevel(nextMap);
    }
}
