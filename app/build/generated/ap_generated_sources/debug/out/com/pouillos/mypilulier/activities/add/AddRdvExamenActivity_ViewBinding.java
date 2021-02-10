// Generated code from Butter Knife. Do not modify!
package com.pouillos.mypilulier.activities.add;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mypilulier.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddRdvExamenActivity_ViewBinding implements Unbinder {
  private AddRdvExamenActivity target;

  private View view7f0800c6;

  @UiThread
  public AddRdvExamenActivity_ViewBinding(AddRdvExamenActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddRdvExamenActivity_ViewBinding(final AddRdvExamenActivity target, View source) {
    this.target = target;

    View view;
    target.selectedExamen = Utils.findRequiredViewAsType(source, R.id.selectionExamen, "field 'selectedExamen'", AutoCompleteTextView.class);
    target.listExamen = Utils.findRequiredViewAsType(source, R.id.listExamen, "field 'listExamen'", TextInputLayout.class);
    target.layoutDate = Utils.findRequiredViewAsType(source, R.id.layoutDate, "field 'layoutDate'", TextInputLayout.class);
    target.textDate = Utils.findRequiredViewAsType(source, R.id.textDate, "field 'textDate'", TextInputEditText.class);
    target.layoutHeure = Utils.findRequiredViewAsType(source, R.id.layoutHeure, "field 'layoutHeure'", TextInputLayout.class);
    target.textHeure = Utils.findRequiredViewAsType(source, R.id.textHeure, "field 'textHeure'", TextInputEditText.class);
    target.layoutNote = Utils.findRequiredViewAsType(source, R.id.layoutNote, "field 'layoutNote'", TextInputLayout.class);
    target.textNote = Utils.findRequiredViewAsType(source, R.id.textNote, "field 'textNote'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.fabSave, "field 'fabSave' and method 'fabSaveClick'");
    target.fabSave = Utils.castView(view, R.id.fabSave, "field 'fabSave'", FloatingActionButton.class);
    view7f0800c6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fabSaveClick();
      }
    });
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.my_progressBar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddRdvExamenActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.selectedExamen = null;
    target.listExamen = null;
    target.layoutDate = null;
    target.textDate = null;
    target.layoutHeure = null;
    target.textHeure = null;
    target.layoutNote = null;
    target.textNote = null;
    target.fabSave = null;
    target.progressBar = null;

    view7f0800c6.setOnClickListener(null);
    view7f0800c6 = null;
  }
}