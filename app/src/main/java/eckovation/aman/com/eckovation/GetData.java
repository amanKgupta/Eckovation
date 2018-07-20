package eckovation.aman.com.eckovation;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Instinct on 7/20/2018.
 */

public class GetData {
    @SerializedName("photos")
    public Photos photos;
    public class Photos{
        @SerializedName("photo")
        public List<PhotoList> lists =new ArrayList<PhotoList>();;
        public class PhotoList {
            @SerializedName("id")
            public String id;
            @SerializedName("owner")
            public String owner;
            @SerializedName("secret")
            public String secret;
            @SerializedName("server")
            public String server;
            @SerializedName("farm")
            public String farm;
        }
    }
}
