package com.example.seatforu;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.seatforu", appContext.getPackageName());
    }

    /**
     * firebase 연동 테스트
     * 테스트값을 db에 저장한 직후 그 값을 읽어들여
     * 기대값과 일치하면 pass, 일치하지 않으면 fail
     */
    @Test
    public void databaseTest() throws InterruptedException {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // path값이 없으면 최상단에 데이터 생성
        // 없던 path 이름을 입력하면 자동으로 경로가 생성됨
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("hello, World");
        Thread.sleep(5000);

        /*
         * 테스트시 실제 db 상에 데이터 변경이 반영되지 않는 것 같다.
         * 디버거로 한 줄씩 실행시키면 setValue 시점에서 값이 파베에 생성(저장)된다.
         * sleep 으로 지연시간 거니까 제대로 파이어베이스에 저장이 되는 것을 보니
         * 실제 웹에 접속하기 전에 프로세스가 끝나서 저장을 하지 못하는 것 같다.
         *
         * 사건의 전말
         * 테스트 실행 -> 우선 데이터를 로컬 캐시에 저장 -> 그 데이터로 테스트 통과
         * -> 웹에 접속되기전 작업목록은 로컬 큐에 유지됨 -> 큐를 온라인에 반영하기 전에 프로세스가 종료됨
         * -> 실제 파베서비스에 반영 안됨 -> 이런 문제 때문에 mock 객체를 사용하는 것이 여러모로 좋음
         * 힌트의 출처 : https://firebase.google.com/docs/database/android/offline-capabilities?hl=ko
         * 소요시간 - 약 2시간
         */
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Log.d(TAG, "onDataChange: " + value);
                assertEquals("hello, World", value);
//                myRef.removeValue(); // 데이터 삭제하는 것도 변화로 간주되니까 한번 더 리스너가 실행되어 null 에러가 난다.
                // 입력 완료 콜백을 따로 불러야 함
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onCancelled: Fail to read vaule", error.toException());
            }
        });

    }
}