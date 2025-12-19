package com.mycompany.chatapp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ResponsiveHelper {
    public static final Dimension LOGIN_SIZE = new Dimension(800, 600);
    public static final Dimension LOGIN_MIN_SIZE = new Dimension(600, 450);
    
    public static final Dimension CHAT_SIZE = new Dimension(1000, 700);
    public static final Dimension CHAT_MIN_SIZE = new Dimension(800, 500);
    
    public static final Dimension CONTACTS_SIZE = new Dimension(900, 600);
    public static final Dimension CONTACTS_MIN_SIZE = new Dimension(700, 450);
    
    public static final Dimension ADD_CONTACT_SIZE = new Dimension(700, 500);
    public static final Dimension ADD_CONTACT_MIN_SIZE = new Dimension(500, 400);
    
    
    public static ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        if (icon == null || icon.getImage() == null) {
            return icon;
        }
        
        Image originalImage = icon.getImage();
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();
        
        return new ImageIcon(scaledImage);
    }
    
    
    public static ImageIcon loadScaledImage(String resourcePath, int width, int height) {
        try {
            ImageIcon originalIcon = new ImageIcon(ResponsiveHelper.class.getResource(resourcePath));
            return scaleImage(originalIcon, width, height);
        } catch (Exception e) {
            System.err.println("Failed to load image: " + resourcePath);
            return null;
        }
    }
    
    
    public static void configureWindow(JFrame frame, Dimension preferredSize, Dimension minimumSize, boolean resizable) {
        frame.setPreferredSize(preferredSize);
        frame.setMinimumSize(minimumSize);
        frame.setResizable(resizable);
        frame.setLocationRelativeTo(null);
    }
    
    
    public static void addImageResizeListener(Component component, JLabel label, 
            ImageIcon originalIcon, double widthRatio, double heightRatio) {
        
        component.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int newWidth = (int) (component.getWidth() * widthRatio);
                int newHeight = (int) (component.getHeight() * heightRatio);
                
                if (newWidth > 0 && newHeight > 0) {
                    ImageIcon scaledIcon = scaleImage(originalIcon, newWidth, newHeight);
                    label.setIcon(scaledIcon);
                }
            }
        });
    }
    
    
    public static void centerOnScreen(JFrame frame) {
        frame.setLocationRelativeTo(null);
    }
}