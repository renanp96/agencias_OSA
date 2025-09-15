package com.osa.desafio;


import com.osa.desafio.agencias.handler.AgenciaNaoEncontrada;
import com.osa.desafio.agencias.models.Agencia;
import com.osa.desafio.agencias.repository.AgenciaRepository;
import com.osa.desafio.agencias.service.AgenciaService;
import com.osa.desafio.agencias.service.DistanciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AgenciaService Test")
public class AgenciaServiceTest {

    @Mock
    private AgenciaRepository agenciaRepository;

    @Mock
    private DistanciaService distanciaService;

    @InjectMocks
    private AgenciaService agenciaService;

    private Agencia agencia1;
    private Agencia agencia2;
    private Agencia agencia3;

    @BeforeEach
    void setup(){
        agencia1 = new Agencia(1L, "Agência Norte 21", 3.0, 4.0);
        agencia2 = new Agencia(2L, "Agência Oeste 89", 6.0, 8.0);
        agencia3 = new Agencia(3L, "Agência Sul 124", 10.0, 10.0);
    }

    @Test
    @DisplayName("Deve retornar todas as agências")
    void deveRetornarTodasAgencias(){
        List<Agencia> agencias = Arrays.asList(agencia1, agencia2, agencia3);
        Mockito.when(agenciaRepository.findAll()).thenReturn(agencias);

        List<Agencia> resultado = agenciaService.getAgencias();

        assertThat(resultado).hasSize(3).containsExactlyInAnyOrder(agencia1, agencia2, agencia3);
        verify(agenciaRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar agência se o ID existir")
    void deveRetornarAgenciaQuandoExistir(){
        Long id = 1L;
        when(agenciaRepository.findById(id)).thenReturn(Optional.of(agencia1));

        Agencia resultado = agenciaService.getAgenciaPeloID(id);

        assertThat(resultado).isEqualTo(agencia1);
        verify(agenciaRepository).findById(id);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio se a agência não existir")
    void deveLancarExcecaoQuandoIdNaoExistir(){
        Long id = 123L;
        when(agenciaRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> agenciaService.getAgenciaPeloID(id))
                .isInstanceOf(AgenciaNaoEncontrada.class)
                .hasMessage("Agência não encontrada no sistema.");

        verify(agenciaRepository).findById(id);
    }

    @Test
    @DisplayName("Deve salvar uma nova agência corretamente")
    void deveSalvarNovaAgencia(){
        Agencia novaAgencia = new Agencia(4L, "Nova Agência", 22.0, -12.0);

        when(agenciaRepository.save(novaAgencia)).thenReturn(novaAgencia);

        Agencia resultado = agenciaService.postNovaAgencia(novaAgencia);

        assertThat(resultado).isNotNull()
                .hasFieldOrPropertyWithValue("id", 4L)
                .hasFieldOrPropertyWithValue("nome", "Nova Agência")
                .hasFieldOrPropertyWithValue("coordenadaX", 22.0)
                .hasFieldOrPropertyWithValue("coordenadaY", -12.0);
        verify(agenciaRepository).save(novaAgencia);
    }

    @Test
    @DisplayName("Deve retornar uma lista contendo as agências mais próxima com base na distância informada")
    void deveRetornarAgenciaMaisProxima() {
        List<Agencia> todasAgencias = Arrays.asList(agencia1, agencia2, agencia3);
        when(agenciaRepository.findAll()).thenReturn(todasAgencias);

        when(distanciaService.calcularDistancia(0.0, 0.0, 3.0, 4.0)).thenReturn(5.0);
        when(distanciaService.calcularDistancia(0.0, 0.0, 6.0, 8.0)).thenReturn(10.0);
        when(distanciaService.calcularDistancia(0.0, 0.0, 10.0, 10.0)).thenReturn(14.14);

        List<Map<String, Object>> resultado = agenciaService.getAgenciaMaisProxima(0.0, 0.0);
        assertThat(resultado).isNotEmpty();

        Map<String, Object> agenciaMaisProxima = resultado.get(0);

        assertThat(agenciaMaisProxima)
                .containsEntry("id", 1L)
                .containsEntry("nome", "Agência Norte 21")
                .containsEntry("coordX", 3.0)
                .containsEntry("coordY", 4.0)
                .containsEntry("distancia", 5.0);

        verify(agenciaRepository).findAll();
        verify(distanciaService).calcularDistancia(0.0, 0.0, 3.0, 4.0);
        verify(distanciaService).calcularDistancia(0.0, 0.0, 6.0, 8.0);
        verify(distanciaService).calcularDistancia(0.0, 0.0, 10.0, 10.0);
    }

    @Test
    @DisplayName("Deve deletar uma agência com base no ID")
    void deveDeletarAgenciaQuandoIdExistir(){
        Long id = 1L;

        agenciaService.deleteAgenciaPeloID(id);
        verify(agenciaRepository, times(1)).deleteById(id);
    }
}
