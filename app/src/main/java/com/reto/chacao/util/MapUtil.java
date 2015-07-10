package com.reto.chacao.util;

import android.content.Context;

import com.reto.chacao.beans.MapProfile;

import java.util.ArrayList;

/**
 * Created by caponte on 7/7/15.
 */
public class MapUtil {

    private static String FILTRO_CULTURA = "filtro_cultura";
    private static String FILTRO_CULTURA_SERVICIOS = "filtro_cultura_servicios";
    private static String FILTRO_CULTURA_EVENTOS = "filtro_cultura_eventos";
    private static String FILTRO_DEPORTES = "filtro_deportes";
    private static String FILTRO_DEPORTES_SERVICIOS = "filtro_deportes_servicios";
    private static String FILTRO_DEPORTES_EVENTOS = "filtro_deportes_eventos";
    private static String FILTRO_BASQUET = "filtro_basquet";
    private static String FILTRO_BEISBOL = "filtro_beisbol";
    private static String FILTRO_FITNESS = "filtro_fitness";
    private static String FILTRO_FUTBOL = "filtro_futbol";
    private static String FILTRO_NATACION = "filtro_natacion";
    private static String FILTRO_SKATE ="filtro_skate";
    private static String FILTRO_TENIS = "filtro_tenis";
    private static String FILTRO_VOLLEYBALL = "filtro_volleyball";
    private static String FILTRO_YOGA = "filtro_yoga";
    private static String FILTRO_ZUMBA = "filtro_zumba";
    private static String FILTRO_ARTE = "filtro_arte";
    private static String FILTRO_CINE = "filtro_cine";
    private static String FILTRO_DANZA = "filtro_danza";
    private static String FILTRO_FOTOS = "filtro_fotos";
    private static String FILTRO_GASTRONOMIA = "filtro_gastronomia";
    private static String FILTRO_IDIOMA = "filtro_idioma";
    private static String FILTRO_MUSICA = "filtro_musica";
    private static String FILTRO_TEATRO = "filtro_teatro";


    private CharSequence[] deportes = { "Basquet", "Beisbol", "Fitness", "Futbol", "Natacion", "Skate", "Tenis", "Volleyball", "Yoga", "Zumba" };


    public static void saveMapFilters(Context context, MapProfile map) {

        SharedPreferenceUtil.save(context, FILTRO_CULTURA, map.isFiltro_cultura());
        SharedPreferenceUtil.save(context, FILTRO_CULTURA_EVENTOS, map.isFiltro_cultura_eventos());
        SharedPreferenceUtil.save(context, FILTRO_CULTURA_SERVICIOS, map.isFiltro_cultura_servicios());
        SharedPreferenceUtil.save(context, FILTRO_DEPORTES, map.isFiltro_deporte());
        SharedPreferenceUtil.save(context, FILTRO_DEPORTES_EVENTOS, map.isFiltro_deporte_eventos());
        SharedPreferenceUtil.save(context, FILTRO_DEPORTES_SERVICIOS, map.isFiltro_deporte_servicios());
        SharedPreferenceUtil.save(context, FILTRO_BASQUET, map.isFiltro_basquet());
        SharedPreferenceUtil.save(context, FILTRO_BEISBOL, map.isFiltro_beisbol());
        SharedPreferenceUtil.save(context, FILTRO_FITNESS, map.isFiltro_fitness());
        SharedPreferenceUtil.save(context, FILTRO_FUTBOL, map.isFiltro_futbol());
        SharedPreferenceUtil.save(context, FILTRO_NATACION, map.isFiltro_natacion());
        SharedPreferenceUtil.save(context, FILTRO_SKATE, map.isFiltro_skate());
        SharedPreferenceUtil.save(context, FILTRO_TENIS, map.isFiltro_tenis());
        SharedPreferenceUtil.save(context, FILTRO_VOLLEYBALL, map.isFiltro_volleyball());
        SharedPreferenceUtil.save(context, FILTRO_YOGA, map.isFiltro_yoga());
        SharedPreferenceUtil.save(context, FILTRO_ZUMBA, map.isFiltro_zumba());
        SharedPreferenceUtil.save(context, FILTRO_ARTE, map.isFiltro_arte());
        SharedPreferenceUtil.save(context, FILTRO_CINE, map.isFiltro_cine());
        SharedPreferenceUtil.save(context, FILTRO_DANZA, map.isFiltro_danza());
        SharedPreferenceUtil.save(context, FILTRO_FOTOS, map.isFiltro_fotos());
        SharedPreferenceUtil.save(context, FILTRO_GASTRONOMIA, map.isFiltro_gastronomia());
        SharedPreferenceUtil.save(context, FILTRO_IDIOMA, map.isFiltro_idiomas());
        SharedPreferenceUtil.save(context, FILTRO_MUSICA, map.isFiltro_musica());
        SharedPreferenceUtil.save(context, FILTRO_TEATRO, map.isFiltro_teatro());
    }


