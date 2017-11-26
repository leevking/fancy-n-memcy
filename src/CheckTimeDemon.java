import sun.java2d.pipe.AAShapePipe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class CheckTimeDemon implements Runnable {

    private boolean isClosed;
    private MainGUI GUI;
    private static ArrayList<SimpleTask> taskArrayList;
    public CheckTimeDemon(MainGUI GUI) {
        this.GUI = GUI;
        isClosed = false;
        refreshList();
    }

    public static void refreshList(){
        taskArrayList=Utility.getAllTasks();
    }
    public void run() {
            while (!isClosed) {
                try {
                    for (SimpleTask task : taskArrayList) {
                        synchronized (task) {
                            if (task.getHoursLeft() < 24) {
                                new DoItLaterDialog(task);
                                System.out.println("Ожидаем потоки");
                                task.wait();

                                System.out.println("Дождались потока");
                                GUI.refreshList();
                                Thread.sleep(5000);
                                System.out.println("sleeps");
                            }
                        }
                    }
                } catch (InterruptedException | ConcurrentModificationException e) {
                    e.printStackTrace();
                }
                refreshList();
            }
        }


    public  void setClosed(){
        isClosed = true;
        System.out.println("closed");
    }
}
