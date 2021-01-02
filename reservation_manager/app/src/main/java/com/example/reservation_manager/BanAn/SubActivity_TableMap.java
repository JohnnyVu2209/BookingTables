package com.example.reservation_manager.BanAn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reservation_manager.R;

public class SubActivity_TableMap extends Fragment {
//        implements View.OnClickListener {

    Button t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18;
    int a = 0;
    public View view;
    public String[] items = {"Bàn thường", "Bàn Vip"};
    public String selectedItems = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("fragmentA", "fragmentA: onCreate");
        super.onCreate(savedInstanceState);
    }

    private void AnhXa() {
        t1 = (Button) view.findViewById(R.id.btnT1);
        t2 = (Button) view.findViewById(R.id.btnT2);
        t3 = (Button) view.findViewById(R.id.btnT3);
        t4 = (Button) view.findViewById(R.id.btnT4);
        t5 = (Button) view.findViewById(R.id.btnT5);
        t6 = (Button) view.findViewById(R.id.btnT6);
        t7 = (Button) view.findViewById(R.id.btnT7);
        t8 = (Button) view.findViewById(R.id.btnT8);
        t9 = (Button) view.findViewById(R.id.btnT9);
        t10 = (Button) view.findViewById(R.id.btnT10);
        t11 = (Button) view.findViewById(R.id.btnT11);
        t12 = (Button) view.findViewById(R.id.btnT12);
        t13 = (Button) view.findViewById(R.id.btnT13);
        t14 = (Button) view.findViewById(R.id.btnT14);
        t15 = (Button) view.findViewById(R.id.btnT15);
        t16 = (Button) view.findViewById(R.id.btnT16);
        t17 = (Button) view.findViewById(R.id.btnT17);
        t18 = (Button) view.findViewById(R.id.btnT18);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("fragmentA", "fragmentA: onCreateView với biến a = " + a);
        view = inflater.inflate(R.layout.fragment_a, container, false);

        AnhXa();
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 1").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 2").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
//        t3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Thông tin bàn 3, Số lượng người: 8").setIcon(R.drawable.spoon_fork);
////                builder.setTitle("Số lượng người: 8");
//                selectedItems = items[0];
//                // gia tri duoc chon mac dinh la 0
//                builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // truyen gia tri vao selecteed
//                        selectedItems = items[which];
//                        if (selectedItems == items[0]) {
//                           t3.setBackground(t3.getContext().getResources().getDrawable(R.drawable.custom_button));
//                        } else {
//                            t3.setBackground(t3.getContext().getResources().getDrawable(R.drawable.custom_button_vip));
//                        }
//                    }
//                });
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast toast = Toast.makeText(getActivity(), "Đã Chọn : " + selectedItems, Toast.LENGTH_SHORT);
//                        toast.show();
////                        dialog.dismiss();
//                    }
//                });
//                builder.create().show();
//            }
//        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 3 -- VIP").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 8");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 4").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 5").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 6 -- VIP ").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 8");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 7").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 8 -- VIP").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 9").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 10").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 11").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 12 -- VIP").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 13").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 14").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 15").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 16 -- VIP").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 17").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 18 -- VIP").setIcon(R.drawable.spoon_fork);
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
//        t1.setOnClickListener(this);
        return  view;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnT1:
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Thông tin bàn 1").setIcon(R.drawable.spoon_fork);
//                builder.setMessage("Số lượng người: 4");
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                builder.create().show();
//                break;
//            case R.id.btnT2:
//                AlertDialog.Builder builde = new AlertDialog.Builder(getActivity());
//                builde.setTitle("Thông tin bàn 1").setIcon(R.drawable.spoon_fork);
//                builde.setMessage("Số lượng người: 4");
//                builde.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                builde.create().show();
//                break;
//            }
//        }

    @Override
    public void onStart() {
        Log.d("fragmentA", "fragmentA: onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("fragmentA", "fragmentA: onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("fragmentA", "fragmentA: onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("fragmentA", "fragmentA: onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("fragmentA", "fragmentA: onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("fragmentA", "fragmentA: onDestroy");
        super.onDestroy();
    }
}


