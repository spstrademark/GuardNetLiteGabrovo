package Fragments;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardnet_lite_gabrovo.R;

import java.io.File;
import java.util.ArrayList;

import Common.SettingsUtils;
import GalleryTools.GalleryAdapter;
import GalleryTools.GalleryList;

public class GalleryFragment extends Fragment {
    SettingsUtils settings;

    private RecyclerView recyclerView;
    private Button gridViewButton;
    private Button listViewButton;

    private static final int GRID = 3;
    private static final int LIST = 1;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.gallery_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settings = SettingsUtils.getInstance();

        gridViewButton = view.findViewById(R.id.gridView);
        listViewButton = view.findViewById(R.id.listView);
        recyclerView = view.findViewById(R.id.imageGalleryRecyclerView);

//        view.setVisibility(View.GONE);
        initRecycleView(view);
        setButtonEvents();
    }

    private File[] GetItems() {
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getResources().getString(R.string.app_name));
        return f.listFiles();
    }

    private void initRecycleView(@NonNull View view) {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), settings.getGalleryView());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<GalleryList> galleryLists = prepareData();

        if (galleryLists != null && galleryLists.size() > 0) {
            view.findViewById(R.id.EmptyGallery).setVisibility(View.INVISIBLE);
            GalleryAdapter adapter = new GalleryAdapter(view.getContext(), galleryLists);
            recyclerView.setHasFixedSize(false);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapter);
        } else {
            gridViewButton.setVisibility(View.INVISIBLE);
            listViewButton.setVisibility(View.INVISIBLE);
        }
    }

    private ArrayList<GalleryList> prepareData() {
        ArrayList<GalleryList> theimage = new ArrayList<>();
        File items[] = GetItems();

        if (items != null && items.length > 0) {
            for (int i = 0; i < items.length; i++) {
                GalleryList galleryList = new GalleryList();
                String path = items[i].toString();
                String title = path.substring(path.lastIndexOf("/") + 1);
                galleryList.setImage_title(title);
                galleryList.setImage_ID(path);
                theimage.add(galleryList);
            }
        }

        return theimage;
    }

    private void setButtonEvents() {
        gridViewButton.setOnClickListener(view1 -> {
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view1.getContext(), GRID);
            recyclerView.setLayoutManager(layoutManager);
            settings.setGalleryView(GRID);
        });

        listViewButton.setOnClickListener(view12 -> {
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view12.getContext(), LIST);
            recyclerView.setLayoutManager(layoutManager);
            settings.setGalleryView(LIST);
        });


    }

    public static Fragment getInstance() {
        return new GalleryFragment();
    }
}