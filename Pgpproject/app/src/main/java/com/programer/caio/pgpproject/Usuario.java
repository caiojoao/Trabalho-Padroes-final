package com.programer.caio.pgpproject;

public class Usuario {

    public String nomeusuario;
    public String email;
    public String sobrenomeusuario;
    public String senhausuario;

    public Usuario() {
    }

    public Usuario(String nomeusuario, String email, String sobrenomeusuario, String senhausuario) {
        this.nomeusuario = nomeusuario;
        this.email = email;
        this.sobrenomeusuario=sobrenomeusuario;
        this.senhausuario = senhausuario;
    }
}
