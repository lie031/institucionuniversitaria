package com.iuniverstaria.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.iuniverstaria.model.InformacionAcademica;
import com.iuniverstaria.util.DBconnection;

public class InformacionAcademicaDAO {

    public boolean insertar(InformacionAcademica academica) {
        String sql = "INSERT INTO informacion_academica (id_funcionario, universidad, nivel_estudio, titulo) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, academica.getIdFuncionario());
            stmt.setString(2, academica.getUniversidad());
            stmt.setString(3, academica.getNivelEstudio());
            stmt.setString(4, academica.getTitulo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<InformacionAcademica> listar(int idFuncionario) {
        List<InformacionAcademica> lista = new ArrayList<>();
        String sql = "SELECT * FROM informacion_academica WHERE id_funcionario = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                InformacionAcademica academica = new InformacionAcademica();
                academica.setIdAcademico(rs.getInt("id_academico"));
                academica.setIdFuncionario(rs.getInt("id_funcionario"));
                academica.setUniversidad(rs.getString("universidad"));
                academica.setNivelEstudio(rs.getString("nivel_estudio"));
                academica.setTitulo(rs.getString("titulo"));
                lista.add(academica);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizar(InformacionAcademica academica) {
        String sql = "UPDATE informacion_academica SET universidad = ?, nivel_estudio = ?, titulo = ? WHERE id_academico = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, academica.getUniversidad());
            stmt.setString(2, academica.getNivelEstudio());
            stmt.setString(3, academica.getTitulo());
            stmt.setInt(4, academica.getIdAcademico());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int idAcademico) {
        String sql = "DELETE FROM informacion_academica WHERE id_academico = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAcademico);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
