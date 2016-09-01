package com.scorebase.scorebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddGroupActivity extends AppCompatActivity {

    // RecyclerView
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Sport> sports;

    // Views
    private EditText editTextGroupName;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button addButton;

    // Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        // View Reference
        editTextGroupName = (EditText)findViewById(R.id.edit_text_group_name);
        radioGroup = (RadioGroup)findViewById(R.id.radio_group);
        radioButton = (RadioButton)findViewById(R.id.button_public);
        addButton = (Button)findViewById(R.id.button_group_add);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        // Firebase Reference
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        // RecyclerView Setting
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initializeData();
        mAdapter = new AddSportAdapter(sports);
        mRecyclerView.setAdapter(mAdapter);

        // View Setting
        radioButton.setChecked(true);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sports state check
                boolean states[] = new boolean[5];
                // Group Name
                String groupName = editTextGroupName.getText().toString().trim();

                // Access Scope
                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton checkedRadioButton = (RadioButton)findViewById(id);
                String accessScope = checkedRadioButton.getText().toString();

                // RecyclerView state
                for (int i=0; i<mAdapter.getItemCount(); i++){
                    RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForAdapterPosition(i);
                    AddSportAdapter.SportViewHolder sportViewHolder = (AddSportAdapter.SportViewHolder) viewHolder;
                    if(sportViewHolder==null){
                        states[i] = false;
                    }else{
                        states[i] = sportViewHolder.getState();
                    }
                }

                // Error Check
                if(TextUtils.isEmpty(groupName) || !isChecked(states)){
                    Toast.makeText(getApplicationContext(), "입력을 완료해주세요!", Toast.LENGTH_LONG).show();
                    return;
                }

                // Save in Database
                databaseReference.child("group").push().setValue(new Group(groupName, accessScope, states));
                finish();
            }
        });
    }

    //  Check RecyclerView is Empty
    private boolean isChecked(boolean[] states){
        for (int i=0; i<states.length; i++){
            if(states[i]==true){
                return true;
            }
        }
        return false;
    }

    // sports init
    private void initializeData(){
        sports = new ArrayList<>();
        sports.add(new Sport(R.drawable.baseball, Group.sportNames[0]));
        sports.add(new Sport(R.drawable.basketball, Group.sportNames[1]));
        sports.add(new Sport(R.drawable.boxing, Group.sportNames[2]));
        sports.add(new Sport(R.drawable.soccer, Group.sportNames[3]));
        sports.add(new Sport(R.drawable.tennisball, Group.sportNames[4]));
    }

    // Sport Class
    class Sport{
        int imageId;
        String name;
        Sport(int imageId, String name){
            this.imageId = imageId;
            this.name = name;
        }
    }
}
