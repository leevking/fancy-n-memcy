import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Класс описывающий самый простой тип задачи
 */
public class SimpleTask implements Task,Serializable {

    /**Поле название задачи*/
    private String name;
    /**Поле описание задачи*/
    private String description;
    /**Предполагаемое время завершения задачи*/
    private LocalDateTime date;

    /**
     * Конструктор создающий экземпляр класса задачи с параметрами
     * @param name - Название новой задачи
     * @param description - Описание новой задачи
     * @param date - Предполагаемое время завершения новой задачи
     */
    public SimpleTask(String name, String description, LocalDateTime date ){
        this.name = name;
        this.description=description;
        this.date = date;
    }

    /**
     *Конструктор создания экземпляра класса задачи по умолчанию
     * Название задается как NewTask + номер задачи
     * Описание - Description is missing
     * Время - текущее время
     */
    public SimpleTask(){
        this.name = "NewTask"+Utility.getTaskCount();
        this.description = "Description is missing";
        LocalDateTime now = LocalDateTime.now();
        this.date = now.withDayOfYear(now.getDayOfYear()+1);
    }

    /**
     * Геттер названия задачи
     * @return возвращает название задачи
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Геттер описания задачи
     * @return возвращает описание задачи
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Геттер даты завершения задачи
     * @return возвращает дату завершения текущей задачи в формате LocalDateTime
     */
    @Override
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Сеттер названия задачи
     * @param name - новое название задачи
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Сеттер описания задачи
     * @param description - новое описание задачи
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Сеттер даты завершения задачи
     * @param date - новая дата завершения задачи в формате LocalDateTime
     */
    @Override
    public void setDate(LocalDateTime date ) {
        this.date = date;
    }

    /**
     * Метод возвращающий количество дней до завершения задачи
     * @return количество дней до завершения задачи
     */
    @Override
    public int getDaysLeft(){
        return date.getDayOfYear()-LocalDateTime.now().getDayOfYear()
        +(date.getYear()-LocalDateTime.now().getYear())*365;
    }

    /**
     * Метод возвращающий количество часов до завершения задачи
     * @return количество часов до завершения задачи
     */
    public int getHoursLeft(){
        return date.getHour()-LocalDateTime.now().getHour()+(date.getDayOfYear()-LocalDateTime.now().getDayOfYear())*24
                +(date.getYear()-LocalDateTime.now().getYear())*365*24;
    }

    /**
     * Преобразование задачи в строку, используемое некоторыми методами по умолчанию
     * @return возвращает строку название задачи + количество дней до завершения
     */
    @Override
    public String toString(){
        return this.name+' '
                + this.getDate().getDayOfMonth()+'-'
                + this.getDate().getMonthValue()+'-'
                + this.getDate().getYear()+' '
                + this.getDate().getHour()+':'
                + this.getDate().getMinute();
    }

    /**
     * Преобразование задачи в строку. Полное
     * @return возвращает название, описание и дату завершения задачи в формте String
     */
    public String taskToString(){
        return this.name+'\n'+this.description+'\n'+this.date+'\n';
    }
}
