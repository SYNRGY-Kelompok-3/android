package com.synrgy.travelid.presentation.profile.orderhistory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.FragmentOrderHistoryBinding
import com.synrgy.travelid.domain.model.main.Notification
import com.synrgy.travelid.domain.model.main.OrderHistory
import com.synrgy.travelid.domain.model.main.UserProfile
import com.synrgy.travelid.presentation.notification.adapter.NotificationAdapter
import com.synrgy.travelid.presentation.profile.orderhistory.adapter.OrderHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryFragment : Fragment() {
    companion object {
        const val ORDER_ID = "OrderId"
    }
    private lateinit var binding: FragmentOrderHistoryBinding
    private val viewModel: OrderHistoryViewModel by viewModels()
    private lateinit var orderHistoryAdapter: OrderHistoryAdapter
    private var id: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userProfile()
        bindView()
        bindAdapter()
        observeViewModel()
        setStatusBarColor()
    }

    private fun bindView(){
        binding.icBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun observeViewModel() {
        viewModel.showEmpty.observe(viewLifecycleOwner, ::handleEmptyData)
        viewModel.showUser.observe(viewLifecycleOwner, ::handleShowUser)
        viewModel.showOrderHistory.observe(viewLifecycleOwner, ::handleDataOrderHistory)
    }

    private fun bindAdapter(){
        orderHistoryAdapter = OrderHistoryAdapter(object : OrderHistoryAdapter.OnClickListener {
            override fun onClickItem(data: OrderHistory) {
                val bundle = Bundle()
                bundle.putInt(ORDER_ID, data.id)
                findNavController().navigate(R.id.action_orderHistoryFragment_to_detailOrderHistoryFragment, bundle)
            }
        })
        binding.rvOrderHistory.adapter = orderHistoryAdapter
    }

    private fun handleEmptyData(isEmpty: Boolean) {
        binding.emptyState.visibility = if(isEmpty) View.VISIBLE else View.GONE
    }

    private fun handleShowUser(userProfile: UserProfile) {
        id = userProfile.id
        viewModel.orderHistory(id)
    }

    private fun handleDataOrderHistory(orderHistory: List<OrderHistory>) {
        orderHistoryAdapter.submitData(orderHistory)
        Log.d("Order History Fragment", "Jumlah data : ${orderHistoryAdapter.itemCount}")
    }

    private fun setStatusBarColor() {
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}