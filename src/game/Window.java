package game;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Window extends Canvas {

    private JFrame frame;

    public Window(int width, int height, String title, BaseGame game) {
        frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.requestFocus();

        frame.add(game);
        frame.setVisible(true);
        game.requestFocus();
        game.start();
    }

    public void close() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
