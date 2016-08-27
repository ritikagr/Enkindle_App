package com.iitism.ritik.enkindle.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iitism.ritik.enkindle.R;

public class AskPopup extends Activity {

    private static final int SELECT_IMAGE_REQUEST_CODE = 1;
    private TextView filePath;
    private Bitmap imageBitmap;
    private ImageView mSelectedImageView;
    private String fileUri;
    private TextView attachFile;

    private EditText question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.7));
        setFinishOnTouchOutside(false);

        question = (EditText) findViewById(R.id.enter_question);
        filePath = (TextView) findViewById(R.id.attach_file_path);
        mSelectedImageView = (ImageView) findViewById(R.id.selected_image);
        attachFile = (TextView) findViewById(R.id.attach_file);

        attachFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Attach_File();
            }
        });
    }

    public void Attach_File()
    {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String pickTitle = "Take or select a photo";
        Intent chooserIntent = Intent.createChooser(pickIntent,pickTitle);

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[] {takePhotoIntent});

        startActivityForResult(chooserIntent,SELECT_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==SELECT_IMAGE_REQUEST_CODE)
        {
            if(resultCode==RESULT_OK && null!=data)
            {
                Uri selectedImage = data.getData();

                if(selectedImage==null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    mSelectedImageView.setImageBitmap(bitmap);
                }
                else {
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                    String picturePath = cursor.getString(columnIndex);
                    filePath.setVisibility(View.VISIBLE);
                    filePath.setText(picturePath);
                    cursor.close();

                    imageBitmap = BitmapFactory.decodeFile(picturePath);
                    mSelectedImageView.setImageBitmap(imageBitmap);
                }
            }
            else
            {

            }
        }
    }

    public void Submit(View view)
    {
        String Question = question.getText().toString();
        //submit the question and attached file to server
        finish();
    }

    public void Cancel(View view)
    {
        finish();
    }
}
