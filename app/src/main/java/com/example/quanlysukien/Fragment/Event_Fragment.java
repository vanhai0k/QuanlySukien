package com.example.quanlysukien.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlysukien.API.API;
import com.example.quanlysukien.R;
import com.example.quanlysukien.RealPathUtil;
import com.example.quanlysukien.adapter.EventAdapter;
import com.example.quanlysukien.model.EventChennal;
import com.example.quanlysukien.model.ReceEvent;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.ByteString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Event_Fragment extends Fragment {

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
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            image.setImageBitmap(bitmap);

                        }catch (IOException e){
                            e.fillInStackTrace();
                        }
                    }

                }
            }
    );
    ImageView dateStart,dateEnd,im_timeS,im_timeE;
    Calendar lich=Calendar.getInstance();
    private Calendar calendar = Calendar.getInstance();

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
    String status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_, container, false);

        add_image = view.findViewById(R.id.add_image);
        linkimage = view.findViewById(R.id.linkimage);
        image = view.findViewById(R.id.image);
        add_event = view.findViewById(R.id.add_event);
        startDate = view.findViewById(R.id.startDate);
        endDate = view.findViewById(R.id.endDate);
        endTime = view.findViewById(R.id.endTime);
        startTime = view.findViewById(R.id.startTime);
        nameEvent = view.findViewById(R.id.nameEvent);
        location = view.findViewById(R.id.location);
        decription = view.findViewById(R.id.description);
        dateStart = view.findViewById(R.id.dateStart);
        dateEnd = view.findViewById(R.id.dateEnd);
        im_timeS = view.findViewById(R.id.im_timeS);
        im_timeE = view.findViewById(R.id.im_timeE);
        limit = view.findViewById(R.id.limit);

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonanh();
            }
        });

        im_timeS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime(view);
            }
        });
        im_timeE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTimeE(view);
            }
        });
        add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDataMulter();
            }
        });


        dateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date(view);
            }
        });
        dateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateE(view);
            }
        });


        return view;
    }
    public void date(View view) {
        int nam  = lich.get(Calendar.YEAR);
        int thang  = lich.get(Calendar.MONTH);
        int ngay  = lich.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datepick= new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
        DatePickerDialog datepick= new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                GregorianCalendar c= new GregorianCalendar(year,month,dayOfMonth);
                endDate.setText(sdf.format(c.getTime()));
            }
        },nam,thang,ngay );
        datepick.show();
    }

        // khong lay duoc du lieu
    private void postDataMulter() {
        String name = nameEvent.getText().toString().trim();
        String stime = startTime.getText().toString().trim();
        String etime = endTime.getText().toString().trim();
        String sdate = startDate.getText().toString().trim();
        String edate = endDate.getText().toString().trim();
        String decsription = decription.getText().toString().trim();
        String locations = location.getText().toString().trim();
        String limits = limit.getText().toString().trim();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        String id_user = sharedPreferences.getString("_id",null);
//        String id_user = "658299f18c3eae21ae2f69a4";


        List<EventChennal.ParticipantModel> participantModels = new ArrayList<>();
        String participantModelsJson = new Gson().toJson(participantModels);

        RequestBody requestN = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody requestL = RequestBody.create(MediaType.parse("multipart/form-data"), locations);
        RequestBody requestD = RequestBody.create(MediaType.parse("multipart/form-data"), decsription);

        Date currentDate = new Date();
        String currentStr = sdf.format(currentDate);
        if (currentStr.compareTo(sdate) >=0 && currentStr.compareTo(edate) <0){
             status = "Dang dien ra";
        }else{
             status = "Chua dien ra";
        }
        RequestBody requestSD = RequestBody.create(MediaType.parse("multipart/form-data"), sdate);
        RequestBody requestED = RequestBody.create(MediaType.parse("multipart/form-data"), edate);


        RequestBody requestIDU = RequestBody.create(MediaType.parse("multipart/form-data"), id_user);
        RequestBody requestStatus = RequestBody.create(MediaType.parse("multipart/form-data"), status);
        RequestBody requestST = RequestBody.create(MediaType.parse("multipart/form-data"), stime);
        RequestBody requestET = RequestBody.create(MediaType.parse("multipart/form-data"), etime);
        RequestBody requestPar = RequestBody.create(MediaType.parse("multipart/form-data"), participantModelsJson);
        RequestBody requestlimit = RequestBody.create(MediaType.parse("multipart/form-data"), limits);

        String strReaPart = null;

// Check if mUri is not null and has a scheme
        if (mUri != null && mUri.getScheme() != null) {
            strReaPart = RealPathUtil.getRealPath(getContext(), mUri);
        } else {
            Toast.makeText(getActivity(), " Image URI file not found", Toast.LENGTH_SHORT).show();
            return;
        }
        if (strReaPart == null) {
            Toast.makeText(getActivity(), " getting image path", Toast.LENGTH_SHORT).show();
            return;
        }
        File image = new File(strReaPart);
        if (!image.exists()) {
            Toast.makeText(getActivity(), "Image file not found", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody requestBodyFile = RequestBody.create(MediaType.parse("image*"), image);
        try {
            MultipartBody.Part mPart = MultipartBody.Part.createFormData("image", image.getName(), requestBodyFile);
        if (mPart == null) {
            Toast.makeText(getActivity(), "Error creating MultipartBody", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name.isEmpty()){
            Toast.makeText(getActivity(), "Don't leave it blank Name Event", Toast.LENGTH_SHORT).show();
            return;
        }
        if (stime.isEmpty()){
            Toast.makeText(getActivity(), "Don't leave it blank Start Time", Toast.LENGTH_SHORT).show();
            return;
        }if (etime.isEmpty()){
            Toast.makeText(getActivity(), "Don't leave it blank End Time", Toast.LENGTH_SHORT).show();
            return;
        }if (sdate.isEmpty()){
            Toast.makeText(getActivity(), "Don't leave it blank Start Date", Toast.LENGTH_SHORT).show();
            return;
        }if (edate.isEmpty()){
            Toast.makeText(getActivity(), "Don't leave it blank End Time", Toast.LENGTH_SHORT).show();
            return;
        }if (locations.isEmpty()){
            Toast.makeText(getActivity(), "Don't leave it blank Locations", Toast.LENGTH_SHORT).show();
            return;
        }if (decsription.isEmpty()){
            Toast.makeText(getActivity(), "Don't leave it blank Decsription", Toast.LENGTH_SHORT).show();
            return;
        }

        API.api.postUserMulter(requestN, requestL, requestD, requestST, requestET,
                requestIDU, requestPar, requestSD, requestED, requestStatus, requestlimit ,mPart
        ).enqueue(new Callback<EventChennal>() {
            @Override
            public void onResponse(Call<EventChennal> call, Response<EventChennal> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    nameEvent.setText("");
                    startDate.setText("");
                    endDate.setText("");
                    startTime.setText("");
                    endTime.setText("");
                    location.setText("");
                    decription.setText("");
                }else{
                    Toast.makeText(getActivity(), "loi: "+ response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventChennal> call, Throwable t) {

            }
        });
        } catch (NullPointerException e) {
            Toast.makeText(getActivity(), "Error creating MultipartBody: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void pickTime(View view) {
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                (timePicker, selectedHourOfDay, selectedMinute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, selectedHourOfDay);
                    calendar.set(Calendar.MINUTE, selectedMinute);
                    updateTime();
                },
                hourOfDay,
                minute,
                true
        );
        timePickerDialog.show();
    }
    public void pickTimeE(View view) {
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
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
        if (getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
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