package han_meng_fei_sha.huSeekbar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class JieMian extends Activity {
	VolumnView volumnview;
 @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.jiemian);
	
	 volumnview = (VolumnView) findViewById(R.id.volumnview);
	ProgressBar progressbar = (ProgressBar) findViewById(R.id.progressBar1);
	volumnview.setProgressBar(progressbar);
			
			
			
}
}
