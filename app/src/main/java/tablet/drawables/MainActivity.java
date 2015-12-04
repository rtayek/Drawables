package tablet.drawables;
import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.graphics.drawable.shapes.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.*;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import static java.lang.Math.*;
public class MainActivity extends AppCompatActivity {
    public class GradientView extends View {
        public GradientView(Context context) {
            super(context);
        }
        public GradientView(Context context,AttributeSet attrs,int defStyleAttr) {
            super(context,attrs,defStyleAttr);
        }
        public GradientView(Context context,AttributeSet attrs) {
            super(context,attrs);
        }
        public void setGradientColors(int bottomColor,int topColor) {
            GradientDrawable gradient=new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,new int[]{bottomColor,topColor});
            gradient.setShape(GradientDrawable.RECTANGLE);
            gradient.setCornerRadius(10.f);
            this.setBackgroundDrawable(gradient);
        }
    }
    DrawableView drawableView;
    LinearLayout row(boolean isRow1) {
        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(w,d);
        int m=(int)round(w*margin);
        layoutParams.setMargins(m,m,m,m);
        for(int i=0;i<n;i++)
            layout.addView(drawableView=new DrawableView(this,i,isRow1),layoutParams);
        return layout;
    }
    public static GradientDrawable setGradientColors(Context context,int bottomColor,int topColor) {
        GradientDrawable gradient=new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,new int[]{bottomColor,topColor});
        gradient.setShape(GradientDrawable.RECTANGLE);
        //gradient.setCornerRadius(10.f);
        return gradient;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        DisplayMetrics metrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        System.out.println("density="+metrics.densityDpi);
        w=d=(int)round(metrics.densityDpi);
        LinearLayout row1=row(true);
        LinearLayout row2=row(false);
        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(row1);
        layout.addView(row2);
        LinearLayout l=new LinearLayout(this);
        //GradientView gradientView=new GradientView(this);
        //gradientView.setGradientColors(0xffff0000,0xff0000ff);
        //l.addView(gradientView);
        //layout.addView(l);
        //GradientDrawable gd = new GradientDrawable(
        //GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xffff0000, 0xff0000ff});
        //gd.setCornerRadius(0f);
        setContentView(layout);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public class DrawableView extends Button {
        public DrawableView(Context context,int column,boolean isRow1) {
            super(context);
            setBackgroundColor(Color.TRANSPARENT);
            this.column=column;
            text=""+(char)('0'+column);
            int r=(int)round(w*radius);
            d0=new ShapeDrawable(new RoundRectShape(new float[]{r,r,r,r,r,r,r,r},null,null));
            d0.getPaint().setColor(0xff000000);
            d0.setBounds(0,0,w,d);
            d1=new ShapeDrawable(new RoundRectShape(new float[]{r,r,r,r,r,r,r,r},null,null));
            d1.getPaint().setColor(on[column]);
            d1.setBounds(edge,edge,w-edge,d-edge);
            d2=new ShapeDrawable(new RoundRectShape(new float[]{r,r,r,r,r,r,r,r},null,null));
            int b=(int)round(w*border);
            d2.setBounds(b/2,b/2,w-b/2,d-b/2);
            d2.getPaint().setColor(isRow1?on[column]:off[column]);
        }
        protected void onDraw(Canvas canvas) {
            d0.draw(canvas);
            d1.draw(canvas);
            d2.draw(canvas);
            Paint paint = new Paint();
            //paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            //canvas.drawPaint(paint);
            paint.setColor(Color.CYAN);
            paint.setTextSize(w*95/100);
            Rect r=new Rect();
            canvas.drawRect(r,paint);
            paint.getTextBounds(text,0,1,r);
            int x=(w-r.width())/2,y=(d-r.height())/2;
            paint.setColor(Color.BLACK);
            canvas.drawText(text,x,d-y,paint);
        }
        final int column;
        final String text;
        ShapeDrawable d0, d1, d2;
    }
    final int n=5, edge=1;
    double margin=.10, radius=.05, border=.15;
    int w, d;
    final int[] on=new int[]{0xffff0000,0xffffff00,0xff00ff00,0xff0000ff,0xffff8000};
    final int[] off=new int[]{0xff800000,0xff808000,0xff008000,0xff000080,0xff804000};
}
