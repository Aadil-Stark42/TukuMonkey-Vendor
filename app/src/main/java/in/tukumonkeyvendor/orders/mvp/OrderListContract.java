package in.tukumonkeyvendor.orders.mvp;


import in.tukumonkeyvendor.orders.model.OrdersListResponse;

public interface OrderListContract {

    void orderList_failure(String msg);
    void orderList_success(OrdersListResponse ordersListResponse);
    void dashboard_logout();

    interface GetOrderListIntractor {

        interface OnFinishedListener {
            void onFinished(OrdersListResponse ordersListResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void orderListAPICall(GetOrderListIntractor.OnFinishedListener onFinishedListener);
    }
}
