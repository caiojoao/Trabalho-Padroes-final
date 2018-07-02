package com.programer.caio.pgpproject;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Pedido {

    public String uid;
    public String autor;
    public String assunto;
    public String pedido;

    public Pedido() {
    }

    public Pedido(String uid, String autor, String assunto, String pedido) {
        this.uid = uid;
        this.autor = autor;
        this.assunto = assunto;
        this.pedido = pedido;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("autor", autor);
        result.put("assunto", assunto);
        result.put("pedido", pedido);

        return result;
    }
}
