package org.zhx.common.colorful;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

/**
 * @ProjectName: colorfullText
 * @Package: org.zhx.common.colorfull
 * @ClassName: Builder
 * @Description:java类作用描述
 * @Author: zhouxue
 * @CreateDate: 2020/8/15 17:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/15 17:24
 * @UpdateRemark: 更新说明
 * @Version:1.0
 */
public class Builder {

    public Context context;
    /**
     * @Description:字段描述
     * @CreateDate:
     */

    public int pressedColor;
    /**
     * @Description:字段描述
     * @CreateDate: 本地图片
     */

    public int drawableSrc;
    /**
     * @Description:字段描述
     * @CreateDate: 图片位置
     */

    public int drawableIndex;

    /**
     * @Description:字段描述
     * @CreateDate: 构建目标
     */

    public String target;
    /**
     * @Description:字段描述
     * @CreateDate: 构建多个目标
     */

    public String[] targets;
    /**
     * @Description:字段描述
     * @CreateDate: 构建起始位置
     */

    public int start;
    /**
     * @Description:字段描述
     * @CreateDate: 构建 终止位置
     */

    public int end;
    /**
     * @Description:字段描述
     * @CreateDate: 字体颜色
     */

    public int textColor;

    /**
     * @Description:字段描述
     * @CreateDate: 字体大小
     */

    public float textSize;
    /**
     * @Description:字段描述
     * @CreateDate: 字体style
     */

    public int typefaces;

    /**
     * @Description:字段描述
     * @CreateDate: 字体背景
     */

    public int backgroundColor;
    /**
     * @Description:字段描述
     * @CreateDate: 是否中划线
     */

    public boolean isStrikethrough = false;

    /**
     * @Description:字段描述
     * @CreateDate: 是否 下划线
     */

    public boolean isUnderline = false;
    /**
     * @Description:字段描述
     * @CreateDate: 文字替换图片
     */

    public boolean isReplaceTarget = false;
    /**
     * @Description:字段描述
     * @CreateDate: 点击事件
     */

    public TargetClick click;
    /**
     * @Description:字段描述
     * @CreateDate:
     */

    public TextView textView;


    public Builder isUnderline(boolean isUnderline) {
        this.isUnderline = isUnderline;
        return this;
    }

    public Builder(Context context) {
        this.context = context;
    }

    public Builder textColor(@ColorRes int color) {
        this.textColor = context.getResources().getColor(color);
        return this;
    }

    public Builder textSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    public Builder insertDrawableSrc(int place, @DrawableRes int src) {
        this.drawableSrc = src;
        this.drawableIndex = place;
        return this;
    }

    public Builder target(String target) {
        targets(target);
        return this;
    }

    public Builder targets(String... targets) {
        this.targets = targets;
        return this;
    }

    public Builder textStyles(int types) {
        this.typefaces = types;
        return this;
    }

    public Builder isStrikethrough(boolean isStrikethrough) {
        this.isStrikethrough = isStrikethrough;
        return this;
    }

    public Builder backgroundColor(@ColorRes int color) {
        this.backgroundColor = color;
        return this;
    }


    public boolean hasTargets(String source, String[] targets) {
        if (targets == null || targets.length == 0) {
            return false;
        }
        for (String target : targets) {
            return hasTarget(source, target);
        }
        return false;
    }


    public boolean hasTarget(String source, String target) {
        boolean hasSource = !TextUtils.isEmpty(source);
        boolean hasTarget = hasSource && !TextUtils.isEmpty(target) && source.contains(target);
        if (!hasTarget && hasSource) {
            char[] chars = source.toCharArray();
            boolean isformate = !TextUtils.isEmpty(source) && start >= 0 && start < chars.length && end >= 0 && end >= start;
            if (isformate) {
                if (end > chars.length - 1) {
                    end = chars.length - 1;
                }
                StringBuilder builder = new StringBuilder();
                for (int i = start; i <= end; i++) {
                    builder.append(chars[i]);
                }
                target = builder.toString();
            }
            if (isformate) {
                if (end > source.length() - 1) {
                    end = source.length();
                }
            }
            hasTarget = isformate;
        } else if (hasSource) {
            start = source.indexOf(target);
            end = start + target.length();
        }
        return hasDrawable(source) || hasTarget;
    }

    public boolean hasDrawable(String source) {
        boolean hasDrawable = false;
        char[] chars = source.toCharArray();
        if (drawableSrc != 0) {
            hasDrawable = true;
            String[] newchars = new String[source.length() + 1];
            for (int i = 0; i < newchars.length; i++) {
                if (i < drawableIndex) {
                    newchars[i] = chars[i] + "";
                } else if (i >= drawableIndex && i < drawableIndex + 1) {
                    newchars[i] = "!";
                } else {
                    newchars[i] = chars[i - 1] + "";
                }
            }
            if (!isReplaceTarget) {
                source = getNewSource(newchars);
                start = drawableIndex;
                end = start + 1;
            }
        }
        return hasDrawable;
    }

    public String getNewSource(String[] newchars) {
        StringBuilder builder = new StringBuilder();
        for (String str : newchars) {
            builder.append(str);
        }
        return builder.toString();
    }

    public Builder replaceDrawableSrc(int drawable) {
        this.isReplaceTarget = true;
        this.drawableSrc = drawable;
        return this;
    }

    public Builder pressdColor(int pressedColor) {
        this.pressedColor = pressedColor;
        return this;
    }


    public Builder spanClick(TargetClick click) {
        this.click = click;
        return this;
    }

    public void bind(TextView textView) {

    }
}

