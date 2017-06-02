package com.example.zodiac.sawa.MyFriends;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zodiac.sawa.GeneralAppInfo;
import com.example.zodiac.sawa.MyRequests.MyRequestsActivity;
import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.interfaces.GetFreinds;
import com.example.zodiac.sawa.models.getFriendsRequest;
import com.example.zodiac.sawa.models.getFriendsResponse;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import org.w3c.dom.Text;

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

/**
 * Created by zodiac on 04/03/2017.
 */


public class MyFriendsActivity extends Activity {


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
        setContentView(R.layout.friends_tab);
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


        adapter = new FastScrollAdapter(this, LayoutFriendsList,0);

        recyclerView = (FastScrollRecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        final getFriendsRequest request = new getFriendsRequest();
        request.setId(GeneralAppInfo.getUserID());
        final Call<List<getFriendsResponse>> FriendsResponse = service.getState(request.getId(), 1);
        FriendsResponse.enqueue(new Callback<List<getFriendsResponse>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<List<getFriendsResponse>> call, Response<List<getFriendsResponse>> response) {
                progressBar.setVisibility(View.GONE);
                FreindsList = response.body();

                LayoutFriendsList.clear();
                if(FreindsList!=null){

                if (FreindsList.size() ==0 )
                {

                    setContentView(R.layout.no_friends_to_show);
                    CircleImageView circle = (CircleImageView)findViewById(R.id.circle);
                    circle.setImageDrawable(getDrawable(R.drawable.no_friends));
                    TextView text= (TextView) findViewById(R.id.text);
                    text.setText("Friends");

                }
                for (int i = 0; i < FreindsList.size(); i++) {
                    LayoutFriendsList.add(new friend(FreindsList.get(i).getId(), FreindsList.get(i).getUser_image(),
                            FreindsList.get(i).getFirstName() + " " + FreindsList.get(i).getLast_name()));
                    recyclerView.setAdapter(new FastScrollAdapter(MyFriendsActivity.this, LayoutFriendsList,0));
                }
            }}

            @Override
            public void onFailure(Call<List<getFriendsResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Log.d("fail to get friends ", "Failure to Get friends");

            }
        });


    }


    public static class friend {
        String Id;
        String imageResourceId;
        String userName;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public friend(String Id, String imageResourceId, String userName) {
            setImageResourceId(imageResourceId);
            setUserName(userName);
            setId(Id);
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

