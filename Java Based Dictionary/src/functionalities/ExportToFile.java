package functionalities;

import data.Dictionary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ExportToFile extends JDialog {
    private JLabel label;
    private JTextField textField;
    private JButton button;
    private Dictionary dictionary;


    public ExportToFile(JFrame frame, Dictionary dictionary) {
        this.dictionary = dictionary;

        label = new JLabel("Wpisz nazwę pliku:");
        textField = new JTextField();

        button = new JButton("Eksportuj");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        button.addActionListener((ActionEvent ae) -> {
            String filename = textField.getText();
            exportToFile(dictionary, filename);
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

    public void exportToFile(Dictionary dictionary, String filename) {
        try {
            FileWriter filewriter = new FileWriter(filename);
            PrintWriter printwriter = new PrintWriter(filewriter);
            for (String s : dictionary.words()){
                printwriter.write(s + " - ");
                printwriter.write(dictionary.searchDefinition(s));
                printwriter.write("\n");
            }
            printwriter.close();
            System.out.println("Pomyślnie zapisano do pliku tekstowego");
        } catch (IOException e) {
            System.out.println("Problem z zapisem bazy danych do pliku");;
            e.printStackTrace();
        }
    }
}
