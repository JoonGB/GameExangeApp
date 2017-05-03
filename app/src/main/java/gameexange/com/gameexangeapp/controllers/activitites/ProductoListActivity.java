package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.ProductoCallback;
import gameexange.com.gameexangeapp.controllers.managers.ProductoManager;
import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;

public class ProductoListActivity extends AppCompatActivity implements ProductoCallback {
    private GridView productosGrid;
    private Foto fotoPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductoManager.getInstance().getAllProductosDTO(ProductoListActivity.this);
    }

    @Override
    public void onSuccess(List<Producto> productos) {
        Log.e("ProductosActivity->", productos.toString());
        if (productos.size() > 0) {

            setContentView(R.layout.activity_products);
            productosGrid = (GridView) findViewById(R.id.grid1);

            ProductoListAdapter adapter = new ProductoListAdapter(this, productos);
            productosGrid.setAdapter(adapter);
        } else {
            setContentView(R.layout.productos_nohayproductos);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e("ProductoListActivity->", "getProductosBy->onFailure ERROR " + t.getMessage());
    }

    @Override
    public void onSuccessFotos(List<Foto> fotos) {
        Log.e("ProductosActivity->", fotos.toString());
        if (fotos.size() > 0) {

        } else {
        }
    }

    @Override
    public void onSuccessFotoPrincipal(Foto foto) {
        Log.e("ProductosActivity->", foto.toString());
        if (foto != null) {
            fotoPrincipal = foto;
        }
    }


    public class ProductoListAdapter extends BaseAdapter {
        private Context context;
        private List<Producto> productosList;

        public ProductoListAdapter(Context context, List<Producto> productosList) {
            this.context = context;
            this.productosList = productosList;
        }

        @Override public int getCount() {
            return productosList.size();
        }
        @Override public Object getItem(int position) {
            return productosList.get(position);
        }
        @Override public long getItemId(int position) {
            return productosList.get(position).getId();
        }

        public class ViewHolder {
            public TextView tvId;
            public TextView tvNombre;
            public TextView tvDescripcion;
            public TextView tvPrecio;
            public ImageView tvImatge;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.products_item, viewGroup, false);

            }

            // Cogemos los items de la view
            TextView tvId = (TextView) view.findViewById(R.id.id);
            TextView tvNombre = (TextView) view.findViewById(R.id.nombre);
            TextView tvDescripcion = (TextView) view.findViewById(R.id.descripcion);
            TextView tvPrecio = (TextView) view.findViewById(R.id.precio);
            ImageView tvImatge = (ImageView) view.findViewById(R.id.foto);

            // Cogemos el producto, le extraemos los atributos y los colocamos en los items
            Producto product = productosList.get(position);
            tvId.setText(Long.toString(product.getId()));
            tvNombre.setText(product.getNombre());
            tvDescripcion.setText(product.getDescripcion());
            tvPrecio.setText(String.valueOf(productosList.get(position).getPrecio()) + " €");
            String fotoprincipal = product.getFotoPrincipal().getFoto();


            byte[] imageAsBytes  = Base64.decode(fotoprincipal, Base64.DEFAULT);
            tvImatge.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            tvImatge.setMaxWidth(80);

            return view;
        }
    }
}
