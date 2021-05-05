package com.pouillos.mypilulier.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.allyants.notifyme.NotifyMe;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.pouillos.mypilulier.MyApp;
import com.pouillos.mypilulier.R;
import com.pouillos.mypilulier.activities.add.AddPrescriptionActivity;

import com.pouillos.mypilulier.activities.utils.DateUtils;
import com.pouillos.mypilulier.dao.AssociationFormeDoseDao;
import com.pouillos.mypilulier.dao.DaoMaster;
import com.pouillos.mypilulier.dao.DaoSession;
import com.pouillos.mypilulier.dao.DoseDao;
import com.pouillos.mypilulier.dao.FormePharmaceutiqueDao;
import com.pouillos.mypilulier.dao.ImportMedicamentDao;
import com.pouillos.mypilulier.dao.MedicamentDao;
import com.pouillos.mypilulier.dao.MedicamentLightDao;
import com.pouillos.mypilulier.dao.PrescriptionDao;
import com.pouillos.mypilulier.dao.PriseDao;
import com.pouillos.mypilulier.entities.AssociationFormeDose;
import com.pouillos.mypilulier.entities.Dose;
import com.pouillos.mypilulier.entities.Medicament;
import com.pouillos.mypilulier.entities.Prise;
import com.pouillos.mypilulier.interfaces.BasicUtils;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Executor;


import icepick.Icepick;

public class NavDrawerActivity extends AppCompatActivity implements BasicUtils {

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    protected Toolbar toolbar;
    protected DrawerLayout drawerLayout;
    protected BottomNavigationView bottomNavigationView;

    protected DaoSession daoSession;

    protected AssociationFormeDoseDao associationFormeDoseDao;
    protected DoseDao doseDao;
    protected FormePharmaceutiqueDao formePharmaceutiqueDao;
    protected ImportMedicamentDao importMedicamentDao;
    protected MedicamentDao medicamentDao;
    protected MedicamentLightDao medicamentLightDao;
    protected PrescriptionDao prescriptionDao;
    protected PriseDao priseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiserDao();
        associationFormeDoseDao = daoSession.getAssociationFormeDoseDao();
        doseDao = daoSession.getDoseDao();
        formePharmaceutiqueDao = daoSession.getFormePharmaceutiqueDao();
        importMedicamentDao = daoSession.getImportMedicamentDao();
        medicamentDao = daoSession.getMedicamentDao();
        medicamentLightDao = daoSession.getMedicamentLightDao();
        prescriptionDao = daoSession.getPrescriptionDao();
        priseDao = daoSession.getPriseDao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myProfilActivity;
        return true;
    }

    public void configureBottomView(){
        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bottom_navigation_home:
                                ouvrirActiviteSuivante(NavDrawerActivity.this, AccueilActivity.class, true);
                                break;
                            case R.id.bottom_navigation_add_prescritpion:
                                ouvrirActiviteSuivante(NavDrawerActivity.this, AddPrescriptionActivity.class, true);
                                break;
                            case R.id.bottom_navigation_raz_prescription:
                                razDb();
                                break;
                        }
                        return true;
                    }
                });
    }

    public void razDb() {
        prescriptionDao.deleteAll();
        priseDao.deleteAll();
    }

    public void configureToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    public void ouvrirActiviteSuivante(Context context, Class classe, boolean bool) {
        Intent intent = new Intent(context, classe);
        startActivity(intent);
        if (bool) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    protected <T> void buildDropdownMenu(List<T> listObj, Context context, AutoCompleteTextView textView) {
        List<String> listString = new ArrayList<>();
        String[] listDeroulante;
        listDeroulante = new String[listObj.size()];
        for (T object : listObj) {
            listString.add(object.toString());
        }
        listString.toArray(listDeroulante);
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.list_item, listDeroulante);
        textView.setAdapter(adapter);
    }

    @Override
    public Executor getMainExecutor() {
        return super.getMainExecutor();
    }

    protected boolean isFilled(TextInputEditText textInputEditText){
        boolean bool;
        if (textInputEditText.length()>0) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void initialiserDao() {
        //Base pendant dev
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "my_pilulier_db");
        //Base de prod
        //AppOpenHelper helper = new AppOpenHelper(this, "my_pilulier_db", null);
        Database db = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public Dose recupDose(Medicament medoc) {
        long formeId = medoc.getFormePharmaceutiqueId();
        AssociationFormeDose assocFormeDose = associationFormeDoseDao.queryRaw("where forme_pharmaceutique_id = ?",""+formeId).get(0);
        Dose dose = doseDao.load(assocFormeDose.getDoseId());
        return dose;
    }

    public Date initDate(Date date) {
        GregorianCalendar calendar = new java.util.GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,7);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        date = calendar.getTime();
        return date;
    }

    // ATTENTION pr test on ajoute 1 minute seulement.
    public Date ajouterJour(Date date, int i) {
        GregorianCalendar calendar = new java.util.GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        //calendar.add(Calendar.MINUTE,1);
        date = calendar.getTime();
        return date;
    }

    protected Date findDateJour() {
        Date date = initDate(new Date());
        return date;
    }
}
