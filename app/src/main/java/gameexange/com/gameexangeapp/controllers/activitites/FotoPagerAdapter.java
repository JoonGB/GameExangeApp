package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.models.Foto;

public class FotoPagerAdapter extends PagerAdapter {

    private Context contextAdapter;
    private LayoutInflater inflaterAdapter;
    private List<Foto> fotosAdapter;

    public FotoPagerAdapter(Context context, List<Foto> fotos) {
        contextAdapter = context;
        inflaterAdapter = (LayoutInflater) contextAdapter.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fotosAdapter = fotos;
    }

    @Override
    public int getCount() {
        return fotosAdapter.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = inflaterAdapter.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);


        byte[] imageProductoAsBytes  = Base64.decode(fotosAdapter.get(position).getFoto(), Base64.DEFAULT);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageProductoAsBytes, 0, imageProductoAsBytes.length));

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
