package com.iuniverstaria.model;

public class GrupoFamiliar {
    private int idFamiliar;
    private int idFuncionario;
    private String nombres;
    private String apellidos;
    private String parentesco;
    private String fechaNacimiento;

    public GrupoFamiliar() {
    }

    public GrupoFamiliar(int idFamiliar, int idFuncionario, String nombres, String apellidos, String parentesco, String fechaNacimiento) {
        this.idFamiliar = idFamiliar;
        this.idFuncionario = idFuncionario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.parentesco = parentesco;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdFamiliar() {
        return idFamiliar;
    }

    public void setIdFamiliar(int idFamiliar) {
        this.idFamiliar = idFamiliar;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}

