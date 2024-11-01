package com.example.coffeeappmanage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.ResponseUser;
import com.example.coffeeappmanage.model.User;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import retrofit2.Call;
import retrofit2.Callback;

public class ForgotPasswordActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edtEmail;
    TextView tvDangKy;
    Button btnXacThuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtEmail = findViewById(R.id.edtEmail);
        tvDangKy = findViewById(R.id.tvDangKy);
        btnXacThuc = findViewById(R.id.btnXacThuc);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        
        btnXacThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerifyCode();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.back_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuBack) {
            // Xử lý sự kiện nhấn nút Back
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void sendVerifyCode() {
        String email = edtEmail.getText().toString().trim();
        if(email.length() == 0){
            Toast.makeText(this, "Yêu cầu nhập Email !", Toast.LENGTH_SHORT).show();
        } else if(!isValidEmail(email)) {
            Toast.makeText(this, "Email không đúng định dạng !", Toast.LENGTH_SHORT).show();
        }else {
            ApiService.apiService.getAllUser().enqueue(new Callback<ResponseUser>() {
                @Override
                public void onResponse(Call<ResponseUser> call, retrofit2.Response<ResponseUser> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ResponseUser responseUser = response.body();
//                        List<User> listUser = responseData.getData();
                        List<User> listUser = (List<User>) responseUser.getData();
                        int statusCode = responseUser.getStatusCode();

                        boolean check = false;
                        for(User user : listUser){
                            if(user.getEmail().equals(email)){
                                check =true;
                                Toast.makeText(ForgotPasswordActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                sendEmailInBackground("anhtainguyen.tan@gmail.com", "1234");
                            }
                        }
                        if(check == false){
                            Toast.makeText(ForgotPasswordActivity.this, "Email chưa được đăng ký!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseUser> call, Throwable throwable) {
                    Toast.makeText(ForgotPasswordActivity.this, "Call api failed" + throwable.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("e", throwable.toString());
                }
            });
        }
    }


    static final String from = "anhtai22042004@gmail.com";
    static final String password = "zxno dxkc wjag xcrk";

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private void sendEmailInBackground(String to, String noiDung) {
        executorService.execute(() -> {
            try {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");

                Authenticator auth = new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                };

                Session session = Session.getInstance(props, auth);
                MimeMessage msg = new MimeMessage(session);
                msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
                msg.setFrom(from);
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
                msg.setSubject("Thử nghiệm gửi email");
                msg.setSentDate(new Date());
                msg.setContent(noiDung, "text/html");

                Transport.send(msg);
            } catch (Exception e) {
                Log.e("error", e.toString());
            }
        });
    }

}