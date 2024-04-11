package br.com.melo.screenmatch.Model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEp;
    private Double avaliacao;
    private LocalDate dataLancamento;

    public Episodio(Integer temporada, DadosEpisodio d) {
        this.temporada = temporada;
        this.titulo = d.titulo();
        this.numeroEp = d.episodio();
        try {
            this.avaliacao = Double.valueOf(d.avaliacao());
        }catch(NumberFormatException ex){
            this.avaliacao = 0.0;
        }

        try {
            this.dataLancamento = LocalDate.parse(d.dataLancamento());
        }catch(DateTimeParseException ex){
            this.dataLancamento = null;
        }
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEp() {
        return numeroEp;
    }

    public void setNumeroEp(Integer numeroEp) {
        this.numeroEp = numeroEp;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return "temporada= " + temporada +
                ", titulo= '" + titulo + '\'' +
                ", numeroEp= " + numeroEp +
                ", avaliacao= " + avaliacao +
                ", dataLancamento= " + dataLancamento;
    }
}
