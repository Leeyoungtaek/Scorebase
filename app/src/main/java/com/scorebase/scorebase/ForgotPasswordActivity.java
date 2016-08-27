package com.scorebase.scorebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnResetPassword, btnBack;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        inputEmail = (EditText)findViewById(R.id.edit_text_email);

        btnResetPassword = (Button)findViewById(R.id.button_reset_password);
        btnBack = (Button)findViewById(R.id.button_back);
        btnBack.setText("<<BACK");

        auth = FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "이메일을 확인해주세요!", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "이메일에 보내는 것을 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}
