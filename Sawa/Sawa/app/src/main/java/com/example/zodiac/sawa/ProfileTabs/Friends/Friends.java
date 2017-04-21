package com.example.zodiac.sawa.ProfileTabs.Friends;

import android.os.Bundle;
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


public class Friends extends AppCompatDialogFragment {

    View view;
    GetFreinds service;
    List<getFriendsResponse> FreindsList;
    ArrayList<friend> LayoutFriendsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.friends_tab, container, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://0bdb1326.ngrok.io/Sawa/public/index.php/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(GetFreinds.class);

        final FastScrollRecyclerView recyclerView = (FastScrollRecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new FastScrollAdapter(getContext(), LayoutFriendsList));

        final getFriendsRequest request = new getFriendsRequest();
        request.setId(1);
        final Call<List<getFriendsResponse>> FriendsResponse = service.getState(request.getId());
        FriendsResponse.enqueue(new Callback<List<getFriendsResponse>>() {
            @Override
            public void onResponse(Call<List<getFriendsResponse>> call, Response<List<getFriendsResponse>> response) {
                FreindsList = response.body();
                for (int i = 0; i < FreindsList.size(); i++) {
                    LayoutFriendsList.add(new friend(FreindsList.get(i).getUser_image(),
                            FreindsList.get(i).getFirstName() + " " + FreindsList.get(i).getLast_name()));
                    recyclerView.setAdapter(new FastScrollAdapter(getContext(), LayoutFriendsList));
                }
            }

            @Override
            public void onFailure(Call<List<getFriendsResponse>> call, Throwable t) {
                Log.d("fail to get friends ", "Failure to Get friends");

            }
        });

        return view;
    }


    public class friend {

        URL imageResourceId;
        String userName;

        public friend(URL imageResourceId, String userName) {
            setImageResourceId(imageResourceId);
            setUserName(userName);
        }


        public void setImageResourceId(URL imageResourceId) {
            this.imageResourceId = imageResourceId;
        }

        public URL getImageResourceId() throws MalformedURLException {
            if (imageResourceId != null)
                return imageResourceId;
            else return (new URL("R.drawable.home"));
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
  
    }
}

