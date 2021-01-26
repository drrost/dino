package world.ucode.menu;

import world.ucode.Constants;

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
        setSelectMenuItem(menuItems.get(0));

        MouseAdapter ma = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String newItem = null;
                for (String text : menuItems) {
                    Rectangle bounds = menuBounds.get(text);
                    if (bounds.contains(e.getPoint())) {
                        newItem = text;
                        break;
                    }
                }
                if (newItem != null && !newItem.equals(selectMenuItem)) {
                    setSelectMenuItem(newItem);
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                focusedItem = null;
                for (String text : menuItems) {
                    Rectangle bounds = menuBounds.get(text);
                    if (bounds.contains(e.getPoint())) {
                        focusedItem = text;
                        repaint();
                        break;
                    }
                }
            }
        };

        addMouseListener(ma);
        addMouseMotionListener(ma);

        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "arrowDown");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "arrowUp");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");

        am.put("arrowDown", new MenuAction(1, KeyEvent.VK_DOWN));
        am.put("arrowUp", new MenuAction(-1, KeyEvent.VK_UP));
        am.put("enter", new MenuAction(0, KeyEvent.VK_ENTER));

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

    private void setSelectMenuItem(String item) {
        selectMenuItem = item;
        switch (item) {
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

    public class MenuAction extends AbstractAction {

        private final int delta;
        private final int keyCode;

        public MenuAction(int delta, int keyCode) {
            this.delta = delta;
            this.keyCode = keyCode;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = menuItems.indexOf(selectMenuItem);
            if (keyCode == KeyEvent.VK_ENTER) {
                handleEnter();
                return;
            }

            if (index < 0) {
                setSelectMenuItem(menuItems.get(0));
            }
            index += delta;
            if (index < 0) {
                setSelectMenuItem(menuItems.get(menuItems.size() - 1));
            } else if (index >= menuItems.size()) {
                setSelectMenuItem(menuItems.get(0));
            } else {
                setSelectMenuItem(menuItems.get(index));
            }
            repaint();
        }

        private void handleEnter() {
            if (selectedMenuItemType == MenuItemType.EXIT) {
                System.exit(0);
            }
        }
    }
}