package com.dreamfacilities.audiobooks.fragments;

import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.dreamfacilities.audiobooks.App;
import com.dreamfacilities.audiobooks.Book;
import com.dreamfacilities.audiobooks.MainActivity;
import com.dreamfacilities.audiobooks.R;
import com.dreamfacilities.audiobooks.ZoomSeekBar;
import com.dreamfacilities.audiobooks.interfaces.ValorListener;

import java.io.IOException;

/**
 * Created by alex on 20/01/17.
 */

public class FragmentDetail extends Fragment implements View.OnTouchListener,
        MediaPlayer.OnPreparedListener,
        MediaController.MediaPlayerControl,
        ValorListener {

    public static String ARG_ID_BOOK = "id_book";
    MediaPlayer mediaPlayer;
    MediaController mediaController;

    private static final int ID_NOTIFICACION = 1;
    private NotificationManager notificManager;
    private NotificationCompat.Builder notificacion;
    private RemoteViews remoteViews;

    ZoomSeekBar zoomSeekBar;

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View view = inflador.inflate(R.layout.fragment_detail, contenedor, false);
        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt(ARG_ID_BOOK);
            setInfoBook(position, view);
        } else {
            setInfoBook(0, view);
        }
        return view;
    }

    private void setInfoBook(int id, View view) {

        Book book = ((App) this.getActivity().getApplication()).getVectorBooks().elementAt(id);

        ((TextView) view.findViewById(R.id.title)).setText(book.title);
        ((TextView) view.findViewById(R.id.autor)).setText(book.autor);

        zoomSeekBar = (ZoomSeekBar) view.findViewById(R.id.seekcontrol);
        zoomSeekBar.setOnValorListener(this);

        App app = (App) getActivity().getApplication();
        ((NetworkImageView) view.findViewById(R.id.cover)).setImageUrl(book.urlImage, app.getImageLoader());

        view.setOnTouchListener(this);

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaController = new MediaController(getActivity());

        Uri audio = Uri.parse(book.urlAudio);

        try {
            mediaPlayer.setDataSource(getActivity(), audio);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("AudioBooks", "ERROR: The audiobook: " + audio + " can't be reproduced", e);
        }

        Log.i("AudioBooks", String.valueOf(book.imageResource));

        remoteViews = new RemoteViews(getActivity().getPackageName(), R.layout.notification);
        remoteViews.setImageViewResource(R.id.notification_play, android.R.drawable.ic_media_play);
        remoteViews.setImageViewResource(R.id.notification_image, book.imageResource);
        remoteViews.setTextViewText(R.id.notification_title, book.title);
        remoteViews.setTextColor(R.id.notification_title, Color.BLACK);
        remoteViews.setTextViewText(R.id.notification_autor, book.autor);
        remoteViews.setTextColor(R.id.notification_autor, Color.BLACK);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        notificacion = (NotificationCompat.Builder) new NotificationCompat.Builder(getActivity())
                                                                            .setContent(remoteViews)
                                                                            .setPriority(Notification.PRIORITY_MAX)
                                                                            .setSmallIcon(R.mipmap.ic_launcher)
                                                                            .setContentTitle("AudioBook Playing")
                                                                            .setContentIntent(pendingIntent);
        notificManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificManager.notify(ID_NOTIFICACION, notificacion.build());

    }

    public void setInfoBook(int id) {
        setInfoBook(id, getView());
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("AUDIOBOOKS", "OnPrepared from MediaPLayer");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        zoomSeekBar.setValMin(0);
        zoomSeekBar.setEscalaMin(0);
        zoomSeekBar.setValMax(Math.round(mediaPlayer.getDuration() / 1000));
        zoomSeekBar.setEscalaMax(Math.round(mediaPlayer.getDuration() / 1000));

        if (preferences.getBoolean("pref_autoplay", true)) {
            mediaPlayer.start();
        }
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(getView().findViewById(R.id.fragment_detail));
        mediaController.setPadding(0, 0, 0, 110);
        mediaController.setEnabled(true);
        mediaController.show();


    }

    @Override
    public boolean onTouch(View vista, MotionEvent evento) {
        mediaController.show();
        return false;
    }

    @Override
    public void onStop() {
        mediaController.hide();
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        } catch (Exception e) {
            Log.d("Audiolibros", "Error in mediaPlayer.stop()");
        }

        super.onStop();
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        try {
            return mediaPlayer.getCurrentPosition();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int getDuration() {

        return mediaPlayer.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.pause();
        mediaPlayer.seekTo(pos);
        mediaPlayer.start();
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onResume() {
        FragmentDetail fragmentDetail = (FragmentDetail) getFragmentManager().findFragmentById(R.id.fragment_detail);
        if (fragmentDetail == null) {
            MainActivity activity = (MainActivity) getActivity();
            activity.showElements(false);
        }
        super.onResume();
    }


    public void onValorChanged(int valor) {
        Log.d("AUDIOBOOKS", "onValorChanged" + valor);

        Log.d("AUDIOBOOKS", "getCurrentPosition" + getCurrentPosition());
        seekTo(valor * 1000);
    }

    ;
}

