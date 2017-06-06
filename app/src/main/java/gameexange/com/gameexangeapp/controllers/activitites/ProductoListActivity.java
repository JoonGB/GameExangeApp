package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import gameexange.com.gameexangeapp.ResultadosBusqueda;
import gameexange.com.gameexangeapp.controllers.managers.ProductoCallback;
import gameexange.com.gameexangeapp.controllers.managers.ProductoManager;
import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;

public class ProductoListActivity extends BaseDrawerActivity implements ProductoCallback {
    RecyclerView recyclerView;
    ProductosAdapter adapter;
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

            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            adapter = new ProductosAdapter(ProductoListActivity.this, productos);
            recyclerView.setAdapter(adapter);
            StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mStaggeredGridLayoutManager);
            /*productosGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (productos.get(i).getId() != null) {
                        Intent intent = new Intent(ProductoListActivity.this, ProductoDetalleActivity.class);
                        intent.putExtra("producto", productos.get(i).getId());
                        startActivity(intent);
                    }
                }
            });*/
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

            if (extras.getBoolean("errorUsuario")) {
                Toast.makeText(this, "Ha ocurrido un error al cargar tu perfil.", Toast.LENGTH_SHORT).show();
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



    class ProductosAdapter extends RecyclerView.Adapter<ProductoHolder> {
        private Context context;
        private List<Producto> productos;
        LayoutInflater inflater;

        public ProductosAdapter (Context context, List<Producto> productos) {
            this.context = context;
            this.productos = productos;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public ProductoHolder onCreateViewHolder(ViewGroup parent, int position) {
            View view = inflater.inflate(R.layout.producto_item, parent, false);
            ProductoHolder holder = new ProductoHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ProductoHolder holder, final int position) {
            holder.tvPrecio.setText(productos.get(position).getPrecio().toString()+"€");
            holder.tvNombre.setText(productos.get(position).getNombre());
            if (productos.get(position).getFotoPrincipal() != null) {
                String fotoprincipal = productos.get(position).getFotoPrincipal().getFoto();
                byte[] imageAsBytes = Base64.decode(fotoprincipal, Base64.DEFAULT);
                holder.ivProducto.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            } else {
                holder.ivProducto.setImageResource(R.drawable.logo);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (productos.get(position).getId() != null) {
                        Intent intent = new Intent(ProductoListActivity.this, ProductoDetalleActivity.class);
                        intent.putExtra("producto", productos.get(position).getId());
                        startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return productos.size();
        }
    }


    class ProductoHolder extends RecyclerView.ViewHolder{
        ImageView ivProducto;
        TextView tvNombre;
        TextView tvPrecio;

        public ProductoHolder(View itemView) {
            super(itemView);
            ivProducto = (ImageView) itemView.findViewById(R.id.ivProducto);
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombre);
            tvPrecio = (TextView) itemView.findViewById(R.id.tvPrecio);
        }
    }
}
