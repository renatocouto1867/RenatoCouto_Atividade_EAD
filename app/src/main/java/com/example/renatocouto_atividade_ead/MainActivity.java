package com.example.renatocouto_atividade_ead;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.renatocouto_atividade_ead.ui.cadastrar.CadastrarDisciplinaFragment;
import com.example.renatocouto_atividade_ead.ui.home.HomeFragment;
import com.example.renatocouto_atividade_ead.ui.listadiciplina.ListarDisciplinasFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//<a href="https://www.flaticon.com/br/icones-gratis/professor" title="professor ícones">Professor ícones criados por Freepik - Flaticon</a>

public class MainActivity extends AppCompatActivity {
    private ImageButton botaoHome;
    private ImageButton botaoSair;
    private BottomNavigationView bottomNavigationView;
    private TextView textTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        inicializarViews();
        configurarCliques();

        //https://developer.android.com/reference/com/google/android/material/navigation/NavigationBarView#setItemActiveIndicatorColor(android.content.res.ColorStateList)
        //muda a cor da seleção dos icones na barra de navegação
        //public void setItemActiveIndicatorColor (ColorStateList csl)
        bottomNavigationView.setItemActiveIndicatorColor(ColorStateList.valueOf(getColor(R.color.gray_400)));
    }

    private void inicializarViews() {
        botaoHome = findViewById(R.id.btn_home);
        botaoSair = findViewById(R.id.btn_sair);
        bottomNavigationView = findViewById(R.id.nav_view);
        textTitulo = findViewById(R.id.txt_titulo_toolbar);

    }

    private void configurarCliques() {
        configurarBotaoSair();
        ouvinteCliqueMenuHome();
        configurarBottomNavigation();
    }

    private void ouvinteCliqueMenuHome() {
        botaoHome.setOnClickListener(view -> {
            HomeFragment homeFragment = new HomeFragment();
            iniciarFragment(homeFragment, R.string.sistema_disciplinas);
        });
    }

    private void iniciarFragment(Fragment fragment, int titulo) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        textTitulo.setText(titulo);
    }


    private void configurarBotaoSair() {
        botaoSair.setOnClickListener(view -> finish());
    }


    private boolean tratarCliqueMenu(@NonNull MenuItem itemMenu) {
        if (itemMenu.getItemId() == R.id.menu_cadastrar) {

            CadastrarDisciplinaFragment cadastrarDisciplinaFragment = new CadastrarDisciplinaFragment();
            iniciarFragment(cadastrarDisciplinaFragment, R.string.cadastrar_disciplinas);

            return true;
        }
        if (itemMenu.getItemId() == R.id.menu_listar) {

            ListarDisciplinasFragment listarDisciplinasFragment = new ListarDisciplinasFragment();
            iniciarFragment(listarDisciplinasFragment, R.string.lista_de_disciplinas);

            return true;
        }

        return false;
    }

    private void configurarBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> tratarCliqueMenu(item));
    }

}
