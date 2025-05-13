package com.iuniverstaria.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.iuniverstaria.model.GrupoFamiliar;
import com.iuniverstaria.util.DBconnection;

public class GrupoFamiliarDAO {

    public boolean insertar(GrupoFamiliar familiar) {
        String sql = "INSERT INTO grupo_familiar (id_funcionario, nombres, apellidos, parentesco, fecha_nacimiento) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, familiar.getIdFuncionario());
            stmt.setString(2, familiar.getNombres());
            stmt.setString(3, familiar.getApellidos());
            stmt.setString(4, familiar.getParentesco());
            stmt.setString(5, familiar.getFechaNacimiento());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<GrupoFamiliar> listar(int idFuncionario) {
        List<GrupoFamiliar> lista = new ArrayList<>();
        String sql = "SELECT * FROM grupo_familiar WHERE id_funcionario = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                GrupoFamiliar familiar = new GrupoFamiliar();
                familiar.setIdFamiliar(rs.getInt("id_familiar"));
                familiar.setIdFuncionario(rs.getInt("id_funcionario"));
                familiar.setNombres(rs.getString("nombres"));
                familiar.setApellidos(rs.getString("apellidos"));
                familiar.setParentesco(rs.getString("parentesco"));
                familiar.setFechaNacimiento(rs.getString("fecha_nacimiento"));
                lista.add(familiar);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizar(GrupoFamiliar familiar) {
        String sql = "UPDATE grupo_familiar SET nombres = ?, apellidos = ?, parentesco = ?, fecha_nacimiento = ? WHERE id_familiar = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, familiar.getNombres());
            stmt.setString(2, familiar.getApellidos());
            stmt.setString(3, familiar.getParentesco());
            stmt.setString(4, familiar.getFechaNacimiento());
            stmt.setInt(5, familiar.getIdFamiliar());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int idFamiliar) {
        String sql = "DELETE FROM grupo_familiar WHERE id_familiar = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFamiliar);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
