package com.hhp.ailatrieuphu.view.dialog;

import static com.hhp.ailatrieuphu.view.fragment.PlayFragment.BEST_SCORE;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hhp.ailatrieuphu.database.entity.User;
import com.hhp.ailatrieuphu.databinding.DialogLeaderboardBinding;
import com.hhp.ailatrieuphu.view.OnPlayCallback;
import com.hhp.ailatrieuphu.view.adapter.LeaderBoardAdapter;
import com.hhp.ailatrieuphu.view.base.BaseDialog;
import com.hhp.ailatrieuphu.viewmodel.CommonVM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeaderBoardDialog extends BaseDialog<DialogLeaderboardBinding, CommonVM, OnPlayCallback> {
    public static final String TAG = LeaderBoardDialog.class.getName();
    private final List<User> listUser = new ArrayList<>();
    private LeaderBoardAdapter adapter;
    private boolean isLoaded;

    public LeaderBoardDialog() {
        super(null, null);
    }

    @Override
    public void initView() {
        setCancelable(false);
        binding.btBack.setOnClickListener(view -> dismiss());
        adapter = new LeaderBoardAdapter(requireContext(),listUser);
        binding.rvLeaderBoard.setAdapter(adapter);
        initUserScore();
    }

    private void initUserScore() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("list_user");
        dbRef.orderByChild(BEST_SCORE).limitToLast(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!isLoaded){
                    for(DataSnapshot postSnapshot: snapshot.getChildren()){
                        User user = postSnapshot.getValue(User.class);
                        adapter.addUser(user);
                    }
                    isLoaded = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        HashMap<String, Integer> mapUser = new HashMap<>();
//        addUser(mapUser, "HHP123", 111);
//        addUser(mapUser, "HHP124", 11);
//        addUser(mapUser, "HHP125", 11);
//        addUser(mapUser, "HHP123", 100);
//        addUser(mapUser, "HHP123", 112);
//        addUser(mapUser, "HHP125", 12);
//        addUser(mapUser, "HHP126", 101);
//        List<User> listUser = new ArrayList<>();
//        mapUser.forEach((username, score) -> listUser.add(new User(username, score)));
//        listUser.sort((user1, user2) -> user2.getBestScore() - user1.getBestScore());
//        LeaderBoardAdapter adapter = new LeaderBoardAdapter(requireContext(), listUser);
//        binding.rvLeaderBoard.setAdapter(adapter);
    }

    private void addUser(HashMap<String, Integer> mapUser, String username, int score) {
        Integer oldScore = mapUser.get(username);
        if (oldScore == null || oldScore < score) mapUser.put(username, score);
    }


    @Override
    public Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    public DialogLeaderboardBinding initViewBinding() {
        return DialogLeaderboardBinding.inflate(getLayoutInflater());
    }
}
