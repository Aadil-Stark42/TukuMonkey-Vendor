package in.tukumonkeyvendor.orders.mvp_orderdetails;


import in.tukumonkeyvendor.orders.model_ordredetails.OrderDetailResponse;

public interface OrderDetailContract {

    void orderdetail_success(OrderDetailResponse orderDetailResponse);

    void orderdetail_failure(String msg);

    void dashboard_logout();

    interface GetorderdetailIntractor {

        interface OnFinishedListener {
            void onFinished(OrderDetailResponse orderDetailResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void orderdetailAPICall(GetorderdetailIntractor.OnFinishedListener onFinishedListener);
    }
}
