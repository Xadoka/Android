package com.example.pantrypal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Registration extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword, editTextPhoneNumber, editTextName, editTextSurname, editTextLastname, editTextIIN, editTextRepassword;
    RadioGroup radioGroupGender;
    RadioButton radioButtonGenderSelected;
    Button buttonRegistration;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    Button textView;
    Spinner rolesSpinner;
    Spinner genderSpinner;
    String[] roles;
    String[] genders;
    String role;
    String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextRepassword = findViewById(R.id.repassword);
        editTextPhoneNumber = findViewById(R.id.phoneNumber);
        editTextName = findViewById(R.id.name);
        editTextSurname = findViewById(R.id.Surname);
        editTextLastname = findViewById(R.id.lastname);
        editTextIIN = findViewById(R.id.iin);
        buttonRegistration = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);
        rolesSpinner = findViewById(R.id.roles_spinner);
        genderSpinner = findViewById(R.id.gender_spinner);

        roles = getResources().getStringArray(R.array.roles);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rolesSpinner.setAdapter(adapter);

        genders = getResources().getStringArray(R.array.sex);
        ArrayAdapter<String> adapterGender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapterGender);

        rolesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                role = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                //Gender Selected method
                //int selectedGenderID = radioGroupGender.getCheckedRadioButtonId();
                //radioButtonGenderSelected = findViewById(selectedGenderID);

                String email, password, phone, name, surname, lastname, iin, date, repassword;
                email = String.valueOf(editTextEmail.getText().toString());
                password = String.valueOf(editTextPassword.getText().toString());
                phone = String.valueOf(editTextPhoneNumber.getText().toString());
                name = String.valueOf(editTextName.getText().toString());
                surname = String.valueOf(editTextSurname.getText().toString());
                lastname = String.valueOf(editTextLastname.getText().toString());
                iin = String.valueOf(editTextIIN.getText().toString());
                repassword = String.valueOf(editTextRepassword.getText().toString());



                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Registration.this, "Введите email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Registration.this, "Введите пароль", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(repassword)) {
                    Toast.makeText(Registration.this, "Подтвердите пароль", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(Registration.this, "Введите имя", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(lastname)) {
                    Toast.makeText(Registration.this, "Введите фамилию", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(surname)) {
                    Toast.makeText(Registration.this, "Введите отчество", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(iin)) {
                    Toast.makeText(Registration.this, "Введите ИИН", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(Registration.this, "Введите номер телефона", Toast.LENGTH_SHORT).show();
                    return;
                }

                //filter
                if (phone.length() != 11) {
                    Toast.makeText(Registration.this, "Введите корректный номер правильно", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 8 || password.length() > 20) {
                    Toast.makeText(Registration.this, "Введите корректный пароль", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (iin.length() != 12) {
                    Toast.makeText(Registration.this, "Длина ИИН должна составлять 12 чисел", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(repassword)) {
                    Toast.makeText(Registration.this, "Введенные пароли не совпадают", Toast.LENGTH_SHORT).show();
                    return;
                }
                //formatter IIN for birthday date
                IinFormatter dateMain = new IinFormatter(iin);
                date = dateMain.getDateMain();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = mAuth.getCurrentUser();


                            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(name, surname, lastname, phone, iin, date, role, gender);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registeres users");
                            reference.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        firebaseUser.sendEmailVerification();

                                        Toast.makeText(Registration.this, "Authentication created.", Toast.LENGTH_SHORT).show();


                                        Intent intent = new Intent(getApplicationContext(), Registration.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                    }
                                }
                            });
                        } else {
                            progressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Registration.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}