package in.tukumonkeyvendor.orders.mvp_search;

import in.tukumonkeyvendor.orders.model.OrdersListResponse;

public interface OrderSearchContract {

    void orderListsearch_failure(String msg);
    void orderListsearch_success(OrdersListResponse ordersListResponse);
    void dashboard_logout();

    interface GetOrderListsearchIntractor {

        interface OnFinishedListener {
            void onFinished(OrdersListResponse ordersListResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void orderListsearchAPICall(GetOrderListsearchIntractor.OnFinishedListener onFinishedListener);
    }
}
