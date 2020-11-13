package functionalities;

import data.Dictionary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ImportFromFile extends JDialog{
    private JLabel label;
    private JTextField textField;
    private JButton button;
    private Dictionary dictionary;

    public ImportFromFile(JFrame frame, Dictionary dictionary) {
        this.dictionary = dictionary;

        label = new JLabel("Wpisz nazwę pliku:");
        textField = new JTextField();
        button = new JButton("Importuj");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        button.addActionListener((ActionEvent ae) -> {
            String filename = textField.getText();
            importFromFile(dictionary, filename);

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

    public void importFromFile(Dictionary dictionary, String filename){
        String line;
        try{
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while((line = br.readLine()) != null){
                String[] arrOfStr = line.split(" - ", 2);
                dictionary.add(arrOfStr[0],arrOfStr[1]);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Input/Output exception");
            e.printStackTrace();
        }

    }
}
