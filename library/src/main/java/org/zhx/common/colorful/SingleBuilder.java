package org.zhx.common.colorful;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * @ProjectName: colorfullText
 * @Package: org.zhx.common.colorfull
 * @ClassName: SingleBuilder
 * @Description:java类作用描述
 * @Author: zhouxue
 * @CreateDate: 2020/8/15 18:00
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/15 18:00
 * @UpdateRemark: 更新说明
 * @Version:1.0
 */
public class SingleBuilder extends Builder {
    /**
     * @Description:字段描述
     * @CreateDate: 需要构建的 文字
     */

    public String source = "";

    public SingleBuilder(Context context) {
        super(context);
    }

    public SingleBuilder source(String source) {
        this.source = source;
        return this;
    }

    private CharSequence build() {
        ColorfulText colorfullText = new ColorfulText();
        colorfullText.init(source);
        if (!TextUtils.isEmpty(source)) {
            if (hasTargets(source, targets)) {
                return colorfullText.build(this);
            }
        }
        return source;
    }

    public void bind(TextView textView) {
        if (textView != null) {
            this.textView = textView;
            textView.setText(build());
        }
    }


}
