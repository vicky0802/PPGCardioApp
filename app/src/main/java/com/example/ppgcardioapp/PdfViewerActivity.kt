package com.example.ppgcardioapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PdfViewerActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var btnOpen: Button
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button
    private lateinit var btnShare: Button
    private lateinit var tvPage: TextView

    private var pfd: ParcelFileDescriptor? = null
    private var renderer: PdfRenderer? = null
    private var pageIndex: Int = 0
    private var currentUri: Uri? = null

    private val openDocument = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            currentUri = uri
            openRenderer(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)

        imageView = findViewById(R.id.ivPage)
        btnOpen = findViewById(R.id.btnOpenPdf)
        btnPrev = findViewById(R.id.btnPrev)
        btnNext = findViewById(R.id.btnNext)
        btnShare = findViewById(R.id.btnShare)
        tvPage = findViewById(R.id.tvPage)

        btnOpen.setOnClickListener { launchPicker() }
        btnPrev.setOnClickListener { showPage(pageIndex - 1) }
        btnNext.setOnClickListener { showPage(pageIndex + 1) }
        btnShare.setOnClickListener { shareCurrent() }

        val dataUri = intent?.data
        if (dataUri != null) {
            currentUri = dataUri
            openRenderer(dataUri)
        } else {
            launchPicker()
        }
    }

    private fun launchPicker() {
        openDocument.launch(arrayOf("application/pdf"))
    }

    private fun openRenderer(uri: Uri) {
        closeRenderer()
        pfd = contentResolver.openFileDescriptor(uri, "r")
        if (pfd != null) {
            renderer = PdfRenderer(pfd!!)
            pageIndex = 0
            showPage(0)
        }
    }

    private fun showPage(index: Int) {
        val r = renderer ?: return
        if (index < 0 || index >= r.pageCount) return
        r.openPage(index).use { page ->
            val w = page.width
            val h = page.height
            val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            imageView.setImageBitmap(bitmap)
        }
        pageIndex = index
        tvPage.text = "Page ${index + 1}/${r.pageCount}"
        btnPrev.isEnabled = index > 0
        btnNext.isEnabled = index + 1 < r.pageCount
        btnShare.isEnabled = currentUri != null
    }

    private fun shareCurrent() {
        val uri = currentUri ?: return
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(intent, "Share PDF"))
    }

    private fun closeRenderer() {
        renderer?.close()
        renderer = null
        pfd?.close()
        pfd = null
    }

    override fun onDestroy() {
        super.onDestroy()
        closeRenderer()
    }
}

