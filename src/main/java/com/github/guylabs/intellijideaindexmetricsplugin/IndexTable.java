package com.github.guylabs.intellijideaindexmetricsplugin;

import com.github.guylabs.intellijideaindexmetricsplugin.services.IndexDataStorage;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class IndexTable {

    private JPanel windowContent;
    private JTable statisticsTable;
    private JButton refresh;
    private JLabel total;

    public IndexTable() {
        refresh.addActionListener(e -> {
            System.err.println("refresh");
            refresh();
        });
        refresh();
    }

    public void refresh() {
        IndexDataStorage instance = IndexDataStorage.Companion.getInstance();
        IndexDataStorage state = instance.getState();
        var totalIndexTime = state.getTotalIndexTime();
        total.setText("Total index time: " + totalIndexTime);
        refresh.setText("Refresh");

        Map<String, Map<String, String>> indexExecutions = state.getIndexExecutions();
        Object[] columnNames = {"#", "Project", "StartTime", "Reason", "Duration", "WasFull", "ScansFileDuration"};
        Object[] labels = {"#", "project", "updatingStart", "indexingReason", "totalTime", "wasFullIndexing", "scanFilesDuration"};

        ArrayList<Map<String, String>> executions = new ArrayList<>(indexExecutions.values());
        executions.sort(Comparator.<Map<String, String>, Long>comparing(m -> Long.parseLong(m.get("updatingStart"))).reversed());

        var model = new AbstractTableModel() {
            public String getColumnName(int column) {
                return columnNames[column].toString();
            }

            public int getRowCount() {
                return executions.size() + 1;
            }

            public int getColumnCount() {
                return columnNames.length;
            }

            public Object getValueAt(int row, int col) {
                if (row == 0) {
                    return getColumnName(col);
                }
                if (col == 0) {
                    return row;
                }
                var execution = executions.get(row - 1);
                var value = execution.get(labels[col]);
                if (col == 2) {
                    return Instant.ofEpochMilli(Long.parseLong(value)).truncatedTo(ChronoUnit.SECONDS);
                }
                return value;
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }

            public void setValueAt(Object value, int row, int col) {
                throw new UnsupportedOperationException();
            }
        };

        statisticsTable.setModel(model);
        statisticsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    public JPanel getContent() {
        return windowContent;
    }
}
