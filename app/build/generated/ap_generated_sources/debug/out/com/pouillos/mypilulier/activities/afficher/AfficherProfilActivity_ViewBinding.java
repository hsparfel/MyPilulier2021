// Generated code from Butter Knife. Do not modify!
package com.pouillos.mypilulier.activities.afficher;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mypilulier.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AfficherProfilActivity_ViewBinding implements Unbinder {
  private AfficherProfilActivity target;

  private View view7f0800be;

  @UiThread
  public AfficherProfilActivity_ViewBinding(AfficherProfilActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AfficherProfilActivity_ViewBinding(final AfficherProfilActivity target, View source) {
    this.target = target;

    View view;
    target.selectedProfil = Utils.findRequiredViewAsType(source, R.id.selectProfil, "field 'selectedProfil'", AutoCompleteTextView.class);
    target.listProfil = Utils.findRequiredViewAsType(source, R.id.listProfil, "field 'listProfil'", TextInputLayout.class);
    target.layoutDate = Utils.findRequiredViewAsType(source, R.id.layoutDate, "field 'layoutDate'", TextInputLayout.class);
    target.textDate = Utils.findRequiredViewAsType(source, R.id.textDate, "field 'textDate'", TextInputEditText.class);
    target.layoutTaille = Utils.findRequiredViewAsType(source, R.id.layoutTaille, "field 'layoutTaille'", TextInputLayout.class);
    target.textTaille = Utils.findRequiredViewAsType(source, R.id.textTaille, "field 'textTaille'", TextInputEditText.class);
    target.layoutPoids = Utils.findRequiredViewAsType(source, R.id.layoutPoids, "field 'layoutPoids'", TextInputLayout.class);
    target.textPoids = Utils.findRequiredViewAsType(source, R.id.textPoids, "field 'textPoids'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.fabDelete, "field 'fabDelete' and method 'fabDeleteClick'");
    target.fabDelete = Utils.castView(view, R.id.fabDelete, "field 'fabDelete'", FloatingActionButton.class);
    view7f0800be = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabDeleteClick();
      }
    });
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.activity_main_toolbar, "field 'toolbar'", Toolbar.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AfficherProfilActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.selectedProfil = null;
    target.listProfil = null;
    target.layoutDate = null;
    target.textDate = null;
    target.layoutTaille = null;
    target.textTaille = null;
    target.layoutPoids = null;
    target.textPoids = null;
    target.fabDelete = null;
    target.toolbar = null;
    target.progressBar = null;

    view7f0800be.setOnClickListener(null);
    view7f0800be = null;
  }
}