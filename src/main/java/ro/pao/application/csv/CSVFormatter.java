package ro.pao.application.csv;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CSVFormatter extends Formatter {

    private static final String CSV_SEPARATOR = ",";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @Override
    public String format(LogRecord record) {

        StringBuilder builder = new StringBuilder();
        builder.append(DATE_FORMAT.format(new Date(record.getMillis())));
        builder.append(CSV_SEPARATOR);
        builder.append(record.getLevel());
        builder.append(CSV_SEPARATOR);
        builder.append(formatMessage(record));
        builder.append(System.lineSeparator());
        return builder.toString();

    }

}
