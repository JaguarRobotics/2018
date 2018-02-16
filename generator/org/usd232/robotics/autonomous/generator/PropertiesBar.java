package org.usd232.robotics.autonomous.generator;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.usd232.robotics.autonomous.AutonomousModel;
import org.usd232.robotics.autonomous.AutonomousRoute;
import org.usd232.robotics.autonomous.AutonomousStep;
import org.usd232.robotics.autonomous.CustomCommandParameter;
import org.usd232.robotics.autonomous.DriveParameter;
import org.usd232.robotics.autonomous.IAutonomousStepParameter;
import org.usd232.robotics.autonomous.SleepParameter;
import org.usd232.robotics.autonomous.TurnParameter;
import org.usd232.robotics.autonomous.generator.model.GeneratorModel;

public class PropertiesBar extends Container implements DocumentListener, ListSelectionListener {
    private static final long serialVersionUID = -1632296736445403910L;
    private JTextField        scaleField;
    private JTextField        startXField;
    private JTextField        startYField;
    private JTextField        timeField;
    private JTextField        distanceField;
    private JTextField        angleField;
    private JTextField        commandField;
    private JTextPane         parameterField;
    private JPanel            stepContainer;
    private GeneratorModel    model;
    private boolean           ignoreValueChanged;

    @Override
    public void insertUpdate(DocumentEvent e) {
        changedUpdate(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changedUpdate(e);
    }

    public float parseFloat(String str) {
        if (str.equals("")) {
            return 0;
        } else if (str.endsWith(".")) {
            str = str.substring(0, str.length() - 1);
        }
        return Float.parseFloat(str);
    }

    public short parseShort(String str) {
        if (str.equals("")) {
            return 0;
        } else {
            return Short.parseShort(str);
        }
    }

    public byte parseByte(String str) {
        if (str.equals("")) {
            return 0;
        } else {
            return Byte.parseByte(str);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        AutonomousModel auto = model.localDatabase.getRawElementAt(model.localDatabaseView.getSelectedIndex()).obj2;
        if (e.getDocument() == scaleField.getDocument()) {
            try {
                auto.setScale(parseFloat(scaleField.getText()));
            } catch (NumberFormatException ex) {
                EventQueue.invokeLater(()->scaleField.setText(Float.toString(auto.getScale())));
            }
        } else {
            AutonomousRoute route = model.versionList.getRawElementAt(model.versionListView.getSelectedIndex());
            if (e.getDocument() == startXField.getDocument()) {
                try {
                    route.setStartX(parseFloat(startXField.getText()));
                } catch (NumberFormatException ex) {
                    EventQueue.invokeLater(()->startXField.setText(Float.toString(route.getStartX())));
                }
            } else if (e.getDocument() == startYField.getDocument()) {
                try {
                    route.setStartX(parseFloat(startYField.getText()));
                } catch (NumberFormatException ex) {
                    EventQueue.invokeLater(()->startYField.setText(Float.toString(route.getStartX())));
                }
            } else {
                int i = model.stepListView.getSelectedIndex();
                if (i >= 0) {
                    AutonomousStep step = model.stepList.getRawElementAt(i);
                    IAutonomousStepParameter param = step.getGenericParameter();
                    boolean changed = false;
                    if (e.getDocument() == timeField.getDocument()) {
                        SleepParameter p = (SleepParameter) param;
                        short old = p.getMillis();
                        try {
                            p.setMillis(parseShort(timeField.getText()));
                            changed = changed || p.getMillis() != old;
                        } catch (NumberFormatException ex) {
                            EventQueue.invokeLater(()->timeField.setText(Short.toString(p.getMillis())));
                        }
                    } else if (e.getDocument() == distanceField.getDocument()) {
                        DriveParameter p = (DriveParameter) param;
                        float old = p.getDistance();
                        try {
                            p.setDistance(parseFloat(distanceField.getText()));
                            changed = changed || p.getDistance() != old;
                        } catch (NumberFormatException ex) {
                            EventQueue.invokeLater(()->distanceField.setText(Float.toString(p.getDistance())));
                        }
                    } else if (e.getDocument() == angleField.getDocument()) {
                        TurnParameter p = (TurnParameter) param;
                        float old = p.getAngle();
                        try {
                            p.setAngle(parseFloat(angleField.getText()) * (float) (Math.PI / 180.0));
                            changed = changed || p.getAngle() != old;
                        } catch (NumberFormatException ex) {
                            EventQueue.invokeLater(()->angleField
                                            .setText(Float.toString(p.getAngle() * (float) (180.0 / Math.PI))));
                        }
                    } else {
                        CustomCommandParameter p = (CustomCommandParameter) param;
                        if (e.getDocument() == commandField.getDocument()) {
                            byte old = p.getCommandID();
                            try {
                                p.setCommandID(parseByte(commandField.getText()));
                                changed = changed || p.getCommandID() != old;
                            } catch (NumberFormatException ex) {
                                EventQueue.invokeLater(()->commandField.setText(Byte.toString(p.getCommandID())));
                            }
                        } else if (e.getDocument() == parameterField.getDocument()) {
                            changed = changed || !parameterField.getText().equals(p.getParameter());
                            p.setParameter(parameterField.getText());
                        }
                    }
                    if (changed) {
                        EventQueue.invokeLater(()-> {
                            ignoreValueChanged = true;
                            int vi = model.stepListView.getSelectedIndex();
                            model.stepList.remove(i);
                            model.stepList.insert(null, i).setParameter(step.getGenericParameter());
                            model.stepListView.setSelectedIndex(vi);
                            ignoreValueChanged = false;
                        });
                    }
                }
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!ignoreValueChanged) {
            if (e.getSource() == model.localDatabaseView.list) {
                scaleField.setEnabled(!model.localDatabaseView.isSelectionEmpty());
            } else if (e.getSource() == model.versionListView.list) {
                startXField.setEnabled(!model.versionListView.isSelectionEmpty());
                startYField.setEnabled(!model.versionListView.isSelectionEmpty());
            } else if (e.getSource() == model.stepListView.list) {
                String name;
                if (model.stepListView.isSelectionEmpty()) {
                    name = "empty";
                } else {
                    AutonomousStep step = model.stepList.getRawElementAt(model.stepListView.getSelectedIndex());
                    switch (step.getType()) {
                        case CustomCommand: {
                            name = "custom";
                            CustomCommandParameter param = (CustomCommandParameter) step.getGenericParameter();
                            commandField.setText(Byte.toString(param.getCommandID()));
                            parameterField.setText(param.getParameter());
                            break;
                        }
                        case Drive: {
                            name = "drive";
                            DriveParameter param = (DriveParameter) step.getGenericParameter();
                            distanceField.setText(Float.toString(param.getDistance()));
                            break;
                        }
                        case Sleep: {
                            name = "sleep";
                            SleepParameter param = (SleepParameter) step.getGenericParameter();
                            timeField.setText(Short.toString(param.getMillis()));
                            break;
                        }
                        case Turn: {
                            name = "turn";
                            TurnParameter param = (TurnParameter) step.getGenericParameter();
                            angleField.setText(Float.toString(param.getAngle() * (float) (180.0 / Math.PI)));
                            break;
                        }
                        default: {
                            name = "empty";
                            break;
                        }
                    }
                }
                ((CardLayout) stepContainer.getLayout()).show(stepContainer, name);
            }
        }
    }

    public PropertiesBar(GeneratorModel model) {
        this.model = model;
        model.addListSelectionListener(this);
        setPreferredSize(new Dimension(320, 400));
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        JLabel lblGeneralParameters = new JLabel("General Parameters:");
        springLayout.putConstraint(SpringLayout.NORTH, lblGeneralParameters, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, lblGeneralParameters, 10, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, lblGeneralParameters, 440, SpringLayout.WEST, this);
        add(lblGeneralParameters);
        JPanel generalConfig = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, generalConfig, 6, SpringLayout.SOUTH, lblGeneralParameters);
        springLayout.putConstraint(SpringLayout.WEST, generalConfig, 10, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, generalConfig, 0, SpringLayout.EAST, lblGeneralParameters);
        add(generalConfig);
        GridBagLayout gbl_generalConfig = new GridBagLayout();
        gbl_generalConfig.columnWidths = new int[] { 81, 333, 0 };
        gbl_generalConfig.rowHeights = new int[] { 19, 19, 19, 0 };
        gbl_generalConfig.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        gbl_generalConfig.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
        generalConfig.setLayout(gbl_generalConfig);
        JLabel lblFieldScale = new JLabel("Field Scale:");
        GridBagConstraints gbc_lblFieldScale = new GridBagConstraints();
        gbc_lblFieldScale.anchor = GridBagConstraints.EAST;
        gbc_lblFieldScale.insets = new Insets(0, 0, 5, 5);
        gbc_lblFieldScale.gridx = 0;
        gbc_lblFieldScale.gridy = 0;
        generalConfig.add(lblFieldScale, gbc_lblFieldScale);
        scaleField = new JTextField();
        scaleField.setEnabled(false);
        GridBagConstraints gbc_scaleField = new GridBagConstraints();
        gbc_scaleField.anchor = GridBagConstraints.NORTH;
        gbc_scaleField.fill = GridBagConstraints.HORIZONTAL;
        gbc_scaleField.insets = new Insets(0, 0, 5, 0);
        gbc_scaleField.gridx = 1;
        gbc_scaleField.gridy = 0;
        generalConfig.add(scaleField, gbc_scaleField);
        scaleField.setColumns(10);
        JLabel lblStartingX = new JLabel("Starting X:");
        GridBagConstraints gbc_lblStartingX = new GridBagConstraints();
        gbc_lblStartingX.anchor = GridBagConstraints.EAST;
        gbc_lblStartingX.insets = new Insets(0, 0, 5, 5);
        gbc_lblStartingX.gridx = 0;
        gbc_lblStartingX.gridy = 1;
        generalConfig.add(lblStartingX, gbc_lblStartingX);
        startXField = new JTextField();
        startXField.setEnabled(false);
        GridBagConstraints gbc_startXField = new GridBagConstraints();
        gbc_startXField.anchor = GridBagConstraints.NORTH;
        gbc_startXField.fill = GridBagConstraints.HORIZONTAL;
        gbc_startXField.insets = new Insets(0, 0, 5, 0);
        gbc_startXField.gridx = 1;
        gbc_startXField.gridy = 1;
        generalConfig.add(startXField, gbc_startXField);
        startXField.setColumns(10);
        JLabel lblStartingY = new JLabel("Starting Y:");
        GridBagConstraints gbc_lblStartingY = new GridBagConstraints();
        gbc_lblStartingY.anchor = GridBagConstraints.EAST;
        gbc_lblStartingY.insets = new Insets(0, 0, 0, 5);
        gbc_lblStartingY.gridx = 0;
        gbc_lblStartingY.gridy = 2;
        generalConfig.add(lblStartingY, gbc_lblStartingY);
        startYField = new JTextField();
        startYField.setEnabled(false);
        GridBagConstraints gbc_startYField = new GridBagConstraints();
        gbc_startYField.anchor = GridBagConstraints.NORTH;
        gbc_startYField.fill = GridBagConstraints.HORIZONTAL;
        gbc_startYField.gridx = 1;
        gbc_startYField.gridy = 2;
        generalConfig.add(startYField, gbc_startYField);
        startYField.setColumns(10);
        stepContainer = new JPanel();
        springLayout.putConstraint(SpringLayout.WEST, stepContainer, 10, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, stepContainer, -10, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, stepContainer, 0, SpringLayout.EAST, lblGeneralParameters);
        add(stepContainer);
        stepContainer.setLayout(new CardLayout(0, 0));
        JPanel emptyConfig = new JPanel();
        stepContainer.add(emptyConfig, "empty");
        JPanel sleepConfig = new JPanel();
        stepContainer.add(sleepConfig, "sleep");
        GridBagLayout gbl_sleepConfig = new GridBagLayout();
        gbl_sleepConfig.columnWidths = new int[] { 0, 0, 0, 0 };
        gbl_sleepConfig.rowHeights = new int[] { 0, 0 };
        gbl_sleepConfig.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        gbl_sleepConfig.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        sleepConfig.setLayout(gbl_sleepConfig);
        JLabel lblTime = new JLabel("Time:");
        GridBagConstraints gbc_lblTime = new GridBagConstraints();
        gbc_lblTime.anchor = GridBagConstraints.EAST;
        gbc_lblTime.insets = new Insets(0, 0, 0, 5);
        gbc_lblTime.gridx = 0;
        gbc_lblTime.gridy = 0;
        sleepConfig.add(lblTime, gbc_lblTime);
        timeField = new JTextField();
        GridBagConstraints gbc_timeField = new GridBagConstraints();
        gbc_timeField.insets = new Insets(0, 0, 0, 5);
        gbc_timeField.fill = GridBagConstraints.HORIZONTAL;
        gbc_timeField.gridx = 1;
        gbc_timeField.gridy = 0;
        sleepConfig.add(timeField, gbc_timeField);
        timeField.setColumns(10);
        JLabel lblMs = new JLabel("ms");
        GridBagConstraints gbc_lblMs = new GridBagConstraints();
        gbc_lblMs.gridx = 2;
        gbc_lblMs.gridy = 0;
        sleepConfig.add(lblMs, gbc_lblMs);
        JLabel lblCommandParameters = new JLabel("Command Parameters:");
        springLayout.putConstraint(SpringLayout.NORTH, lblCommandParameters, 6, SpringLayout.SOUTH, generalConfig);
        springLayout.putConstraint(SpringLayout.NORTH, stepContainer, 6, SpringLayout.SOUTH, lblCommandParameters);
        JPanel driveConfig = new JPanel();
        stepContainer.add(driveConfig, "drive");
        GridBagLayout gbl_driveConfig = new GridBagLayout();
        gbl_driveConfig.columnWidths = new int[] { 0, 0, 0, 0 };
        gbl_driveConfig.rowHeights = new int[] { 0, 0 };
        gbl_driveConfig.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        gbl_driveConfig.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        driveConfig.setLayout(gbl_driveConfig);
        JLabel lblDistance = new JLabel("Distance:");
        GridBagConstraints gbc_lblDistance = new GridBagConstraints();
        gbc_lblDistance.anchor = GridBagConstraints.EAST;
        gbc_lblDistance.insets = new Insets(0, 0, 0, 5);
        gbc_lblDistance.gridx = 0;
        gbc_lblDistance.gridy = 0;
        driveConfig.add(lblDistance, gbc_lblDistance);
        distanceField = new JTextField();
        GridBagConstraints gbc_distanceField = new GridBagConstraints();
        gbc_distanceField.insets = new Insets(0, 0, 0, 5);
        gbc_distanceField.fill = GridBagConstraints.HORIZONTAL;
        gbc_distanceField.gridx = 1;
        gbc_distanceField.gridy = 0;
        driveConfig.add(distanceField, gbc_distanceField);
        distanceField.setColumns(10);
        JLabel lblIn = new JLabel("in");
        GridBagConstraints gbc_lblIn = new GridBagConstraints();
        gbc_lblIn.gridx = 2;
        gbc_lblIn.gridy = 0;
        driveConfig.add(lblIn, gbc_lblIn);
        JPanel turnConfig = new JPanel();
        stepContainer.add(turnConfig, "turn");
        GridBagLayout gbl_turnConfig = new GridBagLayout();
        gbl_turnConfig.columnWidths = new int[] { 0, 0, 0, 0 };
        gbl_turnConfig.rowHeights = new int[] { 0, 0 };
        gbl_turnConfig.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        gbl_turnConfig.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        turnConfig.setLayout(gbl_turnConfig);
        JLabel lblAngle = new JLabel("Angle:");
        GridBagConstraints gbc_lblAngle = new GridBagConstraints();
        gbc_lblAngle.anchor = GridBagConstraints.EAST;
        gbc_lblAngle.insets = new Insets(0, 0, 0, 5);
        gbc_lblAngle.gridx = 0;
        gbc_lblAngle.gridy = 0;
        turnConfig.add(lblAngle, gbc_lblAngle);
        angleField = new JTextField();
        GridBagConstraints gbc_angleField = new GridBagConstraints();
        gbc_angleField.insets = new Insets(0, 0, 0, 5);
        gbc_angleField.fill = GridBagConstraints.HORIZONTAL;
        gbc_angleField.gridx = 1;
        gbc_angleField.gridy = 0;
        turnConfig.add(angleField, gbc_angleField);
        angleField.setColumns(10);
        JLabel lblub = new JLabel("\u00B0");
        GridBagConstraints gbc_lblub = new GridBagConstraints();
        gbc_lblub.gridx = 2;
        gbc_lblub.gridy = 0;
        turnConfig.add(lblub, gbc_lblub);
        JPanel customConfig = new JPanel();
        stepContainer.add(customConfig, "custom");
        GridBagLayout gbl_customConfig = new GridBagLayout();
        gbl_customConfig.columnWidths = new int[] { 0, 0, 0 };
        gbl_customConfig.rowHeights = new int[] { 0, 0, 0 };
        gbl_customConfig.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_customConfig.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        customConfig.setLayout(gbl_customConfig);
        JLabel lblCommandId = new JLabel("Command ID:");
        GridBagConstraints gbc_lblCommandId = new GridBagConstraints();
        gbc_lblCommandId.insets = new Insets(0, 0, 5, 5);
        gbc_lblCommandId.anchor = GridBagConstraints.EAST;
        gbc_lblCommandId.gridx = 0;
        gbc_lblCommandId.gridy = 0;
        customConfig.add(lblCommandId, gbc_lblCommandId);
        commandField = new JTextField();
        GridBagConstraints gbc_commandField = new GridBagConstraints();
        gbc_commandField.insets = new Insets(0, 0, 5, 0);
        gbc_commandField.fill = GridBagConstraints.HORIZONTAL;
        gbc_commandField.gridx = 1;
        gbc_commandField.gridy = 0;
        customConfig.add(commandField, gbc_commandField);
        commandField.setColumns(10);
        JLabel lblParameters = new JLabel("Parameter:");
        GridBagConstraints gbc_lblParameters = new GridBagConstraints();
        gbc_lblParameters.insets = new Insets(0, 0, 0, 5);
        gbc_lblParameters.gridx = 0;
        gbc_lblParameters.gridy = 1;
        customConfig.add(lblParameters, gbc_lblParameters);
        parameterField = new JTextPane();
        GridBagConstraints gbc_parameterField = new GridBagConstraints();
        gbc_parameterField.fill = GridBagConstraints.BOTH;
        gbc_parameterField.gridx = 1;
        gbc_parameterField.gridy = 1;
        customConfig.add(parameterField, gbc_parameterField);
        springLayout.putConstraint(SpringLayout.WEST, lblCommandParameters, 0, SpringLayout.WEST, lblGeneralParameters);
        add(lblCommandParameters);
        scaleField.getDocument().addDocumentListener(this);
        startXField.getDocument().addDocumentListener(this);
        startYField.getDocument().addDocumentListener(this);
        timeField.getDocument().addDocumentListener(this);
        distanceField.getDocument().addDocumentListener(this);
        angleField.getDocument().addDocumentListener(this);
        commandField.getDocument().addDocumentListener(this);
        parameterField.getDocument().addDocumentListener(this);
    }
}
