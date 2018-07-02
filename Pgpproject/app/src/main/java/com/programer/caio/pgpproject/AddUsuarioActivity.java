package com.programer.caio.pgpproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUsuarioActivity extends BaseActivity implements View.OnClickListener{


    private static final String TAG = "AddPedidoActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtSobrenome;
    private EditText edtNome;
    private Button btnCriar;
    private Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usuario);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        // Views
        edtEmail = findViewById(R.id.edt_addEmail);
        edtSenha = findViewById(R.id.edt_Senha);
        edtNome = findViewById(R.id.edt_Nome);
        edtSobrenome= findViewById(R.id.edt_addSobrenome);

        btnCriar = findViewById(R.id.btn_salvarUsuario);
        btnVoltar = findViewById(R.id.btn_Cancel);

        // Click listeners
        btnCriar.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);
    }

    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();
        String nome = edtNome.getText().toString();
        String sobrenome = edtSobrenome.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(AddUsuarioActivity.this, "Falha ao criar usuario",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void onAuthSuccess(FirebaseUser user) {
        String senha = edtSenha.getText().toString();
        String nome = edtNome.getText().toString();
        String sobrenome = edtSobrenome.getText().toString();

        // salva o usuario
        writeNewUser(user.getUid(), nome, user.getEmail(), sobrenome, senha);

        // Vai pra  MainActivity
        startActivity(new Intent(AddUsuarioActivity.this, MainActivity.class));
        finish();
    }


    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setError("Escreva o email");
            result = false;
        } else {
            edtEmail.setError(null);
        }

        if (TextUtils.isEmpty(edtSenha.getText().toString())) {
            edtSenha.setError("Escreva a senha");
            result = false;
        } else {
            edtSenha.setError(null);
        }

        return result;
    }

    private void writeNewUser(String userId, String nomeusuario, String email, String sobrenomeusuario, String senhausuario) {
        Usuario usuario = new Usuario(nomeusuario, email, sobrenomeusuario, senhausuario);

        mDatabase.child("usuarios").child(userId).setValue(usuario);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_salvarUsuario) {
            signUp();
        } else if (i == R.id.btn_Cancel) {
            startActivity(new Intent(AddUsuarioActivity.this, LoginActivity.class));
            finish();
        }
    }
}
