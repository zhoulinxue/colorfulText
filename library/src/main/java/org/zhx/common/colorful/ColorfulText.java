package org.zhx.common.colorful;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.regex.Matcher;

/**
 * @ProjectName: colorfullText
 * @Package: org.zhx.common.colorfull
 * @ClassName: ColorfullText
 * @Description:java类作用描述
 * @Author: zhouxue
 * @CreateDate: 2020/8/13 19:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/13 19:16
 * @UpdateRemark: 更新说明
 * @Version:1.0
 */
public class ColorfulText {
    private String TAG = ColorfulText.class.getSimpleName();
    /**
     * @Description:字段描述
     * @CreateDate:
     */

    public View.OnClickListener onClickListener;
    /**
     * @Description:字段描述
     * @CreateDate: 富文本构建器
     */

    private SpannableStringBuilder mBuilder;

    private int spannable = Spannable.SPAN_INCLUSIVE_INCLUSIVE;

    private Handler mHandler;
    private TextView textView;
    private String source;

    public ColorfulText() {
        mHandler = new Handler();
    }

    public ColorfulText init(String source) {
        this.source = source;
        Log.e(TAG, "init..." + source);
        mBuilder = new SpannableStringBuilder(source);
        return this;
    }

    public ColorfulText creat(Builder... builders) {
        Log.e(TAG, "creat..." + source);
        for (Builder builder : builders) {
            creat(builder,false);
        }
        return this;
    }

    public ColorfulText viewClick(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public void into(TextView textView) {
        if (textView != null) {
            if (onClickListener != null) {
                textView.setOnClickListener(onClickListener);
            }
            textView.setText(mBuilder);
            bindClick(textView);
        }
    }

    /**
     * @param builder
     */
    private CharSequence creat(Builder builder, boolean isPatten) {
        if (builder.hasTargets(source, builder.targets)) {
            for (int i = 0; i < builder.targets.length; i++) {
                String target = builder.targets[i];
                if (builder.hasTarget(source, target))
                    setSpan(builder, target);
            }
        } else if (isPatten) {
            setSpan(builder, "");
        }
        return mBuilder;
    }

    private void setSpan(final Builder builder, final String target) {
        // 设置颜色
        if (builder.textColor != 0) {
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(builder.textColor);
            mBuilder.setSpan(colorSpan, builder.start, builder.end, spannable);
        }
        //设置 字体大小
        if (builder.textSize != 0) {
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(builder.textSize);
            mBuilder.setSpan(sizeSpan, builder.start, builder.end, spannable);
        }
        // 设置背景
        if (builder.backgroundColor != 0) {
            BackgroundColorSpan bgColorSpan = new BackgroundColorSpan(builder.backgroundColor);
            mBuilder.setSpan(bgColorSpan, builder.start, builder.end, spannable);
        }
        // 设置 字体 style
        if (builder.typefaces != 0) {
            StyleSpan styleSpan = new StyleSpan(builder.typefaces);
            mBuilder.setSpan(styleSpan, builder.start, builder.end, spannable);
        }
        // 设置 字体 中划线
        if (builder.isStrikethrough) {
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            mBuilder.setSpan(strikethroughSpan, builder.start, builder.end, spannable);
        }
        // 设置下划线
        if (builder.isUnderline) {
            UnderlineSpan underlineSpan = new UnderlineSpan();
            mBuilder.setSpan(underlineSpan, builder.start, builder.end, spannable);
        }
        //设置图片
        if (builder.hasDrawable()) {
            int start = 0;
            int end = 0;
            int index = TextUtils.isEmpty(target) ? 0 : source.indexOf(target);
            Log.e("source", index + "@@" + target);
            if (builder.isReplaceTarget) {
                builder.drawableIndex = index;
            }
            for (int a = 0; a < builder.drawableSrc.length; a++) {
                int drawable = builder.drawableSrc[a];
                ImageSpan imageSpan = new ImageSpan(builder.context, drawable);
                start = builder.drawableIndex;
                if (builder.isReplaceTarget && a == builder.drawableSrc.length - 1) {
                    end = start + (target.length() - builder.drawableSrc.length) + 1;
                } else {
                    end = builder.drawableIndex + 1;
                }
                builder.drawableIndex++;
                mBuilder.setSpan(imageSpan, start, end, spannable);
            }

        }

        if (builder.isPatten()) {
            Matcher matcher = builder.patten.matcher(mBuilder);
            int end = 0;
            while (matcher.find()) {
                if (builder.find != null) {
                    String findTarget = matcher.group();
                    if (matcher.start() < end) {
                        continue;
                    }
                    int drawable = builder.find.onFind(findTarget);
                    ImageSpan imageSpan = new ImageSpan(builder.context, drawable);
                    end = matcher.start() + findTarget.length();
                    mBuilder.setSpan(imageSpan, matcher.start(), end, spannable);
                }
            }
        }

        if (builder.click != null) {
            SpanPressCallback callback = new SpanPressCallback() {
                @Override
                public void onPress(boolean isPress) {
                    if (onClickListener != null) {
                        if (isPress) {
                            textView.setOnClickListener(null);
                        } else {
                            mHandler.removeCallbacks(null);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setOnClickListener(onClickListener);
                                }
                            }, 300);

                        }
                    }
                }
            };
            TouchableSpan span = new TouchableSpan(builder.textColor, builder.pressedColor, builder.textColor, callback) {
                @Override
                public void onClick(@NonNull View view) {
                    builder.click.onClick(target);
                }
            };
            mBuilder.setSpan(span, builder.start, builder.end, spannable);
            bindClick(builder.textView);
        }


    }

    private void bindClick(TextView textView) {
        if (textView != null) {
            this.textView = textView;
            LinkedMovementMethod linkedMovementMethod = new LinkedMovementMethod();
            textView.setMovementMethod(linkedMovementMethod);
        }
    }


    public CharSequence build(Builder builder, boolean isPatten) {
        return creat(builder, isPatten);
    }
}
