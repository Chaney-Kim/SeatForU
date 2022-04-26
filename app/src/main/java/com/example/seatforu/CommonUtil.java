package com.example.seatforu;

import android.content.Context;
import android.util.TypedValue;

/**
 * 앱 전반에 걸쳐 공통적으로 쓰일 메소드들을 정의한 클래스입니다.
 */
public class CommonUtil {
    public static final int VIBE_SHORT = 4; //ms

    /**
     * @param context : 현재 컨택스를 받습니다.
     * @param dp      : 변환할 dp값을 받습니다.
     * @return : dp를 px 단위에 맞춘 값을 반환합니다.
     */
    public static int dpToPx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
