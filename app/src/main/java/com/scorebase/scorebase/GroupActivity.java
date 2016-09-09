package com.scorebase.scorebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class GroupActivity extends AppCompatActivity {

    // Views
    private TextView groupName, accessScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Intent intent = getIntent();
        Group group = (Group) intent.getSerializableExtra("group");

        // View Reference
        groupName = (TextView)findViewById(R.id.text_view_group_name);
        accessScope = (TextView)findViewById(R.id.text_view_access_scope);

        groupName.setText(group.getName());
        accessScope.setText("< " + group.getAccessScope() + " >");
    }
}
