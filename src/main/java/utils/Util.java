package utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    public static String formatDate(Timestamp timestamp) {
        if(timestamp == null) {
            return "";
        }
        LocalDateTime datetime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");
        String str = datetime.format(formatter);
        return str;
    }

    public static void copyToClipBoard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
