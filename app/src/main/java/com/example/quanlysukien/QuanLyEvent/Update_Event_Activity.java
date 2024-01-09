package com.example.quanlysukien.QuanLyEvent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.R;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Update_Event_Activity extends AppCompatActivity {

    TextView back;
    TextView add_image,linkimage;
    EditText nameEvent,endTime,startTime,startDate,endDate,location,decription,limit;
    Button add_event;
    ImageView image;
    private static final int MY_REQEST_CODE = 10;
    private static final int PICK_IMAGES_REQUEST_CODE = 2;

    Uri mUri;
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if (data == null){
                            return;
                        }
                        // lay 1 anh
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver(), uri);
                            image.setImageBitmap(bitmap);

                        }catch (IOException e){
                            e.fillInStackTrace();
                        }
                    }

                }
            }
    );
    ImageView dateStart,dateEnd,im_timeS,im_timeE,address;
    Calendar lich=Calendar.getInstance();
    private Calendar calendar = Calendar.getInstance();

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);

        xanhxa();


    }

    private void xanhxa() {
        back = findViewById(R.id.back);
        add_image = findViewById(R.id.add_image);
        linkimage = findViewById(R.id.linkimage);
        image = findViewById(R.id.image);
        add_event = findViewById(R.id.add_event);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        endTime = findViewById(R.id.endTime);
        startTime = findViewById(R.id.startTime);
        nameEvent = findViewById(R.id.nameEvent);
        location = findViewById(R.id.location);
        decription = findViewById(R.id.description);
        dateStart = findViewById(R.id.dateStart);
        dateEnd = findViewById(R.id.dateEnd);
        im_timeS = findViewById(R.id.im_timeS);
        im_timeE = findViewById(R.id.im_timeE);
        limit = findViewById(R.id.limit);
        address = findViewById(R.id.address);

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleMaps();
            }
        });

        Intent intent = getIntent();

        String it_title = intent.getStringExtra("nameEvent");
        String it_limit = intent.getStringExtra("limit");
        String it_timeStart = intent.getStringExtra("timeStart");
        String it_timeEnd = intent.getStringExtra("timeEnd");
        String it_dateStart = intent.getStringExtra("dateStart");
        String it_dateEnd = intent.getStringExtra("dateEnd");
        String it_location = intent.getStringExtra("location");
        String it_description = intent.getStringExtra("description");
        String it_image = intent.getStringExtra("image");

        startTime.setText(it_timeStart);
        endTime.setText(it_timeEnd);
        startDate.setText(it_dateStart);
        endDate.setText(it_dateEnd);
        nameEvent.setText(it_title);
        limit.setText(it_limit);
        decription.setText(it_description);
        location.setText(it_location);


        Glide.with(getBaseContext()).load(URL.image + it_image).into(image);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonanh();
            }
        });

        im_timeS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime(v);
            }
        });
        im_timeE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTimeE(v);
            }
        });
        dateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date(v);
            }
        });
        dateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateE(v);
            }
        });
    }

    private void openGoogleMaps() {
        String savedLocation = "Địa chỉ bạn muốn mở";

        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(savedLocation));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Ứng dụng Google Maps không được cài đặt trên thiết bị của bạn.", Toast.LENGTH_SHORT).show();
        }
    }

    public void date(View view) {
        int nam  = lich.get(Calendar.YEAR);
        int thang  = lich.get(Calendar.MONTH);
        int ngay  = lich.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datepick= new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                GregorianCalendar c= new GregorianCalendar(year,month,dayOfMonth);
                startDate.setText(sdf.format(c.getTime()));
            }
        },nam,thang,ngay );
        datepick.show();

    }
    public void dateE(View view) {
        int nam  = lich.get(Calendar.YEAR);
        int thang  = lich.get(Calendar.MONTH);
        int ngay  = lich.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datepick= new DatePickerDialog(this
                , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                GregorianCalendar c= new GregorianCalendar(year,month,dayOfMonth);
                endDate.setText(sdf.format(c.getTime()));
            }
        },nam,thang,ngay );

        datepick.show();


    }
    public void pickTime(View view) {
        if (!isFinishing() && !isDestroyed()) {
            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    this, // Sử dụng "this" thay vì "getBaseContext()"
                    (timePicker, selectedHourOfDay, selectedMinute) -> {
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHourOfDay);
                        calendar.set(Calendar.MINUTE, selectedMinute);
                        updateTime();
                    },
                    hourOfDay,
                    minute,
                    true
            );

            // Kiểm tra trạng thái trước khi hiển thị Dialog
            if (!isFinishing() && !isDestroyed()) {
                timePickerDialog.show();
            }
        }
    }

    public void pickTimeE(View view) {
        if (view.getWindowToken() != null) {
            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    this,
                    (timePicker, selectedHourOfDay, selectedMinute) -> {
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHourOfDay);
                        calendar.set(Calendar.MINUTE, selectedMinute);
                        updateTimeE();
                    },
                    hourOfDay,
                    minute,
                    true
            );

            timePickerDialog.show();
        }
    }
    private void updateTime() {
        startTime.setText(sdfTime.format(calendar.getTime()));
    }
    private void updateTimeE() {
        endTime.setText(sdfTime.format(calendar.getTime()));
    }

    private void chonanh() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else{
            String [] permisstion = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permisstion, MY_REQEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Image"));
    }
}