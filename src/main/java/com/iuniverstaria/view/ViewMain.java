package com.iuniverstaria.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.iuniverstaria.dao.FuncionarioDAO;
import com.iuniverstaria.model.Funcionario;
import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.border.TitledBorder;

public class ViewMain extends JFrame {
    // Paneles principales para organizar la interfaz
    private JPanel panelMain;
    private JPanel panelFormulario;
    private JPanel panelBotones;

    // Componentes del formulario
    private JTextField txtId; // 
    private JTextField txtNumeroIdentificacion;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtFechaNacimiento;
    private JComboBox<String> cbTipoIdentificacion;
    private JComboBox<String> cbEstadoCivil;
    private JComboBox<String> cbSexo;

    // Componentes de la tabla
    private JTable tableFuncionarios;
    private DefaultTableModel modelFuncionarios;
    private JScrollPane scrollPaneFuncionarios; // La tabla siempre debe ir en un ScrollPane

    // Botones
    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnBorrar;
    private JButton btnRefrescar;

    // DAO y otros helpers
    private final FuncionarioDAO funcionarioDAO;
    private int funcionarioIdSeleccionado = -1; // Para guardar el ID del funcionario seleccionado

    // Constructor
    public ViewMain() {
        // Configuración básica de la ventana
        setTitle("Gestión de Funcionarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600); // Tamaño inicial
        setLocationRelativeTo(null); // Centrar la ventana

        // Inicializar el DAO
        funcionarioDAO = new FuncionarioDAO();

        initComponents();
        setupLayout();
        setupListeners();
        listarFuncionarios();

        setVisible(true);
    }

    // Método para crear e inicializar todos los componentes
    private void initComponents() {
        // Paneles
        panelMain = new JPanel();
        panelFormulario = new JPanel();
        panelBotones = new JPanel();

        // Componentes del formulario
        txtId = new JTextField(5);
        txtId.setEditable(false); // ID no editable por el usuario
        txtId.setBackground(Color.LIGHT_GRAY);


        txtNumeroIdentificacion = new JTextField(15);
        txtNombres = new JTextField(20);
        txtApellidos = new JTextField(20);
        txtDireccion = new JTextField(25);
        txtTelefono = new JTextField(15);
        txtFechaNacimiento = new JTextField(10);

        cbTipoIdentificacion = new JComboBox<>(new String[]{"Seleccione...", "Cédula Ciudadanía", "Tarjeta Identidad", "Cédula Extranjería", "Pasaporte"});
        cbEstadoCivil = new JComboBox<>(new String[]{"Seleccione...", "Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a", "Unión Libre"});
        cbSexo = new JComboBox<>(new String[]{"Seleccione...", "Masculino", "Femenino", "Otro"});

        // Modelo y Tabla de funcionarios
        modelFuncionarios = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilita la edición directa
            }
        };

        // Definir las columnas de la tabla
        modelFuncionarios.addColumn("ID"); 
        modelFuncionarios.addColumn("Tipo Doc"); 
        modelFuncionarios.addColumn("Número Doc"); 
        modelFuncionarios.addColumn("Nombres"); 
        modelFuncionarios.addColumn("Apellidos"); 
        modelFuncionarios.addColumn("Estado Civil"); 
        modelFuncionarios.addColumn("Sexo"); 
        modelFuncionarios.addColumn("Dirección"); 
        modelFuncionarios.addColumn("Teléfono"); 
        modelFuncionarios.addColumn("Fecha Nacimiento"); 
        modelFuncionarios.addColumn("Ver Familiar"); 
        modelFuncionarios.addColumn("Ver Académico"); 


        tableFuncionarios = new JTable(modelFuncionarios);
        tableFuncionarios.setAutoCreateRowSorter(true); 

        scrollPaneFuncionarios = new JScrollPane(tableFuncionarios);
        scrollPaneFuncionarios.setPreferredSize(new Dimension(950, 300)); 


