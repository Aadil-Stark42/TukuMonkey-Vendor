package in.tukumonkeyvendor.shopoverview.mvp;

import in.tukumonkeyvendor.shopoverview.model.ShopDetailResponse;

public interface ShopDetailContract {

    void shopdetail_success(ShopDetailResponse shopDetailResponse);

    void shopdetail_failure(String msg);

    void dashboard_logout();

    interface GetshopdetailIntractor {

        interface OnFinishedListener {
            void onFinished(ShopDetailResponse shopDetailResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void shopdetailPICall(GetshopdetailIntractor.OnFinishedListener onFinishedListener);
    }
}
