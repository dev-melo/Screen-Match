package br.com.melo.screenmatch.Principal;

import br.com.melo.screenmatch.Model.DadosEpisodio;
import br.com.melo.screenmatch.Model.DadosSerie;
import br.com.melo.screenmatch.Model.DadosTemporada;
import br.com.melo.screenmatch.Service.ConsumoAPI;
import br.com.melo.screenmatch.Service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);

    private ConsumoAPI consumoAPI = new ConsumoAPI();

    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=137a622b";

    public void ExibeMenu(){

        System.out.println("Digite o nome da série a ser buscada ");
        var nome = leitura.nextLine();

        var json = consumoAPI.obterDados(ENDERECO + nome.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadaList = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemp(); i++) {
            json = consumoAPI.obterDados(ENDERECO + nome +"&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadaList.add(dadosTemporada);
        }

		temporadaList.forEach(System.out::println);

//        for (int i = 0; i < dados.totalTemp(); i++){
//            List<DadosEpisodio> episodios = temporadaList.get(i).episodioList();
//            for (int j = 0; j<episodios.size();j++){
//                System.out.println(episodios.get(j).titulo());
//            }
//        }

        //Lambda, onde refaz totalmente o For anterior.

        temporadaList.forEach(t -> t.episodioList().forEach(e -> System.out.println(e.titulo())));
    }
}
