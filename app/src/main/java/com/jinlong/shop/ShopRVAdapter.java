package com.jinlong.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 作者：贺金龙*
 * 创建时间：2017/8/20 16:16*
 * 类描述：* 首页购物车适配器
 * 修改人：*
 * 修改内容:*
 * 修改时间：*
 */

public class ShopRVAdapter extends RecyclerView.Adapter<ShopRVAdapter.ShopHolder> implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<ShopBean.ResultBean.CartlistBean> mList;
    private RefurbishTotalPrice mRefurbishTotalPrice;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_child_select:/*子条目的点击事件*/
                handleChildClick(view);
                break;
            case R.id.iv_group_select:/*父条目的点击事件*/
                handleGroupClick(view);
                break;
        }
    }

    /**
     * 处理子View的点击事件
     *
     * @param view 相应的控件
     */
    private void handleChildClick(View view) {
        int position = (int) view.getTag();
        mList.get(position).setSelect(!mList.get(position).isSelect());
        /*先找到不同商铺的第一个商品的位置*/
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getIsFirst() == 1) {
                /*所有第一个商品的位置，开始计算所有商品是否处于选中状态
                如果都处于选中状态的话，全选按钮选中，否则全选按钮不选中*/
                for (int j = 0; j < mList.size(); j++) {
                    if (mList.get(j).getShopId() == mList.get(i).getShopId()/*这里证明是同一个商铺的*/
                            && !mList.get(j).isSelect()/*其中有一个没有被选中*/) {
                        mList.get(i).setShopSelect(false);
                        break;
                    } else {
                        mList.get(i).setShopSelect(true);
                    }
                }
            }
        }
        notifyDataSetChanged();

        /*这里要重新设置总价格*/
        reckonPrice();

        /*这里要看看是否是全部选中*/
        isTotalSelect();
    }

    /**
     * 处理父View的点击事件
     *
     * @param view 相应的控件
     */
    private void handleGroupClick(View view) {
        int position = (int) view.getTag();
        /*取反操作*/
        mList.get(position).setShopSelect(!mList.get(position).isShopSelect());
        if (mList.get(position).getIsFirst() == 1) {
            /*所有第一个商品的位置开始计算，这里面因为点击的保证是第一个因为是从属关系的*/
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getShopId() == mList.get(position).getShopId()) {
                    mList.get(i).setSelect(mList.get(position).isShopSelect());
                }
            }
            notifyDataSetChanged();
        }

        /*这里要重新计算总价格*/
        reckonPrice();

        /*这里要看看是否是全部选中*/
        isTotalSelect();
    }

    /**
     * 计算你总价格的方法
     */
    public void reckonPrice() {
        if (mRefurbishTotalPrice != null) {
            int totalPrice = 0;
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).isSelect()) {
                    totalPrice += Integer.valueOf(mList.get(i).getPrice()) * Integer.valueOf(mList.get(i).getCount());
                }
            }
            mRefurbishTotalPrice.refurbishPrice(totalPrice);
        }
    }

    /**
     * 判断所有条目是否全部选中
     */
    public void isTotalSelect() {
        if (mRefurbishTotalPrice != null) {
            boolean isTotalSelect = false;
            for (int i = 0; i < mList.size(); i++) {
                if (!mList.get(i).isSelect()) {
                    isTotalSelect = false;
                    break;
                } else {
                    isTotalSelect = true;
                }
            }
            mRefurbishTotalPrice.isTotalSelect(isTotalSelect);
        }
    }

    private interface HEAD {
        int head = 1;
        int noHead = 2;
    }

    public ShopRVAdapter(Context context, List<ShopBean.ResultBean.CartlistBean> shopItems) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mList = shopItems;
    }

    @Override
    public ShopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_shop, parent, false);
        return new ShopHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShopHolder holder, int position) {
        /*设置顶部是否显示*/
        if (mList.get(position).getIsFirst() == HEAD.head) {
            holder.mHead.setVisibility(View.VISIBLE);
        } else {
            holder.mHead.setVisibility(View.GONE);
        }

        /*设置点击子View的状态*/
        if (mList.get(position).isSelect()) {
            holder.mChildSelect.setImageResource(R.mipmap.shopcart_selected);
        } else {
            holder.mChildSelect.setImageResource(R.mipmap.shopcart_unselected);
        }

        /*判断父View全选按钮的状态*/
        if (mList.get(position).isShopSelect()) {
            holder.mGroutSelect.setImageResource(R.mipmap.shopcart_selected);
        } else {
            holder.mGroutSelect.setImageResource(R.mipmap.shopcart_unselected);
        }

        /*子条目的选中事件*/
        holder.mChildSelect.setTag(position);
        holder.mChildSelect.setOnClickListener(this);

        /*父条目的选中事件*/
        holder.mGroutSelect.setTag(position);
        holder.mGroutSelect.setOnClickListener(this);

        /*设置图片*/
        Glide.with(mContext).load(mList.get(position).getDefaultPic()).into(holder.mShopPic);
    }

    /**
     * 这里添加三个属性，很重要的
     */
    private boolean isSelect = false; //每件商品是否被选中
    private int isFirst = 2;//是否是每个商铺的第一个位置，是返回1，否返回2
    private boolean isShopSelect = false;/*商铺是否被选择*/

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class ShopHolder extends RecyclerView.ViewHolder {
        private LinearLayout mHead;/*头部布局*/
        private ImageView mChildSelect;/*子条目的选中按钮*/
        private ImageView mGroutSelect;/*父条目的选中按钮*/
        private ImageView mShopPic;/*条目的图片*/

        public ShopHolder(View itemView) {
            super(itemView);
            mHead = itemView.findViewById(R.id.ll_head);
            mChildSelect = itemView.findViewById(R.id.iv_child_select);
            mGroutSelect = itemView.findViewById(R.id.iv_group_select);
            mShopPic = itemView.findViewById(R.id.iv_shop_pic);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getIsFirst() == HEAD.head) {
            return HEAD.head;
        } else {
            return HEAD.noHead;
        }
    }

    /**
     * 对外暴露计算总价格的接口
     */
    public interface RefurbishTotalPrice {
        /**
         * 实时更新总价格的方法
         *
         * @param totalPrice 总价格
         */
        void refurbishPrice(int totalPrice);

        /**
         * 是否全部选中
         *
         * @param isSelect true的时候是全部选中，false为不全部选中
         */
        void isTotalSelect(boolean isSelect);
    }

    public void setRefurbishTotalPrice(RefurbishTotalPrice refurbishTotalPrice) {
        mRefurbishTotalPrice = refurbishTotalPrice;
    }

    /**
     * 外部全选按钮
     *
     * @param isSelect 全选还是反选
     */
    public void totalSelect(boolean isSelect) {
        isSelect(isSelect);
        notifyDataSetChanged();
        /*这里应该调用一下价格*/
        reckonPrice();
    }

    /**
     * 根据设置更改集合中的选中状态
     *
     * @param isSelect 选中状态
     */
    public void isSelect(boolean isSelect) {
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setSelect(isSelect);
            mList.get(i).setShopSelect(isSelect);
        }
    }
}
