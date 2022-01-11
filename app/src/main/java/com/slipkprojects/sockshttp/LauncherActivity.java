package com.slipkprojects.sockshttp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.me.wangyuwei.particleview.ParticleView;

/**
 * 作者： 巴掌 on 16/8/31 15:18
 * Github: https://github.com/JeasonWong
 */
public class LauncherActivity extends AppCompatActivity {

    ParticleView mPvGithub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.app_launch);

        mPvGithub = (ParticleView) findViewById(R.id.pv_github);

        mPvGithub.startAnim();

        mPvGithub.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
				@Override
				public void onAnimationEnd() {
					Intent intent = new Intent(LauncherActivity.this, SocksHttpMainActivity.class);
					LauncherActivity.this.startActivity(intent);
					finish();
				}
			});

    }
}
