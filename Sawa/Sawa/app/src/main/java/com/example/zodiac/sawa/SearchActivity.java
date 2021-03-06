package com.example.zodiac.sawa;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.example.zodiac.sawa.MenuActiviries.MyFriendsActivity;
import com.example.zodiac.sawa.RecyclerViewAdapters.FastScrollAdapter;
import com.example.zodiac.sawa.Spring.Models.UserModel;
import com.example.zodiac.sawa.SpringApi.SearchInterface;
import com.example.zodiac.sawa.models.getFriendsResponse;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    SearchView searchView;
    SearchInterface searchInterface;
    public static List<getFriendsResponse> FreindsList;
    public static List<UserModel> userModelList;
    public static ArrayList<MyFriendsActivity.friend> LayoutFriendsList = new ArrayList<>();
    public static FastScrollRecyclerView recyclerView;
    public static RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchView mSearchView = (SearchView) findViewById(R.id.search);
        //mSearchView.setSelected(true);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setIconified(false);
        //   mSearchView.setFocusable(true);

        adapter = new FastScrollAdapter(this, LayoutFriendsList, 1);
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
                    recyclerView.setAdapter(new FastScrollAdapter(SearchActivity.this, LayoutFriendsList, 1));
                } else sendSearchQuery(s);


                return false;
            }

        });


    }

    public void sendSearchQuery(String word) {
        LayoutFriendsList.clear();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        searchInterface = retrofit.create(SearchInterface.class);


        // final getFriendsRequest request = new getFriendsRequest();
        final Call<List<UserModel>> FriendsResponse = searchInterface.getSearchResult(word);
        FriendsResponse.enqueue(new Callback<List<UserModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.code() == 404 || response.code() == 500 || response.code() == 502 || response.code() == 400) {
                    GeneralFunctions generalFunctions = new GeneralFunctions();
                    generalFunctions.showErrorMesaage(getApplicationContext());
                } else {


                    userModelList = response.body();
                    LayoutFriendsList.clear();

                    if (userModelList != null) {

                        if (userModelList.size() == 0) {

                            recyclerView.setAdapter(new FastScrollAdapter(SearchActivity.this, LayoutFriendsList, 1));

                        } else {
                            Log.d("Not null", Integer.toString(userModelList.get(0).getId()));
                            for (int i = 0; i < userModelList.size(); i++) {
                                LayoutFriendsList.add(new MyFriendsActivity.friend(Integer.valueOf(userModelList.get(i).getId()), userModelList.get(i).getImage(),
                                        userModelList.get(i).getFirst_name() + " " + userModelList.get(i).getLast_name()));
                                recyclerView.setAdapter(new FastScrollAdapter(SearchActivity.this, LayoutFriendsList, 1));
                            }
                        }
                    }
                }
             /*   else{

                    recyclerView.setAdapter(new FastScrollAdapter(SearchActivity.this, LayoutFriendsList));}*/

            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                // progressBar.setVisibility(View.GONE);
                GeneralFunctions generalFunctions = new GeneralFunctions();
                generalFunctions.showErrorMesaage(getApplicationContext());
                Log.d("fail to get friends ", "Failure to Get friends");

            }
        });
    }

}

