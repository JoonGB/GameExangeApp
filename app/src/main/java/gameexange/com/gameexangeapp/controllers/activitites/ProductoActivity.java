package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.ProductoCallback;
import gameexange.com.gameexangeapp.controllers.managers.ProductoManager;
import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;

public class ProductoActivity extends AppCompatActivity implements ProductoCallback {
    private List<Producto> productos;
    private Foto fotoProducto;
    private GridView productosGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductoManager.getInstance().getAllProductos(ProductoActivity.this);
    }

    @Override
    public void onSuccess(Object product) {
        productos = (List<Producto>) product;
        Log.e("ProductosActivity->", productos.toString());
        if (productos.size() > 0) {

            setContentView(R.layout.activity_products);
            productosGrid = (GridView) findViewById(R.id.grid1);

            CatalogAdapter adapter = new CatalogAdapter(this, productos);
            productosGrid.setAdapter(adapter);
        } else {
            setContentView(R.layout.productos_nohayproductos);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e("ProductoActivity->", "getProductosBy->onFailure ERROR " + t.getMessage());
    }

    @Override
    public void onSuccessFoto(Object foto) {
        fotoProducto = (Foto) foto;

        setContentView(R.layout.activity_products);
        productosGrid = (GridView) findViewById(R.id.grid1);

        CatalogAdapter adapter = new CatalogAdapter(this, productos);
        productosGrid.setAdapter(adapter);

    }

    public class CatalogAdapter extends BaseAdapter {

        private Context context;
        private List<Producto> catalog;

        public CatalogAdapter(Context context, List<Producto> catalog) {
            this.context = context;
            this.catalog = catalog;
        }
        @Override public int getCount() {
            return catalog.size();
        }
        @Override public Object getItem(int position) {
            return catalog.get(position);
        }
        @Override public long getItemId(int position) {
            return catalog.get(position).getId();
        }

        public class ViewHolder {
            public TextView tvNom;
            public TextView tvPrice;
            public ImageView imImage;
            public TextView tvStock;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Reciclat de vistes
            View myView = convertView;
            if (myView == null) {
                myView = inflater.inflate(R.layout.products_item, parent, false);
            }

            // Cogemos los items de la view
            TextView tvNombre = (TextView) myView.findViewById(R.id.nombre);
            TextView tvDescripcion = (TextView) myView.findViewById(R.id.descripcion);
            TextView tvPrecio = (TextView) myView.findViewById(R.id.precio);
            ImageView tvImatge = (ImageView) myView.findViewById(R.id.foto);

            // Cogemos el producto, le extraemos los atributos y los colocamos en los items
            Producto product = catalog.get(position);
            tvNombre.setText(product.getNombre());
            tvDescripcion.setText(product.getDescripcion());
            tvPrecio.setText(String.valueOf(catalog.get(position).getPrecio()) + " €");
            Set<Foto> fotoSet = product.getFotos();
            if (!fotoSet.isEmpty()) {
               // tvImatge.setImageResource(fotoSet);
            }

            return myView;
        }
    }
}
