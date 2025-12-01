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

public class PauseMenuView extends JPanel {

    // -------- Overlay Constants --------
    private static final int OVERLAY_ALPHA = 160;
    private static final Color OVERLAY_COLOR = new Color(0, 0, 0, OVERLAY_ALPHA);
    private static final int OVERLAY_WIDTH = 800;
    private static final int OVERLAY_HEIGHT = 600;

    // -------- Modal Constants --------
    private static final int MODAL_WIDTH = 400;
    private static final int MODAL_HEIGHT = 360;
    private static final int MODAL_X = 200;
    private static final int MODAL_Y = 120;

    private static final Color MODAL_BACKGROUND = Color.WHITE;
    private static final Color MODAL_BORDER_COLOR = Color.GRAY;

    private static final int MODAL_BORDER_THICKNESS = 3;
    private static final int MODAL_PADDING_TOP = 30;
    private static final int MODAL_PADDING_LEFT = 20;
    private static final int MODAL_PADDING_BOTTOM = 30;

    // -------- Text & Font Constants --------
    private static final String TITLE_TEXT = "Paused";
    private static final int TITLE_FONT_SIZE = 36;
    private static final int BUTTON_FONT_SIZE = 22;
    private static final int TITLE_SPACING = 30;

    // -------- Button Constants --------
    private static final int BUTTON_WIDTH = 250;
    private static final int BUTTON_HEIGHT = 50;

    private static final int BUTTON_SPACING = 20;

    private static final int BUTTON_BORDER_TOP = 10;
    private static final int BUTTON_BORDER_SIDE = 20;
    private static final int BUTTON_BORDER_BOTTOM = 10;

    private static final Color BUTTON_COLOR = new Color(60, 130, 230);
    private static final Color BUTTON_COLOR_HOVER = new Color(40, 110, 210);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    private final String viewName = "pause_menu";

    public PauseMenuView(ResumeController resumeController,
                         SaveController saveController) {

        setLayout(null);
        setOpaque(false);

        // Overlay panel
        final JPanel overlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                final Graphics2D g2 = (Graphics2D) graphics;
                g2.setColor(OVERLAY_COLOR);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        overlay.setBounds(0, 0, OVERLAY_WIDTH, OVERLAY_HEIGHT);
        overlay.setLayout(null);
        add(overlay);

        // Modal container
        final JPanel modal = new JPanel();
        modal.setBackground(MODAL_BACKGROUND);
        modal.setLayout(new BoxLayout(modal, BoxLayout.Y_AXIS));

        modal.setBounds(MODAL_X, MODAL_Y, MODAL_WIDTH, MODAL_HEIGHT);
        modal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MODAL_BORDER_COLOR, MODAL_BORDER_THICKNESS),
                BorderFactory.createEmptyBorder(
                        MODAL_PADDING_TOP,
                        MODAL_PADDING_LEFT,
                        MODAL_PADDING_BOTTOM,
                        MODAL_PADDING_LEFT
                )
        ));

        overlay.add(modal);

        // Title text
        final JLabel title = new JLabel(TITLE_TEXT, SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, TITLE_FONT_SIZE));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        modal.add(title);

        modal.add(Box.createRigidArea(new Dimension(0, TITLE_SPACING)));

        // Buttons
        final Dimension buttonSize = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);

        final JButton resumeButton = createStyledButton("Resume", buttonSize);
        final JButton saveExitButton = createStyledButton("Save & Exit", buttonSize);

        modal.add(resumeButton);
        modal.add(Box.createRigidArea(new Dimension(0, BUTTON_SPACING)));
        modal.add(saveExitButton);

        // Actions
        resumeButton.addActionListener(e -> resumeController.resume());
        saveExitButton.addActionListener(e -> saveController.save());
    }

    private JButton createStyledButton(String text, Dimension size) {

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

    public String getViewName() {
        return viewName;
    }
}
