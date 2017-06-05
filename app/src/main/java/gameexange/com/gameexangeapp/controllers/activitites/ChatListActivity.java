package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.ChatCallback;
import gameexange.com.gameexangeapp.controllers.managers.ChatManager;
import gameexange.com.gameexangeapp.controllers.managers.LoginManager;
import gameexange.com.gameexangeapp.models.Conversacion;
import gameexange.com.gameexangeapp.models.Mensaje;
import gameexange.com.gameexangeapp.models.UserExt;


public class ChatListActivity extends AppCompatActivity implements ChatCallback {
        private ListView chatsList;
        private String userLogin;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            userLogin = LoginManager.getInstance().getUsuario();
            ChatManager.getInstance().getAllUserChats(ChatListActivity.this);
        }

        @Override
        public void onSuccessChatList(final List<Conversacion> conversacions) {
            if (conversacions.size() > 0) {
                setContentView(R.layout.chats_list);
                chatsList = (ListView) findViewById(R.id.list1);

                ChatListAdapter adapter = new ChatListAdapter(this, conversacions);
                chatsList.setAdapter(adapter);
                chatsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (conversacions.get(i).getId() != null) {
                    Intent intent = new Intent(ChatListActivity.this, ChatDetalleActivity.class);
                    intent.putExtra("abrirChat", true);
                    intent.putExtra("chat", conversacions.get(i));
                    startActivity(intent);
                }
                    }
                });
            } else {
                setContentView(R.layout.chats_list_vacio);
            }
        }

        @Override
        public void onSuccessMensajesList(List<Mensaje> mensajeList) {

        }

    @Override
    public void onSuccessChat(Conversacion conversacion) {

    }

    @Override
    public void onSuccessEnviarMensaje() {

    }

    @Override
        public void onFailure(Throwable t) {
            Log.e("ChatListActivity->", t.getMessage());
        }

        public class ChatListAdapter extends BaseAdapter {
            private Context context;
            private List<Conversacion> conversacionList;
            private Conversacion conversacion;

            public ChatListAdapter(Context context, List<Conversacion> conversacionList) {
                this.context = context;
                this.conversacionList = conversacionList;
            }

            @Override public int getCount() {
                return conversacionList.size();
            }
            @Override public Object getItem(int position) {
                return conversacionList.get(position);
            }
            @Override public long getItemId(int position) {
                return conversacionList.get(position).getId();
            }

            private class ViewHolder {
                private ImageView imImagenUsuario;
                private TextView tvNombreUsuario;
                private TextView tvNombreProducto;
            }

            @Override
            public View getView(int position, View view, ViewGroup viewGroup) {
                ViewHolder holder;
                if (view == null) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.chats_item, viewGroup, false);
                    holder = new ViewHolder();
                    holder.imImagenUsuario = (ImageView) view.findViewById(R.id.imagenUsuario);
                    holder.tvNombreUsuario = (TextView) view.findViewById(R.id.nombreUsuario);
                    holder.tvNombreProducto = (TextView) view.findViewById(R.id.nombreProducto);

                    view.setTag(holder);
                } else {
                    holder = (ViewHolder) view.getTag();
                }

                conversacion = conversacionList.get(position);
                UserExt usuario;
                if (conversacion.getUsuario1().getUser().getLogin().equals(userLogin)) {
                    usuario = conversacion.getUsuario2();
                } else {
                    usuario = conversacion.getUsuario1();
                }

                if (usuario.getFoto() != null) {
                    String fotoUsuario = usuario.getFoto();
                    byte[] imageAsBytes = Base64.decode(fotoUsuario, Base64.DEFAULT);
                    holder.imImagenUsuario.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                    holder.imImagenUsuario.setMaxWidth(80);
                } else {
                    holder.imImagenUsuario.setImageResource(R.drawable.logo);
                }

                holder.tvNombreUsuario.setText(usuario.getUser().getLogin());
                holder.tvNombreProducto.setText(conversacion.getProducto().getNombre());

                Log.e("ChatListAdapter->", conversacion.toString());
                return view;
            }
        }
    }

