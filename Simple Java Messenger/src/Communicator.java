import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Communicator {
    JTextArea receivedMessage;
    JTextField message;
    BufferedReader br;
    PrintWriter pw;
    Socket socket;

    public void startWork() {
        JFrame frame = new JFrame("Messenger");
        JPanel panel = new JPanel();

        receivedMessage = new JTextArea(15,35);
        receivedMessage.setLineWrap(true);
        receivedMessage.setWrapStyleWord(true);
        receivedMessage.setEditable(false);

        JScrollPane scroll = new JScrollPane(receivedMessage);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        message = new JTextField(20);

        JButton button = new JButton("Send");
        button.addActionListener(new ButtonListener());

        panel.add(scroll);
        panel.add(message);
        panel.add(button);

        configureConnection();
        Thread client = new Thread(new Client());
        client.start();

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(440,330);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void configureConnection() {
        try {
            socket = new Socket("127.0.0.1", 9998);
            InputStreamReader isreader = new InputStreamReader(socket.getInputStream());
            br = new BufferedReader(isreader);
            pw = new PrintWriter(socket.getOutputStream());

        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            try {
                pw.println(message.getText());
                pw.flush();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            message.setText("");
            message.requestFocus();
        }
    }

    public class Client implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = br.readLine()) != null) {
                    System.out.println("Read: " + message);
                    receivedMessage.append(message + "\n");
                }
            } catch(Exception ex) {ex.printStackTrace();}
        }
    }
}