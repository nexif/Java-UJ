
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;


public class TicTacToeClient implements ActionListener {

    private JFrame frame = new JFrame("Kółko i krzyżyk");
    private JLabel messageLabel = new JLabel("");
    private ImageIcon icon;
    private ImageIcon opponentIcon;

    private Square[] board = new Square[9];
    private Square currentSquare;

    private static int PORT = 9999;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    JMenuItem save, load, exit;

    public TicTacToeClient(String serverAddress) throws Exception {

        socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, "South");

        JPanel boardPanel = new JPanel();
        boardPanel.setBackground(Color.black);
        boardPanel.setLayout(new GridLayout(3, 3, 2, 2));
        for (int i = 0; i < board.length; i++) {
            final int j = i;
            board[i] = new Square();
            board[i].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    currentSquare = board[j];
                    out.println("MOVE " + j);}});
            boardPanel.add(board[i]);
        }

        JMenu menu = new JMenu("Plik");
        save = new JMenuItem("Zapisz grę");
        load = new JMenuItem("Wczytaj grę");
        exit = new JMenuItem("Wyjdź z gry");
        load.addActionListener(this);
        save.addActionListener(this);
        exit.addActionListener(this);
        menu.add(save);
        menu.add(load);
        menu.add(exit);
        JMenuBar menubar =new JMenuBar();
        menubar.add(menu);

        frame.getContentPane().add(boardPanel, "Center");
        frame.getContentPane().add(menubar,"North");
    }

    public void saveGameClient(){
        out.println("SAVE");
    }

    public void loadGameClient(){
        out.println("LOAD");
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==save){
            saveGameClient();
        }else if(e.getSource()==load){
          loadGameClient();
        }else if(e.getSource()==exit){
            int responsevalue = JOptionPane.showConfirmDialog(frame, "Czy na pewnoe chcesz wyjść??", "Kółko i krzyżyk", JOptionPane.YES_NO_OPTION);
            if(responsevalue==0){
                frame.dispose();
            }

        }else{
            System.out.println("Blad");
        }
    }


    public void play() throws Exception {
    	char mark = 'a';
        String response;
        try {
            response = in.readLine();
            if (response.startsWith("WELCOME")) {
                mark = response.charAt(8);
                icon = new ImageIcon(mark == 'X' ? "res/blueX.png" : "res/blueCircle.png");
                opponentIcon  = new ImageIcon(mark == 'X' ? "res/redCircle.png" : "res/redX.png");
                frame.setTitle("Gracz: " + mark);
            }

            while (true) {
                response = in.readLine();
                if (response.startsWith("VALID_MOVE")) {
                    messageLabel.setText("Tura przeciwnika.");
                    currentSquare.setIcon(icon);
                    currentSquare.repaint();
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    int loc = Integer.parseInt(response.substring(15));
                    board[loc].setIcon(opponentIcon);
                    board[loc].repaint();
                    messageLabel.setText("Twoja kolej.");
                } else if (response.startsWith("VICTORY")) {
                    messageLabel.setText("Wygrana!");
                    break;
                } else if (response.startsWith("DEFEAT")) {
                    messageLabel.setText("Przegrana!");
                    break;
                } else if (response.startsWith("TIE")) {
                    messageLabel.setText("Remis!");
                    break;
                } else if (response.startsWith("MESSAGE")) {
                    messageLabel.setText(response.substring(8));
                } else if (response.startsWith("GAME_LOADED")) {
                    for(int i = 0; i < board.length;i++){

                    	if((response = in.readLine()).equals("0"))
                    		continue;
                    	else if(response.equals(Character.toString(mark)))
                    		board[i].setIcon(icon);
                    	else
                    		board[i].setIcon(opponentIcon);
                    	board[i].repaint();
                    }
                    messageLabel.setText(in.readLine());
                }
            }
            out.println("QUIT");
        }
        finally {
            socket.close();
        }
    }

    private boolean wantsToPlayAgain() {
        int response = JOptionPane.showConfirmDialog(frame, "Chcesz zagrać ponownie?", "Kółko i krzyżyk", JOptionPane.YES_NO_OPTION);
        frame.dispose();
        return response == JOptionPane.YES_OPTION;
    }


    static class Square extends JPanel {
        JLabel label = new JLabel((Icon)null);

        public Square() {
            setBackground(Color.white);
            add(label);
        }

        public void setIcon(Icon icon) {
            label.setIcon(icon);
        }
    }


    public static void main(String[] args) throws Exception {

        while (true) {
            String serverAddress = (args.length == 0) ? "localhost" : args[1];
            TicTacToeClient client = new TicTacToeClient(serverAddress);
            client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            client.frame.setSize(500, 570);
            client.frame.setVisible(true);
            client.frame.setResizable(false);
            client.play();
            if (!client.wantsToPlayAgain()) {
                break;
            }
        }
    }
}