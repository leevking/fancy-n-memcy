import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Iterator;

public class MainGUI extends JFrame {
    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JList list1;
    private JPanel textfieldpanel;

    private MainGUI(){
        CheckTimeDemon check = new CheckTimeDemon(this);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                Utility.pullAllTasks();
                check.setClosed();
            }
        });

        try {
            Utility.readAllTasks();
            refreshList();
            new Thread(check).start();
        }catch(IOException|ClassNotFoundException e){
            e.printStackTrace();
        }

        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600,400);
        setMinimumSize(new Dimension(400,200));
        setVisible(true);



        ButtonActionListener buttonListener = new ButtonActionListener();
        button1.addActionListener(buttonListener);
        button2.addActionListener(buttonListener);
        button3.addActionListener(buttonListener);

//        scrollpane1 = new JScrollPane(list1);
//        textfieldpanel.add(scrollpane1);
        //scrollpane1.setVisible(true);
//        scrollpane1.createVerticalScrollBar();
//        scrollpane1.createHorizontalScrollBar();
//        scrollpane1.setWheelScrollingEnabled(true);



        list1.setCellRenderer(new CustomListRenderer());
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount()==2){
                    new ShowFullTaskInfo((SimpleTask)list1.getSelectedValue());
                }
            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(listSelectionEvent.getValueIsAdjusting())button2.setEnabled(false);
                else button2.setEnabled(true);
            }
        });

        panel1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                list1.setVisibleRowCount(getSize().height / 30);
            }
        });
    }

    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "addtask":
                    new AddTaskDialog(null);
                    break;

                case "deltask":
                    Utility.deleteTask((SimpleTask)list1.getSelectedValue());
                    break;

                case "changetask":
                    new AddTaskDialog((SimpleTask)list1.getSelectedValue());
                    break;
            }
            refreshList();
        }
    }



    public void refreshList(){
        DefaultListModel model = new DefaultListModel();
        for(Iterator<SimpleTask> iterator = Utility.getAllTasks().iterator(); iterator.hasNext();){
            model.addElement(iterator.next());
        }
        list1.setModel(model);
    }


    public static void main(String[] args) {
        new MainGUI();
    }
}
