package com.iuniverstaria.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.iuniverstaria.dao.InformacionAcademicaDAO;
import com.iuniverstaria.model.InformacionAcademica;

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.border.TitledBorder;

public class InformacionAcademicaView extends JFrame {
  
    private JPanel panelMain;
    private JPanel panelFormulario;
    private JPanel panelBotones;

 
    private JTextField txtIdAcademico; 
    private JTextField txtUniversidad;
    private JTextField txtNivelEstudio; 
    private JTextField txtTitulo;

    private JTable tableInformacionAcademica;
    private DefaultTableModel modelInformacionAcademica;
    private JScrollPane scrollPaneInformacionAcademica;

    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnBorrar;
    private JButton btnRefrescar;

    private InformacionAcademicaDAO informacionAcademicaDAO;
    private int idFuncionarioPadre; 
    private int idAcademicoSeleccionado = -1; 

  
    public InformacionAcademicaView(int idFuncionario) {
        this.idFuncionarioPadre = idFuncionario; 


        setTitle("Información Académica para Funcionario ID: " + idFuncionarioPadre);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 450); 
        setLocationRelativeTo(null); 

        informacionAcademicaDAO = new InformacionAcademicaDAO();

        initComponents();
        setupLayout();
        setupListeners();
        listarInformacionAcademica();
        setVisible(true);
    }

    private void initComponents() {
        
        panelMain = new JPanel();
        panelFormulario = new JPanel();
        panelBotones = new JPanel();
        txtIdAcademico = new JTextField(5);
        txtIdAcademico.setEditable(false); 
        txtIdAcademico.setBackground(Color.LIGHT_GRAY);
        txtUniversidad = new JTextField(25);
        txtNivelEstudio = new JTextField(15); 
        txtTitulo = new JTextField(20);

        modelInformacionAcademica = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        modelInformacionAcademica.addColumn("ID Académico"); 
        modelInformacionAcademica.addColumn("Universidad"); 
        modelInformacionAcademica.addColumn("Nivel de Estudio"); 
        modelInformacionAcademica.addColumn("Título"); 


        tableInformacionAcademica = new JTable(modelInformacionAcademica);
        tableInformacionAcademica.setAutoCreateRowSorter(true); 

        scrollPaneInformacionAcademica = new JScrollPane(tableInformacionAcademica);
        scrollPaneInformacionAcademica.setPreferredSize(new Dimension(650, 150)); 

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
        panelFormulario.setBorder(new TitledBorder("Datos Académicos"));

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("ID Académico:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(txtIdAcademico, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Universidad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtUniversidad, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Nivel de Estudio:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtNivelEstudio, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 3; 
        panelFormulario.add(txtTitulo, gbc);

        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnBorrar);
        panelBotones.add(btnRefrescar);


        // --- Añadir los paneles al panel principal ---
        panelMain.add(panelFormulario, BorderLayout.NORTH);
        panelMain.add(scrollPaneInformacionAcademica, BorderLayout.CENTER);
        panelMain.add(panelBotones, BorderLayout.SOUTH);

        // --- Establecer el panel principal como el contenido del JFrame ---
        setContentPane(panelMain);
    }

    // Método para configurar los Action Listeners y Mouse Listeners
    private void setupListeners() {
        btnRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarInformacionAcademica(); // Este método ya maneja la excepción
            }
        });

        tableInformacionAcademica.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int row = tableInformacionAcademica.rowAtPoint(evt.getPoint());
                if (row >= 0 && row < tableInformacionAcademica.getRowCount()) {
                    cargarInformacionSeleccionada(row);
                }
            }
        });

        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCamposFormulario();
                idAcademicoSeleccionado = -1; 
            }
        });

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarInformacion();
            }
        });

     
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarInformacion(); 
            }
        });

        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarInformacion(); 
            }
        });
    }

    private void listarInformacionAcademica() {
        modelInformacionAcademica.setRowCount(0); 
        limpiarCamposFormulario(); 

        try {
            List<InformacionAcademica> lista = informacionAcademicaDAO.listar(idFuncionarioPadre);

            for (InformacionAcademica academica : lista) {
                 modelInformacionAcademica.addRow(new Object[]{
                         academica.getIdAcademico(),
                         academica.getUniversidad(),
                         academica.getNivelEstudio(),
                         academica.getTitulo()
                 });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar la información académica: " + e.getMessage(),
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); 
        }
    }

    private void cargarInformacionSeleccionada(int rowIndex) {
        try {
            int id = (int) modelInformacionAcademica.getValueAt(rowIndex, 0); 
            String universidad = (String) modelInformacionAcademica.getValueAt(rowIndex, 1);
            String nivelEstudio = (String) modelInformacionAcademica.getValueAt(rowIndex, 2);
            String titulo = (String) modelInformacionAcademica.getValueAt(rowIndex, 3);

            // Llenar los campos del formulario
            txtIdAcademico.setText(String.valueOf(id));
            txtUniversidad.setText(universidad);
            txtNivelEstudio.setText(nivelEstudio);
            txtTitulo.setText(titulo);

            // Guardar el ID seleccionado
            idAcademicoSeleccionado = id;
        } catch (Exception e) {
             JOptionPane.showMessageDialog(this,
                     "Error al cargar los datos académicos seleccionados: " + e.getMessage(),
                     "Error de Carga",
                     JOptionPane.ERROR_MESSAGE);
             e.printStackTrace();
             limpiarCamposFormulario(); 
        }
    }

    private void limpiarCamposFormulario() {
        txtIdAcademico.setText("");
        txtUniversidad.setText("");
        txtNivelEstudio.setText("");
        txtTitulo.setText("");
        idAcademicoSeleccionado = -1; 
        tableInformacionAcademica.clearSelection();  
    }

    private InformacionAcademica obtenerDatosFormulario() {
        InformacionAcademica academica = new InformacionAcademica();
   
        academica.setIdFuncionario(idFuncionarioPadre);

        if (!txtIdAcademico.getText().isEmpty()) {
            try {
                 academica.setIdAcademico(Integer.parseInt(txtIdAcademico.getText()));
            } catch (NumberFormatException e) {
                 JOptionPane.showMessageDialog(this,
                         "Error interno: El ID académico no es un número válido.",
                         "Error", JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace();
                 return null;
            }
        } else {
            academica.setIdAcademico(0); 
        }

        String universidad = txtUniversidad.getText().trim();
        String nivelEstudio = txtNivelEstudio.getText().trim();
        String titulo = txtTitulo.getText().trim();

        if (universidad.isEmpty() || nivelEstudio.isEmpty() || titulo.isEmpty()) {
             JOptionPane.showMessageDialog(this,
                     "Los campos Universidad, Nivel de Estudio y Título son obligatorios.",
                     "Validación", JOptionPane.WARNING_MESSAGE);
             return null;
        }

        academica.setUniversidad(universidad);
        academica.setNivelEstudio(nivelEstudio);
        academica.setTitulo(titulo);

        return academica;
    }

    private void guardarInformacion() {
        if (idAcademicoSeleccionado != -1) {
            JOptionPane.showMessageDialog(this, "Estás en modo edición. Haz clic en 'Nuevo' para crear un nuevo registro académico.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        InformacionAcademica nuevaInformacion = obtenerDatosFormulario();

        if (nuevaInformacion != null) { 
            try {
                boolean exito = informacionAcademicaDAO.insertar(nuevaInformacion);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Registro académico guardado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCamposFormulario();
                    listarInformacionAcademica(); 
                } else {
                     JOptionPane.showMessageDialog(this, "La operación de guardar no tuvo éxito (sin error específico).", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(this,
                         "Error de base de datos al guardar registro académico: " + e.getMessage(),
                         "Error de Guardado",
                         JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace();
            }
        }
    }

    private void actualizarInformacion() {
        if (idAcademicoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un registro académico de la tabla para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        InformacionAcademica informacionParaActualizar = obtenerDatosFormulario();

        if (informacionParaActualizar != null) {
            
             informacionParaActualizar.setIdAcademico(idAcademicoSeleccionado);

            try {
                boolean exito = informacionAcademicaDAO.actualizar(informacionParaActualizar);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Registro académico actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCamposFormulario();
                    listarInformacionAcademica(); 
                } else {
                     JOptionPane.showMessageDialog(this, "La operación de actualizar no tuvo éxito (puede que el registro ya no exista).", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(this,
                         "Error de base de datos al actualizar registro académico: " + e.getMessage(),
                         "Error de Actualización",
                         JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace();
            }
        }
    }

    private void eliminarInformacion() {
        if (idAcademicoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un registro académico de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas eliminar este registro académico?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                 boolean exito = informacionAcademicaDAO.eliminar(idAcademicoSeleccionado);

                 if (exito) {
                     JOptionPane.showMessageDialog(this, "Registro académico eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                     limpiarCamposFormulario();
                     listarInformacionAcademica(); 
                 } else {
                      JOptionPane.showMessageDialog(this, "La operación de eliminar no tuvo éxito (puede que el registro ya no exista).", "Advertencia", JOptionPane.WARNING_MESSAGE);
                 }
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(this,
                         "Error de base de datos al eliminar registro académico: " + e.getMessage(),
                         "Error de Eliminación",
                         JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace();
            }
        }
    }


}
