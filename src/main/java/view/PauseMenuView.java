package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import interface_adapter.pause_menu.ResumeController;
import interface_adapter.save.SaveController;

/**
 * A pause menu overlay displayed on top of the GameView when the game is paused.
 * It dims the background and shows a modal with Resume and Save & Exit buttons.
 */
public class PauseMenuView extends JPanel {

    private static final int OVERLAY_ALPHA = 160;
    private static final int MODAL_CORNER_ROUNDED = 3;
    private static final int MODAL_WIDTH = 400;
    private static final int MODAL_HEIGHT = 360;
    private static final int MODAL_X = 200;
    private static final int MODAL_Y = 120;
    private static final int TITLE_FONT_SIZE = 36;
    private static final int BORDER_PADDING_TOP = 30;
    private static final int BORDER_PADDING_LEFT = 20;
    private static final int BORDER_PADDING_BOTTOM = 30;
    private static final int BUTTON_WIDTH = 250;
    private static final int BUTTON_HEIGHT = 50;
    private static final int SPACING_AFTER_TITLE = 30;
    private static final int BUTTON_SPACING = 20;
    private static final int OVERLAY_WIDTH = 800;
    private static final int OVERLAY_HEIGHT = 600;

    private final String viewName = "pause_menu";

    /**
     * Creates a new PauseMenuView containing Resume and Save & Exit buttons.
     *
     * @param resumeController the controller responsible for handling resume action.
     * @param saveController   the controller responsible for handling save and exit action.
     */
    public PauseMenuView(ResumeController resumeController,
                         SaveController saveController) {

        setLayout(null);
        setOpaque(false);

        // DARK OVERLAY BACKGROUND
        final JPanel overlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                final Graphics2D g2 = (Graphics2D) graphics;
                g2.setColor(new Color(0, 0, 0, OVERLAY_ALPHA));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        overlay.setBounds(0, 0, OVERLAY_WIDTH, OVERLAY_HEIGHT);
        overlay.setLayout(null);
        add(overlay);

        // CENTER MODAL PANEL
        final JPanel modal = new JPanel();
        modal.setBackground(Color.WHITE);
        modal.setLayout(new BoxLayout(modal, BoxLayout.Y_AXIS));

        modal.setBounds(MODAL_X, MODAL_Y, MODAL_WIDTH, MODAL_HEIGHT);
        modal.setOpaque(true);

        modal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, MODAL_CORNER_ROUNDED, true),
                BorderFactory.createEmptyBorder(
                        BORDER_PADDING_TOP, BORDER_PADDING_LEFT,
                        BORDER_PADDING_BOTTOM, BORDER_PADDING_LEFT
                )
        ));

        overlay.add(modal);

        // TITLE
        final JLabel title = new JLabel("Paused", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, TITLE_FONT_SIZE));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        modal.add(title);

        modal.add(Box.createRigidArea(new Dimension(0, SPACING_AFTER_TITLE)));

        // BUTTON SIZE
        final Dimension buttonSize = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);

        // RESUME BUTTON
        final JButton resumeButton = new JButton("Resume");
        resumeButton.setPreferredSize(buttonSize);
        resumeButton.setMaximumSize(buttonSize);
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // SAVE & EXIT BUTTON
        final JButton saveExitButton = new JButton("Save & Exit");
        saveExitButton.setPreferredSize(buttonSize);
        saveExitButton.setMaximumSize(buttonSize);
        saveExitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        modal.add(resumeButton);
        modal.add(Box.createRigidArea(new Dimension(0, BUTTON_SPACING)));
        modal.add(saveExitButton);

        // ACTION LISTENERS
        resumeButton.addActionListener(event -> resumeController.resume());
        saveExitButton.addActionListener(event -> saveController.save());
    }

    /**
     * Returns the view name used by the ViewManager.
     *
     * @return the pause menu view name.
     */
    public String getViewName() {
        return viewName;
    }
}
