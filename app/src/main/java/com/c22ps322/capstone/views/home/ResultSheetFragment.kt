package com.c22ps322.capstone.views.home

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.c22ps322.capstone.adapters.ListRecipeAdapter
import com.c22ps322.capstone.databinding.FragmentResultSheetBinding
import com.c22ps322.capstone.models.domain.DummyRecipe
import com.c22ps322.capstone.utils.OnItemCallbackInterface
import com.c22ps322.capstone.views.DetailFoodActivity
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ResultSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentResultSheetBinding? = null

    private val binding get() = _binding

    private var listRecipe: ArrayList<DummyRecipe>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            listRecipe = it.getParcelableArrayList(ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultSheetBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.ivClose?.setOnClickListener { this.dismiss() }

        setupRecipeList()
    }

    private fun setupRecipeList() {
        val listRecipeAdapter = ListRecipeAdapter()

        listRecipeAdapter.onItemCallback = object : OnItemCallbackInterface<DummyRecipe>{
            override fun onClick(item: DummyRecipe) {
                val intent = Intent(requireContext(), DetailFoodActivity::class.java)

                intent.putExtra(DetailFoodActivity.RECIPE_PARAM, item)

                startActivity(intent)
            }
        }

        binding?.recipeList?.adapter = listRecipeAdapter

        listRecipeAdapter.submitList(listRecipe)
    }

    override fun onStart() {
        super.onStart()

        val view: FrameLayout = dialog?.findViewById(R.id.design_bottom_sheet)!!

        view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

        val behavior = BottomSheetBehavior.from(view)

        behavior.peekHeight = 3000

        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onCancel(dialog: DialogInterface) {
        dialog.dismiss()
        super.onCancel(dialog)
    }

    companion object {
        private const val ARG_PARAM = "list-recipe"

        @JvmStatic
        fun newInstance(listRecipe: ArrayList<DummyRecipe>) =
            ResultSheetFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_PARAM, listRecipe)
                }
            }
    }
}