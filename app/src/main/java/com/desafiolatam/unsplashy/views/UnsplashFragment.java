package com.desafiolatam.unsplashy.views;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.desafiolatam.unsplashy.R;
import com.desafiolatam.unsplashy.adapter.UnsplashyAdapter;
import com.desafiolatam.unsplashy.background.GetDataSplash;
import com.desafiolatam.unsplashy.models.Unsplash;
import com.desafiolatam.unsplashy.photos.PhotoListener;
import com.desafiolatam.unsplashy.photos.SavePhoto;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UnsplashFragment extends Fragment implements PhotoListener {

    private static final int RC_WRITE = 111;
    private UnsplashyAdapter adapter;

    public UnsplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unsplash, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new UnsplashyAdapter(this);
        recyclerView.setAdapter(adapter);

        new GetPhotos().execute();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
            Toast.makeText(getContext(), "Gracias, para reanudar la descarga presiona la foto", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "No podemos descargar la foto sin tu permisos", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void photosave(Unsplash unsplash) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions, RC_WRITE);
        } else {
            new SavePhoto().downloadExample(unsplash, getContext());
        }
    }

    private class GetPhotos extends GetDataSplash {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(List<Unsplash> unsplashes) {
            adapter.update(unsplashes);
            progressDialog.dismiss();
        }
    }


}
