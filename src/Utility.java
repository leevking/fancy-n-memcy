import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Класс содержащий в себе вспомогательные методы необходимые для работы с пулом задач и
 * записью в файл
 */
public class Utility {

    /** Имя файла для записи и сохранения задачи */
    private static final String SAVE_FILE_NAME = "1.dat";
    /** Количество задач в пуле */
    private static int taskCounts;
    /**Коллекция для хранения всех задач*/
    private static ArrayList<SimpleTask> taskArray;

    private static Iterator<SimpleTask> iterator;

    /**
     * Метод возвращает количество задач в пуле
     * @return количество задач
     */
    public static int getTaskCount(){
        return taskCounts;
    }

    /**
     * Метод возвращает коллекцию всех задач
     * @return ArrayList содержащий все задачи
     */
    public static ArrayList<SimpleTask> getAllTasks(){
        return taskArray;
    }

    /**
     * Метод добавления задачи в коллекцию
     * @param newTask - Новая задача для добавления
     */
    public static void addTask(SimpleTask newTask){
        if(taskArray==null){
            taskArray = new ArrayList<>();
        }
        taskArray.add(newTask);
        taskCounts++;
    }

    /**
     * Метод удаления задачи из коллекции
     * @param removeTask задача, которую нужно удалить из коллекции
     */
    public static void deleteTask(SimpleTask removeTask){
        taskArray.remove(removeTask);
        taskCounts--;
    }

    /**
     * Запись всех задач в файл с помощью сериализации коллекции в файл заданный константой
     */
    public static void pullAllTasks(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(SAVE_FILE_NAME)));
            oos.writeObject(taskArray);
        }
        catch (IOException e){

        }

    }

    public static void setTaskArray(ArrayList<SimpleTask> newList){
        taskArray = newList;
    }

    /**
     * Считывание всех задач из файла, заданного константой
     * @throws IOException выбрасывается при невозможности создать поток для считывания из файла
     * @throws ClassNotFoundException считываемый объект невозможно скастовать к ArrayList<SimpleTask>
     */
    public static void readAllTasks()throws IOException,ClassNotFoundException {
        ArrayList<SimpleTask> bufArr;
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(SAVE_FILE_NAME)));
            bufArr = (ArrayList<SimpleTask>)ois.readObject();
        taskArray = bufArr;
        if(taskArray==null){
            taskArray = new ArrayList<SimpleTask>();
        }
    }

    /**
     * Метод для преобразования всех задач в строку
     * @return возвращает строку со всеми задачами, с их полным описанием, названием и датой
     */
    public static String allTasksToString(){
        StringBuilder result = new StringBuilder();
        for(SimpleTask task: taskArray)result.append(task.taskToString()+'\n');
        return result.toString();
    }

//    public static String[] getTaskNames(){
//        String[] result = new String[taskArray.size()];
//        int i=0;
//        for(SimpleTask task: taskArray)result[i++]=task.getName()+" "+task.getDaysLeft();
//        return result;
//    }

    public static boolean checkActualTime(LocalDateTime date){
        System.out.println(date.getHour()-LocalDateTime.now().getHour());
        System.out.println((date.getDayOfYear()-LocalDateTime.now().getDayOfYear()));
        System.out.println(date.getYear()-LocalDateTime.now().getYear());
        System.out.println(date.getMinute()-LocalDateTime.now().getMinute());
        return ((date.getHour()-LocalDateTime.now().getHour())*60+(date.getDayOfYear()-LocalDateTime.now().getDayOfYear())*24*60
                +(date.getYear()-LocalDateTime.now().getYear())*365*24*60 + (date.getMinute()-LocalDateTime.now().getMinute()))>0;
    }

}
