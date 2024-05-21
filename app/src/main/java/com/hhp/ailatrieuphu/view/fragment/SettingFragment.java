package com.hhp.ailatrieuphu.view.fragment;

import static android.app.Activity.RESULT_OK;
import static com.hhp.ailatrieuphu.database.entity.User.DEFAULT_NAME;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hhp.ailatrieuphu.R;
import com.hhp.ailatrieuphu.databinding.FragmentSettingBinding;
import com.hhp.ailatrieuphu.view.base.BaseFragment;
import com.hhp.ailatrieuphu.view.dialog.ChangePasswordDialog;
import com.hhp.ailatrieuphu.view.dialog.SignOutConfirmDialog;
import com.hhp.ailatrieuphu.view.listener.TextChangeListener;
import com.hhp.ailatrieuphu.viewmodel.CommonVM;

import java.util.HashMap;
import java.util.Map;


public class SettingFragment extends BaseFragment<FragmentSettingBinding, CommonVM> {
    public static final String TAG = SettingFragment.class.getName();
    private FirebaseUser user;
    private DatabaseReference dbRef;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private String path;
    private boolean username;

    private Uri avatarUri;

    @Override
    public void initView() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("list_user");
        path = user.getEmail().replace(".", "-");

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                avatarUri = result.getData().getData();
                if (avatarUri == null) return;
                requireActivity().getContentResolver().takePersistableUriPermission(avatarUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                binding.pbLoading.setVisibility(View.VISIBLE);
                user.updateProfile(new UserProfileChangeRequest.Builder()
                        .setPhotoUri(avatarUri).build()).addOnCompleteListener(task -> {
                    saveAvatarToStorage();
                });
            }
        });

        binding.edtUsername.addTextChangedListener((TextChangeListener) editable -> {
            username = editable.toString().length() < 17 && !editable.toString().isEmpty() && !editable.toString().equals(DEFAULT_NAME);
            binding.tvWarning.setVisibility(username ? View.GONE : View.VISIBLE);
        });

        if (user != null) {
            String username = user.getDisplayName() == null || user.getDisplayName().isEmpty() ? DEFAULT_NAME : user.getDisplayName();
            String email = user.getEmail();
            Uri imageUri = user.getPhotoUrl();
            binding.tvUsername.setText(username);
            binding.tvEmail.setText(email);
            binding.ivAvatar.setImageURI(imageUri);
        }

        binding.ivSignOut.setOnClickListener(this);
        binding.ivEdit.setOnClickListener(this);
        binding.ivCancel.setOnClickListener(this);
        binding.ivConfirm.setOnClickListener(this);
        binding.ivAvatar.setOnClickListener(this);
        binding.tvChangePassword.setOnClickListener(this);

    }

    private void saveAvatarToStorage() {
        String ext = getFireExtension(avatarUri);
        StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("avatar/" + path + "." + ext);
        fileRef.putFile(avatarUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                saveAvatarToDB(fileRef);
            }
        });

    }

    private void saveAvatarToDB(StorageReference fileRef) {
        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String path = user.getEmail().replace(".", "-");
                Map<String, Object> mapUpdate = new HashMap<>();
                mapUpdate.put("avatar", uri.toString());
                dbRef.child(path).updateChildren(mapUpdate).addOnCompleteListener(task -> {
                    binding.ivAvatar.setImageURI(avatarUri);
                    binding.pbLoading.setVisibility(View.GONE);
                });
            }
        });
    }


    private String getFireExtension(Uri imageUri) {
        ContentResolver cr = requireActivity().getContentResolver();
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(cr.getType(imageUri));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivSignOut) {
            SignOutConfirmDialog dialog = new SignOutConfirmDialog(callback);
            dialog.show(getChildFragmentManager(), SignOutConfirmDialog.TAG);
        } else if (view.getId() == R.id.ivEdit) {
            editUsername();
        } else if (view.getId() == R.id.ivCancel) {
            binding.tvWarning.setVisibility(View.GONE);
            changeUsername(false);
        } else if (view.getId() == R.id.ivConfirm) {
            changeUsername(true);
        } else if (view.getId() == R.id.ivAvatar) {
            changeAvatar();
        } else if (view.getId() == R.id.tvChangePassword) {
            ChangePasswordDialog dialog = new ChangePasswordDialog(callback);
            dialog.show(getChildFragmentManager(), ChangePasswordDialog.TAG);
        }
    }

    private void changeAvatar() {
        if (requireActivity().checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            requireActivity().requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 101);
        } else {
            galleryLauncher.launch(new Intent(MediaStore.ACTION_PICK_IMAGES));
        }
    }

    private void changeUsername(boolean isChanged) {
        binding.ivEdit.setVisibility(View.VISIBLE);
        binding.tvUsername.setVisibility(View.VISIBLE);
        binding.edtUsername.setVisibility(View.GONE);
        binding.ivCancel.setVisibility(View.GONE);
        binding.ivConfirm.setVisibility(View.GONE);
        if (isChanged) {
            String newName = binding.edtUsername.getText().toString().trim();
            if (newName.isEmpty() || newName.length() > 17) {
                Toast.makeText(context, "Invalid Username", Toast.LENGTH_SHORT).show();
                return;
            }
            binding.pbLoading.setVisibility(View.VISIBLE);
            user.updateProfile(new UserProfileChangeRequest.Builder()
                    .setDisplayName(newName).build()).addOnCompleteListener(task -> {
                saveUsernameToDB(newName);
            });
        }
    }

    private void saveUsernameToDB(String newName) {
        String path = user.getEmail().replace(".", "-");
        Map<String, Object> mapUpdate = new HashMap<>();
        mapUpdate.put("name", newName);
        dbRef.child(path).updateChildren(mapUpdate).addOnCompleteListener(task -> {
            binding.tvUsername.setText(newName);
            binding.pbLoading.setVisibility(View.GONE);
        });
    }


    private void editUsername() {
        binding.edtUsername.setText(binding.tvUsername.getText());
        binding.ivEdit.setVisibility(View.GONE);
        binding.tvUsername.setVisibility(View.GONE);
        binding.edtUsername.setVisibility(View.VISIBLE);
        binding.ivCancel.setVisibility(View.VISIBLE);
        binding.ivConfirm.setVisibility(View.VISIBLE);
    }

    @Override
    public Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    public FragmentSettingBinding initViewBinding() {
        return FragmentSettingBinding.inflate(getLayoutInflater());
    }
}
