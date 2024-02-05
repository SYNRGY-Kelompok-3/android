package com.synrgy.travelid.presentation.notification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.travelid.databinding.FragmentNotificationBinding
import com.synrgy.travelid.domain.model.main.Notification
import com.synrgy.travelid.domain.model.main.UserProfile
import com.synrgy.travelid.presentation.notification.adapter.NotificationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private val viewModel: NotificationViewModel by viewModels()
    private lateinit var notificationAdapter: NotificationAdapter
    private var id: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userProfile()
        viewModel.checkLoggedIn()

        bindAdapter()
        bindView()
        observeViewModel()
    }

    private fun bindView(){
        binding.tvMarkAllRead.setOnClickListener { markAllItemsAsRead() }
    }

    private fun observeViewModel() {
        viewModel.notLoggedIn.observe(viewLifecycleOwner, ::handleNotLoggedIn)
        viewModel.showEmpty.observe(viewLifecycleOwner, ::handleEmptyData)
        viewModel.showUser.observe(viewLifecycleOwner, ::handleShowUser)
        viewModel.showNotification.observe(viewLifecycleOwner, ::handleDataNotification)
    }

    private fun bindAdapter(){
        notificationAdapter = NotificationAdapter(object : NotificationAdapter.OnClickListener {
            override fun onClickItem(data: Notification) {

            }
        })
        binding.rvNotification.adapter = notificationAdapter
    }

    private fun handleEmptyData(isEmpty: Boolean) {
        binding.emptyState.visibility = if(isEmpty) View.VISIBLE else View.GONE
    }

    private fun handleNotLoggedIn(isNotLoggedIn: Boolean) {
        if (isNotLoggedIn) {
            val bottomSheetLogin = BottomSheetNotificationFragment()
            bottomSheetLogin.isCancelable = false
            bottomSheetLogin.show(childFragmentManager, bottomSheetLogin.tag)
        }
    }

    private fun handleShowUser(userProfile: UserProfile) {
        id = userProfile.id
        viewModel.notification(id)
    }

    private fun handleDataNotification(notification: List<Notification>) {
        notificationAdapter.submitData(notification)

        val notifCounter = notificationAdapter.itemCount.toString()
        binding.tvNotificationCounter.text = notifCounter
    }

    private fun markAllItemsAsRead() {
        for (notification in notificationAdapter.notifications) {
            notification.isBadgeRead = true
        }
        notificationAdapter.notifyDataSetChanged()
    }
}
