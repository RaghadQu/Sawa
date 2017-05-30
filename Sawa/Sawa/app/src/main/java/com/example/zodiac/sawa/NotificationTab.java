package com.example.zodiac.sawa;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zodiac.sawa.DB.DBHandler;
import com.example.zodiac.sawa.MyRequests.MyRequestsActivity;
import com.example.zodiac.sawa.MyRequests.RequestScroll;
import com.example.zodiac.sawa.RecyclerViewAdapters.NotificationAdapter;
import com.example.zodiac.sawa.interfaces.AboutUserApi;
import com.example.zodiac.sawa.interfaces.NotificationApi;
import com.example.zodiac.sawa.models.AboutUser;
import com.example.zodiac.sawa.models.Notification;
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
 * Created by zodiac on 05/22/2017.
 */

public class NotificationTab extends AppCompatDialogFragment {

    View view;
    Context context=getContext();
    public static ArrayList<NotificationAdapter.NotificationRecyclerViewDataProvider> NotificationList = new ArrayList<>();
    public static FastScrollRecyclerView recyclerView;
    public static NotificationAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        adapter = new NotificationAdapter(NotificationList);
        view = inflater.inflate(R.layout.notification_tab, container, false);
        recyclerView=(FastScrollRecyclerView)view.findViewById(R.id.recyclerNotification);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getUserNotifications(context);

        return view;


    }


    public static void getUserNotifications(final Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralAppInfo.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        NotificationApi notificationApi = retrofit.create(NotificationApi.class);
        Call<Notification> notificationResponse = notificationApi.getNotification(GeneralAppInfo.getUserID());

        notificationResponse.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                Notification UserNotification;
                UserNotification = response.body();
                 NotificationList.clear();
                if(UserNotification!= null){
                for (int i = 0; i < UserNotification.getNot_sent_notifications().size(); i++) {
                    Notification.NotificationInfo notificationOne=UserNotification.getNot_sent_notifications().get(i);
                    NotificationList.add(new NotificationAdapter.NotificationRecyclerViewDataProvider(context,Integer.valueOf(notificationOne.getFriend1_id()),notificationOne.getUser_image(),(notificationOne.getFirst_name()+" "+notificationOne.getLast_name()),notificationOne.getTimestamp(),Integer.valueOf(notificationOne.getRead_flag())));
                    NotificationList.get(i).setType(notificationOne.getType());
                    NotificationList.get(i).setNotificatioId(notificationOne.getId());
                }

                for (int i = 0; i < UserNotification.getSent_notifications().size(); i++) {
                    Notification.NotificationInfo notificationOne=UserNotification.getSent_notifications().get(i);
                    NotificationList.add(new NotificationAdapter.NotificationRecyclerViewDataProvider(context,Integer.valueOf(notificationOne.getFriend1_id()),notificationOne.getUser_image(),(notificationOne.getFirst_name()+" "+notificationOne.getLast_name()),notificationOne.getTimestamp(),Integer.valueOf(notificationOne.getRead_flag())));
                    NotificationList.get(i).setType(notificationOne.getType());
                    NotificationList.get(i).setNotificatioId(notificationOne.getId());
                }}
                    recyclerView.setAdapter( new NotificationAdapter(NotificationList));
                }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                Log.d("Fail", t.getMessage());
            }

        });

    }



}
