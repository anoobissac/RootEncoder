/*
 * Copyright (C) 2021 pedroSG94.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pedro.encoder.utils.gl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;

import java.util.Objects;

/**
 * Created by pedro on 23/09/17.
 */

public class TextStreamObject extends StreamObjectBase {

  private static final String TAG = "TextStreamObject";

  private int numFrames;
  private Bitmap imageBitmap;

  public TextStreamObject() {
  }

  @Override
  public int getWidth() {
    return imageBitmap != null ? imageBitmap.getWidth() : 0;
  }

  @Override
  public int getHeight() {
    return imageBitmap != null ? imageBitmap.getHeight() : 0;
  }

  public void load(String text, float textSize, int textColor, Typeface typeface) {
    numFrames = 1;
    imageBitmap = textAsBitmap(text, textSize, textColor, typeface);
    Log.i(TAG, "finish load text");
  }
  public void load(String text,String text1, String text2,float textSize, int textColor, Typeface typeface,Bitmap bitmap ){
    numFrames=1;
    imageBitmap=textAsBitmap(text,text1,text2, textSize, textColor, typeface,bitmap);
    Log.i(TAG, "finish load text");
  }
  @Override
  public void recycle() {
    if (imageBitmap != null && !imageBitmap.isRecycled()) imageBitmap.recycle();
  }

  private Bitmap textAsBitmap(String text, float textSize, int textColor, Typeface typeface) {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setTextSize(textSize);
    paint.setColor(textColor);
    paint.setAlpha(255);
    if (typeface != null) paint.setTypeface(typeface);
    paint.setTextAlign(Paint.Align.LEFT);

    float baseline = -paint.ascent(); // ascent() is negative
    int width = (int) (paint.measureText(text) + 0.5f); // round
    int height = (int) (baseline + paint.descent() + 0.5f);
    Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(image);
    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

    canvas.drawText(text, 0, baseline, paint);
    return image;
  }
  private Bitmap textAsBitmap(String text,String text1, String text2,float textSize, int textColor, Typeface typeface,Bitmap bitmap ){
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setTextSize(textSize);
    paint.setColor(textColor);
    paint.setAlpha(255);
    if (typeface != null) paint.setTypeface(typeface);
    paint.setTextAlign(Paint.Align.LEFT);
    String[] lines = text.split("\n");
    String[] lines1 = text1.split("\n");
    String[] lines2 = text2.split("\n");
    String space = " ";
    Paint paint1=new Paint();
    paint1.setColor(Color.rgb(39,69,245));
    paint1.setStrokeWidth(2);
    Paint paint2=new Paint();
    paint2.setColor(Color.rgb(245,39,145));
    paint2.setStrokeWidth(2);
    Paint paint3=new Paint();
    paint3.setColor(Color.rgb(46,245,39));
    paint3.setStrokeWidth(2);
    int noOfLines= lines.length;
    float baseline=-paint.ascent();
    int widthMax =0;
    int widthMax1 =0;
    int widthMax2 =0;
    for(int i=0; i<noOfLines; i++){
      int width=(int)(paint.measureText(lines[i]) + 0.5f);
      int width1=(int)(paint.measureText(lines1[i]) + 0.5f);
      int width2=(int)(paint.measureText(lines2[i]) + 0.5f);
      widthMax = Math.max(widthMax, width);
      widthMax1 = Math.max(widthMax1, width1);
      widthMax2 = Math.max(widthMax2, width2);
    }
    int totalWidth = 1280-(widthMax+widthMax1+widthMax2)-20;
    int height=((int)(baseline + paint.descent() + 0.5f))*noOfLines;
    Bitmap image=Bitmap.createBitmap(1280,720,Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(image);
    canvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);
    if(bitmap!=null){
      canvas.drawBitmap(bitmap,1180,0,paint);
    }
    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
      if (!Objects.equals(lines[0], space)){
          canvas.drawRoundRect(0,(720-height),(widthMax+(totalWidth/2)-30),720,16,16,paint1);
      }
      canvas.drawRoundRect((widthMax+(totalWidth/2)-20),(720-height),(widthMax+widthMax1+(totalWidth/2)+20),720,16,16,paint2);
      if (!Objects.equals(lines2[0], space)){
        canvas.drawRoundRect((widthMax+widthMax1+(totalWidth/2)+30),(720-height),1280,720,16,16,paint3);
      }
    }
    for(int i=0; i<noOfLines; i++){
      canvas.drawText(lines[noOfLines-i-1], 20f, 715-( (baseline + paint.descent() + 0.5f) * i), paint);
      canvas.drawText(
              lines1[noOfLines-i-1],
              (widthMax +(totalWidth/2)),
              715-((baseline + paint.descent() + 0.5f) * i),
              paint
      );
      canvas.drawText(
              lines2[noOfLines-i-1],
              (widthMax + widthMax1 + (totalWidth)),
              715-((baseline + paint.descent() + 0.5f) * i),
              paint
      );
    }
    return image;

  }
  @Override
  public int getNumFrames() {
    return numFrames;
  }

  @Override
  public Bitmap[] getBitmaps() {
    return new Bitmap[]{imageBitmap};
  }

  @Override
  public int updateFrame() {
    return 0;
  }
}
