package com.c22ps322.capstone.views.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.c22ps322.capstone.databinding.FragmentDetailRecipeBinding


class DetailRecipeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailRecipeBinding? = null

    private val binding get() = _binding

    private var summary: String? = null

    private var sourceUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            summary = it.getString(ARG_PARAM1)

            sourceUrl = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailRecipeBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recipeDescTv?.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(summary.orEmpty(), Html.FROM_HTML_MODE_COMPACT)
        } else {
            HtmlCompat.fromHtml(summary.orEmpty(), HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        binding?.urlLaunchBtn?.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onClick(v: View) {

        sourceUrl ?: return

        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(sourceUrl)
        )
        startActivity(intent)
    }

    companion object {
        private const val ARG_PARAM1 = "summary"

        private const val ARG_PARAM2 = "source-url"

        @JvmStatic
        fun newInstance(summary: String, sourceUrl: String) =
            DetailRecipeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, summary)

                    putString(ARG_PARAM2, sourceUrl)
                }
            }
    }


}