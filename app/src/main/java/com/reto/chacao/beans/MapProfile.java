package com.reto.chacao.beans;

/**
 * Created by caponte on 7/7/15.
 */
public class MapProfile {

    private boolean filtro_cultura;
    private boolean filtro_cultura_eventos;
    private boolean filtro_cultura_servicios;
    private boolean filtro_deporte;
    private boolean filtro_deporte_eventos;
    private boolean filtro_deporte_servicios;

    public MapProfile() {
        this.filtro_cultura = true;
        this.filtro_cultura_eventos = true;
        this.filtro_cultura_servicios = true;
        this.filtro_deporte = true;
        this.filtro_deporte_eventos = true;
        this.filtro_deporte_servicios = true;
    }

    public boolean isFiltro_cultura() {
        return filtro_cultura;
    }

    public boolean isFiltro_cultura_eventos() {
        return filtro_cultura_eventos;
    }

    public boolean isFiltro_cultura_servicios() {
        return filtro_cultura_servicios;
    }

    public boolean isFiltro_deporte() {
        return filtro_deporte;
    }

    public boolean isFiltro_deporte_eventos() {
        return filtro_deporte_eventos;
    }

    public boolean isFiltro_deporte_servicios() {
        return filtro_deporte_servicios;
    }

    public void setFiltro_cultura(boolean filtro_cultura) {
        this.filtro_cultura = filtro_cultura;
    }

    public void setFiltro_cultura_eventos(boolean filtro_cultura_eventos) {
        this.filtro_cultura_eventos = filtro_cultura_eventos;
    }

    public void setFiltro_cultura_servicios(boolean filtro_cultura_servicios) {
        this.filtro_cultura_servicios = filtro_cultura_servicios;
    }

    public void setFiltro_deporte(boolean filtro_deporte) {
        this.filtro_deporte = filtro_deporte;
    }

    public void setFiltro_deporte_eventos(boolean filtro_deporte_eventos) {
        this.filtro_deporte_eventos = filtro_deporte_eventos;
    }

    public void setFiltro_deporte_servicios(boolean filtro_deporte_servicios) {
        this.filtro_deporte_servicios = filtro_deporte_servicios;
    }




}
