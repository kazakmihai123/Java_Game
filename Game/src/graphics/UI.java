package graphics;

import entities.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.*;

public class UI {

    private BufferedImage healthBarImage;

    // Exemplu de scor
    private int score = 0;

    // Font pentru text
    private final Font uiFont = new Font("Arial", Font.BOLD, 20);
    private Font ubuntuFont;
    private Font underDogFont;

    // MENU
    public int commandCnt = 0;

    public UI() {

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Ubuntu-Light.ttf");
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, is);
            ubuntuFont = baseFont.deriveFont(Font.BOLD, 20f); // poți schimba mărimea aici
        } catch (Exception e) {
            e.printStackTrace();
            ubuntuFont = new Font("SansSerif", Font.BOLD, 20); // fallback
        }
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Underdog/Underdog-Regular.ttf");
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, is);
            underDogFont = baseFont.deriveFont(Font.BOLD, 20f); // poți schimba mărimea aici
        } catch (Exception e) {
            e.printStackTrace();
            underDogFont = new Font("SansSerif", Font.BOLD, 20); // fallback
        }

        try {
            InputStream is = getClass().getResourceAsStream("/mage/hp_bar/player_health_bar.png");
            healthBarImage = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void draw(Graphics g, Player player, GamePanel gp) {
        Graphics2D g2 = (Graphics2D) g;

        // Setare font și culoare
        g2.setFont(underDogFont);

        if (gp.gameState == titleState) {
            drawTitleScreen(g2);
        } else if (gp.gameState == playState || gp.gameState == pauseState){
            g2.setColor(Color.WHITE);

            // Exemplu: Desenare scor
            g2.drawString("Score: " + score, 20, 30);

            // Exemplu: Desenare bară de viață
            drawHealthBar(g2, player);
            drawCooldownBar(g2, player);

            if (gp.gameState == pauseState)
                drawPauseScreen(g2);
        }
        else if (gp.gameState == deadState)
        {
            drawDeadScreen(g2);
        }
    }

    private void drawPauseScreen(Graphics2D g) {
        g.setFont(underDogFont.deriveFont(60f)); // 30 este dimensiunea în puncte
        g.setColor(new Color(20, 20, 20, 100));
        g.fillRect(0, 0, screenWidth, screenHeight);
        g.setColor(Color.white);
        g.drawString("PAUSE", screenWidth / 2 - 100, screenHeight / 2);
    }

    private void drawTitleScreen(Graphics2D g) {

        // BACKGROUND
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, screenWidth, screenHeight);

        // TITLE NAME
        g.setFont(underDogFont.deriveFont(60f)); // PLAIN, BOLD sau ITALIC

        String text = "Pocalul Focului";
        int x = getCenteredX(text, g);
        int y = screenHeight / 4;

        // SHADOW
        g.setColor(Color.black);
        g.drawString(text, x + 5, y + 5);

        g.setColor(Color.red);
        g.drawString(text, x, y);

        // MENU
        g.setFont(underDogFont.deriveFont(48f));
        g.setColor(Color.white);

        text = "NEW GAME";
        x = getCenteredX(text, g);
        y = getCenteredY(g);
        g.drawString(text, x, y);
        if (commandCnt == 0)
        {
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);

            g.drawString(">", x - 48, y);
            g.drawString("<", x + textWidth + 24, y);
        }

        text = "LOAD GAME";
        x = getCenteredX(text, g);
        y = getCenteredY(g);
        g.drawString(text, x, y + 64);
        if (commandCnt == 1)
        {
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);

            g.drawString(">", x - 48, y + 64);
            g.drawString("<", x + textWidth + 24, y + 64);
        }

        text = "QUIT";
        x = getCenteredX(text, g);
        y = getCenteredY(g);
        g.drawString(text, x, y + 128);
        if (commandCnt == 2)
        {
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);

            g.drawString(">", x - 48, y + 128);
            g.drawString("<", x + textWidth + 24, y + 128);
        }
    }

    private void drawDeadScreen(Graphics2D g) {
        // TITLE
        String text = "You are Dead !";
        g.setFont(underDogFont.deriveFont(60f));

        int x = getCenteredX(text, g);
        int y = getCenteredY(g);

        // SHADOW
        g.setColor(Color.black);
        g.drawString(text, x + 3, y + 3);

        g.setColor(Color.red);
        g.drawString(text, x, y);

        // RESTART BUTTON
        g.setFont(underDogFont.deriveFont(32f));
        text = "RESTART";
        g.setColor(Color.gray);
        x = getCenteredX(text, g);
        y += 160;
        g.drawString(text, x, y);
        if (commandCnt == 0) // pentru vizualiazarea pozitiei
        {
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);

            g.drawString(">", x - 32, y);
            g.drawString("<", x + textWidth + 20, y);
        }

        // EXIT BUTTON
        text = "EXIT";
        x = getCenteredX(text, g);
        y += 96;
        g.drawString(text, x, y);
        if (commandCnt == 1)
        {
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);

            g.drawString(">", x - 28, y);
            g.drawString("<", x + textWidth + 12, y);
        }

    }

    private void drawHealthBar(Graphics2D g2, Player player) {

        Point p = new Point(20, 50);
        int width = 300;
        int height = 50;
        double healthPercent = (double) player.getCurrentHP() / player.getMaxHP();
        int filledWidth = (int) ( (width - 70) * healthPercent);

        // Desenează bara roșie proporțională
        g2.setColor(new Color(116, 0, 0, 255));
        g2.fillRect(p.x + 35, p.y + 8, filledWidth, height - 20); // cu margini interioare

        // Desenează imaginea de fundal
        g2.drawImage(healthBarImage, p.x, p.y, width, height, null);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCenteredX(String text, Graphics2D g2) {
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        return (screenWidth - textWidth) / 2;
    }

    public int getCenteredY(Graphics2D g2) {
        FontMetrics fm = g2.getFontMetrics();
        int textHeight = fm.getHeight();
        return (screenHeight - textHeight) / 2 + fm.getAscent();
    }

    private void drawCooldownBar(Graphics2D g2, Player player) {
        int barWidth = 100;
        int barHeight = 20;
        int x = screenWidth - barWidth - 20;
        int y = screenHeight - barHeight - 20;

        long cooldown = player.getShootCooldown();
        long timePassed = player.getTimeSinceLastShot();
        float percent = Math.min((float) timePassed / cooldown, 1.0f);

        // Fundal
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x, y, barWidth, barHeight);

        // Umplere
        g2.setColor(new Color(255, 140, 0)); // portocaliu aprins
        g2.fillRect(x, y, (int) (barWidth * percent), barHeight);

        // Contur
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, barWidth, barHeight);

        // Text opțional
        g2.setFont(ubuntuFont.deriveFont(14f));
        g2.drawString("Cooldown", x + 10, y - 5);
    }

}
