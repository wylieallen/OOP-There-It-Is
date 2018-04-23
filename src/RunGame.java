import gameview.GamePanel;

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

        JMenuItem newSummonerGame = new JMenuItem("Start New Summoner Game");
        newSummonerGame.addActionListener(e -> {
            panel.startNewGame("Summoner");
        });

        menu.add(newSummonerGame);

        JMenuItem newSneakGame = new JMenuItem("Start New Sneak Game");
        newSneakGame.addActionListener(e -> {
            panel.startNewGame("Sneak");
        });

        menu.add(newSneakGame);

        JMenuItem newSmasherGame = new JMenuItem("Start New Smasher Game");
        newSmasherGame.addActionListener(e -> {
            panel.startNewGame("Smasher");
        });

        menu.add(newSmasherGame);

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
