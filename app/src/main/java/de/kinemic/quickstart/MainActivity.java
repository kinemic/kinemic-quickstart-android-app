package de.kinemic.quickstart;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import de.kinemic.gesture.Engine;
import de.kinemic.gesture.Gesture;
import de.kinemic.gesture.OnConnectionStateChangeListener;
import de.kinemic.gesture.OnGestureListener;
import de.kinemic.gesture.SearchCallback;
import de.kinemic.gesture.SearchResult;

public class MainActivity extends AppCompatActivity {

    private Engine mEngine;

    private OnConnectionStateChangeListener mConnectionStateListener = (state, reason) -> {
        // show a message on screen when connection state changes
        Toast.makeText(MainActivity.this, state.toString() + " (" + reason.toString() + ")", Toast.LENGTH_SHORT).show();
    };

    private OnGestureListener mGestureListener = new OnGestureListener() {
        @Override
        public void onGesture(final @NonNull Gesture gesture) {
            // show a message on screen for each gesture event
            Toast.makeText(MainActivity.this, gesture.toString(), Toast.LENGTH_SHORT).show();

            // give haptic feedback for every gesture
            mEngine.vibrate(300);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // coarse location permission is needed to scan for bluetooth devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String permissionToRequest = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) ?
                    Manifest.permission.ACCESS_FINE_LOCATION :
                    Manifest.permission.ACCESS_COARSE_LOCATION;
            if (this.checkSelfPermission(permissionToRequest) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{permissionToRequest}, 1);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // create an engine instance
        mEngine = new Engine(getApplicationContext());
    }


    @Override
    protected void onStop() {
        super.onStop();

        // release resources
        mEngine.release();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register listeners
        mEngine.registerOnConnectionStateChangeListener(mConnectionStateListener);
        mEngine.registerOnGestureListener(mGestureListener);

        // connect to the nearest Kinemic Band
        mEngine.connectStrongest(new SearchCallback() {
            @Override
            public void onBandFound(final @NonNull SearchResult band) {
                float confidence = band.getRssi();
                String currentStrongestBand = band.getMacaddress();

                // you could update your ui here ...
            }

            @Override
            public void onSearchStarted() {

            }

            @Override
            public void onSearchStopped() {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // unregister listeners
        mEngine.unregisterOnConnectionStateChangeListener(mConnectionStateListener);
        mEngine.unregisterOnGestureListener(mGestureListener);

        // disconnect Kinemic Band
        mEngine.disconnect();
    }
}
