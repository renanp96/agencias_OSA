package com.osa.desafio;


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
        agencia1 = new Agencia(1L, "Agência Perto", 3.0, 4.0);
        agencia2 = new Agencia(2L, "Agência Média", 6.0, 8.0);
        agencia3 = new Agencia(3L, "Agência Longe", 10.0, 10.0);
    }

    @Test
    @DisplayName("Deve retornar todas as agencias")
    void deveRetornarTodasAgencias(){
        List<Agencia> agencias = Arrays.asList(agencia1, agencia2, agencia3);
        Mockito.when(agenciaRepository.findAll()).thenReturn(agencias);

        List<Agencia> resultado = agenciaService.getAgencias();

        assertThat(resultado).hasSize(3).containsExactlyInAnyOrder(agencia1, agencia2, agencia3);
        verify(agenciaRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar agencia se o ID existir")
    void deveRetornarAgenciaQuandoExistir(){
        Long id = 1L;
        when(agenciaRepository.findById(id)).thenReturn(Optional.of(agencia1));

        Optional<Agencia> resultado = agenciaService.getAgenciaPeloID(id);

        assertThat(resultado).isPresent().contains(agencia1);
        verify(agenciaRepository).findById(id);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio se o ID não existir")
    void deveRetornarOptonalQuandoIdVazio(){
        Long id = 123L;
        when(agenciaRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Agencia> resultado = agenciaService.getAgenciaPeloID(id);

        assertThat(resultado).isEmpty();
        verify(agenciaRepository).findById(id);
    }

    @Test
    @DisplayName("Deve salvar uma nova agencia corretamente")
    void deveSalvarNovaAgencia(){
        Agencia novaAgencia = new Agencia(4L, "Nova Agencia", 22.0, -12.0);

        when(agenciaRepository.save(novaAgencia)).thenReturn(novaAgencia);

        Agencia resultado = agenciaService.postNovaAgencia(novaAgencia);

        assertThat(resultado).isNotNull()
                .hasFieldOrPropertyWithValue("id", 4L)
                .hasFieldOrPropertyWithValue("nome", "Nova Agencia")
                .hasFieldOrPropertyWithValue("coordenadaX", 22.0)
                .hasFieldOrPropertyWithValue("coordenadaY", -12.0);
        verify(agenciaRepository).save(novaAgencia);
    }

    @Test
    @DisplayName("Deve retornar a agência mais próxima com distância calculada")
    void deveRetornarAgenciaMaisProxima() {
        List<Agencia> todasAgencias = Arrays.asList(agencia1, agencia2, agencia3);
        when(agenciaRepository.findAll()).thenReturn(todasAgencias);

        when(distanciaService.calcularDistancia(0.0, 0.0, 3.0, 4.0)).thenReturn(5.0);
        when(distanciaService.calcularDistancia(0.0, 0.0, 6.0, 8.0)).thenReturn(10.0);
        when(distanciaService.calcularDistancia(0.0, 0.0, 10.0, 10.0)).thenReturn(14.14);

        Map<String, Object> resultado = agenciaService.getAgenciaMaisProxima(0.0, 0.0);

        assertThat(resultado)
                .containsEntry("id", 1L)
                .containsEntry("nome", "Agência Perto")
                .containsEntry("coordX", 3.0)
                .containsEntry("coordY", 4.0)
                .containsEntry("distancia", 5.0);

        verify(agenciaRepository).findAll();
        verify(distanciaService).calcularDistancia(0.0, 0.0, 3.0, 4.0);
        verify(distanciaService).calcularDistancia(0.0, 0.0, 6.0, 8.0);
        verify(distanciaService).calcularDistancia(0.0, 0.0, 10.0, 10.0);
    }

    @Test
    @DisplayName("Deve deletar uma agencia com base no ID")
    void deveDeletarAgenciaQuandoIdExistir(){
        Long id = 1L;

        agenciaService.deleteAgenciaPeloID(id);

        verify(agenciaRepository, times(1)).deleteById(id);
    }
}
