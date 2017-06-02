package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.ProductoCallback;
import gameexange.com.gameexangeapp.controllers.managers.ProductoManager;
import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;

public class ProductoListActivity extends BaseDrawerActivity implements ProductoCallback {
    private GridView productosGrid;
    LayoutInflater inflater;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) findViewById(R.id.content_layout);

        ProductoManager.getInstance().getAllProductosDTO(ProductoListActivity.this);
    }

    @Override
    public void onSuccessProductosList(final List<Producto> productos) {
        if (productos.size() > 0) {
            inflater.inflate(R.layout.activity_products, linearLayout);

            productosGrid = (GridView) findViewById(R.id.grid1);

            ProductoListAdapter adapter = new ProductoListAdapter(this, productos);
            productosGrid.setAdapter(adapter);
            productosGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (productos.get(i).getId() != null) {
                        Intent intent = new Intent(ProductoListActivity.this, ProductoDetalleActivity.class);
                        intent.putExtra("producto", productos.get(i).getId());
                        startActivity(intent);
                    }
                }
            });
        } else {
            inflater.inflate(R.layout.productos_nohayproductos, linearLayout);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getBoolean("errorProducto")) {
                Toast.makeText(this, R.string.error_producto, Toast.LENGTH_SHORT).show();
            }
            if (extras.getBoolean("productoCreado")) {
                Toast.makeText(this, "El producto se ha creado correctamente.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccessProducto(Producto producto) {

    }
    @Override
    public void onSuccessFotos(List<Foto> fotos) {

    }
    @Override
    public void onSuccessCrearProducto() {

    }
    @Override
    public void onSuccessFotoPrincipal(Foto foto) {

    }

    @Override
    public void onFailure(Throwable t) {
        Log.e("ProductoListActivity->", t.getMessage());
    }

    public class ProductoListAdapter extends BaseAdapter {
        private Context context;
        private List<Producto> productosList;
        private Producto producto;

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

        private class ViewHolder {
            private ImageView imImagen;
            private TextView tvNombre;
            private TextView tvPrecio;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.products_item, viewGroup, false);
                holder = new ViewHolder();
                holder.tvNombre = (TextView) view.findViewById(R.id.nombre);
                holder.tvPrecio = (TextView) view.findViewById(R.id.precio);
                holder.imImagen = (ImageView) view.findViewById(R.id.foto);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            producto = productosList.get(position);
            holder.tvNombre.setText(producto.getNombre());
            holder.tvPrecio.setText(String.valueOf(productosList.get(position).getPrecio()) + " €");
            if (producto.getFotoPrincipal() != null) {
                String fotoprincipal = producto.getFotoPrincipal().getFoto();
                byte[] imageAsBytes = Base64.decode(fotoprincipal, Base64.DEFAULT);
                holder.imImagen.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                holder.imImagen.setMaxWidth(80);
            } else {
                holder.imImagen.setImageResource(R.drawable.logo);
            }
    Log.e("ProductListAdapter->", producto.toString());
            return view;
        }


    }
}
