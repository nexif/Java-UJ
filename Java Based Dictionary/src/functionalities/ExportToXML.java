package functionalities;

import data.Dictionary;
import logic.Main;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.naming.spi.ObjectFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class ExportToXML extends JDialog {
    private JLabel label;
    private JTextField textField;
    private JButton button;
    private Dictionary dictionary;

    public ExportToXML(JFrame frame, Dictionary dictionary){
        this.dictionary = dictionary;

        label = new JLabel("Wpisz nazwę pliku");
        textField = new JTextField();
        button = new JButton("Eksportuj do XML");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        button.addActionListener((ActionEvent ae) -> {
            String filename = textField.getText();
            exportToXML(dictionary, filename);
        });

        panel.setPreferredSize(new Dimension(200,80));
        panel.add(label);
        panel.add(textField);
        panel.add(button);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void exportToXML(Dictionary dictionary, String filename){
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = dbFactory.newDocumentBuilder();
            Document document = docBuilder.newDocument();

            Element root = document.createElement("dictionary");
            document.appendChild(root);

            Element position;
            Element word;
            Element definition;

            String wordStr;
            for (int i = 0; i < dictionary.size(); i++) {
                wordStr = dictionary.words()[i];

                position = document.createElement("position");
                root.appendChild(position);

                word = document.createElement("word");
                definition = document.createElement("definition");

                word.appendChild(document.createTextNode(wordStr));
                position.appendChild(word);
                definition.appendChild(document.createTextNode(dictionary.searchDefinition(wordStr)));
                position.appendChild(definition);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filename + ".xml"));
            transformer.transform(source, streamResult);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}

