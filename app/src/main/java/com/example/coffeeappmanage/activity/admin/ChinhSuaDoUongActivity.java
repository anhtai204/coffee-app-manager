package com.example.coffeeappmanage.activity.admin;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.plugins.RealPathUtil;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.ResponseSingleProduct;
import com.example.coffeeappmanage.model.ResponseTheLoai;
import com.example.coffeeappmanage.model.TheLoai;
import com.example.coffeeappmanage.model.User;

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

public class ChinhSuaDoUongActivity extends AppCompatActivity {

    User user;
    Product product;
    Toolbar toolbar;
    TheLoaiAdapter theLoaiAdapter;
    EditText edtChinhSuaTen, edtChinhSuaMoTa, edtChinhSuaGia, edtChinhSuaKhuyenMai, edtChinhSuaHinhAnh;
    TextView btnCapNhatDoUong;
    Spinner spn_chinh_sua_do_uong;
    TheLoai theLoai;


    TextView btn_select_picture;
    public static final String TAG = ChinhSuaDoUongActivity.class.getName();
    private static final int MY_REQUEST_CODE = 10;
    private Uri mUri;
    ImageView img_from_api;

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
                                img_from_api.setImageBitmap(bitmap);
                            } else {
                                // Sử dụng phương pháp cũ cho các phiên bản thấp hơn
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                img_from_api.setImageBitmap(bitmap);
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
        setContentView(R.layout.activity_chinh_sua_do_uong);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().get("user");
            product = (Product) getIntent().getExtras().get("product");
        }

        img_from_api = findViewById(R.id.img_from_api);
        Glide.with(this)
                .load(getString(R.string.local_host) + product.getLogo_product())
                .fitCenter()  // Hoặc sử dụng .centerInside() nếu bạn muốn ảnh nhỏ hơn vừa khít với ImageView
                .into(img_from_api);

        mUri = Uri.parse(getString(R.string.local_host) + product.getLogo_product());

        Log.d("product: ", product.toString());
        Log.d("user: ", user.toString());

        toolbar = findViewById(R.id.toolbarSuaDoUong);
        setSupportActionBar(toolbar);

        edtChinhSuaTen = findViewById(R.id.edtChinhSuaTen);
        edtChinhSuaMoTa = findViewById(R.id.edtChinhSuaMoTa);
        edtChinhSuaGia = findViewById(R.id.edtChinhSuaGia);
        edtChinhSuaKhuyenMai = findViewById(R.id.edtChinhSuaKhuyenMai);
//        edtChinhSuaHinhAnh = findViewById(R.id.edtChinhSuaHinhAnh);
        btnCapNhatDoUong = findViewById(R.id.btnCapNhatDoUong);
        spn_chinh_sua_do_uong = findViewById(R.id.spn_chinh_sua_do_uong);

        edtChinhSuaTen.setText(product.getTenSanPham());
        edtChinhSuaMoTa.setText(product.getMo_ta());
        edtChinhSuaGia.setText(product.getGiaSanPham()+"");
        edtChinhSuaKhuyenMai.setText(product.getKhuyenmai_gia()+"");
//        edtChinhSuaHinhAnh.setText(product.getLogo_product());


        loadData();

        btn_select_picture = findViewById(R.id.btn_select_picture);
        btn_select_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });

        spn_chinh_sua_do_uong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ChinhSuaDoUongActivity.this, theLoaiAdapter.getItem(i).getTen_the_loai(), Toast.LENGTH_SHORT).show();
                Log.d("The loai: ", theLoaiAdapter.getItem(i).toString());
                theLoai = theLoaiAdapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnCapNhatDoUong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_product = product.getId_product();
                String tenSanPham = edtChinhSuaTen.getText().toString().trim();
                String moTa = edtChinhSuaMoTa.getText().toString().trim();
                float gia = Float.parseFloat(edtChinhSuaGia.getText().toString().trim());
                float khuyenmai_gia = Float.parseFloat(edtChinhSuaKhuyenMai.getText().toString().trim());
//                String logo_path = edtChinhSuaHinhAnh.getText().toString().trim();

                Product new_product = new Product(id_product, tenSanPham, gia, khuyenmai_gia, theLoai, product.getKhuyenMai(), null, 0, moTa, null);

                Log.d("new product: ", new_product.toString());

                AlertDialog.Builder builder = new AlertDialog.Builder(ChinhSuaDoUongActivity.this);
                builder.setTitle("Update");
                builder.setMessage("Bạn có chắc muốn thay đổi sản phẩm!");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiService.apiService.updateProduct(id_product, new_product).enqueue(new Callback<ResponseSingleProduct>() {
                            @Override
                            public void onResponse(Call<ResponseSingleProduct> call, Response<ResponseSingleProduct> response) {
                                if (response.isSuccessful()) {
                                    // Lấy đường dẫn thực từ URI
                                    String strRealPath = RealPathUtil.getRealPath(ChinhSuaDoUongActivity.this, mUri);
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
                                            resultIntent.putExtra("updatedProduct", new_product);
                                            setResult(Activity.RESULT_OK, resultIntent);
                                            finish(); // Đóng activity và quay lại fragment
                                            Toast.makeText(ChinhSuaDoUongActivity.this, "Update product successed!", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable throwable) {

                                        }
                                    });


//                                    Intent resultIntent = new Intent();
//                                    resultIntent.putExtra("updatedProduct", new_product);
//                                    setResult(Activity.RESULT_OK, resultIntent);
//                                    finish(); // Đóng activity và quay lại fragment
//                                    Toast.makeText(ChinhSuaDoUongActivity.this, "Update product successed!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseSingleProduct> call, Throwable throwable) {
                                Toast.makeText(ChinhSuaDoUongActivity.this, "update product failed", Toast.LENGTH_SHORT).show();
                                Log.d("error update: ", throwable.toString());
                            }
                        });
                    }
                });
                builder.create().show();
            }
        });
    }


    private void loadData(){
        ApiService.apiService.getAllTheLoai().enqueue(new Callback<ResponseTheLoai>() {
            @Override
            public void onResponse(Call<ResponseTheLoai> call, Response<ResponseTheLoai> response) {
                ResponseTheLoai responseTheLoai = response.body();
                List<TheLoai> listTheLoaiApi = responseTheLoai.getData();
                int statusCode = responseTheLoai.getStatusCode();
                String message = responseTheLoai.getMessage();

                theLoaiAdapter = new TheLoaiAdapter(ChinhSuaDoUongActivity.this, R.layout.item_the_loai_selected, listTheLoaiApi);
                spn_chinh_sua_do_uong.setAdapter(theLoaiAdapter);

                // Tìm vị trí của thể loại của sản phẩm trong danh sách thể loại
                if (product != null && product.getTheLoai() != null) {
                    TheLoai theLoaiProduct = product.getTheLoai();
                    int position = -1;

                    for (int i = 0; i < listTheLoaiApi.size(); i++) {
                        if (listTheLoaiApi.get(i).getId_theLoai() == theLoaiProduct.getId_theLoai()) {
                            position = i;
                            break;
                        }
                    }

                    // Nếu tìm thấy vị trí, chọn mục tương ứng trong Spinner
                    if (position != -1) {
                        spn_chinh_sua_do_uong.setSelection(position);
                    }
                }

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
}