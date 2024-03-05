package com.tugas.tugascashierapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.HashMap;
import android.widget.FrameLayout;
import java.util.Map;
import androidx.core.content.ContextCompat;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvHarga;
    private EditText etNama, etCariMenu, etMenu1Jumlah, etMenu2Jumlah, etMenu3Jumlah, etMenu4Jumlah;
    private RadioGroup radioGroup;
    private RadioButton rbVIP3, rbVIP2, rbVIP1;
    private Button btnBeli, btnReset, btnCariMenu;
    private int selectedFrameId = -1;

    private HashMap<String, Integer> menuFrameMap = new HashMap<>();

    private int Harga = 0;

    private int getQuantity(EditText editText) {
        String quantityStr = editText.getText().toString().trim();
        if (quantityStr.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(quantityStr);
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHarga = findViewById(R.id.tvHarga);
        etNama = findViewById(R.id.etNama);
        etCariMenu = findViewById(R.id.etCariMenu);
        etMenu1Jumlah = findViewById(R.id.etMenu1Jumlah);
        etMenu2Jumlah = findViewById(R.id.etMenu2Jumlah);
        etMenu3Jumlah = findViewById(R.id.etMenu3Jumlah);
        etMenu4Jumlah = findViewById(R.id.etMenu4Jumlah);
        radioGroup = findViewById(R.id.radioGroup);
        rbVIP3 = findViewById(R.id.rbVIP3);
        rbVIP2 = findViewById(R.id.rbVIP2);
        rbVIP1 = findViewById(R.id.rbVIP1);
        btnBeli = findViewById(R.id.btnBeli);
        btnCariMenu = findViewById(R.id.btnCariMenu);
        btnReset = findViewById(R.id.btnReset);

        // Simpan daftar nama menu beserta frame ID nya
        HashMap<String, Integer> menuFrameMap = new HashMap<>();
        menuFrameMap.put("Sempoerna", R.id.frmMenu1_container);
        menuFrameMap.put("sempoerna", R.id.frmMenu1_container);
        menuFrameMap.put("L.A", R.id.frmMenu2_container);
        menuFrameMap.put("l.a", R.id.frmMenu2_container);
        menuFrameMap.put("Surya", R.id.frmMenu3_container);
        menuFrameMap.put("surya", R.id.frmMenu3_container);
        menuFrameMap.put("Malboro", R.id.frmMenu4_container);
        menuFrameMap.put("malboro", R.id.frmMenu4_container);

        btnCariMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = etCariMenu.getText().toString().trim();
                if (!keyword.isEmpty()) {
                    for (Map.Entry<String, Integer> entry : menuFrameMap.entrySet()) {
                        if (entry.getKey().equalsIgnoreCase(keyword)) {
                            int frameId = entry.getValue();
                            FrameLayout selectedFrame = findViewById(frameId);
                            selectedFrame.setBackgroundResource(R.drawable.selected_frame_background);
                            selectedFrameId = frameId;
                        }
                    }
                }
            }
        });

        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMenu1Jumlah.getText().toString().isEmpty()) {
                    etMenu1Jumlah.setText("0");
                }
                if (etMenu2Jumlah.getText().toString().isEmpty()) {
                    etMenu2Jumlah.setText("0");
                }
                if (etMenu3Jumlah.getText().toString().isEmpty()) {
                    etMenu3Jumlah.setText("0");
                }
                if (etMenu4Jumlah.getText().toString().isEmpty()) {
                    etMenu4Jumlah.setText("0");
                }
                hitungTotalHarga();
                tampilDeskripsiBeli();
            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
            }
        });
    }

    private void hitungTotalHarga() {
        int menu1Price = Integer.parseInt(etMenu1Jumlah.getText().toString()) * 32000;
        int menu2Price = Integer.parseInt(etMenu2Jumlah.getText().toString()) * 33600;
        int menu3Price = Integer.parseInt(etMenu3Jumlah.getText().toString()) * 29000;
        int menu4Price = Integer.parseInt(etMenu4Jumlah.getText().toString()) * 39000;

        Harga = menu1Price + menu2Price + menu3Price + menu4Price;

        int discount = 0;

        Harga -= discount;

        if (Harga > 35000) {
            Harga -= 1500;
        }

        if (radioGroup.getCheckedRadioButtonId() == R.id.rbVIP3) {
            discount = 1000;
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbVIP2) {
            discount = 700;
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbVIP1) {
            discount = 500;
        }

        // Update dari tvTotalHarga
        tvHarga.setText("Total Harga : Rp." + Harga);
    }

    private void tampilDeskripsiBeli() {
        StringBuilder summary = new StringBuilder("---------------------------\n");
        String namaPembeli = etNama.getText().toString().trim();
        summary.append("Nama Pembeli: ").append(namaPembeli).append("\n");
        if (Integer.parseInt(etMenu1Jumlah.getText().toString()) > 0) {
            summary.append("- Sempoerna x").append(etMenu1Jumlah.getText().toString())
                    .append(" (Rp. ").append(Integer.parseInt(etMenu1Jumlah.getText().toString()) * 32000).append(")\n");
        }
        if (Integer.parseInt(etMenu2Jumlah.getText().toString()) > 0) {
            summary.append("- L.A x").append(etMenu2Jumlah.getText().toString())
                    .append(" (Rp. ").append(Integer.parseInt(etMenu2Jumlah.getText().toString()) * 33600).append(")\n");
        }
        if (Integer.parseInt(etMenu3Jumlah.getText().toString()) > 0) {
            summary.append("- Surya x").append(etMenu3Jumlah.getText().toString())
                    .append(" (Rp. ").append(Integer.parseInt(etMenu3Jumlah.getText().toString()) * 29000).append(")\n");
        }
        if (Integer.parseInt(etMenu4Jumlah.getText().toString()) > 0) {
            summary.append("- Malboro x").append(etMenu4Jumlah.getText().toString())
                    .append(" (Rp. ").append(Integer.parseInt(etMenu4Jumlah.getText().toString()) * 39000).append(")\n");
        }

        summary.append("Membership: ");
        if (radioGroup.getCheckedRadioButtonId() == R.id.rbVIP3) {
            summary.append("VIP3\n");
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbVIP2) {
            summary.append("VIP2\n");
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbVIP1) {
            summary.append("VIP1\n");
        }

        int diskon = 0;


        if (Harga > 35000) {
            diskon += 1500;
            summary.append("Diskon (total harga > 35000): -Rp 1500\n");
        } else {
            summary.append("Diskon (total harga > 35000): -\n");
        }


        if (radioGroup.getCheckedRadioButtonId() == R.id.rbVIP3) {
            diskon += 1000;
            summary.append("Diskon Membership VIP3: -Rp 1000\n");
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbVIP2) {
            diskon += 700;
            summary.append("Diskon Membership VIP2: -Rp 700\n");
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbVIP1) {
            diskon += 500;
            summary.append("Diskon Membership VIP1: -Rp 500\n");
        } else {
            summary.append("Diskon Membership: -\n");
        }


        summary.append("\nTotal Harga: Rp ").append(Harga).append("\n");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Catatan Pembelian")
                .setMessage(summary.toString())
                .setPositiveButton("Beli", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Reset data
                        resetData();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void resetData() {
        etNama.setText("");
        etCariMenu.setText("");
        etMenu1Jumlah.setText("");
        etMenu2Jumlah.setText("");
        etMenu3Jumlah.setText("");
        etMenu4Jumlah.setText("");
        radioGroup.clearCheck();
        tvHarga.setText("Harga : Rp.");
        if (selectedFrameId != -1) {
            FrameLayout selectedFrame = findViewById(selectedFrameId);
            selectedFrame.setBackground(ContextCompat.getDrawable(MainActivity.this, android.R.color.transparent));
            selectedFrameId = -1; // Reset ID frame yg terpilih
        }
        if (menuFrameMap != null) {

            for (int frameId : menuFrameMap.values()) {
                FrameLayout frameLayout = findViewById(frameId);
                frameLayout.setBackground(ContextCompat.getDrawable(MainActivity.this, android.R.color.transparent));
            }
        }
    }
}