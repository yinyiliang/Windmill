package yyl.windmill;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 风车支柱自定义View
 * Created by yinyiliang on 2016/8/15 0015.
 */
public class PillarView extends View {

    private Paint mHolderPaint; //支柱画笔
    private Path path;
    private int width,height;

    public PillarView(Context context) {
        this(context,null);
    }

    public PillarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PillarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        path = new Path();
        mHolderPaint = new Paint();
        mHolderPaint.setStyle(Paint.Style.FILL);
        mHolderPaint.setStrokeWidth(2);
        mHolderPaint.setAntiAlias(true);
        mHolderPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

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

    private float getFitSize(float orgSize) {
        return orgSize * width / 496;
    }
}
