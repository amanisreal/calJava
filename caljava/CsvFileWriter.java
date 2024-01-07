package com.example.caljava;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvFileWriter {

    private static final String CSV_FILE_NAME = "example.csv";

    public static String writeDataToCSV(Context context, List<String[]> data) {
        File directory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

        if (directory != null) {
            File csvFile = new File(directory, CSV_FILE_NAME);

            try {
                FileWriter writer = new FileWriter(csvFile);

                for (String[] row : data) {
                    for (int i = 0; i < row.length; i++) {
                        writer.append(row[i]);
                        if (i < row.length - 1) {
                            writer.append(",");
                        } else {
                            writer.append("\n");
                        }
                    }
                }

                writer.flush();
                writer.close();

                return csvFile.getAbsolutePath(); // Return the absolute path of the created CSV file
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}