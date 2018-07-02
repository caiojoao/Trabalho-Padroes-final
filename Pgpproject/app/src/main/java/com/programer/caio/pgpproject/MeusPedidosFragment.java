package com.programer.caio.pgpproject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MeusPedidosFragment extends PedidoListFragment {

    public MeusPedidosFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("usuarios")
                .child(getUid());
    }
}
