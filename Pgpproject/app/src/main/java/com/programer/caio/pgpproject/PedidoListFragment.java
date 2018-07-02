package com.programer.caio.pgpproject;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public abstract class PedidoListFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "PedidoListFragment";

    private DatabaseReference mDatabase;

    private FirebaseRecyclerAdapter<Pedido, PedidoViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public PedidoListFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_pedidos, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecycler = rootView.findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        Query pedidoQuery = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Pedido>()
                .setQuery(pedidoQuery, Pedido.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<Pedido, PedidoViewHolder>(options) {

            @Override
            public PedidoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new PedidoViewHolder(inflater.inflate(R.layout.item_pedido, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(PedidoViewHolder viewHolder, int position, final Pedido model) {
                final DatabaseReference pedidoRef = getRef(position);

                final String pedidoKey = pedidoRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                viewHolder.bindToPedido(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                       DatabaseReference globalPostRef = mDatabase.child("pedidos").child(pedidoRef.getKey());
                        DatabaseReference userPostRef = mDatabase.child("usuarios-pedidos").child(model.uid).child(pedidoRef.getKey());

                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }


    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);
}

