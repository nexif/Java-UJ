package functionalities;

import data.Dictionary;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;

public class ImportFromXML extends JDialog{
    private JLabel label;
    private JTextField textField;
    private JButton button;
    private Dictionary dictionary;

    public ImportFromXML(JFrame frame, Dictionary dictionary) {
        this.dictionary = dictionary;

        label = new JLabel("Wpisz nazwę pliku:");
        textField = new JTextField();
        button = new JButton("Importuj z XML");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        button.addActionListener((ActionEvent ae) -> {
            String filename = textField.getText();
            importFromXML(filename, dictionary);

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

    public void importFromXML(String filename, Dictionary dictionary){
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                String word;
                String definition;
                StringBuilder element;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    element = new StringBuilder();
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("word")) {
                        word = element.toString();
                    } else if (qName.equalsIgnoreCase("definition")) {
                        definition = element.toString();
                    } else if (qName.equalsIgnoreCase("position")) {
                        dictionary.add(word, definition);
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    element.append(ch, start, length);
                }
            };

            saxParser.parse(new FileInputStream(filename + ".xml"), handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
