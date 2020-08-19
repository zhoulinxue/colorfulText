package org.zhx.common.colorful.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.zhx.common.colorful.Builder;
import org.zhx.common.colorful.ColorfulText;
import org.zhx.common.colorful.Builder;
import org.zhx.common.colorful.OnPatternFind;
import org.zhx.common.colorful.TargetClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //表情替换  和 图片替换 2选1设置 图片替换或者设置 表情替换以后 另一个 功能失效
        enmoj();
        // target
        target();
        //字体大小
        textSize();
        //背景色
        background();
        // 字体 style 粗体  斜体
        textStyle();
        // 删除线
        stickline();
        // 下划线
        underline();
        // 插入图片
        insertDrawable();
        //替换图片
        replaceDrawable();
        // 点击事件
        Spanclick();
        // TextView 点击事件
        textClick();

    }

    private void enmoj() {
        Builder builder = new Builder(this);
        builder.source("发送表情[色][色][色][色][吉他][吉]");
        builder.pattenStr("\\[[^\\]]+\\]", new OnPatternFind() {
            @Override
            public int onFind(String target) {
                Log.i("!!!!", target);
                return "[色]".equals(target) ? R.mipmap.emoji_02 : R.mipmap.emoji_107;
            }
        });
        TextView textView = findViewById(R.id.test_text11);
        builder.bind(textView);
    }

    private void textClick() {
        Builder builder = new Builder(this)
                .targets("《用户注册协议》")// 变色的 文字
                .textColor(R.color.colorPrimary)
                .pressdColor(R.color.colorAccent)
                .spanClick(new TargetClick() {
                    @Override
                    public void onClick(String target) {
                        Toast.makeText(MainActivity.this, target, Toast.LENGTH_SHORT).show();
                    }
                });

        Builder builder1 = new Builder(this)
                .targets("《隐私权政策》")// 变色的 文字
                .textColor(R.color.colorAccent)
                .pressdColor(R.color.colorPrimary)
                .isUnderline(true)
                .spanClick(new TargetClick() {
                    @Override
                    public void onClick(String target) {
                        Toast.makeText(MainActivity.this, target, Toast.LENGTH_SHORT).show();
                    }
                });
        //.......所有builder 都可以叠加
        TextView textView = findViewById(R.id.test_text7);
        ColorfulText text = new ColorfulText();
        text.init("使用即为同意《用户注册协议》和《隐私权政策》");
        text.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "textView", Toast.LENGTH_SHORT).show();
            }
        };
        //...............所有builder 都可以叠加
        text.creat(builder, builder1);
        text.into(textView);
    }


    private void replaceDrawable() {
        Builder builder = new Builder(this)
                .source("颜色大小背景粗细插入图片中划线下划线点击")
                .targets("点击", "图片", "大小")// 变色的 文字
                .replaceDrawableSrc(R.mipmap.emoji_02, R.mipmap.emoji_02, R.mipmap.emoji_02, R.mipmap.emoji_02, R.mipmap.emoji_02, R.mipmap.emoji_02);
        TextView textView = findViewById(R.id.test_text9);
        builder.bind(textView);
    }

    private void insertDrawable() {
        Builder builder = new Builder(this)
                .source("颜色大小背景粗细插入图片中划线下划线点击")
                .insertDrawableSrc(3, R.mipmap.emoji_02, R.mipmap.emoji_02, R.mipmap.emoji_02, R.mipmap.emoji_02, R.mipmap.emoji_02, R.mipmap.emoji_02, R.mipmap.emoji_02, R.mipmap.emoji_107, R.mipmap.emoji_02, R.mipmap.emoji_107);
        TextView textView = findViewById(R.id.test_text8);
        builder.bind(textView);
    }

    private void Spanclick() {
        TextView textView = findViewById(R.id.test_text6);
        Builder builder = new Builder(this)
                .source("颜色大小背景粗细插入图片中划线下划线点击")
                .targets("点击", "颜色")// 变色的 文字
                .textColor(R.color.colorPrimary)
                .pressdColor(R.color.colorAccent)
                .spanClick(new TargetClick() {
                    @Override
                    public void onClick(String target) {
                        Toast.makeText(MainActivity.this, target, Toast.LENGTH_SHORT).show();
                    }
                });

        builder.bind(textView);
    }

    private void stickline() {
        Builder builder = new Builder(this)
                .source("颜色大小背景粗细插入图片中划线下划线点击")
                .target("中划线")// 变色的 文字
                .isStrikethrough(true);
        TextView textView = findViewById(R.id.test_text5);
        builder.bind(textView);
    }

    private void textStyle() {
        Builder builder = new Builder(this)
                .source("颜色大小背景粗细插入图片中划线下划线点击")
                .target("粗细")// 变色的 文字
                .textStyles(Typeface.BOLD);// 0 正常 1 粗体 2 斜体 3 粗斜体
        TextView textView = findViewById(R.id.test_text4);
        builder.bind(textView);
    }

    private void textSize() {
        Builder builder = new Builder(this)
                .source("颜色大小背景粗细插入图片中划线下划线点击")
                .target("大小")// 变色的 文字
                .textSize(1.5f);// 1.5 倍
        TextView textView = findViewById(R.id.test_text3);
        builder.bind(textView);
    }

    private void background() {
        Builder builder = new Builder(this)
                .source("颜色大小背景粗细插入图片中划线下划线点击")
                .targets("颜色", "背景")// 变色的 文字
                .textColor(R.color.white)
                .backgroundColor(R.color.colorAccent);
        TextView textView = findViewById(R.id.test_text2);
        builder.bind(textView);
    }

    private void target() {
        Builder builder = new Builder(this)
                .source("颜色大小背景粗细插入图片中划线下划线点击")
                .target("颜色")// 变色的 文字
                .textColor(R.color.colorAccent);
        TextView textView = findViewById(R.id.test_text1);
        builder.bind(textView);
    }

    private void underline() {
        Builder builder = new Builder(this)
                .source("颜色大小背景粗细插入图片中划线下划线点击")
                .target("下划线")// 变色的 文字
                .isUnderline(true)
                .textColor(R.color.colorAccent);
        TextView textView = findViewById(R.id.test_text);
        builder.bind(textView);
    }

}