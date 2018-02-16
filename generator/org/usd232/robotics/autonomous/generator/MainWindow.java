package org.usd232.robotics.autonomous.generator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import org.usd232.robotics.autonomous.generator.tools.Toolbar;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 4216453804213565036L;

    public static void main(String[] args) {
        new MainWindow();
    }

    public MainWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(640, 480));
        setTitle("Autonomous Generator");
        getContentPane().setLayout(new BorderLayout(0, 0));
        FieldView fieldView = new FieldView();
        getContentPane().add(fieldView, BorderLayout.CENTER);
        SideBar sidebar = new SideBar();
        getContentPane().add(sidebar, BorderLayout.WEST);
        Toolbar toolbar = new Toolbar(fieldView, sidebar);
        getContentPane().add(toolbar, BorderLayout.NORTH);
        setVisible(true);
    }
}
