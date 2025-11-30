package view;

import interface_adapter.pause_menu.ResumeController;
import interface_adapter.save.SaveController;

import javax.swing.*;
import java.awt.*;

public class PauseMenuView extends JPanel {

    private final String viewName = "pause_menu";

    public String getViewName() {
        return viewName;
    }

    public PauseMenuView(ResumeController resumeController,
                         SaveController saveController) {

        setLayout(null);  // allows free positioning
        setOpaque(false); // so overlay shows

        // --------- CREATE DARK OVERLAY BACKGROUND ----------
        JPanel overlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(0, 0, 0, 160)); // translucent black
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        overlay.setBounds(0, 0, 800, 600);
        overlay.setLayout(null);
        add(overlay);

        // ---------- CREATE THE CENTER MODAL PANEL ----------
        JPanel modal = new JPanel();
        modal.setBackground(Color.WHITE);
        modal.setLayout(new BoxLayout(modal, BoxLayout.Y_AXIS));
        modal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        modal.setBounds(200, 120, 400, 360); // centered 400x360 box
        modal.setOpaque(true);

        // Rounded corners
        modal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 3, true),
                BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));

        overlay.add(modal);

        // ---------- TITLE ----------
        JLabel title = new JLabel("Paused", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        modal.add(title);

        modal.add(Box.createRigidArea(new Dimension(0, 30)));

        // ---------- BUTTONS ----------
        Dimension buttonSize = new Dimension(250, 50);

        JButton resumeButton = new JButton("Resume");
        resumeButton.setPreferredSize(buttonSize);
        resumeButton.setMaximumSize(buttonSize);
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton saveExitButton = new JButton("Save & Exit");
        saveExitButton.setPreferredSize(buttonSize);
        saveExitButton.setMaximumSize(buttonSize);
        saveExitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        modal.add(resumeButton);
        modal.add(Box.createRigidArea(new Dimension(0, 20)));
        modal.add(saveExitButton);

        // ---------- ACTIONS ----------
        resumeButton.addActionListener(e -> resumeController.resume());
        saveExitButton.addActionListener(e -> saveController.save());
    }
}
