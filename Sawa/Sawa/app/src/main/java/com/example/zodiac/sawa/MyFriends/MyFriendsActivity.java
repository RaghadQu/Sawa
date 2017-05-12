package com.example.zodiac.sawa.MyFriends;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.example.zodiac.sawa.GeneralAppInfo;
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

/**
 * Created by zodiac on 04/03/2017.
 */


public class MyFriendsActivity  extends Activity {



        GetFreinds service;
        static List<getFriendsResponse> FreindsList;
        static ArrayList<friend> LayoutFriendsList = new ArrayList<>();
        static FastScrollRecyclerView recyclerView;
        static RecyclerView.Adapter adapter;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.friends_tab);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GeneralAppInfo.BACKEND_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            service = retrofit.create(GetFreinds.class);
            final FastScrollRecyclerView recyclerView = (FastScrollRecyclerView) findViewById(R.id.recycler);

            adapter=new FastScrollAdapter(this, LayoutFriendsList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

            final getFriendsRequest request = new getFriendsRequest();
            request.setId(1);
            Log.d("arrive","s");
            final Call<List<getFriendsResponse>> FriendsResponse = service.getState(request.getId(),1);
            FriendsResponse.enqueue(new Callback<List<getFriendsResponse>>() {
                @Override
                public void onResponse(Call<List<getFriendsResponse>> call, Response<List<getFriendsResponse>> response) {
                    FreindsList = response.body();
                    Log.d("arrive","sss");

                    LayoutFriendsList.clear();
                    for (int i = 0; i < FreindsList.size(); i++) {
                        Log.d("arrive","aq");

                        Log.d("FreindsList",""+FreindsList.get(i).getId());

                        LayoutFriendsList.add(new friend(FreindsList.get(i).getId(),FreindsList.get(i).getUser_image(),
                                FreindsList.get(i).getFirstName() + " " + FreindsList.get(i).getLast_name()));
                    }
                    Log.d("arrive","aa");

                    recyclerView.setAdapter(new FastScrollAdapter(MyFriendsActivity.this, LayoutFriendsList));

                }

                @Override
                public void onFailure(Call<List<getFriendsResponse>> call, Throwable t) {
                    Log.d("fail to get friends ", "Failure to Get friends");

                }
            });


        }



        public class friend {
            String Id;
            String imageResourceId;
            String userName;

            public String getId() {
                return Id;
            }

            public void setId(String id) {
                Id = id;
            }

            public friend(String Id,String imageResourceId, String userName) {
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
