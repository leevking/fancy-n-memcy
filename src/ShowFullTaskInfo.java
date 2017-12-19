import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

public class ShowFullTaskInfo extends JFrame{
    private JPanel panel1;
    private JTextArea textArea1;
    private JLabel label1;
    private JLabel label2;
    private JButton closeButton;

    public ShowFullTaskInfo(SimpleTask task){
        label1.setText(task.getName());
        label2.setText(task.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        textArea1.setText(task.getDescription());

        setContentPane(panel1);
        setSize(400,400);
        setLocation(new Point(400,200));
        setMinimumSize(new Dimension(300,150));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        setVisible(true);
    }
}
