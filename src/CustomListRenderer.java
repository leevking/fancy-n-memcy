import javax.swing.*;
import java.awt.*;

public class CustomListRenderer extends JLabel implements ListCellRenderer<SimpleTask> {


    public CustomListRenderer(){
        setOpaque(true);
    }
    @Override
    public Component getListCellRendererComponent(JList<? extends SimpleTask> list, SimpleTask simpleTask, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        setFont(new Font("Garamond", Font.ITALIC,16));
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        setText(simpleTask.toString());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }

}