    public static MapProfile getMapFilters(Context context) {

        MapProfile map = new MapProfile();

        map.setFiltro_cultura(SharedPreferenceUtil.getBoolean(context, FILTRO_CULTURA));
        map.setFiltro_cultura_eventos(SharedPreferenceUtil.getBoolean(context, FILTRO_CULTURA_EVENTOS));
        map.setFiltro_cultura_servicios(SharedPreferenceUtil.getBoolean(context, FILTRO_CULTURA_SERVICIOS));
        map.setFiltro_deporte(SharedPreferenceUtil.getBoolean(context, FILTRO_DEPORTES));
        map.setFiltro_deporte_eventos(SharedPreferenceUtil.getBoolean(context, FILTRO_DEPORTES_EVENTOS));
        map.setFiltro_deporte_servicios(SharedPreferenceUtil.getBoolean(context, FILTRO_DEPORTES_SERVICIOS));
        map.setFiltro_basquet(SharedPreferenceUtil.getBoolean(context, FILTRO_BASQUET));
        map.setFiltro_beisbol(SharedPreferenceUtil.getBoolean(context, FILTRO_BEISBOL));
        map.setFiltro_fitness(SharedPreferenceUtil.getBoolean(context, FILTRO_FITNESS));
        map.setFiltro_futbol(SharedPreferenceUtil.getBoolean(context, FILTRO_FUTBOL));
        map.setFiltro_natacion(SharedPreferenceUtil.getBoolean(context, FILTRO_NATACION));
        map.setFiltro_skate(SharedPreferenceUtil.getBoolean(context, FILTRO_SKATE));
        map.setFiltro_tenis(SharedPreferenceUtil.getBoolean(context, FILTRO_TENIS));
        map.setFiltro_volleyball(SharedPreferenceUtil.getBoolean(context, FILTRO_VOLLEYBALL));
        map.setFiltro_yoga(SharedPreferenceUtil.getBoolean(context, FILTRO_YOGA));
        map.setFiltro_zumba(SharedPreferenceUtil.getBoolean(context, FILTRO_ZUMBA));
        map.setFiltro_arte(SharedPreferenceUtil.getBoolean(context, FILTRO_ARTE));
        map.setFiltro_cine(SharedPreferenceUtil.getBoolean(context, FILTRO_CINE));
        map.setFiltro_danza(SharedPreferenceUtil.getBoolean(context, FILTRO_DANZA));
        map.setFiltro_gastronomia(SharedPreferenceUtil.getBoolean(context, FILTRO_GASTRONOMIA));
        map.setFiltro_fotos(SharedPreferenceUtil.getBoolean(context, FILTRO_FOTOS));
        map.setFiltro_idiomas(SharedPreferenceUtil.getBoolean(context, FILTRO_IDIOMA));
        map.setFiltro_musica(SharedPreferenceUtil.getBoolean(context, FILTRO_MUSICA));
        map.setFiltro_teatro(SharedPreferenceUtil.getBoolean(context, FILTRO_TEATRO));

        return map;
    }

    public static void setFiltroCultura(Context context, boolean register) {
        SharedPreferenceUtil.save(context, FILTRO_CULTURA, register);
    }

