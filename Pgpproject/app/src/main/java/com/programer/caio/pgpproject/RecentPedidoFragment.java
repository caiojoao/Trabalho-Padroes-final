package com.programer.caio.pgpproject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class RecentPedidoFragment extends PedidoListFragment {

    public RecentPedidoFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        Query recentPedidoQuery = databaseReference.child("pedidos")
                .limitToFirst(100);
        return recentPedidoQuery;
    }
}

