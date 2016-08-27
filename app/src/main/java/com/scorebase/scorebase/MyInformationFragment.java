package com.scorebase.scorebase;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lee young teak on 2016-08-25.
 */
public class MyInformationFragment extends Fragment {

    private TextView nameText;
    private Button uploadImage;
    private CircleImageView profileImage;

    private FirebaseAuth auth;

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference imagesRef;
    private StorageReference spaceRef;

    public final static int PICK_FROM_GALLERY = 1001;

    public MyInformationFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_information, container, false);

        profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        profileImage.setImageBitmap(((MainActivity)getActivity()).getImage_bitmap());
        nameText = (TextView) view.findViewById(R.id.text_view_name);
        uploadImage = (Button) view.findViewById(R.id.button_upload_image);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_GALLERY);
            }
        });


        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://scorebase-6b4ac.appspot.com");
        imagesRef = storageRef.child("profileImage");
        String fileName = auth.getCurrentUser().getEmail() + ".jpg";
        spaceRef = imagesRef.child(fileName);


        nameText.setText(auth.getCurrentUser().getEmail());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    ((MainActivity)getActivity()).setImage_bitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData()));
                    profileImage.setImageBitmap(((MainActivity)getActivity()).getImage_bitmap());
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

