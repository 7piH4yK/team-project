package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interface_adapter.main_menu.MainMenuController;
import interface_adapter.main_menu.MainMenuViewModel;
import interface_adapter.question.QuestionController;

public class MainMenuView extends JPanel implements ActionListener, PropertyChangeListener {

    // ---------- UI Constants ----------
    private static final int TITLE_FONT_SIZE = 52;
    private static final int ERROR_FONT_SIZE = 22;
    private static final int BUTTON_FONT_SIZE = 22;

    private static final int BUTTON_WIDTH = 500;
    private static final int BUTTON_HEIGHT = 55;

    private static final int GAP_HEIGHT = 25;
    private static final int TOP_SPACER_HEIGHT = 60;
    private static final int ERROR_SPACER_HEIGHT = 30;
    private static final int BUTTONS_TOP_SPACER_HEIGHT = 40;

    private static final int BUTTON_BORDER_TOP = 10;
    private static final int BUTTON_BORDER_SIDE = 20;
    private static final int BUTTON_BORDER_BOTTOM = 10;

    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color TITLE_COLOR = new Color(40, 40, 40);
    private static final Color BUTTON_COLOR = new Color(60, 130, 230);
    private static final Color BUTTON_COLOR_HOVER = new Color(40, 110, 210);
    private static final Color ERROR_COLOR = Color.RED;
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    private final String viewName = "main menu";
    private final MainMenuViewModel mainMenuViewModel;

    private final JButton startGameButton;
    private final JButton loadGameButton;
    private final JButton exitGameButton;

    private final JLabel errorLabel;

    private MainMenuController mainMenuController;
    private QuestionController questionController;

    public MainMenuView(final MainMenuViewModel mainMenuViewModel) {

        this.mainMenuViewModel = mainMenuViewModel;
        this.mainMenuViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BACKGROUND_COLOR);

        // ----- Title -----
        final JLabel title = new JLabel(MainMenuViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, TITLE_FONT_SIZE));
        title.setForeground(TITLE_COLOR);

        // ----- Error Label -----
        errorLabel = new JLabel("");
        errorLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, ERROR_FONT_SIZE));
        errorLabel.setForeground(ERROR_COLOR);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ----- Button Panel -----
        final JPanel buttons = new JPanel();
        buttons.setBackground(BACKGROUND_COLOR);
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

        final Dimension buttonSize = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);

        startGameButton = createButton(MainMenuViewModel.START_GAME_BUTTON_LABEL, buttonSize);
        loadGameButton = createButton(MainMenuViewModel.LOAD_GAME_BUTTON_LABEL, buttonSize);
        exitGameButton = createButton(MainMenuViewModel.EXIT_GAME_BUTTON_LABEL, buttonSize);

        add(Box.createVerticalStrut(TOP_SPACER_HEIGHT));
        add(title);
        add(Box.createVerticalStrut(ERROR_SPACER_HEIGHT));
        add(errorLabel);
        add(Box.createVerticalStrut(BUTTONS_TOP_SPACER_HEIGHT));
        add(buttons);

        addButton(buttons, startGameButton);
        addButton(buttons, loadGameButton);
        addButton(buttons, exitGameButton);

        attachListeners();
    }

    private JButton createButton(final String text, final Dimension size) {

        final JButton button = new JButton(text);

        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setBackground(BUTTON_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, BUTTON_FONT_SIZE));

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setBorder(BorderFactory.createEmptyBorder(
                BUTTON_BORDER_TOP,
                BUTTON_BORDER_SIDE,
                BUTTON_BORDER_BOTTOM,
                BUTTON_BORDER_SIDE
        ));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }

    private void addButton(final JPanel panel, final JButton button) {
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, GAP_HEIGHT)));
    }

    private void attachListeners() {

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                mainMenuController.switchToGameView();
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                mainMenuController.loadGame();
            }
        });

        exitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        // no action needed
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {

        if (evt.getSource() == mainMenuViewModel) {

            final String errorText = mainMenuViewModel.getState().getErrorMessage();

            if (errorText == null) {
                errorLabel.setText("");
            }
            else {
                errorLabel.setText(errorText);
            }
        }
    }

    public String getViewName() {
        return viewName;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }

    public void setMainMenuController(final MainMenuController controller) {
        this.mainMenuController = controller;
    }

    public void setQuestionController(final QuestionController controller) {
        this.questionController = controller;
    }
}
