package tuananh.com.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DisplayMessageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent= getIntent();
        String message=intent.getStringExtra(MyActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.contennt);
        layout.addView(textView);
    }

}
