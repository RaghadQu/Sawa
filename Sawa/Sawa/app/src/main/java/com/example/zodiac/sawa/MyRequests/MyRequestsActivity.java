package com.example.zodiac.sawa.MyRequests;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.example.zodiac.sawa.R;
import com.example.zodiac.sawa.interfaces.GetFreinds;
import com.example.zodiac.sawa.models.getFriendsRequest;
import com.example.zodiac.sawa.models.getFriendsResponse;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

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
        List<getFriendsResponse> FreindsList;
        ArrayList<friend> LayoutFriendsList = new ArrayList<>();

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.friend_request_tab);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://0bdb1326.ngrok.io/Sawa/public/index.php/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            service = retrofit.create(GetFreinds.class);





            final FastScrollRecyclerView recyclerView = (FastScrollRecyclerView) findViewById(R.id.recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new RequestScroll(this, LayoutFriendsList));

            final getFriendsRequest request = new getFriendsRequest();
            request.setId(1);
            final Call<List<getFriendsResponse>> FriendsResponse = service.getState(request.getId(),0);
            FriendsResponse.enqueue(new Callback<List<getFriendsResponse>>() {
                @Override
                public void onResponse(Call<List<getFriendsResponse>> call, Response<List<getFriendsResponse>> response) {
                    FreindsList = response.body();
                    for (int i = 0; i < FreindsList.size(); i++) {
                        LayoutFriendsList.add(new friend(FreindsList.get(i).getUser_image(),
                                FreindsList.get(i).getFirstName() + " " + FreindsList.get(i).getLast_name()));
                        recyclerView.setAdapter(new RequestScroll(MyRequestsActivity.this, LayoutFriendsList));
                    }
                }

                @Override
                public void onFailure(Call<List<getFriendsResponse>> call, Throwable t) {
                    Log.d("fail to get friends ", "Failure to Get friends");

                }
            });

        }


        public class friend {

            String imageResourceId;
            String userName;

            public friend(String imageResourceId, String userName) {
                setImageResourceId(imageResourceId);
                setUserName(userName);
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

