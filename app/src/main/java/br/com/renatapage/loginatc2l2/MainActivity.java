package br.com.renatapage.loginatc2l2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView lblUser = (TextView) findViewById(R.id.lblUser);
        TextView lblSenha = (TextView) findViewById(R.id.lblSenha);

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            return;
        }

        String user = extras.getString("username");
        String senha = extras.getString("senha");

        if(user != null){

            lblUser.setText("Username: " + user);
        }

        if(senha != null){

            lblSenha.setText("Password: " + senha);
        }

    }
}
