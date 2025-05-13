package com.iuniverstaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.iuniverstaria.model.Funcionario;
import com.iuniverstaria.util.DBconnection;

public class FuncionarioDAO {

    public boolean insertar(Funcionario funcionario) {
        String sql = "INSERT INTO funcionarios (tipo_identificacion, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getTipoIdentificacion());
            stmt.setString(2, funcionario.getNumeroIdentificacion());
            stmt.setString(3, funcionario.getNombres());
            stmt.setString(4, funcionario.getApellidos());
            stmt.setString(5, funcionario.getEstadoCivil());
            stmt.setString(6, funcionario.getSexo());
            stmt.setString(7, funcionario.getDireccion());
            stmt.setString(8, funcionario.getTelefono());
            stmt.setString(9, funcionario.getFechaNacimiento());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Funcionario> listar() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios";

        try (Connection connection = DBconnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
                funcionario.setTipoIdentificacion(rs.getString("tipo_identificacion"));
                funcionario.setNumeroIdentificacion(rs.getString("numero_identificacion"));
                funcionario.setNombres(rs.getString("nombres"));
                funcionario.setApellidos(rs.getString("apellidos"));
                funcionario.setEstadoCivil(rs.getString("estado_civil"));
                funcionario.setSexo(rs.getString("sexo"));
                funcionario.setDireccion(rs.getString("direccion"));
                funcionario.setTelefono(rs.getString("telefono"));
                funcionario.setFechaNacimiento(rs.getString("fecha_nacimiento"));
                lista.add(funcionario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizar(Funcionario funcionario) {
        String sql = "UPDATE funcionarios SET tipo_identificacion = ?, numero_identificacion = ?, nombres = ?, apellidos = ?, estado_civil = ?, sexo = ?, direccion = ?, telefono = ?, fecha_nacimiento = ? WHERE id_funcionario = ?";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getTipoIdentificacion());
            stmt.setString(2, funcionario.getNumeroIdentificacion());
            stmt.setString(3, funcionario.getNombres());
            stmt.setString(4, funcionario.getApellidos());
            stmt.setString(5, funcionario.getEstadoCivil());
            stmt.setString(6, funcionario.getSexo());
            stmt.setString(7, funcionario.getDireccion());
            stmt.setString(8, funcionario.getTelefono());
            stmt.setString(9, funcionario.getFechaNacimiento());
            stmt.setInt(10, funcionario.getIdFuncionario());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM funcionarios WHERE id_funcionario = ?";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
