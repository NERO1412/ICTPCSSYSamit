package com.samit1.sysamit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Month2Activity extends AppCompatActivity implements View.OnClickListener {

    private CardView mJanuary, mFebruary, mMarch, mApril, mMay,mJun, mJuly, mAugust, mSept, mOct, mNov, mDecember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month2);

        mJanuary = (CardView)findViewById(R.id.january);
        mFebruary = (CardView)findViewById(R.id.february);
        mMarch = (CardView)findViewById(R.id.march);
        mApril = (CardView)findViewById(R.id.april);
        mMay = (CardView)findViewById(R.id.may);
        mJun = (CardView)findViewById(R.id.june);
        mJuly = (CardView)findViewById(R.id.july);
        mAugust = (CardView)findViewById(R.id.august);
        mSept = (CardView)findViewById(R.id.september);
        mOct = (CardView)findViewById(R.id.october);
        mNov = (CardView)findViewById(R.id.november);
        mDecember = (CardView)findViewById(R.id.december);

        mJanuary.setOnClickListener(this);
        mFebruary.setOnClickListener(this);
        mMarch.setOnClickListener(this);
        mApril.setOnClickListener(this);
        mMay.setOnClickListener(this);
        mJun.setOnClickListener(this);
        mJuly.setOnClickListener(this);
        mAugust.setOnClickListener(this);
        mSept.setOnClickListener(this);
        mOct.setOnClickListener(this);
        mNov.setOnClickListener(this);
        mDecember.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.january : i = new Intent(this,januaryActivity.class); startActivity(i); break;
            case R.id.february : i = new Intent(this,febActivity.class); startActivity(i); break;
            case R.id.march : i = new Intent(this,marchActivity.class); startActivity(i); break;
            case R.id.april : i = new Intent(this,aprilActivity.class); startActivity(i); break;
            case R.id.may : i = new Intent(this,mayActivity.class); startActivity(i); break;
            case R.id.june : i = new Intent(this,juneActivity.class); startActivity(i); break;
            case R.id.july : i = new Intent(this,julyActivity.class); startActivity(i); break;
            case R.id.august : i = new Intent(this,augustActivity.class); startActivity(i); break;
            case R.id.september : i = new Intent(this,septActivity.class); startActivity(i); break;
            case R.id.october : i = new Intent(this,octActivity.class); startActivity(i); break;
            case R.id.november : i = new Intent(this,novActivity.class); startActivity(i); break;
            case R.id.december : i = new Intent(this,decActivity.class); startActivity(i); break;

            default: break;
        }
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating menu_main.xml
        getMenuInflater().inflate(R.menu.menu_return, menu);
        //Search View
        MenuItem item = menu.findItem(R.id.action_log);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //handler other menu item clicks here
        if (item.getItemId() == R.id.action_return){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}

