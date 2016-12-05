package com.bolnizar.datafun;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bolnizar.datafun.facebook.Page;
import com.bolnizar.datafun.pages.MusicPresenter;
import com.bolnizar.datafun.pages.MusicView;
import com.bolnizar.datafun.pages.PageAdapter;
import com.wunderlist.slidinglayer.SlidingLayer;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MusicActivity extends AppCompatActivity implements MusicView {

    @BindView(R.id.music_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.slidingLayer1)
    SlidingLayer mSlidingLayer;

    @BindView(R.id.filter_contains)
    EditText mEditText;
    @BindView(R.id.filter_after_date_text)
    TextView mAfterDateText;
    @BindView(R.id.filter_before_date_text)
    TextView mBeforeDateText;
    @BindView(R.id.filter_spinner)
    Spinner mSpinner;

    private long mAfterDate = 0, mBeforeDate = 0;
    private String mContains = "";

    private final PageAdapter mPageAdapter = new PageAdapter();
    private final MusicPresenter mMusicPresenter = new MusicPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mPageAdapter);
        mMusicPresenter.loadPages();
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.music_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            mSlidingLayer.openLayer(true);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showPages(List<Page> pages) {
        mPageAdapter.setPages(pages);
    }

    void updateFilters() {
        mMusicPresenter.loadPages(mBeforeDate, mAfterDate, mContains, mSpinner.getSelectedItemPosition());
    }

    @OnTextChanged(R.id.filter_contains)
    void textChanged(CharSequence text) {
        mContains = text.toString();
        updateFilters();
    }

    @OnClick(R.id.filter_before_date_clear)
    void clearBefore() {
        mBeforeDate = 0;
        mBeforeDateText.setText(null);
        updateFilters();
    }

    @OnClick(R.id.filter_after_date_clear)
    void clearAfter() {
        mAfterDate = 0;
        mAfterDateText.setText(null);
        updateFilters();
    }

    @OnClick(R.id.filter_before_date_set)
    void setBefore() {
        final Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                mBeforeDate = calendar.getTimeInMillis();
                mBeforeDateText.setText(String.format(Locale.getDefault(), "%d/%d/%d", i, i1, i2));
                updateFilters();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.filter_after_date_set)
    void setAfter() {
        final Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                mAfterDate = calendar.getTimeInMillis();
                mAfterDateText.setText(String.format(Locale.getDefault(), "%d/%d/%d", i, i1, i2));
                updateFilters();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onBackPressed() {
        if (mSlidingLayer.isOpened()) {
            mSlidingLayer.closeLayer(true);
        } else {
            super.onBackPressed();
        }
    }
}
