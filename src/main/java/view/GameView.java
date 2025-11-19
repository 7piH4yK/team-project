package view;

import entity.ClickableObject;
import entity.DialogueBox;
import entity.DialogueOption;
import entity.DialogueText;
import interface_adapter.game.GameController;
import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;
import interface_adapter.save.SaveController;

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
    private SaveController saveController;


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
        final GameState state = (GameState) evt.getNewValue();

        // remove all children
        this.removeAll();

        try {


            // Render dialogue overlay if active
            DialogueBox dialogue = state.getCurrentDialogue();
            if (dialogue != null) {
                renderDialogueOverlay(dialogue);
            }


            // add clickable objects
            for (ClickableObject clickableObject : state.getClickableObjects()) {
                ImageIcon imageIcon = new ImageIcon();
                imageIcon.setImage(ImageIO.read(new File("src/main/resources", clickableObject.getImage())));
                JLabel label = new JLabel(imageIcon);
                label.setBounds(clickableObject.getCoordinateX(), clickableObject.getCoordinateY(), imageIcon.getIconWidth(), imageIcon.getIconHeight());
                add(label);
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        gameController.click(clickableObject);
                    }
                });
            }

            // save and exit button
            JButton saveExitButton = new JButton("Save & Exit");
            saveExitButton.setBounds(650, 20, 120, 40);
            saveExitButton.addActionListener(e -> saveController.save());
            add(saveExitButton);

            // add background image
            ImageIcon background = new ImageIcon();
            background.setImage(ImageIO.read(new File("src/main/resources", state.getBackgroundImage())));
            JLabel backgroundLabel = new JLabel(background);
            backgroundLabel.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
            add(backgroundLabel);


            // force update ui
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renderDialogueOverlay(DialogueBox dialogue) throws IOException {
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
            JLabel optionLabel = new JLabel(option.getText());
            optionLabel.setForeground(Color.RED);
            optionLabel.setFont(new Font(optionLabel.getFont().getName(), Font.BOLD, optionLabel.getFont().getSize()));
            Dimension preferredSize = optionLabel.getPreferredSize();
            optionLabel.setBounds(option.getCoordinateX(), option.getCoordinateY(), preferredSize.width + 50, preferredSize.height);
            add(optionLabel); // Add at front
            optionLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    gameController.clickDialogueOption(option);
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

    public void setSaveController(SaveController controller) {
        this.saveController = controller;
    }
}
