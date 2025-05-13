package com.iuniverstaria.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.iuniverstaria.dao.GrupoFamiliarDAO;
import com.iuniverstaria.model.GrupoFamiliar;

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.border.TitledBorder;

public class GrupoFamiliarView extends JFrame {
 
    private JPanel panelMain;
    private JPanel panelFormulario;
    private JPanel panelBotones;
    
    private JTextField txtIdFamiliar; 
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtParentesco; 
    private JTextField txtFechaNacimiento; 

    private JTable tableFamiliares;
    private DefaultTableModel modelFamiliares;
    private JScrollPane scrollPaneFamiliares;

    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnBorrar;
    private JButton btnRefrescar;

    private GrupoFamiliarDAO grupoFamiliarDAO;
    private int idFuncionarioPadre; // 
    private int idFamiliarSeleccionado = -1; 


    public GrupoFamiliarView(int idFuncionario) {
        this.idFuncionarioPadre = idFuncionario; 
        setTitle("Grupo Familiar para Funcionario ID: " + idFuncionarioPadre);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500); 
        setLocationRelativeTo(null); 

        grupoFamiliarDAO = new GrupoFamiliarDAO();

        initComponents();
        setupLayout();
        setupListeners();
        listarFamiliares();
        setVisible(true);
    }

    private void initComponents() {
      
        panelMain = new JPanel();
        panelFormulario = new JPanel();
        panelBotones = new JPanel();
        txtIdFamiliar = new JTextField(5);
        txtIdFamiliar.setEditable(false); //
        txtIdFamiliar.setBackground(Color.LIGHT_GRAY);
        txtNombres = new JTextField(20);
        txtApellidos = new JTextField(20);
        txtParentesco = new JTextField(15); 
        txtFechaNacimiento = new JTextField(10); 

        modelFamiliares = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 
            }
        };

        modelFamiliares.addColumn("ID Familiar"); 
        modelFamiliares.addColumn("Nombres"); 
        modelFamiliares.addColumn("Apellidos"); 
        modelFamiliares.addColumn("Parentesco");
        modelFamiliares.addColumn("Fecha Nacimiento"); 

        tableFamiliares = new JTable(modelFamiliares);
        tableFamiliares.setAutoCreateRowSorter(true); 

        scrollPaneFamiliares = new JScrollPane(tableFamiliares);
        scrollPaneFamiliares.setPreferredSize(new Dimension(750, 200)); 

        btnNuevo = new JButton("Nuevo");
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnBorrar = new JButton("Borrar");
        btnRefrescar = new JButton("Refrescar");
    }

    private void setupLayout() {
        panelMain.setLayout(new BorderLayout());

        panelFormulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        panelFormulario.setBorder(new TitledBorder("Datos del Familiar"));

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("ID Familiar:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(txtIdFamiliar, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Nombres:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtNombres, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Apellidos:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtApellidos, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Parentesco:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtParentesco, gbc);

        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Fecha Nacimiento:"), gbc);
        gbc.gridx = 3; gbc.gridy = 2; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtFechaNacimiento, gbc);


        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnBorrar);
        panelBotones.add(btnRefrescar);

        panelMain.add(panelFormulario, BorderLayout.NORTH);
        panelMain.add(scrollPaneFamiliares, BorderLayout.CENTER);
        panelMain.add(panelBotones, BorderLayout.SOUTH);

        setContentPane(panelMain);
    }

    private void setupListeners() {
        btnRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarFamiliares(); 
            }
        });

        tableFamiliares.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int row = tableFamiliares.rowAtPoint(evt.getPoint());
                if (row >= 0 && row < tableFamiliares.getRowCount()) {
                    cargarFamiliarSeleccionado(row);
                }
            }
        });


        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCamposFormulario();
                idFamiliarSeleccionado = -1; 
            }
        });

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarFamiliar(); 
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarFamiliar(); 
            }
        });

        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarFamiliar(); 
            }
        });
    }

    private void listarFamiliares() {
        modelFamiliares.setRowCount(0); 
        limpiarCamposFormulario(); 

        try {
            List<GrupoFamiliar> lista = grupoFamiliarDAO.listar(idFuncionarioPadre);

            for (GrupoFamiliar familiar : lista) {

                 modelFamiliares.addRow(new Object[]{
                         familiar.getIdFamiliar(),                   
                         familiar.getNombres(),
                         familiar.getApellidos(),
                         familiar.getParentesco(),
                         familiar.getFechaNacimiento()
                 });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los familiares: " + e.getMessage(),
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); 
        }
    }

    private void cargarFamiliarSeleccionado(int rowIndex) {
        try {
            int id = (int) modelFamiliares.getValueAt(rowIndex, 0); 
            String nombres = (String) modelFamiliares.getValueAt(rowIndex, 1);
            String apellidos = (String) modelFamiliares.getValueAt(rowIndex, 2);
            String parentesco = (String) modelFamiliares.getValueAt(rowIndex, 3);
            String fechaNacimiento = (String) modelFamiliares.getValueAt(rowIndex, 4);

            txtIdFamiliar.setText(String.valueOf(id));
            txtNombres.setText(nombres);
            txtApellidos.setText(apellidos);
            txtParentesco.setText(parentesco);
            txtFechaNacimiento.setText(fechaNacimiento);

            idFamiliarSeleccionado = id;
        } catch (Exception e) {
             JOptionPane.showMessageDialog(this,
                     "Error al cargar los datos del familiar seleccionado: " + e.getMessage(),
                     "Error de Carga",
                     JOptionPane.ERROR_MESSAGE);
             e.printStackTrace();
             limpiarCamposFormulario(); 
        }
    }

    private void limpiarCamposFormulario() {
        txtIdFamiliar.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtParentesco.setText("");
        txtFechaNacimiento.setText("");
        idFamiliarSeleccionado = -1;
        tableFamiliares.clearSelection(); 
    }

    private GrupoFamiliar obtenerDatosFormulario() {
        GrupoFamiliar familiar = new GrupoFamiliar();

        familiar.setIdFuncionario(idFuncionarioPadre);

        if (!txtIdFamiliar.getText().isEmpty()) {
            try {
                 familiar.setIdFamiliar(Integer.parseInt(txtIdFamiliar.getText()));
            } catch (NumberFormatException e) {
                 JOptionPane.showMessageDialog(this,
                         "Error interno: El ID del familiar no es un número válido.",
                         "Error", JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace();
                 return null;
            }
        } else {
            familiar.setIdFamiliar(0); 
        }

        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String parentesco = txtParentesco.getText().trim();
        String fechaNacimiento = txtFechaNacimiento.getText().trim();

        if (nombres.isEmpty() || apellidos.isEmpty() || parentesco.isEmpty()) {
             JOptionPane.showMessageDialog(this,
                     "Los campos Nombres, Apellidos y Parentesco son obligatorios.",
                     "Validación", JOptionPane.WARNING_MESSAGE);
             return null;
        }
        if (!fechaNacimiento.isEmpty() && !fechaNacimiento.matches("\\d{4}-\\d{2}-\\d{2}")) {
             JOptionPane.showMessageDialog(this,
                     "El formato de la Fecha de Nacimiento debe ser-MM-DD.",
                     "Validación de Fecha", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        familiar.setNombres(nombres);
        familiar.setApellidos(apellidos);
        familiar.setParentesco(parentesco);
        familiar.setFechaNacimiento(fechaNacimiento);

        return familiar;
    }

    private void guardarFamiliar() {

        if (idFamiliarSeleccionado != -1) {
            JOptionPane.showMessageDialog(this, "Estás en modo edición. Haz clic en 'Nuevo' para crear un nuevo familiar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        GrupoFamiliar nuevoFamiliar = obtenerDatosFormulario();

        if (nuevoFamiliar != null) { 
            try {
                boolean exito = grupoFamiliarDAO.insertar(nuevoFamiliar);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Familiar guardado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCamposFormulario();
                    listarFamiliares(); 
                } else {
                     JOptionPane.showMessageDialog(this, "La operación de guardar no tuvo éxito (sin error específico).", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(this,
                         "Error de base de datos al guardar familiar: " + e.getMessage(),
                         "Error de Guardado",
                         JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace();
            }
        }
    }

    private void actualizarFamiliar() {
        if (idFamiliarSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un familiar de la tabla para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        GrupoFamiliar familiarParaActualizar = obtenerDatosFormulario();

        if (familiarParaActualizar != null) {
      
             familiarParaActualizar.setIdFamiliar(idFamiliarSeleccionado);        

            try {
                boolean exito = grupoFamiliarDAO.actualizar(familiarParaActualizar);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Familiar actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCamposFormulario();
                    listarFamiliares(); 
                } else {
                    JOptionPane.showMessageDialog(this, "La operación de actualizar no tuvo éxito (puede que el familiar ya no exista).", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(this,
                         "Error de base de datos al actualizar familiar: " + e.getMessage(),
                         "Error de Actualización",
                         JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace();
            }
        }
    }

    private void eliminarFamiliar() {
        if (idFamiliarSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un familiar de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas eliminar a este familiar?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                 boolean exito = grupoFamiliarDAO.eliminar(idFamiliarSeleccionado);

                 if (exito) {
                     JOptionPane.showMessageDialog(this, "Familiar eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                     limpiarCamposFormulario();
                     listarFamiliares(); 
                 } else {
                      JOptionPane.showMessageDialog(this, "La operación de eliminar no tuvo éxito (puede que el familiar ya no exista).", "Advertencia", JOptionPane.WARNING_MESSAGE);
                 }
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(this,
                         "Error de base de datos al eliminar familiar: " + e.getMessage(),
                         "Error de Eliminación",
                         JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace();
            }
        }
    }
}
