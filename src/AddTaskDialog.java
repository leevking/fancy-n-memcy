import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddTaskDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField inputTaskName;
    private JTextArea inputTaskDescription;
    private JTextField inputTaskDate;
    private SimpleTask task;
    public AddTaskDialog(SimpleTask task) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        ButtonActionListener buttonListener = new ButtonActionListener();
//        contentPane.add(new JScrollPane(this.inputTaskDescription));
        buttonOK.addActionListener(buttonListener);
        buttonCancel.addActionListener(buttonListener);
        this.task=task;
        fillWithTaskInfo();
        setSize(400,300);
        setMinimumSize(new Dimension(300,300));
        setLocation(new Point(400,200));
        setVisible(true);
        pack();




        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);



    }



    private void onOK() {
        LocalDateTime date=null;
        LocalDate date1;
        try{
            date1 = LocalDate.parse(inputTaskDate.getText(),DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            System.out.println("parsed date1");
            date = LocalDateTime.parse(inputTaskDate.getText()+" 23:59",DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            System.out.println("changed format");

        }catch (java.time.format.DateTimeParseException e){
            try {
                date = LocalDateTime.parse(inputTaskDate.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

            }catch (java.time.format.DateTimeParseException e1){
                JOptionPane.showMessageDialog(this, "Wrong date format","Error message", JOptionPane.ERROR_MESSAGE);
                date = null;
            }

        }
        finally {
            if(date!=null)
                if(chechActualTime(date)) {
                    if (task == null) {
                        Utility.addTask(new SimpleTask(
                                inputTaskName.getText(),
                                inputTaskDescription.getText(),
                                date));
                        dispose();
                    } else {
                        task = new SimpleTask(inputTaskName.getText(), inputTaskDescription.getText(), date);
                        dispose();
                    }
                }else JOptionPane.showMessageDialog(this, "Looks like your date is in the past", "Wrong date", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void fillWithTaskInfo(){
            if(task!=null) {
                inputTaskName.setText(task.getName());
                inputTaskDescription.setText(task.getDescription());
                inputTaskDate.setText(task.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            }

    }

    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "OK":
                    onOK();
                    break;
                case "Cancel":
                    onCancel();
                    break;
            }
        }
    }

    private boolean chechActualTime(LocalDateTime date){
        return (date.getHour()-LocalDateTime.now().getHour()+(date.getDayOfYear()-LocalDateTime.now().getDayOfYear())*24
                +(date.getYear()-LocalDateTime.now().getYear())*365*24>0);
    }




}
