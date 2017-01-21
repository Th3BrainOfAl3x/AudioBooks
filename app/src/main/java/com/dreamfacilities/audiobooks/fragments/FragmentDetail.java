package com.dreamfacilities.audiobooks.fragments;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.dreamfacilities.audiobooks.App;
import com.dreamfacilities.audiobooks.Book;
import com.dreamfacilities.audiobooks.R;

import java.io.IOException;

/**
 * Created by alex on 20/01/17.
 */

public class FragmentDetail extends Fragment implements View.OnTouchListener,
        MediaPlayer.OnPreparedListener,
        MediaController.MediaPlayerControl {
    public static String ARG_ID_BOOK = "id_book";
    MediaPlayer mediaPlayer;
    MediaController mediaController;

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

    private void setInfoBook(int id, View vista) {
        Book book = ((App) getActivity().getApplication())
                .getVectorLibros().elementAt(id);
        ((TextView) vista.findViewById(R.id.title)).setText(book.title);
        ((TextView) vista.findViewById(R.id.autor)).setText(book.autor);
        ((ImageView) vista.findViewById(R.id.cover))
                .setImageResource(book.imageResource);
        vista.setOnTouchListener(this);
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
    }

    public void setInfoBook(int id) {
        setInfoBook(id, getView());
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("Audiolibros", "Entramos en onPrepared de MediaPlayer");
        mediaPlayer.start();
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(getView().findViewById(R.id.fragment_detail));
        mediaController.setPadding(0, 0, 0,110);
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
        mediaPlayer.seekTo(pos);
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
  