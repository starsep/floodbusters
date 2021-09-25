package org.example.floodbusters.services;

import android.os.Bundle;

import com.huawei.hms.mlsdk.sounddect.MLSoundDectListener;
import com.huawei.hms.mlsdk.sounddect.MLSoundDector;

public class AlarmDetection {

    MLSoundDector soundDector;

    public AlarmDetection() {
        soundDector = MLSoundDector.createSoundDector();
    }

    public void startDetection() {
        MLSoundDectListener listener = new MLSoundDectListener() {
            @Override
            public void onSoundSuccessResult(Bundle result) {
                // Processing logic when the detection is successful. The detection result ranges from 0 to 12, corresponding to the 13 sound types whose names start with SOUND_EVENT_TYPE defined in MLSoundDectConstants.java.
                int soundType = result.getInt(MLSoundDector.RESULTS_RECOGNIZED);
            }
            @Override
            public void onSoundFailResult(int errCode) {
                // Processing logic for detection failure. The possible cause is that your app is not assigned the microphone permission (Manifest.permission.RECORD_AUDIO).
            }
        };
        soundDector.setSoundDectListener(listener);
    }
}
