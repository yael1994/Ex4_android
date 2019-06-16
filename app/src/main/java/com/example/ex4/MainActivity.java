package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText IP;
    private EditText Port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        IP= findViewById(R.id.IP);
        Port=findViewById(R.id.port);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActionJoistic();
            }
        });
    }
    public void openActionJoistic(){
        int port =Integer.parseInt(Port.getText().toString());
       // Client client = new Client(IP.getText().toString(),port);
       // client.Connect();
        Intent intent= new Intent(this, JoystickActivity.class);
        //intent.putExtra("Client",client);
        intent.putExtra("ip", IP.getText().toString());
        intent.putExtra("port",port);

        startActivity(intent);
    }


}
