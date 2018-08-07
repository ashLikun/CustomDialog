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


