package com.programer.caio.pgpproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddPedidoActivity extends BaseActivity {

    private static final String TAG = "NewPedidoActivity";
    private static final String REQUIRED = "Required";

    private DatabaseReference mDatabase;

    private EditText mAssuntoField;
    private EditText mPedidoField;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pedido);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAssuntoField = findViewById(R.id.field_assunto);
        mPedidoField = findViewById(R.id.field_pedido);
        mSubmitButton = findViewById(R.id.fab_submit_pedido);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPedido();
            }
        });
    }

    private void submitPedido() {
        final String assunto = mAssuntoField.getText().toString();
        final String pedido = mPedidoField.getText().toString();

        if (TextUtils.isEmpty(assunto)) {
            mAssuntoField.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(pedido)) {
            mPedidoField.setError(REQUIRED);
            return;
        }

        setEditingEnabled(false);
        Toast.makeText(this, "Postando...", Toast.LENGTH_SHORT).show();

        final String userId = getUid();
        mDatabase.child("usuarios").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Usuario usuario = dataSnapshot.getValue(Usuario.class);

                        // [START_EXCLUDE]
                        if (usuario == null) {
                            // User is null, error out
                            Log.e(TAG, "Usuario " + userId + " está vazio");
                            Toast.makeText(AddPedidoActivity.this,
                                    "Erro",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPedido(userId, usuario.nomeusuario, assunto, pedido);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });

    }

    private void setEditingEnabled(boolean enabled) {
        mAssuntoField.setEnabled(enabled);
        mPedidoField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }


    private void writeNewPedido(String userId, String nomeusuario, String assunto, String pedido) {
        String key = mDatabase.child("pedidos").push().getKey();
        Pedido pedidos = new Pedido(userId, nomeusuario, assunto, pedido);
        Map<String, Object> postValues = pedidos.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/pedidos/" + key, postValues);
        childUpdates.put("/usuarios-pedidos/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
}
