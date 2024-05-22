package com.group52.bank.GUI.setting;

import javax.swing.*;
import java.awt.*;

public class SetImageSize {
    ImageIcon originalImageIcon;
    ImageIcon scaledImageIcon;
    int scaledWidth;
    int scaledHeight;
    public SetImageSize(ImageIcon originalImageIcon,int width, int height){
        this.originalImageIcon = originalImageIcon;
        scaledWidth = width;
        scaledHeight = height;
        Image originalImage = originalImageIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        scaledImageIcon = new ImageIcon(scaledImage);
    }
    public SetImageSize(ImageIcon originalImageIcon, double scale){
        this.originalImageIcon = originalImageIcon;
        scaledWidth = (int)(originalImageIcon.getIconWidth() * scale);
        scaledHeight = (int)(originalImageIcon.getIconHeight() * scale);
        Image originalImage = originalImageIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        scaledImageIcon = new ImageIcon(scaledImage);
    }
    public ImageIcon getScaledImage(){
        return scaledImageIcon;
    }
}
