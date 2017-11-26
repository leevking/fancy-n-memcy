import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Интерфейс для общего типа задач
 */
public interface Task {

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    LocalDateTime getDate();

    void setDate(LocalDateTime date);

    int getDaysLeft();

    String toString();

}
