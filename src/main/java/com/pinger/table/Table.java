package com.pinger.table;

import java.util.ArrayList;
import java.util.List;

public abstract class Table implements ITable {

    private List<Object> headers = new ArrayList<>();
    private List<List<Object>> rows = new ArrayList<>();
    private final static int SPACES_PADDING = 4;

    public abstract void setData();

    public void addRow(List<Object> row) {
        rows.add(row);
    }

    public List<Object> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Object> headers) {
        this.headers = headers;
    }

    public List<List<Object>> getRows() {
        return rows;
    }

    public void setRows(List<List<Object>> rows) {
        this.rows = rows;
    }

    @Override
    public String convertToHtmlTable() {
        setData();
        return null; //TODO
    }

    @Override
    public String convertToTextTable() {
        setData();
        final StringBuilder builder = new StringBuilder();
        final List<Integer> columnsWidth = calculateColumnsWidth();

        builder.append(createTextCellRow(headers, columnsWidth));
        for (final List<Object> row : rows) {
            builder.append(createTextCellRow(row, columnsWidth));
        }

        return builder.toString();
    }

    private List<Integer> calculateColumnsWidth() {
        final List<Integer> maxColumnsWidth = new ArrayList<>();
        final List<List<Object>> columnsValues = getColumnsValues();
        for (List<Object> columnValues : columnsValues) {
            maxColumnsWidth.add(calculateMaxColumnWidth(columnValues));
        }
        return maxColumnsWidth;
    }

    private List<List<Object>> getColumnsValues() {
        final List<List<Object>> columnsValues = new ArrayList<>();

        for (int i = 0; i < headers.size(); i++) {
            final List<Object> columnValues = new ArrayList<>();
            columnValues.add(headers.get(i));
            for (final List<Object> rowValues : rows) {
                columnValues.add(rowValues.get(i));
            }
            columnsValues.add(columnValues);
        }
        return columnsValues;
    }

    private int calculateMaxColumnWidth(final List<Object> columnValues) {
        int maximumWidth = 0;
        for (Object value : columnValues) {
            if (value != null ) {
                int valueWidth = value.toString().length();
                if (valueWidth > maximumWidth) {
                    maximumWidth = valueWidth;
                }
            }
        }
        return maximumWidth;
    }

    private String multiplyChar(final char character, final int times) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; i++) {
            builder.append(character);
        }
        return builder.toString();
    }

    private String createTextCellRow(final List<Object> values, final List<Integer> maxColumnWidths) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            final Object value = values.get(i);
            final Integer maxColumnWidth = maxColumnWidths.get(i);
            final int valueWidth = value == null ? 0 : value.toString().length();

            int spaceMultiplier = maxColumnWidth - valueWidth + SPACES_PADDING;
            builder.append(createTextCell(value, spaceMultiplier));
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    private String createTextCell(final Object value, final int spaceMultiplier) {
        if (value == null || value.toString().length() == 0) {
            return multiplyChar(' ', spaceMultiplier);
        }
        else {
            return value + multiplyChar(' ', spaceMultiplier);
        }
    }

}
