package com.example.coffeeappmanage.activity.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.ChangePasswordActivity;
import com.example.coffeeappmanage.activity.LoginActivity;
import com.example.coffeeappmanage.model.User;


public class AccountUserFragment extends Fragment {

    Toolbar toolbar;
    private User user;
    TextView tvPhanHoi, tvLienHe, tvDoiMatKhau, tvDangXuat;
    ImageView imgPhanHoi, imgLienHe, imgDoiMatKhau, imgDangXuat;


    public AccountUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account_user, container, false);

        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");

            // Kiểm tra xem user có được truyền qua không
            if (user != null) {
                Log.d("AccountUserFragment user", ": " + user.toString());
            }
        }

        toolbar = view.findViewById(R.id.toolbar);
        tvPhanHoi = view.findViewById(R.id.tvPhanHoi);
        tvLienHe = view.findViewById(R.id.tvLienHe);
        tvDoiMatKhau = view.findViewById(R.id.tvDoiMatKhau);
        tvDangXuat = view.findViewById(R.id.tvDangXuat);
        imgPhanHoi = view.findViewById(R.id.imgPhanHoi);
        imgLienHe = view.findViewById(R.id.imgLienHe);
        imgDoiMatKhau = view.findViewById(R.id.imgDoiMatKhau);
        imgDangXuat = view.findViewById(R.id.imgDangXuat);

        // Đăng xuất
        tvDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Thoát");
                builder.setMessage("Bạn thật sự muốn thoát?");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
//                        getActivity().finish();
                    }
                });
                builder.create().show();
            }
        });
        imgDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Thoát");
                builder.setMessage("Bạn thật sự muốn thoát?");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
//                        getActivity().finish();
                    }
                });
                builder.create().show();
            }
        });


        // Phản hồi
        tvPhanHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPhanHoi = new Intent(view.getContext(), PhanHoiActivity.class);
                startActivity(intentPhanHoi);
            }
        });
        imgPhanHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPhanHoi = new Intent(view.getContext(), PhanHoiActivity.class);
                startActivity(intentPhanHoi);
            }
        });


        // Liên hệ
        tvLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPhanHoi = new Intent(view.getContext(), LienHeActivity.class);
                startActivity(intentPhanHoi);
            }
        });
        imgLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPhanHoi = new Intent(view.getContext(), LienHeActivity.class);
                startActivity(intentPhanHoi);
            }
        });

        // Đổi mật khẩu
        tvDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDanhGia = new Intent(getContext(), ChangePasswordActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("user", user);
                intentDanhGia.putExtras(data);
                startActivity(intentDanhGia);
            }
        });
        imgDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDanhGia = new Intent(getContext(), ChangePasswordActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("user", user);
                intentDanhGia.putExtras(data);
                startActivity(intentDanhGia);
            }
        });




        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
    }


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

    }



    // Phương thức này để inflate menu cho Fragment
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // Inflate the menu, không dùng getMenuInflater() mà dùng inflater
        inflater.inflate(R.menu.back_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuBack) {
            // Gọi phương thức finish() của Activity chứa Fragment
            if (getActivity() != null) {
//                getActivity().finish();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Thoát");
                builder.setMessage("Bạn thật sự muốn thoát?");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                });
                builder.create().show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}