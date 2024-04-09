package br.com.melo.screenmatch.Service;

public interface IConverteDados {
    //Classe genérica
    <T> T obterDados(String json, Class<T> classe);
}
