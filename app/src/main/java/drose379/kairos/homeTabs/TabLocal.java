package drose379.kairos.homeTabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import drose379.kairos.Category;
import drose379.kairos.LocalSubjectCreator;
import drose379.kairos.LocalSubjectReceiver;
import drose379.kairos.OwnerIdUtil;
import drose379.kairos.R;

public class TabLocal extends Fragment {

    private LocalSubjectReceiver subReceiver;
    private LocalSubjectCreator subCreater;

    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstance) {
        super.onCreateView(inflater, container, savedInstance);
        View v = inflater.inflate(R.layout.tab_local,container,false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (subReceiver == null) {
            subReceiver = new LocalSubjectReceiver(this,OwnerIdUtil.getOwnerID(getActivity()));
        }
        if (subCreater == null) {
            subCreater = new LocalSubjectCreator(this,OwnerIdUtil.getOwnerID(getActivity()));
        }
        subReceiver.initGrab();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void getLocalData(ArrayList<String> allCategories,ArrayList<Category> categories) {
        /*use categories with subjects to generate view
            * First, create custom adapter that creates the Category card views
            * Then, work with the ListView inside each card to display the cards subjects
        */
        ListView categoryContainer = (ListView) getView().findViewById(R.id.categoryHolder);
        categoryContainer.setAdapter(new CategoryCardAdapter(getActivity(), categories));

        //also need to initialize the FAB with the categories items
        FloatingActionButton subjectCreateButton = (FloatingActionButton) getView().findViewById(R.id.fab);
        subjectCreateButton.attachToListView(categoryContainer);
        initNewSubMenu(subjectCreateButton, allCategories);
    }

    public void initNewSubMenu(FloatingActionButton button,final ArrayList<String> categories) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog options = new MaterialDialog.Builder(getActivity())
                        .items(new String[]{"Category", "Subject"})
                        .title("Create New...")
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence selection) {
                                switch ((String) selection) {
                                    case "Category":
                                        initCategoryBuilderDialog();
                                        break;
                                    case "Subject":
                                        materialDialog.dismiss();
                                        categoryChooser(categories);
                                        break;
                                }
                            }
                        })
                        .build();
                options.show();
            }
        });

    }

    public void initCategoryBuilderDialog() {
        MaterialDialog cBuilder = new MaterialDialog.Builder(getActivity())
                .title("New Category")
                .customView(R.layout.new_category_layout,true)
                .positiveText("Done")
                .positiveColorRes(R.color.ColorPrimary)
                .negativeText("Cancel")
                .negativeColorRes(R.color.cancel)
                .autoDismiss(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        View dialogView = dialog.getCustomView();
                        if (newCategoryIsValid(dialogView)) {
                            dialog.dismiss();

                            EditText nameArea = (EditText) dialogView.findViewById(R.id.categoryName);
                            EditText descriptionArea = (EditText) dialogView.findViewById(R.id.categoryDescription);
                            String categoryName = nameArea.getText().toString();
                            String categoryDescription = descriptionArea.getText().toString();

                            subCreater.newCategory(categoryName,categoryDescription);
                        }
                    }
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build();
        cBuilder.show();
    }

    public void categoryChooser(ArrayList<String> categories) {
        final String[] menuItems = new String[categories.size()];

        for(int i=0;i<categories.size();i++) {
            menuItems[i] = categories.get(i);
        }

                MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                        .items(menuItems)
                        .title("Choose a Category")
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence selectedCategory) {
                                dialog.dismiss();
                                initSubBuilderDialog((String) selectedCategory);
                            }
                        });
                MaterialDialog categoryChooser = builder.build();
                categoryChooser.show();
    }

    public void initSubBuilderDialog(final String selectedCategory) {
        MaterialDialog subBuilder = new MaterialDialog.Builder(getActivity())
                .customView(R.layout.new_subject_layout,true)
                .title(selectedCategory + " - " + "New Subject")
                .positiveColorRes(R.color.ColorPrimary)
                .negativeColorRes(R.color.cancel)
                .positiveText("Done")
                .negativeText("Cancel")
                .autoDismiss(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        View dialogView = dialog.getCustomView();
                        if (newSubIsValid(dialogView)) {
                            dialog.dismiss();

                            EditText newSubject = (EditText) dialogView.findViewById(R.id.subName);
                            RadioGroup privacyGroup = (RadioGroup) dialogView.findViewById(R.id.privacyToggle);

                            String subject = newSubject.getText().toString();
                            int radioID = privacyGroup.getCheckedRadioButtonId();
                            RadioButton checkedButton = (RadioButton) privacyGroup.findViewById(radioID);

                            subCreater.newLocalSubject(selectedCategory,subject,checkedButton.getText().toString());
                        }
                    }
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build();
        subBuilder.show();
    }

    public boolean newSubIsValid(View dialogView) {
        boolean isValid = false;
        EditText subArea = (EditText) dialogView.findViewById(R.id.subName);
        RadioGroup privacyGroup = (RadioGroup) dialogView.findViewById(R.id.privacyToggle);

        String subject = subArea.getText().toString().isEmpty() ? null : subArea.getText().toString();
        int checkedID = privacyGroup.getCheckedRadioButtonId();

        if (subject != null && checkedID != -1) {
            isValid = true;
        }
        return isValid;
    }

    public boolean newCategoryIsValid(View dialogView) {
        boolean isValid = false;

        EditText name = (EditText) dialogView.findViewById(R.id.categoryName);
        EditText description = (EditText) dialogView.findViewById(R.id.categoryDescription);

        if (!name.getText().toString().isEmpty() && !description.getText().toString().isEmpty()) {
            isValid = true;
        }
        return isValid;
    }

    public void newSubCreated() {
        //Need to add action option to navigate to new subject
        Snackbar.make(getView(),"Success",Snackbar.LENGTH_SHORT).show();
        subReceiver.initGrab();
    }

    public void newCategoryCreated() {
        Snackbar.make(getView(),"Success",Snackbar.LENGTH_SHORT).show();
        subReceiver.initGrab();
    }


}
