package in.tukumonkeyvendor.slot.mvp_delete;

import android.os.Handler;
import org.jetbrains.annotations.NotNull;
import in.tukumonkeyvendor.retrofit.VDeliverzApiVendor;
import in.tukumonkeyvendor.utils.GeneralResponse;
import in.tukumonkeyvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SlotDeleteIntract implements  SlotDeleteContract.GetslotdeleteIntractor {

    String TAG= SlotDeleteIntract.class.getSimpleName();
    String strSlotId;

    interface OnSlotDeleteListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strSlotid, final OnSlotDeleteListener listener) {
        this.strSlotId=strSlotid;

        new Handler().postDelayed(() -> {
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void slotdeleteAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().deleteslot(strSlotId).enqueue(new Callback<GeneralResponse>(){
            @Override
            public void onResponse(@NotNull Call<GeneralResponse> call, @NotNull Response<GeneralResponse> response) {
                if(response.isSuccessful()) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            if(response.body().getStatus()){
                                onFinishedListener.onFinished(response.body());
                            }else {
                                onFinishedListener.onFailure(response.body().getMessage());
                            }
                        }else{
                            onFinishedListener.onFailure(response.message());
                        }
                    }else {
                        onFinishedListener.onFailure(response.message());
                    }
                }else {
                    if(response.code()==401){
                        MnxPreferenceManager.clearAllPreferences();
                        onFinishedListener.do_logout();
                    }else{
                        onFinishedListener.onFailure("Server Error");
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<GeneralResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
