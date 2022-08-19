package in.tukumonkeyvendor.shoplist.mvp;


import in.tukumonkeyvendor.shoplist.model.ShopListResponse;

public interface ShopListContract {

    void shoplist_success(ShopListResponse shopListResponse);

    void shoplist_failure(String msg);

    void dashboard_logout();

    interface GetShopListIntractor {

        interface OnFinishedListener {
            void onFinished(ShopListResponse shopListResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void shoplistAPICall(GetShopListIntractor.OnFinishedListener onFinishedListener);
    }
}
