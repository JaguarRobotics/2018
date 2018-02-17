package org.usd232.robotics.autonomous.generator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import org.usd232.robotics.autonomous.generator.model.GeneratorModel;
import org.usd232.robotics.autonomous.generator.tools.Toolbar;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 4216453804213565036L;

    public static void main(String[] args) {
        GeneratorModel model = new GeneratorModel();
        new MainWindow(model);
        SyncService.start(model, 1810);
    }

    public MainWindow(GeneratorModel model) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(640, 480));
        setTitle("Autonomous Generator");
        getContentPane().setLayout(new BorderLayout(0, 0));
        FieldView fieldView = new FieldView(model);
        getContentPane().add(fieldView, BorderLayout.CENTER);
        SideBar sidebar = new SideBar(model);
        getContentPane().add(sidebar, BorderLayout.WEST);
        PropertiesBar properties = new PropertiesBar(model);
        getContentPane().add(properties, BorderLayout.EAST);
        Toolbar toolbar = new Toolbar(model, fieldView, properties);
        getContentPane().add(toolbar, BorderLayout.NORTH);
        setVisible(true);
    }
}