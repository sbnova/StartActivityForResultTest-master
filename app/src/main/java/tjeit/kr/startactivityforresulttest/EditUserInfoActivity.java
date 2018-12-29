package tjeit.kr.startactivityforresulttest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditUserInfoActivity extends BaseActivity {

    private android.widget.EditText nameEdt;
    private android.widget.Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("이름", nameEdt.getText().toString());

                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }

    @Override
    public void setValues() {

    }

    @Override
    public void bindViews() {

        this.okBtn = (Button) findViewById(R.id.okBtn);
        this.nameEdt = (EditText) findViewById(R.id.nameEdt);
    }
}
