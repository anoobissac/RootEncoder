package com.pedro.rtmpstreamer.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.pedro.rtmpstreamer.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private final String[] PERMISSIONS = {
      Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button bRtmp = (Button) findViewById(R.id.b_rtmp);
    Button bRtsp = (Button) findViewById(R.id.b_rtsp);
    bRtmp.setOnClickListener(this);
    bRtsp.setOnClickListener(this);
    if (!hasPermissions(this, PERMISSIONS)) {
      ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.b_rtmp:
        if (hasPermissions(this, PERMISSIONS)) {
          startActivity(new Intent(this, RtmpActivity.class));
        } else {
          Toast.makeText(this, "You need permissions before", Toast.LENGTH_SHORT).show();
          ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
        break;
      case R.id.b_rtsp:
        if (hasPermissions(this, PERMISSIONS)) {
          startActivity(new Intent(this, RtspActivity.class));
        } else {
          Toast.makeText(this, "You need permissions before", Toast.LENGTH_SHORT).show();
          ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
        break;
      default:
        break;
    }
  }

  public boolean hasPermissions(Context context, String... permissions) {
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        && context != null
        && permissions != null) {
      for (String permission : permissions) {
        if (ActivityCompat.checkSelfPermission(context, permission)
            != PackageManager.PERMISSION_GRANTED) {
          return false;
        }
      }
    }
    return true;
  }
}
