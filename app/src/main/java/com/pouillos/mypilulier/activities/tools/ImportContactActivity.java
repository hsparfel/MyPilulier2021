package com.pouillos.mypilulier.activities.tools;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orm.SugarRecord;
import com.pouillos.mypilulier.R;
import com.pouillos.mypilulier.activities.NavDrawerActivity;
import com.pouillos.mypilulier.activities.utils.DateUtils;
import com.pouillos.mypilulier.entities.Contact;
import com.pouillos.mypilulier.entities.ImportContact;
import com.pouillos.mypilulier.entities.Profession;
import com.pouillos.mypilulier.entities.SavoirFaire;
import com.pouillos.mypilulier.interfaces.BasicUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;

public class ImportContactActivity extends NavDrawerActivity implements Serializable, BasicUtils {

    @State
    ImportContact importContact;

    private List<ImportContact> listImportContactBD;

    @BindView(R.id.nbContactImported)
    TextView nbContactImported;

    @BindView(R.id.fabImport)
    FloatingActionButton fabImport;
    @BindView(R.id.fabSupprDoublon)
    FloatingActionButton fabSupprDoublon;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_import_contact);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        ButterKnife.bind(this);

        progressBar.setVisibility(View.VISIBLE);

        ImportContactActivity.AsyncTaskRunnerBDImportContact runnerBDImportContact = new ImportContactActivity.AsyncTaskRunnerBDImportContact();
        runnerBDImportContact.execute();

        setTitle("Import Contact");

        afficherNbContactEnregistre();
    }

    public void afficherNbContactEnregistre() {
        Long nb = Contact.count(Contact.class);
        int total = 1825106;
        Long pourcentage = nb*100/total;
        nbContactImported.setText(" nb de contacts importes:" + nb.toString()+"/"+total+" ("+pourcentage+"%)");
    }

    public class AsyncTaskRunnerBDImportContact extends AsyncTask<Void, Integer, Void> {
        protected Void doInBackground(Void...voids) {
            publishProgress(0);
            publishProgress(10);
            listImportContactBD = ImportContact.listAll(ImportContact.class);
            Collections.sort(listImportContactBD);
            publishProgress(100);
            return null;
        }
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(ImportContactActivity.this, "recup des list d'import terminee", Toast.LENGTH_LONG).show();
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }

    @OnClick(R.id.fabImport)
    public void fabImportClick() {
        progressBar.setVisibility(View.VISIBLE);
        ImportContactActivity.AsyncTaskRunnerContact runnerContact = new ImportContactActivity.AsyncTaskRunnerContact(this);
        runnerContact.execute();

    }

    private class AsyncTaskRunnerContact extends AsyncTask<Void, Integer, Void> {

        private Context context;
        public AsyncTaskRunnerContact(Context context) {
            this.context=context;
        }

        protected Void doInBackground(Void...voids) {

            InputStream is = null;
            BufferedReader reader = null;

            List<Profession> listProfession = Profession.listAll(Profession.class);
            Map<String, Profession> mapProfession = new HashMap<>();
            for (Profession profession : listProfession) {
                mapProfession.put(profession.getName(), profession);
            }
            List<SavoirFaire> listSavoirFaire = SavoirFaire.listAll(SavoirFaire.class);
            Map<String, SavoirFaire> mapSavoirFaire = new HashMap<>();
            for (SavoirFaire savoirFaire : listSavoirFaire) {
                mapSavoirFaire.put(savoirFaire.getName(), savoirFaire);
            }

            List<ImportContact> listImportContact = ImportContact.find(ImportContact.class,"import_completed = ?","0");
            //List<ImportContact> listImportContact = ImportContact.listAll(ImportContact.class);
//TODO A REVOIR
            int nbImportEffectue =0;
            int nbImportIgnore = 0;
            int readerCount=0;
            int nbLigneLue=0;
            //ImportContact current = listImportContact.get(0);
            for (ImportContact current : listImportContact) {
                nbImportEffectue =0;
                nbImportIgnore = 0;
                //readerCount=0;
                nbLigneLue=0;
                if (current.getDateDebut() == null) {
                    current.setDateDebut(DateUtils.ecrireDateHeure(new Date()));
                }
                if (current.getNbLigneLue() != 0) {
                    nbLigneLue = current.getNbLigneLue();
                }
                if (current.getNbImportEffectue() != 0) {
                    nbImportEffectue = current.getNbImportEffectue();
                }
                if (current.getNbImportIgnore() != 0) {
                    nbImportIgnore = current.getNbImportIgnore();
                }

                //current.setDateFin("");
                //current.setNbImportEffectue(nbImportEffectue);
                ///current.setNbImportIgnore(nbImportIgnore);
                // nbImportEffectue = 0;
                // nbImportIgnore = 0;

                try {
                    is = getAssets().open(current.getPath());
                    reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                    String line = null;

                    int readerSize = 20000;
                    readerCount = 0;
                    int compteur = 0;
                    publishProgress(compteur);
                    while ((line = reader.readLine()) != null) {
                        //todo revoir cette condition

                            if (readerCount<nbLigneLue) {
                                readerCount ++;
                                continue;
                            }
                        readerCount ++;
                        compteur = readerCount*100/readerSize;
                        //compteur ++;
                        publishProgress(compteur);
                        final String SEPARATEUR = "\\|";
                        String lineSplitted[] = line.split(SEPARATEUR);

                        if (lineSplitted[1].equals("Identifiant PP")) {
                            nbImportIgnore++;
                            current.setNbImportIgnore(nbImportIgnore);
                            nbLigneLue++;
                            current.setNbLigneLue(nbLigneLue);
                            current.save();
                            continue;
                        }

                        Contact contact = new Contact();
                        contact.setIdPP(lineSplitted[2]);
                        contact.setCodeCivilite(lineSplitted[3]);
                        contact.setNom(lineSplitted[7].toUpperCase());
                        if (lineSplitted[8].length()>1) {
                            String prenom = lineSplitted[8].substring(0,1).toUpperCase()+lineSplitted[8].substring(1,lineSplitted[8].length()-1).toLowerCase();
                        }
                        contact.setPrenom(lineSplitted[8]);
                        contact.setProfession(mapProfession.get(lineSplitted[10]));

                        if (lineSplitted.length>16) {
                            if (lineSplitted[16].equals("Qualifié en Médecine Générale") || lineSplitted[16].equals("Spécialiste en Médecine Générale")) {
                                contact.setSavoirFaire(mapSavoirFaire.get("Médecine Générale"));
                            } else {
                                contact.setSavoirFaire(mapSavoirFaire.get(lineSplitted[16]));
                            }
                        }

                        if (lineSplitted.length>24) {
                            contact.setRaisonSocial(lineSplitted[24]);
                        }
                        if (lineSplitted.length>26) {
                            contact.setComplement(lineSplitted[26]);
                            if (contact.getComplement().equalsIgnoreCase(contact.getRaisonSocial())) {
                                contact.setComplement(null);
                            }
                        }
                        String adresse = "";
                        if ((lineSplitted.length>28) && (!lineSplitted[28].isEmpty())){
                            adresse = lineSplitted[28]+" ";
                        }

                        if ((lineSplitted.length>31) && (!lineSplitted[31].isEmpty())){
                            adresse += lineSplitted[31]+" ";
                        }
                        if ((lineSplitted.length>32) && (!lineSplitted[32].isEmpty())){
                            adresse += lineSplitted[32];
                        }
                        contact.setAdresse(adresse.toUpperCase());

                        if (lineSplitted.length>34 && !lineSplitted[34].isEmpty())  {
                            if (lineSplitted.length>39 && lineSplitted[39].equalsIgnoreCase("Israel")) {
//plutot different de france à voir si necessaire
                            } else {
                                contact.setCp(lineSplitted[34].substring(0,5));
                                contact.setVille(lineSplitted[34].substring(6));
                            }



                        } else {
                            contact.setCp("");
                        }
                        if (lineSplitted.length>40 && !lineSplitted[40].isEmpty())  {
                            lineSplitted[40] = lineSplitted[40].replace(" ", "");
                            lineSplitted[40] = lineSplitted[40].replace(".", "");
                            if (lineSplitted[40].length() == 9) {
                                contact.setTelephone("0" + lineSplitted[40]);
                            } else if (lineSplitted[40].length() == 10) {
                                contact.setTelephone(lineSplitted[40]);
                            }
                        }
                        if (lineSplitted.length>42 && !lineSplitted[42].isEmpty())  {
                            lineSplitted[42] = lineSplitted[42].replace(" ", "");
                            lineSplitted[42] = lineSplitted[42].replace(".", "");
                            if (lineSplitted[42].length() == 9) {
                                contact.setFax("0" + lineSplitted[42]);
                            } else if (lineSplitted[42].length() == 10) {
                                contact.setFax(lineSplitted[42]);
                            }
                        }
                        if (lineSplitted.length>43 && !lineSplitted[43].isEmpty())  {
                            contact.setEmail(lineSplitted[43]);
                        }

                       /* List<ContactLight> listContactLight = ContactLight.find(ContactLight.class, "id_pp = ?",lineSplitted[2]);
                        if (listContactLight.size()>0) {

                            boolean bool = false;
                            for (ContactLight contactLight : listContactLight){
                                if (comparer(contactLight, contact)){
                                    Log.i("existant","medecin deja cree: "+lineSplitted[2]);
                                    bool = true;
                                    continue;
                                }
                            }
                            if (bool) {
                                continue;
                            }
                        }*/
                        if (contact.getAdresse() != null && contact.getCp() != null && contact.getVille() != null) {
                            contact.enregisterCoordonnees(context);
                        }
                        //Log.i("enregistre","medecin new: "+lineSplitted[2]);
                        contact.save();
                        nbLigneLue++;
                        nbImportEffectue++;
                        current.setNbLigneLue(nbLigneLue);
                        current.setNbImportEffectue(nbImportEffectue);
                        current.save();
                    }
                } catch (final Exception e) {
                    nbImportIgnore++;
                    current.setNbImportIgnore(nbImportIgnore);
                    current.save();
                    e.printStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException ignored) {
                        }
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException ignored) {
                        }
                    }
                }
                current.setDateFin(DateUtils.ecrireDateHeure(new Date()));
                current.setNbImportIgnore(nbImportIgnore);
                current.setNbImportEffectue(nbImportEffectue);
                current.setImportCompleted(true);
                current.save();
                publishProgress(100);
                //a voir si ça passe
                //Toast.makeText(MainActivity.this, "Import de " + current.getPath() + " fini", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            afficherNbContactEnregistre();
            Toast.makeText(ImportContactActivity.this, "IMPORT TOTAL FINI", Toast.LENGTH_LONG).show();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }

    @OnClick(R.id.fabSupprDoublon)
    public void fabSupprDoublonClick() {

        Toast.makeText(ImportContactActivity.this, "raz import contact & contact", Toast.LENGTH_LONG).show();
       //mis en commentaire pr eviter raz non voulue
        // SugarRecord.executeQuery("DELETE FROM IMPORT_CONTACT");
       // SugarRecord.executeQuery("DELETE FROM CONTACT");
       // SugarRecord.executeQuery("update import_contact set import_completed =0 where id = 48");
        SugarRecord.executeQuery("update import_contact set import_completed =0 where id = 71");
    }




}
