package tjeit.kr.startactivityforresulttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    final static int REQUEST_FOR_USER_NAME = 1000;
    final static int REQUEST_FOR_USER_BIRTHDAY = 1001;

    private android.widget.TextView nameTxt;
    private android.widget.Button nameInputBtn;
    private TextView birthDayTxt;
    private Button birthDayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FOR_USER_NAME) {
//            사용자 이름을 입력받아온 경우.

            if (resultCode == RESULT_OK) {
                String userName = data.getStringExtra("이름");
                nameTxt.setText(userName);
            }

            Toast.makeText(mContext, "사용자이름", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == REQUEST_FOR_USER_BIRTHDAY) {
            Toast.makeText(mContext, "생년월일", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void setupEvents() {

        nameInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, EditUserInfoActivity.class);
//                startActivity(intent);
//                입력 결과를 받기 위해 다른 메쏘드로 intent 실행
                startActivityForResult(intent, REQUEST_FOR_USER_NAME);
            }
        });

        birthDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, BirthDayInputActivity.class);
                startActivityForResult(intent, REQUEST_FOR_USER_BIRTHDAY);
            }
        });

    }

    @Override
    public void setValues() {

    }

    @Override
    public void bindViews() {

        this.birthDayBtn = (Button) findViewById(R.id.birthDayBtn);
        this.birthDayTxt = (TextView) findViewById(R.id.birthDayTxt);
        this.nameInputBtn = (Button) findViewById(R.id.nameInputBtn);
        this.nameTxt = (TextView) findViewById(R.id.nameTxt);
    }
}
