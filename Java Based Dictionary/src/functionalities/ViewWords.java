package functionalities;

import data.Dictionary;

import javax.swing.*;
import java.awt.*;

public class ViewWords  extends JDialog {
    private JTextArea textArea;
    private Dictionary dictionary;

    public ViewWords(JFrame frame, Dictionary dictionary) {
        super(frame, true);
        this.dictionary = dictionary;
        textArea = new JTextArea();
        textArea.setEditable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(textArea);

        if (dictionary.isEmpty()) {
            textArea.append("Brak pojęć w słowniku.");
        } else {
            for (String s : dictionary.showWords()) {
                textArea.append(s + "\n");
            }
        }

        panel.setPreferredSize(new Dimension(200, 100));
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
