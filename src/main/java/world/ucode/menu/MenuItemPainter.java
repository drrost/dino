package world.ucode.menu;

import java.awt.*;

public class MenuItemPainter {

    public Dimension getPreferredSize(Graphics2D g2d, String text) {
        return g2d.getFontMetrics().getStringBounds(text, g2d).getBounds().getSize();
    }

    public void paint(Graphics2D g2d, String text, Rectangle bounds,
                      boolean isSelected, boolean isFocused) {
        FontMetrics fontMetrics = g2d.getFontMetrics();
        if (isSelected) {
            paintBackground(g2d, bounds, Color.gray, Color.white);
        } else if (isFocused) {
            paintBackground(g2d, bounds, new Color(148, 148, 148), Color.black);
        } else {
            paintBackground(g2d, bounds, Color.darkGray, Color.lightGray);
        }
        int x = bounds.x + ((bounds.width - fontMetrics.stringWidth(text)) / 2);
        int y = bounds.y + ((bounds.height - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();
        g2d.setColor(isSelected ? Color.white : Color.lightGray);
        g2d.drawString(text, x, y);
    }

    protected void paintBackground(
        Graphics2D g2d, Rectangle bounds, Color background, Color foreground) {
        g2d.setColor(background);
        g2d.fill(bounds);
        g2d.setColor(foreground);
        g2d.draw(bounds);
    }
}