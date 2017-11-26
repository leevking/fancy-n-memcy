//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
///**
// * Класс наследник JFrame, выполняющий роль графического интерфейса взаимодействия с пользователем
// */
//public class GUI extends JFrame {
//
//    /**
//     * Основное текстовое поле для вывода всех задач
//     */
//    private static JTextArea textArea;
//
//    /**
//     * Запуск графического интерфейса
//     */
//    public static void main(String[] args) {
//        GUI newGUI = new GUI();
//        newGUI.setVisible(true);
//    }
//
//    /**
//     * Конструктор графического интерфейса
//     * Создает основной фрейм и делит его на 2 панели
//     * Левая панель предназначена для textArea
//     * Правая панель предназначена для кнопок управления задачами
//     */
//    public GUI(){
//        super("Task Planner");
//        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        this.setSize(400,300);
//        CheckTimeDemon check = new CheckTimeDemon();
//        this.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent windowEvent) {
//                super.windowClosing(windowEvent);
//                Utility.pullAllTasks();
//                check.close();
//            }
//        });
//        this.setResizable(false);
//        JPanel mainPanel =new JPanel();
//        JPanel buttonPanel = new JPanel();
//        mainPanel.setLayout(new BorderLayout());
////        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
//        buttonPanel.setLayout(new GridLayout(4,1,20,20));
//
//
//        getTextArea();
//        mainPanel.add(new JScrollPane(this.textArea),BorderLayout.WEST);
//        mainPanel.add(buttonPanel,BorderLayout.CENTER);
//        this.setContentPane(mainPanel);
//
//        try {
//            Utility.readAllTasks();
//            refreshTextArea();
//            new Thread(check).start();
//        }catch (IOException |NullPointerException|ClassNotFoundException e){
//            e.printStackTrace();
//            textArea.setText("No tasks");
//        }
//
//        ActionListener buttonListener = new ButtonActionListener();
//
//        JButton addTaskB = new JButton("Add Task");
//        addTaskB.setActionCommand("addtask");
//        addTaskB.addActionListener(buttonListener);
//        buttonPanel.add(addTaskB);
//
//        JButton delTaskB = new JButton("Del Task");
//        delTaskB.setActionCommand("deltask");
//        delTaskB.addActionListener(buttonListener);
//        buttonPanel.add(delTaskB);
//    }
//
//    private void getTextArea(){
//        textArea = new JTextArea(10,20);
//        textArea.setLineWrap(true);
//        textArea.setBorder(BorderFactory.createEtchedBorder());
//        textArea.setFont(new Font("Colibri",Font.PLAIN,14));
//        textArea.setFocusable(false);
//        textArea.setVisible(true);
//    }
//
//    /**
//     * Внутренний приватный класс Listener привязанный к кнопкам
//     */
//    private class ButtonActionListener implements ActionListener{
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            switch (e.getActionCommand()) {
//                case "addtask":
//                    String bufString = AddTaskDialog();
//                    try {
//                        if((LocalDateTime.parse(bufString.split("\n", 3)[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm")).getDayOfYear()
//                                -LocalDateTime.now().getDayOfYear())<0)JOptionPane.showMessageDialog(GUI.this,"This date is expired","Input",JOptionPane.ERROR_MESSAGE);
//                        else {
//                            Utility.addTask(new SimpleTask(
//                                    bufString.split("\n", 3)[0],
//                                    bufString.split("\n", 3)[1],
//                                    LocalDateTime.parse(bufString.split("\n", 3)[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm"))));
//                        }
//                    }catch(java.time.format.DateTimeParseException e1){
//                        JOptionPane.showMessageDialog(GUI.this,"Wrong Input format","Input",JOptionPane.ERROR_MESSAGE);
//                    }
//                    refreshTextArea();
//                    break;
//                case "deltask":
//                    Utility.deleteTask((SimpleTask)JOptionPane.showInputDialog(GUI.this,"Choose task to delete",
//                            "Delete task",JOptionPane.QUESTION_MESSAGE,null,Utility.getAllTasks().toArray(),null));
//                    refreshTextArea();
//                    break;
//            }
//        }
//    }
//
//    /**
//     * Метод создающий новую задачу с помощью механизма последовательного вызова
//     * диалоговых окон для ввода необходимых данных
//     * @return возвращает строку, которая парсится для превращения в SimpleTask
//     */
//    private String AddTaskDialog(){
//        return new StringBuilder()
//                .append(JOptionPane.showInputDialog(GUI.this,"Input task name","Input",JOptionPane.QUESTION_MESSAGE)+'\n')
//                .append(JOptionPane.showInputDialog(GUI.this,"Input task description","Input",JOptionPane.QUESTION_MESSAGE)+'\n')
//                .append(JOptionPane.showInputDialog(GUI.this,"Input finish date yyyy-MM-dd HH-mm","Input",JOptionPane.QUESTION_MESSAGE)).toString();
//    }
//
//    /**
//     * Статический метод обновления текстового поля с задачами
//     */
//    public static void refreshTextArea(){
//        textArea.setText(Utility.allTasksToString());
//    }
//}
