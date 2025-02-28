import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;

public class SnakeAndLadder extends JFrame {
    private static final int BOARD_SIZE = 10; // 10x10 Board
    private JButton rollDiceButton;
    private JLabel diceLabel;
    private JPanel boardPanel;
    private JLabel playerLabel;
    private int playerPosition = 1; // Starting position

    // Snakes and Ladders Mapping
    private final HashMap<Integer, Integer> snakes = new HashMap<>();
    private final HashMap<Integer, Integer> ladders = new HashMap<>();

    public SnakeAndLadder() {
        setTitle("Snake and Ladder Game");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize Snakes and Ladders
        initSnakesAndLadders();

        // Create Board
        boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        createBoard();

        // Dice and Controls
        JPanel controlPanel = new JPanel();
        rollDiceButton = new JButton("Roll Dice");
        diceLabel = new JLabel("Roll: 0");
        playerLabel = new JLabel("Player Position: 1");

        rollDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDice();
            }
        });

        controlPanel.add(rollDiceButton);
        controlPanel.add(diceLabel);
        controlPanel.add(playerLabel);

        add(boardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void initSnakesAndLadders() {
        // Snakes: Higher number to lower number
        snakes.put(99, 7);
        snakes.put(70, 55);
        snakes.put(52, 29);
        snakes.put(25, 2);

        // Ladders: Lower number to higher number
        ladders.put(3, 22);
        ladders.put(8, 26);
        ladders.put(20, 41);
        ladders.put(50, 67);
    }

    private void createBoard() {
        boardPanel.removeAll();
        for (int i = 100; i >= 1; i--) {
            JLabel cell = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            if (i == playerPosition) {
                cell.setOpaque(true);
                cell.setBackground(Color.BLUE);
                cell.setForeground(Color.WHITE);
            }

            boardPanel.add(cell);
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void rollDice() {
        Random rand = new Random();
        int diceRoll = rand.nextInt(6) + 1;
        diceLabel.setText("Roll: " + diceRoll);

        // Move Player
        playerPosition += diceRoll;
        if (playerPosition > 100) {
            playerPosition -= diceRoll; // Stay within bounds
        }

        // Check for Snakes or Ladders
        if (ladders.containsKey(playerPosition)) {
            playerPosition = ladders.get(playerPosition);
        } else if (snakes.containsKey(playerPosition)) {
            playerPosition = snakes.get(playerPosition);
        }

        // Update UI
        playerLabel.setText("Player Position: " + playerPosition);
        createBoard();

        // Check for Win
        if (playerPosition == 100) {
            JOptionPane.showMessageDialog(this, "Congratulations! You won!");
            rollDiceButton.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SnakeAndLadder game = new SnakeAndLadder();
            game.setVisible(true);
        });
    }
}