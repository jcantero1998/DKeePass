package com.dani_duran.seccion_042_tabs.Model;

public class Registro {

    private int codigo;
    private String usuario;
    private String contraseña;
    private String url;
    private String comentarios;

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getComentarios() {
        return comentarios;
    }
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Registro() {
    }
    public Registro(String usuario, String contraseña, String url, String comentarios) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.url = url;
        this.comentarios = comentarios;
    }
    public Registro(int codigo, String usuario, String contraseña, String url, String comentarios) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.url = url;
        this.comentarios = comentarios;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + codigo;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Registro other = (Registro) obj;
        if (codigo != other.codigo)
            return false;
        return true;
    }



}