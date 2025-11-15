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
    private final JButton laodGameButton;
    private final JButton exitGameButton;

    public MainMenuView(MainMenuViewModel mainMenuViewModel) {
        this.mainMenuViewModel = mainMenuViewModel;
        this.mainMenuViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(MainMenuViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 48));

        final JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

        Dimension buttonsSize = new Dimension(600, 60);

        startGameButton = new JButton(MainMenuViewModel.START_GAME_BUTTON_LABEL);
        startGameButton.setPreferredSize(buttonsSize);
        startGameButton.setMaximumSize(buttonsSize);
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.add(startGameButton);
        buttons.add(Box.createRigidArea(new Dimension(0, 20)));

        laodGameButton = new JButton(MainMenuViewModel.LAOD_GAME_BUTTON_LABEL);
        laodGameButton.setPreferredSize(buttonsSize);
        laodGameButton.setMaximumSize(buttonsSize);
        laodGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.add(laodGameButton);
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

        laodGameButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        // TODO: implement load game button functionality
                        System.out.print("Load game (TODO)");
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
        this.add(Box.createVerticalStrut(100));
        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No action needed for now
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // No state changes to handle for now
    }

    public String getViewName() {
        return viewName;
    }

    public void setMainMenuController(MainMenuController controller) {
        this.mainMenuController = controller;
    }
}
