package logic;


import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import data.Dictionary;
import data.Option;

public class Main {

    public static void main(String[] args) throws SQLException {
        Dictionary dictionary = new Dictionary();

        dictionary.createTable(dictionary.connectwithDB());
        JFrame frame = new JFrame("Słownik");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(Option.values().length,1));

        JButton[] buttons = new JButton[Option.values().length];
        MainActionListener actionListener = new MainActionListener(dictionary, frame);
        for (int i = 0; i < Option.values().length; i++) {
            buttons[i] = new JButton(Option.values()[i].getDescription());
            buttons[i].addActionListener(actionListener);
            buttons[i].setMinimumSize(new Dimension(500, 50));
            panel.add(buttons[i]);
        }

        panel.setPreferredSize(new Dimension(300, 500));
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
