package com.reto.chacao.util;

import android.content.Context;

import com.reto.chacao.beans.MapProfile;

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


    public static void saveMapFilters(Context context, MapProfile map) {

        SharedPreferenceUtil.save(context, FILTRO_CULTURA, map.isFiltro_cultura());
        SharedPreferenceUtil.save(context, FILTRO_CULTURA_EVENTOS, map.isFiltro_cultura_eventos());
        SharedPreferenceUtil.save(context, FILTRO_CULTURA_SERVICIOS, map.isFiltro_cultura_servicios());
        SharedPreferenceUtil.save(context, FILTRO_DEPORTES, map.isFiltro_deporte());
        SharedPreferenceUtil.save(context, FILTRO_DEPORTES_EVENTOS, map.isFiltro_deporte_eventos());
        SharedPreferenceUtil.save(context, FILTRO_DEPORTES_SERVICIOS, map.isFiltro_deporte_servicios());
    }


    public static MapProfile getMapFilters(Context context) {

        MapProfile map = new MapProfile();

        map.setFiltro_cultura(SharedPreferenceUtil.getBoolean(context, FILTRO_CULTURA));
        map.setFiltro_cultura_eventos(SharedPreferenceUtil.getBoolean(context, FILTRO_CULTURA_EVENTOS));
        map.setFiltro_cultura_servicios(SharedPreferenceUtil.getBoolean(context, FILTRO_CULTURA_SERVICIOS));
        map.setFiltro_deporte(SharedPreferenceUtil.getBoolean(context, FILTRO_DEPORTES));
        map.setFiltro_deporte_eventos(SharedPreferenceUtil.getBoolean(context, FILTRO_DEPORTES_EVENTOS));
        map.setFiltro_deporte_servicios(SharedPreferenceUtil.getBoolean(context, FILTRO_DEPORTES_SERVICIOS));

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

}
