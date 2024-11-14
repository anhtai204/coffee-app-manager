package com.example.coffeeappmanage.activity.admin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.plugins.RealPathUtil;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.Product_1;
import com.example.coffeeappmanage.model.ResponseSingleProduct;
import com.example.coffeeappmanage.model.ResponseTheLoai;
import com.example.coffeeappmanage.model.TheLoai;
import com.example.coffeeappmanage.model.User;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemDoUongActivity extends AppCompatActivity {

    Toolbar toolbarThemDoUong;
    private User user;
    Spinner spnTheLoai;
    TheLoaiAdapter theLoaiAdapter;
    EditText edtTenDoUong, edtGiaDoUong, edtKhuyenMaiDoUong, edtHinhAnhDoUong, edtMoTaDoUong;
    TextView btnThemDoUong;
    float khuyenmai = 0;
    TheLoai theLoai;
    String path_logo = "C:\\Users\\Admin\\AndroidStudioProjects\\Coffee_App\\CoffeeAppManage\\app\\src\\main\\res\\drawable\\caphe1.jpg";

    public static final String TAG = ThemDoUongActivity.class.getName();
    private static final int MY_REQUEST_CODE = 10;
    TextView btn_select_image;
    private Uri mUri = null;
    ImageView img_from_gallery;

    ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            // Sử dụng ImageDecoder trên Android 29+ (API 29 trở lên)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), uri);
                                Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                                img_from_gallery.setImageBitmap(bitmap);
                            } else {
                                // Sử dụng phương pháp cũ cho các phiên bản thấp hơn
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                img_from_gallery.setImageBitmap(bitmap);
                            }
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_them_do_uong);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Nhận đối tượng User từ Bundle
        if(getIntent().getExtras() != null){
            user = (User) getIntent().getExtras().get("user");

            Log.d("ThemDoUongActivity user: ", user.toString());

        }

        toolbarThemDoUong = findViewById(R.id.toolbarThemDoUong);
        setSupportActionBar(toolbarThemDoUong);

        spnTheLoai = findViewById(R.id.spn_the_loai);
        loadData();
        spnTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ThemDoUongActivity.this, theLoaiAdapter.getItem(i).getTen_the_loai(), Toast.LENGTH_SHORT).show();
//                Log.d("The loai: ", theLoaiAdapter.getItem(i).toString());
                theLoai = theLoaiAdapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edtTenDoUong = findViewById(R.id.edtTenDoUong);
        edtGiaDoUong = findViewById(R.id.edtGiaDoUong);
        edtKhuyenMaiDoUong = findViewById(R.id.edtKhuyenMaiDoUong);
//        edtHinhAnhDoUong = findViewById(R.id.edtHinhAnhDoUong);
        btnThemDoUong = findViewById(R.id.btnThemDoUong);
        edtMoTaDoUong = findViewById(R.id.edtMoTaDoUong);


        img_from_gallery = findViewById(R.id.img_from_gallery);
        btn_select_image = findViewById(R.id.btn_select_image);
        btn_select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });

        btnThemDoUong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTenDoUong.getText().toString().trim().equals("") || 
                    edtGiaDoUong.getText().toString().trim().equals("") ||
                    edtMoTaDoUong.getText().toString().trim().equals("")){
                    Toast.makeText(ThemDoUongActivity.this, "Nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else if(mUri == null) {
                    Toast.makeText(ThemDoUongActivity.this, "Chọn ảnh sản phẩm!", Toast.LENGTH_SHORT).show();
                } else {
                    String moTaDoUong = edtMoTaDoUong.getText().toString().trim();
                    String tenDoUong = edtTenDoUong.getText().toString().trim();
                    float giaDoUong = Float.parseFloat(edtGiaDoUong.getText().toString().trim());
                    if(!edtKhuyenMaiDoUong.getText().toString().equals("")){
                        khuyenmai = Float.parseFloat(edtKhuyenMaiDoUong.getText().toString().trim());
                        if(khuyenmai >= giaDoUong){
                            Toast.makeText(ThemDoUongActivity.this, "Khuyến mại không hợp lệ!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    Log.d("ten san pham: ", tenDoUong);
                    Log.d("gia san pham: ", giaDoUong+"");
                    Log.d("khuyen mai gia: ", khuyenmai+"");
                    Log.d("mo ta: ", moTaDoUong);
                    Log.d("the loai: ", theLoai.toString());


                    Product_1 product = new Product_1(0, tenDoUong, giaDoUong, khuyenmai, path_logo, moTaDoUong,
                            theLoai, null);

                    ApiService.apiService.createProduct(product).enqueue(new Callback<ResponseSingleProduct>() {
                        @Override
                        public void onResponse(Call<ResponseSingleProduct> call, Response<ResponseSingleProduct> response) {
                            ResponseSingleProduct responseProduct1 = response.body();
                            Product_1 newProduct = responseProduct1.getData();
                            int statusCode = responseProduct1.getStatusCode();
                            String message = responseProduct1.getMessage();

                            int id_product = newProduct.getId_product();

                            // Lấy đường dẫn thực từ URI
                            String strRealPath = RealPathUtil.getRealPath(ThemDoUongActivity.this, mUri);
                            File file = new File(strRealPath);

                            // Tạo RequestBody từ file
                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                            // Tạo MultipartBody.Part từ file để gửi đến server
                            MultipartBody.Part avt = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

                            // Tạo RequestBody cho id_product
                            RequestBody idProductRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(id_product));

                            ApiService.apiService.uploadImage(idProductRequestBody, avt).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("newProduct", newProduct);
                                    setResult(Activity.RESULT_OK, resultIntent); // Trả kết quả về Fragment
                                    finish(); // Đóng activity
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable throwable) {

                                }
                            });

                        }

                        @Override
                        public void onFailure(Call<ResponseSingleProduct> call, Throwable throwable) {

                        }
                    });
                }
            }
        });
    }

    private void onClickRequestPermission(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Với Android 13 trở lên, yêu cầu quyền READ_MEDIA_IMAGES thay vì READ_EXTERNAL_STORAGE
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                String[] permissions = {Manifest.permission.READ_MEDIA_IMAGES};
                requestPermissions(permissions, MY_REQUEST_CODE);
            }
        } else {
            // Với các phiên bản thấp hơn, sử dụng READ_EXTERNAL_STORAGE
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, MY_REQUEST_CODE);
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }

    private void loadData(){
        ApiService.apiService.getAllTheLoai().enqueue(new Callback<ResponseTheLoai>() {
            @Override
            public void onResponse(Call<ResponseTheLoai> call, Response<ResponseTheLoai> response) {
                ResponseTheLoai responseTheLoai = response.body();
                List<TheLoai> listTheLoaiApi = responseTheLoai.getData();
                int statusCode = responseTheLoai.getStatusCode();
                String message = responseTheLoai.getMessage();

                theLoaiAdapter = new TheLoaiAdapter(ThemDoUongActivity.this, R.layout.item_the_loai_selected, listTheLoaiApi);
                spnTheLoai.setAdapter(theLoaiAdapter);

            }

            @Override
            public void onFailure(Call<ResponseTheLoai> call, Throwable throwable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.back_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuBack) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}