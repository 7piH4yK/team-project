package view;

import entity.*;
import interface_adapter.AppContext;
import interface_adapter.collect_item.CollectItemController;
import interface_adapter.dialogue.DialogueController;
import interface_adapter.dialogue.DialogueState;
import interface_adapter.game.GameController;
import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;
import interface_adapter.save.SaveController;
import interface_adapter.question.QuestionController;
import interface_adapter.pause_menu.PauseController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

/**
 * The View for the Game.
 */
public class GameView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "game";
    private final GameViewModel gameViewModel;
    private GameController gameController;
    private DialogueController dialogueController;
    private SaveController saveController;
    private CollectItemController collectItemController;
    private QuestionController questionController;

    private GameState gameState;

    private PauseController pauseController;


    public GameView(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;
        this.gameViewModel.addPropertyChangeListener(this);
        this.setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No action needed for now
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final Object object = evt.getNewValue();

        // remove all children
        this.removeAll();

        try {

            if (object instanceof DialogueState) {
                final DialogueState state = (DialogueState) object;
                // Render dialogue overlay if active
                DialogueBox dialogue = state.getCurrentDialogue();
                if (dialogue != null) {
                    renderDialogueOverlay(dialogue);
                } else {
                    drawScene(gameState);
                }
            } else {
                final GameState state = (GameState) object;

                drawScene(state);


            }
            // force update ui
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawScene(GameState state) throws IOException {
        gameState = state;
        // add clickable objects
        for (ClickableObject clickable : state.getClickableObjects()) {
            ImageIcon imageIcon = new ImageIcon();
            imageIcon.setImage(ImageIO.read(new File("src/main/resources", clickable.getImage())));
            JLabel label = new JLabel(imageIcon);
            label.setBounds(clickable.getCoordinateX(), clickable.getCoordinateY(), imageIcon.getIconWidth(), imageIcon.getIconHeight());
            add(label);
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    // NEW: Collectible handling
                    if (clickable instanceof Collectibles) {
                        String sceneName = null;
                        if (gameState != null) {
                            sceneName = gameState.getSceneName();
                        }
                        collectItemController.collectItem(
                                clickable.getName(),
                                sceneName
                        );
                        return; // stop normal logic
                    }

                    // OLD: NPC and normal logic
                    if (clickable instanceof NonPlayableCharacter) {
                        dialogueController.click(clickable);
                    } else {
                        gameController.click(clickable);
                    }
                }
            });

        }

        // Pause button
        JButton pauseButton = new JButton("Pause");
        pauseButton.setBounds(650, 20, 120, 40);
        pauseButton.addActionListener(e -> pauseController.pause());
        add(pauseButton);

        // Add a small bag icon (top-right corner)
        ImageIcon bagIcon = new ImageIcon(ImageIO.read(new File("src/main/resources/bag1.png")));
        JButton bagButton = new JButton(bagIcon);
        bagButton.setBounds(20, 480, 64, 64); // Adjust position and size
        bagButton.setContentAreaFilled(false);
        bagButton.setBorderPainted(false);
        bagButton.setFocusPainted(false);
        bagButton.addActionListener(e -> openInventoryPanel());
        add(bagButton);

        // add background image
        ImageIcon background = new ImageIcon();
        background.setImage(ImageIO.read(new File("src/main/resources", state.getBackgroundImage())));
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
        add(backgroundLabel);
    }

    private void renderDialogueOverlay(DialogueBox dialogue) throws IOException {
        // NEW: detect if this is a QuestionBox
        final boolean isQuestionBox = dialogue instanceof QuestionBox;

        // Add dialogue text
        DialogueText dialogueText = dialogue.getText();
        JTextArea textArea = new JTextArea(dialogueText.getText());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setHighlighter(null);
        textArea.setForeground(Color.RED);
        textArea.setBackground(Color.BLACK);
        textArea.setFont(new Font(textArea.getFont().getName(), Font.BOLD, textArea.getFont().getSize()));
        textArea.setBounds(dialogueText.getCoordinateX(), dialogueText.getCoordinateY(), 400, 300);
        add(textArea); // Add at front

        // Add dialogue options
        for (DialogueOption option : dialogue.getOptions()) {
            JLabel optionLabel = new JLabel(option.getName());
            optionLabel.setForeground(Color.RED);
            optionLabel.setFont(new Font(optionLabel.getFont().getName(), Font.BOLD, optionLabel.getFont().getSize()));
            Dimension preferredSize = optionLabel.getPreferredSize();
            optionLabel.setBounds(option.getCoordinateX(), option.getCoordinateY(), preferredSize.width + 50, preferredSize.height);
            add(optionLabel); // Add at front
            optionLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    /// ---------- CASE 1: this is the special prompt option that starts the riddle ----------
                    // (This is in the dialogue BEFORE the question, e.g. laptop "Answer riddle")
                    if (!isQuestionBox && "Answer riddle".equals(option.getName())) {
                        if (questionController != null) {
                            questionController.loadQuestions();  // triggers API + QuestionPresenter
                        }
                        // DO NOT call dialogueController here, or it will navigate away
                        return;
                    }

                    // ---------- CASE 2: we are inside a QuestionBox (the actual riddle) ----------
                    if (isQuestionBox && option instanceof QuestionOption) {
                        QuestionOption qOpt = (QuestionOption) option;

                        if (qOpt.isCorrect()) {
                            JOptionPane.showMessageDialog(
                                    GameView.this,
                                    "Correct!",
                                    "Riddle",
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            // Reveal the classroom key after correct laptop quiz
                            var gameDAO = AppContext.getGameDAO();
                            if (gameDAO != null) {
                                // Tell DAO to add the key to the Scene Table (and update currentScene if needed)
                                gameDAO.revealClassroomKey();

                                // Rebuild GameState from DAO's current scene
                                entity.Scene current = gameDAO.getCurrentScene();

                                GameState newState = new GameState();
                                newState.setSceneName(current.getName());
                                newState.setBackgroundImage(current.getImage());
                                newState.setClickableObjects(current.getObjects());
                                newState.setInventoryItems(gameDAO.getPlayer().getInventory());

                                gameViewModel.setState(newState);
                                gameViewModel.firePropertyChange();
                            } else if (gameState != null) {
                                // Fallback: old behavior
                            // ⭐⭐⭐ GIVE KEY EXIT DIRECTLY ⭐⭐⭐
                            try {
                                var gameDAO = AppContext.getGameDAO();
                                if (gameDAO != null) {

                                    // Directly create a new Key Exit item
                                    Collectibles keyExit = new Collectibles(
                                            "Key Exit",
                                            0, 0,
                                            "key1.png"
                                    );

                                    // Add it to the player's inventory
                                    gameDAO.getPlayer().addToInventory(keyExit);

                                    System.out.println("⭐ Key Exit added directly to inventory!");
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                                // ⭐⭐⭐ END OF KEY REWARD ⭐⭐⭐


                            // After a correct answer: return to main game view (scene-only)
                            if (gameState != null) {
                                gameViewModel.setState(gameState);
                                gameViewModel.firePropertyChange();
                            }

                        } else {
                            JOptionPane.showMessageDialog(
                                    GameView.this,
                                    "Wrong answer, try again!",
                                    "Riddle",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            // Wrong answer: keep the QuestionBox overlay, let them try again.
                        }
                        return; // IMPORTANT: do not fall through to dialogueController for QuestionBox
                    }

                    // ---------- NORMAL DIALOGUE OPTION BEHAVIOR ----------
                    if (dialogueController != null) {
                        dialogueController.clickDialogueOption(option);
                    }
                }
            });
        }

        // Add dialogue box background image
        ImageIcon dialogueBackground = new ImageIcon();
        dialogueBackground.setImage(ImageIO.read(new File("src/main/resources", dialogue.getImage())));
        JLabel dialogueBackgroundLabel = new JLabel(dialogueBackground);
        dialogueBackgroundLabel.setBounds(0, 0, dialogueBackground.getIconWidth(), dialogueBackground.getIconHeight());
        add(dialogueBackgroundLabel); // Add at front

    }

    public String getViewName() {
        return viewName;
    }

    public void setGameController(GameController controller) {
        this.gameController = controller;
    }

    public void setDialogueController(DialogueController controller) {
        this.dialogueController = controller;
    }

    public void setSaveController(SaveController controller) {
        this.saveController = controller;
    }

    private void openInventoryPanel() {
        JFrame inventoryFrame = new JFrame("Inventory");
        inventoryFrame.setSize(400, 300);
        inventoryFrame.setLocationRelativeTo(null);
        inventoryFrame.setLayout(new FlowLayout());

        java.util.List<Collectibles> items = null;

        try {
            // Pull from DAO as single source of truth
            var gameDAO = AppContext.getGameDAO();  // your global reference
            if (gameDAO != null && gameDAO.getPlayer() != null) {
                items = gameDAO.getPlayer().getInventory();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (items == null || items.isEmpty()) {
            inventoryFrame.add(new JLabel("Your inventory is empty."));
        } else {
            for (Collectibles item : items) {
                try {
                    ImageIcon itemIcon = new ImageIcon(
                            ImageIO.read(new File("src/main/resources", item.getImage()))
                    );
                    JLabel itemLabel = new JLabel(itemIcon);
                    itemLabel.setToolTipText(item.getName());
                    inventoryFrame.add(itemLabel);
                } catch (Exception ex) {
                    inventoryFrame.add(new JLabel(item.getName()));
                }
            }
        }

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> inventoryFrame.dispose());
        inventoryFrame.add(closeButton);

        inventoryFrame.setVisible(true);
    }

    public void setCollectItemController(CollectItemController collectController) {
        this.collectItemController = collectController;
    }

    public void setQuestionController(QuestionController controller) {
        this.questionController = controller;
    }

    public void setPauseController(PauseController controller) {
        this.pauseController = controller;
    }
}
