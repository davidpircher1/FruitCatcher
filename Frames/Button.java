package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * trieda Button je vlastna rozsirená verzia {@link JButton},
 * ktora umoznuje jednoduche vytvorenie tlacidla s prednastavenym stylom.
 */
public class Button extends JButton {

    /**
     * konstruktor vytvori nove tlacidlo s danym textom, velkostou, farbami a listenerom.
     *
     * @param text text zobrazeny na tlacidle
     * @param width sirka tlacidla
     * @param height vyska tlacidla
     * @param bgColor farba pozadia tlacidla
     * @param fgColor farba textu tlacidla
     * @param action listener, ktory reaguje na kliknutie
     */
    public Button(String text, int width, int height, Color bgColor, Color fgColor, ActionListener action) {
        super(text);
        setPreferredSize(new Dimension(width, height));
        setBackground(bgColor);
        setForeground(fgColor);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Arial", Font.BOLD, 14));
        addActionListener(action);
    }
}

