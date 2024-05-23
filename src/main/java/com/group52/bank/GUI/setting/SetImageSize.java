package com.group52.bank.GUI.setting;

import javax.swing.*;
import java.awt.*;

public class SetImageSize {
    ImageIcon originalImageIcon;
    ImageIcon scaledImageIcon;
    int scaledWidth;
    int scaledHeight;

    /**
     * Constructs a new SetImageSize with the given original image icon and dimensions.
     *
     * @param originalImageIcon the original image icon
     * @param width the width to scale the image to
     * @param height the height to scale the image to
     */
    public SetImageSize(ImageIcon originalImageIcon,int width, int height){
        this.originalImageIcon = originalImageIcon;
        scaledWidth = width;
        scaledHeight = height;
        Image originalImage = originalImageIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        scaledImageIcon = new ImageIcon(scaledImage);
    }

    /**
     * Constructs a new SetImageSize with the given original image icon and scale.
     *
     * @param originalImageIcon the original image icon
     * @param scale the scale to resize the image to
     */
    public SetImageSize(ImageIcon originalImageIcon, double scale){
        this.originalImageIcon = originalImageIcon;
        scaledWidth = (int)(originalImageIcon.getIconWidth() * scale);
        scaledHeight = (int)(originalImageIcon.getIconHeight() * scale);
        Image originalImage = originalImageIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        scaledImageIcon = new ImageIcon(scaledImage);
    }

    /**
     * Returns the scaled image.
     *
     * @return the scaled image
     */
    public ImageIcon getScaledImage(){
        return scaledImageIcon;
    }
}
