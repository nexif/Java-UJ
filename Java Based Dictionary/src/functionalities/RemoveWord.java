package functionalities;

import data.Dictionary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RemoveWord extends JDialog {
    private JLabel label;
    private JTextField textField;
    private JButton button;
    private Dictionary dictionary;
    private JFrame parentframe;

    public RemoveWord(JFrame frame, Dictionary dictionary){
        this.dictionary = dictionary;
        label = new JLabel("Wpisz nazwę pojęcia:");
        textField = new JTextField();
        button = new JButton("Usuń");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (dictionary.isEmpty()) {
            textField.setText("Brak pojęć w słowniku");
        } else {
            button.addActionListener((ActionEvent ae) -> {
                String definition = textField.getText();
                dictionary.remove(definition);

                dispose();
            });

            panel.setPreferredSize(new Dimension(200, 80));
            panel.add(label);
            panel.add(textField);
            panel.add(button);


            getContentPane().add(panel);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }




}
