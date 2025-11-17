package view;

import data_access.InMemoryGameDataAccessObject;
import entity.ClickableObject;
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
import java.util.Objects;

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
            // add clickable objects
            for (ClickableObject clickableObject : state.getClickableObjects()) {
                if (clickableObject instanceof DialogueOption)
                {
                    JLabel label = new JLabel(((DialogueOption) clickableObject).getText());
                    label.setForeground(Color.RED);
                    label.setFont(new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize()));
                    Dimension preferredSize = label.getPreferredSize();
                    label.setBounds(clickableObject.getCoordinateX(), clickableObject.getCoordinateY(), preferredSize.width, preferredSize.height);
                    add(label);
                    label.addMouseListener(new MouseAdapter()
                    {
                        @Override
                        public void mouseClicked(MouseEvent e) {gameController.click(clickableObject);}
                    });
                }
                else if (clickableObject instanceof DialogueText)
                {
                    JTextArea label = new JTextArea(((DialogueText) clickableObject).getText());
                    label.setLineWrap(true);
                    label.setWrapStyleWord(true);
                    label.setEditable(false);
                    label.setHighlighter(null);
                    label.setForeground(Color.RED);
                    label.setBackground(Color.BLACK);
                    label.setFont(new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize()));
                    label.setBounds(clickableObject.getCoordinateX(), clickableObject.getCoordinateY(), 800-InMemoryGameDataAccessObject.text_xpos-InMemoryGameDataAccessObject.option_xpos, InMemoryGameDataAccessObject.option4_ypos + 28 - InMemoryGameDataAccessObject.option1_ypos);
                    add(label);
//
//                    label.addMouseListener(new MouseAdapter()
//                    {
//                        @Override
//                        public void mouseClicked(MouseEvent e) {gameController.click(clickableObject);}
//                    });
                }
                else {
                    ImageIcon imageIcon = new ImageIcon();
                    imageIcon.setImage(ImageIO.read(new File("src/main/resources", clickableObject.getImage())));
                    JLabel label = new JLabel(imageIcon);
                    label.setBounds(clickableObject.getCoordinateX(), clickableObject.getCoordinateY(), imageIcon.getIconWidth(), imageIcon.getIconHeight());
                    add(label);
                    label.addMouseListener(new MouseAdapter()
                    {
                        @Override
                        public void mouseClicked(MouseEvent e) {gameController.click(clickableObject);}
                    });
                }
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

    public String getViewName() {
        return viewName;
    }

    public void setGameController(GameController controller) {
        this.gameController = controller;
    }

    public void setSaveController(SaveController controller) {this.saveController = controller;}
}
