package com.osa.desafio.agencias.handler;

public class AgenciaNaoEncontrada extends RuntimeException{
    public AgenciaNaoEncontrada(String mensagem){
        super(mensagem);
    }
}
