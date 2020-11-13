package functionalities;

import data.Dictionary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SearchDefinition extends JDialog {
    private JLabel label;
    private JTextField textField;
    private JButton button;
    private JTextArea textArea;
    private Dictionary dictionary;

    public SearchDefinition(JFrame parent, Dictionary dictionary) {
        super(parent, true);
        this.dictionary = dictionary;

        label = new JLabel("Wpisz nazwę pojęcia:");
        textField = new JTextField();
        button = new JButton("Szukaj");
        textArea = new JTextArea();
        textArea.setEditable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (dictionary.isEmpty()) {
            textField.setText("Brak pojęć w słowniku");
        } else {
            button.addActionListener((ActionEvent ae) -> {
                String word = textField.getText();
                String definition = dictionary.searchDefinition(word);
                textArea.setText(word + ": " + definition);
            });
        }

        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panel.setPreferredSize(new Dimension(700, 100));
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        subPanel.add(label);
        subPanel.add(textField);
        subPanel.add(button);
        panel.add(subPanel);
        panel.add(textArea);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
