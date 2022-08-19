package in.tukumonkeyvendor.productcategory.mvp_create;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.tukumonkeyvendor.retrofit.VDeliverzApiVendor;
import in.tukumonkeyvendor.utils.GeneralResponse;
import in.tukumonkeyvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateCategoryIntract implements  CreateCategoryContract.GetCreateCategoryIntractor {

    String TAG= CreateCategoryIntract.class.getSimpleName();
    String strname;

    interface OncreatecategoryListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strname,final OncreatecategoryListener listener) {

        new Handler().postDelayed(() -> {
            this.strname=strname;

            listener.onSuccess();
        }, 500);
    }

    @Override
    public void createcategoryAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().createcategoryforproduct(strname).enqueue(new Callback<GeneralResponse>(){
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
