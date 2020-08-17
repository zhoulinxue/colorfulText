package org.zhx.common.colorfull;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * @ProjectName: colorfullText
 * @Package: org.zhx.common.colorfull
 * @ClassName: TouchableSpan
 * @Description:java类作用描述
 * @Author: zhouxue
 * @CreateDate: 2020/8/15 14:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/15 14:38
 * @UpdateRemark: 更新说明
 * @Version:1.0
 */
public abstract class TouchableSpan extends ClickableSpan {
    private boolean mIsPressed;
    private int mPressedBackgroundColor;
    private int mNormalTextColor;
    private int mPressedTextColor;
    private SpanPressCallback mCallback;

    public TouchableSpan(int normalTextColor, int pressedTextColor, int pressedBackgroundColor, SpanPressCallback callback) {
        mNormalTextColor = normalTextColor;
        mPressedTextColor = pressedTextColor;
        mPressedBackgroundColor = pressedBackgroundColor;
        this.mCallback = callback;
    }

    public void setPressed(boolean isSelected) {
        mIsPressed = isSelected;
        if (mCallback != null) {
            mCallback.onPress(isSelected);
        }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(mIsPressed ? mPressedTextColor : mNormalTextColor);
        ds.bgColor = mIsPressed ? mPressedBackgroundColor : Color.TRANSPARENT;
    }
}
