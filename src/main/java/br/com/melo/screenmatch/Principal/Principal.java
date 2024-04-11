package br.com.melo.screenmatch.Principal;

import br.com.melo.screenmatch.Model.DadosEpisodio;
import br.com.melo.screenmatch.Model.DadosSerie;
import br.com.melo.screenmatch.Model.DadosTemporada;
import br.com.melo.screenmatch.Model.Episodio;
import br.com.melo.screenmatch.Service.ConsumoAPI;
import br.com.melo.screenmatch.Service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);

    private ConsumoAPI consumoAPI = new ConsumoAPI();

    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=137a622b";

    public void ExibeMenu() {

        System.out.println("Digite o nome da série a ser buscada ");
        var nome = leitura.nextLine();

        var json = consumoAPI.obterDados(ENDERECO + nome.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadaList = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemp(); i++) {
            json = consumoAPI.obterDados(ENDERECO + nome + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadaList.add(dadosTemporada);
        }

        //Lambda ou função anônima.

        temporadaList.forEach(t -> t.episodioList().forEach(e -> System.out.println(e.titulo())));

//        List<String> nomes = Arrays.asList("Jacque", "Iasmin", "Paulo", "Rodrigo", "Nico");
//
//        // Exemplo de Stream
//        nomes.stream()
//                .sorted()
//                .limit(3)
//                .filter(n -> n.startsWith("N"))
//                .map(n -> n.toUpperCase())
//                .forEach(System.out::println);

        List<DadosEpisodio> dadosEpisodios = temporadaList.stream()
                .flatMap(t -> t.episodioList().stream())
                .collect(Collectors.toList());

        System.out.println("\nTop 5 episódios: ");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadaList.stream()
                .flatMap(t -> t.episodioList().stream()
                        .map(d -> new Episodio(t.temporada(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        //Filtro por ano
        System.out.println("\nA partir de que ano você deseja ver os episódios?\n");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println("Temporada :" + e.getTemporada() +
                            ", Episódio: " + e.getTitulo() +
                            ", Data de Lançamento: " + e.getDataLancamento().format(formatter)));
    }
}
