package com.android.pdfreader

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.pdfreader.other.adapter
import com.android.pdfreader.other.modal

class MainActivity : AppCompatActivity() {
    var pdf_list: ArrayList<modal> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//       making dialog
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_permission)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)

        //        setting recyclerview
        var rv = findViewById<RecyclerView>(R.id.main_rv)
        rv.layoutManager = LinearLayoutManager(this)

//        taking permission
        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        intent.setData(Uri.fromParts("package", packageName, null))
        val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), {
            if (Environment.isExternalStorageManager()) {
                pdf_list = fetch_pdf()
                dialog.cancel()
                var adapter = adapter(pdf_list,this)
                rv.adapter = adapter
            } else {
                dialog.show()
            }
        })





        if (Environment.isExternalStorageManager()) {
            pdf_list = fetch_pdf()
            var adapter = adapter(pdf_list,this)
            rv.adapter = adapter
        } else {
            dialog.show()
        }

        val btn = dialog.findViewById<Button>(R.id.dialog_btn)
        btn.setOnClickListener {
            result.launch(intent)
        }
    }


    fun fetch_pdf(): ArrayList<modal> {
        var local_pdf_list: ArrayList<modal> = ArrayList()
        val mime = MediaStore.Files.FileColumns.MIME_TYPE + "=?"
        val arg = arrayOf("application/pdf")
        val order = MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        val proj =
            arrayOf(MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.DISPLAY_NAME)
        var cursor = contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            proj,
            mime,
            arg,
            order
        )
//        MediaStore.Files.getContentUri("external")

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val obj = modal(cursor.getString(0), cursor.getString(1))
                local_pdf_list.add(obj)
//                Toast.makeText(this,""+cursor.getString(1),Toast.LENGTH_SHORT).show()
//                print(cursor.getString(1))
            }

        } else {
            Toast.makeText(this, "null cursor", Toast.LENGTH_SHORT).show()
        }
        return local_pdf_list
    }
}