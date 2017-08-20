package com.jinlong.shop;

import java.util.List;

/**
 * 作者：贺金龙*
 * 创建时间：2017/8/19 13:05*
 * 类描述：*
 * 修改人：*
 * 修改内容:*
 * 修改时间：*
 */

public class ShopBean {


    /**
     * message : 查询成功
     * status : 200
     * result : [{"shopId":2,"shopName":"文玩核桃专卖店","cartlist":[{"id":2,"shopId":2,"shopName":"文玩核桃专卖店","defaultPic":"http://img2.woyaogexing.com/2017/08/18/b7c067eae863b418!400x400_big.jpg","productId":2,"productName":"采蘑菇的小女孩","color":null,"size":null,"price":"500","count":"5"},{"id":2,"shopId":2,"shopName":"文玩核桃专卖店","defaultPic":"http://img2.woyaogexing.com/2017/08/18/3b43c84d61f725f5!400x400_big.jpg","productId":3,"productName":"天使的容貌","color":null,"size":null,"price":"60","count":"3"}]},{"shopId":3,"shopName":"图片小店","cartlist":[{"id":2,"shopId":3,"shopName":"图片小店","defaultPic":"http://img2.woyaogexing.com/2017/08/18/54c05174e98bc367!400x400_big.jpg","productId":5,"productName":"小女孩","color":null,"size":null,"price":"360","count":"1"},{"id":2,"shopId":3,"shopName":"图片小店","defaultPic":"http://img2.woyaogexing.com/2017/08/18/b806cd7cefc5355d!400x400_big.jpg","productId":6,"productName":"阳光Boy","color":null,"size":null,"price":"421","count":"3"}]}]
     */

    private String message;
    private String status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * shopId : 2
         * shopName : 文玩核桃专卖店
         * cartlist : [{"id":2,"shopId":2,"shopName":"文玩核桃专卖店","defaultPic":"http://img2.woyaogexing.com/2017/08/18/b7c067eae863b418!400x400_big.jpg","productId":2,"productName":"采蘑菇的小女孩","color":null,"size":null,"price":"500","count":"5"},{"id":2,"shopId":2,"shopName":"文玩核桃专卖店","defaultPic":"http://img2.woyaogexing.com/2017/08/18/3b43c84d61f725f5!400x400_big.jpg","productId":3,"productName":"天使的容貌","color":null,"size":null,"price":"60","count":"3"}]
         */

        private int shopId;
        private String shopName;
        private List<CartlistBean> cartlist;

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public List<CartlistBean> getCartlist() {
            return cartlist;
        }

        public void setCartlist(List<CartlistBean> cartlist) {
            this.cartlist = cartlist;
        }

        public static class CartlistBean {
            /**
             * id : 2
             * shopId : 2
             * shopName : 文玩核桃专卖店
             * defaultPic : http://img2.woyaogexing.com/2017/08/18/b7c067eae863b418!400x400_big.jpg
             * productId : 2
             * productName : 采蘑菇的小女孩
             * color : null
             * size : null
             * price : 500
             * count : 5
             */

            private int id;
            private int shopId;
            private String shopName;
            private String defaultPic;
            private int productId;
            private String productName;
            private Object color;
            private Object size;
            private String price;
            private String count;

            /*添加三个比较重要的参数*/
            private boolean isSelect = false;/*条目是否被选中*/
            private int isFirst = 1;/*当时第一条带商场名称的时候为1 ，否则为2*/
            private boolean isShopSelect = false;/*全选按钮是否被选中*/

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getShopId() {
                return shopId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getDefaultPic() {
                return defaultPic;
            }

            public void setDefaultPic(String defaultPic) {
                this.defaultPic = defaultPic;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public Object getColor() {
                return color;
            }

            public void setColor(Object color) {
                this.color = color;
            }

            public Object getSize() {
                return size;
            }

            public void setSize(Object size) {
                this.size = size;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public int getIsFirst() {
                return isFirst;
            }

            public void setIsFirst(int isFirst) {
                this.isFirst = isFirst;
            }

            public boolean isShopSelect() {
                return isShopSelect;
            }

            public void setShopSelect(boolean shopSelect) {
                isShopSelect = shopSelect;
            }
        }
    }
}
