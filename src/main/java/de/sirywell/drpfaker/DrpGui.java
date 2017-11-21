package de.sirywell.drpfaker;

import com.github.psnrigner.DiscordRichPresence;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DrpGui {

    private JTextField clientIDTextField;
    private JTextField detailsTextField;
    private JTextField stateTextField;
    private JButton updateButton;
    private JPanel mainPanel;
    private JButton exitButton;
    private JTextField startTimestampTextField;
    private JTextField endTimestampTextField;
    private JTextField largeImageKeyTextField;
    private JTextField largeImageTextTextField;
    private JTextField smallImageKeyTextField;
    private JTextField smallImageTextTextField;
    private JButton saveProfileButton;
    private JButton loadProfileButton;
    private JFrame frame;

    private DiscordRichPresence currentPresence;

    public DrpGui() {
        updateButton.addActionListener(e -> {
            createPresence();
            DrpFaker.setPresence(clientIDTextField.getText(), currentPresence);
        });
        exitButton.addActionListener(e -> frame.dispose());
        saveProfileButton.addActionListener(e -> {
            if(currentPresence == null) {
                createPresence();
            }
            JFileChooser chooser = new JFileChooser();
            String path = chooser.getCurrentDirectory().getPath();
            File file = new File(path,"profile.json");
            chooser.setSelectedFile(file);
            int response = chooser.showSaveDialog(frame);
            System.out.println(response);
            if(response == JFileChooser.SAVE_DIALOG) {
                try {
                    FileWriter writer = new FileWriter(chooser.getSelectedFile());
                    System.out.println(DrpFaker.toJsonObject(clientIDTextField.getText(), currentPresence).toString());
                    writer.write(DrpFaker.toJsonObject(clientIDTextField.getText(), currentPresence).toString());
                    writer.close();
                    System.out.println("Finished " + file);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public DrpGui(JFrame frame) {
        this();
        this.frame = frame;
    }

    public Container getMainPanel() {
        return mainPanel;
    }

    private void createPresence() {
        DiscordRichPresence.DiscordRichPresenceBuilder presenceBuilder = DiscordRichPresence.builder()
                .setDetails(detailsTextField.getText())
                .setState(stateTextField.getText())
                .setLargeImageKey(largeImageKeyTextField.getText())
                .setLargeImageText(largeImageTextTextField.getText())
                .setSmallImageKey(smallImageKeyTextField.getText())
                .setSmallImageText(smallImageTextTextField.getText());
        if(!startTimestampTextField.getText().equals("")) {
            long l = Long.parseLong(startTimestampTextField.getText());
            presenceBuilder.setStartTimestamp(l);
        }
        if(!endTimestampTextField.getText().equals("")) {
            long l = Long.parseLong(endTimestampTextField.getText());
            presenceBuilder.setStartTimestamp(l);
        }
        currentPresence = presenceBuilder.build();
    }
}
