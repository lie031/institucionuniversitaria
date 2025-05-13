package com.iuniverstaria.model;

public class InformacionAcademica {
    private int idAcademico;
    private int idFuncionario;
    private String universidad;
    private String nivelEstudio;
    private String titulo;

    public InformacionAcademica() {
    }

    public InformacionAcademica(int idAcademico, int idFuncionario, String universidad, String nivelEstudio, String titulo) {
        this.idAcademico = idAcademico;
        this.idFuncionario = idFuncionario;
        this.universidad = universidad;
        this.nivelEstudio = nivelEstudio;
        this.titulo = titulo;
    }

    public int getIdAcademico() {
        return idAcademico;
    }

    public void setIdAcademico(int idAcademico) {
        this.idAcademico = idAcademico;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getNivelEstudio() {
        return nivelEstudio;
    }

    public void setNivelEstudio(String nivelEstudio) {
        this.nivelEstudio = nivelEstudio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}

