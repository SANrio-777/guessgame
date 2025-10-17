import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class WordGame {
    private static final String[] WORD_BANK = {
        "uttarkhand", "assam", "karnataka", "kerala",
        "tamilnadu", "maharashtra", "madhya pradesh",
        "odisha", "jharkhand"
    };

    private String word;
    private char[] guessedWord;
    private int attempts = 5;

    // GUI components
    private JFrame frame;
    private JLabel wordLabel;
    private JLabel messageLabel;
    private JLabel attemptsLabel;
    private JTextField inputField;
    private JButton guessButton;

    public WordGame() {
        // Pick a random word
        word = WORD_BANK[new Random().nextInt(WORD_BANK.length)];
        guessedWord = new char[word.length()];

        for (int i = 0; i < word.length(); i++) {
            guessedWord[i] = (word.charAt(i) == ' ') ? ' ' : '_';
        }

        // Create GUI
        frame = new JFrame("Word Guess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1, 8, 8));
        frame.getContentPane().setBackground(new Color(230, 240, 255)); // soft blue

        // Center the frame
        frame.setLocationRelativeTo(null);

        // Word label
        wordLabel = new JLabel(getFormattedWord(), SwingConstants.CENTER);
        wordLabel.setFont(new Font("Consolas", Font.BOLD, 26));
        wordLabel.setForeground(new Color(25, 25, 112)); // navy blue

        // Input field
        inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.setBackground(new Color(255, 255, 240)); // ivory
        inputField.setForeground(Color.BLACK);
        inputField.setFont(new Font("Arial", Font.PLAIN, 18));

        // Press Enter = guess
        inputField.addActionListener(e -> handleGuess());

        // Guess Button
        guessButton = new JButton("Guess");
        guessButton.setBackground(new Color(30, 144, 255)); // Dodger blue
        guessButton.setForeground(Color.WHITE);
        guessButton.setFont(new Font("Arial", Font.BOLD, 16));
        guessButton.setOpaque(true);
        guessButton.setBorderPainted(false);
        guessButton.setFocusPainted(false);
        guessButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button hover effect
        guessButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                guessButton.setBackground(new Color(0, 120, 215)); // darker blue
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                guessButton.setBackground(new Color(30, 144, 255)); // normal
            }
        });

        // Button click event
        guessButton.addActionListener(_ -> handleGuess());

        // Message label
        messageLabel = new JLabel("Guess a letter!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        messageLabel.setForeground(Color.DARK_GRAY);

        // Attempts label
        attemptsLabel = new JLabel("Attempts left: " + attempts, SwingConstants.CENTER);
        attemptsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        attemptsLabel.setForeground(new Color(139, 0, 0)); // dark red

        // Add components
        frame.add(wordLabel);
        frame.add(inputField);
        frame.add(guessButton);
        frame.add(messageLabel);
        frame.add(attemptsLabel);

        frame.setVisible(true);
    }

    private void handleGuess() {
        String guessText = inputField.getText().toLowerCase().trim();
        inputField.setText("");

        if (guessText.length() != 1 || !Character.isLetter(guessText.charAt(0))) {
            messageLabel.setText("EHH one letter at a time!");
            messageLabel.setForeground(Color.RED);
            return;
        }

        char guess = guessText.charAt(0);
        boolean correct = false;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                guessedWord[i] = guess;
                correct = true;
            }
        }

        if (correct) {
            messageLabel.setText("Oo Noice!");
            messageLabel.setForeground(new Color(0, 128, 0)); // green
        } else {
            attempts--;
            messageLabel.setText("You can do this! Try again!");
            messageLabel.setForeground(Color.RED);
        }

        attemptsLabel.setText("Attempts left: " + attempts);
        wordLabel.setText(getFormattedWord());

        if (String.valueOf(guessedWord).equals(word)) {
            messageLabel.setText("Yay! You got it! Word: " + word);
            messageLabel.setForeground(new Color(0, 100, 0));
            disableInput();
        } else if (attempts == 0) {
            messageLabel.setText("Uh oh, you lost! The word was: " + word);
            messageLabel.setForeground(Color.RED);
            disableInput();
        }
    }

    private void disableInput() {
        inputField.setEnabled(false);
        guessButton.setEnabled(false);
    }

    private String getFormattedWord() {
        StringBuilder sb = new StringBuilder();
        for (char c : guessedWord) {
            sb.append(c).append(' ');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordGame::new);
    }
}
