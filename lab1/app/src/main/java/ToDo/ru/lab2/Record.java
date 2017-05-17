package ToDo.ru.lab2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class Record {

    public enum Priority {
        LOW,
        NORMAL,
        MAX;

        public static Priority fromInteger(int x) {
            switch(x) {
                case 0:
                    return LOW;
                case 1:
                    return NORMAL;
                case 2:
                    return MAX;
            }
            return null;
        }
    }

    private long id;
    private String title;
    private Priority priority;
    private String description;
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
            this.date = format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public Priority getPriority() {
        return priority;
    }

    public int getPriorityInt() {
        return priority.ordinal();
    }

    public void setPriority(Priority priority){
        this.priority = priority;
    }

    public void setPriority(Integer priority) {
        this.priority = Priority.fromInteger(priority);
    }
}