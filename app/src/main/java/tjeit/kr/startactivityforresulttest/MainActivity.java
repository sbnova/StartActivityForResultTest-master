package tjeit.kr.startactivityforresulttest;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class MainActivity extends BaseActivity {

    final static int REQUEST_FOR_USER_NAME = 1000;
    final static int REQUEST_FOR_USER_BIRTHDAY = 1001;
    final static int REQUEST_FOR_PICTURE_GALLERY = 1002;
    final static int REQUEST_FOR_PICTURE_CROP = 1003;
    final static int REQUEST_FOR_PICTURE_CAMERA = 1004;

    private android.widget.TextView nameTxt;
    private android.widget.Button nameInputBtn;
    private TextView birthDayTxt;
    private Button birthDayBtn;
    private android.widget.ImageView profileImg;
    private Button cameraBtn;
    private Button gallerBtn;

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
        else if(requestCode == REQUEST_FOR_PICTURE_GALLERY){
            if(resultCode == RESULT_OK){
                Uri selectedImageUri = data.getData();
//                String ImagePath = selectedImageUri.getPath();

                Glide.with(mContext).load(selectedImageUri).into(profileImg);

//                cropImage(selectedImageUri);
            }
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


        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PermissionListener permissionListener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
//                        허가가 난 경우
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_FOR_PICTURE_CAMERA);
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
//                        허가가 안난 경우 -> 권한이 필요하다는 토스트 출력
                        Toast.makeText(mContext, "권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                    }
                };

                TedPermission.with(mContext).setPermissions(Manifest.permission.CAMERA).setPermissionListener(permissionListener).check();

            }
        });

        gallerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_FOR_PICTURE_GALLERY);
            }
        });
    }

    private void cropImage(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);

//        잘라낸 파일의 크기
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);

//        잘라내는 비율
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("return-data", true);

//        startActivityForResult("intent, REQUEST_FOR_PICTURE_CROP");
    }

    @Override
    public void setValues() {

    }

    @Override
    public void bindViews() {

        this.gallerBtn = (Button) findViewById(R.id.gallerBtn);
        this.cameraBtn = (Button) findViewById(R.id.cameraBtn);
        this.profileImg = (ImageView) findViewById(R.id.profileImg);
        this.birthDayBtn = (Button) findViewById(R.id.birthDayBtn);
        this.birthDayTxt = (TextView) findViewById(R.id.birthDayTxt);
        this.nameInputBtn = (Button) findViewById(R.id.nameInputBtn);
        this.nameTxt = (TextView) findViewById(R.id.nameTxt);
    }
}
