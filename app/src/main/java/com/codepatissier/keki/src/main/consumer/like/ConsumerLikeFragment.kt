package com.codepatissier.keki.src.main.consumer.like

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerLikeBinding
import com.codepatissier.keki.src.main.consumer.like.model.ConsumerLikeResponse
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreFeedService
import com.codepatissier.keki.util.recycler.like.LikeFeedAdapter
import com.codepatissier.keki.util.recycler.like.LikeFeedData


class ConsumerLikeFragment : BaseFragment<FragmentConsumerLikeBinding>
    (FragmentConsumerLikeBinding::bind, R.layout.fragment_consumer_like), ConsumerLikeView {
    private lateinit var likeFeedAdapter: LikeFeedAdapter
    private val likeDataList = mutableListOf<LikeFeedData>()
    var lastItemVisible = false
    var hasNext = false
    var cursorDate: String? = null
    var positionStart = 0
    var itemSize = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog(requireContext())
        ConsumerLikeService(this).tryGetLike(18)
    }

    private fun setLikeRecyclerView(response: ConsumerLikeResponse) {
        hasNext = response.result.hasNext
        cursorDate = response.result.cursorDate
        for (i in response.result.feeds.indices) {
            likeDataList.apply {
                add(
                    LikeFeedData(response.result.feeds[i].postIdx,
                                response.result.feeds[i].postImgUrl,
                                response.result.feeds[i].dessertName,
                                response.result.feeds[i].dessertPrice)
                )
            }
        }

        binding.rvLikeGrid.setEmptyView(binding.layoutEmptyLike)
        val mLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvLikeGrid.layoutManager = mLayoutManager
        likeFeedAdapter = LikeFeedAdapter(likeDataList, requireContext())
        binding.rvLikeGrid.adapter = likeFeedAdapter

        binding.rvLikeGrid.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                // 현재 보이는 마지막 아이템의 position
                var lastVisibleItemPosition = (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                // 전제 아이템 갯수
                val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1)

                // 마지막 아이템이면 true로 변경
                if( lastVisibleItemPosition != itemTotalCount) {
                    lastItemVisible = true
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                // 스크롤이 멈춰있고, 다음 아이템이 있으며 마지막 아이템일 때 api 호출
                if(newState == RecyclerView.SCROLL_STATE_IDLE && hasNext && lastItemVisible){
                    binding.progress.visibility = View.VISIBLE
                    positionStart = likeDataList.size
                    ConsumerLikeService(this@ConsumerLikeFragment).tryGetLikeNext(cursorDate!!, 15)
                }
            }
        })
    }

    override fun onGetLikeSuccess(response: ConsumerLikeResponse) {
        dismissLoadingDialog()
        setLikeRecyclerView(response)
    }

    override fun onGetLikeFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetNextLikeSuccess(response: ConsumerLikeResponse) {
        binding.progress.visibility = View.GONE

        cursorDate = response.result.cursorDate
        hasNext = response.result.hasNext

        for (i in response.result.feeds.indices) {
            likeDataList.apply {
                add(
                    LikeFeedData(
                        response.result.feeds[i].postIdx,
                        response.result.feeds[i].postImgUrl,
                        response.result.feeds[i].dessertName,
                        response.result.feeds[i].dessertPrice
                    )
                )
            }

            itemSize = likeDataList.size - positionStart
            likeFeedAdapter.notifyItemRangeChanged(positionStart, itemSize)
        }
    }

    override fun onGetNextLikeFailure(message: String) {
        binding.progress.visibility = View.GONE
        showCustomToast("오류 : $message")
    }

}