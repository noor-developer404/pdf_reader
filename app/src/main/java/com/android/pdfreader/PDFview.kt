package com.android.pdfreader

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView
import java.io.File
import java.nio.file.Files

class PDFview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfview)

        var pdfview = findViewById<PDFView>(R.id.pdfView)
        var path = intent.extras?.getString("path")
        pdfview.fromFile(File(path)).load()
    }
}