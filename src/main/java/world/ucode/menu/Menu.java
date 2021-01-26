package world.ucode.menu;

import world.ucode.Constants;
import world.ucode.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu extends JPanel implements ActionListener {

    private List<String> menuItems;
    private String selectMenuItem;
    private int selectedMenuItemIndex;
    private String focusedItem;
    private MenuItemType selectedMenuItemType;

    private MenuItemPainter painter;
    private Map<String, Rectangle> menuBounds;

    private enum MenuItemType {NEW_GAME, HALL_OF_FAME, EXIT}

    public Menu() {
        setBackground(Color.darkGray);
        painter = new MenuItemPainter();
        menuItems = new ArrayList<>(25);
        menuItems.add("New Game");
        menuItems.add("Hall of Fame");
        menuItems.add("Exit");
        setSelectedMenuItemIndex(selectedMenuItemIndex);

        selectedMenuItemIndex = MenuItemType.NEW_GAME.ordinal();
        addKeyListener( new TAdapter());
        setFocusable(true);
    }

    @Override
    public void invalidate() {
        menuBounds = null;
        super.invalidate();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
    }

    public Dimension getPreferredSize(Graphics2D g2d, String text) {
        return g2d.getFontMetrics().getStringBounds(text, g2d).getBounds().getSize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        if (menuBounds == null) {
            menuBounds = new HashMap<>(menuItems.size());
            int width = 0;
            int height = 0;
            for (String text : menuItems) {
                Dimension dim = painter.getPreferredSize(g2d, text);
                width = Math.max(width, dim.width);
                height = Math.max(height, dim.height);
            }

            int x = (getWidth() - (width + 10)) / 2;

            int totalHeight = (height + 10) * menuItems.size();
            totalHeight += 5 * (menuItems.size() - 1);

            int y = (getHeight() - totalHeight) / 2;

            for (String text : menuItems) {
                menuBounds.put(text, new Rectangle(x, y, width + 10, height + 10));
                y += height + 10 + 5;
            }

        }
        for (String text : menuItems) {
            Rectangle bounds = menuBounds.get(text);
            boolean isSelected = text.equals(selectMenuItem);
            boolean isFocused = text.equals(focusedItem);
            painter.paint(g2d, text, bounds, isSelected, isFocused);
        }
        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e);
    }

    private void setSelectedMenuItemIndex(int index) {
        selectedMenuItemIndex = index;
        selectMenuItem = menuItems.get(index);
        switch (selectMenuItem) {
            case "New Game":
                selectedMenuItemType = MenuItemType.NEW_GAME;
                break;
            case "Hall of Fame":
                selectedMenuItemType = MenuItemType.HALL_OF_FAME;
                break;
            default:
                selectedMenuItemType = MenuItemType.EXIT;
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_ENTER) {
                if (selectedMenuItemType == MenuItemType.EXIT)
                    System.exit(0);
                if (selectedMenuItemType == MenuItemType.NEW_GAME) {
                    setFocusable(false);
                    Main.shared().startNewGame();
                }
                if (selectedMenuItemType == MenuItemType.HALL_OF_FAME) {
                    setFocusable(false);
                    Main.shared().showHall();
                }
                return;
            }

            if (key == KeyEvent.VK_DOWN) {
                selectedMenuItemIndex++;
                selectedMenuItemIndex = selectedMenuItemIndex % menuItems.size();
            }

            if (key == KeyEvent.VK_UP) {
                selectedMenuItemIndex--;
                selectedMenuItemIndex = selectedMenuItemIndex < 0 ? 2 : selectedMenuItemIndex;
                setSelectedMenuItemIndex(selectedMenuItemIndex);
            }
            setSelectedMenuItemIndex(selectedMenuItemIndex);
            repaint();
        }
    }
}