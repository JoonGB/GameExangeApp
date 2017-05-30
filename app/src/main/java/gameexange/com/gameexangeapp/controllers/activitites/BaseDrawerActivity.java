package gameexange.com.gameexangeapp.controllers.activitites;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import gameexange.com.gameexangeapp.R;
public class BaseDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

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
        textView.setAdapter(new AutoCompleteVideojuegosAdapter(this, videojuegos));
*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BaseDrawerActivity.this, "Crear producto", Toast.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.base_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
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
            Toast.makeText(this, "Mis chats", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_ajustes) {
            Toast.makeText(this, "Ajustes", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_compartir) {
            Toast.makeText(this, "Gracias por compartir", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
