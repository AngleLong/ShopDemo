package com.jinlong.shop;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现购物车案例
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private String mText;
    private RecyclerView mRv_shop;
    private ShopRVAdapter mShopRVAdapter;
    private boolean totalStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        List<ShopBean.ResultBean.CartlistBean> shopItems = initList();

        isShopFirst(shopItems);

        initRecycleView(shopItems);

        initListener();
    }


    /**
     * 从本地APK中初始化数据
     */
    private void initData() {
        try {
            InputStream is = getAssets().open("preserveFile.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            mText = new String(buffer, "utf-8");
            Log.e(TAG, "initData: " + mText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 规范list集合中的数据，这里就是把返回的数据的第一层舍去
     *
     * @return 所以商品的集合
     */
    private List<ShopBean.ResultBean.CartlistBean> initList() {
        List<ShopBean.ResultBean.CartlistBean> shopItems = new ArrayList<>();
        Gson gson = new Gson();
        ShopBean shopBean = gson.fromJson(mText, ShopBean.class);
        /*这个就是第一层的集合了*/
        List<ShopBean.ResultBean> result = shopBean.getResult();
        /*这个循环结束的时候，就是所有商场条目的数据了*/
        for (int i = 0; i < result.size(); i++) {
            List<ShopBean.ResultBean.CartlistBean> cartlist = result.get(i).getCartlist();
            shopItems.addAll(cartlist);
        }

        return shopItems;
    }

    /**
     * 设置商品是否是第一个商品下的条目
     *
     * @param shopItems 所有商品数据
     */
    private void isShopFirst(List<ShopBean.ResultBean.CartlistBean> shopItems) {
        shopItems.get(0).setIsFirst(1);
        for (int i = 1; i < shopItems.size(); i++) {
            if (shopItems.get(i).getShopId() == shopItems.get(i - 1).getShopId()) {
                shopItems.get(i).setIsFirst(2);
            } else {
                shopItems.get(i).setIsFirst(1);
            }
        }
    }

    /**
     * 初始化商品的集合
     *
     * @param shopItems RecycleView展示的商品数据
     */
    private void initRecycleView(List<ShopBean.ResultBean.CartlistBean> shopItems) {
        mRv_shop = (RecyclerView) findViewById(R.id.rv_shop);

        mRv_shop.setLayoutManager(new LinearLayoutManager(this));
        mShopRVAdapter = new ShopRVAdapter(this, shopItems);
        mRv_shop.setAdapter(mShopRVAdapter);
    }

    private void initListener() {
        /*这里要设置一下全选的按钮状态*/

         final TextView tvTotalPrice = (TextView) findViewById(R.id.tv_totalPrice);
        final TextView tvTotalSelect = (TextView) findViewById(R.id.tv_totalSelect);

        tvTotalSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalStatus = !totalStatus;
                if (totalStatus) {
                    Drawable drawable = getResources().getDrawable(R.mipmap.shopcart_selected);
                    tvTotalSelect.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                } else {
                    Drawable drawable = getResources().getDrawable(R.mipmap.shopcart_unselected);
                    tvTotalSelect.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                }
                mShopRVAdapter.totalSelect(totalStatus);
            }
        });


        /*更新价格的监听*/
        mShopRVAdapter.setRefurbishTotalPrice(new ShopRVAdapter.RefurbishTotalPrice() {
            @Override
            public void refurbishPrice(int totalPrice) {
                tvTotalPrice.setText("总价格:" + totalPrice);
            }

            @Override
            public void isTotalSelect(boolean isSelect) {
                if (isSelect) {
                    Drawable drawable = getResources().getDrawable(R.mipmap.shopcart_selected);
                    tvTotalSelect.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                } else {
                    Drawable drawable = getResources().getDrawable(R.mipmap.shopcart_unselected);
                    tvTotalSelect.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                }
            }
        });
    }
}
