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

        if(txtUser.getText().toString().length() > 0 && isCadastrado(txtUser.getText().toString()).equals(getUniqueIDPreferences()) &&
                txtSenha.getText().toString().length() > 0 && txtSenha.getText().toString().equals(getSenhaPreferences())){

            String senha = txtSenha.getText().toString();
            byte [] senhaHash = senha.getBytes() ;

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("username", getUserPreferences());
            intent.putExtra("senha",getSenhaHash(senhaHash).toString());

            startActivity(intent);


        } else {

                String msg = "O usuário não possui acesso ao sistema";
                lblErro.setText(msg);
            }
        }


    public static byte[] getSenhaHash(byte[] buffer){

        byte [] resultado = null;

        try{

            MessageDigest md5 = MessageDigest.getInstance("MD5");
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

    private String getUserPreferences(){  //Método para resgatar user em Shared Preferences.

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        String retorno = preferences.getString("username", "");

        return retorno;
    }

    private String getSenhaPreferences(){  //Método para resgatar senha em Shared Preferences.

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        String retorno = preferences.getString("senha", "");

        return retorno;
    }

    private String getUniqueIDPreferences(){  //Método para resgatar uniqueID em Shared Preferences.

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String retorno = preferences.getString("uniqueId", "");

        return retorno;
    }

    private String isCadastrado(String username){ //Verifica se o username passado no parâmetro está gravado em Shared Preferences e retorna o UniqueId associado e este username.

        String retorno = "";

        if(username.equals(getUserPreferences())){

          retorno = getUniqueIDPreferences();
        }

        return retorno;
    }
}
