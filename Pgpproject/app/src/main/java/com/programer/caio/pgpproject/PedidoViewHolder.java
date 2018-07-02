package com.programer.caio.pgpproject;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class PedidoViewHolder extends RecyclerView.ViewHolder{
    public TextView assuntoView;
    public TextView autorView;
    public TextView pedidoView;

    public PedidoViewHolder(View itemView) {
        super(itemView);

        assuntoView = itemView.findViewById(R.id.pedido_assunto);
        autorView = itemView.findViewById(R.id.pedido_autor);
        pedidoView = itemView.findViewById(R.id.pedido_pedido);
    }

    public void bindToPedido(Pedido pedido, View.OnClickListener starClickListener) {
        assuntoView.setText(pedido.assunto);
        autorView.setText(pedido.autor);
        pedidoView.setText(pedido.pedido);

    }
}
