package com.github.guylabs.intellijideaindexmetricsplugin;

import com.github.guylabs.intellijideaindexmetricsplugin.services.IndexDataStorage;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
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

        Map<Long, Map<String, String>> indexExecutions = state.getIndexExecutions();
        ArrayList<Long> sessionIds = new ArrayList<>(indexExecutions.keySet());
        Collections.sort(sessionIds);
        Object[] columnNames = {"#", "Reason", "Duration", "WasFull", "ScansFileDuration"};
        Object[] labels = {"#", "indexingReason", "totalTime", "wasFullIndexing", "scanFilesDuration"};

        var model = new AbstractTableModel() {
            public String getColumnName(int column) {
                return columnNames[column].toString();
            }

            public int getRowCount() {
                return sessionIds.size();
            }

            public int getColumnCount() {
                return columnNames.length;
            }

            public Object getValueAt(int row, int col) {
                var sessionId = sessionIds.get(row);
                if (col == 0) {
                    return sessionId;
                }
                Map<String, String> stats = indexExecutions.get(sessionId);
                return stats.get(labels[col]);
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }

            public void setValueAt(Object value, int row, int col) {
                throw new UnsupportedOperationException();
            }
        };

        statisticsTable.setModel(model);
    }

    public JPanel getContent() {
        return windowContent;
    }
}
