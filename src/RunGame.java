import gameview.GamePanel;
import guiframework.DisplayPanel;

import javax.swing.*;
import java.awt.*;

public class RunGame
{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RunGame::createAndShowGui);
    }

    private static void createAndShowGui()
    {
        JFrame frame = new JFrame();
        frame.setTitle("COP4331 - S18 - OOP There It Is - Iteration 3");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GamePanel panel = new GamePanel(frame.getSize());
        panel.setBackground(Color.BLACK);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.validate();
        frame.setVisible(true);

        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem newGame = new JMenuItem("Start New Game");
        newGame.addActionListener(e -> {
            panel.startNewGame();
        });

        menu.add(newGame);

        JMenuItem loadGame = new JMenuItem("Load Game");
        loadGame.addActionListener(e ->
        {
            panel.loadGame();
        });

        menu.add(loadGame);

        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(e ->
        {
            panel.saveGame();
        });

        menu.add(saveGame);

        JMenuItem resetCamera = new JMenuItem("Reset Camera");

        resetCamera.addActionListener(e -> {
            panel.resetCamera();
        });

        menu.add(resetCamera);

        bar.add(menu);
        frame.setJMenuBar(bar);
    }
}
