package com.example.restauyou.AdminFragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;


public class AdminMenuItemAddFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;

    private ImageButton btnBack;
    private FrameLayout imageUploadButton;
    private  Button btnUpload;
    private TextInputEditText etMenuItemName, etMenuItemDescription, etMenuItemPrice;
    private Uri imageUri;
    private ImageView imageViewLogo;

    private ActivityResultLauncher<Intent> pickImageLauncher ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_menu_item_add, container, false);

//        Firebase
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
//        Image picking launcher intialization
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new androidx.activity.result.ActivityResultCallback<androidx.activity.result.ActivityResult>() {
                    @Override
                    public void onActivityResult(androidx.activity.result.ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            imageUri = result.getData().getData();
                            imageViewLogo.setImageURI(imageUri);
                        }
                    }
                }
        );
        btnBack = view.findViewById(R.id.btnBack);
        imageUploadButton = view.findViewById(R.id.imageUpload);
        imageViewLogo = view.findViewById(R.id.ivImageLogo);

        imageUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent = new Intent();
                imageIntent.setType("image/*");
                imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                pickImageLauncher.launch(Intent.createChooser(imageIntent, "Choose Image"));}
        });

        btnBack.setOnClickListener(v -> {
            // Pop fragment
            getParentFragmentManager().popBackStack();
            // Show the main content
            if (getActivity() != null) {
                getActivity().findViewById(R.id.editMenuContent).setVisibility(View.VISIBLE);
            }
        });


        etMenuItemName = view.findViewById(R.id.etMenuItemName);
        etMenuItemDescription = view.findViewById(R.id.etMenuItemDescription);
        etMenuItemPrice = view.findViewById(R.id.etMenuItemPrice);
        btnUpload = view.findViewById(R.id.btnUpload);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isValid = true;
                if(etMenuItemName.getText() ==null || etMenuItemName.getText().toString().trim().isEmpty()){
                    etMenuItemName.setError("Required");
                    isValid = false;
                }
                if(etMenuItemDescription.getText() ==null || etMenuItemDescription.getText().toString().trim().isEmpty()){
                    etMenuItemDescription.setError("Required");
                    isValid = false;
                }
                if(etMenuItemPrice.getText() ==null || etMenuItemPrice.getText().toString().trim().isEmpty()){
                    etMenuItemPrice.setError("Required");
                    isValid = false;
                }
                if (imageUri == null) {

                    Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                if(!isValid){
                    return;
                }


                uploadMenuImageToStorage(etMenuItemName.getText().toString(), etMenuItemDescription.getText().toString(), etMenuItemPrice.getText().toString(), imageUri);
                Toast.makeText(getContext(),etMenuItemName.getText().toString(),Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }

    private void uploadMenuImageToStorage(String itemName, String itemDescription, String itemPrice, Uri imageUri) {
        if(imageUri == null){
            Toast.makeText(getContext(),"Empty URI", Toast.LENGTH_SHORT).show();
        }
        String fileName = "menu_items/"+ System.currentTimeMillis() + ".jpg";
        StorageReference storageReference= firebaseStorage.getReference().child(fileName);

        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl =  uri.toString();
                        MenuItem menuItem = new MenuItem(itemName, itemDescription,"Available", itemPrice, imageUrl);
                        firebaseFirestore.collection("menu_items").add(menuItem).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getContext(), "Menu item added successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Menu Item Upload Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Image URL get failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}