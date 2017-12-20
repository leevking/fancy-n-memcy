import sun.java2d.pipe.AAShapePipe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

public class CheckTimeDemon extends TimerTask implements Runnable {

    private boolean isClosed;
    private MainGUI GUI;
    private ArrayList<SimpleTask> taskArrayList = new ArrayList<SimpleTask>();
    private SimpleTask bufTask;

    public CheckTimeDemon(MainGUI GUI) {
        this.GUI = GUI;
        isClosed = false;
        refreshList();
    }

    public void refreshList(){
        taskArrayList=Utility.getAllTasks();
    }
    public void run() {
        refreshList();
            while (!isClosed) {
                refreshList();
                if (!taskArrayList.isEmpty()) {
                    try {
                        SimpleTask min = taskArrayList.get(0);

                        for (SimpleTask aTaskArrayList : taskArrayList) {
                            //synchronized (bufTask=iterator.next()) {
                            bufTask = aTaskArrayList;

                            if (bufTask.getMinuteLeft() < min.getMinuteLeft()) min = bufTask;
                            //bufTask.wait();

                            //else if(bufTask.getMinuteLeft()<minSleepTime)minSleepTime=bufTask.getMinuteLeft();
                            // }

                        }
                        if (!Utility.checkActualTime(min.getDate()))
                            if (new DoItLaterDialog(GUI, min).getAction()) taskArrayList.remove(min);
                        Utility.setTaskArray(taskArrayList);
                        GUI.refreshList();
                        System.out.println("sleeps mins "+min.getMinuteLeft());
                        if(min.getMinuteLeft()>0)Thread.sleep(min.getMinuteLeft()*60*1000);
                    } catch (InterruptedException | ConcurrentModificationException e) {
                        //e.printStackTrace();
                        System.out.println("перезапуск проверки времмени");
                    }
                }
            }
        }


    public  void setClosed(){
        isClosed = true;
        System.out.println("closed");
    }


}
