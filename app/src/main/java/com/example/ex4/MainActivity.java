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
        IP=(EditText)findViewById(R.id.IP);
        Port=(EditText)findViewById(R.id.port);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActionJoistic();
            }
        });
    }
    public void openActionJoistic(){
//       InetAddress inet=null;
//        int portSend=Integer.parseInt( Port.getText().toString());
//        byte[] ip = IP.getText().toString().getBytes();
//        try {
//            inet = InetAddress.getByAddress(ip);
//            }catch (Exception e){
//        }
//        Client client = new Client(inet,portSend);
        Intent intent= new Intent(this, JoystickActivity.class);
        //intent.putExtra("Client",client);
        startActivity(intent);
    }


}
