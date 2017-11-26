import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DoItLaterDialog extends JFrame{
    private JTextArea textArea1;
    private JButton finishTask;
    private JButton doItLater;
    private JLabel label1;
    private JLabel label2;
    private JPanel mailPanel;
    private JPanel textPanel;
    SimpleTask task;

    public DoItLaterDialog(SimpleTask task){


        this.task = task;
        label1.setText(task.getName());
        label2.setText(task.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        textArea1.setText(task.getDescription());
        setSize(400,400);
        setLocation(new Point(400,200));
        setMinimumSize(new Dimension(300,300));
        setContentPane(mailPanel);
        setVisible(true);
        pack();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        ButtonActionListener buttonActionListener =  new ButtonActionListener();
        finishTask.addActionListener(buttonActionListener);
        finishTask.setActionCommand("finishtask");
        doItLater.addActionListener(buttonActionListener);
        doItLater.setActionCommand("doitlater");

    }

    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            synchronized (task) {
                switch (e.getActionCommand()) {
                    case "finishtask":
                        System.out.println("finishtask");
                        Utility.deleteTask(task);
                        task.notifyAll();
                        System.out.println("Уведомлены потоки");
                        dispose();
                        break;
                    case "doitlater":
                        //вынести в отдельный метод
                        System.out.println("doitlater");
                        task.setDate(LocalDateTime.now().plusDays(1));
                        task.notify();
                        dispose();
                        break;
                }
            }

        }
    }

}