    public static boolean getFiltroCultura(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_CULTURA);
    }

    public static void setFiltroCulturaEventos(Context context, boolean register) {
        SharedPreferenceUtil.save(context, FILTRO_CULTURA_EVENTOS, register);
    }

    public static boolean getFiltroCulturaEventos(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_CULTURA_EVENTOS);
    }

    public static void setFiltroCulturaServicios(Context context, boolean register) {
        SharedPreferenceUtil.save(context, FILTRO_CULTURA_SERVICIOS, register);
    }

    public static void setFiltrosCategoriasDeporte(Context context, ArrayList<CharSequence> deportesSeleccionados){

        for(CharSequence deporte: deportesSeleccionados){
            if (deporte.toString().equals("Basquet"))
                SharedPreferenceUtil.save(context, FILTRO_BASQUET, true);
            else if (deporte.toString().equals("Beisbol"))
                SharedPreferenceUtil.save(context, FILTRO_BEISBOL, true);
            else if (deporte.toString().equals("Fitness"))
                SharedPreferenceUtil.save(context, FILTRO_FITNESS, true);
            else if (deporte.toString().equals("Futbol"))
                SharedPreferenceUtil.save(context, FILTRO_FUTBOL, true);
            else if (deporte.toString().equals("Natacion"))
                SharedPreferenceUtil.save(context, FILTRO_NATACION, true);
            else if (deporte.toString().equals("Skate"))
                SharedPreferenceUtil.save(context, FILTRO_SKATE, true);
            else if (deporte.toString().equals("Tenis"))
                SharedPreferenceUtil.save(context, FILTRO_TENIS, true);
            else if (deporte.toString().equals("Volleyball"))
                SharedPreferenceUtil.save(context, FILTRO_VOLLEYBALL, true);
            else if (deporte.toString().equals("Yoga"))
                SharedPreferenceUtil.save(context, FILTRO_YOGA, true);
            else if (deporte.toString().equals("Zumba"))
                SharedPreferenceUtil.save(context, FILTRO_ZUMBA, true);
        }

    }

    public static void setFiltrosCategoriasCultura(Context context, ArrayList<CharSequence> culturasSeleccionados){

        for(CharSequence cultura: culturasSeleccionados){
            if (cultura.toString().equals("Arte"))
                SharedPreferenceUtil.save(context, FILTRO_ARTE, true);
            else if (cultura.toString().equals("Cine"))
                SharedPreferenceUtil.save(context, FILTRO_CINE, true);
            else if (cultura.toString().equals("Danza"))
                SharedPreferenceUtil.save(context, FILTRO_DANZA, true);
            else if (cultura.toString().equals("Fotos"))
                SharedPreferenceUtil.save(context, FILTRO_FOTOS, true);
            else if (cultura.toString().equals("Gastronomia"))
                SharedPreferenceUtil.save(context, FILTRO_GASTRONOMIA, true);
            else if (cultura.toString().equals("Idiomas"))
                SharedPreferenceUtil.save(context, FILTRO_IDIOMA, true);
            else if (cultura.toString().equals("Musica"))
                SharedPreferenceUtil.save(context, FILTRO_MUSICA, true);
            else if (cultura.toString().equals("Teatro"))
                SharedPreferenceUtil.save(context, FILTRO_TEATRO, true);
        }

    }

    public static boolean getFiltroCulturaServicios(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_CULTURA_SERVICIOS);
    }

    public static void setFiltroDeportes(Context context, boolean register) {
        SharedPreferenceUtil.save(context, FILTRO_DEPORTES, register);
    }

    public static boolean getFiltroDeportes(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_DEPORTES);
    }

    public static void setFiltroDeportesEventos(Context context, boolean register) {
        SharedPreferenceUtil.save(context, FILTRO_DEPORTES_EVENTOS, register);
    }

    public static boolean getFiltroDeportesEventos(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_DEPORTES_EVENTOS);
    }

    public static void setFiltroDeportesServicios(Context context, boolean register) {
        SharedPreferenceUtil.save(context, FILTRO_DEPORTES_SERVICIOS, register);
    }

    public static boolean getFiltroDeportesServicios(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_CULTURA_SERVICIOS);
    }

    public static boolean getFiltroBasquet(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_BASQUET);
    }

    public static boolean getFiltroBeisbol(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_BEISBOL);
    }

    public static boolean getFiltroFitness(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_FITNESS);
    }

    public static boolean getFiltroFutbol(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_FUTBOL);
    }

    public static boolean getFiltroNatacion(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_NATACION);
    }

    public static boolean getFiltroSkate(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_SKATE);
    }

    public static boolean getFiltroTenis(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_TENIS);
    }

    public static boolean getFiltroVolleyball(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_VOLLEYBALL);
    }

    public static boolean getFiltroYoga(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_YOGA);
    }

    public static boolean getFiltroZumba(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_ZUMBA);
    }

    public static boolean getFiltroArte(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_ARTE);
    }

    public static boolean getFiltroCine(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_CINE);
    }

    public static boolean getFiltroDanza(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_DANZA);
    }

    public static boolean getFiltroFotos(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_FOTOS);
    }

    public static boolean getFiltroGastronomia(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_GASTRONOMIA);
    }

    public static boolean getFiltroIdiomas(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_IDIOMA);
    }

    public static boolean getFiltroMusica(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_MUSICA);
    }

    public static boolean getFiltroTeatro(Context context) {
        return SharedPreferenceUtil.getBoolean(context, FILTRO_TEATRO);
    }


}
