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

        DisplayPanel panel = new DisplayPanel();
        panel.setBackground(Color.BLACK);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.validate();
        frame.setVisible(true);
    }
}
