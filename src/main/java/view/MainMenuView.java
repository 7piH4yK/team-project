package view;

import interface_adapter.main_menu.MainMenuController;
import interface_adapter.main_menu.MainMenuViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the Main Menu.
 */
public class MainMenuView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "main menu";
    private final MainMenuViewModel mainMenuViewModel;
    private MainMenuController mainMenuController;

    private final JButton startGameButton;
    private final JButton loadGameButton;
    private final JButton exitGameButton;
    private final JLabel errorLabel;

    public MainMenuView(MainMenuViewModel mainMenuViewModel) {
        this.mainMenuViewModel = mainMenuViewModel;
        this.mainMenuViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(MainMenuViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 48));

        errorLabel = new JLabel((""));
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 24));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

        Dimension buttonsSize = new Dimension(600, 60);

        startGameButton = new JButton(MainMenuViewModel.START_GAME_BUTTON_LABEL);
        startGameButton.setPreferredSize(buttonsSize);
        startGameButton.setMaximumSize(buttonsSize);
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.add(startGameButton);
        buttons.add(Box.createRigidArea(new Dimension(0, 20)));

        loadGameButton = new JButton(MainMenuViewModel.LAOD_GAME_BUTTON_LABEL);
        loadGameButton.setPreferredSize(buttonsSize);
        loadGameButton.setMaximumSize(buttonsSize);
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.add(loadGameButton);
        buttons.add(Box.createRigidArea(new Dimension(0, 20)));

        exitGameButton = new JButton(MainMenuViewModel.EXIT_GAME_BUTTON_LABEL);
        exitGameButton.setPreferredSize(buttonsSize);
        exitGameButton.setMaximumSize(buttonsSize);
        exitGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.add(exitGameButton);
        buttons.add(Box.createRigidArea(new Dimension(0, 20)));

        startGameButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(startGameButton)) {
                            mainMenuController.switchToGameView();
                        }
                    }
                }
        );

        loadGameButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if  (evt.getSource().equals(loadGameButton)) {
                            mainMenuController.loadGame();
                        }
                    }
                }
        );

        exitGameButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        System.exit(0);
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(Box.createVerticalStrut(40));
        this.add(errorLabel);
        this.add(Box.createVerticalStrut(40));
        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No action needed for now
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == mainMenuViewModel) {
            String error = mainMenuViewModel.getState().getErrorMessage();
            errorLabel.setText(error != null ? error:"");
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setMainMenuController(MainMenuController controller) {
        this.mainMenuController = controller;
    }
}
