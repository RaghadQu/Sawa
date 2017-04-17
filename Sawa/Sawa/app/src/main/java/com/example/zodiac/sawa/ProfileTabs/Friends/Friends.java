package com.example.zodiac.sawa.ProfileTabs.Friends;

import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zodiac.sawa.R;

import java.util.ArrayList;

/**
 * Created by zodiac on 04/03/2017.
 */

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;


public class Friends extends AppCompatDialogFragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.friends_tab, container, false);

        ArrayList<friend> FriendsList = new ArrayList<>();

        FriendsList.add(new friend(R.drawable.account,
                "Aarti Taha"));
        FriendsList.add(new friend(R.drawable.account,
                "Akash Mona"));
        FriendsList.add(new friend(R.drawable.account,
                "Ajay Khan"));
        FriendsList.add(new friend(R.drawable.account,
                "Amit Abdeen"));
        FriendsList.add(new friend(R.drawable.account,
                "Anand Rasheel"));
        FriendsList.add(new friend(R.drawable.account,
                "Anju Menaboda"));
        FriendsList.add(new friend(R.drawable.account,
                "John kerri"));
        FriendsList.add(new friend(R.drawable.account,
                "Jyoti Halamonty"));
        FriendsList.add(new friend(R.drawable.account,
                "Rakesh Messi"));
        FriendsList.add(new friend(R.drawable.account,
                "Manu Kharboosh"));
        FriendsList.add(new friend(R.drawable.account,
                "Prakash Helarry"));
        FriendsList.add(new friend(R.drawable.account,
                "Puja Halla"));
        FriendsList.add(new friend(R.drawable.account,
                "Suman Lan"));
        FriendsList.add(new friend(R.drawable.account,
                "Tanu Lan"));
        FriendsList.add(new friend(R.drawable.account,
                "Vijay Hello"));

        FastScrollRecyclerView recyclerView = (FastScrollRecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new FastScrollAdapter(getContext(), FriendsList));
        return view;
    }

    public class friend {

        int imageResourceId;
        String userName;


        public friend(int imageResourceId, String userName) {
            this.imageResourceId = imageResourceId;
            this.userName = userName;

        }

        public String getUserName() {
            return userName;
        }

        public int getImageResourceId() {
            return imageResourceId;
        }


    }
}

