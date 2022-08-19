package in.tukumonkeyvendor.notification.mvp;


import in.tukumonkeyvendor.notification.model.NotificationListResponse;

public interface NotificationListContract {

    void notificationlist_success(NotificationListResponse notificationListResponse);

    void notificationlist_failure(String msg);

    void dashboard_logout();

    interface GetnotificationlistIntractor {

        interface OnFinishedListener {
            void onFinished(NotificationListResponse notificationListResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void notificationlistAPICall(GetnotificationlistIntractor.OnFinishedListener onFinishedListener);
    }
}
