package yyl.windmill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private WindPath mBigWindMill,mSmallWindMill;

    private SeekBar mSeekBar;
    private TextView mSpeedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBigWindMill = (WindPath) findViewById(R.id.id_wind);
        mSmallWindMill = (WindPath) findViewById(R.id.id_windsmall);
        mSeekBar = (SeekBar) findViewById(R.id.id_seekbar);
        mSpeedText = (TextView) findViewById(R.id.speed);
        mSpeedText.setText(mSeekBar.getProgress() + "/" + mSeekBar.getMax());
        mBigWindMill.startAnim();
        mSmallWindMill.startAnim();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mSpeedText.setText(seekBar.getProgress() + "/" + mSeekBar.getMax());

                mBigWindMill.setWindvelocity(seekBar.getProgress());
                mSmallWindMill.setWindvelocity(seekBar.getProgress());
                mBigWindMill.startAnim();
                mSmallWindMill.startAnim();
            }
        });
    }
}
