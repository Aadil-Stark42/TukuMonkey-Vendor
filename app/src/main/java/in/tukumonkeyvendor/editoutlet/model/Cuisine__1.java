
package in.tukumonkeyvendor.editoutlet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cuisine__1 {

    @SerializedName("cuisine_id")
    @Expose
    private Integer cuisineId;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(Integer cuisineId) {
        this.cuisineId = cuisineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
