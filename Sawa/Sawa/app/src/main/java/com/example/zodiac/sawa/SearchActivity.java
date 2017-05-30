package com.example.zodiac.sawa;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zodiac.sawa.MyFriends.FastScrollAdapter;
import com.example.zodiac.sawa.MyFriends.MyFriendsActivity;
import com.example.zodiac.sawa.interfaces.GetFreinds;
import com.example.zodiac.sawa.interfaces.SerachApi;
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

public class SearchActivity extends AppCompatActivity {
    SearchView searchView;
    SerachApi serachApi;
    public static List<getFriendsResponse> FreindsList;
    public static ArrayList<MyFriendsActivity.friend> LayoutFriendsList = new ArrayList<>();
    public static FastScrollRecyclerView recyclerView;
    public static RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchView mSearchView = (SearchView) findViewById(R.id.search);
        mSearchView.setSelected(true);
        adapter = new FastScrollAdapter(this, LayoutFriendsList,1);
        recyclerView = (FastScrollRecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("Enter the query", " Enter search submit " + s);
                sendSearchQuery(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("Enter the query", " Enter search change " + s + " here is the change");
                if (s.length() == 0) {
                    LayoutFriendsList.clear();
                    recyclerView.setAdapter(new FastScrollAdapter(SearchActivity.this, LayoutFriendsList,1));
                } else sendSearchQuery(s);

                return false;
            }
        });


    }

    public void sendSearchQuery(String word) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        serachApi = retrofit.create(SerachApi.class);


        // final getFriendsRequest request = new getFriendsRequest();
        final Call<List<getFriendsResponse>> FriendsResponse = serachApi.getSearchResult(word);
        FriendsResponse.enqueue(new Callback<List<getFriendsResponse>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<List<getFriendsResponse>> call, Response<List<getFriendsResponse>> response) {
                FreindsList = response.body();
                LayoutFriendsList.clear();


                //  LayoutFriendsList.clear();

             /*   if (FreindsList.size() ==0 )
                {

                    setContentView(R.layout.no_friends_to_show);
                    CircleImageView circle = (CircleImageView)findViewById(R.id.circle);
                    circle.setImageDrawable(getDrawable(R.drawable.no_friends_to_show));
                    TextView text= (TextView) findViewById(R.id.text);
                    text.setText("Friends");

                }*/
                Log.d("Enter", " FriendList not null");
                if (FreindsList != null) {
                    if (FreindsList.size() == 0) {
                        recyclerView.setAdapter(new FastScrollAdapter(SearchActivity.this, LayoutFriendsList,1));

                    } else {
                        for (int i = 0; i < FreindsList.size(); i++) {
                            LayoutFriendsList.add(new MyFriendsActivity.friend(FreindsList.get(i).getId(), FreindsList.get(i).getUser_image(),
                                    FreindsList.get(i).getFirstName() + " " + FreindsList.get(i).getLast_name()));
                            recyclerView.setAdapter(new FastScrollAdapter(SearchActivity.this, LayoutFriendsList,1));
                        }
                    }
                }
             /*   else{

                    recyclerView.setAdapter(new FastScrollAdapter(SearchActivity.this, LayoutFriendsList));}*/

            }

            @Override
            public void onFailure(Call<List<getFriendsResponse>> call, Throwable t) {
                // progressBar.setVisibility(View.GONE);

                Log.d("fail to get friends ", "Failure to Get friends");

            }
        });
    }

}

