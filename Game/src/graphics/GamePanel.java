package graphics;

import collision.CollisionChecker;
import entities.Player;
import entities.Projectiles;
import entities.Skeleton;
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
    public TileManager tileManager = new TileManager(this);
    /// KEYBOARD
    KeyboardInput keyboardInput = new KeyboardInput(this);
    /// PLAYER
    public Player player; // Player
    /// COLLISIONS
    public CollisionChecker collisionChecker = new CollisionChecker(this); // Collisions
    /// PROJECTILES
    public ArrayList<Projectiles> projectiles = new ArrayList<>();
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

        gameState = endingState;
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

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
                    player.takeDamage(15);
                }
            }
            for (Skeleton s : enemySetter.skeletons) {
                if (s.getHitbox().intersects(player.getHitbox())) {
                    player.takeDamage(30);
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
                            boolean wasDead = s.isDead();
                            s.takeDamage(p.getDmg());
                            if (!wasDead && s.isDead()) {
                                ui.setScore(ui.getScore() + 50); // sau orice punctaj vrei
                            }
                            p.setInactive();
                        }
                    }
                    for (Skeleton s : enemySetter.skeletons) {
                        if (p.getActive() && s.getHitbox().intersects(p.getHitbox())) {
                            boolean wasDead = s.isDead();
                            s.takeDamage(p.getDmg());
                            if (!wasDead && s.isDead()) {
                                ui.setScore(ui.getScore() + 100); // alt scor pentru skeleton?
                            }
                            p.setInactive();
                        }
                    }
                    if (p.getActive() && enemySetter.finalBoss != null && enemySetter.finalBoss.getHitbox().intersects(p.getHitbox())) {
                        boolean wasDead = enemySetter.finalBoss.isDead();
                        enemySetter.finalBoss.takeDamage(p.getDmg());

                        p.setInactive();

                        if (!wasDead && enemySetter.finalBoss.isDead()) {
                            ui.setScore(ui.getScore() + 1000);
                            gameState = endingState;
                            ui.commandCnt = 0;
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
