package org.usd232.robotics.autonomous.generator;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.usd232.robotics.autonomous.generator.model.ListModelBase;

public class SideList extends Container implements ActionListener, ListSelectionListener {
    private static final long      serialVersionUID = 1890289959504726076L;
    private JTextField             newField;
    private JList<String>          list;
    private JButton                addBtn;
    private JButton                removeBtn;
    private ListModelBase<?, ?, ?> model;
    private boolean                isNamed;
    private SideList               parent;
    private List<SideList>         children;

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == addBtn || e.getSource() == newField) && (!isNamed || newField.getText().length() > 0)) {
            model.add(newField.getText());
            newField.setText("");
        } else if (e.getSource() == removeBtn && !list.isSelectionEmpty()) {
            model.remove(list.getSelectedIndex());
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (list.isSelectionEmpty()) {
            children.forEach(child->child.setEnabled(false));
        } else {
            removeBtn.setEnabled(true);
            children.forEach(child-> {
                child.setEnabled(true);
                child.model.setParentSelected(
                                ((ListModelBase<?, ?, ?>) list.getModel()).getRawElementAt(list.getSelectedIndex()));
            });
        }
    }

    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        newField.setEnabled(b);
        list.setEnabled(b);
        addBtn.setEnabled(b);
        removeBtn.setEnabled(b && !list.isSelectionEmpty());
        children.forEach(child->child.setEnabled(b && !list.isSelectionEmpty()));
    }

    public SideList(String labelName, ListModelBase<?, ?, ?> model, boolean isNamed, boolean canAdd, SideList parent) {
        setPreferredSize(new Dimension(100, 100));
        setBackground(Color.GREEN);
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        JLabel lblLabel = new JLabel(labelName);
        springLayout.putConstraint(SpringLayout.NORTH, lblLabel, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, lblLabel, 10, SpringLayout.WEST, this);
        add(lblLabel);
        list = new JList<String>(model);
        add(list);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);
        springLayout.putConstraint(SpringLayout.NORTH, list, 15, SpringLayout.NORTH, lblLabel);
        springLayout.putConstraint(SpringLayout.WEST, list, 10, SpringLayout.WEST, this);
        newField = new JTextField();
        springLayout.putConstraint(SpringLayout.WEST, newField, 10, SpringLayout.WEST, this);
        add(newField);
        newField.setColumns(10);
        newField.addActionListener(this);
        if (!isNamed) {
            newField.setVisible(false);
        }
        springLayout.putConstraint(SpringLayout.SOUTH, list, -10, SpringLayout.NORTH, newField);
        springLayout.putConstraint(SpringLayout.EAST, list, -10, SpringLayout.EAST, this);
        addBtn = new JButton("+");
        springLayout.putConstraint(SpringLayout.NORTH, newField, 0, SpringLayout.NORTH, addBtn);
        springLayout.putConstraint(SpringLayout.SOUTH, newField, 0, SpringLayout.SOUTH, addBtn);
        springLayout.putConstraint(SpringLayout.EAST, newField, -6, SpringLayout.WEST, addBtn);
        add(addBtn);
        addBtn.addActionListener(this);
        addBtn.setVisible(canAdd);
        removeBtn = new JButton("-");
        springLayout.putConstraint(SpringLayout.NORTH, addBtn, 0, SpringLayout.NORTH, removeBtn);
        springLayout.putConstraint(SpringLayout.EAST, addBtn, -6, SpringLayout.WEST, removeBtn);
        springLayout.putConstraint(SpringLayout.SOUTH, removeBtn, -10, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, removeBtn, -10, SpringLayout.EAST, this);
        add(removeBtn);
        removeBtn.addActionListener(this);
        removeBtn.setEnabled(false);
        this.model = model;
        children = new ArrayList<>();
        this.isNamed = isNamed;
        if (parent != null) {
            this.parent = parent;
            model.setParent(parent.model);
            parent.children.add(this);
            setEnabled(false);
        }
    }
}
