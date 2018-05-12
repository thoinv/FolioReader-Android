/*
 * Copyright (C) 2016 Pedro Paulo de Amorim
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
package com.bookforyou.tuoitredanggiabaonhieu;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bookforyou.tuoitredanggiabaonhieu.utils.Constants;
import com.bookforyou.tuoitredanggiabaonhieu.utils.PrefManager;
import com.fasterxml.jackson.databind.ObjectReader;
import com.folioreader.FolioReader;
import com.folioreader.model.HighLight;
import com.folioreader.model.ReadPosition;
import com.folioreader.model.ReadPositionImpl;
import com.folioreader.util.ObjectMapperSingleton;
import com.folioreader.util.OnHighlightListener;
import com.folioreader.util.ReadPositionListener;

import java.io.IOException;

public class HomeActivity extends Activity
        implements OnHighlightListener, ReadPositionListener {

    private static final String LOG_TAG = HomeActivity.class.getSimpleName();
    private FolioReader folioReader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.folioReader = FolioReader.getInstance(getApplicationContext())
                .setOnHighlightListener(this)
                .setReadPositionListener(this);

        this.getLastReadPosition();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openBook();
            }
        }, 4000);
    }

    private void openBook() {
        this.folioReader.openBook("file:///android_asset/book.epub");
        finish();
    }

    private void getLastReadPosition() {

        ObjectReader objectReader = ObjectMapperSingleton.getObjectMapper().reader();
        ReadPosition readPosition = null;

        try {
            String readPositionJsonData = PrefManager.getString(Constants.K_READ_POSITION, "");
            if (TextUtils.isEmpty(readPositionJsonData)) {
                return;
            }
            readPosition = objectReader.forType(ReadPositionImpl.class)
                    .readValue(readPositionJsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.folioReader.setReadPosition(readPosition);
    }

    @Override
    public void saveReadPosition(ReadPosition readPosition) {
        Log.i(LOG_TAG, "-> ReadPosition = " + readPosition.toJson());
        PrefManager.putString(Constants.K_READ_POSITION, readPosition.toJson());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FolioReader.clear();
    }

    @Override
    public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {
        Log.i(LOG_TAG,"highlight id = " + highlight.getUUID() + " type = " + type);
    }
}