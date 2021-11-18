package com.xinyou.fenfademo.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinyou.fenfademo.GridDatas;
import com.xinyou.fenfademo.MainActivity;
import com.xinyou.fenfademo.R;
import com.xinyou.fenfademo.adapter.CommonAdapter;
import com.xinyou.fenfademo.adapter.ViewHolder;
import com.xinyou.fenfademo.bean.GridCutItem;
import com.xinyou.fenfademo.widget.ControlScrollView;
import com.xinyou.fenfademo.widget.ViewWithSign;

import java.util.ArrayList;
import java.util.Collections;

public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    com.xinyou.fenfademo.widget.DragGridView grid;
    ControlScrollView scroller;

    private ArrayList<GridCutItem> mDatas = new ArrayList<>();
    private CommonAdapter mAdapter;
    private boolean isPrepared;
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        grid = view.findViewById(R.id.grid);
        scroller = view.findViewById(R.id.scroller);
        lazyLoad();
    }

    protected void lazyLoad() {
//        if (!isPrepared || !isVisible) {
//            return;
//        }
        initView();
    }

    ViewWithSign viewWithSign;

    private void initView() {
        scroller.smoothScrollTo(0, 0);
        mDatas.clear();
        for (int i = 0; i < GridDatas.gridDatas.length; i++) {
            GridCutItem gridCutItem = new GridCutItem(GridDatas.gridDatas[i][0], GridDatas.gridDatas[i][1]);
            mDatas.add(gridCutItem);
        }

        grid.setAdapter(mAdapter = new CommonAdapter<GridCutItem>(getActivity(), mDatas, R.layout.adapter_grid_item) {
            @Override
            public void convert(ViewHolder helper, final GridCutItem item, int position) {
                helper.setText(R.id.tv_item, item.getName());
                viewWithSign = helper.getView(R.id.icon);
                viewWithSign.addDrawText(item.getTip());
                if (position == mAdapter.getCount() - 1) {
                    helper.setImageResource(R.id.iv_item, R.drawable.add_more);
                }
            }
        });

        //设置拖拽数据交换
        grid.setOnChangeListener(new com.xinyou.fenfademo.widget.DragGridView.OnChangeListener() {
            @Override
            public void onChange(int from, int to) {
                GridCutItem temp = mDatas.get(from);
                if (from < to) {
                    for (int i = from; i < to; i++) {
                        Collections.swap(mDatas, i, i + 1);
                    }
                } else if (from > to) {
                    for (int i = from; i > to; i--) {
                        Collections.swap(mDatas, i, i - 1);
                    }
                }
                mDatas.set(to, temp);
                mAdapter.notifyDataSetChanged();
            }
        });
        mAdapter.notifyDataSetChanged();
        scroller.setScrollState(new ControlScrollView.ScrollState() {
            @Override
            public void stopTouch() {
                grid.stopDrag();
            }

            @Override
            public void isCanDrag(boolean isCanDrag) {
                grid.setCanDrag(isCanDrag);
            }
        });

        grid.setOnDragStartListener(new com.xinyou.fenfademo.widget.DragGridView.OnDragStartListener() {
            @Override
            public void onDragStart() {
                scroller.requestDisallowInterceptTouchEvent(true);
                scroller.setInControl(false);
                ((MainActivity) getContext()).setViewpagerNoSCroll(true);
            }
        });
        grid.setOnDragEndListener(new com.xinyou.fenfademo.widget.DragGridView.OnDragEndListener() {
            @Override
            public void onDragEnd() {
                scroller.requestDisallowInterceptTouchEvent(false);
                scroller.setInControl(true);
                ((MainActivity) getContext()).setViewpagerNoSCroll(false);
                grid.postInvalidate();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}