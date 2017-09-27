package com.wuzp.newspace.network.entity.read;

/**
 * Created by wuzp on 2017/9/27.
 */
public class ChapterSinglePrice {

    private StatusBean status = new StatusBean();

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    /**
     * tip : 本作品在授权期内可阅读已购vip章节
     * tip_show : Y
     */

    private PriceTipBean price_tip;
    /**
     * role : 3
     * role_name : 白金会员
     */

    private UserinfoBean userinfo;
    /**
     * code : 0
     * msg : 成功
     */

    //    private StatusBean status;
    /**
     * is_vip : N
     * has_buy :
     * price : 0
     * title : null
     * c_id : 0
     * book_id : 5349321
     * src : websina
     * sina_id :
     */

    private ChapterBean chapter;

    public PriceTipBean getPrice_tip() {
        return price_tip;
    }

    public void setPrice_tip(PriceTipBean price_tip) {
        this.price_tip = price_tip;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public ChapterBean getChapter() {
        return chapter;
    }

    public void setChapter(ChapterBean chapter) {
        this.chapter = chapter;
    }

    public static class PriceTipBean {
        private String tip;
        private String tip_show;
        private float price;   //3.2.0免费版本新加字段
        private float buy_price;
        private String pay_method;//这个字段不用管
        private float discount_money;
        private float balance;
        private float voucher_balance;
        private int discount;
        private float voucher_discount;
        private float balance_discount;
        private int can_use_voucher;//0 不可以使用代金券 1可以使用代金券

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public String getTip_show() {
            return tip_show;
        }

        public void setTip_show(String tip_show) {
            this.tip_show = tip_show;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public float getBuy_price() {
            return buy_price;
        }

        public void setBuy_price(float buy_price) {
            this.buy_price = buy_price;
        }

        public String getPay_method() {
            return pay_method;
        }

        public void setPay_method(String pay_method) {
            this.pay_method = pay_method;
        }

        public float getDiscount_money() {
            return discount_money;
        }

        public void setDiscount_money(float discount_money) {
            this.discount_money = discount_money;
        }

        public float getBalance() {
            return balance;
        }

        public void setBalance(float balance) {
            this.balance = balance;
        }

        public float getVoucher_balance() {
            return voucher_balance;
        }

        public void setVoucher_balance(float voucher_balance) {
            this.voucher_balance = voucher_balance;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public float getVoucher_discount() {
            return voucher_discount;
        }

        public void setVoucher_discount(float voucher_discount) {
            this.voucher_discount = voucher_discount;
        }

        public float getBalance_discount() {
            return balance_discount;
        }

        public void setBalance_discount(float balance_discount) {
            this.balance_discount = balance_discount;
        }

        public int getCan_use_voucher() {
            return can_use_voucher;
        }

        public void setCan_use_voucher(int can_use_voucher) {
            this.can_use_voucher = can_use_voucher;
        }
    }

    public static class UserinfoBean {
        private int role;
        private String role_name;

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }
    }

    public static class ChapterBean {
        private String is_vip;
        private String has_buy;
        private float price;
        private String title;
        private String c_id;
        private String book_id;
        private String src;
        private String sina_id;

        public String getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public String getHas_buy() {
            return has_buy;
        }

        public void setHas_buy(String has_buy) {
            this.has_buy = has_buy;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getBook_id() {
            return book_id;
        }

        public void setBook_id(String book_id) {
            this.book_id = book_id;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getSina_id() {
            return sina_id;
        }

        public void setSina_id(String sina_id) {
            this.sina_id = sina_id;
        }
    }
}
