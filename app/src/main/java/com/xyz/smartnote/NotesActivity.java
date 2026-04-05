package com.xyz.smartnote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    private EditText edtTitleAN,edtNotesAN;
    private TextView txtTitleAN,txtNotesAN,btnDelete;
    private RelativeLayout relTxtNote,relEdtNote;
    int position = -1;
    Button btnSaveAN,btnEditAN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        relTxtNote = findViewById(R.id.relTxtNote);
        relEdtNote = findViewById(R.id.relEdtNote);

        txtTitleAN = findViewById(R.id.txtTitleAN);
        txtNotesAN = findViewById(R.id.txtNotesAN);

        edtTitleAN = findViewById(R.id.edtTitleAN);
        edtNotesAN = findViewById(R.id.edtNotesAN);

        btnEditAN = findViewById(R.id.btnEditAN);
        btnSaveAN = findViewById(R.id.btnSaveAN);
        btnDelete = findViewById(R.id.btnDelete);

        //default view case for Addition of note
        relEdtNote.setVisibility(View.VISIBLE);
        relTxtNote.setVisibility(View.GONE);

        //view of note
        Intent intent = getIntent();
        if(null != intent){
            position = intent.getIntExtra("position",-1);
            if(position!=-1){
                String title = Utils.getInstance(this).getNotes().get(position).getTitle();
                String note = Utils.getInstance(this).getNotes().get(position).getNote();
                edtTitleAN.setText(title);
                edtNotesAN.setText(note);
                txtTitleAN.setText(title);
                txtNotesAN.setText(note);
                relEdtNote.setVisibility(View.GONE);
                relTxtNote.setVisibility(View.VISIBLE);
            }
        }

        //edit of note
        relTxtNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relEdtNote.setVisibility(View.VISIBLE);
                relTxtNote.setVisibility(View.GONE);
                Notes note = Utils.getInstance(NotesActivity.this).getNotes().get(position);
                Utils.getInstance(NotesActivity.this).removeNotes(note);
            }
        });

        //on back press addition of notes feature
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(relEdtNote.getVisibility()==View.VISIBLE){
                    Notes note = new Notes(edtTitleAN.getText().toString(),edtNotesAN.getText().toString());
                    if(!note.getNote().isEmpty()){
                        Utils.getInstance(NotesActivity.this).setNotes(note);
                    }
                }
                Intent intent = new Intent(NotesActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }
}