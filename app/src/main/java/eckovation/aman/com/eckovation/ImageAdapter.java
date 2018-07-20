package eckovation.aman.com.eckovation;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Instinct on 7/20/2018.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    ImageView imageView;
    public ArrayList<ImageBitmap> itemsArrayList;
    // Constructor
    ProgressDialog dialog ;
    public ImageAdapter(Context mContext, ArrayList<ImageBitmap> itemsArrayList) {
        this.mContext = mContext;
        this.itemsArrayList = itemsArrayList;
    }

    public int getCount() {
        return itemsArrayList.size()-1;
    }

    public Object getItem(int position) {
        return 0;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
//        dialog = new ProgressDialog(mContext);
//        dialog.setMessage("Please wait.....");
//        dialog.show();
        imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
        imageView.setPadding(16, 16, 16, 16);
        final ImageBitmap imageBitmap =itemsArrayList.get(position);
            if (imageBitmap.bitmap!=null) {
                new Thread(){
                    public void run() {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeStream(new URL(imageBitmap.bitmap).openStream());
                            imageView.setImageBitmap(bitmap);
//                            dialog.hide();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }

        return imageView;
    }

}