        // Botones
        btnNuevo = new JButton("Nuevo");
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnBorrar = new JButton("Borrar");
        btnRefrescar = new JButton("Refrescar");
    }

    // Método para configurar el layout y añadir componentes a los paneles
    private void setupLayout() {
        panelMain.setLayout(new BorderLayout());

        // --- Configurar el panel del formulario ---
        panelFormulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        panelFormulario.setBorder(new TitledBorder("Datos del Funcionario"));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; 

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(txtId, gbc);

        // Primera fila: Tipo y Número Identificación
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Tipo Identificación:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(cbTipoIdentificacion, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Número Identificación:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtNumeroIdentificacion, gbc);

        // Segunda fila: Nombres y Apellidos
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Nombres:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtNombres, gbc);

        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Apellidos:"), gbc);
        gbc.gridx = 3; gbc.gridy = 2; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtApellidos, gbc);

        // Tercera fila: Estado Civil y Sexo
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Estado Civil:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(cbEstadoCivil, gbc);

        gbc.gridx = 2; gbc.gridy = 3; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Sexo:"), gbc);
        gbc.gridx = 3; gbc.gridy = 3; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(cbSexo, gbc);

        // Cuarta fila: Dirección (ocupa 3 columnas)
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 3; // Este campo ocupa 3 columnas
        panelFormulario.add(txtDireccion, gbc);

        // Quinta fila: Teléfono y Fecha Nacimiento
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtTelefono, gbc);

        gbc.gridx = 2; gbc.gridy = 5; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; panelFormulario.add(new JLabel("Fecha Nacimiento:"), gbc);
        gbc.gridx = 3; gbc.gridy = 5; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST; panelFormulario.add(txtFechaNacimiento, gbc);


        // --- Configurar el panel de botones ---
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Botones centrados con espacio
        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnBorrar);
        panelBotones.add(btnRefrescar);


        // --- Añadir los paneles al panel principal ---
        panelMain.setLayout(new BorderLayout()); // Asegurarse de que panelMain tiene layout
        panelMain.add(panelFormulario, BorderLayout.NORTH); // El formulario arriba
        panelMain.add(scrollPaneFuncionarios, BorderLayout.CENTER); // La tabla en el centro
        panelMain.add(panelBotones, BorderLayout.SOUTH); // Los botones abajo

        // --- Establecer el panel principal como el contenido del JFrame ---
        setContentPane(panelMain);
    }

    // Método para configurar los Action Listeners y Mouse Listeners
    private void setupListeners() {
        
        // Listener para Refrescar
        btnRefrescar.addActionListener((ActionEvent e) -> {
            listarFuncionarios(); // Este método ya maneja la excepción
        });

        // Listener para la tabla (click en filas o celdas)
        tableFuncionarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = tableFuncionarios.rowAtPoint(evt.getPoint()); // Obtener la fila clickeada
                int col = tableFuncionarios.columnAtPoint(evt.getPoint()); 
                
                if (row >= 0 && row < tableFuncionarios.getRowCount()) {
                     if (col < 10) { 
                         cargarFuncionarioSeleccionado(row);
                     }
                     
                    if (col == 10) { 
                        try {
                             int idFuncionario = (int) tableFuncionarios.getValueAt(row, 0);                        
                             new GrupoFamiliarView(idFuncionario);


                        } catch (Exception ex) {
                             JOptionPane.showMessageDialog(ViewMain.this,
                                     "Error al abrir la ventana de Grupo Familiar: " + ex.getMessage(),
                                     "Error", JOptionPane.ERROR_MESSAGE);
                             ex.printStackTrace();
                        }
                    } else if (col == 11) { 
                         try {
                              int idFuncionario = (int) tableFuncionarios.getValueAt(row, 0);                               
                              new InformacionAcademicaView(idFuncionario);

                         } catch (Exception ex) {
                             
                             JOptionPane.showMessageDialog(ViewMain.this,
                                     "Error al abrir la ventana de Información Académica: " + ex.getMessage(),
                                     "Error", JOptionPane.ERROR_MESSAGE);
                              ex.printStackTrace();
                         }
                    }
                }
            }
        });

        // Listener para el botón Nuevo
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCamposFormulario();
                funcionarioIdSeleccionado = -1;
            }
        });

        // Listener para el botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarFuncionario(); // 
            }
        });

        // listener para el botón Actualizar
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarFuncionario();
            }
        
        });
       
        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarFuncionario(); 
            }
        });
    }



    // Método para cargar los funcionarios en la tabla desde el DAO
    private void listarFuncionarios() {
        modelFuncionarios.setRowCount(0);
        limpiarCamposFormulario(); 

        try {
            
            List<Funcionario> lista = funcionarioDAO.listar();
     
            for (Funcionario f : lista) {
                
                 modelFuncionarios.addRow(new Object[]{
                         f.getIdFuncionario(),
                         f.getTipoIdentificacion(),
                         f.getNumeroIdentificacion(),
                         f.getNombres(),
                         f.getApellidos(),
                         f.getEstadoCivil(),
                         f.getSexo(),
                         f.getDireccion(),
                         f.getTelefono(),
                         f.getFechaNacimiento(),
                         "Ver Familiar",
                         "Ver Académico"
                 });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los funcionarios: " + e.getMessage(), 
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); 
        }
    }

    // Método para cargar los datos de un funcionario seleccionado en la tabla al formulario
    private void cargarFuncionarioSeleccionado(int rowIndex) {
         try {
             int id = (int) modelFuncionarios.getValueAt(rowIndex, 0); 
             String tipoDoc = (String) modelFuncionarios.getValueAt(rowIndex, 1);
             String numDoc = (String) modelFuncionarios.getValueAt(rowIndex, 2);
             String nombres = (String) modelFuncionarios.getValueAt(rowIndex, 3);
             String apellidos = (String) modelFuncionarios.getValueAt(rowIndex, 4);
             String estadoCivil = (String) modelFuncionarios.getValueAt(rowIndex, 5);
             String sexo = (String) modelFuncionarios.getValueAt(rowIndex, 6);
             String direccion = (String) modelFuncionarios.getValueAt(rowIndex, 7);
             String telefono = (String) modelFuncionarios.getValueAt(rowIndex, 8);
             String fechaNacimiento = (String) modelFuncionarios.getValueAt(rowIndex, 9);


             // Llenar los campos del formulario
             txtId.setText(String.valueOf(id));
             txtNumeroIdentificacion.setText(numDoc);
             txtNombres.setText(nombres);
             txtApellidos.setText(apellidos);
             txtDireccion.setText(direccion);
             txtTelefono.setText(telefono);
             txtFechaNacimiento.setText(fechaNacimiento);

             // Seleccionar el valor correcto en los ComboBoxes
             cbTipoIdentificacion.setSelectedItem(tipoDoc);
             cbEstadoCivil.setSelectedItem(estadoCivil);
             cbSexo.setSelectedItem(sexo);

             // Guardar el ID seleccionado para usarlo en Actualizar/Borrar
             funcionarioIdSeleccionado = id;
         } catch (Exception e) {
              JOptionPane.showMessageDialog(this,
                      "Error al cargar los datos del funcionario seleccionado: " + e.getMessage(),
                      "Error de Carga",
                      JOptionPane.ERROR_MESSAGE);
              e.printStackTrace();
              limpiarCamposFormulario(); 
         }
    }

    private void limpiarCamposFormulario() {
        txtId.setText("");
        txtNumeroIdentificacion.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtFechaNacimiento.setText("");
        cbTipoIdentificacion.setSelectedIndex(0); 
        cbEstadoCivil.setSelectedIndex(0);
        cbSexo.setSelectedIndex(0);
        funcionarioIdSeleccionado = -1; 
        tableFuncionarios.clearSelection(); 
    }

    // Retorna Funcionario si los datos son válidos, null en caso contrario (y muestra mensaje de error)
    private Funcionario obtenerDatosFormulario() {
        Funcionario f = new Funcionario();

        if (!txtId.getText().isEmpty()) {
            try {
                 f.setIdFuncionario(Integer.parseInt(txtId.getText()));
            } catch (NumberFormatException e) {
                 JOptionPane.showMessageDialog(this,
                         "Error interno: El ID del funcionario no es un número válido.",
                         "Error", JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace();
                 return null; 
            }
        } else {
             f.setIdFuncionario(0);
        }


        // Obtener los demás datos de los campos
        String tipoId = (String) cbTipoIdentificacion.getSelectedItem();
        String numeroId = txtNumeroIdentificacion.getText().trim();
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String estadoCivil = (String) cbEstadoCivil.getSelectedItem();
        String sexo = (String) cbSexo.getSelectedItem();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String fechaNacimiento = txtFechaNacimiento.getText().trim();

        // Validaciones básicas de campos obligatorios
        if (tipoId == null || tipoId.equals("Seleccione...") || numeroId.isEmpty() || nombres.isEmpty() || apellidos.isEmpty()) {
             JOptionPane.showMessageDialog(this,
                     "Los campos Tipo Identificación, Número Identificación, Nombres y Apellidos son obligatorios.",
                     "Validación", JOptionPane.WARNING_MESSAGE);
             return null; // Retorna null si faltan campos obligatorios
        }

        // Validación simple de formato de fecha (YYYY-MM-DD)
        if (!fechaNacimiento.isEmpty() && !fechaNacimiento.matches("\\d{4}-\\d{2}-\\d{2}")) {
             JOptionPane.showMessageDialog(this,
                     "El formato de la Fecha de Nacimiento debe ser YYYY-MM-DD.",
                     "Validación de Fecha", JOptionPane.WARNING_MESSAGE);
            return null; // Invalida la operación si el formato es incorrecto
        }


        f.setTipoIdentificacion(tipoId);
        f.setNumeroIdentificacion(numeroId);
        f.setNombres(nombres);
        f.setApellidos(apellidos);
        f.setEstadoCivil(estadoCivil);
        f.setSexo(sexo);
        f.setDireccion(direccion);
        f.setTelefono(telefono);
        f.setFechaNacimiento(fechaNacimiento);

        return f;
    }

    // Método para guardar un nuevo funcionario
    private void guardarFuncionario() {
        if (funcionarioIdSeleccionado != -1) {
            JOptionPane.showMessageDialog(this, "Estás en modo edición. Haz clic en 'Nuevo' para crear un nuevo funcionario.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Funcionario nuevoFuncionario = obtenerDatosFormulario();

        if (nuevoFuncionario != null) { 
            try {
                boolean exito = funcionarioDAO.insertar(nuevoFuncionario);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Funcionario guardado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCamposFormulario();
                    listarFuncionarios(); 
                } else {
                    JOptionPane.showMessageDialog(this, "La operación de guardar no tuvo éxito (sin error específico).", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) { 
                 JOptionPane.showMessageDialog(this,
                         "Error de base de datos al guardar funcionario: " + e.getMessage(),
                         "Error de Guardado",
                         JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace(); 
            }
        }
       
    }

   
    private void actualizarFuncionario() {
     
        if (funcionarioIdSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un funcionario de la tabla para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

       
        Funcionario funcionarioParaActualizar = obtenerDatosFormulario();

        if (funcionarioParaActualizar != null) { 
             funcionarioParaActualizar.setIdFuncionario(funcionarioIdSeleccionado);

            try {
                boolean exito = funcionarioDAO.actualizar(funcionarioParaActualizar);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Funcionario actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCamposFormulario();
                    listarFuncionarios(); 
                } else {
                     JOptionPane.showMessageDialog(this, "La operación de actualizar no tuvo éxito (puede que el funcionario ya no exista).", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) { 
                 JOptionPane.showMessageDialog(this,
                         "Error de base de datos al actualizar funcionario: " + e.getMessage(),
                         "Error de Actualización",
                         JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace(); 
            }
        }
    }

 
     private void eliminarFuncionario() {
         if (funcionarioIdSeleccionado == -1) {
             JOptionPane.showMessageDialog(this, "Selecciona un funcionario de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
             return;
         }

         int confirmacion = JOptionPane.showConfirmDialog(this,
                 "¿Estás seguro de que deseas eliminar a este funcionario?",
                 "Confirmar Eliminación",
                 JOptionPane.YES_NO_OPTION,
                 JOptionPane.QUESTION_MESSAGE); 

         if (confirmacion == JOptionPane.YES_OPTION) {
             try {
                  boolean exito = funcionarioDAO.eliminar(funcionarioIdSeleccionado);

                  if (exito) {
                      JOptionPane.showMessageDialog(this, "Funcionario eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                      limpiarCamposFormulario();
                      listarFuncionarios();
                  } else {
                       JOptionPane.showMessageDialog(this, "La operación de eliminar no tuvo éxito (puede que el funcionario ya no exista).", "Advertencia", JOptionPane.WARNING_MESSAGE);
                  }
             } catch (Exception e) { 
                  JOptionPane.showMessageDialog(this,
                          "Error de base de datos al eliminar funcionario: " + e.getMessage(),
                          "Error de Eliminación",
                          JOptionPane.ERROR_MESSAGE);
                  e.printStackTrace(); 
             }
         }
     }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new ViewMain();
            }
        });
    }
}