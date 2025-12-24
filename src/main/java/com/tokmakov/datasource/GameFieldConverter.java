package com.tokmakov.datasource;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GameFieldConverter implements AttributeConverter<int[][], String> {
    @Override
    public String convertToDatabaseColumn(int[][] attribute) {
        if (attribute == null) {
            return null;
        }
        if (attribute.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < attribute.length; row++) {
            if (row > 0) {
                builder.append(';');
            }
            int[] rowData = attribute[row];
            for (int col = 0; col < rowData.length; col++) {
                if (col > 0) {
                    builder.append(',');
                }
                builder.append(rowData[col]);
            }
        }

        return builder.toString();
    }

    @Override
    public int[][] convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        if (dbData.isEmpty()) {
            return new int[0][0];
        }
        String[] rows = dbData.split(";");
        int[][] result = new int[rows.length][];
        for (int row = 0; row < rows.length; row++) {
            if (rows[row].isEmpty()) {
                result[row] = new int[0];
                continue;
            }
            String[] cols = rows[row].split(",");
            int[] rowData = new int[cols.length];
            for (int col = 0; col < cols.length; col++) {
                rowData[col] = Integer.parseInt(cols[col]);
            }
            result[row] = rowData;
        }
        return result;
    }
}
