package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.ChatCallback;
import gameexange.com.gameexangeapp.controllers.managers.ChatManager;
import gameexange.com.gameexangeapp.controllers.managers.LoginManager;
import gameexange.com.gameexangeapp.models.Conversacion;
import gameexange.com.gameexangeapp.models.Mensaje;
import gameexange.com.gameexangeapp.models.Producto;
import gameexange.com.gameexangeapp.models.User;
import gameexange.com.gameexangeapp.models.UserExt;


public class ChatDetalleActivity extends AppCompatActivity implements ChatCallback {
    private ListView lvMensajes;
    private EditText etMensaje;
    private Button btnEnviar;
    private String userLogin;

    private Conversacion conversacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLogin = LoginManager.getInstance().getUsuario();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getBoolean("chatNuevo")) {
                Producto producto = extras.getParcelable("producto");
                UserExt userExt1 = new UserExt();
                User usuario1 = new User();
                usuario1.setLogin(userLogin);
                userExt1.setUser(usuario1);
                Conversacion conversacion = new Conversacion();
                conversacion.setProducto(producto);
                conversacion.setUsuario1(userExt1);
                conversacion.setUsuario2(producto.getUsuarioext());
                ChatManager.getInstance().crearNuevoChat(ChatDetalleActivity.this, conversacion);
            }

            if (extras.getBoolean("abrirChat")) {
                conversacion = extras.getParcelable("chat");
                ChatManager.getInstance().getAllChatMensajes(ChatDetalleActivity.this, conversacion);
            }
        }
    }

    @Override
    public void onSuccessMensajesList(final List<Mensaje> mensajeList) {
        setContentView(R.layout.mensajes_list);
        lvMensajes = (ListView) findViewById(R.id.list2);
        etMensaje = (EditText) findViewById(R.id.etMensaje);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        if (mensajeList.size() > 0) {
            MensajeListAdapter adapter = new MensajeListAdapter(this, mensajeList);
            lvMensajes.setAdapter(adapter);
        }

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etMensaje.getText().equals("")) {
                    enviarMensaje();
                }
            }
        });
    }

    @Override
    public void onSuccessChat(Conversacion conversacion) {
        Log.e("SuccessChat->", conversacion.toString());
        ChatManager.getInstance().getAllChatMensajes(ChatDetalleActivity.this, conversacion);
    }

    @Override
    public void onSuccessEnviarMensaje() {
        ChatManager.getInstance().getAllChatMensajes(ChatDetalleActivity.this, conversacion);
    }

    private void enviarMensaje() {
        Mensaje mensaje = new Mensaje();
        mensaje.setConversacion(conversacion);
        mensaje.setTexto(etMensaje.getText().toString());
        if (conversacion.getUsuario1().getUser().getLogin().equals(userLogin)) {
            mensaje.setEmisor(conversacion.getUsuario1().getUser());
            mensaje.setReceptor(conversacion.getUsuario2().getUser());
        } else {
            mensaje.setEmisor(conversacion.getUsuario2().getUser());
            mensaje.setReceptor(conversacion.getUsuario1().getUser());
        }

        ChatManager.getInstance().enviarMensaje(ChatDetalleActivity.this, mensaje);
    }

    @Override
    public void onSuccessChatList(List<Conversacion> conversacionList) {

    }
    @Override
    public void onFailure(Throwable t) {
        Log.e("ChatListActivity->", t.getMessage());
    }

    public class MensajeListAdapter extends BaseAdapter {
        private Context context;
        private List<Mensaje> mensajeList;
        private Mensaje mensaje;

        public MensajeListAdapter(Context context, List<Mensaje> mensajeList) {
            this.context = context;
            this.mensajeList = mensajeList;
        }

        @Override public int getCount() {
            return mensajeList.size();
        }
        @Override public Object getItem(int position) {
            return mensajeList.get(position);
        }
        @Override public long getItemId(int position) {
            return mensajeList.get(position).getId();
        }

        private class ViewHolder {
            private TextView tvMensaje1;
            private TextView tvMensaje2;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.mensajes_item, viewGroup, false);
                holder = new ViewHolder();
                holder.tvMensaje1 = (TextView) view.findViewById(R.id.mensaje1);
                holder.tvMensaje2 = (TextView) view.findViewById(R.id.mensaje2);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            mensaje = mensajeList.get(position);
            if (mensaje.getEmisor().getLogin().equals(userLogin)) {
                holder.tvMensaje2.setText(mensaje.getTexto());
                holder.tvMensaje1.setVisibility(View.INVISIBLE);
            } else {
                holder.tvMensaje1.setText(mensaje.getTexto());
                holder.tvMensaje2.setVisibility(View.INVISIBLE);
            }

            Log.e("MensajeListAdapter->", mensaje.toString());
            return view;
        }
    }
}

