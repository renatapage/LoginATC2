package br.com.renatapage.loginatc2l2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText txtUser;
    private EditText txtSenha;
    private TextView lblErro;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//------------------------------------------------------------

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        txtUser = (EditText) findViewById(R.id.txtUser);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        lblErro = (TextView) findViewById(R.id.lblErro);
        user = new User("renatapage", "renata123");
        savePreferences(user);
    }

    @Override
    public void onClick(View view) {

        lblErro.setText("");

        if(txtUser.getText().toString().length() > 0 && isCadastrado(txtUser.getText().toString()).equals(loadPreferences("uniqueId")) &&
                txtSenha.getText().toString().length() > 0 && txtSenha.getText().toString().equals(loadPreferences("senha"))){

            byte [] senha = txtSenha.getText().toString().getBytes();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("username", loadPreferences("username"));
            intent.putExtra("senha",getSenhaHash(senha));

            startActivity(intent);


        } else {

                String msg = "O usuário não possui acesso ao sistema";
                lblErro.setText(msg);
            }
        }


    public static byte[] getSenhaHash(byte[] buffer){

        byte [] resultado = null;

        try{

            MessageDigest md5 = MessageDigest.getInstance("SHA-512");
            md5.update(buffer);
            resultado = md5.digest();

        } catch (NoSuchAlgorithmException e){

        }

        return resultado;
    }

    private void savePreferences(User user){  ////Método para salvar usuário em Shared Preferences.

        SharedPreferences preference = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("uniqueId", user.getUniqueID());
        editor.putString("username", user.getUsername());
        editor.putString("senha", user.getPassword());
        editor.commit();

    }

    private String loadPreferences(String key){ //Método para resgatar dados em Shared Preferences.

        String retorno = "";
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        switch (key){

            case "uniqueId":

                retorno = preferences.getString("uniqueId", "");
                break;

            case "username":

                retorno = preferences.getString("username", "");
                break;

            case "senha":

                retorno = preferences.getString("senha", "");
                break;
        }

        return retorno;
    }

    private String isCadastrado(String username){ //Verifica se o username passado no parâmetro está gravado em Shared Preferences e retorna o UniqueId associado e este username.

        String retorno = "";

        if(username.equals(loadPreferences("username"))){

          retorno = loadPreferences("uniqueId");
        }

        return retorno;
    }


}
