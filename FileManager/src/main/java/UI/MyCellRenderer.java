package UI;

import javax.swing.*;
import java.awt.*;

public class MyCellRenderer extends JLabel implements ListCellRenderer {
    Icon[] icons;

    public MyCellRenderer() {

    }
    public MyCellRenderer(Icon[] icons) {
        this.icons=icons;
    }
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String s=value.toString();
        setText(s);
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setIcon(icons[index]);
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }
}
