package com.example.zodiac.sawa.MyRequests;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.GeneralFunctions;
import com.example.zodiac.sawa.MyFriends.MyFriendsActivity;
import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.interfaces.GetFreinds;
import com.example.zodiac.sawa.models.getFriendsRequest;
import com.example.zodiac.sawa.models.getFriendsResponse;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by raghadq on 5/2/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.zodiac.sawa.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by zodiac on 04/03/2017.
 */
import com.example.zodiac.sawa.interfaces.GetFreinds;
import com.example.zodiac.sawa.models.getFriendsRequest;
import com.example.zodiac.sawa.models.getFriendsResponse;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyRequestsActivity extends Activity {

    GetFreinds service;
    public static List<getFriendsResponse> FreindsList;
    public static ArrayList<friend> LayoutFriendsList = new ArrayList<>();
    public static FastScrollRecyclerView recyclerView;
    public static RecyclerView.Adapter adapter;

    @Override
    protected void onResume() {

        super.onResume();
        this.onCreate(null);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_request_tab);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(GetFreinds.class);

        final ProgressBar progressBar;
        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        ObjectAnimator anim;
        anim = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        anim.setDuration(2000);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();

        adapter = new RequestScroll(this, LayoutFriendsList);
        recyclerView = (FastScrollRecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        final getFriendsRequest request = new getFriendsRequest();
        request.setId(GeneralAppInfo.getUserID());
        GeneralFunctions generalFunctions = new GeneralFunctions();
        boolean isOnline = generalFunctions.isOnline(getApplicationContext());


        if (isOnline == false) {
            Toast.makeText(this, "no internet connection!",
                    Toast.LENGTH_LONG).show();
        } else {
            final Call<List<getFriendsResponse>> FriendsResponse = service.getState(request.getId(), 0);
            FriendsResponse.enqueue(new Callback<List<getFriendsResponse>>() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(Call<List<getFriendsResponse>> call, Response<List<getFriendsResponse>> response) {
                    progressBar.setVisibility(View.GONE);
                    FreindsList = response.body();
                    LayoutFriendsList.clear();
                    if (FreindsList.size() == 0) {

                        setContentView(R.layout.no_friends_to_show);
                        CircleImageView circle = (CircleImageView) findViewById(R.id.circle);
                        circle.setImageDrawable(getDrawable(R.drawable.no_requests_to_show));

                    } else {
                        for (int i = 0; i < FreindsList.size(); i++) {
                            LayoutFriendsList.add(new friend(FreindsList.get(i).getId(), FreindsList.get(i).getUser_image(),
                                    FreindsList.get(i).getFirstName() + " " + FreindsList.get(i).getLast_name()));
                            recyclerView.setAdapter(new RequestScroll(MyRequestsActivity.this, LayoutFriendsList));
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<getFriendsResponse>> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Log.d("fail to get friends ", "Failure to Get friends");

                }
            });
        }
    }


    public class friend {

        String Id;
        String imageResourceId;
        String userName;

        public friend(String Id, String imageResourceId, String userName) {
            setImageResourceId(imageResourceId);
            setId(Id);
            setUserName(userName);
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public void setImageResourceId(String imageResourceId) {
            this.imageResourceId = imageResourceId;
        }

        public String getImageResourceId() throws MalformedURLException {
            return imageResourceId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

    }

}

