package graphics;

import entities.Player;
import entities.Projectiles;
import inputs.KeyboardInput;
import tiles.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static utils.Constants.*;


public class GamePanel extends JPanel {

    TileManager tileManager = new TileManager();
    KeyboardInput keyboardInput = new KeyboardInput();
    Player player = new Player();

    ArrayList<Projectiles> projectiles = new ArrayList<>();

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setFocusable(true);
        setDoubleBuffered(true);
        requestFocus();
        addKeyListener(keyboardInput);
        addMouseListener(keyboardInput);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        tileManager.draw(g, player);

        player.draw(g);
        for (int i = 0; i < projectiles.size(); i++)
        {
            projectiles.get(i).draw(g, player);
        }
    }

    public void update()
    {
        if (keyboardInput.isEscapeClick()) { System.exit(0); }
        player.update(keyboardInput, this);
        projectiles.removeIf(p -> !p.getActive());
        projectiles.forEach(Projectiles::update);
    }

    public void addProj(int dmg, double playerX, double playerY, int cursorX, int cursorY)
    {
        projectiles.add(new Projectiles(dmg, playerX, playerY, cursorX, cursorY));
    }

}
