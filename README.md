# <center>自定义View之天气软件——风车的绘制</center> #

由于本人使用的华为手机，真是被华为天气的界面圈粉，之前有仿写了其中一个自定义的view，今天带来另一个自定义view，就是其中的风车，先上图


![](http://oc3vooyt7.bkt.clouddn.com/GIF_20160818_203320.gif)

相信大家在其它很多天气软件就看到过。

## 一、概述 ##

在图中，风车叶子和圆点是在一个自定义View中，而风车的杆子则是另一个自定义View，让它们分开是因为风车叶子是要旋转的，要用到RotateAnimation来控制，到时候就只要让风车叶子所在的View旋转就可以了。

其中风车叶子和杆子都是使用贝塞尔曲线来绘制的。如果对贝塞尔曲线还不了解可以看看徐宜生的这篇博客[http://blog.csdn.net/eclipsexys/article/details/51956908](http://blog.csdn.net/eclipsexys/article/details/51956908 "贝塞尔曲线开发的艺术")。

## 二、风车叶子的绘制 ##

先来个徒手绘制的概念图

![](http://i.imgur.com/g25h0zg.png)

可以看到我们主要有五个坐标点，首先把Path移动到（x1，y1）,这里用的是三阶贝塞尔曲线,贝塞尔曲线的两个控制点为(x2,y2),(x3,y3)，这两个点的X坐标是相等的，线段的终点为(x4,y4)；然后再是二阶贝塞尔曲线，控制点只有一个，为(x5,y5)，终点为(x1,y1)；

这里绘制好了一片风车叶，然后我们旋转画布120°，绘制一次，再旋转120°绘制一次，就OK了。

记得把画笔Paint的属性调为mWindPaint.setStyle(Paint.Style.FILL)，填充型。

代码片段为：

	/**
     * 绘制扇叶
     * @param canvas
     */
    private void drawFan(Canvas canvas) {
        Path path = new Path();
        path.moveTo(x1, y1);

        path.cubicTo(x2,y2,x3,y3,x4,y4);
        path.quadTo(x5,y5,x1,y1);
        canvas.drawPath(path, mWindPaint);

        canvas.rotate(120,centerX,centerY);
        canvas.drawPath(path, mWindPaint);

        canvas.rotate(120,centerX,centerY);
        canvas.drawPath(path, mWindPaint);

    }

至于中心圆点就绘制个小圆就行了，重要的是坐标点的控制，代码如下：

	/**
     * 绘制中心圆点
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        canvas.drawCircle(centerX,centerY,getFitSize(20), mWindPaint);
    }

## 三、风车叶子的动画 ##

其实就是让风车叶子所在的自定义View旋转就好了，代码如下：

	mAnimation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
    mAnimation.setRepeatCount(-1); //设置为无限重复
    mAnimation.setInterpolator(new LinearInterpolator());//匀速
    mAnimation.setFillAfter(true);

	public void startAnim() {
       stopAnim();
       mAnimation.setDuration(1800 - mWindVelocity*100);
       Log.e("风速",1800 - mWindVelocity*100+"");
       startAnimation(mAnimation);
    }

    public void stopAnim() {
        clearAnimation();
    }

Animation.RELATIVE_TO_SELF的作用就是相对于自己旋转，然后旋转中心即为自定义view的中心，为了后面和风车杆子拼凑到一起，这个View的宽高必须相等。而控制旋转的速度我们只要控制mAnimation.setDuration()里的时间就行。

## 四、风车杆的绘制 ##

杆子的绘制起始大同小异，也可以直接画一条两条直线都行，这里我为了美观还是用了二阶贝塞尔曲线来绘制底部。示意图：

![](http://i.imgur.com/Hd6zlwC.png) 简单的说一下，我们先移动Path到点a,然后lineTo到点b,lineTo到点c，然后使用二阶贝塞尔曲线，控制点为点d，终点为点e，最后调用path.close()闭合就绘制完成了。代码如下：

	@Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        path.moveTo(width/2+getFitSize(10),width/2+getFitSize(40));
        path.lineTo(width/2-getFitSize(10),width/2+getFitSize(40));
        path.lineTo(width/2-getFitSize(20),height-getFitSize(20));
        path.quadTo(width/2,height,width/2+getFitSize(20),height-getFitSize(20));
        path.close();
        canvas.drawPath(path,mHolderPaint);
        canvas.restore();
    }

## 五、拼凑 ##

将风车叶子和风车杆的两个View一起放置到RelativeLayout中，两个View的宽度一定要相等，不然风车旋转时会不在杆子的正上方。代码如下：

	<RelativeLayout
        android:id="@+id/relativeLayout"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">

        <yyl.beziertest.views.WindPath
            android:id="@+id/id_wind"
            android:layout_width="120dp"
            android:layout_height="120dp"/>

        <yyl.beziertest.views.PillarView
            android:id="@+id/view2"
            android:layout_width="120dp"
            android:layout_height="150dp"/>

        <yyl.beziertest.views.PillarView
            android:id="@+id/view"
            android:layout_width="80dp"
            android:layout_height="100dp"
            android:layout_alignBottom="@+id/view2"
            android:layout_toEndOf="@+id/id_wind"/>

        <yyl.beziertest.views.WindPath
            android:id="@+id/id_windsmall"
            android:layout_width="80dp"
            android:layout_height="100dp"
            android:layout_alignTop="@+id/view"
            android:layout_alignStart="@+id/view"/>

    </RelativeLayout>


此自定义View的博客地址为：[http://blog.csdn.net/yy497078141/article/details/52215622](http://blog.csdn.net/yy497078141/article/details/52215622)

如果喜欢请star！！