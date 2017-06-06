package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.UserManager;
import android.support.design.widget.FloatingActionButton;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import gameexange.com.gameexangeapp.FiltrosBusquedaProductos;
import gameexange.com.gameexangeapp.ObtenerLocalizacion;
import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.ResultadosBusqueda;
import gameexange.com.gameexangeapp.controllers.managers.UsuarioCallback;
import gameexange.com.gameexangeapp.controllers.managers.UsuarioManager;
import gameexange.com.gameexangeapp.models.UserExt;
import gameexange.com.gameexangeapp.models.Videojuego;

public class BaseDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UsuarioCallback {
    private View view;

    private ImageView ivUsuario;
    private TextView tvLoginUsuario;
    private TextView tvEmailUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        NavigationView header = (NavigationView) findViewById(R.id.nav_view);
        View headerView = header.getHeaderView(0);
        ivUsuario = (ImageView) headerView.findViewById(R.id.ivUsuario);
        tvLoginUsuario = (TextView) headerView.findViewById(R.id.tvLoginUsuario);
        tvEmailUsuario = (TextView) headerView.findViewById(R.id.tvEmailUsuario);

/*
        List<Videojuego> videojuegos = new ArrayList<>();
        /*Videojuego nuevoVideojuego = new Videojuego();
        nuevoVideojuego.setId(7346L);
        nuevoVideojuego.setNombre("The Legend of Zelda: Breath of the Wild");
        nuevoVideojuego.setMiniatura("http://images.igdb.com/igdb/image/upload/t_thumb/jk9el4ksl4c7qwaex2y5.png");
        videojuegos.add(nuevoVideojuego);
        Videojuego nuevoVideojuego2 = new Videojuego();
        nuevoVideojuego2.setId(19459L);
        nuevoVideojuego2.setNombre("FIFA 17");
        nuevoVideojuego2.setMiniatura("http://images.igdb.com/igdb/image/upload/t_thumb/cmtplicvdajycqx2vz6t.png");
        videojuegos.add(nuevoVideojuego2);
        final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.buscador);
        textView.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
            @Override
            public void onEditorAcitionListener(TextView v, int actionId, KeyEvent event) {
                if(textView.getText().toString().length() > 2) {
                    VideojuegoManager.getInstance().busquedaProductos(BaseDrawerActivity.this, textView.getText().toString());
                    //ProductoManager.getInstance().getAllProductosDTO(ProductoListActivity.this);
                }
            }
        });
*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BaseDrawerActivity.this, CrearProductoActivity.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        UsuarioManager.getInstance().getUsuarioExtByLogin(BaseDrawerActivity.this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.busqueda_videojuego_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            startActivity(new Intent(BaseDrawerActivity.this, FiltrosBusquedaProductos.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_mis_productos) {
            Toast.makeText(this, "Mis productos", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_videojuegos_deseados) {
            Toast.makeText(this, "¿Qué deseas?", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_mis_chats) {
            startActivity(new Intent(BaseDrawerActivity.this, ChatListActivity.class));
        } else if (id == R.id.nav_ajustes) {
            Toast.makeText(this, "Ajustes", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_compartir) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Descárgate Gameexchange! Muy pronto en Play Store.");
            startActivity(Intent.createChooser(intent, "Compartir con"));
        } else if (id == R.id.nav_busqueda_videojuegos) {
            Intent intent = new Intent(this, BusquedaVideojuego.class);
            startActivity(intent);
        } else if (id == R.id.nav_localizacion) {
            Intent intent = new Intent(this, ObtenerLocalizacion.class);
            startActivity(intent);
        } else if (id == R.id.nav_productos) {
            Intent intent = new Intent(this, ResultadosBusqueda.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSuccessUserExt(UserExt userExt) {
        if (userExt.getFoto() != null) {
            String fotoUsuario = userExt.getFoto();
            byte[] imageAsBytes = Base64.decode(fotoUsuario, Base64.DEFAULT);
            ivUsuario.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } else {
            ivUsuario.setImageResource(R.drawable.logo);
        }

        tvLoginUsuario.setText(userExt.getUser().getLogin());
        tvEmailUsuario.setText(userExt.getUser().getEmail());
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
