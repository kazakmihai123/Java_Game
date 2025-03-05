package graphics;

import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow()
    {
        setTitle("Joc Java");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
