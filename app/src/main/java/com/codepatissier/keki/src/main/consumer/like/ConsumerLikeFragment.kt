package com.codepatissier.keki.src.main.consumer.like

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerLikeBinding
import com.codepatissier.keki.util.recycler.like.LikeFeedAdapter
import com.codepatissier.keki.util.recycler.like.LikeFeedData


class ConsumerLikeFragment : BaseFragment<FragmentConsumerLikeBinding>
    (FragmentConsumerLikeBinding::bind, R.layout.fragment_consumer_like) {
    private lateinit var likeFeedAdapter: LikeFeedAdapter
    private val likeDataList = mutableListOf<LikeFeedData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLikeRecyclerView()
    }

    private fun setLikeRecyclerView() {
        // 테스트용 임시 데이터
        likeDataList.apply {
            for (i in 0 until 3) {
                add(
                    LikeFeedData(
                        1,
                        "https://i.pinimg.com/originals/b5/01/02/b501027800aa25a77441e4398f31262b.jpg",
                        "케이크 1호",
                        51200
                    )
                )
                add(
                    LikeFeedData(
                        2,
                        "https://i.pinimg.com/originals/b9/43/90/b943904a9243aa3d485a65c55a07acde.jpg",
                        "케이크 2호",
                        31000
                    )
                )
                add(
                    LikeFeedData(
                        3,
                        "https://i.pinimg.com/originals/c9/fd/04/c9fd04c6a219910cc47c3eca876ed999.jpg",
                        "케이크 3호",
                        31000
                    )
                )
                add(
                    LikeFeedData(
                        4,
                        "https://i.pinimg.com/originals/1f/54/cd/1f54cd979085b41485487e6ac8e93a5b.jpg",
                        "미니 케이크",
                        31000
                    )
                )
                add(
                    LikeFeedData(
                        5,
                        "https://i.pinimg.com/originals/b5/5c/30/b55c30da99174a8ed1ed6ef18f8b1fe2.jpg",
                        "도시락 케이크",
                        31000
                    )
                )
                add(
                    LikeFeedData(
                        6,
                        "https://i.pinimg.com/originals/a2/10/99/a2109966af2471edb8828b282be848ac.jpg",
                        "Cake is a cake. I like a cake!",
                        31000
                    )
                )
                add(
                    LikeFeedData(
                        7,
                        "https://i.pinimg.com/originals/6a/78/d0/6a78d04b7e2c89d12a4c11598d567696.jpg",
                        "천방지축 빙글빙글 돌아가는 케이크의 하루",
                        1231000
                    )
                )
            }
        }

        binding.rvLikeGrid.setEmptyView(binding.layoutEmptyLike)
        val mLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvLikeGrid.layoutManager = mLayoutManager
        likeFeedAdapter = LikeFeedAdapter(likeDataList, requireContext())
        binding.rvLikeGrid.adapter = likeFeedAdapter
    }

}