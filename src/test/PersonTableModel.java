package test;

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import domain.Person;

public class PersonTableModel implements TableModel {
    private List<Person> persons;

    public PersonTableModel(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int index) {
        switch(index) {
            case 0: return "ID";
            case 1: return "Фамилия и инициалы";
            case 2: return "Рост";
            case 3: return "Вес";
            case 4: return "Гражданство";
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int index) {
        switch(index) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
            case 3:
                return Double.class;
            case 4:
                return Boolean.class;
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return persons.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int colIndex) {
        Person person = persons.get(rowIndex);
        switch(colIndex) {
            case 0:
                return person.getId();
            case 1:
                return person.getLastName() + " "
                     + person.getFirstName().charAt(0) + ". "
                     + person.getMiddleName().charAt(0) + ".";
            case 2:
                return person.getHeight();
            case 3:
                return person.getWeight();
            case 4:
                return person.isCitizen();
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int colIndex) {}

    @Override
    public boolean isCellEditable(int rowIndex, int colIndex) {
        return false;
    }

    @Override
    public void addTableModelListener(TableModelListener listener) {}

    @Override
    public void removeTableModelListener(TableModelListener listener) {}
}