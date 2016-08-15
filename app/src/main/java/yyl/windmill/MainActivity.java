package yyl.windmill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private WindPath mBigWindMill,mSmallWindMill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBigWindMill = (WindPath) findViewById(R.id.id_wind);
        mSmallWindMill = (WindPath) findViewById(R.id.id_windsmall);
        initWind();
    }
    private void initWind() {
        mBigWindMill.setWindvelocity(3);
        mSmallWindMill.setWindvelocity(3);
        mBigWindMill.startAnim();
        mSmallWindMill.startAnim();
    }
}
