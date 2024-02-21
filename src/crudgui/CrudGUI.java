package crudgui;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
/**
 *
 * @author Miguel Antonio
 */
public class CrudGUI extends JFrame {
    private List<Entidad> entidades;
    private JTable entidadTable;
    private DefaultTableModel tableModel;
    private JTextField idField;
    private JTextField nombreField;
    private JTextField tipoField;

    public CrudGUI() {
        entidades = new ArrayList<>();

        setTitle("CRUD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Campos de entrada para los datos de la entidad
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        inputPanel.add(nombreField);
        inputPanel.add(new JLabel("Tipo:"));
        tipoField = new JTextField();
        inputPanel.add(tipoField);

        add(inputPanel, BorderLayout.NORTH);

        // Crear modelo de tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Tipo");
        tableModel.addColumn("Marcado");

        entidadTable = new JTable(tableModel);
        entidadTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = entidadTable.getSelectedRow();
                if (selectedRow != -1) {
                    Entidad entidadSeleccionada = entidades.get(selectedRow);
                    idField.setText(entidadSeleccionada.getId());
                    nombreField.setText(entidadSeleccionada.getNombre());
                    tipoField.setText(entidadSeleccionada.getTipo());
                }
            }
        });
        add(new JScrollPane(entidadTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        // Botón para agregar una nueva entidad
        JButton addButton = new JButton("Agregar");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEntidad();
            }
        });
        buttonPanel.add(addButton);

        // Botón para eliminar una entidad
        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEntidad();
            }
        });
        buttonPanel.add(deleteButton);

        // Botón para modificar una entidad
        JButton editButton = new JButton("Modificar");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarEntidad();
            }
        });
        buttonPanel.add(editButton);

        // Botón para marcar o desmarcar una entidad
        JButton markButton = new JButton("Marcar/Desmarcar");
        markButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marcarDesmarcarEntidad();
            }
        });
        buttonPanel.add(markButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Método para agregar una nueva entidad
    private void agregarEntidad() {
        String id = idField.getText();
        String nombre = nombreField.getText();
        String tipo = tipoField.getText();
        if (!id.isEmpty() && !nombre.isEmpty() && !tipo.isEmpty()) {
            Entidad nuevaEntidad = new Entidad(id, nombre, tipo);
            entidades.add(nuevaEntidad);
            actualizarTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
        }
    }

    // Método para eliminar una entidad
    private void eliminarEntidad() {
        int selectedRow = entidadTable.getSelectedRow();
        if (selectedRow != -1) {
            Entidad entidadSeleccionada = entidades.get(selectedRow);
            if (!entidadSeleccionada.isMarcado()) {
                entidades.remove(selectedRow);
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "La entidad marcada no puede ser eliminada.");
            }
        }
    }

    // Método para modificar una entidad
    private void modificarEntidad() {
        int selectedRow = entidadTable.getSelectedRow();
        if (selectedRow != -1) {
            Entidad entidadSeleccionada = entidades.get(selectedRow);
            if (!entidadSeleccionada.isMarcado()) {
                String nuevoId = idField.getText();
                String nuevoNombre = nombreField.getText();
                String nuevoTipo = tipoField.getText();
                if (!nuevoId.isEmpty() && !nuevoNombre.isEmpty() && !nuevoTipo.isEmpty()) {
                    entidadSeleccionada.setId(nuevoId);
                    entidadSeleccionada.setNombre(nuevoNombre);
                    entidadSeleccionada.setTipo(nuevoTipo);
                    actualizarTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "La entidad marcada no puede ser modificada.");
            }
        }
    }

    // Método para marcar o desmarcar una entidad
    private void marcarDesmarcarEntidad() {
        int selectedRow = entidadTable.getSelectedRow();
        if (selectedRow != -1) {
            Entidad entidadSeleccionada = entidades.get(selectedRow);
            entidadSeleccionada.toggleMarcado();
            actualizarTabla();
        }
    }

    // Método para actualizar la tabla
    private void actualizarTabla() {
        // Limpiar la tabla
        tableModel.setRowCount(0);
        // Llenar la tabla con los datos actualizados de las entidades
        for (Entidad entidad : entidades) {
            Object[] rowData = {entidad.getId(), entidad.getNombre(), entidad.getTipo(), entidad.isMarcado()};
            tableModel.addRow(rowData);
        }
    }

    // Método para limpiar los campos de entrada
    private void limpiarCampos() {
        idField.setText("");
        nombreField.setText("");
        tipoField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CrudGUI();
            }
        });
    }
}
