package rtb;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

class TweetDate {
    private static DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    private LocalDate date;
    
    TweetDate(String date) {
        if (date == null) throw new NullPointerException();
        this.date = LocalDate.parse(date, formatter);
    }
    
    TweetDate(Date date) {
        if (date == null) throw new NullPointerException();
        Instant instant = date.toInstant();
        this.date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                                 .toLocalDate();
    }
    
    void addTo(StringBuilder builder) {
        if (builder == null) throw new NullPointerException();
        builder.append("[")
               .append(date.format(formatter))
               .append("]");
    }
}
