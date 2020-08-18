# colorfulText
lib for spannableString
[ ![Download](https://api.bintray.com/packages/zhx/common/colorfulText/images/download.svg?version=1.0.0) ](https://bintray.com/zhx/common/colorfulText/1.0.0/link)

## 能干什么？
 1、 封装 spannableString 的功能 实现 文字部分字体 、换色 、背景 、style、下划线、删除线、替换图片、插入图片
 
 2、封装、分离 TextView 部分字体内容点击事件及TextView 点击事件
 ## 集成
 ```
 allprojects {
     repositories {
         jcenter()
     }
 }
 ```
 ```
 dependencies {
     implementation 'org.zhx.common:colorfulText:1.0.0'
  }
 ```
### 非Androidx 项目 ：
  gradle.properties中 添加：
 ```
 android.useAndroidX=true
 android.enableJetifier=true
 ```
 ###  单一 属性 修改
 ```
 Builder builder = new Builder(this)
                .source("颜色大小背景粗细插入图片中划线下划线点击")
                .target("颜色")// 变色的 文字
                .textColor(R.color.colorAccent);
        TextView textView = findViewById(R.id.test_text1);
        builder.bind(textView);
```

 ###  多属性 修改
 ```
 Builder builder = new Builder(this)
                .targets("点击")// 变色的 文字
                .textColor(R.color.colorPrimary)
                .pressdColor(R.color.colorAccent)
                .spanClick(new TargetClick() {
                    @Override
                    public void onClick(String target) {
                        Toast.makeText(MainActivity.this, target, Toast.LENGTH_SHORT).show();
                    }
                });

        Builder builder1 = new Builder(this)
                .targets("颜色")// 变色的 文字
                .textColor(R.color.colorAccent)
                .pressdColor(R.color.colorPrimary)
                .spanClick(new TargetClick() {
                    @Override
                    public void onClick(String target) {
                        Toast.makeText(MainActivity.this, target, Toast.LENGTH_SHORT).show();
                    }
                });
        //.......所有builder 都可以叠加
        TextView textView = findViewById(R.id.test_text7);
        ColorfulText text = new ColorfulText();
        text.init("颜色大小背景粗细插入图片中划线下划线点击");
        text.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "textView", Toast.LENGTH_SHORT).show();
            }
        };
        //...............所有builder 都可以叠加
        text.creat(builder, builder1);
        text.into(textView);
```

### 多关键字 修改
```
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
```


