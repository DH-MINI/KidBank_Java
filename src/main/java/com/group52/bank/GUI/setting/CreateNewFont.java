package com.group52.bank.GUI.setting;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/* Select new fonts */
public class CreateNewFont {
    Font font;
    public CreateNewFont(String name, float size) throws IOException, FontFormatException {
        switch(name){
            case "Pacifico":
                font = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/Pacifico/Pacifico-Regular.ttf"));
                break;
            case "PermanentMarker":
                font = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/Permanent_Marker/PermanentMarker-Regular.ttf"));
                break;
            case "Caveat":
                font = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/Caveat/Caveat-VariableFont_wght.ttf"));
                break;
            case "BungeeSpice":
                font = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/Bungee_Spice/BungeeSpice-Regular.ttf"));
                break;
            default:
                font = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/Poetsen_One/PoetsenOne-Regular.ttf"));
                break;
        }
        font = font.deriveFont(size);
    }
    public Font getFont(){
        return font;
    }
    public static void main(String[] args) throws IOException, FontFormatException {
        JLabel label = new JLabel("Hello World");
        JFrame frame = new JFrame();
        label.setFont(new CreateNewFont("BungeeSpice", 50f).getFont());
        frame.getContentPane().add(label);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
