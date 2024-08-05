package br.app.backend.service;

public class SMSNotificacaoService implements NotificacaoService {

    @Override
    public void enviarNotificacao(String mensagem, String destinatario) {
        System.out.println("Enviando SMS para " + destinatario + " : " + mensagem);
    }
}
