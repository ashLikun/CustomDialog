[![Release](https://jitpack.io/v/ashLikun/CustomDialog.svg)](https://jitpack.io/#ashLikun/CustomDialog)

customdialog项目简介
   常用的对话框

## 使用方法

build.gradle文件中添加:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
并且:

```gradle
dependencies {
    implementation 'com.github.ashLikun.CustomDialog:{latest version}'
    implementation 'com.github.ashLikun.frame:numberprogressbar:+'
    implementation 'com.github.ashLikun:CircleProgressView:+'
}
```



### 1.用法
```java
 <!--加载框背景-->
    <color name="dialog_load_back_color">#90ffffff</color>
    <!--加载框标题-->
    <color name="dialog_load_title_color">#ff555555</color>
    <!--加载框圆圈颜色-->
    <color name="dialog_load_circle_color">#ff676767</color>
    <color name="dialog_progress_text_color">#ff555555</color>
    <color name="dialog_select_cancel_text_color">#999999</color>
    <color name="dialog_data_back_color">#ffffffff</color>
    <color name="dialog_data_queding_color">#ff333333</color>
    public void onProgressClick(View view) {
        DialogProgress progress = new DialogProgress(this);
        progress.show();
    }

    public void onTimeClick(View view) {
        DialogDateTime progress = new DialogDateTime(this, DialogDateTime.MODE.DATE_Y_M_D_H_M);
        progress.show();
    }

    public void onSelectMoreClick(View view) {
        DialogSelectMore progress = new DialogSelectMore(this, "11111", "22222222", "333333333");
        progress.show();
    }

    public void onLoadDialogClick(View view) {
        LoadDialog progress = new LoadDialog(this);
        progress.show();
    }

```


