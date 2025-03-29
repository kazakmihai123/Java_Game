package graphics;

import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow(GamePanel gamePanel)
    {
        setTitle("Joc Java");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(gamePanel);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
